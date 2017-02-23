package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.LibFileItem;
import io.protone.repository.LibFileItemRepository;
import io.protone.service.dto.LibFileItemDTO;
import io.protone.service.mapper.LibFileItemMapper;

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

import io.protone.domain.enumeration.LibFileTypeEnum;
/**
 * Test class for the LibFileItemResource REST controller.
 *
 * @see LibFileItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LibFileItemResourceIntTest {

    private static final String DEFAULT_IDX = "AAAAAAAAAA";
    private static final String UPDATED_IDX = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LibFileTypeEnum DEFAULT_TYPE = LibFileTypeEnum.FT_ROOT;
    private static final LibFileTypeEnum UPDATED_TYPE = LibFileTypeEnum.FT_DIRECTORY;

    @Autowired
    private LibFileItemRepository libFileItemRepository;

    @Autowired
    private LibFileItemMapper libFileItemMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restLibFileItemMockMvc;

    private LibFileItem libFileItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            LibFileItemResource libFileItemResource = new LibFileItemResource(libFileItemRepository, libFileItemMapper);
        this.restLibFileItemMockMvc = MockMvcBuilders.standaloneSetup(libFileItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LibFileItem createEntity(EntityManager em) {
        LibFileItem libFileItem = new LibFileItem()
                .idx(DEFAULT_IDX)
                .name(DEFAULT_NAME)
                .type(DEFAULT_TYPE);
        return libFileItem;
    }

    @Before
    public void initTest() {
        libFileItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createLibFileItem() throws Exception {
        int databaseSizeBeforeCreate = libFileItemRepository.findAll().size();

        // Create the LibFileItem
        LibFileItemDTO libFileItemDTO = libFileItemMapper.libFileItemToLibFileItemDTO(libFileItem);

        restLibFileItemMockMvc.perform(post("/api/lib-file-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libFileItemDTO)))
            .andExpect(status().isCreated());

        // Validate the LibFileItem in the database
        List<LibFileItem> libFileItemList = libFileItemRepository.findAll();
        assertThat(libFileItemList).hasSize(databaseSizeBeforeCreate + 1);
        LibFileItem testLibFileItem = libFileItemList.get(libFileItemList.size() - 1);
        assertThat(testLibFileItem.getIdx()).isEqualTo(DEFAULT_IDX);
        assertThat(testLibFileItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLibFileItem.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createLibFileItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = libFileItemRepository.findAll().size();

        // Create the LibFileItem with an existing ID
        LibFileItem existingLibFileItem = new LibFileItem();
        existingLibFileItem.setId(1L);
        LibFileItemDTO existingLibFileItemDTO = libFileItemMapper.libFileItemToLibFileItemDTO(existingLibFileItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLibFileItemMockMvc.perform(post("/api/lib-file-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingLibFileItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LibFileItem> libFileItemList = libFileItemRepository.findAll();
        assertThat(libFileItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdxIsRequired() throws Exception {
        int databaseSizeBeforeTest = libFileItemRepository.findAll().size();
        // set the field null
        libFileItem.setIdx(null);

        // Create the LibFileItem, which fails.
        LibFileItemDTO libFileItemDTO = libFileItemMapper.libFileItemToLibFileItemDTO(libFileItem);

        restLibFileItemMockMvc.perform(post("/api/lib-file-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libFileItemDTO)))
            .andExpect(status().isBadRequest());

        List<LibFileItem> libFileItemList = libFileItemRepository.findAll();
        assertThat(libFileItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = libFileItemRepository.findAll().size();
        // set the field null
        libFileItem.setName(null);

        // Create the LibFileItem, which fails.
        LibFileItemDTO libFileItemDTO = libFileItemMapper.libFileItemToLibFileItemDTO(libFileItem);

        restLibFileItemMockMvc.perform(post("/api/lib-file-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libFileItemDTO)))
            .andExpect(status().isBadRequest());

        List<LibFileItem> libFileItemList = libFileItemRepository.findAll();
        assertThat(libFileItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLibFileItems() throws Exception {
        // Initialize the database
        libFileItemRepository.saveAndFlush(libFileItem);

        // Get all the libFileItemList
        restLibFileItemMockMvc.perform(get("/api/lib-file-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(libFileItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].idx").value(hasItem(DEFAULT_IDX.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getLibFileItem() throws Exception {
        // Initialize the database
        libFileItemRepository.saveAndFlush(libFileItem);

        // Get the libFileItem
        restLibFileItemMockMvc.perform(get("/api/lib-file-items/{id}", libFileItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(libFileItem.getId().intValue()))
            .andExpect(jsonPath("$.idx").value(DEFAULT_IDX.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLibFileItem() throws Exception {
        // Get the libFileItem
        restLibFileItemMockMvc.perform(get("/api/lib-file-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLibFileItem() throws Exception {
        // Initialize the database
        libFileItemRepository.saveAndFlush(libFileItem);
        int databaseSizeBeforeUpdate = libFileItemRepository.findAll().size();

        // Update the libFileItem
        LibFileItem updatedLibFileItem = libFileItemRepository.findOne(libFileItem.getId());
        updatedLibFileItem
                .idx(UPDATED_IDX)
                .name(UPDATED_NAME)
                .type(UPDATED_TYPE);
        LibFileItemDTO libFileItemDTO = libFileItemMapper.libFileItemToLibFileItemDTO(updatedLibFileItem);

        restLibFileItemMockMvc.perform(put("/api/lib-file-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libFileItemDTO)))
            .andExpect(status().isOk());

        // Validate the LibFileItem in the database
        List<LibFileItem> libFileItemList = libFileItemRepository.findAll();
        assertThat(libFileItemList).hasSize(databaseSizeBeforeUpdate);
        LibFileItem testLibFileItem = libFileItemList.get(libFileItemList.size() - 1);
        assertThat(testLibFileItem.getIdx()).isEqualTo(UPDATED_IDX);
        assertThat(testLibFileItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLibFileItem.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingLibFileItem() throws Exception {
        int databaseSizeBeforeUpdate = libFileItemRepository.findAll().size();

        // Create the LibFileItem
        LibFileItemDTO libFileItemDTO = libFileItemMapper.libFileItemToLibFileItemDTO(libFileItem);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLibFileItemMockMvc.perform(put("/api/lib-file-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libFileItemDTO)))
            .andExpect(status().isCreated());

        // Validate the LibFileItem in the database
        List<LibFileItem> libFileItemList = libFileItemRepository.findAll();
        assertThat(libFileItemList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLibFileItem() throws Exception {
        // Initialize the database
        libFileItemRepository.saveAndFlush(libFileItem);
        int databaseSizeBeforeDelete = libFileItemRepository.findAll().size();

        // Get the libFileItem
        restLibFileItemMockMvc.perform(delete("/api/lib-file-items/{id}", libFileItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LibFileItem> libFileItemList = libFileItemRepository.findAll();
        assertThat(libFileItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LibFileItem.class);
    }
}
