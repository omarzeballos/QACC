package com.qanteros.app.web.rest;

import com.qanteros.app.QaccApp;
import com.qanteros.app.domain.UsrUsuario;
import com.qanteros.app.repository.UsrUsuarioRepository;
import com.qanteros.app.service.UsrUsuarioService;
import com.qanteros.app.service.dto.UsrUsuarioDTO;
import com.qanteros.app.service.mapper.UsrUsuarioMapper;
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
 * Integration tests for the {@Link UsrUsuarioResource} REST controller.
 */
@SpringBootTest(classes = QaccApp.class)
public class UsrUsuarioResourceIT {

    private static final Long DEFAULT_ID_USUARIO = 1L;
    private static final Long UPDATED_ID_USUARIO = 2L;

    private static final String DEFAULT_CUENTA = "AAAAAAAAAA";
    private static final String UPDATED_CUENTA = "BBBBBBBBBB";

    private static final String DEFAULT_CLAVE = "AAAAAAAAAA";
    private static final String UPDATED_CLAVE = "BBBBBBBBBB";

    private static final Estado DEFAULT_ESTADO = Estado.ACTIVO;
    private static final Estado UPDATED_ESTADO = Estado.INACTIVO;

    private static final String DEFAULT_OPEUSR = "AAAAAAAAAA";
    private static final String UPDATED_OPEUSR = "BBBBBBBBBB";

    private static final Instant DEFAULT_OPEFECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_OPEFECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_OPEOPE = "AAAAAAAAAA";
    private static final String UPDATED_OPEOPE = "BBBBBBBBBB";

    @Autowired
    private UsrUsuarioRepository usrUsuarioRepository;

    @Autowired
    private UsrUsuarioMapper usrUsuarioMapper;

    @Autowired
    private UsrUsuarioService usrUsuarioService;

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

    private MockMvc restUsrUsuarioMockMvc;

    private UsrUsuario usrUsuario;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UsrUsuarioResource usrUsuarioResource = new UsrUsuarioResource(usrUsuarioService);
        this.restUsrUsuarioMockMvc = MockMvcBuilders.standaloneSetup(usrUsuarioResource)
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
    public static UsrUsuario createEntity(EntityManager em) {
        UsrUsuario usrUsuario = new UsrUsuario()
            .idUsuario(DEFAULT_ID_USUARIO)
            .cuenta(DEFAULT_CUENTA)
            .clave(DEFAULT_CLAVE)
            .estado(DEFAULT_ESTADO)
            .opeusr(DEFAULT_OPEUSR)
            .opefecha(DEFAULT_OPEFECHA)
            .opeope(DEFAULT_OPEOPE);
        return usrUsuario;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UsrUsuario createUpdatedEntity(EntityManager em) {
        UsrUsuario usrUsuario = new UsrUsuario()
            .idUsuario(UPDATED_ID_USUARIO)
            .cuenta(UPDATED_CUENTA)
            .clave(UPDATED_CLAVE)
            .estado(UPDATED_ESTADO)
            .opeusr(UPDATED_OPEUSR)
            .opefecha(UPDATED_OPEFECHA)
            .opeope(UPDATED_OPEOPE);
        return usrUsuario;
    }

    @BeforeEach
    public void initTest() {
        usrUsuario = createEntity(em);
    }

    @Test
    @Transactional
    public void createUsrUsuario() throws Exception {
        int databaseSizeBeforeCreate = usrUsuarioRepository.findAll().size();

        // Create the UsrUsuario
        UsrUsuarioDTO usrUsuarioDTO = usrUsuarioMapper.toDto(usrUsuario);
        restUsrUsuarioMockMvc.perform(post("/api/usr-usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usrUsuarioDTO)))
            .andExpect(status().isCreated());

        // Validate the UsrUsuario in the database
        List<UsrUsuario> usrUsuarioList = usrUsuarioRepository.findAll();
        assertThat(usrUsuarioList).hasSize(databaseSizeBeforeCreate + 1);
        UsrUsuario testUsrUsuario = usrUsuarioList.get(usrUsuarioList.size() - 1);
        assertThat(testUsrUsuario.getIdUsuario()).isEqualTo(DEFAULT_ID_USUARIO);
        assertThat(testUsrUsuario.getCuenta()).isEqualTo(DEFAULT_CUENTA);
        assertThat(testUsrUsuario.getClave()).isEqualTo(DEFAULT_CLAVE);
        assertThat(testUsrUsuario.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testUsrUsuario.getOpeusr()).isEqualTo(DEFAULT_OPEUSR);
        assertThat(testUsrUsuario.getOpefecha()).isEqualTo(DEFAULT_OPEFECHA);
        assertThat(testUsrUsuario.getOpeope()).isEqualTo(DEFAULT_OPEOPE);
    }

    @Test
    @Transactional
    public void createUsrUsuarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = usrUsuarioRepository.findAll().size();

        // Create the UsrUsuario with an existing ID
        usrUsuario.setId(1L);
        UsrUsuarioDTO usrUsuarioDTO = usrUsuarioMapper.toDto(usrUsuario);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsrUsuarioMockMvc.perform(post("/api/usr-usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usrUsuarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UsrUsuario in the database
        List<UsrUsuario> usrUsuarioList = usrUsuarioRepository.findAll();
        assertThat(usrUsuarioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdUsuarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = usrUsuarioRepository.findAll().size();
        // set the field null
        usrUsuario.setIdUsuario(null);

        // Create the UsrUsuario, which fails.
        UsrUsuarioDTO usrUsuarioDTO = usrUsuarioMapper.toDto(usrUsuario);

        restUsrUsuarioMockMvc.perform(post("/api/usr-usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usrUsuarioDTO)))
            .andExpect(status().isBadRequest());

        List<UsrUsuario> usrUsuarioList = usrUsuarioRepository.findAll();
        assertThat(usrUsuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCuentaIsRequired() throws Exception {
        int databaseSizeBeforeTest = usrUsuarioRepository.findAll().size();
        // set the field null
        usrUsuario.setCuenta(null);

        // Create the UsrUsuario, which fails.
        UsrUsuarioDTO usrUsuarioDTO = usrUsuarioMapper.toDto(usrUsuario);

        restUsrUsuarioMockMvc.perform(post("/api/usr-usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usrUsuarioDTO)))
            .andExpect(status().isBadRequest());

        List<UsrUsuario> usrUsuarioList = usrUsuarioRepository.findAll();
        assertThat(usrUsuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClaveIsRequired() throws Exception {
        int databaseSizeBeforeTest = usrUsuarioRepository.findAll().size();
        // set the field null
        usrUsuario.setClave(null);

        // Create the UsrUsuario, which fails.
        UsrUsuarioDTO usrUsuarioDTO = usrUsuarioMapper.toDto(usrUsuario);

        restUsrUsuarioMockMvc.perform(post("/api/usr-usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usrUsuarioDTO)))
            .andExpect(status().isBadRequest());

        List<UsrUsuario> usrUsuarioList = usrUsuarioRepository.findAll();
        assertThat(usrUsuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = usrUsuarioRepository.findAll().size();
        // set the field null
        usrUsuario.setEstado(null);

        // Create the UsrUsuario, which fails.
        UsrUsuarioDTO usrUsuarioDTO = usrUsuarioMapper.toDto(usrUsuario);

        restUsrUsuarioMockMvc.perform(post("/api/usr-usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usrUsuarioDTO)))
            .andExpect(status().isBadRequest());

        List<UsrUsuario> usrUsuarioList = usrUsuarioRepository.findAll();
        assertThat(usrUsuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOpeusrIsRequired() throws Exception {
        int databaseSizeBeforeTest = usrUsuarioRepository.findAll().size();
        // set the field null
        usrUsuario.setOpeusr(null);

        // Create the UsrUsuario, which fails.
        UsrUsuarioDTO usrUsuarioDTO = usrUsuarioMapper.toDto(usrUsuario);

        restUsrUsuarioMockMvc.perform(post("/api/usr-usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usrUsuarioDTO)))
            .andExpect(status().isBadRequest());

        List<UsrUsuario> usrUsuarioList = usrUsuarioRepository.findAll();
        assertThat(usrUsuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOpefechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = usrUsuarioRepository.findAll().size();
        // set the field null
        usrUsuario.setOpefecha(null);

        // Create the UsrUsuario, which fails.
        UsrUsuarioDTO usrUsuarioDTO = usrUsuarioMapper.toDto(usrUsuario);

        restUsrUsuarioMockMvc.perform(post("/api/usr-usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usrUsuarioDTO)))
            .andExpect(status().isBadRequest());

        List<UsrUsuario> usrUsuarioList = usrUsuarioRepository.findAll();
        assertThat(usrUsuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOpeopeIsRequired() throws Exception {
        int databaseSizeBeforeTest = usrUsuarioRepository.findAll().size();
        // set the field null
        usrUsuario.setOpeope(null);

        // Create the UsrUsuario, which fails.
        UsrUsuarioDTO usrUsuarioDTO = usrUsuarioMapper.toDto(usrUsuario);

        restUsrUsuarioMockMvc.perform(post("/api/usr-usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usrUsuarioDTO)))
            .andExpect(status().isBadRequest());

        List<UsrUsuario> usrUsuarioList = usrUsuarioRepository.findAll();
        assertThat(usrUsuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUsrUsuarios() throws Exception {
        // Initialize the database
        usrUsuarioRepository.saveAndFlush(usrUsuario);

        // Get all the usrUsuarioList
        restUsrUsuarioMockMvc.perform(get("/api/usr-usuarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usrUsuario.getId().intValue())))
            .andExpect(jsonPath("$.[*].idUsuario").value(hasItem(DEFAULT_ID_USUARIO.intValue())))
            .andExpect(jsonPath("$.[*].cuenta").value(hasItem(DEFAULT_CUENTA.toString())))
            .andExpect(jsonPath("$.[*].clave").value(hasItem(DEFAULT_CLAVE.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].opeusr").value(hasItem(DEFAULT_OPEUSR.toString())))
            .andExpect(jsonPath("$.[*].opefecha").value(hasItem(DEFAULT_OPEFECHA.toString())))
            .andExpect(jsonPath("$.[*].opeope").value(hasItem(DEFAULT_OPEOPE.toString())));
    }
    
    @Test
    @Transactional
    public void getUsrUsuario() throws Exception {
        // Initialize the database
        usrUsuarioRepository.saveAndFlush(usrUsuario);

        // Get the usrUsuario
        restUsrUsuarioMockMvc.perform(get("/api/usr-usuarios/{id}", usrUsuario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(usrUsuario.getId().intValue()))
            .andExpect(jsonPath("$.idUsuario").value(DEFAULT_ID_USUARIO.intValue()))
            .andExpect(jsonPath("$.cuenta").value(DEFAULT_CUENTA.toString()))
            .andExpect(jsonPath("$.clave").value(DEFAULT_CLAVE.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.opeusr").value(DEFAULT_OPEUSR.toString()))
            .andExpect(jsonPath("$.opefecha").value(DEFAULT_OPEFECHA.toString()))
            .andExpect(jsonPath("$.opeope").value(DEFAULT_OPEOPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUsrUsuario() throws Exception {
        // Get the usrUsuario
        restUsrUsuarioMockMvc.perform(get("/api/usr-usuarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUsrUsuario() throws Exception {
        // Initialize the database
        usrUsuarioRepository.saveAndFlush(usrUsuario);

        int databaseSizeBeforeUpdate = usrUsuarioRepository.findAll().size();

        // Update the usrUsuario
        UsrUsuario updatedUsrUsuario = usrUsuarioRepository.findById(usrUsuario.getId()).get();
        // Disconnect from session so that the updates on updatedUsrUsuario are not directly saved in db
        em.detach(updatedUsrUsuario);
        updatedUsrUsuario
            .idUsuario(UPDATED_ID_USUARIO)
            .cuenta(UPDATED_CUENTA)
            .clave(UPDATED_CLAVE)
            .estado(UPDATED_ESTADO)
            .opeusr(UPDATED_OPEUSR)
            .opefecha(UPDATED_OPEFECHA)
            .opeope(UPDATED_OPEOPE);
        UsrUsuarioDTO usrUsuarioDTO = usrUsuarioMapper.toDto(updatedUsrUsuario);

        restUsrUsuarioMockMvc.perform(put("/api/usr-usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usrUsuarioDTO)))
            .andExpect(status().isOk());

        // Validate the UsrUsuario in the database
        List<UsrUsuario> usrUsuarioList = usrUsuarioRepository.findAll();
        assertThat(usrUsuarioList).hasSize(databaseSizeBeforeUpdate);
        UsrUsuario testUsrUsuario = usrUsuarioList.get(usrUsuarioList.size() - 1);
        assertThat(testUsrUsuario.getIdUsuario()).isEqualTo(UPDATED_ID_USUARIO);
        assertThat(testUsrUsuario.getCuenta()).isEqualTo(UPDATED_CUENTA);
        assertThat(testUsrUsuario.getClave()).isEqualTo(UPDATED_CLAVE);
        assertThat(testUsrUsuario.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testUsrUsuario.getOpeusr()).isEqualTo(UPDATED_OPEUSR);
        assertThat(testUsrUsuario.getOpefecha()).isEqualTo(UPDATED_OPEFECHA);
        assertThat(testUsrUsuario.getOpeope()).isEqualTo(UPDATED_OPEOPE);
    }

    @Test
    @Transactional
    public void updateNonExistingUsrUsuario() throws Exception {
        int databaseSizeBeforeUpdate = usrUsuarioRepository.findAll().size();

        // Create the UsrUsuario
        UsrUsuarioDTO usrUsuarioDTO = usrUsuarioMapper.toDto(usrUsuario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsrUsuarioMockMvc.perform(put("/api/usr-usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usrUsuarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UsrUsuario in the database
        List<UsrUsuario> usrUsuarioList = usrUsuarioRepository.findAll();
        assertThat(usrUsuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUsrUsuario() throws Exception {
        // Initialize the database
        usrUsuarioRepository.saveAndFlush(usrUsuario);

        int databaseSizeBeforeDelete = usrUsuarioRepository.findAll().size();

        // Delete the usrUsuario
        restUsrUsuarioMockMvc.perform(delete("/api/usr-usuarios/{id}", usrUsuario.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UsrUsuario> usrUsuarioList = usrUsuarioRepository.findAll();
        assertThat(usrUsuarioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UsrUsuario.class);
        UsrUsuario usrUsuario1 = new UsrUsuario();
        usrUsuario1.setId(1L);
        UsrUsuario usrUsuario2 = new UsrUsuario();
        usrUsuario2.setId(usrUsuario1.getId());
        assertThat(usrUsuario1).isEqualTo(usrUsuario2);
        usrUsuario2.setId(2L);
        assertThat(usrUsuario1).isNotEqualTo(usrUsuario2);
        usrUsuario1.setId(null);
        assertThat(usrUsuario1).isNotEqualTo(usrUsuario2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UsrUsuarioDTO.class);
        UsrUsuarioDTO usrUsuarioDTO1 = new UsrUsuarioDTO();
        usrUsuarioDTO1.setId(1L);
        UsrUsuarioDTO usrUsuarioDTO2 = new UsrUsuarioDTO();
        assertThat(usrUsuarioDTO1).isNotEqualTo(usrUsuarioDTO2);
        usrUsuarioDTO2.setId(usrUsuarioDTO1.getId());
        assertThat(usrUsuarioDTO1).isEqualTo(usrUsuarioDTO2);
        usrUsuarioDTO2.setId(2L);
        assertThat(usrUsuarioDTO1).isNotEqualTo(usrUsuarioDTO2);
        usrUsuarioDTO1.setId(null);
        assertThat(usrUsuarioDTO1).isNotEqualTo(usrUsuarioDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(usrUsuarioMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(usrUsuarioMapper.fromId(null)).isNull();
    }
}
