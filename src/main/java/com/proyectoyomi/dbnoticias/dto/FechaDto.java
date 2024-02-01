package com.proyectoyomi.dbnoticias.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Objeto para mapear json
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FechaDto {
    private String fechaInicio;
    private String fechaFinal;
}
