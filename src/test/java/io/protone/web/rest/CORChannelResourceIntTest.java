package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CORChannel;
import io.protone.repository.CORChannelRepository;
import io.protone.service.dto.CORChannelDTO;
import io.protone.service.mapper.CORChannelMapper;

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
 * Test class for the CORChannelResource REST controller.
 *
 * @see CORChannelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CORChannelResourceIntTest {

    private static final String DEFAULT_SHORTCUT = "AAA";
    private static final String UPDATED_SHORTCUT = "BBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Inject
    private CORChannelRepository cORChannelRepository;

    @Inject
    private CORChannelMapper cORChannelMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCORChannelMockMvc;

    private CORChannel cORChannel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CORChannelResource cORChannelResource = new CORChannelResource();
        ReflectionTestUtils.setField(cORChannelResource, "cORChannelRepository", cORChannelRepository);
        ReflectionTestUtils.setField(cORChannelResource, "cORChannelMapper", cORChannelMapper);
        this.restCORChannelMockMvc = MockMvcBuilders.standaloneSetup(cORChannelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CORChannel createEntity(EntityManager em) {
        CORChannel cORChannel = new CORChannel()
                .shortcut(DEFAULT_SHORTCUT)
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION);
        return cORChannel;
    }

    @Before
    public void initTest() {
        cORChannel = createEntity(em);
    }

    @Test
    @Transactional
    public void createCORChannel() throws Exception {
        int databaseSizeBeforeCreate = cORChannelRepository.findAll().size();

        // Create the CORChannel
        CORChannelDTO cORChannelDTO = cORChannelMapper.cORChannelToCORChannelDTO(cORChannel);

        restCORChannelMockMvc.perform(post("/api/c-or-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORChannelDTO)))
            .andExpect(status().isCreated());

        // Validate the CORChannel in the database
        List<CORChannel> cORChannelList = cORChannelRepository.findAll();
        assertThat(cORChannelList).hasSize(databaseSizeBeforeCreate + 1);
        CORChannel testCORChannel = cORChannelList.get(cORChannelList.size() - 1);
        assertThat(testCORChannel.getShortcut()).isEqualTo(DEFAULT_SHORTCUT);
        assertThat(testCORChannel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCORChannel.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCORChannelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cORChannelRepository.findAll().size();

        // Create the CORChannel with an existing ID
        CORChannel existingCORChannel = new CORChannel();
        existingCORChannel.setId(1L);
        CORChannelDTO existingCORChannelDTO = cORChannelMapper.cORChannelToCORChannelDTO(existingCORChannel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCORChannelMockMvc.perform(post("/api/c-or-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCORChannelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CORChannel> cORChannelList = cORChannelRepository.findAll();
        assertThat(cORChannelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkShortcutIsRequired() throws Exception {
        int databaseSizeBeforeTest = cORChannelRepository.findAll().size();
        // set the field null
        cORChannel.setShortcut(null);

        // Create the CORChannel, which fails.
        CORChannelDTO cORChannelDTO = cORChannelMapper.cORChannelToCORChannelDTO(cORChannel);

        restCORChannelMockMvc.perform(post("/api/c-or-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORChannelDTO)))
            .andExpect(status().isBadRequest());

        List<CORChannel> cORChannelList = cORChannelRepository.findAll();
        assertThat(cORChannelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cORChannelRepository.findAll().size();
        // set the field null
        cORChannel.setName(null);

        // Create the CORChannel, which fails.
        CORChannelDTO cORChannelDTO = cORChannelMapper.cORChannelToCORChannelDTO(cORChannel);

        restCORChannelMockMvc.perform(post("/api/c-or-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORChannelDTO)))
            .andExpect(status().isBadRequest());

        List<CORChannel> cORChannelList = cORChannelRepository.findAll();
        assertThat(cORChannelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCORChannels() throws Exception {
        // Initialize the database
        cORChannelRepository.saveAndFlush(cORChannel);

        // Get all the cORChannelList
        restCORChannelMockMvc.perform(get("/api/c-or-channels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cORChannel.getId().intValue())))
            .andExpect(jsonPath("$.[*].shortcut").value(hasItem(DEFAULT_SHORTCUT.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCORChannel() throws Exception {
        // Initialize the database
        cORChannelRepository.saveAndFlush(cORChannel);

        // Get the cORChannel
        restCORChannelMockMvc.perform(get("/api/c-or-channels/{id}", cORChannel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cORChannel.getId().intValue()))
            .andExpect(jsonPath("$.shortcut").value(DEFAULT_SHORTCUT.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCORChannel() throws Exception {
        // Get the cORChannel
        restCORChannelMockMvc.perform(get("/api/c-or-channels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCORChannel() throws Exception {
        // Initialize the database
        cORChannelRepository.saveAndFlush(cORChannel);
        int databaseSizeBeforeUpdate = cORChannelRepository.findAll().size();

        // Update the cORChannel
        CORChannel updatedCORChannel = cORChannelRepository.findOne(cORChannel.getId());
        updatedCORChannel
                .shortcut(UPDATED_SHORTCUT)
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION);
        CORChannelDTO cORChannelDTO = cORChannelMapper.cORChannelToCORChannelDTO(updatedCORChannel);

        restCORChannelMockMvc.perform(put("/api/c-or-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORChannelDTO)))
            .andExpect(status().isOk());

        // Validate the CORChannel in the database
        List<CORChannel> cORChannelList = cORChannelRepository.findAll();
        assertThat(cORChannelList).hasSize(databaseSizeBeforeUpdate);
        CORChannel testCORChannel = cORChannelList.get(cORChannelList.size() - 1);
        assertThat(testCORChannel.getShortcut()).isEqualTo(UPDATED_SHORTCUT);
        assertThat(testCORChannel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCORChannel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCORChannel() throws Exception {
        int databaseSizeBeforeUpdate = cORChannelRepository.findAll().size();

        // Create the CORChannel
        CORChannelDTO cORChannelDTO = cORChannelMapper.cORChannelToCORChannelDTO(cORChannel);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCORChannelMockMvc.perform(put("/api/c-or-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cORChannelDTO)))
            .andExpect(status().isCreated());

        // Validate the CORChannel in the database
        List<CORChannel> cORChannelList = cORChannelRepository.findAll();
        assertThat(cORChannelList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCORChannel() throws Exception {
        // Initialize the database
        cORChannelRepository.saveAndFlush(cORChannel);
        int databaseSizeBeforeDelete = cORChannelRepository.findAll().size();

        // Get the cORChannel
        restCORChannelMockMvc.perform(delete("/api/c-or-channels/{id}", cORChannel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CORChannel> cORChannelList = cORChannelRepository.findAll();
        assertThat(cORChannelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
