package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CFGMarkerConfiguration;
import io.protone.repository.CFGMarkerConfigurationRepository;
import io.protone.service.dto.CFGMarkerConfigurationDTO;
import io.protone.service.mapper.CFGMarkerConfigurationMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.protone.domain.enumeration.LIBMarkerTypeEnum;
/**
 * Test class for the CFGMarkerConfigurationResource REST controller.
 *
 * @see CFGMarkerConfigurationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CFGMarkerConfigurationResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_KEYBOARD_SHORTCUT = "AAAAAAAAAA";
    private static final String UPDATED_KEYBOARD_SHORTCUT = "BBBBBBBBBB";

    private static final LIBMarkerTypeEnum DEFAULT_TYPE = LIBMarkerTypeEnum.MT_BASIC;
    private static final LIBMarkerTypeEnum UPDATED_TYPE = LIBMarkerTypeEnum.MT_INTRO;

    @Inject
    private CFGMarkerConfigurationRepository cFGMarkerConfigurationRepository;

    @Inject
    private CFGMarkerConfigurationMapper cFGMarkerConfigurationMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCFGMarkerConfigurationMockMvc;

    private CFGMarkerConfiguration cFGMarkerConfiguration;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CFGMarkerConfigurationResource cFGMarkerConfigurationResource = new CFGMarkerConfigurationResource();
        ReflectionTestUtils.setField(cFGMarkerConfigurationResource, "cFGMarkerConfigurationRepository", cFGMarkerConfigurationRepository);
        ReflectionTestUtils.setField(cFGMarkerConfigurationResource, "cFGMarkerConfigurationMapper", cFGMarkerConfigurationMapper);
        this.restCFGMarkerConfigurationMockMvc = MockMvcBuilders.standaloneSetup(cFGMarkerConfigurationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CFGMarkerConfiguration createEntity(EntityManager em) {
        CFGMarkerConfiguration cFGMarkerConfiguration = new CFGMarkerConfiguration()
                .name(DEFAULT_NAME)
                .displayName(DEFAULT_DISPLAY_NAME)
                .color(DEFAULT_COLOR)
                .keyboardShortcut(DEFAULT_KEYBOARD_SHORTCUT)
                .type(DEFAULT_TYPE);
        return cFGMarkerConfiguration;
    }

    @Before
    public void initTest() {
        cFGMarkerConfiguration = createEntity(em);
    }

    @Test
    @Transactional
    public void createCFGMarkerConfiguration() throws Exception {
        int databaseSizeBeforeCreate = cFGMarkerConfigurationRepository.findAll().size();

        // Create the CFGMarkerConfiguration
        CFGMarkerConfigurationDTO cFGMarkerConfigurationDTO = cFGMarkerConfigurationMapper.cFGMarkerConfigurationToCFGMarkerConfigurationDTO(cFGMarkerConfiguration);

        restCFGMarkerConfigurationMockMvc.perform(post("/api/c-fg-marker-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cFGMarkerConfigurationDTO)))
            .andExpect(status().isCreated());

        // Validate the CFGMarkerConfiguration in the database
        List<CFGMarkerConfiguration> cFGMarkerConfigurationList = cFGMarkerConfigurationRepository.findAll();
        assertThat(cFGMarkerConfigurationList).hasSize(databaseSizeBeforeCreate + 1);
        CFGMarkerConfiguration testCFGMarkerConfiguration = cFGMarkerConfigurationList.get(cFGMarkerConfigurationList.size() - 1);
        assertThat(testCFGMarkerConfiguration.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCFGMarkerConfiguration.getDisplayName()).isEqualTo(DEFAULT_DISPLAY_NAME);
        assertThat(testCFGMarkerConfiguration.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testCFGMarkerConfiguration.getKeyboardShortcut()).isEqualTo(DEFAULT_KEYBOARD_SHORTCUT);
        assertThat(testCFGMarkerConfiguration.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createCFGMarkerConfigurationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cFGMarkerConfigurationRepository.findAll().size();

        // Create the CFGMarkerConfiguration with an existing ID
        CFGMarkerConfiguration existingCFGMarkerConfiguration = new CFGMarkerConfiguration();
        existingCFGMarkerConfiguration.setId(1L);
        CFGMarkerConfigurationDTO existingCFGMarkerConfigurationDTO = cFGMarkerConfigurationMapper.cFGMarkerConfigurationToCFGMarkerConfigurationDTO(existingCFGMarkerConfiguration);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCFGMarkerConfigurationMockMvc.perform(post("/api/c-fg-marker-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCFGMarkerConfigurationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CFGMarkerConfiguration> cFGMarkerConfigurationList = cFGMarkerConfigurationRepository.findAll();
        assertThat(cFGMarkerConfigurationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cFGMarkerConfigurationRepository.findAll().size();
        // set the field null
        cFGMarkerConfiguration.setName(null);

        // Create the CFGMarkerConfiguration, which fails.
        CFGMarkerConfigurationDTO cFGMarkerConfigurationDTO = cFGMarkerConfigurationMapper.cFGMarkerConfigurationToCFGMarkerConfigurationDTO(cFGMarkerConfiguration);

        restCFGMarkerConfigurationMockMvc.perform(post("/api/c-fg-marker-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cFGMarkerConfigurationDTO)))
            .andExpect(status().isBadRequest());

        List<CFGMarkerConfiguration> cFGMarkerConfigurationList = cFGMarkerConfigurationRepository.findAll();
        assertThat(cFGMarkerConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDisplayNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cFGMarkerConfigurationRepository.findAll().size();
        // set the field null
        cFGMarkerConfiguration.setDisplayName(null);

        // Create the CFGMarkerConfiguration, which fails.
        CFGMarkerConfigurationDTO cFGMarkerConfigurationDTO = cFGMarkerConfigurationMapper.cFGMarkerConfigurationToCFGMarkerConfigurationDTO(cFGMarkerConfiguration);

        restCFGMarkerConfigurationMockMvc.perform(post("/api/c-fg-marker-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cFGMarkerConfigurationDTO)))
            .andExpect(status().isBadRequest());

        List<CFGMarkerConfiguration> cFGMarkerConfigurationList = cFGMarkerConfigurationRepository.findAll();
        assertThat(cFGMarkerConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkColorIsRequired() throws Exception {
        int databaseSizeBeforeTest = cFGMarkerConfigurationRepository.findAll().size();
        // set the field null
        cFGMarkerConfiguration.setColor(null);

        // Create the CFGMarkerConfiguration, which fails.
        CFGMarkerConfigurationDTO cFGMarkerConfigurationDTO = cFGMarkerConfigurationMapper.cFGMarkerConfigurationToCFGMarkerConfigurationDTO(cFGMarkerConfiguration);

        restCFGMarkerConfigurationMockMvc.perform(post("/api/c-fg-marker-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cFGMarkerConfigurationDTO)))
            .andExpect(status().isBadRequest());

        List<CFGMarkerConfiguration> cFGMarkerConfigurationList = cFGMarkerConfigurationRepository.findAll();
        assertThat(cFGMarkerConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKeyboardShortcutIsRequired() throws Exception {
        int databaseSizeBeforeTest = cFGMarkerConfigurationRepository.findAll().size();
        // set the field null
        cFGMarkerConfiguration.setKeyboardShortcut(null);

        // Create the CFGMarkerConfiguration, which fails.
        CFGMarkerConfigurationDTO cFGMarkerConfigurationDTO = cFGMarkerConfigurationMapper.cFGMarkerConfigurationToCFGMarkerConfigurationDTO(cFGMarkerConfiguration);

        restCFGMarkerConfigurationMockMvc.perform(post("/api/c-fg-marker-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cFGMarkerConfigurationDTO)))
            .andExpect(status().isBadRequest());

        List<CFGMarkerConfiguration> cFGMarkerConfigurationList = cFGMarkerConfigurationRepository.findAll();
        assertThat(cFGMarkerConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cFGMarkerConfigurationRepository.findAll().size();
        // set the field null
        cFGMarkerConfiguration.setType(null);

        // Create the CFGMarkerConfiguration, which fails.
        CFGMarkerConfigurationDTO cFGMarkerConfigurationDTO = cFGMarkerConfigurationMapper.cFGMarkerConfigurationToCFGMarkerConfigurationDTO(cFGMarkerConfiguration);

        restCFGMarkerConfigurationMockMvc.perform(post("/api/c-fg-marker-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cFGMarkerConfigurationDTO)))
            .andExpect(status().isBadRequest());

        List<CFGMarkerConfiguration> cFGMarkerConfigurationList = cFGMarkerConfigurationRepository.findAll();
        assertThat(cFGMarkerConfigurationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCFGMarkerConfigurations() throws Exception {
        // Initialize the database
        cFGMarkerConfigurationRepository.saveAndFlush(cFGMarkerConfiguration);

        // Get all the cFGMarkerConfigurationList
        restCFGMarkerConfigurationMockMvc.perform(get("/api/c-fg-marker-configurations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cFGMarkerConfiguration.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].displayName").value(hasItem(DEFAULT_DISPLAY_NAME.toString())))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR.toString())))
            .andExpect(jsonPath("$.[*].keyboardShortcut").value(hasItem(DEFAULT_KEYBOARD_SHORTCUT.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getCFGMarkerConfiguration() throws Exception {
        // Initialize the database
        cFGMarkerConfigurationRepository.saveAndFlush(cFGMarkerConfiguration);

        // Get the cFGMarkerConfiguration
        restCFGMarkerConfigurationMockMvc.perform(get("/api/c-fg-marker-configurations/{id}", cFGMarkerConfiguration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cFGMarkerConfiguration.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.displayName").value(DEFAULT_DISPLAY_NAME.toString()))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR.toString()))
            .andExpect(jsonPath("$.keyboardShortcut").value(DEFAULT_KEYBOARD_SHORTCUT.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCFGMarkerConfiguration() throws Exception {
        // Get the cFGMarkerConfiguration
        restCFGMarkerConfigurationMockMvc.perform(get("/api/c-fg-marker-configurations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCFGMarkerConfiguration() throws Exception {
        // Initialize the database
        cFGMarkerConfigurationRepository.saveAndFlush(cFGMarkerConfiguration);
        int databaseSizeBeforeUpdate = cFGMarkerConfigurationRepository.findAll().size();

        // Update the cFGMarkerConfiguration
        CFGMarkerConfiguration updatedCFGMarkerConfiguration = cFGMarkerConfigurationRepository.findOne(cFGMarkerConfiguration.getId());
        updatedCFGMarkerConfiguration
                .name(UPDATED_NAME)
                .displayName(UPDATED_DISPLAY_NAME)
                .color(UPDATED_COLOR)
                .keyboardShortcut(UPDATED_KEYBOARD_SHORTCUT)
                .type(UPDATED_TYPE);
        CFGMarkerConfigurationDTO cFGMarkerConfigurationDTO = cFGMarkerConfigurationMapper.cFGMarkerConfigurationToCFGMarkerConfigurationDTO(updatedCFGMarkerConfiguration);

        restCFGMarkerConfigurationMockMvc.perform(put("/api/c-fg-marker-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cFGMarkerConfigurationDTO)))
            .andExpect(status().isOk());

        // Validate the CFGMarkerConfiguration in the database
        List<CFGMarkerConfiguration> cFGMarkerConfigurationList = cFGMarkerConfigurationRepository.findAll();
        assertThat(cFGMarkerConfigurationList).hasSize(databaseSizeBeforeUpdate);
        CFGMarkerConfiguration testCFGMarkerConfiguration = cFGMarkerConfigurationList.get(cFGMarkerConfigurationList.size() - 1);
        assertThat(testCFGMarkerConfiguration.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCFGMarkerConfiguration.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testCFGMarkerConfiguration.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testCFGMarkerConfiguration.getKeyboardShortcut()).isEqualTo(UPDATED_KEYBOARD_SHORTCUT);
        assertThat(testCFGMarkerConfiguration.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingCFGMarkerConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = cFGMarkerConfigurationRepository.findAll().size();

        // Create the CFGMarkerConfiguration
        CFGMarkerConfigurationDTO cFGMarkerConfigurationDTO = cFGMarkerConfigurationMapper.cFGMarkerConfigurationToCFGMarkerConfigurationDTO(cFGMarkerConfiguration);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCFGMarkerConfigurationMockMvc.perform(put("/api/c-fg-marker-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cFGMarkerConfigurationDTO)))
            .andExpect(status().isCreated());

        // Validate the CFGMarkerConfiguration in the database
        List<CFGMarkerConfiguration> cFGMarkerConfigurationList = cFGMarkerConfigurationRepository.findAll();
        assertThat(cFGMarkerConfigurationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCFGMarkerConfiguration() throws Exception {
        // Initialize the database
        cFGMarkerConfigurationRepository.saveAndFlush(cFGMarkerConfiguration);
        int databaseSizeBeforeDelete = cFGMarkerConfigurationRepository.findAll().size();

        // Get the cFGMarkerConfiguration
        restCFGMarkerConfigurationMockMvc.perform(delete("/api/c-fg-marker-configurations/{id}", cFGMarkerConfiguration.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CFGMarkerConfiguration> cFGMarkerConfigurationList = cFGMarkerConfigurationRepository.findAll();
        assertThat(cFGMarkerConfigurationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
