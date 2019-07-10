package com.qanteros.app.web.rest;

import com.qanteros.app.service.AutUsuarioModuloService;
import com.qanteros.app.web.rest.errors.BadRequestAlertException;
import com.qanteros.app.service.dto.AutUsuarioModuloDTO;

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
 * REST controller for managing {@link com.qanteros.app.domain.AutUsuarioModulo}.
 */
@RestController
@RequestMapping("/api")
public class AutUsuarioModuloResource {

    private final Logger log = LoggerFactory.getLogger(AutUsuarioModuloResource.class);

    private static final String ENTITY_NAME = "qaccAutUsuarioModulo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AutUsuarioModuloService autUsuarioModuloService;

    public AutUsuarioModuloResource(AutUsuarioModuloService autUsuarioModuloService) {
        this.autUsuarioModuloService = autUsuarioModuloService;
    }

    /**
     * {@code POST  /aut-usuario-modulos} : Create a new autUsuarioModulo.
     *
     * @param autUsuarioModuloDTO the autUsuarioModuloDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new autUsuarioModuloDTO, or with status {@code 400 (Bad Request)} if the autUsuarioModulo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/aut-usuario-modulos")
    public ResponseEntity<AutUsuarioModuloDTO> createAutUsuarioModulo(@Valid @RequestBody AutUsuarioModuloDTO autUsuarioModuloDTO) throws URISyntaxException {
        log.debug("REST request to save AutUsuarioModulo : {}", autUsuarioModuloDTO);
        if (autUsuarioModuloDTO.getId() != null) {
            throw new BadRequestAlertException("A new autUsuarioModulo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AutUsuarioModuloDTO result = autUsuarioModuloService.save(autUsuarioModuloDTO);
        return ResponseEntity.created(new URI("/api/aut-usuario-modulos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /aut-usuario-modulos} : Updates an existing autUsuarioModulo.
     *
     * @param autUsuarioModuloDTO the autUsuarioModuloDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autUsuarioModuloDTO,
     * or with status {@code 400 (Bad Request)} if the autUsuarioModuloDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the autUsuarioModuloDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/aut-usuario-modulos")
    public ResponseEntity<AutUsuarioModuloDTO> updateAutUsuarioModulo(@Valid @RequestBody AutUsuarioModuloDTO autUsuarioModuloDTO) throws URISyntaxException {
        log.debug("REST request to update AutUsuarioModulo : {}", autUsuarioModuloDTO);
        if (autUsuarioModuloDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AutUsuarioModuloDTO result = autUsuarioModuloService.save(autUsuarioModuloDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, autUsuarioModuloDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /aut-usuario-modulos} : get all the autUsuarioModulos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of autUsuarioModulos in body.
     */
    @GetMapping("/aut-usuario-modulos")
    public List<AutUsuarioModuloDTO> getAllAutUsuarioModulos() {
        log.debug("REST request to get all AutUsuarioModulos");
        return autUsuarioModuloService.findAll();
    }

    /**
     * {@code GET  /aut-usuario-modulos/:id} : get the "id" autUsuarioModulo.
     *
     * @param id the id of the autUsuarioModuloDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the autUsuarioModuloDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/aut-usuario-modulos/{id}")
    public ResponseEntity<AutUsuarioModuloDTO> getAutUsuarioModulo(@PathVariable Long id) {
        log.debug("REST request to get AutUsuarioModulo : {}", id);
        Optional<AutUsuarioModuloDTO> autUsuarioModuloDTO = autUsuarioModuloService.findOne(id);
        return ResponseUtil.wrapOrNotFound(autUsuarioModuloDTO);
    }

    /**
     * {@code DELETE  /aut-usuario-modulos/:id} : delete the "id" autUsuarioModulo.
     *
     * @param id the id of the autUsuarioModuloDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/aut-usuario-modulos/{id}")
    public ResponseEntity<Void> deleteAutUsuarioModulo(@PathVariable Long id) {
        log.debug("REST request to delete AutUsuarioModulo : {}", id);
        autUsuarioModuloService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
