package com.qanteros.app.web.rest;

import com.qanteros.app.QaccApp;
import com.qanteros.app.domain.AutPerfilRol;
import com.qanteros.app.repository.AutPerfilRolRepository;
import com.qanteros.app.service.AutPerfilRolService;
import com.qanteros.app.service.dto.AutPerfilRolDTO;
import com.qanteros.app.service.mapper.AutPerfilRolMapper;
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
 * Integration tests for the {@Link AutPerfilRolResource} REST controller.
 */
@SpringBootTest(classes = QaccApp.class)
public class AutPerfilRolResourceIT {

    private static final Long DEFAULT_ID_PERFIL = 1L;
    private static final Long UPDATED_ID_PERFIL = 2L;

    private static final Long DEFAULT_ID_ROL = 1L;
    private static final Long UPDATED_ID_ROL = 2L;

    private static final String DEFAULT_OPEUSR = "AAAAAAAAAA";
    private static final String UPDATED_OPEUSR = "BBBBBBBBBB";

    private static final Instant DEFAULT_OPEFECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_OPEFECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_OPEOPE = "AAAAAAAAAA";
    private static final String UPDATED_OPEOPE = "BBBBBBBBBB";

    @Autowired
    private AutPerfilRolRepository autPerfilRolRepository;

    @Autowired
    private AutPerfilRolMapper autPerfilRolMapper;

    @Autowired
    private AutPerfilRolService autPerfilRolService;

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

    private MockMvc restAutPerfilRolMockMvc;

    private AutPerfilRol autPerfilRol;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AutPerfilRolResource autPerfilRolResource = new AutPerfilRolResource(autPerfilRolService);
        this.restAutPerfilRolMockMvc = MockMvcBuilders.standaloneSetup(autPerfilRolResource)
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
    public static AutPerfilRol createEntity(EntityManager em) {
        AutPerfilRol autPerfilRol = new AutPerfilRol()
            .idPerfil(DEFAULT_ID_PERFIL)
            .idRol(DEFAULT_ID_ROL)
            .opeusr(DEFAULT_OPEUSR)
            .opefecha(DEFAULT_OPEFECHA)
            .opeope(DEFAULT_OPEOPE);
        return autPerfilRol;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AutPerfilRol createUpdatedEntity(EntityManager em) {
        AutPerfilRol autPerfilRol = new AutPerfilRol()
            .idPerfil(UPDATED_ID_PERFIL)
            .idRol(UPDATED_ID_ROL)
            .opeusr(UPDATED_OPEUSR)
            .opefecha(UPDATED_OPEFECHA)
            .opeope(UPDATED_OPEOPE);
        return autPerfilRol;
    }

    @BeforeEach
    public void initTest() {
        autPerfilRol = createEntity(em);
    }

    @Test
    @Transactional
    public void createAutPerfilRol() throws Exception {
        int databaseSizeBeforeCreate = autPerfilRolRepository.findAll().size();

        // Create the AutPerfilRol
        AutPerfilRolDTO autPerfilRolDTO = autPerfilRolMapper.toDto(autPerfilRol);
        restAutPerfilRolMockMvc.perform(post("/api/aut-perfil-rols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autPerfilRolDTO)))
            .andExpect(status().isCreated());

        // Validate the AutPerfilRol in the database
        List<AutPerfilRol> autPerfilRolList = autPerfilRolRepository.findAll();
        assertThat(autPerfilRolList).hasSize(databaseSizeBeforeCreate + 1);
        AutPerfilRol testAutPerfilRol = autPerfilRolList.get(autPerfilRolList.size() - 1);
        assertThat(testAutPerfilRol.getIdPerfil()).isEqualTo(DEFAULT_ID_PERFIL);
        assertThat(testAutPerfilRol.getIdRol()).isEqualTo(DEFAULT_ID_ROL);
        assertThat(testAutPerfilRol.getOpeusr()).isEqualTo(DEFAULT_OPEUSR);
        assertThat(testAutPerfilRol.getOpefecha()).isEqualTo(DEFAULT_OPEFECHA);
        assertThat(testAutPerfilRol.getOpeope()).isEqualTo(DEFAULT_OPEOPE);
    }

    @Test
    @Transactional
    public void createAutPerfilRolWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = autPerfilRolRepository.findAll().size();

        // Create the AutPerfilRol with an existing ID
        autPerfilRol.setId(1L);
        AutPerfilRolDTO autPerfilRolDTO = autPerfilRolMapper.toDto(autPerfilRol);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutPerfilRolMockMvc.perform(post("/api/aut-perfil-rols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autPerfilRolDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AutPerfilRol in the database
        List<AutPerfilRol> autPerfilRolList = autPerfilRolRepository.findAll();
        assertThat(autPerfilRolList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdPerfilIsRequired() throws Exception {
        int databaseSizeBeforeTest = autPerfilRolRepository.findAll().size();
        // set the field null
        autPerfilRol.setIdPerfil(null);

        // Create the AutPerfilRol, which fails.
        AutPerfilRolDTO autPerfilRolDTO = autPerfilRolMapper.toDto(autPerfilRol);

        restAutPerfilRolMockMvc.perform(post("/api/aut-perfil-rols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autPerfilRolDTO)))
            .andExpect(status().isBadRequest());

        List<AutPerfilRol> autPerfilRolList = autPerfilRolRepository.findAll();
        assertThat(autPerfilRolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdRolIsRequired() throws Exception {
        int databaseSizeBeforeTest = autPerfilRolRepository.findAll().size();
        // set the field null
        autPerfilRol.setIdRol(null);

        // Create the AutPerfilRol, which fails.
        AutPerfilRolDTO autPerfilRolDTO = autPerfilRolMapper.toDto(autPerfilRol);

        restAutPerfilRolMockMvc.perform(post("/api/aut-perfil-rols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autPerfilRolDTO)))
            .andExpect(status().isBadRequest());

        List<AutPerfilRol> autPerfilRolList = autPerfilRolRepository.findAll();
        assertThat(autPerfilRolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOpeusrIsRequired() throws Exception {
        int databaseSizeBeforeTest = autPerfilRolRepository.findAll().size();
        // set the field null
        autPerfilRol.setOpeusr(null);

        // Create the AutPerfilRol, which fails.
        AutPerfilRolDTO autPerfilRolDTO = autPerfilRolMapper.toDto(autPerfilRol);

        restAutPerfilRolMockMvc.perform(post("/api/aut-perfil-rols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autPerfilRolDTO)))
            .andExpect(status().isBadRequest());

        List<AutPerfilRol> autPerfilRolList = autPerfilRolRepository.findAll();
        assertThat(autPerfilRolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOpefechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = autPerfilRolRepository.findAll().size();
        // set the field null
        autPerfilRol.setOpefecha(null);

        // Create the AutPerfilRol, which fails.
        AutPerfilRolDTO autPerfilRolDTO = autPerfilRolMapper.toDto(autPerfilRol);

        restAutPerfilRolMockMvc.perform(post("/api/aut-perfil-rols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autPerfilRolDTO)))
            .andExpect(status().isBadRequest());

        List<AutPerfilRol> autPerfilRolList = autPerfilRolRepository.findAll();
        assertThat(autPerfilRolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOpeopeIsRequired() throws Exception {
        int databaseSizeBeforeTest = autPerfilRolRepository.findAll().size();
        // set the field null
        autPerfilRol.setOpeope(null);

        // Create the AutPerfilRol, which fails.
        AutPerfilRolDTO autPerfilRolDTO = autPerfilRolMapper.toDto(autPerfilRol);

        restAutPerfilRolMockMvc.perform(post("/api/aut-perfil-rols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autPerfilRolDTO)))
            .andExpect(status().isBadRequest());

        List<AutPerfilRol> autPerfilRolList = autPerfilRolRepository.findAll();
        assertThat(autPerfilRolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAutPerfilRols() throws Exception {
        // Initialize the database
        autPerfilRolRepository.saveAndFlush(autPerfilRol);

        // Get all the autPerfilRolList
        restAutPerfilRolMockMvc.perform(get("/api/aut-perfil-rols?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autPerfilRol.getId().intValue())))
            .andExpect(jsonPath("$.[*].idPerfil").value(hasItem(DEFAULT_ID_PERFIL.intValue())))
            .andExpect(jsonPath("$.[*].idRol").value(hasItem(DEFAULT_ID_ROL.intValue())))
            .andExpect(jsonPath("$.[*].opeusr").value(hasItem(DEFAULT_OPEUSR.toString())))
            .andExpect(jsonPath("$.[*].opefecha").value(hasItem(DEFAULT_OPEFECHA.toString())))
            .andExpect(jsonPath("$.[*].opeope").value(hasItem(DEFAULT_OPEOPE.toString())));
    }
    
    @Test
    @Transactional
    public void getAutPerfilRol() throws Exception {
        // Initialize the database
        autPerfilRolRepository.saveAndFlush(autPerfilRol);

        // Get the autPerfilRol
        restAutPerfilRolMockMvc.perform(get("/api/aut-perfil-rols/{id}", autPerfilRol.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(autPerfilRol.getId().intValue()))
            .andExpect(jsonPath("$.idPerfil").value(DEFAULT_ID_PERFIL.intValue()))
            .andExpect(jsonPath("$.idRol").value(DEFAULT_ID_ROL.intValue()))
            .andExpect(jsonPath("$.opeusr").value(DEFAULT_OPEUSR.toString()))
            .andExpect(jsonPath("$.opefecha").value(DEFAULT_OPEFECHA.toString()))
            .andExpect(jsonPath("$.opeope").value(DEFAULT_OPEOPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAutPerfilRol() throws Exception {
        // Get the autPerfilRol
        restAutPerfilRolMockMvc.perform(get("/api/aut-perfil-rols/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAutPerfilRol() throws Exception {
        // Initialize the database
        autPerfilRolRepository.saveAndFlush(autPerfilRol);

        int databaseSizeBeforeUpdate = autPerfilRolRepository.findAll().size();

        // Update the autPerfilRol
        AutPerfilRol updatedAutPerfilRol = autPerfilRolRepository.findById(autPerfilRol.getId()).get();
        // Disconnect from session so that the updates on updatedAutPerfilRol are not directly saved in db
        em.detach(updatedAutPerfilRol);
        updatedAutPerfilRol
            .idPerfil(UPDATED_ID_PERFIL)
            .idRol(UPDATED_ID_ROL)
            .opeusr(UPDATED_OPEUSR)
            .opefecha(UPDATED_OPEFECHA)
            .opeope(UPDATED_OPEOPE);
        AutPerfilRolDTO autPerfilRolDTO = autPerfilRolMapper.toDto(updatedAutPerfilRol);

        restAutPerfilRolMockMvc.perform(put("/api/aut-perfil-rols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autPerfilRolDTO)))
            .andExpect(status().isOk());

        // Validate the AutPerfilRol in the database
        List<AutPerfilRol> autPerfilRolList = autPerfilRolRepository.findAll();
        assertThat(autPerfilRolList).hasSize(databaseSizeBeforeUpdate);
        AutPerfilRol testAutPerfilRol = autPerfilRolList.get(autPerfilRolList.size() - 1);
        assertThat(testAutPerfilRol.getIdPerfil()).isEqualTo(UPDATED_ID_PERFIL);
        assertThat(testAutPerfilRol.getIdRol()).isEqualTo(UPDATED_ID_ROL);
        assertThat(testAutPerfilRol.getOpeusr()).isEqualTo(UPDATED_OPEUSR);
        assertThat(testAutPerfilRol.getOpefecha()).isEqualTo(UPDATED_OPEFECHA);
        assertThat(testAutPerfilRol.getOpeope()).isEqualTo(UPDATED_OPEOPE);
    }

    @Test
    @Transactional
    public void updateNonExistingAutPerfilRol() throws Exception {
        int databaseSizeBeforeUpdate = autPerfilRolRepository.findAll().size();

        // Create the AutPerfilRol
        AutPerfilRolDTO autPerfilRolDTO = autPerfilRolMapper.toDto(autPerfilRol);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutPerfilRolMockMvc.perform(put("/api/aut-perfil-rols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autPerfilRolDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AutPerfilRol in the database
        List<AutPerfilRol> autPerfilRolList = autPerfilRolRepository.findAll();
        assertThat(autPerfilRolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAutPerfilRol() throws Exception {
        // Initialize the database
        autPerfilRolRepository.saveAndFlush(autPerfilRol);

        int databaseSizeBeforeDelete = autPerfilRolRepository.findAll().size();

        // Delete the autPerfilRol
        restAutPerfilRolMockMvc.perform(delete("/api/aut-perfil-rols/{id}", autPerfilRol.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AutPerfilRol> autPerfilRolList = autPerfilRolRepository.findAll();
        assertThat(autPerfilRolList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutPerfilRol.class);
        AutPerfilRol autPerfilRol1 = new AutPerfilRol();
        autPerfilRol1.setId(1L);
        AutPerfilRol autPerfilRol2 = new AutPerfilRol();
        autPerfilRol2.setId(autPerfilRol1.getId());
        assertThat(autPerfilRol1).isEqualTo(autPerfilRol2);
        autPerfilRol2.setId(2L);
        assertThat(autPerfilRol1).isNotEqualTo(autPerfilRol2);
        autPerfilRol1.setId(null);
        assertThat(autPerfilRol1).isNotEqualTo(autPerfilRol2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutPerfilRolDTO.class);
        AutPerfilRolDTO autPerfilRolDTO1 = new AutPerfilRolDTO();
        autPerfilRolDTO1.setId(1L);
        AutPerfilRolDTO autPerfilRolDTO2 = new AutPerfilRolDTO();
        assertThat(autPerfilRolDTO1).isNotEqualTo(autPerfilRolDTO2);
        autPerfilRolDTO2.setId(autPerfilRolDTO1.getId());
        assertThat(autPerfilRolDTO1).isEqualTo(autPerfilRolDTO2);
        autPerfilRolDTO2.setId(2L);
        assertThat(autPerfilRolDTO1).isNotEqualTo(autPerfilRolDTO2);
        autPerfilRolDTO1.setId(null);
        assertThat(autPerfilRolDTO1).isNotEqualTo(autPerfilRolDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(autPerfilRolMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(autPerfilRolMapper.fromId(null)).isNull();
    }
}
