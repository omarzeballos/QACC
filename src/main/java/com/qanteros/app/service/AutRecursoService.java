package com.qanteros.app.service;

import com.qanteros.app.domain.AutRecurso;
import com.qanteros.app.repository.AutRecursoRepository;
import com.qanteros.app.service.dto.AutRecursoDTO;
import com.qanteros.app.service.mapper.AutRecursoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AutRecurso}.
 */
@Service
@Transactional
public class AutRecursoService {

    private final Logger log = LoggerFactory.getLogger(AutRecursoService.class);

    private final AutRecursoRepository autRecursoRepository;

    private final AutRecursoMapper autRecursoMapper;

    public AutRecursoService(AutRecursoRepository autRecursoRepository, AutRecursoMapper autRecursoMapper) {
        this.autRecursoRepository = autRecursoRepository;
        this.autRecursoMapper = autRecursoMapper;
    }

    /**
     * Save a autRecurso.
     *
     * @param autRecursoDTO the entity to save.
     * @return the persisted entity.
     */
    public AutRecursoDTO save(AutRecursoDTO autRecursoDTO) {
        log.debug("Request to save AutRecurso : {}", autRecursoDTO);
        AutRecurso autRecurso = autRecursoMapper.toEntity(autRecursoDTO);
        autRecurso = autRecursoRepository.save(autRecurso);
        return autRecursoMapper.toDto(autRecurso);
    }

    /**
     * Get all the autRecursos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AutRecursoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AutRecursos");
        return autRecursoRepository.findAll(pageable)
            .map(autRecursoMapper::toDto);
    }


    /**
     * Get one autRecurso by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AutRecursoDTO> findOne(Long id) {
        log.debug("Request to get AutRecurso : {}", id);
        return autRecursoRepository.findById(id)
            .map(autRecursoMapper::toDto);
    }

    /**
     * Delete the autRecurso by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AutRecurso : {}", id);
        autRecursoRepository.deleteById(id);
    }
}
