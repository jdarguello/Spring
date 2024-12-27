package com.ecommerce.ProductosApi.controladores;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ProductosApi.modelos.Articulo;
import com.ecommerce.ProductosApi.modelos.CarritoCompras;
import com.ecommerce.ProductosApi.servicios.CarritoService;

@RestController
@RequestMapping("/api/carrito")
public class CarritoControlador {
    
    private CarritoService service;

    public CarritoControlador (CarritoService service) {
        this.service = service;
    }

    @GetMapping
    public List<CarritoCompras> getArticulos () {
        return this.service.obtenerTodosCarritos();
    }

    @PostMapping
    public CarritoCompras nuevoCarrito(@RequestBody CarritoCompras carrito) {
        return this.service.nuevoCarrito(carrito);
    }

    @PutMapping("/{carritoId}")
    public CarritoCompras addArticulo (@PathVariable("carritoId") Long carritoId, @RequestBody Articulo nuevoArticulo) {
        return this.service.añadirArticulo(carritoId, nuevoArticulo);
    }


}
