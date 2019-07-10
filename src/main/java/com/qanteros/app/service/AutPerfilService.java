package com.qanteros.app.service;

import com.qanteros.app.domain.AutPerfil;
import com.qanteros.app.repository.AutPerfilRepository;
import com.qanteros.app.service.dto.AutPerfilDTO;
import com.qanteros.app.service.mapper.AutPerfilMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AutPerfil}.
 */
@Service
@Transactional
public class AutPerfilService {

    private final Logger log = LoggerFactory.getLogger(AutPerfilService.class);

    private final AutPerfilRepository autPerfilRepository;

    private final AutPerfilMapper autPerfilMapper;

    public AutPerfilService(AutPerfilRepository autPerfilRepository, AutPerfilMapper autPerfilMapper) {
        this.autPerfilRepository = autPerfilRepository;
        this.autPerfilMapper = autPerfilMapper;
    }

    /**
     * Save a autPerfil.
     *
     * @param autPerfilDTO the entity to save.
     * @return the persisted entity.
     */
    public AutPerfilDTO save(AutPerfilDTO autPerfilDTO) {
        log.debug("Request to save AutPerfil : {}", autPerfilDTO);
        AutPerfil autPerfil = autPerfilMapper.toEntity(autPerfilDTO);
        autPerfil = autPerfilRepository.save(autPerfil);
        return autPerfilMapper.toDto(autPerfil);
    }

    /**
     * Get all the autPerfils.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AutPerfilDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AutPerfils");
        return autPerfilRepository.findAll(pageable)
            .map(autPerfilMapper::toDto);
    }


    /**
     * Get one autPerfil by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AutPerfilDTO> findOne(Long id) {
        log.debug("Request to get AutPerfil : {}", id);
        return autPerfilRepository.findById(id)
            .map(autPerfilMapper::toDto);
    }

    /**
     * Delete the autPerfil by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AutPerfil : {}", id);
        autPerfilRepository.deleteById(id);
    }
}
