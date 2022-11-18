package com.edu.idat.amorecaffe.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "cargos")
@Table(name = "cargos", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "nombre","slug", "id" }) })
@Data
public class CargoEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    @Column(name = "nombre", nullable = false,unique = true)
    @NotNull(message = "name must not be null")
    @Size(min = 1)
    private String nombre;
    @Column(name = "slug", nullable = false,unique = true)
    private String slug;
    @Column(name = "descripcion", nullable = false)
    private String descripcion;
    @PrePersist
    public void prePersist() {
        id = UUID.randomUUID().toString();
        if ( this.slug == null ) {
            this.slug = this.nombre;
        }
        if(this.descripcion == null){
            this.descripcion = this.nombre;
        }
        this.slug = this.slug.toLowerCase().replaceAll(" ", "_")
            .replaceAll("'","");
    }
    
}
