package com.ecommerce.ProductosApi.controladores;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ProductosApi.modelos.Producto;
import com.ecommerce.ProductosApi.servicios.ProductoService;

@RestController                     //Habilita la exposición de APIs
@RequestMapping("/api/producto")    //Especifica la url raíz
public class ProductoControlador {
    
    private ProductoService service;

    public ProductoControlador (ProductoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Producto> getProductos() {
        return this.service.obtenerTodosProductos();
    }

    @GetMapping("/{id}")
    public Producto getProducto (@PathVariable("id") Long productoId) {
        return this.service.obtenerProductoPorId(productoId);
    }

    @PostMapping
    public Producto crearProducto (@RequestBody Producto producto) {
        return this.service.crearProducto(producto);
    }

}
