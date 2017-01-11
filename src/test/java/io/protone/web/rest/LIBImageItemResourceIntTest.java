package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.LIBImageItem;
import io.protone.repository.LIBImageItemRepository;
import io.protone.service.dto.LIBImageItemDTO;
import io.protone.service.mapper.LIBImageItemMapper;

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

/**
 * Test class for the LIBImageItemResource REST controller.
 *
 * @see LIBImageItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LIBImageItemResourceIntTest {

    private static final String DEFAULT_IDX = "AAAAAAAAAA";
    private static final String UPDATED_IDX = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Inject
    private LIBImageItemRepository lIBImageItemRepository;

    @Inject
    private LIBImageItemMapper lIBImageItemMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restLIBImageItemMockMvc;

    private LIBImageItem lIBImageItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LIBImageItemResource lIBImageItemResource = new LIBImageItemResource();
        ReflectionTestUtils.setField(lIBImageItemResource, "lIBImageItemRepository", lIBImageItemRepository);
        ReflectionTestUtils.setField(lIBImageItemResource, "lIBImageItemMapper", lIBImageItemMapper);
        this.restLIBImageItemMockMvc = MockMvcBuilders.standaloneSetup(lIBImageItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LIBImageItem createEntity(EntityManager em) {
        LIBImageItem lIBImageItem = new LIBImageItem()
                .idx(DEFAULT_IDX)
                .name(DEFAULT_NAME);
        return lIBImageItem;
    }

    @Before
    public void initTest() {
        lIBImageItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createLIBImageItem() throws Exception {
        int databaseSizeBeforeCreate = lIBImageItemRepository.findAll().size();

        // Create the LIBImageItem
        LIBImageItemDTO lIBImageItemDTO = lIBImageItemMapper.lIBImageItemToLIBImageItemDTO(lIBImageItem);

        restLIBImageItemMockMvc.perform(post("/api/l-ib-image-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBImageItemDTO)))
            .andExpect(status().isCreated());

        // Validate the LIBImageItem in the database
        List<LIBImageItem> lIBImageItemList = lIBImageItemRepository.findAll();
        assertThat(lIBImageItemList).hasSize(databaseSizeBeforeCreate + 1);
        LIBImageItem testLIBImageItem = lIBImageItemList.get(lIBImageItemList.size() - 1);
        assertThat(testLIBImageItem.getIdx()).isEqualTo(DEFAULT_IDX);
        assertThat(testLIBImageItem.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createLIBImageItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lIBImageItemRepository.findAll().size();

        // Create the LIBImageItem with an existing ID
        LIBImageItem existingLIBImageItem = new LIBImageItem();
        existingLIBImageItem.setId(1L);
        LIBImageItemDTO existingLIBImageItemDTO = lIBImageItemMapper.lIBImageItemToLIBImageItemDTO(existingLIBImageItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLIBImageItemMockMvc.perform(post("/api/l-ib-image-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingLIBImageItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LIBImageItem> lIBImageItemList = lIBImageItemRepository.findAll();
        assertThat(lIBImageItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdxIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBImageItemRepository.findAll().size();
        // set the field null
        lIBImageItem.setIdx(null);

        // Create the LIBImageItem, which fails.
        LIBImageItemDTO lIBImageItemDTO = lIBImageItemMapper.lIBImageItemToLIBImageItemDTO(lIBImageItem);

        restLIBImageItemMockMvc.perform(post("/api/l-ib-image-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBImageItemDTO)))
            .andExpect(status().isBadRequest());

        List<LIBImageItem> lIBImageItemList = lIBImageItemRepository.findAll();
        assertThat(lIBImageItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBImageItemRepository.findAll().size();
        // set the field null
        lIBImageItem.setName(null);

        // Create the LIBImageItem, which fails.
        LIBImageItemDTO lIBImageItemDTO = lIBImageItemMapper.lIBImageItemToLIBImageItemDTO(lIBImageItem);

        restLIBImageItemMockMvc.perform(post("/api/l-ib-image-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBImageItemDTO)))
            .andExpect(status().isBadRequest());

        List<LIBImageItem> lIBImageItemList = lIBImageItemRepository.findAll();
        assertThat(lIBImageItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLIBImageItems() throws Exception {
        // Initialize the database
        lIBImageItemRepository.saveAndFlush(lIBImageItem);

        // Get all the lIBImageItemList
        restLIBImageItemMockMvc.perform(get("/api/l-ib-image-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lIBImageItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].idx").value(hasItem(DEFAULT_IDX.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getLIBImageItem() throws Exception {
        // Initialize the database
        lIBImageItemRepository.saveAndFlush(lIBImageItem);

        // Get the lIBImageItem
        restLIBImageItemMockMvc.perform(get("/api/l-ib-image-items/{id}", lIBImageItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lIBImageItem.getId().intValue()))
            .andExpect(jsonPath("$.idx").value(DEFAULT_IDX.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLIBImageItem() throws Exception {
        // Get the lIBImageItem
        restLIBImageItemMockMvc.perform(get("/api/l-ib-image-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLIBImageItem() throws Exception {
        // Initialize the database
        lIBImageItemRepository.saveAndFlush(lIBImageItem);
        int databaseSizeBeforeUpdate = lIBImageItemRepository.findAll().size();

        // Update the lIBImageItem
        LIBImageItem updatedLIBImageItem = lIBImageItemRepository.findOne(lIBImageItem.getId());
        updatedLIBImageItem
                .idx(UPDATED_IDX)
                .name(UPDATED_NAME);
        LIBImageItemDTO lIBImageItemDTO = lIBImageItemMapper.lIBImageItemToLIBImageItemDTO(updatedLIBImageItem);

        restLIBImageItemMockMvc.perform(put("/api/l-ib-image-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBImageItemDTO)))
            .andExpect(status().isOk());

        // Validate the LIBImageItem in the database
        List<LIBImageItem> lIBImageItemList = lIBImageItemRepository.findAll();
        assertThat(lIBImageItemList).hasSize(databaseSizeBeforeUpdate);
        LIBImageItem testLIBImageItem = lIBImageItemList.get(lIBImageItemList.size() - 1);
        assertThat(testLIBImageItem.getIdx()).isEqualTo(UPDATED_IDX);
        assertThat(testLIBImageItem.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingLIBImageItem() throws Exception {
        int databaseSizeBeforeUpdate = lIBImageItemRepository.findAll().size();

        // Create the LIBImageItem
        LIBImageItemDTO lIBImageItemDTO = lIBImageItemMapper.lIBImageItemToLIBImageItemDTO(lIBImageItem);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLIBImageItemMockMvc.perform(put("/api/l-ib-image-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBImageItemDTO)))
            .andExpect(status().isCreated());

        // Validate the LIBImageItem in the database
        List<LIBImageItem> lIBImageItemList = lIBImageItemRepository.findAll();
        assertThat(lIBImageItemList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLIBImageItem() throws Exception {
        // Initialize the database
        lIBImageItemRepository.saveAndFlush(lIBImageItem);
        int databaseSizeBeforeDelete = lIBImageItemRepository.findAll().size();

        // Get the lIBImageItem
        restLIBImageItemMockMvc.perform(delete("/api/l-ib-image-items/{id}", lIBImageItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LIBImageItem> lIBImageItemList = lIBImageItemRepository.findAll();
        assertThat(lIBImageItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
