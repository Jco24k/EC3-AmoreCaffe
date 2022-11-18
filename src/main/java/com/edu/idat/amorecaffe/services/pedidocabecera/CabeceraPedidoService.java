/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edu.idat.amorecaffe.services.pedidocabecera;

import java.util.List;
import java.util.Map;

import com.edu.idat.amorecaffe.entity.CabeceraPedidoEntity;

/**
 *
 * @author 51934
 */
public interface CabeceraPedidoService {

    public List<CabeceraPedidoEntity> findAll();

    public CabeceraPedidoEntity findOne(String search) throws ClassNotFoundException;

    public CabeceraPedidoEntity create(CabeceraPedidoEntity cabeceraPedidoEntity) throws ClassNotFoundException;

    public CabeceraPedidoEntity update(CabeceraPedidoEntity cabeceraPedidoEntity,String id)throws ClassNotFoundException;

    public void delete(String id)throws ClassNotFoundException, IllegalArgumentException, Exception;
    public void delete2(String id)throws ClassNotFoundException, IllegalArgumentException, Exception;

}
