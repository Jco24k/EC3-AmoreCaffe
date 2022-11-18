/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edu.idat.amorecaffe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.idat.amorecaffe.entity.CargoEntity;
import com.edu.idat.amorecaffe.entity.DistritoEntity;
import com.edu.idat.amorecaffe.entity.EmpleadoEntity;

/**
 *
 * @author 51934
 */
public interface EmpleadoRepository extends JpaRepository<EmpleadoEntity, String> {
    List<EmpleadoEntity> findByDistrito(DistritoEntity distrito);
    List<EmpleadoEntity> findByCargo(CargoEntity cargo);

    EmpleadoEntity findByDni(String dni);

}
