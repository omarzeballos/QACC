package com.qanteros.app.repository;

import com.qanteros.app.domain.AutRecurso;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AutRecurso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AutRecursoRepository extends JpaRepository<AutRecurso, Long> {

}
