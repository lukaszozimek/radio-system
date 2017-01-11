package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.LIBFileItem;
import io.protone.repository.LIBFileItemRepository;
import io.protone.service.dto.LIBFileItemDTO;
import io.protone.service.mapper.LIBFileItemMapper;

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

import io.protone.domain.enumeration.LIBFileTypeEnum;
/**
 * Test class for the LIBFileItemResource REST controller.
 *
 * @see LIBFileItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LIBFileItemResourceIntTest {

    private static final String DEFAULT_IDX = "AAAAAAAAAA";
    private static final String UPDATED_IDX = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LIBFileTypeEnum DEFAULT_TYPE = LIBFileTypeEnum.FT_ROOT;
    private static final LIBFileTypeEnum UPDATED_TYPE = LIBFileTypeEnum.FT_DIRECTORY;

    @Inject
    private LIBFileItemRepository lIBFileItemRepository;

    @Inject
    private LIBFileItemMapper lIBFileItemMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restLIBFileItemMockMvc;

    private LIBFileItem lIBFileItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LIBFileItemResource lIBFileItemResource = new LIBFileItemResource();
        ReflectionTestUtils.setField(lIBFileItemResource, "lIBFileItemRepository", lIBFileItemRepository);
        ReflectionTestUtils.setField(lIBFileItemResource, "lIBFileItemMapper", lIBFileItemMapper);
        this.restLIBFileItemMockMvc = MockMvcBuilders.standaloneSetup(lIBFileItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LIBFileItem createEntity(EntityManager em) {
        LIBFileItem lIBFileItem = new LIBFileItem()
                .idx(DEFAULT_IDX)
                .name(DEFAULT_NAME)
                .type(DEFAULT_TYPE);
        return lIBFileItem;
    }

    @Before
    public void initTest() {
        lIBFileItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createLIBFileItem() throws Exception {
        int databaseSizeBeforeCreate = lIBFileItemRepository.findAll().size();

        // Create the LIBFileItem
        LIBFileItemDTO lIBFileItemDTO = lIBFileItemMapper.lIBFileItemToLIBFileItemDTO(lIBFileItem);

        restLIBFileItemMockMvc.perform(post("/api/l-ib-file-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBFileItemDTO)))
            .andExpect(status().isCreated());

        // Validate the LIBFileItem in the database
        List<LIBFileItem> lIBFileItemList = lIBFileItemRepository.findAll();
        assertThat(lIBFileItemList).hasSize(databaseSizeBeforeCreate + 1);
        LIBFileItem testLIBFileItem = lIBFileItemList.get(lIBFileItemList.size() - 1);
        assertThat(testLIBFileItem.getIdx()).isEqualTo(DEFAULT_IDX);
        assertThat(testLIBFileItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLIBFileItem.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createLIBFileItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lIBFileItemRepository.findAll().size();

        // Create the LIBFileItem with an existing ID
        LIBFileItem existingLIBFileItem = new LIBFileItem();
        existingLIBFileItem.setId(1L);
        LIBFileItemDTO existingLIBFileItemDTO = lIBFileItemMapper.lIBFileItemToLIBFileItemDTO(existingLIBFileItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLIBFileItemMockMvc.perform(post("/api/l-ib-file-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingLIBFileItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LIBFileItem> lIBFileItemList = lIBFileItemRepository.findAll();
        assertThat(lIBFileItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdxIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBFileItemRepository.findAll().size();
        // set the field null
        lIBFileItem.setIdx(null);

        // Create the LIBFileItem, which fails.
        LIBFileItemDTO lIBFileItemDTO = lIBFileItemMapper.lIBFileItemToLIBFileItemDTO(lIBFileItem);

        restLIBFileItemMockMvc.perform(post("/api/l-ib-file-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBFileItemDTO)))
            .andExpect(status().isBadRequest());

        List<LIBFileItem> lIBFileItemList = lIBFileItemRepository.findAll();
        assertThat(lIBFileItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBFileItemRepository.findAll().size();
        // set the field null
        lIBFileItem.setName(null);

        // Create the LIBFileItem, which fails.
        LIBFileItemDTO lIBFileItemDTO = lIBFileItemMapper.lIBFileItemToLIBFileItemDTO(lIBFileItem);

        restLIBFileItemMockMvc.perform(post("/api/l-ib-file-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBFileItemDTO)))
            .andExpect(status().isBadRequest());

        List<LIBFileItem> lIBFileItemList = lIBFileItemRepository.findAll();
        assertThat(lIBFileItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLIBFileItems() throws Exception {
        // Initialize the database
        lIBFileItemRepository.saveAndFlush(lIBFileItem);

        // Get all the lIBFileItemList
        restLIBFileItemMockMvc.perform(get("/api/l-ib-file-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lIBFileItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].idx").value(hasItem(DEFAULT_IDX.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getLIBFileItem() throws Exception {
        // Initialize the database
        lIBFileItemRepository.saveAndFlush(lIBFileItem);

        // Get the lIBFileItem
        restLIBFileItemMockMvc.perform(get("/api/l-ib-file-items/{id}", lIBFileItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lIBFileItem.getId().intValue()))
            .andExpect(jsonPath("$.idx").value(DEFAULT_IDX.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLIBFileItem() throws Exception {
        // Get the lIBFileItem
        restLIBFileItemMockMvc.perform(get("/api/l-ib-file-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLIBFileItem() throws Exception {
        // Initialize the database
        lIBFileItemRepository.saveAndFlush(lIBFileItem);
        int databaseSizeBeforeUpdate = lIBFileItemRepository.findAll().size();

        // Update the lIBFileItem
        LIBFileItem updatedLIBFileItem = lIBFileItemRepository.findOne(lIBFileItem.getId());
        updatedLIBFileItem
                .idx(UPDATED_IDX)
                .name(UPDATED_NAME)
                .type(UPDATED_TYPE);
        LIBFileItemDTO lIBFileItemDTO = lIBFileItemMapper.lIBFileItemToLIBFileItemDTO(updatedLIBFileItem);

        restLIBFileItemMockMvc.perform(put("/api/l-ib-file-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBFileItemDTO)))
            .andExpect(status().isOk());

        // Validate the LIBFileItem in the database
        List<LIBFileItem> lIBFileItemList = lIBFileItemRepository.findAll();
        assertThat(lIBFileItemList).hasSize(databaseSizeBeforeUpdate);
        LIBFileItem testLIBFileItem = lIBFileItemList.get(lIBFileItemList.size() - 1);
        assertThat(testLIBFileItem.getIdx()).isEqualTo(UPDATED_IDX);
        assertThat(testLIBFileItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLIBFileItem.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingLIBFileItem() throws Exception {
        int databaseSizeBeforeUpdate = lIBFileItemRepository.findAll().size();

        // Create the LIBFileItem
        LIBFileItemDTO lIBFileItemDTO = lIBFileItemMapper.lIBFileItemToLIBFileItemDTO(lIBFileItem);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLIBFileItemMockMvc.perform(put("/api/l-ib-file-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBFileItemDTO)))
            .andExpect(status().isCreated());

        // Validate the LIBFileItem in the database
        List<LIBFileItem> lIBFileItemList = lIBFileItemRepository.findAll();
        assertThat(lIBFileItemList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLIBFileItem() throws Exception {
        // Initialize the database
        lIBFileItemRepository.saveAndFlush(lIBFileItem);
        int databaseSizeBeforeDelete = lIBFileItemRepository.findAll().size();

        // Get the lIBFileItem
        restLIBFileItemMockMvc.perform(delete("/api/l-ib-file-items/{id}", lIBFileItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LIBFileItem> lIBFileItemList = lIBFileItemRepository.findAll();
        assertThat(lIBFileItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
