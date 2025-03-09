package com.BancoC.taller6.unit.modelos;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import modelos.Producto;
import reactor.test.StepVerifier;

public class ProductoTest extends ModelosTest {

    @Test
    void createProducto() {
        assertEquals(camisaGuardada, camisa);
    }

    @Test
    void getProductos() {
        Producto pantalonComprado = repository.save(pantalon).block();

        StepVerifier
            .create(repository.findAll())
            .expectNextMatches(
                prenda -> this.comparacionProducto(prenda, camisaGuardada)
            )
            .expectNextMatches(
                prenda -> this.comparacionProducto(prenda, pantalonComprado)
            )
            .verifyComplete();
    }

    @Test
    void updateProducto() {
        camisaGuardada.setInventario(0);
        camisaGuardada.setPrecio(2_000_000.0);

        repository.save(camisaGuardada)
            .then(repository.findById(camisaGuardada.getProductoId()))
            .as(StepVerifier::create)
            .expectNextMatches(
                prenda -> prenda.getPrecio().equals(2_000_000.0)
                            && prenda.getInventario() == 0
            )
            .verifyComplete();
    }

    private Boolean comparacionProducto(Producto referencia, Producto producto) {
        return referencia.getProductoId() == producto.getProductoId()
            && referencia.getInventario() == producto.getInventario()
            && referencia.getNombre().equals(producto.getNombre())
            && referencia.getPrecio().equals(producto.getPrecio());
    }

}
