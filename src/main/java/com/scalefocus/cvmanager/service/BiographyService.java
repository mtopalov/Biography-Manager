package com.scalefocus.cvmanager.service;

import com.scalefocus.cvmanager.converter.EuroPassConverter;
import com.scalefocus.cvmanager.exception.BiographyNotFoundException;
import com.scalefocus.cvmanager.model.Biography;
import com.scalefocus.cvmanager.repository.BiographyRepository;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Mariyan Topalov
 */
@Service
public class BiographyService {

    private final BiographyRepository repository;

    private final EuroPassConverter converter;

    public BiographyService(BiographyRepository repository, EuroPassConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    //GET MAPPINGS
    public List<JSONObject> findAll() {
        List<Biography> biographies = repository.findAll();
        return biographies.stream()
                .map(converter::toJsonObject)
                .collect(Collectors.toList());
    }

    public JSONObject findById(Long id) throws BiographyNotFoundException {
        return repository.findById(id)
                .map(converter::toJsonObject)
                .orElseThrow(() -> new BiographyNotFoundException("There's no biography with that id!"));
    }

    //DELETE MAPPINGS
    public void deleteAll() {
        repository.deleteAll();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    //POST MAPPINGS
    public JSONObject save(Biography biography) throws IOException {
        byte[] imageAsByteArray;
        String encodedImage;
        String inputPath = biography.getIdentification().getPhoto().getData();

        try (InputStream inputStream = new URL(inputPath).openStream()) {
            imageAsByteArray = IOUtils.toByteArray(inputStream);
        } catch (MalformedURLException exception) {
            imageAsByteArray = Files.readAllBytes(Paths.get("default-image.jpg"));
        }
        encodedImage = Base64.encode(imageAsByteArray);

        biography.getIdentification().getPhoto().setData(encodedImage);

        repository.save(biography);
        return converter.toJsonObject(biography);
    }


    public void biographyToPdf(Long id) throws BiographyNotFoundException {
        final String uri = "https://europass.cedefop.europa.eu/rest/v1/document/to/pdf-cv";
        RestTemplate restTemplate = new RestTemplate();
        byte[] response = restTemplate.postForObject(uri, findById(id), byte[].class);

        assert response != null;
        try (FileOutputStream outputStream = new FileOutputStream("cv.pdf")) {
            outputStream.write(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void biographyToWord(Long id) throws BiographyNotFoundException {
        final String uri = "https://europass.cedefop.europa.eu/rest/v1/document/to/word";
        RestTemplate restTemplate = new RestTemplate();
        byte[] response = restTemplate.postForObject(uri, findById(id), byte[].class);

        assert response != null;
        try (FileOutputStream outputStream = new FileOutputStream("cv.doc")) {
            outputStream.write(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
