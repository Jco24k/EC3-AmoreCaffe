/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edu.idat.amorecaffe.services.cliente;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.edu.idat.amorecaffe.entity.CabeceraPedidoEntity;
import com.edu.idat.amorecaffe.entity.ClienteEntity;
import com.edu.idat.amorecaffe.entity.DistritoEntity;
import com.edu.idat.amorecaffe.repository.CabeceraPedidoRepository;
import com.edu.idat.amorecaffe.repository.ClienteRepository;
import com.edu.idat.amorecaffe.repository.DistritoRepository;

/**
 *
 * @author 51934
 */
@Service
public class ClienteServiceImp implements ClienteService {

    Pattern uuidValidate = Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");

    @Autowired
    private ClienteRepository ClienteRepository;

    @Autowired
    private DistritoRepository distritoRepository;

    @Autowired
    private CabeceraPedidoRepository cabPedidoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ClienteEntity> findAll() {
        return ClienteRepository.findAll();
    }

    @Override
    public ClienteEntity findOne(String search) throws ClassNotFoundException {
        ClienteEntity Cliente;

        if (uuidValidate.matcher(search).matches()) {
            Cliente = ClienteRepository.findById(search).orElse(null);
        } else {
            Cliente = ClienteRepository.findByDni(search);
        }
        if (Cliente == null) {
            throw new ClassNotFoundException(
                    String.format("Cliente with id or slug '%s' not found", search));
        }
        return Cliente;
    }

    @Override
    public ClienteEntity create(ClienteEntity Cliente) throws ClassNotFoundException {
        String codCategoria = Cliente.getDistrito().getId();
        distritoRepository.findById(codCategoria).orElseThrow(() -> new ClassNotFoundException(
                String.format("Distrito with id '%s' not found", codCategoria)));
        verificar(Cliente.getDni());
        return ClienteRepository.save(Cliente);
    }

    @Override
    public void delete(String id) throws ClassNotFoundException, IllegalArgumentException, Exception {
        if (!uuidValidate.matcher(id).matches()) {
            throw new IllegalArgumentException(String.format("id '%s' must be a uuid", id));
        }
        ClienteEntity Cliente = this.findOne(id);
        List<CabeceraPedidoEntity> ListCabVenta = cabPedidoRepository.findByCliente(Cliente);

        if (!ListCabVenta.isEmpty())
            throw new Exception(String.format("Cliente with id '%s' cannot be deleted because it is in use.", id));

        ClienteRepository.delete(Cliente);
        return;
    }

    @Override
    public ClienteEntity update(ClienteEntity ClienteEntityDto, String id) throws ClassNotFoundException {
        if (!uuidValidate.matcher(id).matches()) {
            throw new IllegalArgumentException(String.format("id '%s' must be a uuid", id));
        }
        ClienteEntity cli = this.findOne(id);
        BeanUtils.copyProperties(ClienteEntityDto, cli);
        return ClienteRepository.save(cli);
    }

    private void verificar(String dni) {
        ClienteEntity ClienteNotValidate = ClienteRepository.findByDni(dni);
        if (ClienteNotValidate != null) {
            throw new IllegalArgumentException(
                    String.format("Cliente with dni '%s' is exists in db", dni));
        }
    }
}
