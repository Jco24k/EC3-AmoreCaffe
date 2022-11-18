/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edu.idat.amorecaffe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.idat.amorecaffe.entity.CabeceraPedidoEntity;
import com.edu.idat.amorecaffe.entity.ComprobanteEntity;

/**
 *
 * @author 51934
 */
public interface ComprobanteRepository extends JpaRepository<ComprobanteEntity, String> {
    ComprobanteEntity findByNroComprobante(String comprobante);
    ComprobanteEntity findByVenta(CabeceraPedidoEntity venta);

    
}
