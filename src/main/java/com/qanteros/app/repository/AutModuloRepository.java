package com.qanteros.app.repository;

import com.qanteros.app.domain.AutModulo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AutModulo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AutModuloRepository extends JpaRepository<AutModulo, Long> {

}
