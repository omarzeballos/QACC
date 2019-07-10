package com.qanteros.app.web.rest;

import com.qanteros.app.service.AutPerfilRolService;
import com.qanteros.app.web.rest.errors.BadRequestAlertException;
import com.qanteros.app.service.dto.AutPerfilRolDTO;

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
 * REST controller for managing {@link com.qanteros.app.domain.AutPerfilRol}.
 */
@RestController
@RequestMapping("/api")
public class AutPerfilRolResource {

    private final Logger log = LoggerFactory.getLogger(AutPerfilRolResource.class);

    private static final String ENTITY_NAME = "qaccAutPerfilRol";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AutPerfilRolService autPerfilRolService;

    public AutPerfilRolResource(AutPerfilRolService autPerfilRolService) {
        this.autPerfilRolService = autPerfilRolService;
    }

    /**
     * {@code POST  /aut-perfil-rols} : Create a new autPerfilRol.
     *
     * @param autPerfilRolDTO the autPerfilRolDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new autPerfilRolDTO, or with status {@code 400 (Bad Request)} if the autPerfilRol has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/aut-perfil-rols")
    public ResponseEntity<AutPerfilRolDTO> createAutPerfilRol(@Valid @RequestBody AutPerfilRolDTO autPerfilRolDTO) throws URISyntaxException {
        log.debug("REST request to save AutPerfilRol : {}", autPerfilRolDTO);
        if (autPerfilRolDTO.getId() != null) {
            throw new BadRequestAlertException("A new autPerfilRol cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AutPerfilRolDTO result = autPerfilRolService.save(autPerfilRolDTO);
        return ResponseEntity.created(new URI("/api/aut-perfil-rols/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /aut-perfil-rols} : Updates an existing autPerfilRol.
     *
     * @param autPerfilRolDTO the autPerfilRolDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autPerfilRolDTO,
     * or with status {@code 400 (Bad Request)} if the autPerfilRolDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the autPerfilRolDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/aut-perfil-rols")
    public ResponseEntity<AutPerfilRolDTO> updateAutPerfilRol(@Valid @RequestBody AutPerfilRolDTO autPerfilRolDTO) throws URISyntaxException {
        log.debug("REST request to update AutPerfilRol : {}", autPerfilRolDTO);
        if (autPerfilRolDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AutPerfilRolDTO result = autPerfilRolService.save(autPerfilRolDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, autPerfilRolDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /aut-perfil-rols} : get all the autPerfilRols.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of autPerfilRols in body.
     */
    @GetMapping("/aut-perfil-rols")
    public List<AutPerfilRolDTO> getAllAutPerfilRols() {
        log.debug("REST request to get all AutPerfilRols");
        return autPerfilRolService.findAll();
    }

    /**
     * {@code GET  /aut-perfil-rols/:id} : get the "id" autPerfilRol.
     *
     * @param id the id of the autPerfilRolDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the autPerfilRolDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/aut-perfil-rols/{id}")
    public ResponseEntity<AutPerfilRolDTO> getAutPerfilRol(@PathVariable Long id) {
        log.debug("REST request to get AutPerfilRol : {}", id);
        Optional<AutPerfilRolDTO> autPerfilRolDTO = autPerfilRolService.findOne(id);
        return ResponseUtil.wrapOrNotFound(autPerfilRolDTO);
    }

    /**
     * {@code DELETE  /aut-perfil-rols/:id} : delete the "id" autPerfilRol.
     *
     * @param id the id of the autPerfilRolDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/aut-perfil-rols/{id}")
    public ResponseEntity<Void> deleteAutPerfilRol(@PathVariable Long id) {
        log.debug("REST request to delete AutPerfilRol : {}", id);
        autPerfilRolService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
