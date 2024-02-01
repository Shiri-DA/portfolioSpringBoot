package com.proyectoyomi.dbnoticias.model;

import java.util.ArrayList;

public class ConsultaPregunta {
    private ArrayList<String> provincias;
    private Object drogas;
    private Object implicados;
    private Object fechas;

    public ConsultaPregunta(ArrayList<String> provincias, Object drogas, Object implicados, Object fechas) {
        this.provincias = provincias;
        this.drogas = drogas;
        this.implicados = implicados;
        this.fechas = fechas;
    }

    public ArrayList<String> getProvincias() {
        return provincias;
    }

    public Object getDrogas() {
        return drogas;
    }

    public Object getImplicados() {
        return implicados;
    }

    public Object getFechas() {
        return fechas;
    }
}
