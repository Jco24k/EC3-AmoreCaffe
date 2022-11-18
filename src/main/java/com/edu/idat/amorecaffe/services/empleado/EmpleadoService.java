/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edu.idat.amorecaffe.services.empleado;

import java.util.List;
import java.util.Map;

import com.edu.idat.amorecaffe.entity.EmpleadoEntity;

/**
 *
 * @author 51934
 */
public interface EmpleadoService {

    public List<EmpleadoEntity> findAll();

    public EmpleadoEntity findOne(String search)throws ClassNotFoundException;

    public EmpleadoEntity create(EmpleadoEntity EmpleadoEntity) throws ClassNotFoundException;

    public EmpleadoEntity update(EmpleadoEntity EmpleadoEntityDto, String id) throws ClassNotFoundException;

    public void delete(String id)throws ClassNotFoundException, IllegalArgumentException, Exception;
}
