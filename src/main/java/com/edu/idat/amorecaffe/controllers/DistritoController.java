package com.edu.idat.amorecaffe.controllers;


import com.edu.idat.amorecaffe.entity.DistritoEntity;
import com.edu.idat.amorecaffe.services.distrito.DistritoService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author 51934
 */
@CrossOrigin
@RestController
@RequestMapping("/distrito")
public class DistritoController {

    @Autowired
    private DistritoService distritoService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DistritoEntity> findAll() {
        return distritoService.findAll();
    }

    @PostMapping("/create")
    public ResponseEntity<?> add(@Valid @RequestBody DistritoEntity distritoDto) {
        return new ResponseEntity<>(distritoService.create(distritoDto), HttpStatus.CREATED);

    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> patch(@PathVariable String id, @RequestBody Map<Object, Object> distritoDto) throws ClassNotFoundException {
        return new ResponseEntity<>(distritoService.update(distritoDto,id), HttpStatus.OK);

    }

    @GetMapping("/search/{search}")
    public ResponseEntity<?> search(@PathVariable String search) throws ClassNotFoundException {
        return new ResponseEntity<>(distritoService.findOne(search), HttpStatus.OK);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) throws ClassNotFoundException,IllegalArgumentException,Exception {
        distritoService.delete(id);
        return httpStatus(HttpStatus.BAD_REQUEST, String.format("Distrito with id '%s' deleted successfully",id));
    }
     

    private ResponseEntity<?> httpStatus(HttpStatus http, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        return new ResponseEntity<>(response, http);
    }
    
    
}
