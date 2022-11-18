/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edu.idat.amorecaffe.services.cliente;

import java.util.List;
import java.util.Map;

import com.edu.idat.amorecaffe.entity.ClienteEntity;

/**
 *
 * @author 51934
 */
public interface ClienteService {

    public List<ClienteEntity> findAll();

    public ClienteEntity findOne(String search)throws ClassNotFoundException;

    public ClienteEntity create(ClienteEntity clienteEntity) throws ClassNotFoundException;

    public ClienteEntity update( ClienteEntity clienteEntity,String id)throws ClassNotFoundException;

    public void delete(String id)throws ClassNotFoundException, IllegalArgumentException ,Exception;
}
