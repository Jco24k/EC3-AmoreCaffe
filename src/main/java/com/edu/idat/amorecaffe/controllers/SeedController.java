package com.edu.idat.amorecaffe.controllers;

import com.edu.idat.amorecaffe.entity.CabeceraPedidoEntity;
import com.edu.idat.amorecaffe.services.CabeceraPedidoService;
import com.edu.idat.amorecaffe.services.SeedService;
import java.util.Arrays;

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
@RequestMapping("/seed")
public class SeedController {

    @Autowired
    private SeedService seedService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> seedExecute() {
        seedService.seedExecute();
        return httpStatus(HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<?> httpStatus(HttpStatus http) {
        Map<String, Object> response = new HashMap<>();
        for (String opt : Arrays.asList("categoria", "ditrito", "cargo",
                "cliente,empleado", "producto",
                "cabecera_pedido", "detalle_pedido", "comprobante")) {
            response.put(opt, "3 inserted data");
        }
        return new ResponseEntity<>(response, http);
    }
}
