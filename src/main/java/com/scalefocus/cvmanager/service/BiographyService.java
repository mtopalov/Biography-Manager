package com.scalefocus.cvmanager.service;

import com.scalefocus.cvmanager.converter.EuroPassConverter;
import com.scalefocus.cvmanager.exception.BiographyNotFoundException;
import com.scalefocus.cvmanager.exception.WrongFormatException;
import com.scalefocus.cvmanager.model.biography.Biography;
import com.scalefocus.cvmanager.repository.BiographyRepository;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

/**
 * Service that handles any manipulation of {@link Biography}. Either it is a database CRUD or any other operation.
 * Also does requests to "Europass REST API".
 *
 * @author Mariyan Topalov
 */
@Service
public class BiographyService {

    /**
     * List of supported formats.
     */
    private final List<String> supportedFormats = Arrays.asList("word", "xml", "pdf");

    private final Logger logger = LoggerFactory.getLogger(BiographyService.class);

    private final BiographyRepository repository;

    private final EuroPassConverter converter;

    public BiographyService(BiographyRepository repository, EuroPassConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    //GET MAPPINGS

    /**
     * Finds the {@link Biography} that corresponds to the given {@link Long} id, given as parameter, and returns it as {@link JSONObject}.
     * Throws {@link BiographyNotFoundException} if no biography with the given id exist.
     *
     * @param id the id to be searched for
     * @return the biography that corresponds to the given id, converted to json object
     *
     * @throws BiographyNotFoundException if no biography with given id was found.
     */
    public Biography findById(Long id) throws BiographyNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new BiographyNotFoundException("There's no biography with that id!"));
    }

    //DELETE MAPPINGS

    /**
     * Deletes a single {@link Biography} from the database by id, given as argument.
     *
     * @param id the biography with that id will be deleted.
     */
    public void deleteById(Long id) throws BiographyNotFoundException {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new BiographyNotFoundException("No biography with id " + id + " exists!");
        }
    }

    /**
     * Saves the given {@link Biography} to the database.
     *
     * @param biography the biography to be saved.
     * @return saved biography as JsonObject
     *
     * @throws IOException if IOException occurs.
     */
    public JSONObject save(Biography biography) throws IOException {
        String imagePath = biography.getIdentification().getPhoto().getData();
        byte[] image = getBytesOfImage(imagePath);
        String encodedImage = encode(image);
        biography.getIdentification().getPhoto().setData(encodedImage);
        repository.save(biography);
        return converter.toJsonObject(biography);
    }

    /**
     * Gets the {@link Biography} from the database, by given id as argument.
     * Makes a request to "Europass REST API" with the biography and the given format.
     * If the biography, given as request body, is valid and the format is one of the supported formats, a response from the "Europass REST API" will be returned.
     * The returned response will be written to a file, in the project directory. The file format is based on the argument format.
     *
     * @param id     the id of the Biography.
     * @param format the desired file format.
     * @throws BiographyNotFoundException if biography with the given ID does not exist.
     * @throws WrongFormatException       if the chosen format is not supported.
     */
    public void convertToDesiredFileFormat(Long id, String format) throws BiographyNotFoundException, WrongFormatException {
        if (isValid(format)) {
            throw new WrongFormatException("The format you've entered is not available! Available formats are "
                    + String.join(", ", supportedFormats) + ".");
        }
        String BASE_URL = "https://europass.cedefop.europa.eu/rest/v1/document/to/";
        String uri = BASE_URL + format;
        Biography requestBody = findById(id);
        byte[] response = getResponseFromEuropassApi(uri, requestBody);
        if ("word".equals(format)) {
            format = "doc";
        }
        writeBiographyToFile(response, createFileName(requestBody), format);
    }

    /**
     * Returns true if the {@link String} format, given as argument, is a supported format.
     *
     * @param format to be verified.
     */
    private boolean isValid(String format) {
        return supportedFormats.contains(format);
    }

    /**
     * Takes the {@link byte[]} image, given as argument, and returns it's {@link Base64} representation.
     *
     * @param image to be encoded.
     * @return {@link Base64} representation of the {@link byte[]} image.
     */
    private String encode(byte[] image) {
        return Base64.getEncoder().encodeToString(image);
    }

    /**
     * Reads an image by {@link String} path, given as argument and returns it as byte[].
     * If the path is incorrect, a default photo is returned.
     *
     * @param imagePath the path of the image.
     * @return the image converted to byte[], if the image cannot be retrieved from the path, given as argument, a default photo is retrieved.
     *
     * @throws IOException if IOException occurs.
     */
    private byte[] getBytesOfImage(String imagePath) throws IOException {
        byte[] imageAsByteArray;
        try (InputStream inputStream = new URL(imagePath).openStream()) {
            imageAsByteArray = IOUtils.toByteArray(inputStream);
        } catch (MalformedURLException exception) {
            //if the url is not fine, a default image will be loaded
            imageAsByteArray = Files.readAllBytes(Paths.get("default-image.jpg"));
        }
        return imageAsByteArray;
    }

    /**
     * Converts the {@link Biography}, given as argument, to {@link JSONObject}.
     *
     * @param biography biography to be converted.
     * @return the biography, given as argument, to JSONObject
     */
    public JSONObject asJsonObject(Biography biography) {
        return converter.toJsonObject(biography);
    }

    /**
     * Makes a {@link org.springframework.http.HttpMethod#POST} request to the URL with the desired request body {@link Biography}.
     * The URL and the request body are given as arguments.
     * The request body is a {@link JSONObject} representation of the actual {@link Biography}.
     *
     * @param uri              the URL to be requested
     * @param requestBiography the biography to be send as {@link org.springframework.web.bind.annotation.RequestBody}
     * @return {@link byte[]} representation of a file.
     */
    private byte[] getResponseFromEuropassApi(String uri, Biography requestBiography) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(uri, asJsonObject(requestBiography), byte[].class);
    }

    /**
     * Writes the {@link byte[]}, given as argument, to a file.
     * The file's name and extension are also given as arguments.
     *
     * @param input         the source to be written
     * @param fileName      the name of the file
     * @param fileExtension the extension of the file
     */
    private void writeBiographyToFile(byte[] input, String fileName, String fileExtension) {
        try (FileOutputStream outputStream = new FileOutputStream(fileName + "." + fileExtension)) {
            outputStream.write(input);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Creates a {@link String}, composed of {@link Biography}s first and last name, and also the id of the biography(for uniqueness).
     * The return value of this method will later be used for file naming.
     *
     * @param biography biography for which the file name will be created.
     */
    private String createFileName(Biography biography) {
        return biography.getIdentification().getPersonName().toString() +
                biography.getId();
    }
}
