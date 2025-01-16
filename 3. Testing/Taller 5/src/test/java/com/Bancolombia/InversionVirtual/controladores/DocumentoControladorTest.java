package com.Bancolombia.InversionVirtual.controladores;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.StatusResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.Bancolombia.InversionVirtual.config.ErrorModel;
import com.Bancolombia.InversionVirtual.modelos.Cliente;
import com.Bancolombia.InversionVirtual.modelos.Documento;
import com.Bancolombia.InversionVirtual.servicios.Interfaces.ClienteOperaciones;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(DocumentoControlador.class)
public class DocumentoControladorTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;  //Serializador JSON

    @Autowired
    private ClienteOperaciones clienteOperaciones;

    @TestConfiguration
    public static class TestConfig {
        @Bean
        public ClienteOperaciones mockeOperaciones() {
            return mock(ClienteOperaciones.class);
        }
    }

    private Documento cedulaFernanda;

    private String requestBody;

    @BeforeEach
    public void setUp() throws Exception {
        cedulaFernanda = Documento.builder()
            .documentoId(2L)
            .tipo("CC")
            .numeroDocumento("10983929238")
            .build();

        when(clienteOperaciones.registrarNuevoDocumento(1L, cedulaFernanda))
            .thenReturn(cedulaFernanda);

        requestBody = objectMapper.writeValueAsString(cedulaFernanda);   //Serialización JSON de la cédula
    }

    @Test
    public void nuevoDocumento201 () throws Exception {
        MvcResult response = postNuevoDocumento(status().isCreated(), requestBody);

        Documento documentoObtenido = objectMapper.readValue(response.getResponse().getContentAsString(), Documento.class);

        assertEquals(documentoObtenido.getDocumentoId(), 2L);
        assertEquals(documentoObtenido.getTipo(), "CC");
        assertEquals(documentoObtenido.getNumeroDocumento(), "10983929238");
    }

    @Test
    public void nuevoDocumento400YaRegistrado() throws Exception {
         when(clienteOperaciones.registrarNuevoDocumento(1L, cedulaFernanda))
            .thenThrow(new SQLException("400: el documento ya está registrado en BD"));

        
        MvcResult result = postNuevoDocumento(status().isBadRequest(), requestBody);

        ErrorModel error = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorModel.class);


        assertEquals(error.status(), 400);
        assertEquals(error.message(), "400: el documento ya está registrado en BD");
        assertEquals(error.path(), "/api/docs");
    }

    @Test
    public void nuevoDocumento400InfoIncompleta() throws Exception {
        //Error de documento incompleto
        Documento pasaporteIncompleto = Documento.builder()
            .tipo("PP")
            .build();
        
        when(clienteOperaciones.registrarNuevoDocumento(1L, pasaporteIncompleto))
            .thenThrow(new InstantiationException("400: la información del documento está incompleta"));
        
        MvcResult result = postNuevoDocumento(status().isBadRequest(), objectMapper.writeValueAsString(pasaporteIncompleto));

        ErrorModel error = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorModel.class);

        assertEquals(error.status(), 400);
        assertEquals(error.message(), "400: la información del documento está incompleta");
    }

    private MvcResult postNuevoDocumento(ResultMatcher status, String requestBody) throws Exception {
        return mvc.perform(post("/api/docs")
            .param("clienteId", "1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status)
        .andReturn();
    }
}
