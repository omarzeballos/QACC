package com.qanteros.app.repository;

import com.qanteros.app.domain.AutUsuarioRol;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AutUsuarioRol entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AutUsuarioRolRepository extends JpaRepository<AutUsuarioRol, Long> {

}
