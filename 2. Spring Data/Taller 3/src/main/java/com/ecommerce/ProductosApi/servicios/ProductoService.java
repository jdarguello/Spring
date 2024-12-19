package com.ecommerce.ProductosApi.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.ProductosApi.modelos.Producto;
import com.ecommerce.ProductosApi.repositorios.ProductoRepository;

@Service
public class ProductoService {

    //@Autowired
    private ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    public Producto crearProducto (Producto producto) {
        return this.repository.save(producto); //Se conecta a la BD y crea el nuevo registro
    }

    public List<Producto> obtenerTodosProductos () {
        return this.repository.findAll();
    }

    public Producto obtenerProductoPorId(Long id) {
        return this.repository.findById(id).get();
    }
    
}
