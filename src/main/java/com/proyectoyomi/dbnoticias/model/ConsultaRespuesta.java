package com.proyectoyomi.dbnoticias.model;

import java.util.List;
import java.util.Optional;

// Creamos un objeto con la respuesta de la consulta que se enviará al frontend
public class ConsultaRespuesta {
    private List<Noticia> noticias;
    private List<Localizacion> localizaciones;
    private Aprehension aprehension;
    private Operacion operacion;

    public ConsultaRespuesta(
            List<Noticia> noticias,
            List<Localizacion> localizaciones,
            Aprehension aprehension,
            Operacion operacion
    ) {
        this.aprehension = aprehension;
        this.operacion = operacion;
        this.localizaciones = localizaciones;
        this.noticias = noticias;
    }

    // Para que de momento no de error
    // TODO rehacer cuando se finalice
    public ConsultaRespuesta() {}

}
