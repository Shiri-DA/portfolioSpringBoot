package com.proyectoyomi.dbnoticias.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@AllArgsConstructor
@Getter
@Setter
public class Operacion {
    @Id
    @SequenceGenerator(
            name="operacion_sequence",
            sequenceName = "operacion_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "operacion_sequence"
    )
    private Long id;

    @Column(name="accion")
    private String accion;

    @Column(name="fecha")
    private String fecha;

    @Column(name="nombre")
    private String nombre;

    @Column(name="totalImplicados")
    private Integer totalImplicados;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinTable(name = "operacionNoticia",
            joinColumns = { @JoinColumn(name = "operacion_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "noticia_id", referencedColumnName = "id") })
    @JsonIgnore
    private Set<Noticia> noticias = new HashSet<>();

    @OneToMany(mappedBy = "operacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Aprehension> aprehensiones = new HashSet<>();

    public Operacion() {}

    public Operacion(String accion, String fecha, String nombre, Integer totalImplicados) {
        this.accion = accion;
        this.fecha = fecha;
        this.nombre = nombre;
        this.totalImplicados = totalImplicados;
    }

    public Operacion(Long id, String accion, String fecha, String nombre, Integer totalImplicados) {
        this.id = id;
        this.accion = accion;
        this.fecha = fecha;
        this.nombre = nombre;
        this.totalImplicados = totalImplicados;
    }

}
