package com.proyectoyomi.dbnoticias.repository;

import com.proyectoyomi.dbnoticias.model.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoticiaRepository
        extends JpaRepository<Noticia, Long> {

    // Buscar por URL
    @Query("SELECT n FROM Noticia n WHERE n.url = ?1")
    Optional<Noticia> findNoticiabyUrl(String url) ;

    // Seleccionamos por revisión, pero ofrece un listado de resultados no solo uno
    // TODO encontrar el modo que devuelva solo un resultado
    @Query("SELECT n FROM Noticia n WHERE n.revisado = ?1")
   Optional<List<Noticia>> findFirstByNoticiabyRevisado(Boolean condicion);


    // Actualiza la revisión y relación de la noticia
    @Modifying
    @Query("UPDATE Noticia n SET n.revisado= :revisado, " +
            "n.relacionado = :relacionado, " +
            "n.volcado = :volcado WHERE n.id = :id")
    public void updateRelacionRevisionNoticia(
            @Param("revisado") Boolean revisado,
            @Param("relacionado") Boolean relacionado,
            @Param("id") Long id,
            @Param("volcado") Boolean volcado
    );



}
