/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edu.idat.amorecaffe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.edu.idat.amorecaffe.entity.CabeceraPedidoEntity;
import com.edu.idat.amorecaffe.entity.DetallePedidoEntity;
import com.edu.idat.amorecaffe.entity.DetallePedidoId;
import com.edu.idat.amorecaffe.entity.ProductoEntity;

/**
 *
 * @author 51934
 */
public interface DetallePedidoRepository extends JpaRepository<DetallePedidoEntity, DetallePedidoId> {
    List<DetallePedidoEntity> findByProducto(ProductoEntity productoEntity);
    List<DetallePedidoEntity> findByCabventa( CabeceraPedidoEntity cabeceraPedidoEntity);

    @Query(value = "delete from det_pedidos where cabventa_id = :cabventa_id",nativeQuery = true)
	public void DeleteDetVenta(@Param("cabventa_id") String cabventa_id);

    @Query(value = "select * from det_pedidos where cabventa_id = :cabventa_id",nativeQuery = true)
	public List<DetallePedidoEntity> SelectDetVenta(@Param("cabventa_id") String cabventa_id);

    @Query(value = "update det_pedidos set cantidad =:cantidad , precio=:precio,subtotal=:subtotal " 
    +"where cabventa_id =:cabventa_id and producto_id =:producto_id",nativeQuery = true)
	public DetallePedidoEntity UpdateDetalleVenta(@Param("cabventa_id") String cabventa_id
    ,@Param("producto_id") String producto_id,@Param("cantidad") int cantidad,
    @Param("precio") double precio,@Param("subtotal") double subtotal

    );
}
