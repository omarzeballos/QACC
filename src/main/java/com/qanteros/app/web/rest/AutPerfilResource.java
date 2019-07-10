package com.qanteros.app.web.rest;

import com.qanteros.app.service.AutPerfilService;
import com.qanteros.app.web.rest.errors.BadRequestAlertException;
import com.qanteros.app.service.dto.AutPerfilDTO;

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
 * REST controller for managing {@link com.qanteros.app.domain.AutPerfil}.
 */
@RestController
@RequestMapping("/api")
public class AutPerfilResource {

    private final Logger log = LoggerFactory.getLogger(AutPerfilResource.class);

    private static final String ENTITY_NAME = "qaccAutPerfil";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AutPerfilService autPerfilService;

    public AutPerfilResource(AutPerfilService autPerfilService) {
        this.autPerfilService = autPerfilService;
    }

    /**
     * {@code POST  /aut-perfils} : Create a new autPerfil.
     *
     * @param autPerfilDTO the autPerfilDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new autPerfilDTO, or with status {@code 400 (Bad Request)} if the autPerfil has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/aut-perfils")
    public ResponseEntity<AutPerfilDTO> createAutPerfil(@Valid @RequestBody AutPerfilDTO autPerfilDTO) throws URISyntaxException {
        log.debug("REST request to save AutPerfil : {}", autPerfilDTO);
        if (autPerfilDTO.getId() != null) {
            throw new BadRequestAlertException("A new autPerfil cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AutPerfilDTO result = autPerfilService.save(autPerfilDTO);
        return ResponseEntity.created(new URI("/api/aut-perfils/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /aut-perfils} : Updates an existing autPerfil.
     *
     * @param autPerfilDTO the autPerfilDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autPerfilDTO,
     * or with status {@code 400 (Bad Request)} if the autPerfilDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the autPerfilDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/aut-perfils")
    public ResponseEntity<AutPerfilDTO> updateAutPerfil(@Valid @RequestBody AutPerfilDTO autPerfilDTO) throws URISyntaxException {
        log.debug("REST request to update AutPerfil : {}", autPerfilDTO);
        if (autPerfilDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AutPerfilDTO result = autPerfilService.save(autPerfilDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, autPerfilDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /aut-perfils} : get all the autPerfils.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of autPerfils in body.
     */
    @GetMapping("/aut-perfils")
    public ResponseEntity<List<AutPerfilDTO>> getAllAutPerfils(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of AutPerfils");
        Page<AutPerfilDTO> page = autPerfilService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /aut-perfils/:id} : get the "id" autPerfil.
     *
     * @param id the id of the autPerfilDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the autPerfilDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/aut-perfils/{id}")
    public ResponseEntity<AutPerfilDTO> getAutPerfil(@PathVariable Long id) {
        log.debug("REST request to get AutPerfil : {}", id);
        Optional<AutPerfilDTO> autPerfilDTO = autPerfilService.findOne(id);
        return ResponseUtil.wrapOrNotFound(autPerfilDTO);
    }

    /**
     * {@code DELETE  /aut-perfils/:id} : delete the "id" autPerfil.
     *
     * @param id the id of the autPerfilDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/aut-perfils/{id}")
    public ResponseEntity<Void> deleteAutPerfil(@PathVariable Long id) {
        log.debug("REST request to delete AutPerfil : {}", id);
        autPerfilService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
