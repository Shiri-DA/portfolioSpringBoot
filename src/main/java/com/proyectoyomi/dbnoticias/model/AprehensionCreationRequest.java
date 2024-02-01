package com.proyectoyomi.dbnoticias.model;


import java.util.List;

// Clase para el post desde web, que envia noticia y aprehension juntos
public class AprehensionCreationRequest {
//    private Noticia noticia;
    private Aprehension aprehension;

    private List<Localizacion> localizacion;
    private Operacion operacion;
    private Long idNoticia;

    public AprehensionCreationRequest(
//            Noticia noticia,
            Aprehension aprehension,
            List<Localizacion> localizacion,
            Operacion operacion,
            Long idNoticia) {


//        this.noticia = noticia;
        this.aprehension = aprehension;
        this.localizacion = localizacion;
        this.operacion = operacion;
        this.idNoticia = idNoticia;
    }

//    public Noticia getNoticia() {
//        return noticia;
//    }

//    public void setNoticia(Noticia noticia) {
//        this.noticia = noticia;
//    }


    public Long getIdNoticia() {
        return idNoticia;
    }

    public void setIdNoticia(Long idNoticia) {
        this.idNoticia = idNoticia;
    }

    public Aprehension getAprehension() {
        return aprehension;
    }

    public void setAprehension(Aprehension aprehension) {
        this.aprehension = aprehension;
    }

    public List<Localizacion> getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(List<Localizacion> localizacion) {
        this.localizacion = localizacion;
    }

    public Operacion getOperacion() {
        return operacion;
    }

    public void setOperacion(Operacion operacion) {
        this.operacion = operacion;
    }
}
