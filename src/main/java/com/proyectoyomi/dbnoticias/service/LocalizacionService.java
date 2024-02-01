package com.proyectoyomi.dbnoticias.service;

import com.proyectoyomi.dbnoticias.model.Localizacion;
import com.proyectoyomi.dbnoticias.repository.LocalizacionRepository;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

@Service
public class LocalizacionService {

    private final LocalizacionRepository localizacionRepository;


    public LocalizacionService(
            LocalizacionRepository localizacionRepository
    ) { this.localizacionRepository = localizacionRepository; }

    public void addNewLocalizacion(Localizacion localizacion) {
        localizacionRepository.save(localizacion);
    }
}
