package com.qanteros.app.service.mapper;

import com.qanteros.app.domain.*;
import com.qanteros.app.service.dto.AutPerfilRolDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AutPerfilRol} and its DTO {@link AutPerfilRolDTO}.
 */
@Mapper(componentModel = "spring", uses = {AutPerfilMapper.class, AutRolMapper.class})
public interface AutPerfilRolMapper extends EntityMapper<AutPerfilRolDTO, AutPerfilRol> {

    @Mapping(source = "idPerfil.id", target = "idPerfilId")
    @Mapping(source = "idRol.id", target = "idRolId")
    AutPerfilRolDTO toDto(AutPerfilRol autPerfilRol);

    @Mapping(source = "idPerfilId", target = "idPerfil")
    @Mapping(source = "idRolId", target = "idRol")
    AutPerfilRol toEntity(AutPerfilRolDTO autPerfilRolDTO);

    default AutPerfilRol fromId(Long id) {
        if (id == null) {
            return null;
        }
        AutPerfilRol autPerfilRol = new AutPerfilRol();
        autPerfilRol.setId(id);
        return autPerfilRol;
    }
}
