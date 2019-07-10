package com.qanteros.app.web.rest;

import com.qanteros.app.service.UsrUsuarioService;
import com.qanteros.app.web.rest.errors.BadRequestAlertException;
import com.qanteros.app.service.dto.UsrUsuarioDTO;

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
 * REST controller for managing {@link com.qanteros.app.domain.UsrUsuario}.
 */
@RestController
@RequestMapping("/api")
public class UsrUsuarioResource {

    private final Logger log = LoggerFactory.getLogger(UsrUsuarioResource.class);

    private static final String ENTITY_NAME = "qaccUsrUsuario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UsrUsuarioService usrUsuarioService;

    public UsrUsuarioResource(UsrUsuarioService usrUsuarioService) {
        this.usrUsuarioService = usrUsuarioService;
    }

    /**
     * {@code POST  /usr-usuarios} : Create a new usrUsuario.
     *
     * @param usrUsuarioDTO the usrUsuarioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new usrUsuarioDTO, or with status {@code 400 (Bad Request)} if the usrUsuario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/usr-usuarios")
    public ResponseEntity<UsrUsuarioDTO> createUsrUsuario(@Valid @RequestBody UsrUsuarioDTO usrUsuarioDTO) throws URISyntaxException {
        log.debug("REST request to save UsrUsuario : {}", usrUsuarioDTO);
        if (usrUsuarioDTO.getId() != null) {
            throw new BadRequestAlertException("A new usrUsuario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UsrUsuarioDTO result = usrUsuarioService.save(usrUsuarioDTO);
        return ResponseEntity.created(new URI("/api/usr-usuarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /usr-usuarios} : Updates an existing usrUsuario.
     *
     * @param usrUsuarioDTO the usrUsuarioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usrUsuarioDTO,
     * or with status {@code 400 (Bad Request)} if the usrUsuarioDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the usrUsuarioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/usr-usuarios")
    public ResponseEntity<UsrUsuarioDTO> updateUsrUsuario(@Valid @RequestBody UsrUsuarioDTO usrUsuarioDTO) throws URISyntaxException {
        log.debug("REST request to update UsrUsuario : {}", usrUsuarioDTO);
        if (usrUsuarioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UsrUsuarioDTO result = usrUsuarioService.save(usrUsuarioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, usrUsuarioDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /usr-usuarios} : get all the usrUsuarios.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of usrUsuarios in body.
     */
    @GetMapping("/usr-usuarios")
    public ResponseEntity<List<UsrUsuarioDTO>> getAllUsrUsuarios(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of UsrUsuarios");
        Page<UsrUsuarioDTO> page = usrUsuarioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /usr-usuarios/:id} : get the "id" usrUsuario.
     *
     * @param id the id of the usrUsuarioDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the usrUsuarioDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/usr-usuarios/{id}")
    public ResponseEntity<UsrUsuarioDTO> getUsrUsuario(@PathVariable Long id) {
        log.debug("REST request to get UsrUsuario : {}", id);
        Optional<UsrUsuarioDTO> usrUsuarioDTO = usrUsuarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(usrUsuarioDTO);
    }

    /**
     * {@code DELETE  /usr-usuarios/:id} : delete the "id" usrUsuario.
     *
     * @param id the id of the usrUsuarioDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/usr-usuarios/{id}")
    public ResponseEntity<Void> deleteUsrUsuario(@PathVariable Long id) {
        log.debug("REST request to delete UsrUsuario : {}", id);
        usrUsuarioService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
