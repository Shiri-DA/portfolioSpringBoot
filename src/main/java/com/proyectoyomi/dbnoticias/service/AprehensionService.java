package com.proyectoyomi.dbnoticias.service;

import com.proyectoyomi.dbnoticias.model.*;
import com.proyectoyomi.dbnoticias.repository.AprehensionRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;



@Service
public class AprehensionService {

    private final AprehensionRepository aprehensionRepository;
    private final NoticiaService noticiaService;
    private final OperacionService operacionService;
    private final LocalizacionService localizacionService;


    public AprehensionService(AprehensionRepository aprehensionRepository,
                              NoticiaService noticiaService,
                              OperacionService operacionService,
                              LocalizacionService localizacionService) {
        this.aprehensionRepository = aprehensionRepository;
        this.noticiaService = noticiaService;
        this.operacionService = operacionService;
        this.localizacionService = localizacionService;
    }


    public void addNewAprehension(AprehensionCreationRequest newData) throws Exception {
        Aprehension aprehension = newData.getAprehension();
        List<Localizacion> localizacion = newData.getLocalizacion();
        Operacion operacion =  newData.getOperacion();

        // Recibimos el ID de la noticia desde el frontend, despúes de que esta haya sido actualizada
        // con este ID instanciamos un nuevo objeto búscandolo en la database
        // seguidamente lo añadimos a un hash set y lo metemos en la operación que subimos
        Optional<Noticia> llamadaNoticia = noticiaService.findById(newData.getIdNoticia());
        Noticia noticia = llamadaNoticia.get();
        Set<Noticia> hashNoticias = new HashSet<>();
        hashNoticias.add(noticia);
        operacion.setNoticias(hashNoticias);



        operacionService.addNewOperacion(operacion);

        aprehension.setOperacion(operacion);
        aprehensionRepository.save(aprehension);

        for (Localizacion element : localizacion) {
            element.setOperacion(operacion);
            localizacionService.addNewLocalizacion(element);
        }
    }

}
