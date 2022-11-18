/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edu.idat.amorecaffe.services.pedidodetalle;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.edu.idat.amorecaffe.entity.CabeceraPedidoEntity;
import com.edu.idat.amorecaffe.entity.DetallePedidoEntity;
import com.edu.idat.amorecaffe.entity.DetallePedidoId;
import com.edu.idat.amorecaffe.entity.ProductoEntity;
import com.edu.idat.amorecaffe.repository.CabeceraPedidoRepository;
import com.edu.idat.amorecaffe.repository.DetallePedidoRepository;
import com.edu.idat.amorecaffe.repository.ProductoRepository;

/**
 *
 * @author 51934
 */
@Service
public class DetallePedidoServiceImp implements DetallePedidoService {

    Pattern uuidValidate = Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");

    @Autowired
    private DetallePedidoRepository DetallePedidoRepository;
    @Autowired
    private CabeceraPedidoRepository cabPedidoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<DetallePedidoEntity> findAll() {
        return DetallePedidoRepository.findAll();
    }

    @Override
    public DetallePedidoEntity findOne(String cabventa, String pro) throws ClassNotFoundException {
        DetallePedidoEntity detallePedido = null;
        detallePedido = DetallePedidoRepository.findById(new DetallePedidoId(cabventa, pro)).orElse(null);

        if (detallePedido == null) {
            throw new ClassNotFoundException(
                    String.format("DetallePedido with id ['%s','%s'] not found", cabventa, pro));
        }
        return detallePedido;
    }

    @Override
    public DetallePedidoEntity create(DetallePedidoEntity detallePedido) throws ClassNotFoundException {
        String codProducto = detallePedido.getProducto().getId(), codVenta = detallePedido.getCabventa().getId();
        ProductoEntity pro = productoRepository.findById(codProducto).orElseThrow(() -> new ClassNotFoundException(
                String.format("Producto with id '%s' not found", codProducto)));
        CabeceraPedidoEntity cabVenta = cabPedidoRepository.findById(codVenta)
                .orElseThrow(() -> new ClassNotFoundException(
                        String.format("CabeceraPedido with id '%s' not found", codVenta)));

        DetallePedidoId detId = new DetallePedidoId(cabVenta.getId(), pro.getId());

        detallePedido.setId(detId);
        detallePedido.setPrecio_producto(pro.getPrecio());
        detallePedido.setSubtotal(detallePedido.getPrecio_producto() * Double.valueOf(detallePedido.getCantidad()));
        return DetallePedidoRepository.save(detallePedido);
    }

    @Override
    public void delete(String id) throws ClassNotFoundException, IllegalArgumentException, Exception {
        if (!uuidValidate.matcher(id).matches()) {
            throw new IllegalArgumentException(String.format("id '%s' must be a uuid",
                    id));
        }
        List<DetallePedidoEntity> DetallePedido = DetallePedidoRepository.SelectDetVenta(id);
        if (DetallePedido.isEmpty()) {
            throw new ClassNotFoundException(
                    String.format("DetallePedido with id '%s' not found", id));
        }
        DetallePedidoRepository.DeleteDetVenta(id);
        return; 
    }

    @Override
    public DetallePedidoEntity update(DetallePedidoEntity DetallePedidoEntityDto, String cabventa, String pro)
            throws ClassNotFoundException {
        if (!uuidValidate.matcher(cabventa).matches() || !uuidValidate.matcher(pro).matches()) {
            throw new IllegalArgumentException(String.format("id ['%s','%s'] must be a uuid", cabventa, pro));
        }
        String codProducto = DetallePedidoEntityDto.getProducto().getId(), codVenta = DetallePedidoEntityDto.getCabventa().getId();
        ProductoEntity proFind= productoRepository.findById(pro).orElseThrow(() -> new ClassNotFoundException(
                String.format("Producto with id '%s' not found", pro)));
        cabPedidoRepository.findById(cabventa)
                .orElseThrow(() -> new ClassNotFoundException(
                        String.format("CabeceraPedido with id '%s' not found", cabventa)));

        double precio = proFind.getPrecio();
        int cantidad = DetallePedidoEntityDto.getCantidad();
        double total =  precio * Double.valueOf(cantidad); 
        DetallePedidoRepository.UpdateDetalleVenta(codVenta, codProducto,
        cantidad, precio, total);
        return this.findOne(cabventa, codProducto);
    }

    // private void verificar(String dni){
    // DetallePedidoEntity DetallePedidoNotValidate =
    // DetallePedidoRepository.findByDni(dni);
    // if (DetallePedidoNotValidate != null) {
    // throw new IllegalArgumentException(
    // String.format("DetallePedido with dni '%s' is exists in db", dni));
    // }
    // }
}
