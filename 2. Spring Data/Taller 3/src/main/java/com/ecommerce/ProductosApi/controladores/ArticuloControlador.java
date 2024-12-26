package com.ecommerce.ProductosApi.controladores;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ProductosApi.modelos.Articulo;
import com.ecommerce.ProductosApi.servicios.ArticuloService;

@RestController
@RequestMapping("/api/articulo")
public class ArticuloControlador {
    
    private ArticuloService service;

    public ArticuloControlador (ArticuloService service) {
        this.service = service;
    }

    @GetMapping
    public List<Articulo> getArticulos () {
        return this.service.obtenerTodosArticulos();
    }

    @PostMapping
    public Articulo crearArticulo (@RequestBody Articulo articulo) {
        return this.service.crearArticulo(articulo);
    }

}
