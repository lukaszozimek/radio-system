package io.protone.web.rest;

import io.protone.ProtoneApp;

import io.protone.domain.CorChannel;
import io.protone.repository.CorChannelRepository;
import io.protone.service.dto.CorChannelDTO;
import io.protone.service.mapper.CorChannelMapper;

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
 * Test class for the CorChannelResource REST controller.
 *
 * @see CorChannelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CorChannelResourceIntTest {

    private static final String DEFAULT_SHORTCUT = "AAA";
    private static final String UPDATED_SHORTCUT = "BBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CorChannelRepository corChannelRepository;

    @Autowired
    private CorChannelMapper corChannelMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restCorChannelMockMvc;

    private CorChannel corChannel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            CorChannelResource corChannelResource = new CorChannelResource(corChannelRepository, corChannelMapper);
        this.restCorChannelMockMvc = MockMvcBuilders.standaloneSetup(corChannelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CorChannel createEntity(EntityManager em) {
        CorChannel corChannel = new CorChannel()
                .shortcut(DEFAULT_SHORTCUT)
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION);
        return corChannel;
    }

    @Before
    public void initTest() {
        corChannel = createEntity(em);
    }

    @Test
    @Transactional
    public void createCorChannel() throws Exception {
        int databaseSizeBeforeCreate = corChannelRepository.findAll().size();

        // Create the CorChannel
        CorChannelDTO corChannelDTO = corChannelMapper.corChannelToCorChannelDTO(corChannel);

        restCorChannelMockMvc.perform(post("/api/cor-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corChannelDTO)))
            .andExpect(status().isCreated());

        // Validate the CorChannel in the database
        List<CorChannel> corChannelList = corChannelRepository.findAll();
        assertThat(corChannelList).hasSize(databaseSizeBeforeCreate + 1);
        CorChannel testCorChannel = corChannelList.get(corChannelList.size() - 1);
        assertThat(testCorChannel.getShortcut()).isEqualTo(DEFAULT_SHORTCUT);
        assertThat(testCorChannel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCorChannel.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCorChannelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = corChannelRepository.findAll().size();

        // Create the CorChannel with an existing ID
        CorChannel existingCorChannel = new CorChannel();
        existingCorChannel.setId(1L);
        CorChannelDTO existingCorChannelDTO = corChannelMapper.corChannelToCorChannelDTO(existingCorChannel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorChannelMockMvc.perform(post("/api/cor-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCorChannelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CorChannel> corChannelList = corChannelRepository.findAll();
        assertThat(corChannelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkShortcutIsRequired() throws Exception {
        int databaseSizeBeforeTest = corChannelRepository.findAll().size();
        // set the field null
        corChannel.setShortcut(null);

        // Create the CorChannel, which fails.
        CorChannelDTO corChannelDTO = corChannelMapper.corChannelToCorChannelDTO(corChannel);

        restCorChannelMockMvc.perform(post("/api/cor-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corChannelDTO)))
            .andExpect(status().isBadRequest());

        List<CorChannel> corChannelList = corChannelRepository.findAll();
        assertThat(corChannelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = corChannelRepository.findAll().size();
        // set the field null
        corChannel.setName(null);

        // Create the CorChannel, which fails.
        CorChannelDTO corChannelDTO = corChannelMapper.corChannelToCorChannelDTO(corChannel);

        restCorChannelMockMvc.perform(post("/api/cor-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corChannelDTO)))
            .andExpect(status().isBadRequest());

        List<CorChannel> corChannelList = corChannelRepository.findAll();
        assertThat(corChannelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCorChannels() throws Exception {
        // Initialize the database
        corChannelRepository.saveAndFlush(corChannel);

        // Get all the corChannelList
        restCorChannelMockMvc.perform(get("/api/cor-channels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(corChannel.getId().intValue())))
            .andExpect(jsonPath("$.[*].shortcut").value(hasItem(DEFAULT_SHORTCUT.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCorChannel() throws Exception {
        // Initialize the database
        corChannelRepository.saveAndFlush(corChannel);

        // Get the corChannel
        restCorChannelMockMvc.perform(get("/api/cor-channels/{id}", corChannel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(corChannel.getId().intValue()))
            .andExpect(jsonPath("$.shortcut").value(DEFAULT_SHORTCUT.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCorChannel() throws Exception {
        // Get the corChannel
        restCorChannelMockMvc.perform(get("/api/cor-channels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorChannel() throws Exception {
        // Initialize the database
        corChannelRepository.saveAndFlush(corChannel);
        int databaseSizeBeforeUpdate = corChannelRepository.findAll().size();

        // Update the corChannel
        CorChannel updatedCorChannel = corChannelRepository.findOne(corChannel.getId());
        updatedCorChannel
                .shortcut(UPDATED_SHORTCUT)
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION);
        CorChannelDTO corChannelDTO = corChannelMapper.corChannelToCorChannelDTO(updatedCorChannel);

        restCorChannelMockMvc.perform(put("/api/cor-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corChannelDTO)))
            .andExpect(status().isOk());

        // Validate the CorChannel in the database
        List<CorChannel> corChannelList = corChannelRepository.findAll();
        assertThat(corChannelList).hasSize(databaseSizeBeforeUpdate);
        CorChannel testCorChannel = corChannelList.get(corChannelList.size() - 1);
        assertThat(testCorChannel.getShortcut()).isEqualTo(UPDATED_SHORTCUT);
        assertThat(testCorChannel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCorChannel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCorChannel() throws Exception {
        int databaseSizeBeforeUpdate = corChannelRepository.findAll().size();

        // Create the CorChannel
        CorChannelDTO corChannelDTO = corChannelMapper.corChannelToCorChannelDTO(corChannel);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCorChannelMockMvc.perform(put("/api/cor-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(corChannelDTO)))
            .andExpect(status().isCreated());

        // Validate the CorChannel in the database
        List<CorChannel> corChannelList = corChannelRepository.findAll();
        assertThat(corChannelList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCorChannel() throws Exception {
        // Initialize the database
        corChannelRepository.saveAndFlush(corChannel);
        int databaseSizeBeforeDelete = corChannelRepository.findAll().size();

        // Get the corChannel
        restCorChannelMockMvc.perform(delete("/api/cor-channels/{id}", corChannel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CorChannel> corChannelList = corChannelRepository.findAll();
        assertThat(corChannelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorChannel.class);
    }
}