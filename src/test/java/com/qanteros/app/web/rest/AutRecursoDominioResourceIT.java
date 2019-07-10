package com.qanteros.app.web.rest;

import com.qanteros.app.QaccApp;
import com.qanteros.app.domain.AutRecursoDominio;
import com.qanteros.app.repository.AutRecursoDominioRepository;
import com.qanteros.app.service.AutRecursoDominioService;
import com.qanteros.app.service.dto.AutRecursoDominioDTO;
import com.qanteros.app.service.mapper.AutRecursoDominioMapper;
import com.qanteros.app.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.qanteros.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.qanteros.app.domain.enumeration.Estado;
/**
 * Integration tests for the {@Link AutRecursoDominioResource} REST controller.
 */
@SpringBootTest(classes = QaccApp.class)
public class AutRecursoDominioResourceIT {

    private static final Long DEFAULT_ID_RECURSO = 1L;
    private static final Long UPDATED_ID_RECURSO = 2L;

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Estado DEFAULT_ESTADO = Estado.ACTIVO;
    private static final Estado UPDATED_ESTADO = Estado.INACTIVO;

    private static final String DEFAULT_OPEUSR = "AAAAAAAAAA";
    private static final String UPDATED_OPEUSR = "BBBBBBBBBB";

    private static final Instant DEFAULT_OPEFECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_OPEFECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_OPEOPE = "AAAAAAAAAA";
    private static final String UPDATED_OPEOPE = "BBBBBBBBBB";

    @Autowired
    private AutRecursoDominioRepository autRecursoDominioRepository;

    @Autowired
    private AutRecursoDominioMapper autRecursoDominioMapper;

    @Autowired
    private AutRecursoDominioService autRecursoDominioService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAutRecursoDominioMockMvc;

    private AutRecursoDominio autRecursoDominio;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AutRecursoDominioResource autRecursoDominioResource = new AutRecursoDominioResource(autRecursoDominioService);
        this.restAutRecursoDominioMockMvc = MockMvcBuilders.standaloneSetup(autRecursoDominioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AutRecursoDominio createEntity(EntityManager em) {
        AutRecursoDominio autRecursoDominio = new AutRecursoDominio()
            .idRecurso(DEFAULT_ID_RECURSO)
            .nombre(DEFAULT_NOMBRE)
            .estado(DEFAULT_ESTADO)
            .opeusr(DEFAULT_OPEUSR)
            .opefecha(DEFAULT_OPEFECHA)
            .opeope(DEFAULT_OPEOPE);
        return autRecursoDominio;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AutRecursoDominio createUpdatedEntity(EntityManager em) {
        AutRecursoDominio autRecursoDominio = new AutRecursoDominio()
            .idRecurso(UPDATED_ID_RECURSO)
            .nombre(UPDATED_NOMBRE)
            .estado(UPDATED_ESTADO)
            .opeusr(UPDATED_OPEUSR)
            .opefecha(UPDATED_OPEFECHA)
            .opeope(UPDATED_OPEOPE);
        return autRecursoDominio;
    }

    @BeforeEach
    public void initTest() {
        autRecursoDominio = createEntity(em);
    }

    @Test
    @Transactional
    public void createAutRecursoDominio() throws Exception {
        int databaseSizeBeforeCreate = autRecursoDominioRepository.findAll().size();

        // Create the AutRecursoDominio
        AutRecursoDominioDTO autRecursoDominioDTO = autRecursoDominioMapper.toDto(autRecursoDominio);
        restAutRecursoDominioMockMvc.perform(post("/api/aut-recurso-dominios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRecursoDominioDTO)))
            .andExpect(status().isCreated());

        // Validate the AutRecursoDominio in the database
        List<AutRecursoDominio> autRecursoDominioList = autRecursoDominioRepository.findAll();
        assertThat(autRecursoDominioList).hasSize(databaseSizeBeforeCreate + 1);
        AutRecursoDominio testAutRecursoDominio = autRecursoDominioList.get(autRecursoDominioList.size() - 1);
        assertThat(testAutRecursoDominio.getIdRecurso()).isEqualTo(DEFAULT_ID_RECURSO);
        assertThat(testAutRecursoDominio.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testAutRecursoDominio.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testAutRecursoDominio.getOpeusr()).isEqualTo(DEFAULT_OPEUSR);
        assertThat(testAutRecursoDominio.getOpefecha()).isEqualTo(DEFAULT_OPEFECHA);
        assertThat(testAutRecursoDominio.getOpeope()).isEqualTo(DEFAULT_OPEOPE);
    }

    @Test
    @Transactional
    public void createAutRecursoDominioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = autRecursoDominioRepository.findAll().size();

        // Create the AutRecursoDominio with an existing ID
        autRecursoDominio.setId(1L);
        AutRecursoDominioDTO autRecursoDominioDTO = autRecursoDominioMapper.toDto(autRecursoDominio);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutRecursoDominioMockMvc.perform(post("/api/aut-recurso-dominios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRecursoDominioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AutRecursoDominio in the database
        List<AutRecursoDominio> autRecursoDominioList = autRecursoDominioRepository.findAll();
        assertThat(autRecursoDominioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdRecursoIsRequired() throws Exception {
        int databaseSizeBeforeTest = autRecursoDominioRepository.findAll().size();
        // set the field null
        autRecursoDominio.setIdRecurso(null);

        // Create the AutRecursoDominio, which fails.
        AutRecursoDominioDTO autRecursoDominioDTO = autRecursoDominioMapper.toDto(autRecursoDominio);

        restAutRecursoDominioMockMvc.perform(post("/api/aut-recurso-dominios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRecursoDominioDTO)))
            .andExpect(status().isBadRequest());

        List<AutRecursoDominio> autRecursoDominioList = autRecursoDominioRepository.findAll();
        assertThat(autRecursoDominioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = autRecursoDominioRepository.findAll().size();
        // set the field null
        autRecursoDominio.setNombre(null);

        // Create the AutRecursoDominio, which fails.
        AutRecursoDominioDTO autRecursoDominioDTO = autRecursoDominioMapper.toDto(autRecursoDominio);

        restAutRecursoDominioMockMvc.perform(post("/api/aut-recurso-dominios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRecursoDominioDTO)))
            .andExpect(status().isBadRequest());

        List<AutRecursoDominio> autRecursoDominioList = autRecursoDominioRepository.findAll();
        assertThat(autRecursoDominioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = autRecursoDominioRepository.findAll().size();
        // set the field null
        autRecursoDominio.setEstado(null);

        // Create the AutRecursoDominio, which fails.
        AutRecursoDominioDTO autRecursoDominioDTO = autRecursoDominioMapper.toDto(autRecursoDominio);

        restAutRecursoDominioMockMvc.perform(post("/api/aut-recurso-dominios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRecursoDominioDTO)))
            .andExpect(status().isBadRequest());

        List<AutRecursoDominio> autRecursoDominioList = autRecursoDominioRepository.findAll();
        assertThat(autRecursoDominioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOpeusrIsRequired() throws Exception {
        int databaseSizeBeforeTest = autRecursoDominioRepository.findAll().size();
        // set the field null
        autRecursoDominio.setOpeusr(null);

        // Create the AutRecursoDominio, which fails.
        AutRecursoDominioDTO autRecursoDominioDTO = autRecursoDominioMapper.toDto(autRecursoDominio);

        restAutRecursoDominioMockMvc.perform(post("/api/aut-recurso-dominios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRecursoDominioDTO)))
            .andExpect(status().isBadRequest());

        List<AutRecursoDominio> autRecursoDominioList = autRecursoDominioRepository.findAll();
        assertThat(autRecursoDominioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOpefechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = autRecursoDominioRepository.findAll().size();
        // set the field null
        autRecursoDominio.setOpefecha(null);

        // Create the AutRecursoDominio, which fails.
        AutRecursoDominioDTO autRecursoDominioDTO = autRecursoDominioMapper.toDto(autRecursoDominio);

        restAutRecursoDominioMockMvc.perform(post("/api/aut-recurso-dominios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRecursoDominioDTO)))
            .andExpect(status().isBadRequest());

        List<AutRecursoDominio> autRecursoDominioList = autRecursoDominioRepository.findAll();
        assertThat(autRecursoDominioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOpeopeIsRequired() throws Exception {
        int databaseSizeBeforeTest = autRecursoDominioRepository.findAll().size();
        // set the field null
        autRecursoDominio.setOpeope(null);

        // Create the AutRecursoDominio, which fails.
        AutRecursoDominioDTO autRecursoDominioDTO = autRecursoDominioMapper.toDto(autRecursoDominio);

        restAutRecursoDominioMockMvc.perform(post("/api/aut-recurso-dominios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRecursoDominioDTO)))
            .andExpect(status().isBadRequest());

        List<AutRecursoDominio> autRecursoDominioList = autRecursoDominioRepository.findAll();
        assertThat(autRecursoDominioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAutRecursoDominios() throws Exception {
        // Initialize the database
        autRecursoDominioRepository.saveAndFlush(autRecursoDominio);

        // Get all the autRecursoDominioList
        restAutRecursoDominioMockMvc.perform(get("/api/aut-recurso-dominios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autRecursoDominio.getId().intValue())))
            .andExpect(jsonPath("$.[*].idRecurso").value(hasItem(DEFAULT_ID_RECURSO.intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].opeusr").value(hasItem(DEFAULT_OPEUSR.toString())))
            .andExpect(jsonPath("$.[*].opefecha").value(hasItem(DEFAULT_OPEFECHA.toString())))
            .andExpect(jsonPath("$.[*].opeope").value(hasItem(DEFAULT_OPEOPE.toString())));
    }
    
    @Test
    @Transactional
    public void getAutRecursoDominio() throws Exception {
        // Initialize the database
        autRecursoDominioRepository.saveAndFlush(autRecursoDominio);

        // Get the autRecursoDominio
        restAutRecursoDominioMockMvc.perform(get("/api/aut-recurso-dominios/{id}", autRecursoDominio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(autRecursoDominio.getId().intValue()))
            .andExpect(jsonPath("$.idRecurso").value(DEFAULT_ID_RECURSO.intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.opeusr").value(DEFAULT_OPEUSR.toString()))
            .andExpect(jsonPath("$.opefecha").value(DEFAULT_OPEFECHA.toString()))
            .andExpect(jsonPath("$.opeope").value(DEFAULT_OPEOPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAutRecursoDominio() throws Exception {
        // Get the autRecursoDominio
        restAutRecursoDominioMockMvc.perform(get("/api/aut-recurso-dominios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAutRecursoDominio() throws Exception {
        // Initialize the database
        autRecursoDominioRepository.saveAndFlush(autRecursoDominio);

        int databaseSizeBeforeUpdate = autRecursoDominioRepository.findAll().size();

        // Update the autRecursoDominio
        AutRecursoDominio updatedAutRecursoDominio = autRecursoDominioRepository.findById(autRecursoDominio.getId()).get();
        // Disconnect from session so that the updates on updatedAutRecursoDominio are not directly saved in db
        em.detach(updatedAutRecursoDominio);
        updatedAutRecursoDominio
            .idRecurso(UPDATED_ID_RECURSO)
            .nombre(UPDATED_NOMBRE)
            .estado(UPDATED_ESTADO)
            .opeusr(UPDATED_OPEUSR)
            .opefecha(UPDATED_OPEFECHA)
            .opeope(UPDATED_OPEOPE);
        AutRecursoDominioDTO autRecursoDominioDTO = autRecursoDominioMapper.toDto(updatedAutRecursoDominio);

        restAutRecursoDominioMockMvc.perform(put("/api/aut-recurso-dominios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRecursoDominioDTO)))
            .andExpect(status().isOk());

        // Validate the AutRecursoDominio in the database
        List<AutRecursoDominio> autRecursoDominioList = autRecursoDominioRepository.findAll();
        assertThat(autRecursoDominioList).hasSize(databaseSizeBeforeUpdate);
        AutRecursoDominio testAutRecursoDominio = autRecursoDominioList.get(autRecursoDominioList.size() - 1);
        assertThat(testAutRecursoDominio.getIdRecurso()).isEqualTo(UPDATED_ID_RECURSO);
        assertThat(testAutRecursoDominio.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testAutRecursoDominio.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testAutRecursoDominio.getOpeusr()).isEqualTo(UPDATED_OPEUSR);
        assertThat(testAutRecursoDominio.getOpefecha()).isEqualTo(UPDATED_OPEFECHA);
        assertThat(testAutRecursoDominio.getOpeope()).isEqualTo(UPDATED_OPEOPE);
    }

    @Test
    @Transactional
    public void updateNonExistingAutRecursoDominio() throws Exception {
        int databaseSizeBeforeUpdate = autRecursoDominioRepository.findAll().size();

        // Create the AutRecursoDominio
        AutRecursoDominioDTO autRecursoDominioDTO = autRecursoDominioMapper.toDto(autRecursoDominio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutRecursoDominioMockMvc.perform(put("/api/aut-recurso-dominios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRecursoDominioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AutRecursoDominio in the database
        List<AutRecursoDominio> autRecursoDominioList = autRecursoDominioRepository.findAll();
        assertThat(autRecursoDominioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAutRecursoDominio() throws Exception {
        // Initialize the database
        autRecursoDominioRepository.saveAndFlush(autRecursoDominio);

        int databaseSizeBeforeDelete = autRecursoDominioRepository.findAll().size();

        // Delete the autRecursoDominio
        restAutRecursoDominioMockMvc.perform(delete("/api/aut-recurso-dominios/{id}", autRecursoDominio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AutRecursoDominio> autRecursoDominioList = autRecursoDominioRepository.findAll();
        assertThat(autRecursoDominioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutRecursoDominio.class);
        AutRecursoDominio autRecursoDominio1 = new AutRecursoDominio();
        autRecursoDominio1.setId(1L);
        AutRecursoDominio autRecursoDominio2 = new AutRecursoDominio();
        autRecursoDominio2.setId(autRecursoDominio1.getId());
        assertThat(autRecursoDominio1).isEqualTo(autRecursoDominio2);
        autRecursoDominio2.setId(2L);
        assertThat(autRecursoDominio1).isNotEqualTo(autRecursoDominio2);
        autRecursoDominio1.setId(null);
        assertThat(autRecursoDominio1).isNotEqualTo(autRecursoDominio2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutRecursoDominioDTO.class);
        AutRecursoDominioDTO autRecursoDominioDTO1 = new AutRecursoDominioDTO();
        autRecursoDominioDTO1.setId(1L);
        AutRecursoDominioDTO autRecursoDominioDTO2 = new AutRecursoDominioDTO();
        assertThat(autRecursoDominioDTO1).isNotEqualTo(autRecursoDominioDTO2);
        autRecursoDominioDTO2.setId(autRecursoDominioDTO1.getId());
        assertThat(autRecursoDominioDTO1).isEqualTo(autRecursoDominioDTO2);
        autRecursoDominioDTO2.setId(2L);
        assertThat(autRecursoDominioDTO1).isNotEqualTo(autRecursoDominioDTO2);
        autRecursoDominioDTO1.setId(null);
        assertThat(autRecursoDominioDTO1).isNotEqualTo(autRecursoDominioDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(autRecursoDominioMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(autRecursoDominioMapper.fromId(null)).isNull();
    }
}
