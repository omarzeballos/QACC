package com.qanteros.app.web.rest;

import com.qanteros.app.QaccApp;
import com.qanteros.app.domain.AutModulo;
import com.qanteros.app.repository.AutModuloRepository;
import com.qanteros.app.service.AutModuloService;
import com.qanteros.app.service.dto.AutModuloDTO;
import com.qanteros.app.service.mapper.AutModuloMapper;
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
 * Integration tests for the {@Link AutModuloResource} REST controller.
 */
@SpringBootTest(classes = QaccApp.class)
public class AutModuloResourceIT {

    private static final Long DEFAULT_ID_MODULO = 1L;
    private static final Long UPDATED_ID_MODULO = 2L;

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_ABREVIACION = "AAAAAAAAAA";
    private static final String UPDATED_ABREVIACION = "BBBBBBBBBB";

    private static final Estado DEFAULT_ESTADO = Estado.ACTIVO;
    private static final Estado UPDATED_ESTADO = Estado.INACTIVO;

    private static final String DEFAULT_OPEUSR = "AAAAAAAAAA";
    private static final String UPDATED_OPEUSR = "BBBBBBBBBB";

    private static final Instant DEFAULT_OPEFECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_OPEFECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_OPEOPE = "AAAAAAAAAA";
    private static final String UPDATED_OPEOPE = "BBBBBBBBBB";

    @Autowired
    private AutModuloRepository autModuloRepository;

    @Autowired
    private AutModuloMapper autModuloMapper;

    @Autowired
    private AutModuloService autModuloService;

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

    private MockMvc restAutModuloMockMvc;

    private AutModulo autModulo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AutModuloResource autModuloResource = new AutModuloResource(autModuloService);
        this.restAutModuloMockMvc = MockMvcBuilders.standaloneSetup(autModuloResource)
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
    public static AutModulo createEntity(EntityManager em) {
        AutModulo autModulo = new AutModulo()
            .idModulo(DEFAULT_ID_MODULO)
            .nombre(DEFAULT_NOMBRE)
            .abreviacion(DEFAULT_ABREVIACION)
            .estado(DEFAULT_ESTADO)
            .opeusr(DEFAULT_OPEUSR)
            .opefecha(DEFAULT_OPEFECHA)
            .opeope(DEFAULT_OPEOPE);
        return autModulo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AutModulo createUpdatedEntity(EntityManager em) {
        AutModulo autModulo = new AutModulo()
            .idModulo(UPDATED_ID_MODULO)
            .nombre(UPDATED_NOMBRE)
            .abreviacion(UPDATED_ABREVIACION)
            .estado(UPDATED_ESTADO)
            .opeusr(UPDATED_OPEUSR)
            .opefecha(UPDATED_OPEFECHA)
            .opeope(UPDATED_OPEOPE);
        return autModulo;
    }

    @BeforeEach
    public void initTest() {
        autModulo = createEntity(em);
    }

    @Test
    @Transactional
    public void createAutModulo() throws Exception {
        int databaseSizeBeforeCreate = autModuloRepository.findAll().size();

        // Create the AutModulo
        AutModuloDTO autModuloDTO = autModuloMapper.toDto(autModulo);
        restAutModuloMockMvc.perform(post("/api/aut-modulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autModuloDTO)))
            .andExpect(status().isCreated());

        // Validate the AutModulo in the database
        List<AutModulo> autModuloList = autModuloRepository.findAll();
        assertThat(autModuloList).hasSize(databaseSizeBeforeCreate + 1);
        AutModulo testAutModulo = autModuloList.get(autModuloList.size() - 1);
        assertThat(testAutModulo.getIdModulo()).isEqualTo(DEFAULT_ID_MODULO);
        assertThat(testAutModulo.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testAutModulo.getAbreviacion()).isEqualTo(DEFAULT_ABREVIACION);
        assertThat(testAutModulo.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testAutModulo.getOpeusr()).isEqualTo(DEFAULT_OPEUSR);
        assertThat(testAutModulo.getOpefecha()).isEqualTo(DEFAULT_OPEFECHA);
        assertThat(testAutModulo.getOpeope()).isEqualTo(DEFAULT_OPEOPE);
    }

    @Test
    @Transactional
    public void createAutModuloWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = autModuloRepository.findAll().size();

        // Create the AutModulo with an existing ID
        autModulo.setId(1L);
        AutModuloDTO autModuloDTO = autModuloMapper.toDto(autModulo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutModuloMockMvc.perform(post("/api/aut-modulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autModuloDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AutModulo in the database
        List<AutModulo> autModuloList = autModuloRepository.findAll();
        assertThat(autModuloList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdModuloIsRequired() throws Exception {
        int databaseSizeBeforeTest = autModuloRepository.findAll().size();
        // set the field null
        autModulo.setIdModulo(null);

        // Create the AutModulo, which fails.
        AutModuloDTO autModuloDTO = autModuloMapper.toDto(autModulo);

        restAutModuloMockMvc.perform(post("/api/aut-modulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autModuloDTO)))
            .andExpect(status().isBadRequest());

        List<AutModulo> autModuloList = autModuloRepository.findAll();
        assertThat(autModuloList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = autModuloRepository.findAll().size();
        // set the field null
        autModulo.setNombre(null);

        // Create the AutModulo, which fails.
        AutModuloDTO autModuloDTO = autModuloMapper.toDto(autModulo);

        restAutModuloMockMvc.perform(post("/api/aut-modulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autModuloDTO)))
            .andExpect(status().isBadRequest());

        List<AutModulo> autModuloList = autModuloRepository.findAll();
        assertThat(autModuloList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAbreviacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = autModuloRepository.findAll().size();
        // set the field null
        autModulo.setAbreviacion(null);

        // Create the AutModulo, which fails.
        AutModuloDTO autModuloDTO = autModuloMapper.toDto(autModulo);

        restAutModuloMockMvc.perform(post("/api/aut-modulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autModuloDTO)))
            .andExpect(status().isBadRequest());

        List<AutModulo> autModuloList = autModuloRepository.findAll();
        assertThat(autModuloList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = autModuloRepository.findAll().size();
        // set the field null
        autModulo.setEstado(null);

        // Create the AutModulo, which fails.
        AutModuloDTO autModuloDTO = autModuloMapper.toDto(autModulo);

        restAutModuloMockMvc.perform(post("/api/aut-modulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autModuloDTO)))
            .andExpect(status().isBadRequest());

        List<AutModulo> autModuloList = autModuloRepository.findAll();
        assertThat(autModuloList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOpeusrIsRequired() throws Exception {
        int databaseSizeBeforeTest = autModuloRepository.findAll().size();
        // set the field null
        autModulo.setOpeusr(null);

        // Create the AutModulo, which fails.
        AutModuloDTO autModuloDTO = autModuloMapper.toDto(autModulo);

        restAutModuloMockMvc.perform(post("/api/aut-modulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autModuloDTO)))
            .andExpect(status().isBadRequest());

        List<AutModulo> autModuloList = autModuloRepository.findAll();
        assertThat(autModuloList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOpefechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = autModuloRepository.findAll().size();
        // set the field null
        autModulo.setOpefecha(null);

        // Create the AutModulo, which fails.
        AutModuloDTO autModuloDTO = autModuloMapper.toDto(autModulo);

        restAutModuloMockMvc.perform(post("/api/aut-modulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autModuloDTO)))
            .andExpect(status().isBadRequest());

        List<AutModulo> autModuloList = autModuloRepository.findAll();
        assertThat(autModuloList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOpeopeIsRequired() throws Exception {
        int databaseSizeBeforeTest = autModuloRepository.findAll().size();
        // set the field null
        autModulo.setOpeope(null);

        // Create the AutModulo, which fails.
        AutModuloDTO autModuloDTO = autModuloMapper.toDto(autModulo);

        restAutModuloMockMvc.perform(post("/api/aut-modulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autModuloDTO)))
            .andExpect(status().isBadRequest());

        List<AutModulo> autModuloList = autModuloRepository.findAll();
        assertThat(autModuloList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAutModulos() throws Exception {
        // Initialize the database
        autModuloRepository.saveAndFlush(autModulo);

        // Get all the autModuloList
        restAutModuloMockMvc.perform(get("/api/aut-modulos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autModulo.getId().intValue())))
            .andExpect(jsonPath("$.[*].idModulo").value(hasItem(DEFAULT_ID_MODULO.intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].abreviacion").value(hasItem(DEFAULT_ABREVIACION.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].opeusr").value(hasItem(DEFAULT_OPEUSR.toString())))
            .andExpect(jsonPath("$.[*].opefecha").value(hasItem(DEFAULT_OPEFECHA.toString())))
            .andExpect(jsonPath("$.[*].opeope").value(hasItem(DEFAULT_OPEOPE.toString())));
    }
    
    @Test
    @Transactional
    public void getAutModulo() throws Exception {
        // Initialize the database
        autModuloRepository.saveAndFlush(autModulo);

        // Get the autModulo
        restAutModuloMockMvc.perform(get("/api/aut-modulos/{id}", autModulo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(autModulo.getId().intValue()))
            .andExpect(jsonPath("$.idModulo").value(DEFAULT_ID_MODULO.intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.abreviacion").value(DEFAULT_ABREVIACION.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.opeusr").value(DEFAULT_OPEUSR.toString()))
            .andExpect(jsonPath("$.opefecha").value(DEFAULT_OPEFECHA.toString()))
            .andExpect(jsonPath("$.opeope").value(DEFAULT_OPEOPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAutModulo() throws Exception {
        // Get the autModulo
        restAutModuloMockMvc.perform(get("/api/aut-modulos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAutModulo() throws Exception {
        // Initialize the database
        autModuloRepository.saveAndFlush(autModulo);

        int databaseSizeBeforeUpdate = autModuloRepository.findAll().size();

        // Update the autModulo
        AutModulo updatedAutModulo = autModuloRepository.findById(autModulo.getId()).get();
        // Disconnect from session so that the updates on updatedAutModulo are not directly saved in db
        em.detach(updatedAutModulo);
        updatedAutModulo
            .idModulo(UPDATED_ID_MODULO)
            .nombre(UPDATED_NOMBRE)
            .abreviacion(UPDATED_ABREVIACION)
            .estado(UPDATED_ESTADO)
            .opeusr(UPDATED_OPEUSR)
            .opefecha(UPDATED_OPEFECHA)
            .opeope(UPDATED_OPEOPE);
        AutModuloDTO autModuloDTO = autModuloMapper.toDto(updatedAutModulo);

        restAutModuloMockMvc.perform(put("/api/aut-modulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autModuloDTO)))
            .andExpect(status().isOk());

        // Validate the AutModulo in the database
        List<AutModulo> autModuloList = autModuloRepository.findAll();
        assertThat(autModuloList).hasSize(databaseSizeBeforeUpdate);
        AutModulo testAutModulo = autModuloList.get(autModuloList.size() - 1);
        assertThat(testAutModulo.getIdModulo()).isEqualTo(UPDATED_ID_MODULO);
        assertThat(testAutModulo.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testAutModulo.getAbreviacion()).isEqualTo(UPDATED_ABREVIACION);
        assertThat(testAutModulo.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testAutModulo.getOpeusr()).isEqualTo(UPDATED_OPEUSR);
        assertThat(testAutModulo.getOpefecha()).isEqualTo(UPDATED_OPEFECHA);
        assertThat(testAutModulo.getOpeope()).isEqualTo(UPDATED_OPEOPE);
    }

    @Test
    @Transactional
    public void updateNonExistingAutModulo() throws Exception {
        int databaseSizeBeforeUpdate = autModuloRepository.findAll().size();

        // Create the AutModulo
        AutModuloDTO autModuloDTO = autModuloMapper.toDto(autModulo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutModuloMockMvc.perform(put("/api/aut-modulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autModuloDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AutModulo in the database
        List<AutModulo> autModuloList = autModuloRepository.findAll();
        assertThat(autModuloList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAutModulo() throws Exception {
        // Initialize the database
        autModuloRepository.saveAndFlush(autModulo);

        int databaseSizeBeforeDelete = autModuloRepository.findAll().size();

        // Delete the autModulo
        restAutModuloMockMvc.perform(delete("/api/aut-modulos/{id}", autModulo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AutModulo> autModuloList = autModuloRepository.findAll();
        assertThat(autModuloList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutModulo.class);
        AutModulo autModulo1 = new AutModulo();
        autModulo1.setId(1L);
        AutModulo autModulo2 = new AutModulo();
        autModulo2.setId(autModulo1.getId());
        assertThat(autModulo1).isEqualTo(autModulo2);
        autModulo2.setId(2L);
        assertThat(autModulo1).isNotEqualTo(autModulo2);
        autModulo1.setId(null);
        assertThat(autModulo1).isNotEqualTo(autModulo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutModuloDTO.class);
        AutModuloDTO autModuloDTO1 = new AutModuloDTO();
        autModuloDTO1.setId(1L);
        AutModuloDTO autModuloDTO2 = new AutModuloDTO();
        assertThat(autModuloDTO1).isNotEqualTo(autModuloDTO2);
        autModuloDTO2.setId(autModuloDTO1.getId());
        assertThat(autModuloDTO1).isEqualTo(autModuloDTO2);
        autModuloDTO2.setId(2L);
        assertThat(autModuloDTO1).isNotEqualTo(autModuloDTO2);
        autModuloDTO1.setId(null);
        assertThat(autModuloDTO1).isNotEqualTo(autModuloDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(autModuloMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(autModuloMapper.fromId(null)).isNull();
    }
}
