/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edu.idat.amorecaffe.services;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edu.idat.amorecaffe.entity.ComprobanteEntity;
import com.edu.idat.amorecaffe.repository.CabeceraPedidoRepository;
import com.edu.idat.amorecaffe.repository.ComprobanteRepository;

/**
 *
 * @author 51934
 */
@Service
public class ComprobanteServiceImp implements ComprobanteService {

    Pattern uuidValidate = Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");

    @Autowired
    private ComprobanteRepository ComprobanteRepository;

    @Autowired
    private CabeceraPedidoRepository cabPedidoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ComprobanteEntity> findAll() {
        return ComprobanteRepository.findAll();
    }

    @Override
    public ComprobanteEntity findOne(String search) throws ClassNotFoundException {
        ComprobanteEntity Comprobante;

        if (uuidValidate.matcher(search).matches()) {
            Comprobante = ComprobanteRepository.findById(search).orElse(null);
        } else {
            Comprobante = ComprobanteRepository.findByNroComprobante(search);
        }
        if (Comprobante == null) {
            throw new ClassNotFoundException(
                    String.format("Comprobante with id or slug '%s' not found", search));
        }
        return Comprobante;
    }

    @Override
    public ComprobanteEntity create(ComprobanteEntity comprobante) throws ClassNotFoundException {
        String codVenta = comprobante.getVenta().getId();
        cabPedidoRepository.findById(codVenta).orElseThrow(() -> new ClassNotFoundException(
                String.format("CabeceraPedido with id '%s' not found", codVenta)));
        verificar(comprobante.getNroComprobante());
        return ComprobanteRepository.save(comprobante);
    }
 
    @Override
    public void delete(String id) throws ClassNotFoundException, IllegalArgumentException,Exception {
        if (!uuidValidate.matcher(id).matches()) {
            throw new IllegalArgumentException(String.format("id '%s' must be a uuid", id));
        }
        ComprobanteEntity comprobante = this.findOne(id);
        comprobante.setEstado(false);
        ComprobanteRepository.save(comprobante);
    }

    @Override
    public ComprobanteEntity update(ComprobanteEntity comprobanteEntityDto,String id) throws ClassNotFoundException{
        if (!uuidValidate.matcher(id).matches()) {
            throw new IllegalArgumentException(String.format("id '%s' must be a uuid", id));
        }
        ComprobanteEntity comprob = this.findOne(id);
        BeanUtils.copyProperties(comprobanteEntityDto, comprob);
        comprob.setId(id);
        return ComprobanteRepository.save(comprob);
    }

    private void verificar(String nro_comprobante){
        ComprobanteEntity compValidate = ComprobanteRepository.findByNroComprobante(nro_comprobante);
        if (compValidate != null) {
            throw new IllegalArgumentException(
                    String.format("Comprobante with nro_comprobante '%s' is exists in db",nro_comprobante));
        }
    }

}
