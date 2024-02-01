package com.proyectoyomi.dbnoticias.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table
@Builder
@AllArgsConstructor
public class Noticia {
    @Id
    @SequenceGenerator(
            name="noticia_sequence",
            sequenceName = "noticia_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "noticia_sequence"
    )
    private Long id;

    @Column(name="url")
    private String url;

    @Column(name="titular")
    private String titular;

    @Column(name="subtitular")
    private String subtitular;

    @Column(name="fecha")
    private String fecha;

    @Column(name="revisado")
    private Boolean revisado;

    @Column(name="relacionado")
    private Boolean relacionado;

    @Column(name="volcado")
    private Boolean volcado;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "noticias")
    private Set<Operacion> operaciones = new HashSet<>();


    public Noticia() {}

    public Noticia(Long id,
                   String url,
                   String titular,
                   String subtitular,
                   String fecha,
                   Boolean revisado,
                   Boolean relacionado,
                   Boolean volcado) {
        this.id = id;
        this.url = url;
        this.titular = titular;
        this.subtitular = subtitular;
        this.fecha = fecha;
        this.revisado = revisado;
        this.relacionado = relacionado;
        this.volcado = volcado;
    }

    public Noticia(String url,
                   String titular,
                   String subtitular,
                   String fecha,
                   Boolean revisado,
                   Boolean relacionado,
                   Boolean volcado) {
        this.url = url;
        this.titular = titular;
        this.subtitular = subtitular;
        this.fecha = fecha;
        this.revisado = revisado;
        this.relacionado = relacionado;
        this.volcado = volcado;
    }

    public Noticia(String url,
                   String titular,
                   String subtitular,
                   String fecha) {
        this.url = url;
        this.titular = titular;
        this.subtitular = subtitular;
        this.fecha = fecha;
        setRevisado();
        setRelacionado();
        setVolcado();
    }

    // -- SETTERS & GETTERS --

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getSubtitular() {
        return subtitular;
    }

    public void setSubtitular(String subtitular) {
        this.subtitular = subtitular;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Boolean getRevisado() {
        return revisado;
    }

    public void setRevisado(Boolean revisado) {
        this.revisado = revisado;
    }

    // Seteamos para que si es null se auto-setee como falso
    public void setRevisado() {
        if (this.revisado == null) {
            this.revisado = false;
        }
    }

    public Boolean getRelacionado() {
        return relacionado;
    }

    public void setRelacionado(Boolean relacionado) {
        this.relacionado = relacionado;
    }

    // Seteamos para que si revisado es falso se autosetee como null
    public void setRelacionado() {
        if (!this.revisado) {
            this.relacionado = null;
        }
    }

    public Boolean getVolcado() {
        return volcado;
    }

    public void setVolcado(Boolean volcado) {
        this.volcado = volcado;
    }

    // Seteamos para que si el relacionado es falso o null se autosetee como null
    public void setVolcado() {
        if (this.relacionado == null || !this.relacionado) {
            this.volcado = null;
        }
    }

    public Set<Operacion> getOperaciones() {
        return operaciones;
    }

    public void setOperaciones(Set<Operacion> operacion) {
        this.operaciones = operacion;
    }

    // ----------------
}
