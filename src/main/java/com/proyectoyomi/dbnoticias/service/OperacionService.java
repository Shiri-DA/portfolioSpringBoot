package com.proyectoyomi.dbnoticias.service;

import com.proyectoyomi.dbnoticias.model.Operacion;
import com.proyectoyomi.dbnoticias.repository.OperacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class OperacionService {

    private final OperacionRepository operacionRepository;


    public OperacionService(OperacionRepository operacionRepository) {this.operacionRepository = operacionRepository;}

    @Transactional
    public void addNewOperacion(Operacion operacion) {
        operacionRepository.save(operacion);
    }



}
