package com.BancoC.security.controladores;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/saludo")
public class SaludarController {

    @GetMapping
    public String saludar() {
        return "Hola!";
    }
    
}
