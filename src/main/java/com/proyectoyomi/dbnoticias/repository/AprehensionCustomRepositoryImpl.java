package com.proyectoyomi.dbnoticias.repository;

import com.proyectoyomi.dbnoticias.model.Aprehension;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Predicates;

import java.util.ArrayList;
import java.util.List;

public class AprehensionCustomRepositoryImpl implements AprehensionCustomRepository {
    @Autowired
    private EntityManager em;
    @Override
    public List<Aprehension> buscarPorDrogaExcluyente(Long idOperacion, List<String[]> drogas) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        // Creamos un objeto query para aprehension y le asignamos el root
        CriteriaQuery<Aprehension> cq = cb.createQuery(Aprehension.class);
        Root<Aprehension> root = cq.from(Aprehension.class);

        // Unimos la tabla de operacion para la busqueda de IDs de operacion
        Join<Object, Object> operacion = root.join("operacion");
        
        List<Predicate> predicates = new ArrayList<>();
//        String[] drogaInicial = drogas.get(0);
//        String nombreDrogaInicial = drogaInicial[0];
//        String minimoInicial = drogaInicial[1];
//        String maximoInicial = drogaInicial[2];
        // Creamos las querys
//        Predicate drogaPredicate = cb.between(root.get(nombreDrogaInicial), minimoInicial, maximoInicial);

        for (int i = 0; i < drogas.size(); i++) {
            String[] droga = drogas.get(i);
            String nombre = droga[0];
            String minimo = droga[1];
            String maximo = droga[2];
            Predicate newPredicate = cb.between(root.get(nombre), minimo, maximo);
            predicates.add(newPredicate);
        }

        Predicate drogaPredicate = cb.or(predicates.toArray(new Predicate[0]));
        Predicate operacionPredicate = cb.equal(operacion.get("id"), idOperacion);


        // Insertamos las querys
        cq.where(drogaPredicate, operacionPredicate);

        TypedQuery<Aprehension> query = em.createQuery(cq);
        return query.getResultList();
    }
}
