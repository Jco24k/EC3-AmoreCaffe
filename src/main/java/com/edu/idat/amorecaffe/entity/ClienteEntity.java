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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
@Entity(name = "clientes")
@Table(name = "clientes", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "dni", "id" }) })
@Data
public class ClienteEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    @Column(name = "nombre", nullable = false)
    @NotNull(message = "name must not be null")
    @Size(min = 1)
    private String nombre;
    @Column(name = "apellido", nullable = false)
    @NotNull(message = "name must not be null")
    @Size(min = 1)
    private String apellido;
    @Column(name = "dni", nullable = false, length = 8, unique = true)
    @NotNull(message = "dni must not be null")
    @Size(min = 8, max = 8, message = "dni must be to 8 digits")
    @Pattern(regexp = "[0-9]*", message = "dni must be a number")
    private String dni;
    @Column(name = "telefono", nullable = false, length = 8)
    @NotNull(message = "CellPhone must not be null")
    @Size(min = 9, max = 9, message = "CellPhone must be to 9 digits")
    @Pattern(regexp = "[0-9]*", message = "CellPhone must be a number")
    private String telefono;
    @Column(name = "direccion", nullable = false)
    @NotNull(message = "address must not be null")
    private String direccion;
    @OneToOne
	@JoinColumn(name="id_distrito")
	private DistritoEntity distrito;


    @PrePersist
    public void prePersist() {
        id = UUID.randomUUID().toString();
    }
}



