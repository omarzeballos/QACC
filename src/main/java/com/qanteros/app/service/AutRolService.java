package com.qanteros.app.service;

import com.qanteros.app.domain.AutRol;
import com.qanteros.app.repository.AutRolRepository;
import com.qanteros.app.service.dto.AutRolDTO;
import com.qanteros.app.service.mapper.AutRolMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AutRol}.
 */
@Service
@Transactional
public class AutRolService {

    private final Logger log = LoggerFactory.getLogger(AutRolService.class);

    private final AutRolRepository autRolRepository;

    private final AutRolMapper autRolMapper;

    public AutRolService(AutRolRepository autRolRepository, AutRolMapper autRolMapper) {
        this.autRolRepository = autRolRepository;
        this.autRolMapper = autRolMapper;
    }

    /**
     * Save a autRol.
     *
     * @param autRolDTO the entity to save.
     * @return the persisted entity.
     */
    public AutRolDTO save(AutRolDTO autRolDTO) {
        log.debug("Request to save AutRol : {}", autRolDTO);
        AutRol autRol = autRolMapper.toEntity(autRolDTO);
        autRol = autRolRepository.save(autRol);
        return autRolMapper.toDto(autRol);
    }

    /**
     * Get all the autRols.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AutRolDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AutRols");
        return autRolRepository.findAll(pageable)
            .map(autRolMapper::toDto);
    }


    /**
     * Get one autRol by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AutRolDTO> findOne(Long id) {
        log.debug("Request to get AutRol : {}", id);
        return autRolRepository.findById(id)
            .map(autRolMapper::toDto);
    }

    /**
     * Delete the autRol by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AutRol : {}", id);
        autRolRepository.deleteById(id);
    }
}
