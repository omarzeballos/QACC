package com.qanteros.app.web.rest;

import com.qanteros.app.service.AutRolService;
import com.qanteros.app.web.rest.errors.BadRequestAlertException;
import com.qanteros.app.service.dto.AutRolDTO;

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
 * REST controller for managing {@link com.qanteros.app.domain.AutRol}.
 */
@RestController
@RequestMapping("/api")
public class AutRolResource {

    private final Logger log = LoggerFactory.getLogger(AutRolResource.class);

    private static final String ENTITY_NAME = "qaccAutRol";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AutRolService autRolService;

    public AutRolResource(AutRolService autRolService) {
        this.autRolService = autRolService;
    }

    /**
     * {@code POST  /aut-rols} : Create a new autRol.
     *
     * @param autRolDTO the autRolDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new autRolDTO, or with status {@code 400 (Bad Request)} if the autRol has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/aut-rols")
    public ResponseEntity<AutRolDTO> createAutRol(@Valid @RequestBody AutRolDTO autRolDTO) throws URISyntaxException {
        log.debug("REST request to save AutRol : {}", autRolDTO);
        if (autRolDTO.getId() != null) {
            throw new BadRequestAlertException("A new autRol cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AutRolDTO result = autRolService.save(autRolDTO);
        return ResponseEntity.created(new URI("/api/aut-rols/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /aut-rols} : Updates an existing autRol.
     *
     * @param autRolDTO the autRolDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autRolDTO,
     * or with status {@code 400 (Bad Request)} if the autRolDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the autRolDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/aut-rols")
    public ResponseEntity<AutRolDTO> updateAutRol(@Valid @RequestBody AutRolDTO autRolDTO) throws URISyntaxException {
        log.debug("REST request to update AutRol : {}", autRolDTO);
        if (autRolDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AutRolDTO result = autRolService.save(autRolDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, autRolDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /aut-rols} : get all the autRols.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of autRols in body.
     */
    @GetMapping("/aut-rols")
    public ResponseEntity<List<AutRolDTO>> getAllAutRols(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of AutRols");
        Page<AutRolDTO> page = autRolService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /aut-rols/:id} : get the "id" autRol.
     *
     * @param id the id of the autRolDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the autRolDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/aut-rols/{id}")
    public ResponseEntity<AutRolDTO> getAutRol(@PathVariable Long id) {
        log.debug("REST request to get AutRol : {}", id);
        Optional<AutRolDTO> autRolDTO = autRolService.findOne(id);
        return ResponseUtil.wrapOrNotFound(autRolDTO);
    }

    /**
     * {@code DELETE  /aut-rols/:id} : delete the "id" autRol.
     *
     * @param id the id of the autRolDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/aut-rols/{id}")
    public ResponseEntity<Void> deleteAutRol(@PathVariable Long id) {
        log.debug("REST request to delete AutRol : {}", id);
        autRolService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
