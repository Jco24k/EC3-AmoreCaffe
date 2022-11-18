/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edu.idat.amorecaffe.services.distrito;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import com.edu.idat.amorecaffe.entity.ClienteEntity;
import com.edu.idat.amorecaffe.entity.DistritoEntity;
import com.edu.idat.amorecaffe.entity.EmpleadoEntity;
import com.edu.idat.amorecaffe.repository.ClienteRepository;
import com.edu.idat.amorecaffe.repository.DistritoRepository;
import com.edu.idat.amorecaffe.repository.EmpleadoRepository;

/**
 *
 * @author 51934
 */
@Service
public class DistritoServiceImp implements DistritoService {

    Pattern uuidValidate = Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");

    @Autowired
    private DistritoRepository DistritoRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    @Transactional(readOnly = true)
    public List<DistritoEntity> findAll() {
        return DistritoRepository.findAll();
    }

    @Override
    public DistritoEntity findOne(String search) throws ClassNotFoundException {
        DistritoEntity Distrito;

        if (uuidValidate.matcher(search).matches()) {
            Distrito = DistritoRepository.findById(search).orElse(null);
        } else {
            Distrito = DistritoRepository.findBySlug(search);
        }
        if (Distrito == null) {
            throw new ClassNotFoundException(
                    String.format("Distrito with id or slug '%s' not found", search));
        }
        return Distrito;
    }

    @Override
    public DistritoEntity create(DistritoEntity distrito) {
        String nombre = distrito.getNombre(), slug = distrito.getSlug();
        verificar(nombre, slug);
        return DistritoRepository.save(distrito);
    }

    @Override
    public void delete(String id) throws ClassNotFoundException, IllegalArgumentException ,Exception{
        if (!uuidValidate.matcher(id).matches()) {
            throw new IllegalArgumentException(String.format("id '%s' must be a uuid", id));
        }
        DistritoEntity distrito = this.findOne(id);
        List<EmpleadoEntity> ListEmpleado = empleadoRepository.findByDistrito(distrito);
        List<ClienteEntity> ListCliente = clienteRepository.findByDistrito(distrito);

        if(!ListEmpleado.isEmpty() || !ListCliente.isEmpty())
            throw new Exception(String.format("Distrito with id '%s' cannot be deleted because it is in use.", id ));
        
            DistritoRepository.delete(distrito);
        return;
    } 

    @Override
    public DistritoEntity update(Map<Object, Object> distritoEntityDto,String id) throws ClassNotFoundException{
        if (!uuidValidate.matcher(id).matches()) {
            throw new IllegalArgumentException(String.format("id '%s' must be a uuid", id));
        }
        DistritoEntity distritoEntity = this.findOne(id);
        distritoEntityDto.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(DistritoEntity.class, (String) key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, distritoEntity, value);
        });
        // verificar(distritoEntity.getNombre(), distritoEntity.getSlug());
        return DistritoRepository.save(distritoEntity);
    }


    public void verificar(String nombre,String  slug){
        List<DistritoEntity> DistritoNotValidate = DistritoRepository.findByNombreOrSlug(nombre, slug);
        if (!DistritoNotValidate.isEmpty()) {
            throw new IllegalArgumentException(
                    String.format("Distrito with nombre or slug ['%s','%s'] is exists in db", nombre, slug));
        }
    }


}
