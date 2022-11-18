/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edu.idat.amorecaffe.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.validation.constraints.Positive;

/**
 *
 * @author 51934
 */
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity(name = "productos")
@Table(name = "productos", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id","nombre"})})
@Data
public class ProductoEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    @Column(name = "nombre", nullable = false,unique = true)
    @NotNull(message = "name must not be null")
    @Size(min = 1)
    private String nombre;
    @Column(name = "slug", nullable = false,unique = true)
    private String slug;
    @Column(name = "precio", nullable = false,columnDefinition="Decimal(10,2)")
    @NotNull(message = "precio must not be null")
    @Positive
    private Double precio;
    @Column(name = "cantidad", nullable = false)
    @NotNull(message = "cantidad must not be null") 
    @Positive
    private int cantidad;
    @OneToOne 
    @JoinColumn(name = "id_categoria")
    private CategoriaEntity categoria;

    @PrePersist
    @PreUpdate
    public void prePersist() {
        id = UUID.randomUUID().toString();
        if ( this.slug == null ) {
            this.slug = this.nombre;
        }
        this.slug = this.slug.toLowerCase().replaceAll(" ", "_")
            .replaceAll("'","");
    }
}
