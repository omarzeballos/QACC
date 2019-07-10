package com.qanteros.app.service;

import com.qanteros.app.domain.AutRecursoDominio;
import com.qanteros.app.repository.AutRecursoDominioRepository;
import com.qanteros.app.service.dto.AutRecursoDominioDTO;
import com.qanteros.app.service.mapper.AutRecursoDominioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AutRecursoDominio}.
 */
@Service
@Transactional
public class AutRecursoDominioService {

    private final Logger log = LoggerFactory.getLogger(AutRecursoDominioService.class);

    private final AutRecursoDominioRepository autRecursoDominioRepository;

    private final AutRecursoDominioMapper autRecursoDominioMapper;

    public AutRecursoDominioService(AutRecursoDominioRepository autRecursoDominioRepository, AutRecursoDominioMapper autRecursoDominioMapper) {
        this.autRecursoDominioRepository = autRecursoDominioRepository;
        this.autRecursoDominioMapper = autRecursoDominioMapper;
    }

    /**
     * Save a autRecursoDominio.
     *
     * @param autRecursoDominioDTO the entity to save.
     * @return the persisted entity.
     */
    public AutRecursoDominioDTO save(AutRecursoDominioDTO autRecursoDominioDTO) {
        log.debug("Request to save AutRecursoDominio : {}", autRecursoDominioDTO);
        AutRecursoDominio autRecursoDominio = autRecursoDominioMapper.toEntity(autRecursoDominioDTO);
        autRecursoDominio = autRecursoDominioRepository.save(autRecursoDominio);
        return autRecursoDominioMapper.toDto(autRecursoDominio);
    }

    /**
     * Get all the autRecursoDominios.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AutRecursoDominioDTO> findAll() {
        log.debug("Request to get all AutRecursoDominios");
        return autRecursoDominioRepository.findAll().stream()
            .map(autRecursoDominioMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one autRecursoDominio by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AutRecursoDominioDTO> findOne(Long id) {
        log.debug("Request to get AutRecursoDominio : {}", id);
        return autRecursoDominioRepository.findById(id)
            .map(autRecursoDominioMapper::toDto);
    }

    /**
     * Delete the autRecursoDominio by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AutRecursoDominio : {}", id);
        autRecursoDominioRepository.deleteById(id);
    }
}
