/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edu.idat.amorecaffe.services.empleado;

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
import com.edu.idat.amorecaffe.entity.EmpleadoEntity;
import com.edu.idat.amorecaffe.repository.CabeceraPedidoRepository;
import com.edu.idat.amorecaffe.repository.CargoRepository;
import com.edu.idat.amorecaffe.repository.DistritoRepository;
import com.edu.idat.amorecaffe.repository.EmpleadoRepository;

/**
 *
 * @author 51934
 */
@Service
public class EmpleadoServiceImp implements EmpleadoService {

    Pattern uuidValidate = Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");

    @Autowired
    private EmpleadoRepository empleadoRepository;
    @Autowired
    private DistritoRepository distritoRepository;
    @Autowired
    private CargoRepository cargoRepository;
    @Autowired
    private CabeceraPedidoRepository cabPedidoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<EmpleadoEntity> findAll() {
        return empleadoRepository.findAll();
    }

    @Override
    public EmpleadoEntity findOne(String search) throws ClassNotFoundException {
        EmpleadoEntity Empleado;
        if (uuidValidate.matcher(search).matches()) {
            Empleado = empleadoRepository.findById(search).orElse(null);
        } else {
            Empleado = empleadoRepository.findByDni(search);
        }
        if (Empleado == null) {
            throw new ClassNotFoundException(
                    String.format("Empleado with id or slug '%s' not found", search));
        }
        return Empleado;
    }

    @Override
    public EmpleadoEntity create(EmpleadoEntity empleado) throws ClassNotFoundException {
        String codDistrito = empleado.getDistrito().getId(), codCargo = empleado.getCargo().getId();

        distritoRepository.findById(codDistrito).orElseThrow(() -> new ClassNotFoundException(
                String.format("Distrito with id '%s' not found", codDistrito)));
        cargoRepository.findById(codCargo).orElseThrow(() -> new ClassNotFoundException(
                String.format("Cargo with id '%s' not found", codCargo)));
        verificar(empleado.getDni());
        return empleadoRepository.save(empleado);
    }

    @Override
    public void delete(String id) throws ClassNotFoundException, IllegalArgumentException, Exception {
        if (!uuidValidate.matcher(id).matches()) {
            throw new IllegalArgumentException(String.format("id '%s' must be a uuid", id));
        }
        EmpleadoEntity Empleado = this.findOne(id);
        List<CabeceraPedidoEntity> ListCabVenta = cabPedidoRepository.findByEmpleado(Empleado);
        if (!ListCabVenta.isEmpty())
            throw new Exception(String.format("Empleado with id '%s' cannot be deleted because it is in use.", id));

        empleadoRepository.delete(Empleado);
        return;
    }

    @Override
    public EmpleadoEntity update(EmpleadoEntity EmpleadoEntityDto, String id) throws ClassNotFoundException {
        if (!uuidValidate.matcher(id).matches()) {
            throw new IllegalArgumentException(String.format("id '%s' must be a uuid", id));
        }
        EmpleadoEntity emp = this.findOne(id);
        BeanUtils.copyProperties(EmpleadoEntityDto, emp);

        // verificar(emp.getDni());
        return empleadoRepository.save(emp);
    }

    private void verificar(String dni) {
        EmpleadoEntity empleadoNotValid = empleadoRepository.findByDni(dni);
        if (empleadoNotValid != null) {
            throw new IllegalArgumentException(
                    String.format("Empleado with dni '%s' is exists in db", dni));
        }
    }
}
