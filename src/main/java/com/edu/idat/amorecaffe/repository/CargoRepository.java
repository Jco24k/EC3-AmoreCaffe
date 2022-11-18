/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edu.idat.amorecaffe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.idat.amorecaffe.entity.CargoEntity;

/**
 * 
 * @author 51934
 */
public interface CargoRepository extends JpaRepository<CargoEntity, String> {
    CargoEntity findBySlug(String slug);
    List<CargoEntity> findByNombreOrSlug(String nombre, String slug);

}
