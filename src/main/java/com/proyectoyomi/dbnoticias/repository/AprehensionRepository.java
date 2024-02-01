package com.proyectoyomi.dbnoticias.repository;

import com.proyectoyomi.dbnoticias.model.Aprehension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AprehensionRepository  extends JpaRepository<Aprehension, Long>, AprehensionCustomRepository,
 JpaSpecificationExecutor<Aprehension>{

}
