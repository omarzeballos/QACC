package com.qanteros.app.repository;

import com.qanteros.app.domain.AutRecursoDominio;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AutRecursoDominio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AutRecursoDominioRepository extends JpaRepository<AutRecursoDominio, Long> {

}
