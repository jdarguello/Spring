package com.ecommerce.ProductosApi.modelos;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Generated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Carrito_Compras")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarritoCompras {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long carritoId;

    private String nombreCliente;
    private Integer cantidadArticulos = 0;
    private Double total = 0.0;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Articulo> articulos = new ArrayList<>();

    public Integer getCantidadArticulos () {
        Integer cantidad = 0;
        for (Articulo articulo : articulos) {
            cantidad = cantidad + articulo.getCantidad();
        }
        return cantidad;
    }

    public Double getTotal () {
        Double suma = 0.0;
        for (Articulo articulo : articulos) {
            suma = suma + articulo.getSubtotal();
        }
        return suma;
    }
}
