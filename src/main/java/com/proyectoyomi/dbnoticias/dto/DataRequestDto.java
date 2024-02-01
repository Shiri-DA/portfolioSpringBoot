package com.proyectoyomi.dbnoticias.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;


// Dto para recibir json
@Setter
@Getter
@NoArgsConstructor
public class DataRequestDto {
    private ArrayList<String> provincias;
    private AprehensionDto drogas;
    private ImplicadosDto implicados;
    private FechaDto fecha;

    // Mapeamos los json nesteados en otras clases DTO
    public DataRequestDto(
            ArrayList<String> provincias,
            String drogas,
            String implicados,
            String fecha) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        this.drogas = objectMapper.readValue(drogas, AprehensionDto.class);
        this.provincias = provincias;
        this.implicados = objectMapper.readValue(implicados, ImplicadosDto.class);
        this.fecha = objectMapper.readValue(fecha, FechaDto.class);
    }
}
