package com.proyectoyomi.dbnoticias.repository;

import com.proyectoyomi.dbnoticias.model.Operacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperacionRepository extends JpaRepository<Operacion, Long> {

    // Buscamos por fecha e implicados y devolvemos objeto Operacion entero
    @Query("SELECT o FROM Operacion o " +
            "WHERE (o.totalImplicados between :minimoImplicados AND :maximoImplicados) " +
            "AND (o.fecha between :fechaInicio AND :fechaFinal)")
    List<Operacion> searchOperacionByImplicadosAndFecha(
            @Param("minimoImplicados") Integer minimoImplicados,
            @Param("maximoImplicados") Integer maximoImplicados,
            @Param("fechaInicio") String fechaInicio,
            @Param("fechaFinal") String fechaFinal
    );


}

