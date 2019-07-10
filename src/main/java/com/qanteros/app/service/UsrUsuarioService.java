package com.qanteros.app.service;

import com.qanteros.app.domain.UsrUsuario;
import com.qanteros.app.repository.UsrUsuarioRepository;
import com.qanteros.app.service.dto.UsrUsuarioDTO;
import com.qanteros.app.service.mapper.UsrUsuarioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UsrUsuario}.
 */
@Service
@Transactional
public class UsrUsuarioService {

    private final Logger log = LoggerFactory.getLogger(UsrUsuarioService.class);

    private final UsrUsuarioRepository usrUsuarioRepository;

    private final UsrUsuarioMapper usrUsuarioMapper;

    public UsrUsuarioService(UsrUsuarioRepository usrUsuarioRepository, UsrUsuarioMapper usrUsuarioMapper) {
        this.usrUsuarioRepository = usrUsuarioRepository;
        this.usrUsuarioMapper = usrUsuarioMapper;
    }

    /**
     * Save a usrUsuario.
     *
     * @param usrUsuarioDTO the entity to save.
     * @return the persisted entity.
     */
    public UsrUsuarioDTO save(UsrUsuarioDTO usrUsuarioDTO) {
        log.debug("Request to save UsrUsuario : {}", usrUsuarioDTO);
        UsrUsuario usrUsuario = usrUsuarioMapper.toEntity(usrUsuarioDTO);
        usrUsuario = usrUsuarioRepository.save(usrUsuario);
        return usrUsuarioMapper.toDto(usrUsuario);
    }

    /**
     * Get all the usrUsuarios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UsrUsuarioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UsrUsuarios");
        return usrUsuarioRepository.findAll(pageable)
            .map(usrUsuarioMapper::toDto);
    }


    /**
     * Get one usrUsuario by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UsrUsuarioDTO> findOne(Long id) {
        log.debug("Request to get UsrUsuario : {}", id);
        return usrUsuarioRepository.findById(id)
            .map(usrUsuarioMapper::toDto);
    }

    /**
     * Delete the usrUsuario by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UsrUsuario : {}", id);
        usrUsuarioRepository.deleteById(id);
    }
}
