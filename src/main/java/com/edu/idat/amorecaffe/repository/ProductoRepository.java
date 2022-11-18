/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edu.idat.amorecaffe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.idat.amorecaffe.entity.CategoriaEntity;
import com.edu.idat.amorecaffe.entity.ProductoEntity;

/**
 *
 * @author 51934
 */
public interface ProductoRepository extends JpaRepository<ProductoEntity, String> {
    List<ProductoEntity> findByCategoria(CategoriaEntity categoria);
    List<ProductoEntity> findByNombreOrSlug(String nombre, String slug);
    ProductoEntity findBySlug(String slug);

    
}
