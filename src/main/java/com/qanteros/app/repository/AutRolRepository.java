package com.qanteros.app.repository;

import com.qanteros.app.domain.AutRol;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AutRol entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AutRolRepository extends JpaRepository<AutRol, Long> {

}
