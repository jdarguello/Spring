package com.Bancolombia.InversionVirtual.controladores;

import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @MockitoBean
    private ClienteOperaciones clienteOperaciones;

    private Cliente Fernanda;
    private Documento cedulaFernanda;

    @BeforeEach
    public void setUp() {
        Fernanda = Cliente.builder()
            .clienteId(1L)
            .nombre("Fernanda Aristizabal")
            .build();
        
        cedulaFernanda = Documento.builder()
            .documentoId(2L)
            .cliente(Fernanda)
            .numeroDocumento("10983929238")
            .build();

        //Vincular documento con cliente
        Fernanda.setDocumentos(new ArrayList<>());      //Lista vacía de documentos
        Fernanda.getDocumentos().add(cedulaFernanda);   //Adición de cédula

        //Comportamiento del servicio (ClienteOperaciones)
        when(clienteOperaciones.registrarNuevoDocumento(1L, cedulaFernanda))
            .thenReturn(cedulaFernanda);
    }

    @Test
    public void nuevoDocumento201 () throws Exception {

        String requestBody = objectMapper.writeValueAsString(cedulaFernanda);

        MvcResult response =  mvc.perform(post("/api/docs")
                .param("clienteId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(status().isCreated())
            .andReturn();

        //Documento documento = objectMapper.readValue(response.getResponse().getContentAsString(), Documento.class);
    }

    @Test
    public void nuevoDocumento4xx() {
        //TODO
    }

    @Test
    public void nuevoDocumento5xx() {
        //TODO
    }

}
