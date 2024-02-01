package com.proyectoyomi.dbnoticias.repository;

import com.proyectoyomi.dbnoticias.model.Aprehension;

import java.util.List;

public interface AprehensionCustomRepository {
    List<Aprehension> buscarPorDrogaExcluyente(Long idOperacion, List<String[]> drogas);
}
