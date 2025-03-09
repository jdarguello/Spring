package com.BancoC.taller6.servicios.contratos;

import modelos.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoOperaciones {
    Mono<Producto> nuevoProducto(Producto producto);
    Flux<Producto> todosProductos();
    Mono<Producto> obtenerProducto(Long productoId);
    Mono<Producto> actualizarProducto(Long productoId, Producto updates);
    Mono<Void> eliminarProducto(Long productoId);
}
