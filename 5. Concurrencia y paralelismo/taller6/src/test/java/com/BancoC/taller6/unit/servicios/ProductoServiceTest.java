package com.BancoC.taller6.unit.servicios;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;

import modelos.Producto;
import reactor.test.StepVerifier;

public class ProductoServiceTest extends ServiciosTest {
    
    @Test
    void nuevoProducto() {
        StepVerifier
            .create(productoOperaciones.nuevoProducto(camisa))
            .expectNextMatches(
                prenda -> this.verificacionProducto(camisaBD, prenda)
            )
            .verifyComplete();
    }

    @Test
    void nuevoProductoInvalido() {
        //Precio null
        camisa.setPrecio(null);
        StepVerifier
            .create(productoOperaciones.nuevoProducto(camisa))
            .expectErrorMatches(
                error -> error instanceof Exception &&
                         error.getMessage().equals("Debe tener un precio definido")
            )
            .verify();

        //Precio negativo
        camisa.setPrecio(-1_000.0);
        StepVerifier
            .create(productoOperaciones.nuevoProducto(camisa))
            .expectErrorMatches(
                error -> error instanceof Exception &&
                         error.getMessage().equals("Alerta: no se admiten precios negativos")
            )
            .verify();  

    }

    @Test
    void obtenerTodosProductosExistentes() {
        StepVerifier
            .create(productoOperaciones.todosProductos())
            .expectNextMatches(
                prenda -> verificacionProducto(camisaBD, prenda)
            )
            .expectNextMatches(
                prenda -> verificacionProducto(pantalonBD, prenda)
            )
            .verifyComplete();
    }

    @Test
    void obtenerProductoExistente() {
        StepVerifier
            .create(productoOperaciones.obtenerProducto(102L))
            .expectNextMatches(
                prenda -> prenda.equals(pantalonBD)
            )
            .verifyComplete();
    }

    @Test
    void obtenerProductoInexistente() {
        StepVerifier
            .create(productoOperaciones.obtenerProducto(100L))
            .expectNextCount(0)
            .verifyComplete();
    }

    @Test
    void actualizarProducto() {
        StepVerifier
            .create(productoOperaciones.actualizarProducto(
                102L, 
                Producto.builder()
                    .precio(500_000.0)
                    .inventario(10)
                    .build()
            ))
            .expectNextMatches(
                prenda -> verificacionProducto(pantalonActualizado, prenda)
            )
            .verifyComplete();
        
        verify(productoRepository, times(1)).save(pantalonActualizado);
    }

    @Test
    void actualizarProductoNoValido() {
        StepVerifier
            .create(productoOperaciones.actualizarProducto(
                102L,
                Producto.builder()
                    .inventario(-1)
                    .build()
            ))
            .expectNextCount(0)
            .verifyComplete();
    }

    @Test
    void eliminarProducto() {
        StepVerifier
            .create(productoOperaciones.eliminarProducto(102L))
            .expectSubscription()
            .verifyComplete();

        verify(productoRepository, times(1)).deleteById(102L);
    }

    private Boolean verificacionProducto(Producto referencia, Producto producto) {
        return referencia.getProductoId() > 0 &&
            referencia.getNombre().equals(producto.getNombre()) &&
            referencia.getInventario() == producto.getInventario() &&
            referencia.getDescripcion().equals(producto.getDescripcion());
    }
}
