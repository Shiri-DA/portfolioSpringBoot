package com.proyectoyomi.dbnoticias.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.proyectoyomi.dbnoticias.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/consulta")
@CrossOrigin("http://localhost:3000")
public class ConsultaController {

    private final ConsultaService consultaService;

    @Autowired
    public ConsultaController(ConsultaService consultaService) { this.consultaService = consultaService; }

    @PostMapping(consumes = "application/json")
    public String consultaWeb(@RequestBody String request) throws JsonProcessingException {
        return consultaService.resultadosBusqueda(request);
    }





}
