/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edu.idat.amorecaffe.services.cargo;

// import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
// import java.util.Map;
import java.util.regex.Pattern;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;
import com.edu.idat.amorecaffe.entity.CargoEntity;
import com.edu.idat.amorecaffe.entity.EmpleadoEntity;
import com.edu.idat.amorecaffe.repository.CargoRepository;
import com.edu.idat.amorecaffe.repository.EmpleadoRepository;

/**
 *
 * @author 51934
 */
@Service
public class CargoServiceImp implements CargoService {

    Pattern uuidValidate = Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CargoEntity> findAll() {
        return cargoRepository.findAll();
    }

    @Override
    public CargoEntity findOne(String search) throws ClassNotFoundException {
        CargoEntity cargo;

        if (uuidValidate.matcher(search).matches()) {
            cargo = cargoRepository.findById(search).orElse(null);
        } else {
            cargo = cargoRepository.findBySlug(search);
        }
        if (cargo == null) {
            throw new ClassNotFoundException(
                    String.format("Cargo with id or slug '%s' not found", search));
        }
        return cargo;
    }

    @Override
    public CargoEntity create(CargoEntity cargo) {
        String nombre = cargo.getNombre(), slug = cargo.getSlug();
        verificar(nombre, slug,null);
        return cargoRepository.save(cargo);
    }

    @Override
    public void delete(String id)  throws ClassNotFoundException,IllegalArgumentException,Exception {
        if (!uuidValidate.matcher(id).matches()) {
            throw new IllegalArgumentException(String.format("id '%s' must be a uuid", id));
        }
        CargoEntity cargo = this.findOne(id);
        
        List<EmpleadoEntity> ListEmpleado = empleadoRepository.findByCargo(cargo);
        if(!ListEmpleado.isEmpty())
            throw new Exception(String.format("Cargo with id '%s' cannot be deleted because it is in use.", id ));
        cargoRepository.delete(cargo);
        return;
    }

    @Override
    public CargoEntity update(Map<Object, Object> cargoEntityDto,String id) throws ClassNotFoundException {
        if (!uuidValidate.matcher(id).matches()) {
            throw new IllegalArgumentException(String.format("id '%s' must be a uuid", id));
        }
        CargoEntity cargo = this.findOne(id);
        cargoEntityDto.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(CargoEntity.class, (String) key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, cargo, value);
        });

        return cargoRepository.save(cargo);
    } 

    private void verificar(String nombre, String slug , String id){
        List<CargoEntity> cargosNotValid = cargoRepository.findByNombreOrSlug(nombre, slug);
        if (!cargosNotValid.isEmpty()) {
            throw new IllegalArgumentException(
                    String.format("Cargo with nombre or slug ['%s','%s'] is exists in db", nombre,  slug == null ? nombre : slug));
        }
    }


}
