package io.protone.web.api.library;

import io.protone.ProtoneApp;
import io.protone.web.rest.dto.library.LibMarkerConfigurationDTO;
import io.protone.util.TestUtil;
import io.protone.domain.CfgMarkerConfiguration;
import io.protone.domain.CorNetwork;
import io.protone.domain.enumeration.LibMarkerTypeEnum;
import io.protone.repository.cfg.CfgMarkerConfigurationRepository;
import io.protone.service.cor.CorNetworkService;
import io.protone.web.rest.errors.ExceptionTranslator;
import io.protone.web.api.library.impl.LibraryMarkerConfigurationResourceImpl;
import io.protone.web.rest.mapper.CfgMarkerConfigurationMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static io.protone.web.api.cor.CorNetworkResourceIntTest.TEST_NETWORK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by lukaszozimek on 02/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LibraryMarkerConfigurationResourceImplTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_KEYBOARD_SHORTCUT = "AAAAAAAAAA";
    private static final String UPDATED_KEYBOARD_SHORTCUT = "BBBBBBBBBB";

    private static final LibMarkerTypeEnum DEFAULT_TYPE = LibMarkerTypeEnum.MT_BASIC;
    private static final LibMarkerTypeEnum UPDATED_TYPE = LibMarkerTypeEnum.MT_INTRO;

    @Autowired
    private CfgMarkerConfigurationRepository cfgMarkerConfigurationRepository;

    @Autowired
    private CfgMarkerConfigurationMapper cfgMarkerConfigurationMapper;

    @Autowired
    private CorNetworkService corNetworkService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCfgMarkerConfigurationMockMvc;

    private CfgMarkerConfiguration cfgMarkerConfiguration;

    private CorNetwork corNetwork;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LibraryMarkerConfigurationResourceImpl libraryMarkerConfigurationResource = new LibraryMarkerConfigurationResourceImpl();

        ReflectionTestUtils.setField(libraryMarkerConfigurationResource, "cfgMarkerConfigurationRepository", cfgMarkerConfigurationRepository);
        ReflectionTestUtils.setField(libraryMarkerConfigurationResource, "cfgMarkerConfigurationMapper", cfgMarkerConfigurationMapper);
        ReflectionTestUtils.setField(libraryMarkerConfigurationResource, "corNetworkService", corNetworkService);

        corNetwork = new CorNetwork().shortcut(TEST_NETWORK);
        corNetwork.setId(1L);


        this.restCfgMarkerConfigurationMockMvc = MockMvcBuilders.standaloneSetup(libraryMarkerConfigurationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CfgMarkerConfiguration createEntity(EntityManager em) {
        CfgMarkerConfiguration cfgMarkerConfiguration = new CfgMarkerConfiguration()
            .name(DEFAULT_NAME)
            .displayName(DEFAULT_DISPLAY_NAME)
            .color(DEFAULT_COLOR)
            .keyboardShortcut(DEFAULT_KEYBOARD_SHORTCUT)
            .type(DEFAULT_TYPE);
        return cfgMarkerConfiguration;
    }

    @Before
    public void initTest() {
        cfgMarkerConfiguration = createEntity(em).network(corNetwork);
    }

    @Test
    @Transactional
    public void createCfgMarkerConfiguration() throws Exception {
        int databaseSizeBeforeCreate = cfgMarkerConfigurationRepository.findAll().size();

        // Create the CfgMarkerConfiguration
        LibMarkerConfigurationDTO cfgMarkerConfigurationDTO = cfgMarkerConfigurationMapper.DB2DTO(cfgMarkerConfiguration);

        restCfgMarkerConfigurationMockMvc.perform(post("/api/v1/network/{networkShortcut}/configuration/library/marker", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cfgMarkerConfigurationDTO)))
            .andExpect(status().isCreated());

        // Validate the CfgMarkerConfiguration in the database
        List<CfgMarkerConfiguration> cfgMarkerConfigurationList = cfgMarkerConfigurationRepository.findAll();
        assertThat(cfgMarkerConfigurationList).hasSize(databaseSizeBeforeCreate + 1);
        CfgMarkerConfiguration testCfgMarkerConfiguration = cfgMarkerConfigurationList.get(cfgMarkerConfigurationList.size() - 1);
        assertThat(testCfgMarkerConfiguration.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCfgMarkerConfiguration.getDisplayName()).isEqualTo(DEFAULT_DISPLAY_NAME);
        assertThat(testCfgMarkerConfiguration.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testCfgMarkerConfiguration.getKeyboardShortcut()).isEqualTo(DEFAULT_KEYBOARD_SHORTCUT);
        assertThat(testCfgMarkerConfiguration.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createCfgMarkerConfigurationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cfgMarkerConfigurationRepository.findAll().size();

        // Create the CfgMarkerConfiguration with an existing ID
        CfgMarkerConfiguration existingCfgMarkerConfiguration = new CfgMarkerConfiguration();
        existingCfgMarkerConfiguration.setId(1L);
        LibMarkerConfigurationDTO existingCfgMarkerConfigurationDTO = cfgMarkerConfigurationMapper.DB2DTO(existingCfgMarkerConfiguration);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCfgMarkerConfigurationMockMvc.perform(post("/api/v1/network/{networkShortcut}/configuration/library/marker", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCfgMarkerConfigurationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CfgMarkerConfiguration> cfgMarkerConfigurationList = cfgMarkerConfigurationRepository.findAll();
        assertThat(cfgMarkerConfigurationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cfgMarkerConfigurationRepository.findAll().size();
        // set the field null
        cfgMarkerConfiguration.setName(null);

        // Create the CfgMarkerConfiguration, which fails.
        LibMarkerConfigurationDTO cfgMarkerConfigurationDTO = cfgMarkerConfigurationMapper.DB2DTO(cfgMarkerConfiguration);

        restCfgMarkerConfigurationMockMvc.perform(post("/api/v1/network/{networkShortcut}/configuration/library/marker", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cfgMarkerConfigurationDTO)))
            .andExpect(status().isBadRequest());

        List<CfgMarkerConfiguration> cfgMarkerConfigurationList = cfgMarkerConfigurationRepository.findAll();
        assertThat(cfgMarkerConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDisplayNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cfgMarkerConfigurationRepository.findAll().size();
        // set the field null
        cfgMarkerConfiguration.setDisplayName(null);

        // Create the CfgMarkerConfiguration, which fails.
        LibMarkerConfigurationDTO cfgMarkerConfigurationDTO = cfgMarkerConfigurationMapper.DB2DTO(cfgMarkerConfiguration);

        restCfgMarkerConfigurationMockMvc.perform(post("/api/v1/network/{networkShortcut}/configuration/library/marker", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cfgMarkerConfigurationDTO)))
            .andExpect(status().isBadRequest());

        List<CfgMarkerConfiguration> cfgMarkerConfigurationList = cfgMarkerConfigurationRepository.findAll();
        assertThat(cfgMarkerConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkColorIsRequired() throws Exception {
        int databaseSizeBeforeTest = cfgMarkerConfigurationRepository.findAll().size();
        // set the field null
        cfgMarkerConfiguration.setColor(null);

        // Create the CfgMarkerConfiguration, which fails.
        LibMarkerConfigurationDTO cfgMarkerConfigurationDTO = cfgMarkerConfigurationMapper.DB2DTO(cfgMarkerConfiguration);

        restCfgMarkerConfigurationMockMvc.perform(post("/api/v1/network/{networkShortcut}/configuration/library/marker", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cfgMarkerConfigurationDTO)))
            .andExpect(status().isBadRequest());

        List<CfgMarkerConfiguration> cfgMarkerConfigurationList = cfgMarkerConfigurationRepository.findAll();
        assertThat(cfgMarkerConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKeyboardShortcutIsRequired() throws Exception {
        int databaseSizeBeforeTest = cfgMarkerConfigurationRepository.findAll().size();
        // set the field null
        cfgMarkerConfiguration.setKeyboardShortcut(null);

        // Create the CfgMarkerConfiguration, which fails.
        LibMarkerConfigurationDTO cfgMarkerConfigurationDTO = cfgMarkerConfigurationMapper.DB2DTO(cfgMarkerConfiguration);

        restCfgMarkerConfigurationMockMvc.perform(post("/api/v1/network/{networkShortcut}/configuration/library/marker", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cfgMarkerConfigurationDTO)))
            .andExpect(status().isBadRequest());

        List<CfgMarkerConfiguration> cfgMarkerConfigurationList = cfgMarkerConfigurationRepository.findAll();
        assertThat(cfgMarkerConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cfgMarkerConfigurationRepository.findAll().size();
        // set the field null
        cfgMarkerConfiguration.setType(null);

        // Create the CfgMarkerConfiguration, which fails.
        LibMarkerConfigurationDTO cfgMarkerConfigurationDTO = cfgMarkerConfigurationMapper.DB2DTO(cfgMarkerConfiguration);

        restCfgMarkerConfigurationMockMvc.perform(post("/api/v1/network/{networkShortcut}/configuration/library/marker", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cfgMarkerConfigurationDTO)))
            .andExpect(status().isBadRequest());

        List<CfgMarkerConfiguration> cfgMarkerConfigurationList = cfgMarkerConfigurationRepository.findAll();
        assertThat(cfgMarkerConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCfgMarkerConfigurations() throws Exception {
        // Initialize the database
        cfgMarkerConfigurationRepository.saveAndFlush(cfgMarkerConfiguration.network(corNetwork));

        // Get all the cfgMarkerConfigurationList
        restCfgMarkerConfigurationMockMvc.perform(get("/api/v1/network/{networkShortcut}/configuration/library/marker?sort=id,desc", corNetwork.getShortcut()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cfgMarkerConfiguration.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].displayName").value(hasItem(DEFAULT_DISPLAY_NAME.toString())))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR.toString())))
            .andExpect(jsonPath("$.[*].keyboardShortcut").value(hasItem(DEFAULT_KEYBOARD_SHORTCUT.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getCfgMarkerConfiguration() throws Exception {
        // Initialize the database
        cfgMarkerConfigurationRepository.saveAndFlush(cfgMarkerConfiguration.network(corNetwork));

        // Get the cfgMarkerConfiguration
        restCfgMarkerConfigurationMockMvc.perform(get("/api/v1/network/{networkShortcut}/configuration/library/marker/{id}", corNetwork.getShortcut(), cfgMarkerConfiguration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cfgMarkerConfiguration.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.displayName").value(DEFAULT_DISPLAY_NAME.toString()))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR.toString()))
            .andExpect(jsonPath("$.keyboardShortcut").value(DEFAULT_KEYBOARD_SHORTCUT.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCfgMarkerConfiguration() throws Exception {
        // Get the cfgMarkerConfiguration
        restCfgMarkerConfigurationMockMvc.perform(get("/api/v1/network/{networkShortcut}/configuration/library/marker/{id}", corNetwork.getShortcut(), Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCfgMarkerConfiguration() throws Exception {
        // Initialize the database
        cfgMarkerConfigurationRepository.saveAndFlush(cfgMarkerConfiguration.network(corNetwork));
        int databaseSizeBeforeUpdate = cfgMarkerConfigurationRepository.findAll().size();

        // Update the cfgMarkerConfiguration
        CfgMarkerConfiguration updatedCfgMarkerConfiguration = cfgMarkerConfigurationRepository.findOne(cfgMarkerConfiguration.getId());
        updatedCfgMarkerConfiguration
            .name(UPDATED_NAME)
            .displayName(UPDATED_DISPLAY_NAME)
            .color(UPDATED_COLOR)
            .keyboardShortcut(UPDATED_KEYBOARD_SHORTCUT)
            .type(UPDATED_TYPE);
        LibMarkerConfigurationDTO cfgMarkerConfigurationDTO = cfgMarkerConfigurationMapper.DB2DTO(updatedCfgMarkerConfiguration);

        restCfgMarkerConfigurationMockMvc.perform(put("/api/v1/network/{networkShortcut}/configuration/library/marker", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cfgMarkerConfigurationDTO)))
            .andExpect(status().isOk());

        // Validate the CfgMarkerConfiguration in the database
        List<CfgMarkerConfiguration> cfgMarkerConfigurationList = cfgMarkerConfigurationRepository.findAll();
        assertThat(cfgMarkerConfigurationList).hasSize(databaseSizeBeforeUpdate);
        CfgMarkerConfiguration testCfgMarkerConfiguration = cfgMarkerConfigurationList.get(cfgMarkerConfigurationList.size() - 1);
        assertThat(testCfgMarkerConfiguration.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCfgMarkerConfiguration.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testCfgMarkerConfiguration.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testCfgMarkerConfiguration.getKeyboardShortcut()).isEqualTo(UPDATED_KEYBOARD_SHORTCUT);
        assertThat(testCfgMarkerConfiguration.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingCfgMarkerConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = cfgMarkerConfigurationRepository.findAll().size();

        // Create the CfgMarkerConfiguration
        LibMarkerConfigurationDTO cfgMarkerConfigurationDTO = cfgMarkerConfigurationMapper.DB2DTO(cfgMarkerConfiguration);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCfgMarkerConfigurationMockMvc.perform(put("/api/v1/network/{networkShortcut}/configuration/library/marker", corNetwork.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cfgMarkerConfigurationDTO)))
            .andExpect(status().isCreated());

        // Validate the CfgMarkerConfiguration in the database
        List<CfgMarkerConfiguration> cfgMarkerConfigurationList = cfgMarkerConfigurationRepository.findAll();
        assertThat(cfgMarkerConfigurationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCfgMarkerConfiguration() throws Exception {
        // Initialize the database
        cfgMarkerConfigurationRepository.saveAndFlush(cfgMarkerConfiguration.network(corNetwork));
        int databaseSizeBeforeDelete = cfgMarkerConfigurationRepository.findAll().size();

        // Get the cfgMarkerConfiguration
        restCfgMarkerConfigurationMockMvc.perform(delete("/api/v1/network/{networkShortcut}/configuration/library/marker/{id}", corNetwork.getShortcut(), cfgMarkerConfiguration.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CfgMarkerConfiguration> cfgMarkerConfigurationList = cfgMarkerConfigurationRepository.findAll();
        assertThat(cfgMarkerConfigurationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CfgMarkerConfiguration.class);
    }

}
