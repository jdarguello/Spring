package com.ecommerce.ProductosApi.servicios;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.ProductosApi.modelos.CarritoCompras;
import com.ecommerce.ProductosApi.repositorios.CarritoRepository;

@Service
public class CarritoService {

    private CarritoRepository repository;

    public CarritoService (CarritoRepository repository) {
        this.repository = repository;
    }

    public List<CarritoCompras> obtenerTodosCarritos () {
        return this.repository.findAll();
    }

    public CarritoCompras nuevoCarrito (CarritoCompras carrito) {
        return this.repository.save(carrito);
    }
}
