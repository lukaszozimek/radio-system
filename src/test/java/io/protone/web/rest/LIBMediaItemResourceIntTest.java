package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.LIBMediaItem;
import io.protone.repository.LIBMediaItemRepository;
import io.protone.service.dto.LIBMediaItemDTO;
import io.protone.service.mapper.LIBMediaItemMapper;

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

import io.protone.domain.enumeration.LIBItemTypeEnum;
import io.protone.domain.enumeration.LIBItemStateEnum;
/**
 * Test class for the LIBMediaItemResource REST controller.
 *
 * @see LIBMediaItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LIBMediaItemResourceIntTest {

    private static final String DEFAULT_IDX = "AAAAAAAAAA";
    private static final String UPDATED_IDX = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LIBItemTypeEnum DEFAULT_ITEM_TYPE = LIBItemTypeEnum.IT_AUDIO;
    private static final LIBItemTypeEnum UPDATED_ITEM_TYPE = LIBItemTypeEnum.IT_VIDEO;

    private static final Long DEFAULT_LENGTH = 1L;
    private static final Long UPDATED_LENGTH = 2L;

    private static final LIBItemStateEnum DEFAULT_STATE = LIBItemStateEnum.IS_NEW;
    private static final LIBItemStateEnum UPDATED_STATE = LIBItemStateEnum.IS_POSTPROCESS;

    private static final String DEFAULT_COMMAND = "AAAAAAAAAA";
    private static final String UPDATED_COMMAND = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Inject
    private LIBMediaItemRepository lIBMediaItemRepository;

    @Inject
    private LIBMediaItemMapper lIBMediaItemMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restLIBMediaItemMockMvc;

    private LIBMediaItem lIBMediaItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LIBMediaItemResource lIBMediaItemResource = new LIBMediaItemResource();
        ReflectionTestUtils.setField(lIBMediaItemResource, "lIBMediaItemRepository", lIBMediaItemRepository);
        ReflectionTestUtils.setField(lIBMediaItemResource, "lIBMediaItemMapper", lIBMediaItemMapper);
        this.restLIBMediaItemMockMvc = MockMvcBuilders.standaloneSetup(lIBMediaItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LIBMediaItem createEntity(EntityManager em) {
        LIBMediaItem lIBMediaItem = new LIBMediaItem()
                .idx(DEFAULT_IDX)
                .name(DEFAULT_NAME)
                .itemType(DEFAULT_ITEM_TYPE)
                .length(DEFAULT_LENGTH)
                .state(DEFAULT_STATE)
                .command(DEFAULT_COMMAND)
                .description(DEFAULT_DESCRIPTION);
        return lIBMediaItem;
    }

    @Before
    public void initTest() {
        lIBMediaItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createLIBMediaItem() throws Exception {
        int databaseSizeBeforeCreate = lIBMediaItemRepository.findAll().size();

        // Create the LIBMediaItem
        LIBMediaItemDTO lIBMediaItemDTO = lIBMediaItemMapper.lIBMediaItemToLIBMediaItemDTO(lIBMediaItem);

        restLIBMediaItemMockMvc.perform(post("/api/l-ib-media-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBMediaItemDTO)))
            .andExpect(status().isCreated());

        // Validate the LIBMediaItem in the database
        List<LIBMediaItem> lIBMediaItemList = lIBMediaItemRepository.findAll();
        assertThat(lIBMediaItemList).hasSize(databaseSizeBeforeCreate + 1);
        LIBMediaItem testLIBMediaItem = lIBMediaItemList.get(lIBMediaItemList.size() - 1);
        assertThat(testLIBMediaItem.getIdx()).isEqualTo(DEFAULT_IDX);
        assertThat(testLIBMediaItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLIBMediaItem.getItemType()).isEqualTo(DEFAULT_ITEM_TYPE);
        assertThat(testLIBMediaItem.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testLIBMediaItem.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testLIBMediaItem.getCommand()).isEqualTo(DEFAULT_COMMAND);
        assertThat(testLIBMediaItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createLIBMediaItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lIBMediaItemRepository.findAll().size();

        // Create the LIBMediaItem with an existing ID
        LIBMediaItem existingLIBMediaItem = new LIBMediaItem();
        existingLIBMediaItem.setId(1L);
        LIBMediaItemDTO existingLIBMediaItemDTO = lIBMediaItemMapper.lIBMediaItemToLIBMediaItemDTO(existingLIBMediaItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLIBMediaItemMockMvc.perform(post("/api/l-ib-media-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingLIBMediaItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LIBMediaItem> lIBMediaItemList = lIBMediaItemRepository.findAll();
        assertThat(lIBMediaItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdxIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBMediaItemRepository.findAll().size();
        // set the field null
        lIBMediaItem.setIdx(null);

        // Create the LIBMediaItem, which fails.
        LIBMediaItemDTO lIBMediaItemDTO = lIBMediaItemMapper.lIBMediaItemToLIBMediaItemDTO(lIBMediaItem);

        restLIBMediaItemMockMvc.perform(post("/api/l-ib-media-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBMediaItemDTO)))
            .andExpect(status().isBadRequest());

        List<LIBMediaItem> lIBMediaItemList = lIBMediaItemRepository.findAll();
        assertThat(lIBMediaItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBMediaItemRepository.findAll().size();
        // set the field null
        lIBMediaItem.setName(null);

        // Create the LIBMediaItem, which fails.
        LIBMediaItemDTO lIBMediaItemDTO = lIBMediaItemMapper.lIBMediaItemToLIBMediaItemDTO(lIBMediaItem);

        restLIBMediaItemMockMvc.perform(post("/api/l-ib-media-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBMediaItemDTO)))
            .andExpect(status().isBadRequest());

        List<LIBMediaItem> lIBMediaItemList = lIBMediaItemRepository.findAll();
        assertThat(lIBMediaItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkItemTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBMediaItemRepository.findAll().size();
        // set the field null
        lIBMediaItem.setItemType(null);

        // Create the LIBMediaItem, which fails.
        LIBMediaItemDTO lIBMediaItemDTO = lIBMediaItemMapper.lIBMediaItemToLIBMediaItemDTO(lIBMediaItem);

        restLIBMediaItemMockMvc.perform(post("/api/l-ib-media-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBMediaItemDTO)))
            .andExpect(status().isBadRequest());

        List<LIBMediaItem> lIBMediaItemList = lIBMediaItemRepository.findAll();
        assertThat(lIBMediaItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLengthIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBMediaItemRepository.findAll().size();
        // set the field null
        lIBMediaItem.setLength(null);

        // Create the LIBMediaItem, which fails.
        LIBMediaItemDTO lIBMediaItemDTO = lIBMediaItemMapper.lIBMediaItemToLIBMediaItemDTO(lIBMediaItem);

        restLIBMediaItemMockMvc.perform(post("/api/l-ib-media-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBMediaItemDTO)))
            .andExpect(status().isBadRequest());

        List<LIBMediaItem> lIBMediaItemList = lIBMediaItemRepository.findAll();
        assertThat(lIBMediaItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = lIBMediaItemRepository.findAll().size();
        // set the field null
        lIBMediaItem.setState(null);

        // Create the LIBMediaItem, which fails.
        LIBMediaItemDTO lIBMediaItemDTO = lIBMediaItemMapper.lIBMediaItemToLIBMediaItemDTO(lIBMediaItem);

        restLIBMediaItemMockMvc.perform(post("/api/l-ib-media-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBMediaItemDTO)))
            .andExpect(status().isBadRequest());

        List<LIBMediaItem> lIBMediaItemList = lIBMediaItemRepository.findAll();
        assertThat(lIBMediaItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLIBMediaItems() throws Exception {
        // Initialize the database
        lIBMediaItemRepository.saveAndFlush(lIBMediaItem);

        // Get all the lIBMediaItemList
        restLIBMediaItemMockMvc.perform(get("/api/l-ib-media-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lIBMediaItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].idx").value(hasItem(DEFAULT_IDX.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].itemType").value(hasItem(DEFAULT_ITEM_TYPE.toString())))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH.intValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].command").value(hasItem(DEFAULT_COMMAND.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getLIBMediaItem() throws Exception {
        // Initialize the database
        lIBMediaItemRepository.saveAndFlush(lIBMediaItem);

        // Get the lIBMediaItem
        restLIBMediaItemMockMvc.perform(get("/api/l-ib-media-items/{id}", lIBMediaItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lIBMediaItem.getId().intValue()))
            .andExpect(jsonPath("$.idx").value(DEFAULT_IDX.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.itemType").value(DEFAULT_ITEM_TYPE.toString()))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH.intValue()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.command").value(DEFAULT_COMMAND.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLIBMediaItem() throws Exception {
        // Get the lIBMediaItem
        restLIBMediaItemMockMvc.perform(get("/api/l-ib-media-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLIBMediaItem() throws Exception {
        // Initialize the database
        lIBMediaItemRepository.saveAndFlush(lIBMediaItem);
        int databaseSizeBeforeUpdate = lIBMediaItemRepository.findAll().size();

        // Update the lIBMediaItem
        LIBMediaItem updatedLIBMediaItem = lIBMediaItemRepository.findOne(lIBMediaItem.getId());
        updatedLIBMediaItem
                .idx(UPDATED_IDX)
                .name(UPDATED_NAME)
                .itemType(UPDATED_ITEM_TYPE)
                .length(UPDATED_LENGTH)
                .state(UPDATED_STATE)
                .command(UPDATED_COMMAND)
                .description(UPDATED_DESCRIPTION);
        LIBMediaItemDTO lIBMediaItemDTO = lIBMediaItemMapper.lIBMediaItemToLIBMediaItemDTO(updatedLIBMediaItem);

        restLIBMediaItemMockMvc.perform(put("/api/l-ib-media-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBMediaItemDTO)))
            .andExpect(status().isOk());

        // Validate the LIBMediaItem in the database
        List<LIBMediaItem> lIBMediaItemList = lIBMediaItemRepository.findAll();
        assertThat(lIBMediaItemList).hasSize(databaseSizeBeforeUpdate);
        LIBMediaItem testLIBMediaItem = lIBMediaItemList.get(lIBMediaItemList.size() - 1);
        assertThat(testLIBMediaItem.getIdx()).isEqualTo(UPDATED_IDX);
        assertThat(testLIBMediaItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLIBMediaItem.getItemType()).isEqualTo(UPDATED_ITEM_TYPE);
        assertThat(testLIBMediaItem.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testLIBMediaItem.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testLIBMediaItem.getCommand()).isEqualTo(UPDATED_COMMAND);
        assertThat(testLIBMediaItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingLIBMediaItem() throws Exception {
        int databaseSizeBeforeUpdate = lIBMediaItemRepository.findAll().size();

        // Create the LIBMediaItem
        LIBMediaItemDTO lIBMediaItemDTO = lIBMediaItemMapper.lIBMediaItemToLIBMediaItemDTO(lIBMediaItem);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLIBMediaItemMockMvc.perform(put("/api/l-ib-media-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lIBMediaItemDTO)))
            .andExpect(status().isCreated());

        // Validate the LIBMediaItem in the database
        List<LIBMediaItem> lIBMediaItemList = lIBMediaItemRepository.findAll();
        assertThat(lIBMediaItemList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLIBMediaItem() throws Exception {
        // Initialize the database
        lIBMediaItemRepository.saveAndFlush(lIBMediaItem);
        int databaseSizeBeforeDelete = lIBMediaItemRepository.findAll().size();

        // Get the lIBMediaItem
        restLIBMediaItemMockMvc.perform(delete("/api/l-ib-media-items/{id}", lIBMediaItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LIBMediaItem> lIBMediaItemList = lIBMediaItemRepository.findAll();
        assertThat(lIBMediaItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
