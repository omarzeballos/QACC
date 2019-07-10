package com.qanteros.app.service.mapper;

import com.qanteros.app.domain.*;
import com.qanteros.app.service.dto.AutRecursoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AutRecurso} and its DTO {@link AutRecursoDTO}.
 */
@Mapper(componentModel = "spring", uses = {AutRecursoDominioMapper.class, AutModuloMapper.class})
public interface AutRecursoMapper extends EntityMapper<AutRecursoDTO, AutRecurso> {

    @Mapping(source = "idRecursoDominio.id", target = "idRecursoDominioId")
    @Mapping(source = "idRecursoPadre.id", target = "idRecursoPadreId")
    @Mapping(source = "idModulo.id", target = "idModuloId")
    AutRecursoDTO toDto(AutRecurso autRecurso);

    @Mapping(source = "idRecursoDominioId", target = "idRecursoDominio")
    @Mapping(source = "idRecursoPadreId", target = "idRecursoPadre")
    @Mapping(source = "idModuloId", target = "idModulo")
    AutRecurso toEntity(AutRecursoDTO autRecursoDTO);

    default AutRecurso fromId(Long id) {
        if (id == null) {
            return null;
        }
        AutRecurso autRecurso = new AutRecurso();
        autRecurso.setId(id);
        return autRecurso;
    }
}
