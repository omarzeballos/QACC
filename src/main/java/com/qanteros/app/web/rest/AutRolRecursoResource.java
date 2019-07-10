package com.qanteros.app.web.rest;

import com.qanteros.app.service.AutRolRecursoService;
import com.qanteros.app.web.rest.errors.BadRequestAlertException;
import com.qanteros.app.service.dto.AutRolRecursoDTO;

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
 * REST controller for managing {@link com.qanteros.app.domain.AutRolRecurso}.
 */
@RestController
@RequestMapping("/api")
public class AutRolRecursoResource {

    private final Logger log = LoggerFactory.getLogger(AutRolRecursoResource.class);

    private static final String ENTITY_NAME = "qaccAutRolRecurso";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AutRolRecursoService autRolRecursoService;

    public AutRolRecursoResource(AutRolRecursoService autRolRecursoService) {
        this.autRolRecursoService = autRolRecursoService;
    }

    /**
     * {@code POST  /aut-rol-recursos} : Create a new autRolRecurso.
     *
     * @param autRolRecursoDTO the autRolRecursoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new autRolRecursoDTO, or with status {@code 400 (Bad Request)} if the autRolRecurso has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/aut-rol-recursos")
    public ResponseEntity<AutRolRecursoDTO> createAutRolRecurso(@Valid @RequestBody AutRolRecursoDTO autRolRecursoDTO) throws URISyntaxException {
        log.debug("REST request to save AutRolRecurso : {}", autRolRecursoDTO);
        if (autRolRecursoDTO.getId() != null) {
            throw new BadRequestAlertException("A new autRolRecurso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AutRolRecursoDTO result = autRolRecursoService.save(autRolRecursoDTO);
        return ResponseEntity.created(new URI("/api/aut-rol-recursos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /aut-rol-recursos} : Updates an existing autRolRecurso.
     *
     * @param autRolRecursoDTO the autRolRecursoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autRolRecursoDTO,
     * or with status {@code 400 (Bad Request)} if the autRolRecursoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the autRolRecursoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/aut-rol-recursos")
    public ResponseEntity<AutRolRecursoDTO> updateAutRolRecurso(@Valid @RequestBody AutRolRecursoDTO autRolRecursoDTO) throws URISyntaxException {
        log.debug("REST request to update AutRolRecurso : {}", autRolRecursoDTO);
        if (autRolRecursoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AutRolRecursoDTO result = autRolRecursoService.save(autRolRecursoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, autRolRecursoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /aut-rol-recursos} : get all the autRolRecursos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of autRolRecursos in body.
     */
    @GetMapping("/aut-rol-recursos")
    public List<AutRolRecursoDTO> getAllAutRolRecursos() {
        log.debug("REST request to get all AutRolRecursos");
        return autRolRecursoService.findAll();
    }

    /**
     * {@code GET  /aut-rol-recursos/:id} : get the "id" autRolRecurso.
     *
     * @param id the id of the autRolRecursoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the autRolRecursoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/aut-rol-recursos/{id}")
    public ResponseEntity<AutRolRecursoDTO> getAutRolRecurso(@PathVariable Long id) {
        log.debug("REST request to get AutRolRecurso : {}", id);
        Optional<AutRolRecursoDTO> autRolRecursoDTO = autRolRecursoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(autRolRecursoDTO);
    }

    /**
     * {@code DELETE  /aut-rol-recursos/:id} : delete the "id" autRolRecurso.
     *
     * @param id the id of the autRolRecursoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/aut-rol-recursos/{id}")
    public ResponseEntity<Void> deleteAutRolRecurso(@PathVariable Long id) {
        log.debug("REST request to delete AutRolRecurso : {}", id);
        autRolRecursoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
