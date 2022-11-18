/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edu.idat.amorecaffe.services.comprobante;

import java.util.List;
import java.util.Map;

import com.edu.idat.amorecaffe.entity.ComprobanteEntity;

/**
 *
 * @author 51934
 */
public interface ComprobanteService {

    public List<ComprobanteEntity> findAll();

    public ComprobanteEntity findOne(String search)throws ClassNotFoundException;

    public ComprobanteEntity create(ComprobanteEntity comprobanteEntity)throws ClassNotFoundException ;

    public ComprobanteEntity update( Map<Object, Object> comprobanteEntity,String id) throws ClassNotFoundException;

    public void delete(String id)throws ClassNotFoundException, IllegalArgumentException,Exception;
}
