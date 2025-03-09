package com.BancoC.taller6.servicios;

import org.springframework.stereotype.Service;

import com.BancoC.taller6.repositories.ProductoRepository;
import com.BancoC.taller6.servicios.contratos.ProductoOperaciones;

import modelos.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductoService implements ProductoOperaciones {

    private ProductoRepository repository;

    public ProductoService (ProductoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Producto> nuevoProducto(Producto producto) {
        try {
            this.validaciones(producto);
        } catch(Exception exception) {
            return Mono.error(exception);
        }
        return repository.save(producto);
    }

    @Override
    public Flux<Producto> todosProductos() {
        return repository.findAll();
    }

    @Override
    public Mono<Producto> obtenerProducto(Long productoId) {
        return repository.findById(productoId);
    }

    @Override
    public Mono<Producto> actualizarProducto(Long productoId, Producto updates) {
        return this.obtenerProducto(productoId)
            .filter(producto -> producto != null && isUpdateValid(updates))
            .map(
                producto -> {
                    realizarUpdate(producto, updates);
                    return producto;
                }
            ).flatMap(this::nuevoProducto);
    }

    @Override
    public Mono<Void> eliminarProducto(Long productoId) {
        return repository.deleteById(productoId);
    }

    private void validaciones(Producto producto) throws Exception {
        if (producto == null) {
            throw new Exception("Datos inválidos: producto nulo");
        } else if (producto.getPrecio() == null) {
            throw new Exception("Debe tener un precio definido");
        } else if (producto.getPrecio() < 0) {
            throw new Exception("Alerta: no se admiten precios negativos");
        }
    }

    private void realizarUpdate(Producto productoBD, Producto updates) {
        productoBD.setPrecio(updates.getPrecio());
        productoBD.setInventario(updates.getInventario());
    }

    private Boolean isUpdateValid(Producto updates) {
        if (updates.getInventario() != null && updates.getInventario() < 0) {
            return false;
        } else if (updates.getPrecio() != null && updates.getPrecio() <= 0) {
            return false;
        }
        return true;
    }
    
}
