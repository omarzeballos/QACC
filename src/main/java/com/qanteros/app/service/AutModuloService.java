package com.qanteros.app.service;

import com.qanteros.app.domain.AutModulo;
import com.qanteros.app.repository.AutModuloRepository;
import com.qanteros.app.service.dto.AutModuloDTO;
import com.qanteros.app.service.mapper.AutModuloMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AutModulo}.
 */
@Service
@Transactional
public class AutModuloService {

    private final Logger log = LoggerFactory.getLogger(AutModuloService.class);

    private final AutModuloRepository autModuloRepository;

    private final AutModuloMapper autModuloMapper;

    public AutModuloService(AutModuloRepository autModuloRepository, AutModuloMapper autModuloMapper) {
        this.autModuloRepository = autModuloRepository;
        this.autModuloMapper = autModuloMapper;
    }

    /**
     * Save a autModulo.
     *
     * @param autModuloDTO the entity to save.
     * @return the persisted entity.
     */
    public AutModuloDTO save(AutModuloDTO autModuloDTO) {
        log.debug("Request to save AutModulo : {}", autModuloDTO);
        AutModulo autModulo = autModuloMapper.toEntity(autModuloDTO);
        autModulo = autModuloRepository.save(autModulo);
        return autModuloMapper.toDto(autModulo);
    }

    /**
     * Get all the autModulos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AutModuloDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AutModulos");
        return autModuloRepository.findAll(pageable)
            .map(autModuloMapper::toDto);
    }


    /**
     * Get one autModulo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AutModuloDTO> findOne(Long id) {
        log.debug("Request to get AutModulo : {}", id);
        return autModuloRepository.findById(id)
            .map(autModuloMapper::toDto);
    }

    /**
     * Delete the autModulo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AutModulo : {}", id);
        autModuloRepository.deleteById(id);
    }
}
