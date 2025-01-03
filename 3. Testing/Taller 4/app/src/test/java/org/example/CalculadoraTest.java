package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CalculadoraTest {

    private Calculadora ejemploCalculadora;

    @BeforeEach
    void setUp () {
        ejemploCalculadora = new Calculadora();
    }

    @Test
    void suma() {
        assertEquals(ejemploCalculadora.suma(2, 5), 7);
    }

    @Test
    void resta() {
        assertEquals(ejemploCalculadora.resta(5,3), 2);
    }
}
