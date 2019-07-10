package com.qanteros.app.service;

import com.qanteros.app.domain.AutUsuarioRol;
import com.qanteros.app.repository.AutUsuarioRolRepository;
import com.qanteros.app.service.dto.AutUsuarioRolDTO;
import com.qanteros.app.service.mapper.AutUsuarioRolMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AutUsuarioRol}.
 */
@Service
@Transactional
public class AutUsuarioRolService {

    private final Logger log = LoggerFactory.getLogger(AutUsuarioRolService.class);

    private final AutUsuarioRolRepository autUsuarioRolRepository;

    private final AutUsuarioRolMapper autUsuarioRolMapper;

    public AutUsuarioRolService(AutUsuarioRolRepository autUsuarioRolRepository, AutUsuarioRolMapper autUsuarioRolMapper) {
        this.autUsuarioRolRepository = autUsuarioRolRepository;
        this.autUsuarioRolMapper = autUsuarioRolMapper;
    }

    /**
     * Save a autUsuarioRol.
     *
     * @param autUsuarioRolDTO the entity to save.
     * @return the persisted entity.
     */
    public AutUsuarioRolDTO save(AutUsuarioRolDTO autUsuarioRolDTO) {
        log.debug("Request to save AutUsuarioRol : {}", autUsuarioRolDTO);
        AutUsuarioRol autUsuarioRol = autUsuarioRolMapper.toEntity(autUsuarioRolDTO);
        autUsuarioRol = autUsuarioRolRepository.save(autUsuarioRol);
        return autUsuarioRolMapper.toDto(autUsuarioRol);
    }

    /**
     * Get all the autUsuarioRols.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AutUsuarioRolDTO> findAll() {
        log.debug("Request to get all AutUsuarioRols");
        return autUsuarioRolRepository.findAll().stream()
            .map(autUsuarioRolMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one autUsuarioRol by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AutUsuarioRolDTO> findOne(Long id) {
        log.debug("Request to get AutUsuarioRol : {}", id);
        return autUsuarioRolRepository.findById(id)
            .map(autUsuarioRolMapper::toDto);
    }

    /**
     * Delete the autUsuarioRol by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AutUsuarioRol : {}", id);
        autUsuarioRolRepository.deleteById(id);
    }
}
