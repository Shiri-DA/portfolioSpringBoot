package com.proyectoyomi.dbnoticias.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table
//LOMBOK LIBRERIA
public class Aprehension {
    @Id
    @SequenceGenerator(
            name="aprehension_sequence",
            sequenceName = "aprehension_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "aprehension_sequence"
    )
    private Long id;

    @Column(name="marihuana")
    private Float marihuana;

    @Column(name="cocaina")
    private Float cocaina;

    @Column(name="hachis")
    private Float hachis;

    @Column(name="plantaciones")
    private Integer plantaciones;

    @Column(name="plantas")
    private Integer plantas;

    @Column(name="embarcaciones")
    private Integer embarcaciones;

    @Column(name="dinero")
    private String dinero;

    @Column(name="vehiculos")
    private Integer vehiculos;

    @ManyToOne(optional = false)
    @JoinColumn(name="operacionId", nullable = false)
    @JsonIgnore
    private Operacion operacion;


    public Aprehension() {}



    public Aprehension(Long id, Float marihuana, Float cocaina, float hachis, Integer plantaciones, Integer plantas, Integer embarcaciones, String dinero, Integer vehiculos) {
        this.id = id;
        this.marihuana = marihuana;
        this.cocaina = cocaina;
        this.hachis = hachis;
        this.plantaciones = plantaciones;
        this.plantas = plantas;
        this.embarcaciones = embarcaciones;
        this.dinero = dinero;
        this.vehiculos = vehiculos;
    }

    public Aprehension(Float marihuana, Float cocaina, float hachis, Integer plantaciones, Integer plantas, Integer embarcaciones, String dinero, Integer vehiculos) {
        this.marihuana = marihuana;
        this.cocaina = cocaina;
        this.hachis = hachis;
        this.plantaciones = plantaciones;
        this.plantas = plantas;
        this.embarcaciones = embarcaciones;
        this.dinero = dinero;
        this.vehiculos = vehiculos;
    }


// getters and setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getMarihuana() {
        return marihuana;
    }

    public void setMarihuana(Float marihuana) {
        this.marihuana = marihuana;
    }

    public Float getCocaina() {
        return cocaina;
    }

    public void setCocaina(Float cocaina) {
        this.cocaina = cocaina;
    }

    public float getHachis() {
        return hachis;
    }

    public void setHachis(float hachis) {
        this.hachis = hachis;
    }

    public Integer getPlantaciones() {
        return plantaciones;
    }

    public void setPlantaciones(Integer plantaciones) {
        this.plantaciones = plantaciones;
    }

    public Integer getPlantas() {
        return plantas;
    }

    public void setPlantas(Integer plantas) {
        this.plantas = plantas;
    }

    public Integer getEmbarcaciones() {
        return embarcaciones;
    }

    public void setEmbarcaciones(Integer embarcaciones) {
        this.embarcaciones = embarcaciones;
    }

    public String getDinero() {
        return dinero;
    }

    public void setDinero(String dinero) {
        this.dinero = dinero;
    }

    public Integer getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(Integer vehiculos) {
        this.vehiculos = vehiculos;
    }

    public Operacion getOperacion() {
        return operacion;
    }

    public void setOperacion(Operacion operacion) {
        this.operacion = operacion;
    }

    // -----
}
