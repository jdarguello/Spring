package com.ecommerce.ProductosApi.modelos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Producto")
@Data           
@AllArgsConstructor     //Crea un constructor con todos los argumentos
@NoArgsConstructor      //Crea un constructor sin argumentos   
@Builder                //Facilita la creación de objetos
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productoId;

    @Column(name = "nombre", length = 50, nullable = false, unique = true)
    private String nombreProducto;
    private Double precio;
    private Integer inventario;

    //@OneToMany(mappedBy = "producto", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    //@JsonIgnore
    //private List<Articulo> articulos;
}
