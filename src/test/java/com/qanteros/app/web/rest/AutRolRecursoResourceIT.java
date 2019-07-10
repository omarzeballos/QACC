package com.qanteros.app.web.rest;

import com.qanteros.app.QaccApp;
import com.qanteros.app.domain.AutRolRecurso;
import com.qanteros.app.repository.AutRolRecursoRepository;
import com.qanteros.app.service.AutRolRecursoService;
import com.qanteros.app.service.dto.AutRolRecursoDTO;
import com.qanteros.app.service.mapper.AutRolRecursoMapper;
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

/**
 * Integration tests for the {@Link AutRolRecursoResource} REST controller.
 */
@SpringBootTest(classes = QaccApp.class)
public class AutRolRecursoResourceIT {

    private static final Long DEFAULT_ID_ROL = 1L;
    private static final Long UPDATED_ID_ROL = 2L;

    private static final Long DEFAULT_ID_RECURSO = 1L;
    private static final Long UPDATED_ID_RECURSO = 2L;

    private static final String DEFAULT_OPEUSR = "AAAAAAAAAA";
    private static final String UPDATED_OPEUSR = "BBBBBBBBBB";

    private static final Instant DEFAULT_OPEFECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_OPEFECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_OPEOPE = "AAAAAAAAAA";
    private static final String UPDATED_OPEOPE = "BBBBBBBBBB";

    @Autowired
    private AutRolRecursoRepository autRolRecursoRepository;

    @Autowired
    private AutRolRecursoMapper autRolRecursoMapper;

    @Autowired
    private AutRolRecursoService autRolRecursoService;

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

    private MockMvc restAutRolRecursoMockMvc;

    private AutRolRecurso autRolRecurso;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AutRolRecursoResource autRolRecursoResource = new AutRolRecursoResource(autRolRecursoService);
        this.restAutRolRecursoMockMvc = MockMvcBuilders.standaloneSetup(autRolRecursoResource)
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
    public static AutRolRecurso createEntity(EntityManager em) {
        AutRolRecurso autRolRecurso = new AutRolRecurso()
            .idRol(DEFAULT_ID_ROL)
            .idRecurso(DEFAULT_ID_RECURSO)
            .opeusr(DEFAULT_OPEUSR)
            .opefecha(DEFAULT_OPEFECHA)
            .opeope(DEFAULT_OPEOPE);
        return autRolRecurso;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AutRolRecurso createUpdatedEntity(EntityManager em) {
        AutRolRecurso autRolRecurso = new AutRolRecurso()
            .idRol(UPDATED_ID_ROL)
            .idRecurso(UPDATED_ID_RECURSO)
            .opeusr(UPDATED_OPEUSR)
            .opefecha(UPDATED_OPEFECHA)
            .opeope(UPDATED_OPEOPE);
        return autRolRecurso;
    }

    @BeforeEach
    public void initTest() {
        autRolRecurso = createEntity(em);
    }

    @Test
    @Transactional
    public void createAutRolRecurso() throws Exception {
        int databaseSizeBeforeCreate = autRolRecursoRepository.findAll().size();

        // Create the AutRolRecurso
        AutRolRecursoDTO autRolRecursoDTO = autRolRecursoMapper.toDto(autRolRecurso);
        restAutRolRecursoMockMvc.perform(post("/api/aut-rol-recursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRolRecursoDTO)))
            .andExpect(status().isCreated());

        // Validate the AutRolRecurso in the database
        List<AutRolRecurso> autRolRecursoList = autRolRecursoRepository.findAll();
        assertThat(autRolRecursoList).hasSize(databaseSizeBeforeCreate + 1);
        AutRolRecurso testAutRolRecurso = autRolRecursoList.get(autRolRecursoList.size() - 1);
        assertThat(testAutRolRecurso.getIdRol()).isEqualTo(DEFAULT_ID_ROL);
        assertThat(testAutRolRecurso.getIdRecurso()).isEqualTo(DEFAULT_ID_RECURSO);
        assertThat(testAutRolRecurso.getOpeusr()).isEqualTo(DEFAULT_OPEUSR);
        assertThat(testAutRolRecurso.getOpefecha()).isEqualTo(DEFAULT_OPEFECHA);
        assertThat(testAutRolRecurso.getOpeope()).isEqualTo(DEFAULT_OPEOPE);
    }

    @Test
    @Transactional
    public void createAutRolRecursoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = autRolRecursoRepository.findAll().size();

        // Create the AutRolRecurso with an existing ID
        autRolRecurso.setId(1L);
        AutRolRecursoDTO autRolRecursoDTO = autRolRecursoMapper.toDto(autRolRecurso);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutRolRecursoMockMvc.perform(post("/api/aut-rol-recursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRolRecursoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AutRolRecurso in the database
        List<AutRolRecurso> autRolRecursoList = autRolRecursoRepository.findAll();
        assertThat(autRolRecursoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdRolIsRequired() throws Exception {
        int databaseSizeBeforeTest = autRolRecursoRepository.findAll().size();
        // set the field null
        autRolRecurso.setIdRol(null);

        // Create the AutRolRecurso, which fails.
        AutRolRecursoDTO autRolRecursoDTO = autRolRecursoMapper.toDto(autRolRecurso);

        restAutRolRecursoMockMvc.perform(post("/api/aut-rol-recursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRolRecursoDTO)))
            .andExpect(status().isBadRequest());

        List<AutRolRecurso> autRolRecursoList = autRolRecursoRepository.findAll();
        assertThat(autRolRecursoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdRecursoIsRequired() throws Exception {
        int databaseSizeBeforeTest = autRolRecursoRepository.findAll().size();
        // set the field null
        autRolRecurso.setIdRecurso(null);

        // Create the AutRolRecurso, which fails.
        AutRolRecursoDTO autRolRecursoDTO = autRolRecursoMapper.toDto(autRolRecurso);

        restAutRolRecursoMockMvc.perform(post("/api/aut-rol-recursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRolRecursoDTO)))
            .andExpect(status().isBadRequest());

        List<AutRolRecurso> autRolRecursoList = autRolRecursoRepository.findAll();
        assertThat(autRolRecursoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOpeusrIsRequired() throws Exception {
        int databaseSizeBeforeTest = autRolRecursoRepository.findAll().size();
        // set the field null
        autRolRecurso.setOpeusr(null);

        // Create the AutRolRecurso, which fails.
        AutRolRecursoDTO autRolRecursoDTO = autRolRecursoMapper.toDto(autRolRecurso);

        restAutRolRecursoMockMvc.perform(post("/api/aut-rol-recursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRolRecursoDTO)))
            .andExpect(status().isBadRequest());

        List<AutRolRecurso> autRolRecursoList = autRolRecursoRepository.findAll();
        assertThat(autRolRecursoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOpefechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = autRolRecursoRepository.findAll().size();
        // set the field null
        autRolRecurso.setOpefecha(null);

        // Create the AutRolRecurso, which fails.
        AutRolRecursoDTO autRolRecursoDTO = autRolRecursoMapper.toDto(autRolRecurso);

        restAutRolRecursoMockMvc.perform(post("/api/aut-rol-recursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRolRecursoDTO)))
            .andExpect(status().isBadRequest());

        List<AutRolRecurso> autRolRecursoList = autRolRecursoRepository.findAll();
        assertThat(autRolRecursoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOpeopeIsRequired() throws Exception {
        int databaseSizeBeforeTest = autRolRecursoRepository.findAll().size();
        // set the field null
        autRolRecurso.setOpeope(null);

        // Create the AutRolRecurso, which fails.
        AutRolRecursoDTO autRolRecursoDTO = autRolRecursoMapper.toDto(autRolRecurso);

        restAutRolRecursoMockMvc.perform(post("/api/aut-rol-recursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRolRecursoDTO)))
            .andExpect(status().isBadRequest());

        List<AutRolRecurso> autRolRecursoList = autRolRecursoRepository.findAll();
        assertThat(autRolRecursoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAutRolRecursos() throws Exception {
        // Initialize the database
        autRolRecursoRepository.saveAndFlush(autRolRecurso);

        // Get all the autRolRecursoList
        restAutRolRecursoMockMvc.perform(get("/api/aut-rol-recursos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autRolRecurso.getId().intValue())))
            .andExpect(jsonPath("$.[*].idRol").value(hasItem(DEFAULT_ID_ROL.intValue())))
            .andExpect(jsonPath("$.[*].idRecurso").value(hasItem(DEFAULT_ID_RECURSO.intValue())))
            .andExpect(jsonPath("$.[*].opeusr").value(hasItem(DEFAULT_OPEUSR.toString())))
            .andExpect(jsonPath("$.[*].opefecha").value(hasItem(DEFAULT_OPEFECHA.toString())))
            .andExpect(jsonPath("$.[*].opeope").value(hasItem(DEFAULT_OPEOPE.toString())));
    }
    
    @Test
    @Transactional
    public void getAutRolRecurso() throws Exception {
        // Initialize the database
        autRolRecursoRepository.saveAndFlush(autRolRecurso);

        // Get the autRolRecurso
        restAutRolRecursoMockMvc.perform(get("/api/aut-rol-recursos/{id}", autRolRecurso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(autRolRecurso.getId().intValue()))
            .andExpect(jsonPath("$.idRol").value(DEFAULT_ID_ROL.intValue()))
            .andExpect(jsonPath("$.idRecurso").value(DEFAULT_ID_RECURSO.intValue()))
            .andExpect(jsonPath("$.opeusr").value(DEFAULT_OPEUSR.toString()))
            .andExpect(jsonPath("$.opefecha").value(DEFAULT_OPEFECHA.toString()))
            .andExpect(jsonPath("$.opeope").value(DEFAULT_OPEOPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAutRolRecurso() throws Exception {
        // Get the autRolRecurso
        restAutRolRecursoMockMvc.perform(get("/api/aut-rol-recursos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAutRolRecurso() throws Exception {
        // Initialize the database
        autRolRecursoRepository.saveAndFlush(autRolRecurso);

        int databaseSizeBeforeUpdate = autRolRecursoRepository.findAll().size();

        // Update the autRolRecurso
        AutRolRecurso updatedAutRolRecurso = autRolRecursoRepository.findById(autRolRecurso.getId()).get();
        // Disconnect from session so that the updates on updatedAutRolRecurso are not directly saved in db
        em.detach(updatedAutRolRecurso);
        updatedAutRolRecurso
            .idRol(UPDATED_ID_ROL)
            .idRecurso(UPDATED_ID_RECURSO)
            .opeusr(UPDATED_OPEUSR)
            .opefecha(UPDATED_OPEFECHA)
            .opeope(UPDATED_OPEOPE);
        AutRolRecursoDTO autRolRecursoDTO = autRolRecursoMapper.toDto(updatedAutRolRecurso);

        restAutRolRecursoMockMvc.perform(put("/api/aut-rol-recursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRolRecursoDTO)))
            .andExpect(status().isOk());

        // Validate the AutRolRecurso in the database
        List<AutRolRecurso> autRolRecursoList = autRolRecursoRepository.findAll();
        assertThat(autRolRecursoList).hasSize(databaseSizeBeforeUpdate);
        AutRolRecurso testAutRolRecurso = autRolRecursoList.get(autRolRecursoList.size() - 1);
        assertThat(testAutRolRecurso.getIdRol()).isEqualTo(UPDATED_ID_ROL);
        assertThat(testAutRolRecurso.getIdRecurso()).isEqualTo(UPDATED_ID_RECURSO);
        assertThat(testAutRolRecurso.getOpeusr()).isEqualTo(UPDATED_OPEUSR);
        assertThat(testAutRolRecurso.getOpefecha()).isEqualTo(UPDATED_OPEFECHA);
        assertThat(testAutRolRecurso.getOpeope()).isEqualTo(UPDATED_OPEOPE);
    }

    @Test
    @Transactional
    public void updateNonExistingAutRolRecurso() throws Exception {
        int databaseSizeBeforeUpdate = autRolRecursoRepository.findAll().size();

        // Create the AutRolRecurso
        AutRolRecursoDTO autRolRecursoDTO = autRolRecursoMapper.toDto(autRolRecurso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutRolRecursoMockMvc.perform(put("/api/aut-rol-recursos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRolRecursoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AutRolRecurso in the database
        List<AutRolRecurso> autRolRecursoList = autRolRecursoRepository.findAll();
        assertThat(autRolRecursoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAutRolRecurso() throws Exception {
        // Initialize the database
        autRolRecursoRepository.saveAndFlush(autRolRecurso);

        int databaseSizeBeforeDelete = autRolRecursoRepository.findAll().size();

        // Delete the autRolRecurso
        restAutRolRecursoMockMvc.perform(delete("/api/aut-rol-recursos/{id}", autRolRecurso.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AutRolRecurso> autRolRecursoList = autRolRecursoRepository.findAll();
        assertThat(autRolRecursoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutRolRecurso.class);
        AutRolRecurso autRolRecurso1 = new AutRolRecurso();
        autRolRecurso1.setId(1L);
        AutRolRecurso autRolRecurso2 = new AutRolRecurso();
        autRolRecurso2.setId(autRolRecurso1.getId());
        assertThat(autRolRecurso1).isEqualTo(autRolRecurso2);
        autRolRecurso2.setId(2L);
        assertThat(autRolRecurso1).isNotEqualTo(autRolRecurso2);
        autRolRecurso1.setId(null);
        assertThat(autRolRecurso1).isNotEqualTo(autRolRecurso2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutRolRecursoDTO.class);
        AutRolRecursoDTO autRolRecursoDTO1 = new AutRolRecursoDTO();
        autRolRecursoDTO1.setId(1L);
        AutRolRecursoDTO autRolRecursoDTO2 = new AutRolRecursoDTO();
        assertThat(autRolRecursoDTO1).isNotEqualTo(autRolRecursoDTO2);
        autRolRecursoDTO2.setId(autRolRecursoDTO1.getId());
        assertThat(autRolRecursoDTO1).isEqualTo(autRolRecursoDTO2);
        autRolRecursoDTO2.setId(2L);
        assertThat(autRolRecursoDTO1).isNotEqualTo(autRolRecursoDTO2);
        autRolRecursoDTO1.setId(null);
        assertThat(autRolRecursoDTO1).isNotEqualTo(autRolRecursoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(autRolRecursoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(autRolRecursoMapper.fromId(null)).isNull();
    }
}
