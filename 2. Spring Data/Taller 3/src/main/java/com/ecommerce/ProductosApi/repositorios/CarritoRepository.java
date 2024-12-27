package com.ecommerce.ProductosApi.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.ecommerce.ProductosApi.modelos.Articulo;
import com.ecommerce.ProductosApi.modelos.CarritoCompras;

public interface CarritoRepository extends JpaRepository<CarritoCompras, Long> {
    public CarritoCompras findByCarritoId(Long carritoId);
}
