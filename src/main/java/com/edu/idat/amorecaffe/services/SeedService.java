/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edu.idat.amorecaffe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.idat.amorecaffe.data.SeedData;
import com.edu.idat.amorecaffe.entity.CabeceraPedidoEntity;
import com.edu.idat.amorecaffe.entity.CargoEntity;
import com.edu.idat.amorecaffe.entity.CategoriaEntity;
import com.edu.idat.amorecaffe.entity.ClienteEntity;
import com.edu.idat.amorecaffe.entity.ComprobanteEntity;
import com.edu.idat.amorecaffe.entity.DetallePedidoEntity;
import com.edu.idat.amorecaffe.entity.DetallePedidoId;
import com.edu.idat.amorecaffe.entity.DistritoEntity;
import com.edu.idat.amorecaffe.entity.EmpleadoEntity;
import com.edu.idat.amorecaffe.entity.ProductoEntity;
import com.edu.idat.amorecaffe.repository.CabeceraPedidoRepository;
import com.edu.idat.amorecaffe.repository.CargoRepository;
import com.edu.idat.amorecaffe.repository.CategoriaRepository;
import com.edu.idat.amorecaffe.repository.ClienteRepository;
import com.edu.idat.amorecaffe.repository.ComprobanteRepository;
import com.edu.idat.amorecaffe.repository.DetallePedidoRepository;
import com.edu.idat.amorecaffe.repository.DistritoRepository;
import com.edu.idat.amorecaffe.repository.EmpleadoRepository;
import com.edu.idat.amorecaffe.repository.ProductoRepository;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author 51934
 */
@Service
public class SeedService {

    @Autowired
    private CargoRepository cargoRepository;
    @Autowired
    private DistritoRepository distritoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private EmpleadoRepository empleadoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private CabeceraPedidoRepository cabeceraPedidoRepository;
    @Autowired
    private DetallePedidoRepository detallePedidoRepository;
    @Autowired 
    private ComprobanteRepository comprobanteRepository;
    
    
    public void seedExecute() {
        deleteTables();
        List<CargoEntity> cargoList = cargoRepository.saveAll(SeedData.cargo);
        List<DistritoEntity> distritoList = distritoRepository.saveAll(SeedData.distrito);
        List<CategoriaEntity> categoriaList = categoriaRepository.saveAll(SeedData.categoria);
        List<ProductoEntity> productosList = insertProductos(categoriaList);
        List<EmpleadoEntity> empleadosList = insertEmpleados(distritoList, cargoList);
        List<ClienteEntity> clientesList = insertClientes(distritoList);
        List<CabeceraPedidoEntity> cabsList = insertCabecera(clientesList, empleadosList);
        
        insertDetalle(cabsList, productosList);
        insertComprobante(cabsList);

    }

    private List<ProductoEntity> insertProductos(List<CategoriaEntity> categoriaList) {
        int random = (int) (Math.random() * categoriaList.size() + 1);
        List<ProductoEntity> products = SeedData.producto;
        for (ProductoEntity pro : products) {
            pro.setCategoria(categoriaList.get(random - 1));
        }
        return productoRepository.saveAll(products);
    }

    private List<EmpleadoEntity> insertEmpleados(List<DistritoEntity> distritoList,
            List<CargoEntity> cargoList) {
        List<EmpleadoEntity> empleados = SeedData.empleado;
        for (EmpleadoEntity emp : empleados) {
            int randomDistrito = (int) (Math.random() * distritoList.size() + 1);
            int randomCargo = (int) (Math.random() * cargoList.size() + 1);
            emp.setCargo(cargoList.get(randomCargo - 1));
            emp.setDistrito(distritoList.get(randomDistrito - 1));
        }

        return empleadoRepository.saveAll(empleados);
    }

    private List<ClienteEntity> insertClientes(List<DistritoEntity> distritoList) {
        List<ClienteEntity> clientes = SeedData.cliente;
        for (ClienteEntity emp : clientes) {
            int randomDistrito = (int) (Math.random() * distritoList.size() + 1);
            emp.setDistrito(distritoList.get(randomDistrito - 1));
        }
        return clienteRepository.saveAll(clientes);
    }

    private List<CabeceraPedidoEntity> insertCabecera(List<ClienteEntity> clienteList, List<EmpleadoEntity> empleadoList) {
        List<CabeceraPedidoEntity> cabs = new ArrayList<>();
        for (int i = 0; i <= 2; i++) {
            int randomCliente = (int) (Math.random() * clienteList.size() + 1);
            int randomEmpleado = (int) (Math.random() * empleadoList.size() + 1);
            cabs.add(new CabeceraPedidoEntity(
                    clienteList.get(randomCliente - 1),
                    empleadoList.get(randomEmpleado - 1),
                    100.00, new Date()
            ));
        }
        return cabeceraPedidoRepository.saveAll(cabs);
    }

    private void insertDetalle(List<CabeceraPedidoEntity> cabsList,
            List<ProductoEntity> productoList) {
        List<DetallePedidoEntity> dets = SeedData.detallePedido;
        for (int i = 0; i <= 2; i++) {
            DetallePedidoEntity detail = dets.get(i);
            CabeceraPedidoEntity cabSelect = cabsList.get(i);
            ProductoEntity pro = productoList.get(i);

            detail.setProducto(pro);
            detail.setCabventa(cabSelect);
            DetallePedidoId detId = new DetallePedidoId(detail.getCabventa().getId(), detail.getProducto().getId());
            detail.setId(detId);
            detail.setPrecio_producto(pro.getPrecio());
            detail.setSubtotal(detail.getPrecio_producto() * detail.getCantidad());
            
            cabSelect.setTotal(detail.getSubtotal());
           
        }
        cabeceraPedidoRepository.saveAll(cabsList);
        detallePedidoRepository.saveAll(dets);
    }

    private void insertComprobante(List<CabeceraPedidoEntity> cabList) {
        List<ComprobanteEntity> comprobantes = SeedData.comprobante;
        for (int i = 0; i <= 2; i++) {
            comprobantes.get(i).setVenta(cabList.get(i));
        }
        comprobanteRepository.saveAll(comprobantes);
    }
    
    private void deleteTables() {

        comprobanteRepository.deleteAll();
        detallePedidoRepository.deleteAll();
        cabeceraPedidoRepository.deleteAll();

        empleadoRepository.deleteAll();
        clienteRepository.deleteAll();
        productoRepository.deleteAll();

        cargoRepository.deleteAll();
        distritoRepository.deleteAll();
        categoriaRepository.deleteAll();
    }
}
