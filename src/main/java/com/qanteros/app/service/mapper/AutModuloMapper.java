package com.qanteros.app.service.mapper;

import com.qanteros.app.domain.*;
import com.qanteros.app.service.dto.AutModuloDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AutModulo} and its DTO {@link AutModuloDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AutModuloMapper extends EntityMapper<AutModuloDTO, AutModulo> {



    default AutModulo fromId(Long id) {
        if (id == null) {
            return null;
        }
        AutModulo autModulo = new AutModulo();
        autModulo.setId(id);
        return autModulo;
    }
}
