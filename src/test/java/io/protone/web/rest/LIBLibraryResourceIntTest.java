package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.LIBLibrary;
import io.protone.repository.LIBLibraryRepository;
import io.protone.service.dto.LIBLibraryDTO;
import io.protone.service.mapper.LIBLibraryMapper;

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

import io.protone.domain.enumeration.LIBCounterTypeEnum;
import io.protone.domain.enumeration.LIBObjectTypeEnum;
/**
 * Test class for the LIBLibraryResource REST controller.
 *
 * @see LIBLibraryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LIBLibraryResourceIntTest {

    private static final String DEFAULT_PREFIX = "A";
    private static final String UPDATED_PREFIX = "B";

    private static final Integer DEFAULT_IDX_LENGTH = 1;
    private static final Integer UPDATED_IDX_LENGTH = 2;

    private static final String DEFAULT_SHORTCUT = "AAA";
    private static final String UPDATED_SHORTCUT = "BBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_COUNTER = 1L;
    private static final Long UPDATED_COUNTER = 2L;

    private static final LIBCounterTypeEnum DEFAULT_COUNTER_TYPE = LIBCounterTypeEnum.CT_COUNTER;
    private static final LIBCounterTypeEnum UPDATED_COUNTER_TYPE = LIBCounterTypeEnum.CT_PREFIX_COUNTER;

    private static final LIBObjectTypeEnum DEFAULT_LIBRARY_TYPE = LIBObjectTypeEnum.OT_IMAGE;
    private static final LIBObjectTypeEnum UPDATED_LIBRARY_TYPE = LIBObjectTypeEnum.OT_AUDIO;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Inject
    private LIBLibraryRepository lIBLibraryRepository;

    @Inject
    private LIBLibraryMapper lIBLibraryMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restLIBLibraryMockMvc;

    private LIBLibrary lIBLibrary;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LIBLibraryResource lIBLibraryResource = new LIBLibraryResource();
        ReflectionTestUtils.setField(lIBLibraryResource, "lIBLibraryRepository", lIBLibraryRepository);
        ReflectionTestUtils.setField(lIBLibraryResource, "lIBLibraryMapper", lIBLibraryMapper);
        this.restLIBLibraryMockMvc = MockMvcBuilders.standaloneSetup(lIBLibraryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LIBLibrary createEntity(EntityManager em) {
        LIBLibrary lIBLibrary = new LIBLibrary()
                .prefix(DEFAULT_PREFIX)
                .idxLength(DEFAULT_IDX_LENGTH)
                .shortcut(DEFAULT_SHORTCUT)
                .name(DEFAULT_NAME)
                .counter(DEFAULT_COUNTER)
                .counterType(DEFAULT_COUNTER_TYPE)
                .libraryType(DEFAULT_LIBRARY_TYPE)
                .description(DEFAULT_DESCRIPTION);
        return lIBLibrary;
    }

    @Before
    public void initTest() {
        lIBLibrary = createEntity(em);
    }

    @Test
    @Transactional
    public void createLIBLibrary() throws Exception {
        int databaseSizeBeforeCreate = lIBLibraryRepository.findAll().size();

        // Create the LIBLibrary
        LIBLibraryDTO lIBLibraryDTO = lIBLibraryMapper.lIBLibraryToLIBLibraryDTO(lIBLibrary);

        restLIBLibraryMockMvc.perform(post("/api/l-ib-libraries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBLibraryDTO)))
            .andExpect(status().isCreated());

        // Validate the LIBLibrary in the database
        List<LIBLibrary> lIBLibraryList = lIBLibraryRepository.findAll();
        assertThat(lIBLibraryList).hasSize(databaseSizeBeforeCreate + 1);
        LIBLibrary testLIBLibrary = lIBLibraryList.get(lIBLibraryList.size() - 1);
        assertThat(testLIBLibrary.getPrefix()).isEqualTo(DEFAULT_PREFIX);
        assertThat(testLIBLibrary.getIdxLength()).isEqualTo(DEFAULT_IDX_LENGTH);
        assertThat(testLIBLibrary.getShortcut()).isEqualTo(DEFAULT_SHORTCUT);
        assertThat(testLIBLibrary.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLIBLibrary.getCounter()).isEqualTo(DEFAULT_COUNTER);
        assertThat(testLIBLibrary.getCounterType()).isEqualTo(DEFAULT_COUNTER_TYPE);
        assertThat(testLIBLibrary.getLibraryType()).isEqualTo(DEFAULT_LIBRARY_TYPE);
        assertThat(testLIBLibrary.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createLIBLibraryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lIBLibraryRepository.findAll().size();

        // Create the LIBLibrary with an existing ID
        LIBLibrary existingLIBLibrary = new LIBLibrary();
        existingLIBLibrary.setId(1L);
        LIBLibraryDTO existingLIBLibraryDTO = lIBLibraryMapper.lIBLibraryToLIBLibraryDTO(existingLIBLibrary);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLIBLibraryMockMvc.perform(post("/api/l-ib-libraries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingLIBLibraryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LIBLibrary> lIBLibraryList = lIBLibraryRepository.findAll();
        assertThat(lIBLibraryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPrefixIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBLibraryRepository.findAll().size();
        // set the field null
        lIBLibrary.setPrefix(null);

        // Create the LIBLibrary, which fails.
        LIBLibraryDTO lIBLibraryDTO = lIBLibraryMapper.lIBLibraryToLIBLibraryDTO(lIBLibrary);

        restLIBLibraryMockMvc.perform(post("/api/l-ib-libraries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBLibraryDTO)))
            .andExpect(status().isBadRequest());

        List<LIBLibrary> lIBLibraryList = lIBLibraryRepository.findAll();
        assertThat(lIBLibraryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdxLengthIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBLibraryRepository.findAll().size();
        // set the field null
        lIBLibrary.setIdxLength(null);

        // Create the LIBLibrary, which fails.
        LIBLibraryDTO lIBLibraryDTO = lIBLibraryMapper.lIBLibraryToLIBLibraryDTO(lIBLibrary);

        restLIBLibraryMockMvc.perform(post("/api/l-ib-libraries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBLibraryDTO)))
            .andExpect(status().isBadRequest());

        List<LIBLibrary> lIBLibraryList = lIBLibraryRepository.findAll();
        assertThat(lIBLibraryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShortcutIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBLibraryRepository.findAll().size();
        // set the field null
        lIBLibrary.setShortcut(null);

        // Create the LIBLibrary, which fails.
        LIBLibraryDTO lIBLibraryDTO = lIBLibraryMapper.lIBLibraryToLIBLibraryDTO(lIBLibrary);

        restLIBLibraryMockMvc.perform(post("/api/l-ib-libraries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBLibraryDTO)))
            .andExpect(status().isBadRequest());

        List<LIBLibrary> lIBLibraryList = lIBLibraryRepository.findAll();
        assertThat(lIBLibraryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBLibraryRepository.findAll().size();
        // set the field null
        lIBLibrary.setName(null);

        // Create the LIBLibrary, which fails.
        LIBLibraryDTO lIBLibraryDTO = lIBLibraryMapper.lIBLibraryToLIBLibraryDTO(lIBLibrary);

        restLIBLibraryMockMvc.perform(post("/api/l-ib-libraries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBLibraryDTO)))
            .andExpect(status().isBadRequest());

        List<LIBLibrary> lIBLibraryList = lIBLibraryRepository.findAll();
        assertThat(lIBLibraryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCounterIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBLibraryRepository.findAll().size();
        // set the field null
        lIBLibrary.setCounter(null);

        // Create the LIBLibrary, which fails.
        LIBLibraryDTO lIBLibraryDTO = lIBLibraryMapper.lIBLibraryToLIBLibraryDTO(lIBLibrary);

        restLIBLibraryMockMvc.perform(post("/api/l-ib-libraries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBLibraryDTO)))
            .andExpect(status().isBadRequest());

        List<LIBLibrary> lIBLibraryList = lIBLibraryRepository.findAll();
        assertThat(lIBLibraryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLibraryTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBLibraryRepository.findAll().size();
        // set the field null
        lIBLibrary.setLibraryType(null);

        // Create the LIBLibrary, which fails.
        LIBLibraryDTO lIBLibraryDTO = lIBLibraryMapper.lIBLibraryToLIBLibraryDTO(lIBLibrary);

        restLIBLibraryMockMvc.perform(post("/api/l-ib-libraries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBLibraryDTO)))
            .andExpect(status().isBadRequest());

        List<LIBLibrary> lIBLibraryList = lIBLibraryRepository.findAll();
        assertThat(lIBLibraryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLIBLibraries() throws Exception {
        // Initialize the database
        lIBLibraryRepository.saveAndFlush(lIBLibrary);

        // Get all the lIBLibraryList
        restLIBLibraryMockMvc.perform(get("/api/l-ib-libraries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lIBLibrary.getId().intValue())))
            .andExpect(jsonPath("$.[*].prefix").value(hasItem(DEFAULT_PREFIX.toString())))
            .andExpect(jsonPath("$.[*].idxLength").value(hasItem(DEFAULT_IDX_LENGTH)))
            .andExpect(jsonPath("$.[*].shortcut").value(hasItem(DEFAULT_SHORTCUT.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].counter").value(hasItem(DEFAULT_COUNTER.intValue())))
            .andExpect(jsonPath("$.[*].counterType").value(hasItem(DEFAULT_COUNTER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].libraryType").value(hasItem(DEFAULT_LIBRARY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getLIBLibrary() throws Exception {
        // Initialize the database
        lIBLibraryRepository.saveAndFlush(lIBLibrary);

        // Get the lIBLibrary
        restLIBLibraryMockMvc.perform(get("/api/l-ib-libraries/{id}", lIBLibrary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lIBLibrary.getId().intValue()))
            .andExpect(jsonPath("$.prefix").value(DEFAULT_PREFIX.toString()))
            .andExpect(jsonPath("$.idxLength").value(DEFAULT_IDX_LENGTH))
            .andExpect(jsonPath("$.shortcut").value(DEFAULT_SHORTCUT.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.counter").value(DEFAULT_COUNTER.intValue()))
            .andExpect(jsonPath("$.counterType").value(DEFAULT_COUNTER_TYPE.toString()))
            .andExpect(jsonPath("$.libraryType").value(DEFAULT_LIBRARY_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLIBLibrary() throws Exception {
        // Get the lIBLibrary
        restLIBLibraryMockMvc.perform(get("/api/l-ib-libraries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLIBLibrary() throws Exception {
        // Initialize the database
        lIBLibraryRepository.saveAndFlush(lIBLibrary);
        int databaseSizeBeforeUpdate = lIBLibraryRepository.findAll().size();

        // Update the lIBLibrary
        LIBLibrary updatedLIBLibrary = lIBLibraryRepository.findOne(lIBLibrary.getId());
        updatedLIBLibrary
                .prefix(UPDATED_PREFIX)
                .idxLength(UPDATED_IDX_LENGTH)
                .shortcut(UPDATED_SHORTCUT)
                .name(UPDATED_NAME)
                .counter(UPDATED_COUNTER)
                .counterType(UPDATED_COUNTER_TYPE)
                .libraryType(UPDATED_LIBRARY_TYPE)
                .description(UPDATED_DESCRIPTION);
        LIBLibraryDTO lIBLibraryDTO = lIBLibraryMapper.lIBLibraryToLIBLibraryDTO(updatedLIBLibrary);

        restLIBLibraryMockMvc.perform(put("/api/l-ib-libraries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBLibraryDTO)))
            .andExpect(status().isOk());

        // Validate the LIBLibrary in the database
        List<LIBLibrary> lIBLibraryList = lIBLibraryRepository.findAll();
        assertThat(lIBLibraryList).hasSize(databaseSizeBeforeUpdate);
        LIBLibrary testLIBLibrary = lIBLibraryList.get(lIBLibraryList.size() - 1);
        assertThat(testLIBLibrary.getPrefix()).isEqualTo(UPDATED_PREFIX);
        assertThat(testLIBLibrary.getIdxLength()).isEqualTo(UPDATED_IDX_LENGTH);
        assertThat(testLIBLibrary.getShortcut()).isEqualTo(UPDATED_SHORTCUT);
        assertThat(testLIBLibrary.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLIBLibrary.getCounter()).isEqualTo(UPDATED_COUNTER);
        assertThat(testLIBLibrary.getCounterType()).isEqualTo(UPDATED_COUNTER_TYPE);
        assertThat(testLIBLibrary.getLibraryType()).isEqualTo(UPDATED_LIBRARY_TYPE);
        assertThat(testLIBLibrary.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingLIBLibrary() throws Exception {
        int databaseSizeBeforeUpdate = lIBLibraryRepository.findAll().size();

        // Create the LIBLibrary
        LIBLibraryDTO lIBLibraryDTO = lIBLibraryMapper.lIBLibraryToLIBLibraryDTO(lIBLibrary);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLIBLibraryMockMvc.perform(put("/api/l-ib-libraries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBLibraryDTO)))
            .andExpect(status().isCreated());

        // Validate the LIBLibrary in the database
        List<LIBLibrary> lIBLibraryList = lIBLibraryRepository.findAll();
        assertThat(lIBLibraryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLIBLibrary() throws Exception {
        // Initialize the database
        lIBLibraryRepository.saveAndFlush(lIBLibrary);
        int databaseSizeBeforeDelete = lIBLibraryRepository.findAll().size();

        // Get the lIBLibrary
        restLIBLibraryMockMvc.perform(delete("/api/l-ib-libraries/{id}", lIBLibrary.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LIBLibrary> lIBLibraryList = lIBLibraryRepository.findAll();
        assertThat(lIBLibraryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
