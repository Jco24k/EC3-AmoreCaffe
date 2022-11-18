/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edu.idat.amorecaffe.services.pedidodetalle;

import java.util.List;
import java.util.Map;

import com.edu.idat.amorecaffe.entity.DetallePedidoEntity;

/**
 *
 * @author 51934
 */
public interface DetallePedidoService {

    public List<DetallePedidoEntity> findAll();

    public DetallePedidoEntity findOne(String cabventa,String pro) throws ClassNotFoundException;

    public DetallePedidoEntity create(DetallePedidoEntity detallePedidoEntity) throws ClassNotFoundException;

    public DetallePedidoEntity update(DetallePedidoEntity DetallePedidoEntityDto,String cabventa,String pro) throws ClassNotFoundException ;

    public void delete(String id) throws ClassNotFoundException, IllegalArgumentException, Exception;
}
