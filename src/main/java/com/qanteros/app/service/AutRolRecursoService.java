package com.qanteros.app.service;

import com.qanteros.app.domain.AutRolRecurso;
import com.qanteros.app.repository.AutRolRecursoRepository;
import com.qanteros.app.service.dto.AutRolRecursoDTO;
import com.qanteros.app.service.mapper.AutRolRecursoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AutRolRecurso}.
 */
@Service
@Transactional
public class AutRolRecursoService {

    private final Logger log = LoggerFactory.getLogger(AutRolRecursoService.class);

    private final AutRolRecursoRepository autRolRecursoRepository;

    private final AutRolRecursoMapper autRolRecursoMapper;

    public AutRolRecursoService(AutRolRecursoRepository autRolRecursoRepository, AutRolRecursoMapper autRolRecursoMapper) {
        this.autRolRecursoRepository = autRolRecursoRepository;
        this.autRolRecursoMapper = autRolRecursoMapper;
    }

    /**
     * Save a autRolRecurso.
     *
     * @param autRolRecursoDTO the entity to save.
     * @return the persisted entity.
     */
    public AutRolRecursoDTO save(AutRolRecursoDTO autRolRecursoDTO) {
        log.debug("Request to save AutRolRecurso : {}", autRolRecursoDTO);
        AutRolRecurso autRolRecurso = autRolRecursoMapper.toEntity(autRolRecursoDTO);
        autRolRecurso = autRolRecursoRepository.save(autRolRecurso);
        return autRolRecursoMapper.toDto(autRolRecurso);
    }

    /**
     * Get all the autRolRecursos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AutRolRecursoDTO> findAll() {
        log.debug("Request to get all AutRolRecursos");
        return autRolRecursoRepository.findAll().stream()
            .map(autRolRecursoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one autRolRecurso by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AutRolRecursoDTO> findOne(Long id) {
        log.debug("Request to get AutRolRecurso : {}", id);
        return autRolRecursoRepository.findById(id)
            .map(autRolRecursoMapper::toDto);
    }

    /**
     * Delete the autRolRecurso by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AutRolRecurso : {}", id);
        autRolRecursoRepository.deleteById(id);
    }
}
