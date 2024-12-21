package com.ecommerce.ProductosApi.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.ecommerce.ProductosApi.modelos.Articulo;


public interface ArticuloRepository extends JpaRepository<Articulo, Long> {
    
}
