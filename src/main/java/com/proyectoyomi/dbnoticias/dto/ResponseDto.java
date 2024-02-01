package com.proyectoyomi.dbnoticias.dto;

import com.proyectoyomi.dbnoticias.model.Aprehension;
import com.proyectoyomi.dbnoticias.model.Localizacion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {
    private Long operacionId;
    private String accion;
    private String fecha;
    private Integer implicados;
    private String nombreOperacion;

    private List<Localizacion> localizaciones;
    private Aprehension aprehension;
}
