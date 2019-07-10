package com.qanteros.app.repository;

import com.qanteros.app.domain.AutRolRecurso;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AutRolRecurso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AutRolRecursoRepository extends JpaRepository<AutRolRecurso, Long> {

}
