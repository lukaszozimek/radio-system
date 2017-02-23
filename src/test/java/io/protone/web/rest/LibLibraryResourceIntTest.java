package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.LibLibrary;
import io.protone.repository.LibLibraryRepository;
import io.protone.service.dto.LibLibraryDTO;
import io.protone.service.mapper.LibLibraryMapper;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.protone.domain.enumeration.LibCounterTypeEnum;
import io.protone.domain.enumeration.LibObjectTypeEnum;
/**
 * Test class for the LibLibraryResource REST controller.
 *
 * @see LibLibraryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LibLibraryResourceIntTest {

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

    private static final LibCounterTypeEnum DEFAULT_COUNTER_TYPE = LibCounterTypeEnum.CT_COUNTER;
    private static final LibCounterTypeEnum UPDATED_COUNTER_TYPE = LibCounterTypeEnum.CT_PREFIX_COUNTER;

    private static final LibObjectTypeEnum DEFAULT_LIBRARY_TYPE = LibObjectTypeEnum.OT_IMAGE;
    private static final LibObjectTypeEnum UPDATED_LIBRARY_TYPE = LibObjectTypeEnum.OT_AUDIO;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private LibLibraryRepository libLibraryRepository;

    @Autowired
    private LibLibraryMapper libLibraryMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restLibLibraryMockMvc;

    private LibLibrary libLibrary;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            LibLibraryResource libLibraryResource = new LibLibraryResource(libLibraryRepository, libLibraryMapper);
        this.restLibLibraryMockMvc = MockMvcBuilders.standaloneSetup(libLibraryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LibLibrary createEntity(EntityManager em) {
        LibLibrary libLibrary = new LibLibrary()
                .prefix(DEFAULT_PREFIX)
                .idxLength(DEFAULT_IDX_LENGTH)
                .shortcut(DEFAULT_SHORTCUT)
                .name(DEFAULT_NAME)
                .counter(DEFAULT_COUNTER)
                .counterType(DEFAULT_COUNTER_TYPE)
                .libraryType(DEFAULT_LIBRARY_TYPE)
                .description(DEFAULT_DESCRIPTION);
        return libLibrary;
    }

    @Before
    public void initTest() {
        libLibrary = createEntity(em);
    }

    @Test
    @Transactional
    public void createLibLibrary() throws Exception {
        int databaseSizeBeforeCreate = libLibraryRepository.findAll().size();

        // Create the LibLibrary
        LibLibraryDTO libLibraryDTO = libLibraryMapper.libLibraryToLibLibraryDTO(libLibrary);

        restLibLibraryMockMvc.perform(post("/api/lib-libraries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libLibraryDTO)))
            .andExpect(status().isCreated());

        // Validate the LibLibrary in the database
        List<LibLibrary> libLibraryList = libLibraryRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeCreate + 1);
        LibLibrary testLibLibrary = libLibraryList.get(libLibraryList.size() - 1);
        assertThat(testLibLibrary.getPrefix()).isEqualTo(DEFAULT_PREFIX);
        assertThat(testLibLibrary.getIdxLength()).isEqualTo(DEFAULT_IDX_LENGTH);
        assertThat(testLibLibrary.getShortcut()).isEqualTo(DEFAULT_SHORTCUT);
        assertThat(testLibLibrary.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLibLibrary.getCounter()).isEqualTo(DEFAULT_COUNTER);
        assertThat(testLibLibrary.getCounterType()).isEqualTo(DEFAULT_COUNTER_TYPE);
        assertThat(testLibLibrary.getLibraryType()).isEqualTo(DEFAULT_LIBRARY_TYPE);
        assertThat(testLibLibrary.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createLibLibraryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = libLibraryRepository.findAll().size();

        // Create the LibLibrary with an existing ID
        LibLibrary existingLibLibrary = new LibLibrary();
        existingLibLibrary.setId(1L);
        LibLibraryDTO existingLibLibraryDTO = libLibraryMapper.libLibraryToLibLibraryDTO(existingLibLibrary);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLibLibraryMockMvc.perform(post("/api/lib-libraries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingLibLibraryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LibLibrary> libLibraryList = libLibraryRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPrefixIsRequired() throws Exception {
        int databaseSizeBeforeTest = libLibraryRepository.findAll().size();
        // set the field null
        libLibrary.setPrefix(null);

        // Create the LibLibrary, which fails.
        LibLibraryDTO libLibraryDTO = libLibraryMapper.libLibraryToLibLibraryDTO(libLibrary);

        restLibLibraryMockMvc.perform(post("/api/lib-libraries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libLibraryDTO)))
            .andExpect(status().isBadRequest());

        List<LibLibrary> libLibraryList = libLibraryRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdxLengthIsRequired() throws Exception {
        int databaseSizeBeforeTest = libLibraryRepository.findAll().size();
        // set the field null
        libLibrary.setIdxLength(null);

        // Create the LibLibrary, which fails.
        LibLibraryDTO libLibraryDTO = libLibraryMapper.libLibraryToLibLibraryDTO(libLibrary);

        restLibLibraryMockMvc.perform(post("/api/lib-libraries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libLibraryDTO)))
            .andExpect(status().isBadRequest());

        List<LibLibrary> libLibraryList = libLibraryRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShortcutIsRequired() throws Exception {
        int databaseSizeBeforeTest = libLibraryRepository.findAll().size();
        // set the field null
        libLibrary.setShortcut(null);

        // Create the LibLibrary, which fails.
        LibLibraryDTO libLibraryDTO = libLibraryMapper.libLibraryToLibLibraryDTO(libLibrary);

        restLibLibraryMockMvc.perform(post("/api/lib-libraries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libLibraryDTO)))
            .andExpect(status().isBadRequest());

        List<LibLibrary> libLibraryList = libLibraryRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = libLibraryRepository.findAll().size();
        // set the field null
        libLibrary.setName(null);

        // Create the LibLibrary, which fails.
        LibLibraryDTO libLibraryDTO = libLibraryMapper.libLibraryToLibLibraryDTO(libLibrary);

        restLibLibraryMockMvc.perform(post("/api/lib-libraries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libLibraryDTO)))
            .andExpect(status().isBadRequest());

        List<LibLibrary> libLibraryList = libLibraryRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCounterIsRequired() throws Exception {
        int databaseSizeBeforeTest = libLibraryRepository.findAll().size();
        // set the field null
        libLibrary.setCounter(null);

        // Create the LibLibrary, which fails.
        LibLibraryDTO libLibraryDTO = libLibraryMapper.libLibraryToLibLibraryDTO(libLibrary);

        restLibLibraryMockMvc.perform(post("/api/lib-libraries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libLibraryDTO)))
            .andExpect(status().isBadRequest());

        List<LibLibrary> libLibraryList = libLibraryRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLibraryTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = libLibraryRepository.findAll().size();
        // set the field null
        libLibrary.setLibraryType(null);

        // Create the LibLibrary, which fails.
        LibLibraryDTO libLibraryDTO = libLibraryMapper.libLibraryToLibLibraryDTO(libLibrary);

        restLibLibraryMockMvc.perform(post("/api/lib-libraries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libLibraryDTO)))
            .andExpect(status().isBadRequest());

        List<LibLibrary> libLibraryList = libLibraryRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLibLibraries() throws Exception {
        // Initialize the database
        libLibraryRepository.saveAndFlush(libLibrary);

        // Get all the libLibraryList
        restLibLibraryMockMvc.perform(get("/api/lib-libraries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(libLibrary.getId().intValue())))
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
    public void getLibLibrary() throws Exception {
        // Initialize the database
        libLibraryRepository.saveAndFlush(libLibrary);

        // Get the libLibrary
        restLibLibraryMockMvc.perform(get("/api/lib-libraries/{id}", libLibrary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(libLibrary.getId().intValue()))
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
    public void getNonExistingLibLibrary() throws Exception {
        // Get the libLibrary
        restLibLibraryMockMvc.perform(get("/api/lib-libraries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLibLibrary() throws Exception {
        // Initialize the database
        libLibraryRepository.saveAndFlush(libLibrary);
        int databaseSizeBeforeUpdate = libLibraryRepository.findAll().size();

        // Update the libLibrary
        LibLibrary updatedLibLibrary = libLibraryRepository.findOne(libLibrary.getId());
        updatedLibLibrary
                .prefix(UPDATED_PREFIX)
                .idxLength(UPDATED_IDX_LENGTH)
                .shortcut(UPDATED_SHORTCUT)
                .name(UPDATED_NAME)
                .counter(UPDATED_COUNTER)
                .counterType(UPDATED_COUNTER_TYPE)
                .libraryType(UPDATED_LIBRARY_TYPE)
                .description(UPDATED_DESCRIPTION);
        LibLibraryDTO libLibraryDTO = libLibraryMapper.libLibraryToLibLibraryDTO(updatedLibLibrary);

        restLibLibraryMockMvc.perform(put("/api/lib-libraries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libLibraryDTO)))
            .andExpect(status().isOk());

        // Validate the LibLibrary in the database
        List<LibLibrary> libLibraryList = libLibraryRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeUpdate);
        LibLibrary testLibLibrary = libLibraryList.get(libLibraryList.size() - 1);
        assertThat(testLibLibrary.getPrefix()).isEqualTo(UPDATED_PREFIX);
        assertThat(testLibLibrary.getIdxLength()).isEqualTo(UPDATED_IDX_LENGTH);
        assertThat(testLibLibrary.getShortcut()).isEqualTo(UPDATED_SHORTCUT);
        assertThat(testLibLibrary.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLibLibrary.getCounter()).isEqualTo(UPDATED_COUNTER);
        assertThat(testLibLibrary.getCounterType()).isEqualTo(UPDATED_COUNTER_TYPE);
        assertThat(testLibLibrary.getLibraryType()).isEqualTo(UPDATED_LIBRARY_TYPE);
        assertThat(testLibLibrary.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingLibLibrary() throws Exception {
        int databaseSizeBeforeUpdate = libLibraryRepository.findAll().size();

        // Create the LibLibrary
        LibLibraryDTO libLibraryDTO = libLibraryMapper.libLibraryToLibLibraryDTO(libLibrary);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLibLibraryMockMvc.perform(put("/api/lib-libraries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libLibraryDTO)))
            .andExpect(status().isCreated());

        // Validate the LibLibrary in the database
        List<LibLibrary> libLibraryList = libLibraryRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLibLibrary() throws Exception {
        // Initialize the database
        libLibraryRepository.saveAndFlush(libLibrary);
        int databaseSizeBeforeDelete = libLibraryRepository.findAll().size();

        // Get the libLibrary
        restLibLibraryMockMvc.perform(delete("/api/lib-libraries/{id}", libLibrary.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LibLibrary> libLibraryList = libLibraryRepository.findAll();
        assertThat(libLibraryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LibLibrary.class);
    }
}
