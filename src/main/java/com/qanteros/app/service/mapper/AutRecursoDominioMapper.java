package com.qanteros.app.service.mapper;

import com.qanteros.app.domain.*;
import com.qanteros.app.service.dto.AutRecursoDominioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AutRecursoDominio} and its DTO {@link AutRecursoDominioDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AutRecursoDominioMapper extends EntityMapper<AutRecursoDominioDTO, AutRecursoDominio> {



    default AutRecursoDominio fromId(Long id) {
        if (id == null) {
            return null;
        }
        AutRecursoDominio autRecursoDominio = new AutRecursoDominio();
        autRecursoDominio.setId(id);
        return autRecursoDominio;
    }
}
