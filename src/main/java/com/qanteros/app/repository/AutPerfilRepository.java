package com.qanteros.app.repository;

import com.qanteros.app.domain.AutPerfil;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AutPerfil entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AutPerfilRepository extends JpaRepository<AutPerfil, Long> {

}
