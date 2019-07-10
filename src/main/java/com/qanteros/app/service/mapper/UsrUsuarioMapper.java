package com.qanteros.app.service.mapper;

import com.qanteros.app.domain.*;
import com.qanteros.app.service.dto.UsrUsuarioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UsrUsuario} and its DTO {@link UsrUsuarioDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UsrUsuarioMapper extends EntityMapper<UsrUsuarioDTO, UsrUsuario> {



    default UsrUsuario fromId(Long id) {
        if (id == null) {
            return null;
        }
        UsrUsuario usrUsuario = new UsrUsuario();
        usrUsuario.setId(id);
        return usrUsuario;
    }
}
