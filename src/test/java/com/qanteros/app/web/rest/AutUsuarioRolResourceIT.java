package com.qanteros.app.web.rest;

import com.qanteros.app.QaccApp;
import com.qanteros.app.domain.AutUsuarioRol;
import com.qanteros.app.repository.AutUsuarioRolRepository;
import com.qanteros.app.service.AutUsuarioRolService;
import com.qanteros.app.service.dto.AutUsuarioRolDTO;
import com.qanteros.app.service.mapper.AutUsuarioRolMapper;
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
 * Integration tests for the {@Link AutUsuarioRolResource} REST controller.
 */
@SpringBootTest(classes = QaccApp.class)
public class AutUsuarioRolResourceIT {

    private static final Long DEFAULT_ID_USUARIO = 1L;
    private static final Long UPDATED_ID_USUARIO = 2L;

    private static final Long DEFAULT_ID_ROL = 1L;
    private static final Long UPDATED_ID_ROL = 2L;

    private static final String DEFAULT_OPEUSR = "AAAAAAAAAA";
    private static final String UPDATED_OPEUSR = "BBBBBBBBBB";

    private static final Instant DEFAULT_OPEFECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_OPEFECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_OPEOPE = "AAAAAAAAAA";
    private static final String UPDATED_OPEOPE = "BBBBBBBBBB";

    @Autowired
    private AutUsuarioRolRepository autUsuarioRolRepository;

    @Autowired
    private AutUsuarioRolMapper autUsuarioRolMapper;

    @Autowired
    private AutUsuarioRolService autUsuarioRolService;

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

    private MockMvc restAutUsuarioRolMockMvc;

    private AutUsuarioRol autUsuarioRol;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AutUsuarioRolResource autUsuarioRolResource = new AutUsuarioRolResource(autUsuarioRolService);
        this.restAutUsuarioRolMockMvc = MockMvcBuilders.standaloneSetup(autUsuarioRolResource)
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
    public static AutUsuarioRol createEntity(EntityManager em) {
        AutUsuarioRol autUsuarioRol = new AutUsuarioRol()
            .idUsuario(DEFAULT_ID_USUARIO)
            .idRol(DEFAULT_ID_ROL)
            .opeusr(DEFAULT_OPEUSR)
            .opefecha(DEFAULT_OPEFECHA)
            .opeope(DEFAULT_OPEOPE);
        return autUsuarioRol;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AutUsuarioRol createUpdatedEntity(EntityManager em) {
        AutUsuarioRol autUsuarioRol = new AutUsuarioRol()
            .idUsuario(UPDATED_ID_USUARIO)
            .idRol(UPDATED_ID_ROL)
            .opeusr(UPDATED_OPEUSR)
            .opefecha(UPDATED_OPEFECHA)
            .opeope(UPDATED_OPEOPE);
        return autUsuarioRol;
    }

    @BeforeEach
    public void initTest() {
        autUsuarioRol = createEntity(em);
    }

    @Test
    @Transactional
    public void createAutUsuarioRol() throws Exception {
        int databaseSizeBeforeCreate = autUsuarioRolRepository.findAll().size();

        // Create the AutUsuarioRol
        AutUsuarioRolDTO autUsuarioRolDTO = autUsuarioRolMapper.toDto(autUsuarioRol);
        restAutUsuarioRolMockMvc.perform(post("/api/aut-usuario-rols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autUsuarioRolDTO)))
            .andExpect(status().isCreated());

        // Validate the AutUsuarioRol in the database
        List<AutUsuarioRol> autUsuarioRolList = autUsuarioRolRepository.findAll();
        assertThat(autUsuarioRolList).hasSize(databaseSizeBeforeCreate + 1);
        AutUsuarioRol testAutUsuarioRol = autUsuarioRolList.get(autUsuarioRolList.size() - 1);
        assertThat(testAutUsuarioRol.getIdUsuario()).isEqualTo(DEFAULT_ID_USUARIO);
        assertThat(testAutUsuarioRol.getIdRol()).isEqualTo(DEFAULT_ID_ROL);
        assertThat(testAutUsuarioRol.getOpeusr()).isEqualTo(DEFAULT_OPEUSR);
        assertThat(testAutUsuarioRol.getOpefecha()).isEqualTo(DEFAULT_OPEFECHA);
        assertThat(testAutUsuarioRol.getOpeope()).isEqualTo(DEFAULT_OPEOPE);
    }

    @Test
    @Transactional
    public void createAutUsuarioRolWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = autUsuarioRolRepository.findAll().size();

        // Create the AutUsuarioRol with an existing ID
        autUsuarioRol.setId(1L);
        AutUsuarioRolDTO autUsuarioRolDTO = autUsuarioRolMapper.toDto(autUsuarioRol);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutUsuarioRolMockMvc.perform(post("/api/aut-usuario-rols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autUsuarioRolDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AutUsuarioRol in the database
        List<AutUsuarioRol> autUsuarioRolList = autUsuarioRolRepository.findAll();
        assertThat(autUsuarioRolList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdUsuarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = autUsuarioRolRepository.findAll().size();
        // set the field null
        autUsuarioRol.setIdUsuario(null);

        // Create the AutUsuarioRol, which fails.
        AutUsuarioRolDTO autUsuarioRolDTO = autUsuarioRolMapper.toDto(autUsuarioRol);

        restAutUsuarioRolMockMvc.perform(post("/api/aut-usuario-rols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autUsuarioRolDTO)))
            .andExpect(status().isBadRequest());

        List<AutUsuarioRol> autUsuarioRolList = autUsuarioRolRepository.findAll();
        assertThat(autUsuarioRolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdRolIsRequired() throws Exception {
        int databaseSizeBeforeTest = autUsuarioRolRepository.findAll().size();
        // set the field null
        autUsuarioRol.setIdRol(null);

        // Create the AutUsuarioRol, which fails.
        AutUsuarioRolDTO autUsuarioRolDTO = autUsuarioRolMapper.toDto(autUsuarioRol);

        restAutUsuarioRolMockMvc.perform(post("/api/aut-usuario-rols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autUsuarioRolDTO)))
            .andExpect(status().isBadRequest());

        List<AutUsuarioRol> autUsuarioRolList = autUsuarioRolRepository.findAll();
        assertThat(autUsuarioRolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOpeusrIsRequired() throws Exception {
        int databaseSizeBeforeTest = autUsuarioRolRepository.findAll().size();
        // set the field null
        autUsuarioRol.setOpeusr(null);

        // Create the AutUsuarioRol, which fails.
        AutUsuarioRolDTO autUsuarioRolDTO = autUsuarioRolMapper.toDto(autUsuarioRol);

        restAutUsuarioRolMockMvc.perform(post("/api/aut-usuario-rols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autUsuarioRolDTO)))
            .andExpect(status().isBadRequest());

        List<AutUsuarioRol> autUsuarioRolList = autUsuarioRolRepository.findAll();
        assertThat(autUsuarioRolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOpefechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = autUsuarioRolRepository.findAll().size();
        // set the field null
        autUsuarioRol.setOpefecha(null);

        // Create the AutUsuarioRol, which fails.
        AutUsuarioRolDTO autUsuarioRolDTO = autUsuarioRolMapper.toDto(autUsuarioRol);

        restAutUsuarioRolMockMvc.perform(post("/api/aut-usuario-rols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autUsuarioRolDTO)))
            .andExpect(status().isBadRequest());

        List<AutUsuarioRol> autUsuarioRolList = autUsuarioRolRepository.findAll();
        assertThat(autUsuarioRolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOpeopeIsRequired() throws Exception {
        int databaseSizeBeforeTest = autUsuarioRolRepository.findAll().size();
        // set the field null
        autUsuarioRol.setOpeope(null);

        // Create the AutUsuarioRol, which fails.
        AutUsuarioRolDTO autUsuarioRolDTO = autUsuarioRolMapper.toDto(autUsuarioRol);

        restAutUsuarioRolMockMvc.perform(post("/api/aut-usuario-rols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autUsuarioRolDTO)))
            .andExpect(status().isBadRequest());

        List<AutUsuarioRol> autUsuarioRolList = autUsuarioRolRepository.findAll();
        assertThat(autUsuarioRolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAutUsuarioRols() throws Exception {
        // Initialize the database
        autUsuarioRolRepository.saveAndFlush(autUsuarioRol);

        // Get all the autUsuarioRolList
        restAutUsuarioRolMockMvc.perform(get("/api/aut-usuario-rols?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autUsuarioRol.getId().intValue())))
            .andExpect(jsonPath("$.[*].idUsuario").value(hasItem(DEFAULT_ID_USUARIO.intValue())))
            .andExpect(jsonPath("$.[*].idRol").value(hasItem(DEFAULT_ID_ROL.intValue())))
            .andExpect(jsonPath("$.[*].opeusr").value(hasItem(DEFAULT_OPEUSR.toString())))
            .andExpect(jsonPath("$.[*].opefecha").value(hasItem(DEFAULT_OPEFECHA.toString())))
            .andExpect(jsonPath("$.[*].opeope").value(hasItem(DEFAULT_OPEOPE.toString())));
    }
    
    @Test
    @Transactional
    public void getAutUsuarioRol() throws Exception {
        // Initialize the database
        autUsuarioRolRepository.saveAndFlush(autUsuarioRol);

        // Get the autUsuarioRol
        restAutUsuarioRolMockMvc.perform(get("/api/aut-usuario-rols/{id}", autUsuarioRol.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(autUsuarioRol.getId().intValue()))
            .andExpect(jsonPath("$.idUsuario").value(DEFAULT_ID_USUARIO.intValue()))
            .andExpect(jsonPath("$.idRol").value(DEFAULT_ID_ROL.intValue()))
            .andExpect(jsonPath("$.opeusr").value(DEFAULT_OPEUSR.toString()))
            .andExpect(jsonPath("$.opefecha").value(DEFAULT_OPEFECHA.toString()))
            .andExpect(jsonPath("$.opeope").value(DEFAULT_OPEOPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAutUsuarioRol() throws Exception {
        // Get the autUsuarioRol
        restAutUsuarioRolMockMvc.perform(get("/api/aut-usuario-rols/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAutUsuarioRol() throws Exception {
        // Initialize the database
        autUsuarioRolRepository.saveAndFlush(autUsuarioRol);

        int databaseSizeBeforeUpdate = autUsuarioRolRepository.findAll().size();

        // Update the autUsuarioRol
        AutUsuarioRol updatedAutUsuarioRol = autUsuarioRolRepository.findById(autUsuarioRol.getId()).get();
        // Disconnect from session so that the updates on updatedAutUsuarioRol are not directly saved in db
        em.detach(updatedAutUsuarioRol);
        updatedAutUsuarioRol
            .idUsuario(UPDATED_ID_USUARIO)
            .idRol(UPDATED_ID_ROL)
            .opeusr(UPDATED_OPEUSR)
            .opefecha(UPDATED_OPEFECHA)
            .opeope(UPDATED_OPEOPE);
        AutUsuarioRolDTO autUsuarioRolDTO = autUsuarioRolMapper.toDto(updatedAutUsuarioRol);

        restAutUsuarioRolMockMvc.perform(put("/api/aut-usuario-rols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autUsuarioRolDTO)))
            .andExpect(status().isOk());

        // Validate the AutUsuarioRol in the database
        List<AutUsuarioRol> autUsuarioRolList = autUsuarioRolRepository.findAll();
        assertThat(autUsuarioRolList).hasSize(databaseSizeBeforeUpdate);
        AutUsuarioRol testAutUsuarioRol = autUsuarioRolList.get(autUsuarioRolList.size() - 1);
        assertThat(testAutUsuarioRol.getIdUsuario()).isEqualTo(UPDATED_ID_USUARIO);
        assertThat(testAutUsuarioRol.getIdRol()).isEqualTo(UPDATED_ID_ROL);
        assertThat(testAutUsuarioRol.getOpeusr()).isEqualTo(UPDATED_OPEUSR);
        assertThat(testAutUsuarioRol.getOpefecha()).isEqualTo(UPDATED_OPEFECHA);
        assertThat(testAutUsuarioRol.getOpeope()).isEqualTo(UPDATED_OPEOPE);
    }

    @Test
    @Transactional
    public void updateNonExistingAutUsuarioRol() throws Exception {
        int databaseSizeBeforeUpdate = autUsuarioRolRepository.findAll().size();

        // Create the AutUsuarioRol
        AutUsuarioRolDTO autUsuarioRolDTO = autUsuarioRolMapper.toDto(autUsuarioRol);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutUsuarioRolMockMvc.perform(put("/api/aut-usuario-rols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autUsuarioRolDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AutUsuarioRol in the database
        List<AutUsuarioRol> autUsuarioRolList = autUsuarioRolRepository.findAll();
        assertThat(autUsuarioRolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAutUsuarioRol() throws Exception {
        // Initialize the database
        autUsuarioRolRepository.saveAndFlush(autUsuarioRol);

        int databaseSizeBeforeDelete = autUsuarioRolRepository.findAll().size();

        // Delete the autUsuarioRol
        restAutUsuarioRolMockMvc.perform(delete("/api/aut-usuario-rols/{id}", autUsuarioRol.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AutUsuarioRol> autUsuarioRolList = autUsuarioRolRepository.findAll();
        assertThat(autUsuarioRolList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutUsuarioRol.class);
        AutUsuarioRol autUsuarioRol1 = new AutUsuarioRol();
        autUsuarioRol1.setId(1L);
        AutUsuarioRol autUsuarioRol2 = new AutUsuarioRol();
        autUsuarioRol2.setId(autUsuarioRol1.getId());
        assertThat(autUsuarioRol1).isEqualTo(autUsuarioRol2);
        autUsuarioRol2.setId(2L);
        assertThat(autUsuarioRol1).isNotEqualTo(autUsuarioRol2);
        autUsuarioRol1.setId(null);
        assertThat(autUsuarioRol1).isNotEqualTo(autUsuarioRol2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutUsuarioRolDTO.class);
        AutUsuarioRolDTO autUsuarioRolDTO1 = new AutUsuarioRolDTO();
        autUsuarioRolDTO1.setId(1L);
        AutUsuarioRolDTO autUsuarioRolDTO2 = new AutUsuarioRolDTO();
        assertThat(autUsuarioRolDTO1).isNotEqualTo(autUsuarioRolDTO2);
        autUsuarioRolDTO2.setId(autUsuarioRolDTO1.getId());
        assertThat(autUsuarioRolDTO1).isEqualTo(autUsuarioRolDTO2);
        autUsuarioRolDTO2.setId(2L);
        assertThat(autUsuarioRolDTO1).isNotEqualTo(autUsuarioRolDTO2);
        autUsuarioRolDTO1.setId(null);
        assertThat(autUsuarioRolDTO1).isNotEqualTo(autUsuarioRolDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(autUsuarioRolMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(autUsuarioRolMapper.fromId(null)).isNull();
    }
}
