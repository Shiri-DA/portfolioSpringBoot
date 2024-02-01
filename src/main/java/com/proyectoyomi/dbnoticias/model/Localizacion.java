package com.proyectoyomi.dbnoticias.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table
public class Localizacion {
    @Id
    @SequenceGenerator(
            name = "localizacion_sequence",
            sequenceName = "localizacion_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "localizacion_sequence"
    )
    private Long id;

    @Column(name = "pais")
    private String pais;

    @Column(name = "ccaa")
    private String ccaa;

    @Column(name = "provincia")
    private String provincia;

    @Column(name = "ciudad")
    private String ciudad;

    @Column(name = "barrio")
    private String barrio;

    @Column(name = "calle")
    private String calle;

    @ManyToOne(optional = false)
    @JoinColumn(name="operacionId", nullable = false)
    @JsonIgnore
    private Operacion operacion;

    public Localizacion() {
    }

    public Localizacion(String pais,
                        String ccaa,
                        String provincia,
                        String ciudad,
                        String barrio,
                        String calle) {
        this.pais = pais.toLowerCase();
        this.ccaa = ccaa.toLowerCase();
        this.provincia = provincia.toLowerCase();
        this.ciudad = ciudad.toLowerCase();
        this.barrio = barrio.toLowerCase();
        this.calle = calle.toLowerCase();
    }


    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais.toLowerCase();
    }

    public String getCcaa() {
        return ccaa;
    }

    public void setCcaa(String ccaa) {
        this.ccaa = ccaa.toLowerCase();
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia.toLowerCase();
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad.toLowerCase();
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio.toLowerCase();
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle.toLowerCase();
    }

    public Operacion getOperacion() {
        return operacion;
    }

    public void setOperacion(Operacion operacion) {
        this.operacion = operacion;
    }

    // ----
}
