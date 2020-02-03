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
import java.util.List;

/**
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
    @GetMapping("/")
    public ResponseEntity<List<JSONObject>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> findById(@PathVariable Long id) throws BiographyNotFoundException {
        Biography biography = service.findById(id);
        return ResponseEntity.ok(service.asJsonObject(biography));
    }

    //DELETE MAPPINGS
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) throws BiographyNotFoundException {
        service.deleteById(id);
        return ResponseEntity.ok("Biography with ID " + id + " deleted!");
    }

    //POST MAPPINGS
    @PostMapping("/")
    public ResponseEntity<Object> save(@RequestBody Biography biography) throws IOException {
        return new ResponseEntity<>(service.save(biography), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/{format}")
    public ResponseEntity<Object> toDesiredFileFormat(@PathVariable Long id, @PathVariable String format) throws BiographyNotFoundException, WrongFormatException {
        service.toDesiredFileFormat(id, format);

        return ResponseEntity.ok("Biography successfully converted to " + format + "!");
    }

}
