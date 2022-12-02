package com.edu.idat.amorecaffe.entity;

import java.io.Serializable;
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
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tipo_comprobante")
@Table(name = "tipo_comprobante", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "nro_comprobante", "id" }) })
@Data
public class ComprobanteEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    @Column(name = "tipo", nullable = false)
    @NotNull(message = "tipo must not be null")
    @Size(min = 1)
    private String tipo;
    @Column(name = "nro_comprobante", nullable = false,unique = true)
    @NotNull(message = "nro_comprobante must not be null")
    @Size(min = 1)
    private String nroComprobante;

    @OneToOne
    @JoinColumn(name = "id_venta",unique = true)
    private CabeceraPedidoEntity venta;

    @Column(name = "estado", nullable = false, columnDefinition = "bit(1) default 1")
    private Boolean estado;

    @PrePersist
    public void prePersist() {
        id = UUID.randomUUID().toString();
        if (estado == null) {
            estado = true;
        }
    }

    public ComprobanteEntity(String tipo, String nroComprobante) {
        this.tipo = tipo;
        this.nroComprobante = nroComprobante;
    }
    
    
}
