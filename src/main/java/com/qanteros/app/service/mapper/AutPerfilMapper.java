package com.qanteros.app.service.mapper;

import com.qanteros.app.domain.*;
import com.qanteros.app.service.dto.AutPerfilDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AutPerfil} and its DTO {@link AutPerfilDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AutPerfilMapper extends EntityMapper<AutPerfilDTO, AutPerfil> {



    default AutPerfil fromId(Long id) {
        if (id == null) {
            return null;
        }
        AutPerfil autPerfil = new AutPerfil();
        autPerfil.setId(id);
        return autPerfil;
    }
}
