package com.proyectoyomi.dbnoticias.controller;

import com.proyectoyomi.dbnoticias.model.Noticia;
import com.proyectoyomi.dbnoticias.service.NoticiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/noticia")
@CrossOrigin("http://localhost:3000")
public class NoticiaController {

    private final NoticiaService noticiaService;

    @Autowired
    public NoticiaController(NoticiaService noticiaService) {
        this.noticiaService = noticiaService;
    }

    @GetMapping("")
    public List<Noticia> findAll() {
        return noticiaService.findAll();
    }


// Recibe de la web un multipart con CSV y llama a función de parseo para crear objeto y volcar en database
    @PostMapping(value ="/subirarchivo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void subidaYVolcadoCSV(
            @RequestParam MultipartFile archivo
    )  {
        noticiaService.volcadoCSV(archivo);
    }


    // Obtiene la primera noticia que no esté revisada y la envía
    @GetMapping("/revisado")
    public Noticia noticiaByRevisado()  {
        return noticiaService.getNoticiaByRevisado(false);
    }

    // Revisión de la noticia si está relacionada o no
    @PostMapping(value="/revisado", consumes = "application/json")
    public void updateNoticiaRelacionada(@RequestBody Noticia noticia) {
        noticiaService.resultadoRevisionNoticia(noticia);
    }


}
