/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edu.idat.amorecaffe.services.producto;

import java.util.List;
import java.util.Map;

import com.edu.idat.amorecaffe.entity.ProductoEntity;

/**
 *
 * @author 51934
 */
public interface ProductoService {

    public List<ProductoEntity> findAll();

    public ProductoEntity findOne(String search)throws ClassNotFoundException;

    public ProductoEntity create(ProductoEntity productoEntity) throws ClassNotFoundException;

    public ProductoEntity update(Map<Object, Object> ProductoEntityDto, String id) throws ClassNotFoundException;

    public void delete(String id)throws ClassNotFoundException, IllegalArgumentException, Exception;
}
