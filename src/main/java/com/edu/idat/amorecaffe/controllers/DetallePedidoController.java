package com.edu.idat.amorecaffe.controllers;


import com.edu.idat.amorecaffe.entity.DetallePedidoEntity;
import com.edu.idat.amorecaffe.services.pedidodetalle.DetallePedidoService;
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
@RequestMapping("/detpedido")
public class DetallePedidoController {
 
    @Autowired 
    private DetallePedidoService DetallePedidoService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DetallePedidoEntity> findAll() {
        return DetallePedidoService.findAll();
    }

    @PostMapping("/create")
    public ResponseEntity<?> add(@Valid @RequestBody DetallePedidoEntity DetallePedidoEntity) throws ClassNotFoundException{
        return new ResponseEntity<>(DetallePedidoService.create(DetallePedidoEntity), HttpStatus.CREATED);

    }
    @PatchMapping("/update/{cabventa}/{pro}")
    public ResponseEntity<?> patch(@PathVariable String cabventa,@PathVariable String pro,@Valid @RequestBody DetallePedidoEntity DetallePedidoDto) throws ClassNotFoundException {
        return new ResponseEntity<>(DetallePedidoService.update(DetallePedidoDto,cabventa,pro), HttpStatus.OK);

    }

    @GetMapping("/search/{cabventa}/{pro}")
    public ResponseEntity<?> search(@PathVariable String cabventa,@PathVariable String pro ) throws ClassNotFoundException {
        return new ResponseEntity<>(DetallePedidoService.findOne(cabventa,pro), HttpStatus.OK);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) throws ClassNotFoundException,IllegalArgumentException, Exception {
        DetallePedidoService.delete(id);
        return httpStatus(HttpStatus.BAD_REQUEST, String.format("DetallePedido with id '%s' deleted successfully",id));
    } 
     

    private ResponseEntity<?> httpStatus(HttpStatus http, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        return new ResponseEntity<>(response, http);
    }
    
    
}
