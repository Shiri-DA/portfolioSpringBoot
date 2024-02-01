package com.proyectoyomi.dbnoticias.controller;

import com.proyectoyomi.dbnoticias.model.*;
import com.proyectoyomi.dbnoticias.service.AprehensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/noticia")
@CrossOrigin("http://localhost:3000")
public class AprehensionController {

    private final AprehensionService aprehensionService;


    @Autowired
    public AprehensionController(
            AprehensionService aprehensionService
    ) {
        this.aprehensionService = aprehensionService;

    }


    @PostMapping(value="revisado/nuevaAprehension", consumes = "application/json;charset=UTF-8")
    public void newAprehension(
            @RequestBody AprehensionCreationRequest newData
    ) throws Exception {
        aprehensionService.addNewAprehension(newData);

    }

}
