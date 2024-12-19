package com.ecommerce.ProductosApi.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.ecommerce.ProductosApi.modelos.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findAll();

    Producto findByNombreProducto(String nombre);
}
