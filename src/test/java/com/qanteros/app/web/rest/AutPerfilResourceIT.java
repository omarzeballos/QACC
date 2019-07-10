package com.qanteros.app.web.rest;

import com.qanteros.app.QaccApp;
import com.qanteros.app.domain.AutPerfil;
import com.qanteros.app.repository.AutPerfilRepository;
import com.qanteros.app.service.AutPerfilService;
import com.qanteros.app.service.dto.AutPerfilDTO;
import com.qanteros.app.service.mapper.AutPerfilMapper;
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
 * Integration tests for the {@Link AutPerfilResource} REST controller.
 */
@SpringBootTest(classes = QaccApp.class)
public class AutPerfilResourceIT {

    private static final Long DEFAULT_ID_PERFIL = 1L;
    private static final Long UPDATED_ID_PERFIL = 2L;

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
    private AutPerfilRepository autPerfilRepository;

    @Autowired
    private AutPerfilMapper autPerfilMapper;

    @Autowired
    private AutPerfilService autPerfilService;

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

    private MockMvc restAutPerfilMockMvc;

    private AutPerfil autPerfil;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AutPerfilResource autPerfilResource = new AutPerfilResource(autPerfilService);
        this.restAutPerfilMockMvc = MockMvcBuilders.standaloneSetup(autPerfilResource)
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
    public static AutPerfil createEntity(EntityManager em) {
        AutPerfil autPerfil = new AutPerfil()
            .idPerfil(DEFAULT_ID_PERFIL)
            .nombre(DEFAULT_NOMBRE)
            .estado(DEFAULT_ESTADO)
            .opeusr(DEFAULT_OPEUSR)
            .opefecha(DEFAULT_OPEFECHA)
            .opeope(DEFAULT_OPEOPE);
        return autPerfil;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AutPerfil createUpdatedEntity(EntityManager em) {
        AutPerfil autPerfil = new AutPerfil()
            .idPerfil(UPDATED_ID_PERFIL)
            .nombre(UPDATED_NOMBRE)
            .estado(UPDATED_ESTADO)
            .opeusr(UPDATED_OPEUSR)
            .opefecha(UPDATED_OPEFECHA)
            .opeope(UPDATED_OPEOPE);
        return autPerfil;
    }

    @BeforeEach
    public void initTest() {
        autPerfil = createEntity(em);
    }

    @Test
    @Transactional
    public void createAutPerfil() throws Exception {
        int databaseSizeBeforeCreate = autPerfilRepository.findAll().size();

        // Create the AutPerfil
        AutPerfilDTO autPerfilDTO = autPerfilMapper.toDto(autPerfil);
        restAutPerfilMockMvc.perform(post("/api/aut-perfils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autPerfilDTO)))
            .andExpect(status().isCreated());

        // Validate the AutPerfil in the database
        List<AutPerfil> autPerfilList = autPerfilRepository.findAll();
        assertThat(autPerfilList).hasSize(databaseSizeBeforeCreate + 1);
        AutPerfil testAutPerfil = autPerfilList.get(autPerfilList.size() - 1);
        assertThat(testAutPerfil.getIdPerfil()).isEqualTo(DEFAULT_ID_PERFIL);
        assertThat(testAutPerfil.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testAutPerfil.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testAutPerfil.getOpeusr()).isEqualTo(DEFAULT_OPEUSR);
        assertThat(testAutPerfil.getOpefecha()).isEqualTo(DEFAULT_OPEFECHA);
        assertThat(testAutPerfil.getOpeope()).isEqualTo(DEFAULT_OPEOPE);
    }

    @Test
    @Transactional
    public void createAutPerfilWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = autPerfilRepository.findAll().size();

        // Create the AutPerfil with an existing ID
        autPerfil.setId(1L);
        AutPerfilDTO autPerfilDTO = autPerfilMapper.toDto(autPerfil);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutPerfilMockMvc.perform(post("/api/aut-perfils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autPerfilDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AutPerfil in the database
        List<AutPerfil> autPerfilList = autPerfilRepository.findAll();
        assertThat(autPerfilList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdPerfilIsRequired() throws Exception {
        int databaseSizeBeforeTest = autPerfilRepository.findAll().size();
        // set the field null
        autPerfil.setIdPerfil(null);

        // Create the AutPerfil, which fails.
        AutPerfilDTO autPerfilDTO = autPerfilMapper.toDto(autPerfil);

        restAutPerfilMockMvc.perform(post("/api/aut-perfils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autPerfilDTO)))
            .andExpect(status().isBadRequest());

        List<AutPerfil> autPerfilList = autPerfilRepository.findAll();
        assertThat(autPerfilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = autPerfilRepository.findAll().size();
        // set the field null
        autPerfil.setNombre(null);

        // Create the AutPerfil, which fails.
        AutPerfilDTO autPerfilDTO = autPerfilMapper.toDto(autPerfil);

        restAutPerfilMockMvc.perform(post("/api/aut-perfils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autPerfilDTO)))
            .andExpect(status().isBadRequest());

        List<AutPerfil> autPerfilList = autPerfilRepository.findAll();
        assertThat(autPerfilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = autPerfilRepository.findAll().size();
        // set the field null
        autPerfil.setEstado(null);

        // Create the AutPerfil, which fails.
        AutPerfilDTO autPerfilDTO = autPerfilMapper.toDto(autPerfil);

        restAutPerfilMockMvc.perform(post("/api/aut-perfils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autPerfilDTO)))
            .andExpect(status().isBadRequest());

        List<AutPerfil> autPerfilList = autPerfilRepository.findAll();
        assertThat(autPerfilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOpeusrIsRequired() throws Exception {
        int databaseSizeBeforeTest = autPerfilRepository.findAll().size();
        // set the field null
        autPerfil.setOpeusr(null);

        // Create the AutPerfil, which fails.
        AutPerfilDTO autPerfilDTO = autPerfilMapper.toDto(autPerfil);

        restAutPerfilMockMvc.perform(post("/api/aut-perfils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autPerfilDTO)))
            .andExpect(status().isBadRequest());

        List<AutPerfil> autPerfilList = autPerfilRepository.findAll();
        assertThat(autPerfilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOpefechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = autPerfilRepository.findAll().size();
        // set the field null
        autPerfil.setOpefecha(null);

        // Create the AutPerfil, which fails.
        AutPerfilDTO autPerfilDTO = autPerfilMapper.toDto(autPerfil);

        restAutPerfilMockMvc.perform(post("/api/aut-perfils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autPerfilDTO)))
            .andExpect(status().isBadRequest());

        List<AutPerfil> autPerfilList = autPerfilRepository.findAll();
        assertThat(autPerfilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOpeopeIsRequired() throws Exception {
        int databaseSizeBeforeTest = autPerfilRepository.findAll().size();
        // set the field null
        autPerfil.setOpeope(null);

        // Create the AutPerfil, which fails.
        AutPerfilDTO autPerfilDTO = autPerfilMapper.toDto(autPerfil);

        restAutPerfilMockMvc.perform(post("/api/aut-perfils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autPerfilDTO)))
            .andExpect(status().isBadRequest());

        List<AutPerfil> autPerfilList = autPerfilRepository.findAll();
        assertThat(autPerfilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAutPerfils() throws Exception {
        // Initialize the database
        autPerfilRepository.saveAndFlush(autPerfil);

        // Get all the autPerfilList
        restAutPerfilMockMvc.perform(get("/api/aut-perfils?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autPerfil.getId().intValue())))
            .andExpect(jsonPath("$.[*].idPerfil").value(hasItem(DEFAULT_ID_PERFIL.intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].opeusr").value(hasItem(DEFAULT_OPEUSR.toString())))
            .andExpect(jsonPath("$.[*].opefecha").value(hasItem(DEFAULT_OPEFECHA.toString())))
            .andExpect(jsonPath("$.[*].opeope").value(hasItem(DEFAULT_OPEOPE.toString())));
    }
    
    @Test
    @Transactional
    public void getAutPerfil() throws Exception {
        // Initialize the database
        autPerfilRepository.saveAndFlush(autPerfil);

        // Get the autPerfil
        restAutPerfilMockMvc.perform(get("/api/aut-perfils/{id}", autPerfil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(autPerfil.getId().intValue()))
            .andExpect(jsonPath("$.idPerfil").value(DEFAULT_ID_PERFIL.intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.opeusr").value(DEFAULT_OPEUSR.toString()))
            .andExpect(jsonPath("$.opefecha").value(DEFAULT_OPEFECHA.toString()))
            .andExpect(jsonPath("$.opeope").value(DEFAULT_OPEOPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAutPerfil() throws Exception {
        // Get the autPerfil
        restAutPerfilMockMvc.perform(get("/api/aut-perfils/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAutPerfil() throws Exception {
        // Initialize the database
        autPerfilRepository.saveAndFlush(autPerfil);

        int databaseSizeBeforeUpdate = autPerfilRepository.findAll().size();

        // Update the autPerfil
        AutPerfil updatedAutPerfil = autPerfilRepository.findById(autPerfil.getId()).get();
        // Disconnect from session so that the updates on updatedAutPerfil are not directly saved in db
        em.detach(updatedAutPerfil);
        updatedAutPerfil
            .idPerfil(UPDATED_ID_PERFIL)
            .nombre(UPDATED_NOMBRE)
            .estado(UPDATED_ESTADO)
            .opeusr(UPDATED_OPEUSR)
            .opefecha(UPDATED_OPEFECHA)
            .opeope(UPDATED_OPEOPE);
        AutPerfilDTO autPerfilDTO = autPerfilMapper.toDto(updatedAutPerfil);

        restAutPerfilMockMvc.perform(put("/api/aut-perfils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autPerfilDTO)))
            .andExpect(status().isOk());

        // Validate the AutPerfil in the database
        List<AutPerfil> autPerfilList = autPerfilRepository.findAll();
        assertThat(autPerfilList).hasSize(databaseSizeBeforeUpdate);
        AutPerfil testAutPerfil = autPerfilList.get(autPerfilList.size() - 1);
        assertThat(testAutPerfil.getIdPerfil()).isEqualTo(UPDATED_ID_PERFIL);
        assertThat(testAutPerfil.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testAutPerfil.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testAutPerfil.getOpeusr()).isEqualTo(UPDATED_OPEUSR);
        assertThat(testAutPerfil.getOpefecha()).isEqualTo(UPDATED_OPEFECHA);
        assertThat(testAutPerfil.getOpeope()).isEqualTo(UPDATED_OPEOPE);
    }

    @Test
    @Transactional
    public void updateNonExistingAutPerfil() throws Exception {
        int databaseSizeBeforeUpdate = autPerfilRepository.findAll().size();

        // Create the AutPerfil
        AutPerfilDTO autPerfilDTO = autPerfilMapper.toDto(autPerfil);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutPerfilMockMvc.perform(put("/api/aut-perfils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autPerfilDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AutPerfil in the database
        List<AutPerfil> autPerfilList = autPerfilRepository.findAll();
        assertThat(autPerfilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAutPerfil() throws Exception {
        // Initialize the database
        autPerfilRepository.saveAndFlush(autPerfil);

        int databaseSizeBeforeDelete = autPerfilRepository.findAll().size();

        // Delete the autPerfil
        restAutPerfilMockMvc.perform(delete("/api/aut-perfils/{id}", autPerfil.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AutPerfil> autPerfilList = autPerfilRepository.findAll();
        assertThat(autPerfilList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutPerfil.class);
        AutPerfil autPerfil1 = new AutPerfil();
        autPerfil1.setId(1L);
        AutPerfil autPerfil2 = new AutPerfil();
        autPerfil2.setId(autPerfil1.getId());
        assertThat(autPerfil1).isEqualTo(autPerfil2);
        autPerfil2.setId(2L);
        assertThat(autPerfil1).isNotEqualTo(autPerfil2);
        autPerfil1.setId(null);
        assertThat(autPerfil1).isNotEqualTo(autPerfil2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutPerfilDTO.class);
        AutPerfilDTO autPerfilDTO1 = new AutPerfilDTO();
        autPerfilDTO1.setId(1L);
        AutPerfilDTO autPerfilDTO2 = new AutPerfilDTO();
        assertThat(autPerfilDTO1).isNotEqualTo(autPerfilDTO2);
        autPerfilDTO2.setId(autPerfilDTO1.getId());
        assertThat(autPerfilDTO1).isEqualTo(autPerfilDTO2);
        autPerfilDTO2.setId(2L);
        assertThat(autPerfilDTO1).isNotEqualTo(autPerfilDTO2);
        autPerfilDTO1.setId(null);
        assertThat(autPerfilDTO1).isNotEqualTo(autPerfilDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(autPerfilMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(autPerfilMapper.fromId(null)).isNull();
    }
}
