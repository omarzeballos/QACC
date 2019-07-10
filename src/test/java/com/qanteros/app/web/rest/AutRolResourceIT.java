package com.qanteros.app.web.rest;

import com.qanteros.app.QaccApp;
import com.qanteros.app.domain.AutRol;
import com.qanteros.app.repository.AutRolRepository;
import com.qanteros.app.service.AutRolService;
import com.qanteros.app.service.dto.AutRolDTO;
import com.qanteros.app.service.mapper.AutRolMapper;
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
 * Integration tests for the {@Link AutRolResource} REST controller.
 */
@SpringBootTest(classes = QaccApp.class)
public class AutRolResourceIT {

    private static final Long DEFAULT_ID_ROL = 1L;
    private static final Long UPDATED_ID_ROL = 2L;

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
    private AutRolRepository autRolRepository;

    @Autowired
    private AutRolMapper autRolMapper;

    @Autowired
    private AutRolService autRolService;

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

    private MockMvc restAutRolMockMvc;

    private AutRol autRol;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AutRolResource autRolResource = new AutRolResource(autRolService);
        this.restAutRolMockMvc = MockMvcBuilders.standaloneSetup(autRolResource)
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
    public static AutRol createEntity(EntityManager em) {
        AutRol autRol = new AutRol()
            .idRol(DEFAULT_ID_ROL)
            .nombre(DEFAULT_NOMBRE)
            .estado(DEFAULT_ESTADO)
            .opeusr(DEFAULT_OPEUSR)
            .opefecha(DEFAULT_OPEFECHA)
            .opeope(DEFAULT_OPEOPE);
        return autRol;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AutRol createUpdatedEntity(EntityManager em) {
        AutRol autRol = new AutRol()
            .idRol(UPDATED_ID_ROL)
            .nombre(UPDATED_NOMBRE)
            .estado(UPDATED_ESTADO)
            .opeusr(UPDATED_OPEUSR)
            .opefecha(UPDATED_OPEFECHA)
            .opeope(UPDATED_OPEOPE);
        return autRol;
    }

    @BeforeEach
    public void initTest() {
        autRol = createEntity(em);
    }

    @Test
    @Transactional
    public void createAutRol() throws Exception {
        int databaseSizeBeforeCreate = autRolRepository.findAll().size();

        // Create the AutRol
        AutRolDTO autRolDTO = autRolMapper.toDto(autRol);
        restAutRolMockMvc.perform(post("/api/aut-rols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRolDTO)))
            .andExpect(status().isCreated());

        // Validate the AutRol in the database
        List<AutRol> autRolList = autRolRepository.findAll();
        assertThat(autRolList).hasSize(databaseSizeBeforeCreate + 1);
        AutRol testAutRol = autRolList.get(autRolList.size() - 1);
        assertThat(testAutRol.getIdRol()).isEqualTo(DEFAULT_ID_ROL);
        assertThat(testAutRol.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testAutRol.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testAutRol.getOpeusr()).isEqualTo(DEFAULT_OPEUSR);
        assertThat(testAutRol.getOpefecha()).isEqualTo(DEFAULT_OPEFECHA);
        assertThat(testAutRol.getOpeope()).isEqualTo(DEFAULT_OPEOPE);
    }

    @Test
    @Transactional
    public void createAutRolWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = autRolRepository.findAll().size();

        // Create the AutRol with an existing ID
        autRol.setId(1L);
        AutRolDTO autRolDTO = autRolMapper.toDto(autRol);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutRolMockMvc.perform(post("/api/aut-rols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRolDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AutRol in the database
        List<AutRol> autRolList = autRolRepository.findAll();
        assertThat(autRolList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdRolIsRequired() throws Exception {
        int databaseSizeBeforeTest = autRolRepository.findAll().size();
        // set the field null
        autRol.setIdRol(null);

        // Create the AutRol, which fails.
        AutRolDTO autRolDTO = autRolMapper.toDto(autRol);

        restAutRolMockMvc.perform(post("/api/aut-rols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRolDTO)))
            .andExpect(status().isBadRequest());

        List<AutRol> autRolList = autRolRepository.findAll();
        assertThat(autRolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = autRolRepository.findAll().size();
        // set the field null
        autRol.setNombre(null);

        // Create the AutRol, which fails.
        AutRolDTO autRolDTO = autRolMapper.toDto(autRol);

        restAutRolMockMvc.perform(post("/api/aut-rols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRolDTO)))
            .andExpect(status().isBadRequest());

        List<AutRol> autRolList = autRolRepository.findAll();
        assertThat(autRolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = autRolRepository.findAll().size();
        // set the field null
        autRol.setEstado(null);

        // Create the AutRol, which fails.
        AutRolDTO autRolDTO = autRolMapper.toDto(autRol);

        restAutRolMockMvc.perform(post("/api/aut-rols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRolDTO)))
            .andExpect(status().isBadRequest());

        List<AutRol> autRolList = autRolRepository.findAll();
        assertThat(autRolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOpeusrIsRequired() throws Exception {
        int databaseSizeBeforeTest = autRolRepository.findAll().size();
        // set the field null
        autRol.setOpeusr(null);

        // Create the AutRol, which fails.
        AutRolDTO autRolDTO = autRolMapper.toDto(autRol);

        restAutRolMockMvc.perform(post("/api/aut-rols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRolDTO)))
            .andExpect(status().isBadRequest());

        List<AutRol> autRolList = autRolRepository.findAll();
        assertThat(autRolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOpefechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = autRolRepository.findAll().size();
        // set the field null
        autRol.setOpefecha(null);

        // Create the AutRol, which fails.
        AutRolDTO autRolDTO = autRolMapper.toDto(autRol);

        restAutRolMockMvc.perform(post("/api/aut-rols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRolDTO)))
            .andExpect(status().isBadRequest());

        List<AutRol> autRolList = autRolRepository.findAll();
        assertThat(autRolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOpeopeIsRequired() throws Exception {
        int databaseSizeBeforeTest = autRolRepository.findAll().size();
        // set the field null
        autRol.setOpeope(null);

        // Create the AutRol, which fails.
        AutRolDTO autRolDTO = autRolMapper.toDto(autRol);

        restAutRolMockMvc.perform(post("/api/aut-rols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRolDTO)))
            .andExpect(status().isBadRequest());

        List<AutRol> autRolList = autRolRepository.findAll();
        assertThat(autRolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAutRols() throws Exception {
        // Initialize the database
        autRolRepository.saveAndFlush(autRol);

        // Get all the autRolList
        restAutRolMockMvc.perform(get("/api/aut-rols?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autRol.getId().intValue())))
            .andExpect(jsonPath("$.[*].idRol").value(hasItem(DEFAULT_ID_ROL.intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].opeusr").value(hasItem(DEFAULT_OPEUSR.toString())))
            .andExpect(jsonPath("$.[*].opefecha").value(hasItem(DEFAULT_OPEFECHA.toString())))
            .andExpect(jsonPath("$.[*].opeope").value(hasItem(DEFAULT_OPEOPE.toString())));
    }
    
    @Test
    @Transactional
    public void getAutRol() throws Exception {
        // Initialize the database
        autRolRepository.saveAndFlush(autRol);

        // Get the autRol
        restAutRolMockMvc.perform(get("/api/aut-rols/{id}", autRol.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(autRol.getId().intValue()))
            .andExpect(jsonPath("$.idRol").value(DEFAULT_ID_ROL.intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.opeusr").value(DEFAULT_OPEUSR.toString()))
            .andExpect(jsonPath("$.opefecha").value(DEFAULT_OPEFECHA.toString()))
            .andExpect(jsonPath("$.opeope").value(DEFAULT_OPEOPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAutRol() throws Exception {
        // Get the autRol
        restAutRolMockMvc.perform(get("/api/aut-rols/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAutRol() throws Exception {
        // Initialize the database
        autRolRepository.saveAndFlush(autRol);

        int databaseSizeBeforeUpdate = autRolRepository.findAll().size();

        // Update the autRol
        AutRol updatedAutRol = autRolRepository.findById(autRol.getId()).get();
        // Disconnect from session so that the updates on updatedAutRol are not directly saved in db
        em.detach(updatedAutRol);
        updatedAutRol
            .idRol(UPDATED_ID_ROL)
            .nombre(UPDATED_NOMBRE)
            .estado(UPDATED_ESTADO)
            .opeusr(UPDATED_OPEUSR)
            .opefecha(UPDATED_OPEFECHA)
            .opeope(UPDATED_OPEOPE);
        AutRolDTO autRolDTO = autRolMapper.toDto(updatedAutRol);

        restAutRolMockMvc.perform(put("/api/aut-rols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRolDTO)))
            .andExpect(status().isOk());

        // Validate the AutRol in the database
        List<AutRol> autRolList = autRolRepository.findAll();
        assertThat(autRolList).hasSize(databaseSizeBeforeUpdate);
        AutRol testAutRol = autRolList.get(autRolList.size() - 1);
        assertThat(testAutRol.getIdRol()).isEqualTo(UPDATED_ID_ROL);
        assertThat(testAutRol.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testAutRol.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testAutRol.getOpeusr()).isEqualTo(UPDATED_OPEUSR);
        assertThat(testAutRol.getOpefecha()).isEqualTo(UPDATED_OPEFECHA);
        assertThat(testAutRol.getOpeope()).isEqualTo(UPDATED_OPEOPE);
    }

    @Test
    @Transactional
    public void updateNonExistingAutRol() throws Exception {
        int databaseSizeBeforeUpdate = autRolRepository.findAll().size();

        // Create the AutRol
        AutRolDTO autRolDTO = autRolMapper.toDto(autRol);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutRolMockMvc.perform(put("/api/aut-rols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autRolDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AutRol in the database
        List<AutRol> autRolList = autRolRepository.findAll();
        assertThat(autRolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAutRol() throws Exception {
        // Initialize the database
        autRolRepository.saveAndFlush(autRol);

        int databaseSizeBeforeDelete = autRolRepository.findAll().size();

        // Delete the autRol
        restAutRolMockMvc.perform(delete("/api/aut-rols/{id}", autRol.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AutRol> autRolList = autRolRepository.findAll();
        assertThat(autRolList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutRol.class);
        AutRol autRol1 = new AutRol();
        autRol1.setId(1L);
        AutRol autRol2 = new AutRol();
        autRol2.setId(autRol1.getId());
        assertThat(autRol1).isEqualTo(autRol2);
        autRol2.setId(2L);
        assertThat(autRol1).isNotEqualTo(autRol2);
        autRol1.setId(null);
        assertThat(autRol1).isNotEqualTo(autRol2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutRolDTO.class);
        AutRolDTO autRolDTO1 = new AutRolDTO();
        autRolDTO1.setId(1L);
        AutRolDTO autRolDTO2 = new AutRolDTO();
        assertThat(autRolDTO1).isNotEqualTo(autRolDTO2);
        autRolDTO2.setId(autRolDTO1.getId());
        assertThat(autRolDTO1).isEqualTo(autRolDTO2);
        autRolDTO2.setId(2L);
        assertThat(autRolDTO1).isNotEqualTo(autRolDTO2);
        autRolDTO1.setId(null);
        assertThat(autRolDTO1).isNotEqualTo(autRolDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(autRolMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(autRolMapper.fromId(null)).isNull();
    }
}
