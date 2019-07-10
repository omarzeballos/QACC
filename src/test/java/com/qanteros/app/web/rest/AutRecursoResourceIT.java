package com.qanteros.app.web.rest;

import com.qanteros.app.QaccApp;
import com.qanteros.app.domain.AutRecurso;
import com.qanteros.app.repository.AutRecursoRepository;
import com.qanteros.app.service.AutRecursoService;
import com.qanteros.app.service.dto.AutRecursoDTO;
import com.qanteros.app.service.mapper.AutRecursoMapper;
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
 * Integration tests for the {@Link AutRecursoResource} REST controller.
 */
@SpringBootTest(classes = QaccApp.class)
public class AutRecursoResourceIT {

    private static final Long DEFAULT_ID_RECURSO = 1L;
    private static final Long UPDATED_ID_RECURSO = 2L;

    private static final Long DEFAULT_ID_RECURSO_DOMINIO = 1L;
    private static final Long UPDATED_ID_RECURSO_DOMINIO = 2L;

    private static final Long DEFAULT_ID_RECURSO_PADRE = 1L;
    private static final Long UPDATED_ID_RECURSO_PADRE = 2L;

    private static final Long DEFAULT_ID_MODULO = 1L;
    private static final Long UPDATED_ID_MODULO = 2L;

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
    private AutRecursoRepository autRecursoRepository;

    @Autowired
    private AutRecursoMapper autRecursoMapper;

    @Autowired
    private AutRecursoService autRecursoService;

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

    private MockMvc restAutRecursoMockMvc;

    private AutRecurso autRecurso;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AutRecursoResource autRecursoResource = new AutRecursoResource(autRecursoService);
        this.restAutRecursoMockMvc = MockMvcBuilders.standaloneSetup(autRecursoResource)
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
    public static AutRecurso createEntity(EntityManager em) {
        AutRecurso autRecurso = new AutRecurso()
            .idRecurso(DEFAULT_ID_RECURSO)
            .idRecursoDominio(DEFAULT_ID_RECURSO_DOMINIO)
            .idRecursoPadre(DEFAULT_ID_RECURSO_PADRE)
            .idModulo(DEFAULT_ID_MODULO)
            .nombre(DEFAULT_NOMBRE)
            .estado(DEFAULT_ESTADO)
            .opeusr(DEFAULT_OPEUSR)
            .opefecha(DEFAULT_OPEFECHA)
            .opeope(DEFAULT_OPEOPE);
        return autRecurso;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AutRecurso createUpdatedEntity(EntityManager em) {
        AutRecurso autRecurso = new AutRecurso()
            .idRecurso(UPDATED_ID_RECURSO)
            .idRecursoDominio(UPDATED_ID_RECURSO_DOMINIO)
            .idRecursoPadre(UPDATED_ID_RECURSO_PADRE)
            .idModulo(UPDATED_ID_MODULO)
            .nombre(UPDATED_NOMBRE)
            .estado(UPDATED_ESTADO)
            .opeusr(UPDATED_OPEUSR)
            .opefecha(UPDATED_OPEFECHA)
            .opeope(UPDATED_OPEOPE);
        return autRecurso;
    }

    @BeforeEach
    public void initTest() {
        autRecurso = createEntity(em);
    }

    @Test
    @Transactional
    public void createAutRecurso() throws Exception {
        int databaseSizeBeforeCreate = autRecursoRepository.findAll().size();

        // Create the AutRecurso
        AutRecursoDTO autRecursoDTO = autRecursoMapper.toDto(autRecurso);
        restAutRecursoMockMvc.perform(post("/api/aut-recursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRecursoDTO)))
            .andExpect(status().isCreated());

        // Validate the AutRecurso in the database
        List<AutRecurso> autRecursoList = autRecursoRepository.findAll();
        assertThat(autRecursoList).hasSize(databaseSizeBeforeCreate + 1);
        AutRecurso testAutRecurso = autRecursoList.get(autRecursoList.size() - 1);
        assertThat(testAutRecurso.getIdRecurso()).isEqualTo(DEFAULT_ID_RECURSO);
        assertThat(testAutRecurso.getIdRecursoDominio()).isEqualTo(DEFAULT_ID_RECURSO_DOMINIO);
        assertThat(testAutRecurso.getIdRecursoPadre()).isEqualTo(DEFAULT_ID_RECURSO_PADRE);
        assertThat(testAutRecurso.getIdModulo()).isEqualTo(DEFAULT_ID_MODULO);
        assertThat(testAutRecurso.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testAutRecurso.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testAutRecurso.getOpeusr()).isEqualTo(DEFAULT_OPEUSR);
        assertThat(testAutRecurso.getOpefecha()).isEqualTo(DEFAULT_OPEFECHA);
        assertThat(testAutRecurso.getOpeope()).isEqualTo(DEFAULT_OPEOPE);
    }

    @Test
    @Transactional
    public void createAutRecursoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = autRecursoRepository.findAll().size();

        // Create the AutRecurso with an existing ID
        autRecurso.setId(1L);
        AutRecursoDTO autRecursoDTO = autRecursoMapper.toDto(autRecurso);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutRecursoMockMvc.perform(post("/api/aut-recursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRecursoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AutRecurso in the database
        List<AutRecurso> autRecursoList = autRecursoRepository.findAll();
        assertThat(autRecursoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdRecursoIsRequired() throws Exception {
        int databaseSizeBeforeTest = autRecursoRepository.findAll().size();
        // set the field null
        autRecurso.setIdRecurso(null);

        // Create the AutRecurso, which fails.
        AutRecursoDTO autRecursoDTO = autRecursoMapper.toDto(autRecurso);

        restAutRecursoMockMvc.perform(post("/api/aut-recursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRecursoDTO)))
            .andExpect(status().isBadRequest());

        List<AutRecurso> autRecursoList = autRecursoRepository.findAll();
        assertThat(autRecursoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdRecursoDominioIsRequired() throws Exception {
        int databaseSizeBeforeTest = autRecursoRepository.findAll().size();
        // set the field null
        autRecurso.setIdRecursoDominio(null);

        // Create the AutRecurso, which fails.
        AutRecursoDTO autRecursoDTO = autRecursoMapper.toDto(autRecurso);

        restAutRecursoMockMvc.perform(post("/api/aut-recursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRecursoDTO)))
            .andExpect(status().isBadRequest());

        List<AutRecurso> autRecursoList = autRecursoRepository.findAll();
        assertThat(autRecursoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdModuloIsRequired() throws Exception {
        int databaseSizeBeforeTest = autRecursoRepository.findAll().size();
        // set the field null
        autRecurso.setIdModulo(null);

        // Create the AutRecurso, which fails.
        AutRecursoDTO autRecursoDTO = autRecursoMapper.toDto(autRecurso);

        restAutRecursoMockMvc.perform(post("/api/aut-recursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRecursoDTO)))
            .andExpect(status().isBadRequest());

        List<AutRecurso> autRecursoList = autRecursoRepository.findAll();
        assertThat(autRecursoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = autRecursoRepository.findAll().size();
        // set the field null
        autRecurso.setNombre(null);

        // Create the AutRecurso, which fails.
        AutRecursoDTO autRecursoDTO = autRecursoMapper.toDto(autRecurso);

        restAutRecursoMockMvc.perform(post("/api/aut-recursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRecursoDTO)))
            .andExpect(status().isBadRequest());

        List<AutRecurso> autRecursoList = autRecursoRepository.findAll();
        assertThat(autRecursoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = autRecursoRepository.findAll().size();
        // set the field null
        autRecurso.setEstado(null);

        // Create the AutRecurso, which fails.
        AutRecursoDTO autRecursoDTO = autRecursoMapper.toDto(autRecurso);

        restAutRecursoMockMvc.perform(post("/api/aut-recursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRecursoDTO)))
            .andExpect(status().isBadRequest());

        List<AutRecurso> autRecursoList = autRecursoRepository.findAll();
        assertThat(autRecursoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOpeusrIsRequired() throws Exception {
        int databaseSizeBeforeTest = autRecursoRepository.findAll().size();
        // set the field null
        autRecurso.setOpeusr(null);

        // Create the AutRecurso, which fails.
        AutRecursoDTO autRecursoDTO = autRecursoMapper.toDto(autRecurso);

        restAutRecursoMockMvc.perform(post("/api/aut-recursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRecursoDTO)))
            .andExpect(status().isBadRequest());

        List<AutRecurso> autRecursoList = autRecursoRepository.findAll();
        assertThat(autRecursoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOpefechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = autRecursoRepository.findAll().size();
        // set the field null
        autRecurso.setOpefecha(null);

        // Create the AutRecurso, which fails.
        AutRecursoDTO autRecursoDTO = autRecursoMapper.toDto(autRecurso);

        restAutRecursoMockMvc.perform(post("/api/aut-recursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRecursoDTO)))
            .andExpect(status().isBadRequest());

        List<AutRecurso> autRecursoList = autRecursoRepository.findAll();
        assertThat(autRecursoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOpeopeIsRequired() throws Exception {
        int databaseSizeBeforeTest = autRecursoRepository.findAll().size();
        // set the field null
        autRecurso.setOpeope(null);

        // Create the AutRecurso, which fails.
        AutRecursoDTO autRecursoDTO = autRecursoMapper.toDto(autRecurso);

        restAutRecursoMockMvc.perform(post("/api/aut-recursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRecursoDTO)))
            .andExpect(status().isBadRequest());

        List<AutRecurso> autRecursoList = autRecursoRepository.findAll();
        assertThat(autRecursoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAutRecursos() throws Exception {
        // Initialize the database
        autRecursoRepository.saveAndFlush(autRecurso);

        // Get all the autRecursoList
        restAutRecursoMockMvc.perform(get("/api/aut-recursos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autRecurso.getId().intValue())))
            .andExpect(jsonPath("$.[*].idRecurso").value(hasItem(DEFAULT_ID_RECURSO.intValue())))
            .andExpect(jsonPath("$.[*].idRecursoDominio").value(hasItem(DEFAULT_ID_RECURSO_DOMINIO.intValue())))
            .andExpect(jsonPath("$.[*].idRecursoPadre").value(hasItem(DEFAULT_ID_RECURSO_PADRE.intValue())))
            .andExpect(jsonPath("$.[*].idModulo").value(hasItem(DEFAULT_ID_MODULO.intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].opeusr").value(hasItem(DEFAULT_OPEUSR.toString())))
            .andExpect(jsonPath("$.[*].opefecha").value(hasItem(DEFAULT_OPEFECHA.toString())))
            .andExpect(jsonPath("$.[*].opeope").value(hasItem(DEFAULT_OPEOPE.toString())));
    }
    
    @Test
    @Transactional
    public void getAutRecurso() throws Exception {
        // Initialize the database
        autRecursoRepository.saveAndFlush(autRecurso);

        // Get the autRecurso
        restAutRecursoMockMvc.perform(get("/api/aut-recursos/{id}", autRecurso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(autRecurso.getId().intValue()))
            .andExpect(jsonPath("$.idRecurso").value(DEFAULT_ID_RECURSO.intValue()))
            .andExpect(jsonPath("$.idRecursoDominio").value(DEFAULT_ID_RECURSO_DOMINIO.intValue()))
            .andExpect(jsonPath("$.idRecursoPadre").value(DEFAULT_ID_RECURSO_PADRE.intValue()))
            .andExpect(jsonPath("$.idModulo").value(DEFAULT_ID_MODULO.intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.opeusr").value(DEFAULT_OPEUSR.toString()))
            .andExpect(jsonPath("$.opefecha").value(DEFAULT_OPEFECHA.toString()))
            .andExpect(jsonPath("$.opeope").value(DEFAULT_OPEOPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAutRecurso() throws Exception {
        // Get the autRecurso
        restAutRecursoMockMvc.perform(get("/api/aut-recursos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAutRecurso() throws Exception {
        // Initialize the database
        autRecursoRepository.saveAndFlush(autRecurso);

        int databaseSizeBeforeUpdate = autRecursoRepository.findAll().size();

        // Update the autRecurso
        AutRecurso updatedAutRecurso = autRecursoRepository.findById(autRecurso.getId()).get();
        // Disconnect from session so that the updates on updatedAutRecurso are not directly saved in db
        em.detach(updatedAutRecurso);
        updatedAutRecurso
            .idRecurso(UPDATED_ID_RECURSO)
            .idRecursoDominio(UPDATED_ID_RECURSO_DOMINIO)
            .idRecursoPadre(UPDATED_ID_RECURSO_PADRE)
            .idModulo(UPDATED_ID_MODULO)
            .nombre(UPDATED_NOMBRE)
            .estado(UPDATED_ESTADO)
            .opeusr(UPDATED_OPEUSR)
            .opefecha(UPDATED_OPEFECHA)
            .opeope(UPDATED_OPEOPE);
        AutRecursoDTO autRecursoDTO = autRecursoMapper.toDto(updatedAutRecurso);

        restAutRecursoMockMvc.perform(put("/api/aut-recursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRecursoDTO)))
            .andExpect(status().isOk());

        // Validate the AutRecurso in the database
        List<AutRecurso> autRecursoList = autRecursoRepository.findAll();
        assertThat(autRecursoList).hasSize(databaseSizeBeforeUpdate);
        AutRecurso testAutRecurso = autRecursoList.get(autRecursoList.size() - 1);
        assertThat(testAutRecurso.getIdRecurso()).isEqualTo(UPDATED_ID_RECURSO);
        assertThat(testAutRecurso.getIdRecursoDominio()).isEqualTo(UPDATED_ID_RECURSO_DOMINIO);
        assertThat(testAutRecurso.getIdRecursoPadre()).isEqualTo(UPDATED_ID_RECURSO_PADRE);
        assertThat(testAutRecurso.getIdModulo()).isEqualTo(UPDATED_ID_MODULO);
        assertThat(testAutRecurso.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testAutRecurso.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testAutRecurso.getOpeusr()).isEqualTo(UPDATED_OPEUSR);
        assertThat(testAutRecurso.getOpefecha()).isEqualTo(UPDATED_OPEFECHA);
        assertThat(testAutRecurso.getOpeope()).isEqualTo(UPDATED_OPEOPE);
    }

    @Test
    @Transactional
    public void updateNonExistingAutRecurso() throws Exception {
        int databaseSizeBeforeUpdate = autRecursoRepository.findAll().size();

        // Create the AutRecurso
        AutRecursoDTO autRecursoDTO = autRecursoMapper.toDto(autRecurso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutRecursoMockMvc.perform(put("/api/aut-recursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRecursoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AutRecurso in the database
        List<AutRecurso> autRecursoList = autRecursoRepository.findAll();
        assertThat(autRecursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAutRecurso() throws Exception {
        // Initialize the database
        autRecursoRepository.saveAndFlush(autRecurso);

        int databaseSizeBeforeDelete = autRecursoRepository.findAll().size();

        // Delete the autRecurso
        restAutRecursoMockMvc.perform(delete("/api/aut-recursos/{id}", autRecurso.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AutRecurso> autRecursoList = autRecursoRepository.findAll();
        assertThat(autRecursoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutRecurso.class);
        AutRecurso autRecurso1 = new AutRecurso();
        autRecurso1.setId(1L);
        AutRecurso autRecurso2 = new AutRecurso();
        autRecurso2.setId(autRecurso1.getId());
        assertThat(autRecurso1).isEqualTo(autRecurso2);
        autRecurso2.setId(2L);
        assertThat(autRecurso1).isNotEqualTo(autRecurso2);
        autRecurso1.setId(null);
        assertThat(autRecurso1).isNotEqualTo(autRecurso2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutRecursoDTO.class);
        AutRecursoDTO autRecursoDTO1 = new AutRecursoDTO();
        autRecursoDTO1.setId(1L);
        AutRecursoDTO autRecursoDTO2 = new AutRecursoDTO();
        assertThat(autRecursoDTO1).isNotEqualTo(autRecursoDTO2);
        autRecursoDTO2.setId(autRecursoDTO1.getId());
        assertThat(autRecursoDTO1).isEqualTo(autRecursoDTO2);
        autRecursoDTO2.setId(2L);
        assertThat(autRecursoDTO1).isNotEqualTo(autRecursoDTO2);
        autRecursoDTO1.setId(null);
        assertThat(autRecursoDTO1).isNotEqualTo(autRecursoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(autRecursoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(autRecursoMapper.fromId(null)).isNull();
    }
}
