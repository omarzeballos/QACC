package com.qanteros.app.service.mapper;

import com.qanteros.app.domain.*;
import com.qanteros.app.service.dto.AutRolRecursoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AutRolRecurso} and its DTO {@link AutRolRecursoDTO}.
 */
@Mapper(componentModel = "spring", uses = {AutRolMapper.class, AutRecursoMapper.class})
public interface AutRolRecursoMapper extends EntityMapper<AutRolRecursoDTO, AutRolRecurso> {

    @Mapping(source = "idRol.id", target = "idRolId")
    @Mapping(source = "idRecurso.id", target = "idRecursoId")
    AutRolRecursoDTO toDto(AutRolRecurso autRolRecurso);

    @Mapping(source = "idRolId", target = "idRol")
    @Mapping(source = "idRecursoId", target = "idRecurso")
    AutRolRecurso toEntity(AutRolRecursoDTO autRolRecursoDTO);

    default AutRolRecurso fromId(Long id) {
        if (id == null) {
            return null;
        }
        AutRolRecurso autRolRecurso = new AutRolRecurso();
        autRolRecurso.setId(id);
        return autRolRecurso;
    }
}
