package com.qanteros.app.service.mapper;

import com.qanteros.app.domain.*;
import com.qanteros.app.service.dto.AutUsuarioRolDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AutUsuarioRol} and its DTO {@link AutUsuarioRolDTO}.
 */
@Mapper(componentModel = "spring", uses = {UsrUsuarioMapper.class, AutRolMapper.class})
public interface AutUsuarioRolMapper extends EntityMapper<AutUsuarioRolDTO, AutUsuarioRol> {

    @Mapping(source = "idUsuario.id", target = "idUsuarioId")
    @Mapping(source = "idRol.id", target = "idRolId")
    AutUsuarioRolDTO toDto(AutUsuarioRol autUsuarioRol);

    @Mapping(source = "idUsuarioId", target = "idUsuario")
    @Mapping(source = "idRolId", target = "idRol")
    AutUsuarioRol toEntity(AutUsuarioRolDTO autUsuarioRolDTO);

    default AutUsuarioRol fromId(Long id) {
        if (id == null) {
            return null;
        }
        AutUsuarioRol autUsuarioRol = new AutUsuarioRol();
        autUsuarioRol.setId(id);
        return autUsuarioRol;
    }
}
