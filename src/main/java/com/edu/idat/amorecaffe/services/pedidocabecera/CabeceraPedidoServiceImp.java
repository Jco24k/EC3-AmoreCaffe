/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edu.idat.amorecaffe.services.pedidocabecera;

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
import com.edu.idat.amorecaffe.entity.ComprobanteEntity;
import com.edu.idat.amorecaffe.entity.DetallePedidoEntity;
import com.edu.idat.amorecaffe.entity.EmpleadoEntity;
import com.edu.idat.amorecaffe.entity.CabeceraPedidoEntity;
import com.edu.idat.amorecaffe.repository.CabeceraPedidoRepository;
import com.edu.idat.amorecaffe.repository.ClienteRepository;
import com.edu.idat.amorecaffe.repository.ComprobanteRepository;
import com.edu.idat.amorecaffe.repository.DetallePedidoRepository;
import com.edu.idat.amorecaffe.repository.EmpleadoRepository;
import com.edu.idat.amorecaffe.repository.CabeceraPedidoRepository;

/**
 *
 * @author 51934
 */
@Service
public class CabeceraPedidoServiceImp implements CabeceraPedidoService {

    Pattern uuidValidate = Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");

    @Autowired
    private CabeceraPedidoRepository CabeceraPedidoRepository;
    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ComprobanteRepository comprobanteRepository;

    @Autowired
    private DetallePedidoRepository detPedidoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CabeceraPedidoEntity> findAll() {
        return CabeceraPedidoRepository.findAll();
    }

    @Override
    public CabeceraPedidoEntity findOne(String search) throws ClassNotFoundException {
        CabeceraPedidoEntity cabPedido = null;
        if (uuidValidate.matcher(search).matches()) {
            cabPedido = CabeceraPedidoRepository.findById(search).orElse(null);
        }
        if (cabPedido == null) {
            throw new ClassNotFoundException(
                    String.format("CabeceraPedido with id '%s' not found", search));
        }
        return cabPedido;
    }

    @Override
    public CabeceraPedidoEntity create(CabeceraPedidoEntity cabeceraPedido) throws ClassNotFoundException {
        String codEmpleado = cabeceraPedido.getEmpleado().getId(), codCliente = cabeceraPedido.getCliente().getId();
        empleadoRepository.findById(codEmpleado).orElseThrow(() -> new ClassNotFoundException(
                String.format("Empleado with id '%s' not found", codEmpleado)));

        clienteRepository.findById(codCliente).orElseThrow(() -> new ClassNotFoundException(
                String.format("Cliente with id '%s' not found", codCliente)));
        return CabeceraPedidoRepository.save(cabeceraPedido);
    }

    @Override
    public void delete(String id) throws ClassNotFoundException, IllegalArgumentException, Exception {
        if (!uuidValidate.matcher(id).matches()) {
            throw new IllegalArgumentException(String.format("id '%s' must be a uuid", id));
        }
        CabeceraPedidoEntity CabeceraPedido = this.findOne(id);
        ComprobanteEntity comprbt = comprobanteRepository.findByVenta(CabeceraPedido);
        List<DetallePedidoEntity> cabVenta = detPedidoRepository.findByCabventa(CabeceraPedido);

        if (comprbt != null)
            throw new Exception(
                    String.format("CabeceraPedido with id '%s' cannot be deleted because it is in use.(comprobante)", id));
        if ( !cabVenta.isEmpty())
            throw new Exception(
                    String.format("CabeceraPedido with id '%s' cannot be deleted because it is in use.(detalle_venta)", id));

        CabeceraPedidoRepository.delete(CabeceraPedido);
        return;
    }

    public void delete2(String id) throws ClassNotFoundException, IllegalArgumentException, Exception {
        if (!uuidValidate.matcher(id).matches()) {
            throw new IllegalArgumentException(String.format("id '%s' must be a uuid", id));
        }
        CabeceraPedidoEntity cabeceraPedido = this.findOne(id);
        cabeceraPedido.setEstado(false);
        CabeceraPedidoRepository.save(cabeceraPedido);
    }

    @Override
    public CabeceraPedidoEntity update(CabeceraPedidoEntity CabeceraPedidoEntityDto, String id)
            throws ClassNotFoundException {
        if (!uuidValidate.matcher(id).matches()) {
            throw new IllegalArgumentException(String.format("id '%s' must be a uuid", id));
        }
        CabeceraPedidoEntity emp = this.findOne(id);
        BeanUtils.copyProperties(CabeceraPedidoEntityDto, emp);
        // verificar(emp.getDni());
        return CabeceraPedidoRepository.save(emp);
    }

    // private void verificar(String dni){
    // CabeceraPedidoEntity CabeceraPedidoNotValid =
    // CabeceraPedidoRepository.findByDni(dni);
    // if (CabeceraPedidoNotValid != null) {
    // throw new IllegalArgumentException(
    // String.format("CabeceraPedido with dni '%s' is exists in db", dni));
    // }
    // }
}
