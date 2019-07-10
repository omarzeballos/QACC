package com.qanteros.app.service.mapper;

import com.qanteros.app.domain.*;
import com.qanteros.app.service.dto.AutRolDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AutRol} and its DTO {@link AutRolDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AutRolMapper extends EntityMapper<AutRolDTO, AutRol> {



    default AutRol fromId(Long id) {
        if (id == null) {
            return null;
        }
        AutRol autRol = new AutRol();
        autRol.setId(id);
        return autRol;
    }
}
