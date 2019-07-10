package com.qanteros.app.service;

import com.qanteros.app.domain.AutUsuarioModulo;
import com.qanteros.app.repository.AutUsuarioModuloRepository;
import com.qanteros.app.service.dto.AutUsuarioModuloDTO;
import com.qanteros.app.service.mapper.AutUsuarioModuloMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AutUsuarioModulo}.
 */
@Service
@Transactional
public class AutUsuarioModuloService {

    private final Logger log = LoggerFactory.getLogger(AutUsuarioModuloService.class);

    private final AutUsuarioModuloRepository autUsuarioModuloRepository;

    private final AutUsuarioModuloMapper autUsuarioModuloMapper;

    public AutUsuarioModuloService(AutUsuarioModuloRepository autUsuarioModuloRepository, AutUsuarioModuloMapper autUsuarioModuloMapper) {
        this.autUsuarioModuloRepository = autUsuarioModuloRepository;
        this.autUsuarioModuloMapper = autUsuarioModuloMapper;
    }

    /**
     * Save a autUsuarioModulo.
     *
     * @param autUsuarioModuloDTO the entity to save.
     * @return the persisted entity.
     */
    public AutUsuarioModuloDTO save(AutUsuarioModuloDTO autUsuarioModuloDTO) {
        log.debug("Request to save AutUsuarioModulo : {}", autUsuarioModuloDTO);
        AutUsuarioModulo autUsuarioModulo = autUsuarioModuloMapper.toEntity(autUsuarioModuloDTO);
        autUsuarioModulo = autUsuarioModuloRepository.save(autUsuarioModulo);
        return autUsuarioModuloMapper.toDto(autUsuarioModulo);
    }

    /**
     * Get all the autUsuarioModulos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AutUsuarioModuloDTO> findAll() {
        log.debug("Request to get all AutUsuarioModulos");
        return autUsuarioModuloRepository.findAll().stream()
            .map(autUsuarioModuloMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one autUsuarioModulo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AutUsuarioModuloDTO> findOne(Long id) {
        log.debug("Request to get AutUsuarioModulo : {}", id);
        return autUsuarioModuloRepository.findById(id)
            .map(autUsuarioModuloMapper::toDto);
    }

    /**
     * Delete the autUsuarioModulo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AutUsuarioModulo : {}", id);
        autUsuarioModuloRepository.deleteById(id);
    }
}
