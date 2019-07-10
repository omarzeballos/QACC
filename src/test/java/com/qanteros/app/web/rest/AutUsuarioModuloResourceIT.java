package com.qanteros.app.web.rest;

import com.qanteros.app.QaccApp;
import com.qanteros.app.domain.AutUsuarioModulo;
import com.qanteros.app.repository.AutUsuarioModuloRepository;
import com.qanteros.app.service.AutUsuarioModuloService;
import com.qanteros.app.service.dto.AutUsuarioModuloDTO;
import com.qanteros.app.service.mapper.AutUsuarioModuloMapper;
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
 * Integration tests for the {@Link AutUsuarioModuloResource} REST controller.
 */
@SpringBootTest(classes = QaccApp.class)
public class AutUsuarioModuloResourceIT {

    private static final Long DEFAULT_ID_USUARIO = 1L;
    private static final Long UPDATED_ID_USUARIO = 2L;

    private static final Long DEFAULT_ID_MODULO = 1L;
    private static final Long UPDATED_ID_MODULO = 2L;

    private static final String DEFAULT_OPEUSR = "AAAAAAAAAA";
    private static final String UPDATED_OPEUSR = "BBBBBBBBBB";

    private static final Instant DEFAULT_OPEFECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_OPEFECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_OPEOPE = "AAAAAAAAAA";
    private static final String UPDATED_OPEOPE = "BBBBBBBBBB";

    @Autowired
    private AutUsuarioModuloRepository autUsuarioModuloRepository;

    @Autowired
    private AutUsuarioModuloMapper autUsuarioModuloMapper;

    @Autowired
    private AutUsuarioModuloService autUsuarioModuloService;

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

    private MockMvc restAutUsuarioModuloMockMvc;

    private AutUsuarioModulo autUsuarioModulo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AutUsuarioModuloResource autUsuarioModuloResource = new AutUsuarioModuloResource(autUsuarioModuloService);
        this.restAutUsuarioModuloMockMvc = MockMvcBuilders.standaloneSetup(autUsuarioModuloResource)
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
    public static AutUsuarioModulo createEntity(EntityManager em) {
        AutUsuarioModulo autUsuarioModulo = new AutUsuarioModulo()
            .idUsuario(DEFAULT_ID_USUARIO)
            .idModulo(DEFAULT_ID_MODULO)
            .opeusr(DEFAULT_OPEUSR)
            .opefecha(DEFAULT_OPEFECHA)
            .opeope(DEFAULT_OPEOPE);
        return autUsuarioModulo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AutUsuarioModulo createUpdatedEntity(EntityManager em) {
        AutUsuarioModulo autUsuarioModulo = new AutUsuarioModulo()
            .idUsuario(UPDATED_ID_USUARIO)
            .idModulo(UPDATED_ID_MODULO)
            .opeusr(UPDATED_OPEUSR)
            .opefecha(UPDATED_OPEFECHA)
            .opeope(UPDATED_OPEOPE);
        return autUsuarioModulo;
    }

    @BeforeEach
    public void initTest() {
        autUsuarioModulo = createEntity(em);
    }

    @Test
    @Transactional
    public void createAutUsuarioModulo() throws Exception {
        int databaseSizeBeforeCreate = autUsuarioModuloRepository.findAll().size();

        // Create the AutUsuarioModulo
        AutUsuarioModuloDTO autUsuarioModuloDTO = autUsuarioModuloMapper.toDto(autUsuarioModulo);
        restAutUsuarioModuloMockMvc.perform(post("/api/aut-usuario-modulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autUsuarioModuloDTO)))
            .andExpect(status().isCreated());

        // Validate the AutUsuarioModulo in the database
        List<AutUsuarioModulo> autUsuarioModuloList = autUsuarioModuloRepository.findAll();
        assertThat(autUsuarioModuloList).hasSize(databaseSizeBeforeCreate + 1);
        AutUsuarioModulo testAutUsuarioModulo = autUsuarioModuloList.get(autUsuarioModuloList.size() - 1);
        assertThat(testAutUsuarioModulo.getIdUsuario()).isEqualTo(DEFAULT_ID_USUARIO);
        assertThat(testAutUsuarioModulo.getIdModulo()).isEqualTo(DEFAULT_ID_MODULO);
        assertThat(testAutUsuarioModulo.getOpeusr()).isEqualTo(DEFAULT_OPEUSR);
        assertThat(testAutUsuarioModulo.getOpefecha()).isEqualTo(DEFAULT_OPEFECHA);
        assertThat(testAutUsuarioModulo.getOpeope()).isEqualTo(DEFAULT_OPEOPE);
    }

    @Test
    @Transactional
    public void createAutUsuarioModuloWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = autUsuarioModuloRepository.findAll().size();

        // Create the AutUsuarioModulo with an existing ID
        autUsuarioModulo.setId(1L);
        AutUsuarioModuloDTO autUsuarioModuloDTO = autUsuarioModuloMapper.toDto(autUsuarioModulo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutUsuarioModuloMockMvc.perform(post("/api/aut-usuario-modulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autUsuarioModuloDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AutUsuarioModulo in the database
        List<AutUsuarioModulo> autUsuarioModuloList = autUsuarioModuloRepository.findAll();
        assertThat(autUsuarioModuloList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdUsuarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = autUsuarioModuloRepository.findAll().size();
        // set the field null
        autUsuarioModulo.setIdUsuario(null);

        // Create the AutUsuarioModulo, which fails.
        AutUsuarioModuloDTO autUsuarioModuloDTO = autUsuarioModuloMapper.toDto(autUsuarioModulo);

        restAutUsuarioModuloMockMvc.perform(post("/api/aut-usuario-modulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autUsuarioModuloDTO)))
            .andExpect(status().isBadRequest());

        List<AutUsuarioModulo> autUsuarioModuloList = autUsuarioModuloRepository.findAll();
        assertThat(autUsuarioModuloList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdModuloIsRequired() throws Exception {
        int databaseSizeBeforeTest = autUsuarioModuloRepository.findAll().size();
        // set the field null
        autUsuarioModulo.setIdModulo(null);

        // Create the AutUsuarioModulo, which fails.
        AutUsuarioModuloDTO autUsuarioModuloDTO = autUsuarioModuloMapper.toDto(autUsuarioModulo);

        restAutUsuarioModuloMockMvc.perform(post("/api/aut-usuario-modulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autUsuarioModuloDTO)))
            .andExpect(status().isBadRequest());

        List<AutUsuarioModulo> autUsuarioModuloList = autUsuarioModuloRepository.findAll();
        assertThat(autUsuarioModuloList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOpeusrIsRequired() throws Exception {
        int databaseSizeBeforeTest = autUsuarioModuloRepository.findAll().size();
        // set the field null
        autUsuarioModulo.setOpeusr(null);

        // Create the AutUsuarioModulo, which fails.
        AutUsuarioModuloDTO autUsuarioModuloDTO = autUsuarioModuloMapper.toDto(autUsuarioModulo);

        restAutUsuarioModuloMockMvc.perform(post("/api/aut-usuario-modulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autUsuarioModuloDTO)))
            .andExpect(status().isBadRequest());

        List<AutUsuarioModulo> autUsuarioModuloList = autUsuarioModuloRepository.findAll();
        assertThat(autUsuarioModuloList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOpefechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = autUsuarioModuloRepository.findAll().size();
        // set the field null
        autUsuarioModulo.setOpefecha(null);

        // Create the AutUsuarioModulo, which fails.
        AutUsuarioModuloDTO autUsuarioModuloDTO = autUsuarioModuloMapper.toDto(autUsuarioModulo);

        restAutUsuarioModuloMockMvc.perform(post("/api/aut-usuario-modulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autUsuarioModuloDTO)))
            .andExpect(status().isBadRequest());

        List<AutUsuarioModulo> autUsuarioModuloList = autUsuarioModuloRepository.findAll();
        assertThat(autUsuarioModuloList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOpeopeIsRequired() throws Exception {
        int databaseSizeBeforeTest = autUsuarioModuloRepository.findAll().size();
        // set the field null
        autUsuarioModulo.setOpeope(null);

        // Create the AutUsuarioModulo, which fails.
        AutUsuarioModuloDTO autUsuarioModuloDTO = autUsuarioModuloMapper.toDto(autUsuarioModulo);

        restAutUsuarioModuloMockMvc.perform(post("/api/aut-usuario-modulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autUsuarioModuloDTO)))
            .andExpect(status().isBadRequest());

        List<AutUsuarioModulo> autUsuarioModuloList = autUsuarioModuloRepository.findAll();
        assertThat(autUsuarioModuloList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAutUsuarioModulos() throws Exception {
        // Initialize the database
        autUsuarioModuloRepository.saveAndFlush(autUsuarioModulo);

        // Get all the autUsuarioModuloList
        restAutUsuarioModuloMockMvc.perform(get("/api/aut-usuario-modulos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autUsuarioModulo.getId().intValue())))
            .andExpect(jsonPath("$.[*].idUsuario").value(hasItem(DEFAULT_ID_USUARIO.intValue())))
            .andExpect(jsonPath("$.[*].idModulo").value(hasItem(DEFAULT_ID_MODULO.intValue())))
            .andExpect(jsonPath("$.[*].opeusr").value(hasItem(DEFAULT_OPEUSR.toString())))
            .andExpect(jsonPath("$.[*].opefecha").value(hasItem(DEFAULT_OPEFECHA.toString())))
            .andExpect(jsonPath("$.[*].opeope").value(hasItem(DEFAULT_OPEOPE.toString())));
    }
    
    @Test
    @Transactional
    public void getAutUsuarioModulo() throws Exception {
        // Initialize the database
        autUsuarioModuloRepository.saveAndFlush(autUsuarioModulo);

        // Get the autUsuarioModulo
        restAutUsuarioModuloMockMvc.perform(get("/api/aut-usuario-modulos/{id}", autUsuarioModulo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(autUsuarioModulo.getId().intValue()))
            .andExpect(jsonPath("$.idUsuario").value(DEFAULT_ID_USUARIO.intValue()))
            .andExpect(jsonPath("$.idModulo").value(DEFAULT_ID_MODULO.intValue()))
            .andExpect(jsonPath("$.opeusr").value(DEFAULT_OPEUSR.toString()))
            .andExpect(jsonPath("$.opefecha").value(DEFAULT_OPEFECHA.toString()))
            .andExpect(jsonPath("$.opeope").value(DEFAULT_OPEOPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAutUsuarioModulo() throws Exception {
        // Get the autUsuarioModulo
        restAutUsuarioModuloMockMvc.perform(get("/api/aut-usuario-modulos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAutUsuarioModulo() throws Exception {
        // Initialize the database
        autUsuarioModuloRepository.saveAndFlush(autUsuarioModulo);

        int databaseSizeBeforeUpdate = autUsuarioModuloRepository.findAll().size();

        // Update the autUsuarioModulo
        AutUsuarioModulo updatedAutUsuarioModulo = autUsuarioModuloRepository.findById(autUsuarioModulo.getId()).get();
        // Disconnect from session so that the updates on updatedAutUsuarioModulo are not directly saved in db
        em.detach(updatedAutUsuarioModulo);
        updatedAutUsuarioModulo
            .idUsuario(UPDATED_ID_USUARIO)
            .idModulo(UPDATED_ID_MODULO)
            .opeusr(UPDATED_OPEUSR)
            .opefecha(UPDATED_OPEFECHA)
            .opeope(UPDATED_OPEOPE);
        AutUsuarioModuloDTO autUsuarioModuloDTO = autUsuarioModuloMapper.toDto(updatedAutUsuarioModulo);

        restAutUsuarioModuloMockMvc.perform(put("/api/aut-usuario-modulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autUsuarioModuloDTO)))
            .andExpect(status().isOk());

        // Validate the AutUsuarioModulo in the database
        List<AutUsuarioModulo> autUsuarioModuloList = autUsuarioModuloRepository.findAll();
        assertThat(autUsuarioModuloList).hasSize(databaseSizeBeforeUpdate);
        AutUsuarioModulo testAutUsuarioModulo = autUsuarioModuloList.get(autUsuarioModuloList.size() - 1);
        assertThat(testAutUsuarioModulo.getIdUsuario()).isEqualTo(UPDATED_ID_USUARIO);
        assertThat(testAutUsuarioModulo.getIdModulo()).isEqualTo(UPDATED_ID_MODULO);
        assertThat(testAutUsuarioModulo.getOpeusr()).isEqualTo(UPDATED_OPEUSR);
        assertThat(testAutUsuarioModulo.getOpefecha()).isEqualTo(UPDATED_OPEFECHA);
        assertThat(testAutUsuarioModulo.getOpeope()).isEqualTo(UPDATED_OPEOPE);
    }

    @Test
    @Transactional
    public void updateNonExistingAutUsuarioModulo() throws Exception {
        int databaseSizeBeforeUpdate = autUsuarioModuloRepository.findAll().size();

        // Create the AutUsuarioModulo
        AutUsuarioModuloDTO autUsuarioModuloDTO = autUsuarioModuloMapper.toDto(autUsuarioModulo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutUsuarioModuloMockMvc.perform(put("/api/aut-usuario-modulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autUsuarioModuloDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AutUsuarioModulo in the database
        List<AutUsuarioModulo> autUsuarioModuloList = autUsuarioModuloRepository.findAll();
        assertThat(autUsuarioModuloList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAutUsuarioModulo() throws Exception {
        // Initialize the database
        autUsuarioModuloRepository.saveAndFlush(autUsuarioModulo);

        int databaseSizeBeforeDelete = autUsuarioModuloRepository.findAll().size();

        // Delete the autUsuarioModulo
        restAutUsuarioModuloMockMvc.perform(delete("/api/aut-usuario-modulos/{id}", autUsuarioModulo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AutUsuarioModulo> autUsuarioModuloList = autUsuarioModuloRepository.findAll();
        assertThat(autUsuarioModuloList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutUsuarioModulo.class);
        AutUsuarioModulo autUsuarioModulo1 = new AutUsuarioModulo();
        autUsuarioModulo1.setId(1L);
        AutUsuarioModulo autUsuarioModulo2 = new AutUsuarioModulo();
        autUsuarioModulo2.setId(autUsuarioModulo1.getId());
        assertThat(autUsuarioModulo1).isEqualTo(autUsuarioModulo2);
        autUsuarioModulo2.setId(2L);
        assertThat(autUsuarioModulo1).isNotEqualTo(autUsuarioModulo2);
        autUsuarioModulo1.setId(null);
        assertThat(autUsuarioModulo1).isNotEqualTo(autUsuarioModulo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutUsuarioModuloDTO.class);
        AutUsuarioModuloDTO autUsuarioModuloDTO1 = new AutUsuarioModuloDTO();
        autUsuarioModuloDTO1.setId(1L);
        AutUsuarioModuloDTO autUsuarioModuloDTO2 = new AutUsuarioModuloDTO();
        assertThat(autUsuarioModuloDTO1).isNotEqualTo(autUsuarioModuloDTO2);
        autUsuarioModuloDTO2.setId(autUsuarioModuloDTO1.getId());
        assertThat(autUsuarioModuloDTO1).isEqualTo(autUsuarioModuloDTO2);
        autUsuarioModuloDTO2.setId(2L);
        assertThat(autUsuarioModuloDTO1).isNotEqualTo(autUsuarioModuloDTO2);
        autUsuarioModuloDTO1.setId(null);
        assertThat(autUsuarioModuloDTO1).isNotEqualTo(autUsuarioModuloDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(autUsuarioModuloMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(autUsuarioModuloMapper.fromId(null)).isNull();
    }
}
