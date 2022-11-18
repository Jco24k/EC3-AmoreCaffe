/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edu.idat.amorecaffe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.idat.amorecaffe.entity.CabeceraPedidoEntity;
import com.edu.idat.amorecaffe.entity.ClienteEntity;
import com.edu.idat.amorecaffe.entity.ComprobanteEntity;
import com.edu.idat.amorecaffe.entity.EmpleadoEntity;

/**
 *
 * @author 51934
 */
public interface CabeceraPedidoRepository extends JpaRepository<CabeceraPedidoEntity, String> {
    
    List<CabeceraPedidoEntity> findByCliente(ClienteEntity clienteEntity);

    List<CabeceraPedidoEntity> findByEmpleado(EmpleadoEntity empleadoEntity);

}
