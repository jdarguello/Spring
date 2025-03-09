package com.BancoC.taller6.unit.modelos;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.BancoC.taller6.GeneralTest;
import com.BancoC.taller6.repositories.ProductoRepository;

import modelos.Producto;
import reactor.core.publisher.Mono;

@DataR2dbcTest
@Testcontainers
@ActiveProfiles("test")
public class ModelosTest extends GeneralTest {

    @Autowired
    protected ProductoRepository repository;

    protected Producto camisaGuardada;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres =
        new PostgreSQLContainer<>("postgres:15");

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp(); //=> importa objetos de pruebas

        camisaGuardada = repository.save(camisa).block();
    }

}
