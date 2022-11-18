/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edu.idat.amorecaffe.services.distrito;

import java.util.List;
import java.util.Map;

import com.edu.idat.amorecaffe.entity.DistritoEntity;

/**
 *
 * @author 51934
 */
public interface DistritoService {

    public List<DistritoEntity> findAll();

    public DistritoEntity findOne(String search)throws ClassNotFoundException ;

    public DistritoEntity create(DistritoEntity distritoEntity);

    public DistritoEntity update(Map<Object, Object> distritoEntity,String id)throws ClassNotFoundException;

    public void delete(String id)throws ClassNotFoundException, IllegalArgumentException,Exception;
}
