/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edu.idat.amorecaffe.services.categoria;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.edu.idat.amorecaffe.entity.CategoriaEntity;
import com.edu.idat.amorecaffe.entity.ProductoEntity;
import com.edu.idat.amorecaffe.repository.CategoriaRepository;
import com.edu.idat.amorecaffe.repository.ProductoRepository;

/**
 *
 * @author 51934
 */
@Service
public class CategoriaServiceImp implements CategoriaService {

    Pattern uuidValidate = Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");

    @Autowired
    private CategoriaRepository CategoriaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaEntity> findAll() {
        return CategoriaRepository.findAll();
    }

    @Override
    public CategoriaEntity findOne(String search) throws ClassNotFoundException {
        CategoriaEntity Categoria;

        if (uuidValidate.matcher(search).matches()) {
            Categoria = CategoriaRepository.findById(search).orElse(null);
        } else {
            Categoria = CategoriaRepository.findBySlug(search);
        }
        if (Categoria == null) {
            throw new ClassNotFoundException(
                    String.format("Categoria with id or slug '%s' not found", search));
        }
        return Categoria;
    }

    @Override
    public CategoriaEntity create(CategoriaEntity categoria) {
        verificar(categoria.getNombre(), categoria.getSlug());
        return CategoriaRepository.save(categoria);
    }

    @Override
    public void delete(String id) throws ClassNotFoundException, IllegalArgumentException,Exception {
        if (!uuidValidate.matcher(id).matches()) throw new IllegalArgumentException(String.format("id '%s' must be a uuid", id));
        
        CategoriaEntity categoria = this.findOne(id);
        List<ProductoEntity> ListProducto = productoRepository.findByCategoria(categoria);
        if(!ListProducto.isEmpty())
            throw new Exception(String.format("Categoria with id '%s' cannot be deleted because it is in use.", id ));
        CategoriaRepository.delete(categoria);
        return;
    }

    @Override
    public CategoriaEntity update( Map<Object, Object>  categoriaEntityDto,String id) throws ClassNotFoundException{
        if (!uuidValidate.matcher(id).matches()) {
            throw new IllegalArgumentException(String.format("id '%s' must be a uuid", id));
        }
        CategoriaEntity cat = this.findOne(id);
        categoriaEntityDto.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(CategoriaEntity.class, (String) key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, cat, value);
        });
        // verificar(cat.getNombre(), cat.getSlug());
        return CategoriaRepository.save(cat);
    }

    private void verificar(String nombre,String slug){
        List<CategoriaEntity> categoryValidate = CategoriaRepository.findByNombreOrSlug(nombre, slug);
        if (!categoryValidate.isEmpty()) {
            throw new IllegalArgumentException(
                    String.format("Categoria with nombre or slug ['%s','%s'] is exists in db", nombre,
                            slug == null ? nombre : slug));
        }
    }

}
