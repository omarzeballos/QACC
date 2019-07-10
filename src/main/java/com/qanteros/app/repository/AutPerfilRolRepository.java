package com.qanteros.app.repository;

import com.qanteros.app.domain.AutPerfilRol;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AutPerfilRol entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AutPerfilRolRepository extends JpaRepository<AutPerfilRol, Long> {

}
