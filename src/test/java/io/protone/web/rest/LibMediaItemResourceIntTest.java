package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.LibMediaItem;
import io.protone.repository.LibMediaItemRepository;
import io.protone.service.dto.LibMediaItemDTO;
import io.protone.service.mapper.LibMediaItemMapper;

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

import io.protone.domain.enumeration.LibItemTypeEnum;
import io.protone.domain.enumeration.LibItemStateEnum;
/**
 * Test class for the LibMediaItemResource REST controller.
 *
 * @see LibMediaItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LibMediaItemResourceIntTest {

    private static final String DEFAULT_IDX = "AAAAAAAAAA";
    private static final String UPDATED_IDX = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LibItemTypeEnum DEFAULT_ITEM_TYPE = LibItemTypeEnum.IT_AUDIO;
    private static final LibItemTypeEnum UPDATED_ITEM_TYPE = LibItemTypeEnum.IT_VIDEO;

    private static final Long DEFAULT_LENGTH = 1L;
    private static final Long UPDATED_LENGTH = 2L;

    private static final LibItemStateEnum DEFAULT_STATE = LibItemStateEnum.IS_NEW;
    private static final LibItemStateEnum UPDATED_STATE = LibItemStateEnum.IS_POSTPROCESS;

    private static final String DEFAULT_COMMAND = "AAAAAAAAAA";
    private static final String UPDATED_COMMAND = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private LibMediaItemRepository libMediaItemRepository;

    @Autowired
    private LibMediaItemMapper libMediaItemMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restLibMediaItemMockMvc;

    private LibMediaItem libMediaItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            LibMediaItemResource libMediaItemResource = new LibMediaItemResource(libMediaItemRepository, libMediaItemMapper);
        this.restLibMediaItemMockMvc = MockMvcBuilders.standaloneSetup(libMediaItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LibMediaItem createEntity(EntityManager em) {
        LibMediaItem libMediaItem = new LibMediaItem()
                .idx(DEFAULT_IDX)
                .name(DEFAULT_NAME)
                .itemType(DEFAULT_ITEM_TYPE)
                .length(DEFAULT_LENGTH)
                .state(DEFAULT_STATE)
                .command(DEFAULT_COMMAND)
                .description(DEFAULT_DESCRIPTION);
        return libMediaItem;
    }

    @Before
    public void initTest() {
        libMediaItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createLibMediaItem() throws Exception {
        int databaseSizeBeforeCreate = libMediaItemRepository.findAll().size();

        // Create the LibMediaItem
        LibMediaItemDTO libMediaItemDTO = libMediaItemMapper.libMediaItemToLibMediaItemDTO(libMediaItem);

        restLibMediaItemMockMvc.perform(post("/api/lib-media-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libMediaItemDTO)))
            .andExpect(status().isCreated());

        // Validate the LibMediaItem in the database
        List<LibMediaItem> libMediaItemList = libMediaItemRepository.findAll();
        assertThat(libMediaItemList).hasSize(databaseSizeBeforeCreate + 1);
        LibMediaItem testLibMediaItem = libMediaItemList.get(libMediaItemList.size() - 1);
        assertThat(testLibMediaItem.getIdx()).isEqualTo(DEFAULT_IDX);
        assertThat(testLibMediaItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLibMediaItem.getItemType()).isEqualTo(DEFAULT_ITEM_TYPE);
        assertThat(testLibMediaItem.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testLibMediaItem.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testLibMediaItem.getCommand()).isEqualTo(DEFAULT_COMMAND);
        assertThat(testLibMediaItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createLibMediaItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = libMediaItemRepository.findAll().size();

        // Create the LibMediaItem with an existing ID
        LibMediaItem existingLibMediaItem = new LibMediaItem();
        existingLibMediaItem.setId(1L);
        LibMediaItemDTO existingLibMediaItemDTO = libMediaItemMapper.libMediaItemToLibMediaItemDTO(existingLibMediaItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLibMediaItemMockMvc.perform(post("/api/lib-media-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingLibMediaItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LibMediaItem> libMediaItemList = libMediaItemRepository.findAll();
        assertThat(libMediaItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdxIsRequired() throws Exception {
        int databaseSizeBeforeTest = libMediaItemRepository.findAll().size();
        // set the field null
        libMediaItem.setIdx(null);

        // Create the LibMediaItem, which fails.
        LibMediaItemDTO libMediaItemDTO = libMediaItemMapper.libMediaItemToLibMediaItemDTO(libMediaItem);

        restLibMediaItemMockMvc.perform(post("/api/lib-media-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libMediaItemDTO)))
            .andExpect(status().isBadRequest());

        List<LibMediaItem> libMediaItemList = libMediaItemRepository.findAll();
        assertThat(libMediaItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = libMediaItemRepository.findAll().size();
        // set the field null
        libMediaItem.setName(null);

        // Create the LibMediaItem, which fails.
        LibMediaItemDTO libMediaItemDTO = libMediaItemMapper.libMediaItemToLibMediaItemDTO(libMediaItem);

        restLibMediaItemMockMvc.perform(post("/api/lib-media-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libMediaItemDTO)))
            .andExpect(status().isBadRequest());

        List<LibMediaItem> libMediaItemList = libMediaItemRepository.findAll();
        assertThat(libMediaItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkItemTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = libMediaItemRepository.findAll().size();
        // set the field null
        libMediaItem.setItemType(null);

        // Create the LibMediaItem, which fails.
        LibMediaItemDTO libMediaItemDTO = libMediaItemMapper.libMediaItemToLibMediaItemDTO(libMediaItem);

        restLibMediaItemMockMvc.perform(post("/api/lib-media-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libMediaItemDTO)))
            .andExpect(status().isBadRequest());

        List<LibMediaItem> libMediaItemList = libMediaItemRepository.findAll();
        assertThat(libMediaItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLengthIsRequired() throws Exception {
        int databaseSizeBeforeTest = libMediaItemRepository.findAll().size();
        // set the field null
        libMediaItem.setLength(null);

        // Create the LibMediaItem, which fails.
        LibMediaItemDTO libMediaItemDTO = libMediaItemMapper.libMediaItemToLibMediaItemDTO(libMediaItem);

        restLibMediaItemMockMvc.perform(post("/api/lib-media-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libMediaItemDTO)))
            .andExpect(status().isBadRequest());

        List<LibMediaItem> libMediaItemList = libMediaItemRepository.findAll();
        assertThat(libMediaItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = libMediaItemRepository.findAll().size();
        // set the field null
        libMediaItem.setState(null);

        // Create the LibMediaItem, which fails.
        LibMediaItemDTO libMediaItemDTO = libMediaItemMapper.libMediaItemToLibMediaItemDTO(libMediaItem);

        restLibMediaItemMockMvc.perform(post("/api/lib-media-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libMediaItemDTO)))
            .andExpect(status().isBadRequest());

        List<LibMediaItem> libMediaItemList = libMediaItemRepository.findAll();
        assertThat(libMediaItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLibMediaItems() throws Exception {
        // Initialize the database
        libMediaItemRepository.saveAndFlush(libMediaItem);

        // Get all the libMediaItemList
        restLibMediaItemMockMvc.perform(get("/api/lib-media-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(libMediaItem.getId().intValue())))
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
    public void getLibMediaItem() throws Exception {
        // Initialize the database
        libMediaItemRepository.saveAndFlush(libMediaItem);

        // Get the libMediaItem
        restLibMediaItemMockMvc.perform(get("/api/lib-media-items/{id}", libMediaItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(libMediaItem.getId().intValue()))
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
    public void getNonExistingLibMediaItem() throws Exception {
        // Get the libMediaItem
        restLibMediaItemMockMvc.perform(get("/api/lib-media-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLibMediaItem() throws Exception {
        // Initialize the database
        libMediaItemRepository.saveAndFlush(libMediaItem);
        int databaseSizeBeforeUpdate = libMediaItemRepository.findAll().size();

        // Update the libMediaItem
        LibMediaItem updatedLibMediaItem = libMediaItemRepository.findOne(libMediaItem.getId());
        updatedLibMediaItem
                .idx(UPDATED_IDX)
                .name(UPDATED_NAME)
                .itemType(UPDATED_ITEM_TYPE)
                .length(UPDATED_LENGTH)
                .state(UPDATED_STATE)
                .command(UPDATED_COMMAND)
                .description(UPDATED_DESCRIPTION);
        LibMediaItemDTO libMediaItemDTO = libMediaItemMapper.libMediaItemToLibMediaItemDTO(updatedLibMediaItem);

        restLibMediaItemMockMvc.perform(put("/api/lib-media-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libMediaItemDTO)))
            .andExpect(status().isOk());

        // Validate the LibMediaItem in the database
        List<LibMediaItem> libMediaItemList = libMediaItemRepository.findAll();
        assertThat(libMediaItemList).hasSize(databaseSizeBeforeUpdate);
        LibMediaItem testLibMediaItem = libMediaItemList.get(libMediaItemList.size() - 1);
        assertThat(testLibMediaItem.getIdx()).isEqualTo(UPDATED_IDX);
        assertThat(testLibMediaItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLibMediaItem.getItemType()).isEqualTo(UPDATED_ITEM_TYPE);
        assertThat(testLibMediaItem.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testLibMediaItem.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testLibMediaItem.getCommand()).isEqualTo(UPDATED_COMMAND);
        assertThat(testLibMediaItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingLibMediaItem() throws Exception {
        int databaseSizeBeforeUpdate = libMediaItemRepository.findAll().size();

        // Create the LibMediaItem
        LibMediaItemDTO libMediaItemDTO = libMediaItemMapper.libMediaItemToLibMediaItemDTO(libMediaItem);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLibMediaItemMockMvc.perform(put("/api/lib-media-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libMediaItemDTO)))
            .andExpect(status().isCreated());

        // Validate the LibMediaItem in the database
        List<LibMediaItem> libMediaItemList = libMediaItemRepository.findAll();
        assertThat(libMediaItemList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLibMediaItem() throws Exception {
        // Initialize the database
        libMediaItemRepository.saveAndFlush(libMediaItem);
        int databaseSizeBeforeDelete = libMediaItemRepository.findAll().size();

        // Get the libMediaItem
        restLibMediaItemMockMvc.perform(delete("/api/lib-media-items/{id}", libMediaItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LibMediaItem> libMediaItemList = libMediaItemRepository.findAll();
        assertThat(libMediaItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LibMediaItem.class);
    }
}
