package com.scalefocus.cvmanager.controller;

import com.scalefocus.cvmanager.exception.BiographyNotFoundException;
import com.scalefocus.cvmanager.exception.WrongFormatException;
import com.scalefocus.cvmanager.model.biography.Biography;
import com.scalefocus.cvmanager.service.BiographyService;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Controller that handles CRUD operations through {@link org.springframework.http.HttpMethod}s.
 *
 * @author Mariyan Topalov
 */
@RestController
@RequestMapping("/biographies")
public class BiographyController {

    private final BiographyService service;

    public BiographyController(BiographyService service) {
        this.service = service;
    }

    //GET MAPPINGS

    /**
     * Handles the GET request of "/biographies/{id}" URL. Outputs the {@link Biography}, in {@link JSONObject} format, which matches the id, given as {@link PathVariable}.
     *
     * @param id the id that matches the biography to be outputted.
     * @throws BiographyNotFoundException if a biography with given id does not exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> findById(@PathVariable Long id) throws BiographyNotFoundException {
        Biography biography = service.findById(id);
        return ResponseEntity.ok(service.asJsonObject(biography));
    }

    //DELETE MAPPINGS

    /**
     * Handles the DELETE request of "/biographies/{id}" URL. Deletes the {@link Biography}, which matches the given argument id.
     * The "{id}" is given as {@link PathVariable}.
     *
     * @param id the biography with this id will be deleted from the database.
     * @throws BiographyNotFoundException if a biography with given id does not exist.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) throws BiographyNotFoundException {
        service.deleteById(id);
        return ResponseEntity.ok("Biography with ID " + id + " deleted!");
    }

    //POST MAPPINGS

    /**
     * Handles the POST request of "/biographies/" URL. Saves the {@link Biography}, given as request body argument, in the database.
     *
     * @param biography biography to be saved in the database.
     * @throws IOException if IOException occurs.
     */
    @PostMapping("/")
    public ResponseEntity<Object> save(@RequestBody Biography biography) throws IOException {
        return new ResponseEntity<>(service.save(biography), HttpStatus.CREATED);
    }

    /**
     * Handles the POST request of "/biographies/{id}/{format}".
     * Gets the biography, that matches the id, given as {@link PathVariable}, from the database.
     * The biography then will be send as request body to "Europass REST API", be converted to the given argument format, also given as {@link PathVariable}.
     * The returned response will be written to file. The file format is the same as the argument format.
     *
     * @param id     biography with that id will be proceeded to the "Europass REST API", converted to the desired format, and written to file.
     * @param format the desired format.
     * @throws BiographyNotFoundException if biography with given id does not exist.
     * @throws WrongFormatException       if the given format is not supported.
     * @see BiographyService
     */
    @PostMapping("/{id}/{format}")
    public ResponseEntity<Object> toDesiredFileFormat(@PathVariable Long id, @PathVariable String format) throws BiographyNotFoundException, WrongFormatException {
        service.convertToDesiredFileFormat(id, format);

        return ResponseEntity.ok("Biography successfully converted to " + format + "!");
    }
}
