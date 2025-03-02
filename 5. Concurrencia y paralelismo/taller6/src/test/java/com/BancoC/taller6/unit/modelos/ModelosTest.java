package com.BancoC.taller6.unit.modelos;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import modelos.Producto;

@DataR2dbcTest
@Testcontainers
public class ModelosTest {

    @Container
    static PostgreSQLContainer<?> postgres =
        new PostgreSQLContainer<>("postgres:16.0");
     
    
    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        // Note: The R2DBC URL syntax for PostgreSQL is "r2dbc:postgresql://..."
        registry.add("spring.r2dbc.url", () ->
                "r2dbc:postgresql://" + postgres.getHost() +
                ":" + postgres.getFirstMappedPort() +
                "/" + postgres.getDatabaseName());
        registry.add("spring.r2dbc.username", postgres::getUsername);
        registry.add("spring.r2dbc.password", postgres::getPassword);
    }
        
    
    @Test
    public void connectionEstablished() {
        assertTrue(postgres.isCreated());
        assertTrue(postgres.isRunning());
    }

    protected Producto camisa;
    protected Producto pantalon;

    @BeforeEach
    public void setUp() {
        camisa = Producto.builder()
            .nombre("Camisa cuadros")
            .precio(100_000.0)
            .descripcion("Hermosa camisa de cuadros. ¡Compra ya!")
            .inventario(15)
            .build();

        pantalon = Producto.builder()
            .nombre("Pantalón cuero")
            .precio(1_000_000.0)
            .descripcion("Hermoso pantalón de edición limitada. ¡Compra ya!")
            .inventario(2)
            .build();
    }


}
