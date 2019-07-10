package com.qanteros.app.web.rest;

import com.qanteros.app.service.AutRecursoService;
import com.qanteros.app.web.rest.errors.BadRequestAlertException;
import com.qanteros.app.service.dto.AutRecursoDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.qanteros.app.domain.AutRecurso}.
 */
@RestController
@RequestMapping("/api")
public class AutRecursoResource {

    private final Logger log = LoggerFactory.getLogger(AutRecursoResource.class);

    private static final String ENTITY_NAME = "qaccAutRecurso";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AutRecursoService autRecursoService;

    public AutRecursoResource(AutRecursoService autRecursoService) {
        this.autRecursoService = autRecursoService;
    }

    /**
     * {@code POST  /aut-recursos} : Create a new autRecurso.
     *
     * @param autRecursoDTO the autRecursoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new autRecursoDTO, or with status {@code 400 (Bad Request)} if the autRecurso has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/aut-recursos")
    public ResponseEntity<AutRecursoDTO> createAutRecurso(@Valid @RequestBody AutRecursoDTO autRecursoDTO) throws URISyntaxException {
        log.debug("REST request to save AutRecurso : {}", autRecursoDTO);
        if (autRecursoDTO.getId() != null) {
            throw new BadRequestAlertException("A new autRecurso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AutRecursoDTO result = autRecursoService.save(autRecursoDTO);
        return ResponseEntity.created(new URI("/api/aut-recursos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /aut-recursos} : Updates an existing autRecurso.
     *
     * @param autRecursoDTO the autRecursoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autRecursoDTO,
     * or with status {@code 400 (Bad Request)} if the autRecursoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the autRecursoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/aut-recursos")
    public ResponseEntity<AutRecursoDTO> updateAutRecurso(@Valid @RequestBody AutRecursoDTO autRecursoDTO) throws URISyntaxException {
        log.debug("REST request to update AutRecurso : {}", autRecursoDTO);
        if (autRecursoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AutRecursoDTO result = autRecursoService.save(autRecursoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, autRecursoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /aut-recursos} : get all the autRecursos.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of autRecursos in body.
     */
    @GetMapping("/aut-recursos")
    public ResponseEntity<List<AutRecursoDTO>> getAllAutRecursos(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of AutRecursos");
        Page<AutRecursoDTO> page = autRecursoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /aut-recursos/:id} : get the "id" autRecurso.
     *
     * @param id the id of the autRecursoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the autRecursoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/aut-recursos/{id}")
    public ResponseEntity<AutRecursoDTO> getAutRecurso(@PathVariable Long id) {
        log.debug("REST request to get AutRecurso : {}", id);
        Optional<AutRecursoDTO> autRecursoDTO = autRecursoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(autRecursoDTO);
    }

    /**
     * {@code DELETE  /aut-recursos/:id} : delete the "id" autRecurso.
     *
     * @param id the id of the autRecursoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/aut-recursos/{id}")
    public ResponseEntity<Void> deleteAutRecurso(@PathVariable Long id) {
        log.debug("REST request to delete AutRecurso : {}", id);
        autRecursoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
