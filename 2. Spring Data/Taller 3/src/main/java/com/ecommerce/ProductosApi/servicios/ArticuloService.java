package com.ecommerce.ProductosApi.servicios;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.ProductosApi.modelos.Articulo;
import com.ecommerce.ProductosApi.repositorios.ArticuloRepository;

@Service
public class ArticuloService {
    
    private ArticuloRepository repository;

    public ArticuloService (ArticuloRepository repository) {
        this.repository = repository;
    }

    public Articulo crearArticulo(Articulo articulo) {
        return this.repository.save(articulo);
    }

    public List<Articulo> obtenerTodosArticulos () {
        return this.repository.findAll();
    }

}
