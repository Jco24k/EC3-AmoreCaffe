/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edu.idat.amorecaffe.services.categoria;

import java.util.List;
import java.util.Map;

import com.edu.idat.amorecaffe.entity.CategoriaEntity;

/**
 *
 * @author 51934
 */
public interface CategoriaService {

    public List<CategoriaEntity> findAll();

    public CategoriaEntity findOne(String search)throws ClassNotFoundException;

    public CategoriaEntity create(CategoriaEntity categoriaEntity);

    public CategoriaEntity update( Map<Object, Object>  categoriaEntity,String id)throws ClassNotFoundException;

    public void delete(String id) throws ClassNotFoundException,IllegalArgumentException,Exception;
}
