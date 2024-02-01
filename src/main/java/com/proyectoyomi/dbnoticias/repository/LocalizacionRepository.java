package com.proyectoyomi.dbnoticias.repository;

import com.proyectoyomi.dbnoticias.model.Localizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface LocalizacionRepository extends JpaRepository<Localizacion, Long> {

    // Recibe un array de provincias y une los resultados operacion
    // Seguramente no se utilice esta función, revisar en futuro
    // TODO: añadir clausula WHERE por fecha.
    // TODO: comprobar el formato de fecha recibido y el usado por postgresql
//    @Query("SELECT new com.proyectoyomi.dbnoticias.dto.LocalizacionOperacionResponse(" +
//            "l.pais, l.ccaa, l.provincia, l.ciudad, o.id, o.accion, o.fecha, o.nombre, o.totalImplicados) " +
//            "FROM Localizacion l JOIN l.operacion o " +
//            "WHERE l.provincia IN ?1")
//    List<LocalizacionOperacionResponse> findLocalizacionByProvincina(ArrayList<String> provincias);
//
    // Busca por id de operacion y provincia y luego de esos resultados selecciona todas las localizaciones que tengan el id de operacióm
    @Query("SELECT l FROM Localizacion l JOIN l.operacion o " +
            "WHERE o.id IN " +
            "(SELECT DISTINCT b.id FROM Localizacion a JOIN a.operacion b " +
            "WHERE (a.provincia in :provincias) " +
            "AND (b.id = :idOperacion))")
    List<Localizacion> findOperacionByProvincia(
            @Param("idOperacion") Long idOperacion,
            @Param("provincias") ArrayList<String> provincias
    );
}

