/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edu.idat.amorecaffe.entity;

import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 * @author 51934
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "cab_pedidos")
@Table(name = "cab_pedidos", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "id" }) })
@Data
public class CabeceraPedidoEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @OneToOne
    @JoinColumn(name = "id_cliente")
    private ClienteEntity cliente;

    @OneToOne
    @JoinColumn(name = "id_empleado")
    private EmpleadoEntity empleado;

   

    @Column(name = "total", nullable = false,columnDefinition="Decimal(10,2)")
    @Positive()
    private Double total;
    @Column(name = "fecha", nullable = false)
    private Date fecha;
    @Column(name = "estado", nullable = false,columnDefinition = "bit(1) default '1'")
    private Boolean estado;
    

    @PrePersist
    public void prePersist() {
        id = UUID.randomUUID().toString();
        fecha =  new Date();
        if(estado == null) estado = true;
    }
}
