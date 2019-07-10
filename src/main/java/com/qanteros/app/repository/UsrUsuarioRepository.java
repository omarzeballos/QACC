package com.qanteros.app.repository;

import com.qanteros.app.domain.UsrUsuario;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UsrUsuario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsrUsuarioRepository extends JpaRepository<UsrUsuario, Long> {

}
