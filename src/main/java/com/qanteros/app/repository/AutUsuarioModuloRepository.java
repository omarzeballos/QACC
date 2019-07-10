package com.qanteros.app.repository;

import com.qanteros.app.domain.AutUsuarioModulo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AutUsuarioModulo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AutUsuarioModuloRepository extends JpaRepository<AutUsuarioModulo, Long> {

}
