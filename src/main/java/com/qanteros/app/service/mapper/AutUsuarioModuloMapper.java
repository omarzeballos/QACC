package com.qanteros.app.service.mapper;

import com.qanteros.app.domain.*;
import com.qanteros.app.service.dto.AutUsuarioModuloDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AutUsuarioModulo} and its DTO {@link AutUsuarioModuloDTO}.
 */
@Mapper(componentModel = "spring", uses = {UsrUsuarioMapper.class, AutModuloMapper.class})
public interface AutUsuarioModuloMapper extends EntityMapper<AutUsuarioModuloDTO, AutUsuarioModulo> {

    @Mapping(source = "idUsuario.id", target = "idUsuarioId")
    @Mapping(source = "idModulo.id", target = "idModuloId")
    AutUsuarioModuloDTO toDto(AutUsuarioModulo autUsuarioModulo);

    @Mapping(source = "idUsuarioId", target = "idUsuario")
    @Mapping(source = "idModuloId", target = "idModulo")
    AutUsuarioModulo toEntity(AutUsuarioModuloDTO autUsuarioModuloDTO);

    default AutUsuarioModulo fromId(Long id) {
        if (id == null) {
            return null;
        }
        AutUsuarioModulo autUsuarioModulo = new AutUsuarioModulo();
        autUsuarioModulo.setId(id);
        return autUsuarioModulo;
    }
}
