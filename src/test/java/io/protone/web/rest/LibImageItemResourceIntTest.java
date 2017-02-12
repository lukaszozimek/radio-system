package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.LibImageItem;
import io.protone.repository.LibImageItemRepository;
import io.protone.service.dto.LibImageItemDTO;
import io.protone.service.mapper.LibImageItemMapper;

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

/**
 * Test class for the LibImageItemResource REST controller.
 *
 * @see LibImageItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LibImageItemResourceIntTest {

    private static final String DEFAULT_IDX = "AAAAAAAAAA";
    private static final String UPDATED_IDX = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private LibImageItemRepository libImageItemRepository;

    @Autowired
    private LibImageItemMapper libImageItemMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restLibImageItemMockMvc;

    private LibImageItem libImageItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            LibImageItemResource libImageItemResource = new LibImageItemResource(libImageItemRepository, libImageItemMapper);
        this.restLibImageItemMockMvc = MockMvcBuilders.standaloneSetup(libImageItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LibImageItem createEntity(EntityManager em) {
        LibImageItem libImageItem = new LibImageItem()
                .idx(DEFAULT_IDX)
                .name(DEFAULT_NAME);
        return libImageItem;
    }

    @Before
    public void initTest() {
        libImageItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createLibImageItem() throws Exception {
        int databaseSizeBeforeCreate = libImageItemRepository.findAll().size();

        // Create the LibImageItem
        LibImageItemDTO libImageItemDTO = libImageItemMapper.libImageItemToLibImageItemDTO(libImageItem);

        restLibImageItemMockMvc.perform(post("/api/lib-image-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libImageItemDTO)))
            .andExpect(status().isCreated());

        // Validate the LibImageItem in the database
        List<LibImageItem> libImageItemList = libImageItemRepository.findAll();
        assertThat(libImageItemList).hasSize(databaseSizeBeforeCreate + 1);
        LibImageItem testLibImageItem = libImageItemList.get(libImageItemList.size() - 1);
        assertThat(testLibImageItem.getIdx()).isEqualTo(DEFAULT_IDX);
        assertThat(testLibImageItem.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createLibImageItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = libImageItemRepository.findAll().size();

        // Create the LibImageItem with an existing ID
        LibImageItem existingLibImageItem = new LibImageItem();
        existingLibImageItem.setId(1L);
        LibImageItemDTO existingLibImageItemDTO = libImageItemMapper.libImageItemToLibImageItemDTO(existingLibImageItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLibImageItemMockMvc.perform(post("/api/lib-image-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingLibImageItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LibImageItem> libImageItemList = libImageItemRepository.findAll();
        assertThat(libImageItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdxIsRequired() throws Exception {
        int databaseSizeBeforeTest = libImageItemRepository.findAll().size();
        // set the field null
        libImageItem.setIdx(null);

        // Create the LibImageItem, which fails.
        LibImageItemDTO libImageItemDTO = libImageItemMapper.libImageItemToLibImageItemDTO(libImageItem);

        restLibImageItemMockMvc.perform(post("/api/lib-image-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libImageItemDTO)))
            .andExpect(status().isBadRequest());

        List<LibImageItem> libImageItemList = libImageItemRepository.findAll();
        assertThat(libImageItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = libImageItemRepository.findAll().size();
        // set the field null
        libImageItem.setName(null);

        // Create the LibImageItem, which fails.
        LibImageItemDTO libImageItemDTO = libImageItemMapper.libImageItemToLibImageItemDTO(libImageItem);

        restLibImageItemMockMvc.perform(post("/api/lib-image-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libImageItemDTO)))
            .andExpect(status().isBadRequest());

        List<LibImageItem> libImageItemList = libImageItemRepository.findAll();
        assertThat(libImageItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLibImageItems() throws Exception {
        // Initialize the database
        libImageItemRepository.saveAndFlush(libImageItem);

        // Get all the libImageItemList
        restLibImageItemMockMvc.perform(get("/api/lib-image-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(libImageItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].idx").value(hasItem(DEFAULT_IDX.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getLibImageItem() throws Exception {
        // Initialize the database
        libImageItemRepository.saveAndFlush(libImageItem);

        // Get the libImageItem
        restLibImageItemMockMvc.perform(get("/api/lib-image-items/{id}", libImageItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(libImageItem.getId().intValue()))
            .andExpect(jsonPath("$.idx").value(DEFAULT_IDX.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLibImageItem() throws Exception {
        // Get the libImageItem
        restLibImageItemMockMvc.perform(get("/api/lib-image-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLibImageItem() throws Exception {
        // Initialize the database
        libImageItemRepository.saveAndFlush(libImageItem);
        int databaseSizeBeforeUpdate = libImageItemRepository.findAll().size();

        // Update the libImageItem
        LibImageItem updatedLibImageItem = libImageItemRepository.findOne(libImageItem.getId());
        updatedLibImageItem
                .idx(UPDATED_IDX)
                .name(UPDATED_NAME);
        LibImageItemDTO libImageItemDTO = libImageItemMapper.libImageItemToLibImageItemDTO(updatedLibImageItem);

        restLibImageItemMockMvc.perform(put("/api/lib-image-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libImageItemDTO)))
            .andExpect(status().isOk());

        // Validate the LibImageItem in the database
        List<LibImageItem> libImageItemList = libImageItemRepository.findAll();
        assertThat(libImageItemList).hasSize(databaseSizeBeforeUpdate);
        LibImageItem testLibImageItem = libImageItemList.get(libImageItemList.size() - 1);
        assertThat(testLibImageItem.getIdx()).isEqualTo(UPDATED_IDX);
        assertThat(testLibImageItem.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingLibImageItem() throws Exception {
        int databaseSizeBeforeUpdate = libImageItemRepository.findAll().size();

        // Create the LibImageItem
        LibImageItemDTO libImageItemDTO = libImageItemMapper.libImageItemToLibImageItemDTO(libImageItem);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLibImageItemMockMvc.perform(put("/api/lib-image-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libImageItemDTO)))
            .andExpect(status().isCreated());

        // Validate the LibImageItem in the database
        List<LibImageItem> libImageItemList = libImageItemRepository.findAll();
        assertThat(libImageItemList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLibImageItem() throws Exception {
        // Initialize the database
        libImageItemRepository.saveAndFlush(libImageItem);
        int databaseSizeBeforeDelete = libImageItemRepository.findAll().size();

        // Get the libImageItem
        restLibImageItemMockMvc.perform(delete("/api/lib-image-items/{id}", libImageItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LibImageItem> libImageItemList = libImageItemRepository.findAll();
        assertThat(libImageItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LibImageItem.class);
    }
}
