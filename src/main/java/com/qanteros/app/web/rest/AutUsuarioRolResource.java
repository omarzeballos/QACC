package com.qanteros.app.web.rest;

import com.qanteros.app.service.AutUsuarioRolService;
import com.qanteros.app.web.rest.errors.BadRequestAlertException;
import com.qanteros.app.service.dto.AutUsuarioRolDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.qanteros.app.domain.AutUsuarioRol}.
 */
@RestController
@RequestMapping("/api")
public class AutUsuarioRolResource {

    private final Logger log = LoggerFactory.getLogger(AutUsuarioRolResource.class);

    private static final String ENTITY_NAME = "qaccAutUsuarioRol";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AutUsuarioRolService autUsuarioRolService;

    public AutUsuarioRolResource(AutUsuarioRolService autUsuarioRolService) {
        this.autUsuarioRolService = autUsuarioRolService;
    }

    /**
     * {@code POST  /aut-usuario-rols} : Create a new autUsuarioRol.
     *
     * @param autUsuarioRolDTO the autUsuarioRolDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new autUsuarioRolDTO, or with status {@code 400 (Bad Request)} if the autUsuarioRol has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/aut-usuario-rols")
    public ResponseEntity<AutUsuarioRolDTO> createAutUsuarioRol(@Valid @RequestBody AutUsuarioRolDTO autUsuarioRolDTO) throws URISyntaxException {
        log.debug("REST request to save AutUsuarioRol : {}", autUsuarioRolDTO);
        if (autUsuarioRolDTO.getId() != null) {
            throw new BadRequestAlertException("A new autUsuarioRol cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AutUsuarioRolDTO result = autUsuarioRolService.save(autUsuarioRolDTO);
        return ResponseEntity.created(new URI("/api/aut-usuario-rols/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /aut-usuario-rols} : Updates an existing autUsuarioRol.
     *
     * @param autUsuarioRolDTO the autUsuarioRolDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autUsuarioRolDTO,
     * or with status {@code 400 (Bad Request)} if the autUsuarioRolDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the autUsuarioRolDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/aut-usuario-rols")
    public ResponseEntity<AutUsuarioRolDTO> updateAutUsuarioRol(@Valid @RequestBody AutUsuarioRolDTO autUsuarioRolDTO) throws URISyntaxException {
        log.debug("REST request to update AutUsuarioRol : {}", autUsuarioRolDTO);
        if (autUsuarioRolDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AutUsuarioRolDTO result = autUsuarioRolService.save(autUsuarioRolDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, autUsuarioRolDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /aut-usuario-rols} : get all the autUsuarioRols.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of autUsuarioRols in body.
     */
    @GetMapping("/aut-usuario-rols")
    public List<AutUsuarioRolDTO> getAllAutUsuarioRols() {
        log.debug("REST request to get all AutUsuarioRols");
        return autUsuarioRolService.findAll();
    }

    /**
     * {@code GET  /aut-usuario-rols/:id} : get the "id" autUsuarioRol.
     *
     * @param id the id of the autUsuarioRolDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the autUsuarioRolDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/aut-usuario-rols/{id}")
    public ResponseEntity<AutUsuarioRolDTO> getAutUsuarioRol(@PathVariable Long id) {
        log.debug("REST request to get AutUsuarioRol : {}", id);
        Optional<AutUsuarioRolDTO> autUsuarioRolDTO = autUsuarioRolService.findOne(id);
        return ResponseUtil.wrapOrNotFound(autUsuarioRolDTO);
    }

    /**
     * {@code DELETE  /aut-usuario-rols/:id} : delete the "id" autUsuarioRol.
     *
     * @param id the id of the autUsuarioRolDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/aut-usuario-rols/{id}")
    public ResponseEntity<Void> deleteAutUsuarioRol(@PathVariable Long id) {
        log.debug("REST request to delete AutUsuarioRol : {}", id);
        autUsuarioRolService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
