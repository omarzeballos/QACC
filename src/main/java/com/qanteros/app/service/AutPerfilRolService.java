package com.qanteros.app.service;

import com.qanteros.app.domain.AutPerfilRol;
import com.qanteros.app.repository.AutPerfilRolRepository;
import com.qanteros.app.service.dto.AutPerfilRolDTO;
import com.qanteros.app.service.mapper.AutPerfilRolMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AutPerfilRol}.
 */
@Service
@Transactional
public class AutPerfilRolService {

    private final Logger log = LoggerFactory.getLogger(AutPerfilRolService.class);

    private final AutPerfilRolRepository autPerfilRolRepository;

    private final AutPerfilRolMapper autPerfilRolMapper;

    public AutPerfilRolService(AutPerfilRolRepository autPerfilRolRepository, AutPerfilRolMapper autPerfilRolMapper) {
        this.autPerfilRolRepository = autPerfilRolRepository;
        this.autPerfilRolMapper = autPerfilRolMapper;
    }

    /**
     * Save a autPerfilRol.
     *
     * @param autPerfilRolDTO the entity to save.
     * @return the persisted entity.
     */
    public AutPerfilRolDTO save(AutPerfilRolDTO autPerfilRolDTO) {
        log.debug("Request to save AutPerfilRol : {}", autPerfilRolDTO);
        AutPerfilRol autPerfilRol = autPerfilRolMapper.toEntity(autPerfilRolDTO);
        autPerfilRol = autPerfilRolRepository.save(autPerfilRol);
        return autPerfilRolMapper.toDto(autPerfilRol);
    }

    /**
     * Get all the autPerfilRols.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AutPerfilRolDTO> findAll() {
        log.debug("Request to get all AutPerfilRols");
        return autPerfilRolRepository.findAll().stream()
            .map(autPerfilRolMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one autPerfilRol by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AutPerfilRolDTO> findOne(Long id) {
        log.debug("Request to get AutPerfilRol : {}", id);
        return autPerfilRolRepository.findById(id)
            .map(autPerfilRolMapper::toDto);
    }

    /**
     * Delete the autPerfilRol by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AutPerfilRol : {}", id);
        autPerfilRolRepository.deleteById(id);
    }
}
