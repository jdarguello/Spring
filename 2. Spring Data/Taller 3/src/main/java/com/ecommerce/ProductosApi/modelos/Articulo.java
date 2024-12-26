package com.ecommerce.ProductosApi.modelos;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Articulo")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Articulo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "articuloGen")
    @SequenceGenerator(name = "articuloGen", sequenceName = "articulo_sequence", allocationSize = 1)
    private Long articuloId;

    private Integer cantidad;
    private Integer descuento;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productoId")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "carritoId")
    @JsonIgnore
    private CarritoCompras carrito;

    public Double getSubtotal () {
        return cantidad*this.producto.getPrecio();
    }

}
