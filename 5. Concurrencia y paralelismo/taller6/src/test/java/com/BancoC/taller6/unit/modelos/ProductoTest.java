package com.BancoC.taller6.unit.modelos;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import com.BancoC.taller6.repositories.ProductoRepository;

import modelos.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class ProductoTest extends ModelosTest {

    
    @Autowired
    private ProductoRepository repository;

    @Test
    public void pruebaConexion() {
        assertTrue(postgres.isCreated());
        assertTrue(postgres.isRunning());
    }

    @Test
    public void createAndGetProductos() {
        Mono<Producto> guardarUnProducto = repository.save(camisa);

        StepVerifier.create(guardarUnProducto)
            .expectNextCount(1)
            .verifyComplete();
        System.out.println(guardarUnProducto);
        

        /* 
        StepVerifier.create(secuencia)
            .consumeNextWith(producto -> {
                System.out.println("Retrieved Producto: " + producto);
            })
            .expectNextMatches(producto -> false) // Should now fail
            .verifyComplete();*/
    }

}
