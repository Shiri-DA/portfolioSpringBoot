package com.proyectoyomi.dbnoticias.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


// Objeto para mapear json
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AprehensionDto {
    private Integer cocainaMinimo;
    private Integer cocainaMaximo;
    private Integer marihuanaMaximo;
    private Integer marihuanaMinimo;
    private Integer hachisMaximo;
    private Integer hachisMinimo;
    private Integer plantacionesMinimo;
    private Integer plantacionesMaximo;
    private Integer plantasMinimo;
    private Integer plantasMaximo;

    // Método para meter las variables en un objeto con la categoria de cada variable
    public List<String[]> convertToList() {
        List<String[]> listado = new ArrayList<>();
        String[] cocaina = {
                "cocaina", Integer.toString(getCocainaMinimo()), Integer.toString(getCocainaMaximo())
        };
        String[] marihuana = {
                "marihuana", Integer.toString(getMarihuanaMinimo()), Integer.toString(getMarihuanaMaximo())};
        String[] hachis = {
                "hachis", Integer.toString(getHachisMinimo()), Integer.toString(getHachisMaximo())
        };
        String[] plantaciones = {
                "plantaciones", Integer.toString(getPlantacionesMinimo()), Integer.toString(getPlantacionesMaximo())
        };
        String[] plantas = {
                "plantas", Integer.toString(getPlantasMinimo()), Integer.toString(getPlantasMaximo())
        };
        listado.add(cocaina);
        listado.add(marihuana);
        listado.add(hachis);
        listado.add(plantaciones);
        listado.add(plantas);
        return listado;
    }
}
