package com.qanteros.app.web.rest;

import com.qanteros.app.service.AutModuloService;
import com.qanteros.app.web.rest.errors.BadRequestAlertException;
import com.qanteros.app.service.dto.AutModuloDTO;

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
 * REST controller for managing {@link com.qanteros.app.domain.AutModulo}.
 */
@RestController
@RequestMapping("/api")
public class AutModuloResource {

    private final Logger log = LoggerFactory.getLogger(AutModuloResource.class);

    private static final String ENTITY_NAME = "qaccAutModulo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AutModuloService autModuloService;

    public AutModuloResource(AutModuloService autModuloService) {
        this.autModuloService = autModuloService;
    }

    /**
     * {@code POST  /aut-modulos} : Create a new autModulo.
     *
     * @param autModuloDTO the autModuloDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new autModuloDTO, or with status {@code 400 (Bad Request)} if the autModulo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/aut-modulos")
    public ResponseEntity<AutModuloDTO> createAutModulo(@Valid @RequestBody AutModuloDTO autModuloDTO) throws URISyntaxException {
        log.debug("REST request to save AutModulo : {}", autModuloDTO);
        if (autModuloDTO.getId() != null) {
            throw new BadRequestAlertException("A new autModulo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AutModuloDTO result = autModuloService.save(autModuloDTO);
        return ResponseEntity.created(new URI("/api/aut-modulos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /aut-modulos} : Updates an existing autModulo.
     *
     * @param autModuloDTO the autModuloDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autModuloDTO,
     * or with status {@code 400 (Bad Request)} if the autModuloDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the autModuloDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/aut-modulos")
    public ResponseEntity<AutModuloDTO> updateAutModulo(@Valid @RequestBody AutModuloDTO autModuloDTO) throws URISyntaxException {
        log.debug("REST request to update AutModulo : {}", autModuloDTO);
        if (autModuloDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AutModuloDTO result = autModuloService.save(autModuloDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, autModuloDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /aut-modulos} : get all the autModulos.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of autModulos in body.
     */
    @GetMapping("/aut-modulos")
    public ResponseEntity<List<AutModuloDTO>> getAllAutModulos(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of AutModulos");
        Page<AutModuloDTO> page = autModuloService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /aut-modulos/:id} : get the "id" autModulo.
     *
     * @param id the id of the autModuloDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the autModuloDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/aut-modulos/{id}")
    public ResponseEntity<AutModuloDTO> getAutModulo(@PathVariable Long id) {
        log.debug("REST request to get AutModulo : {}", id);
        Optional<AutModuloDTO> autModuloDTO = autModuloService.findOne(id);
        return ResponseUtil.wrapOrNotFound(autModuloDTO);
    }

    /**
     * {@code DELETE  /aut-modulos/:id} : delete the "id" autModulo.
     *
     * @param id the id of the autModuloDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/aut-modulos/{id}")
    public ResponseEntity<Void> deleteAutModulo(@PathVariable Long id) {
        log.debug("REST request to delete AutModulo : {}", id);
        autModuloService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
