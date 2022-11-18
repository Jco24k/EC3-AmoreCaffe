/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edu.idat.amorecaffe.services.producto;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.edu.idat.amorecaffe.entity.CategoriaEntity;
import com.edu.idat.amorecaffe.entity.DetallePedidoEntity;
import com.edu.idat.amorecaffe.entity.ProductoEntity;
import com.edu.idat.amorecaffe.repository.DetallePedidoRepository;
import com.edu.idat.amorecaffe.repository.ProductoRepository;
import com.edu.idat.amorecaffe.repository.CategoriaRepository;

/**
 *
 * @author 51934
 */
@Service
public class ProductoServiceImp implements ProductoService {

    Pattern uuidValidate = Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");

    @Autowired
    private ProductoRepository ProductoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private DetallePedidoRepository detPedidoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProductoEntity> findAll() {
        return ProductoRepository.findAll();
    }

    @Override
    public ProductoEntity findOne(String search) throws ClassNotFoundException {
        ProductoEntity producto;
        if (uuidValidate.matcher(search).matches()) {
            producto = ProductoRepository.findById(search).orElse(null);
        } else {
            producto = ProductoRepository.findBySlug(search);
        }
        if (producto == null) {
            throw new ClassNotFoundException(
                    String.format("Producto with id or slug '%s' not found", search));
        }
        return producto;
    }

    @Override
    public ProductoEntity create(ProductoEntity producto) throws ClassNotFoundException {

        String codCategoria = producto.getCategoria().getId();
        CategoriaEntity cat = categoriaRepository.findById(codCategoria).orElseThrow(() -> new ClassNotFoundException(
                String.format("Categoria with id '%s' not found", codCategoria)));
        verificar(producto.getNombre(), producto.getSlug());
        return ProductoRepository.save(producto);
    }

    @Override
    public void delete(String id) throws ClassNotFoundException, IllegalArgumentException, Exception {
        if (!uuidValidate.matcher(id).matches()) {
            throw new IllegalArgumentException(String.format("id '%s' must be a uuid", id));
        }
        ProductoEntity Producto = this.findOne(id);
        List<DetallePedidoEntity> ListCabVenta = detPedidoRepository.findByProducto(Producto);
        if (!ListCabVenta.isEmpty())
            throw new Exception(String.format("Producto with id '%s' cannot be deleted because it is in use.", id));

        ProductoRepository.delete(Producto);
        return;
    }

    @Override
    public ProductoEntity update(Map<Object, Object> ProductoEntityDto, String id) throws ClassNotFoundException {
        if (!uuidValidate.matcher(id).matches()) {
            throw new IllegalArgumentException(String.format("id '%s' must be a uuid", id));
        }
        ProductoEntity prod = this.findOne(id);
        BeanUtils.copyProperties(ProductoEntityDto, prod);

        // verificar(prod.getNombre(), prod.getSlug());
        return ProductoRepository.save(prod);
    }

    private void verificar(String nombre, String slug) {
        List<ProductoEntity> productoNotValid = ProductoRepository.findByNombreOrSlug(nombre, slug);
        if (!productoNotValid.isEmpty()) {
            throw new IllegalArgumentException(
                    String.format("Producto with nombre or slug ['%s','%s'] is exists in db", nombre, slug));
        }
    }

}
