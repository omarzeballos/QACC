package com.qanteros.app.web.rest;

import com.qanteros.app.service.AutRecursoDominioService;
import com.qanteros.app.web.rest.errors.BadRequestAlertException;
import com.qanteros.app.service.dto.AutRecursoDominioDTO;

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
 * REST controller for managing {@link com.qanteros.app.domain.AutRecursoDominio}.
 */
@RestController
@RequestMapping("/api")
public class AutRecursoDominioResource {

    private final Logger log = LoggerFactory.getLogger(AutRecursoDominioResource.class);

    private static final String ENTITY_NAME = "qaccAutRecursoDominio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AutRecursoDominioService autRecursoDominioService;

    public AutRecursoDominioResource(AutRecursoDominioService autRecursoDominioService) {
        this.autRecursoDominioService = autRecursoDominioService;
    }

    /**
     * {@code POST  /aut-recurso-dominios} : Create a new autRecursoDominio.
     *
     * @param autRecursoDominioDTO the autRecursoDominioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new autRecursoDominioDTO, or with status {@code 400 (Bad Request)} if the autRecursoDominio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/aut-recurso-dominios")
    public ResponseEntity<AutRecursoDominioDTO> createAutRecursoDominio(@Valid @RequestBody AutRecursoDominioDTO autRecursoDominioDTO) throws URISyntaxException {
        log.debug("REST request to save AutRecursoDominio : {}", autRecursoDominioDTO);
        if (autRecursoDominioDTO.getId() != null) {
            throw new BadRequestAlertException("A new autRecursoDominio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AutRecursoDominioDTO result = autRecursoDominioService.save(autRecursoDominioDTO);
        return ResponseEntity.created(new URI("/api/aut-recurso-dominios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /aut-recurso-dominios} : Updates an existing autRecursoDominio.
     *
     * @param autRecursoDominioDTO the autRecursoDominioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autRecursoDominioDTO,
     * or with status {@code 400 (Bad Request)} if the autRecursoDominioDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the autRecursoDominioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/aut-recurso-dominios")
    public ResponseEntity<AutRecursoDominioDTO> updateAutRecursoDominio(@Valid @RequestBody AutRecursoDominioDTO autRecursoDominioDTO) throws URISyntaxException {
        log.debug("REST request to update AutRecursoDominio : {}", autRecursoDominioDTO);
        if (autRecursoDominioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AutRecursoDominioDTO result = autRecursoDominioService.save(autRecursoDominioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, autRecursoDominioDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /aut-recurso-dominios} : get all the autRecursoDominios.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of autRecursoDominios in body.
     */
    @GetMapping("/aut-recurso-dominios")
    public List<AutRecursoDominioDTO> getAllAutRecursoDominios() {
        log.debug("REST request to get all AutRecursoDominios");
        return autRecursoDominioService.findAll();
    }

    /**
     * {@code GET  /aut-recurso-dominios/:id} : get the "id" autRecursoDominio.
     *
     * @param id the id of the autRecursoDominioDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the autRecursoDominioDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/aut-recurso-dominios/{id}")
    public ResponseEntity<AutRecursoDominioDTO> getAutRecursoDominio(@PathVariable Long id) {
        log.debug("REST request to get AutRecursoDominio : {}", id);
        Optional<AutRecursoDominioDTO> autRecursoDominioDTO = autRecursoDominioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(autRecursoDominioDTO);
    }

    /**
     * {@code DELETE  /aut-recurso-dominios/:id} : delete the "id" autRecursoDominio.
     *
     * @param id the id of the autRecursoDominioDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/aut-recurso-dominios/{id}")
    public ResponseEntity<Void> deleteAutRecursoDominio(@PathVariable Long id) {
        log.debug("REST request to delete AutRecursoDominio : {}", id);
        autRecursoDominioService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
