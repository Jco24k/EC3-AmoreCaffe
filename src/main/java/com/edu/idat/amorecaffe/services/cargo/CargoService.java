/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edu.idat.amorecaffe.services.cargo;

import java.util.List;
import java.util.Map;

import com.edu.idat.amorecaffe.entity.CargoEntity;

/**
 *
 * @author 51934
 */
public interface CargoService {

    public List<CargoEntity> findAll();

    public CargoEntity findOne(String search) throws ClassNotFoundException;

    public CargoEntity create(CargoEntity cargoEntity);

    public CargoEntity update(Map<Object, Object> cargoEntity,String id)throws ClassNotFoundException;

    public void delete(String id) throws ClassNotFoundException, IllegalArgumentException,Exception;
}
