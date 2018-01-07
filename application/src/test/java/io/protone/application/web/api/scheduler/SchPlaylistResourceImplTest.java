package io.protone.application.web.api.scheduler;


import io.protone.application.ProtoneApp;
import io.protone.application.util.TestUtil;
import io.protone.application.web.api.scheduler.impl.SchPlaylistResourceImpl;
import io.protone.application.web.rest.errors.ExceptionTranslator;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorOrganization;
import io.protone.core.service.CorChannelService;
import io.protone.scheduler.api.dto.SchPlaylistDTO;
import io.protone.scheduler.domain.SchPlaylist;
import io.protone.scheduler.mapper.SchPlaylistMapper;
import io.protone.scheduler.repository.SchPlaylistRepository;
import io.protone.scheduler.service.SchPlaylistService;
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
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static io.protone.application.util.TestConstans.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by lukaszozimek on 14.05.2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchPlaylistResourceImplTest {


    private static final LocalDate DEFAULT_PLAYLIST_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PLAYLIST_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SchPlaylistRepository schPlaylistRepository;


    @Autowired
    private SchPlaylistService schPlaylistService;

    @Autowired
    private SchPlaylistMapper schPlaylistMapper;

    @Autowired
    private CorChannelService corChannelService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSchPlaylistMockMvc;

    private SchPlaylist schPlaylist;

    private CorOrganization corOrganization;

    private CorChannel corChannel;


    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchPlaylist createEntity(EntityManager em) {
        SchPlaylist traPlaylist = new SchPlaylist()
                .date(DEFAULT_PLAYLIST_DATE);
        return traPlaylist;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SchPlaylistResourceImpl traPlaylistResource = new SchPlaylistResourceImpl();
        ReflectionTestUtils.setField(traPlaylistResource, "schPlaylistService", schPlaylistService);
        ReflectionTestUtils.setField(traPlaylistResource, "schPlaylistMapper", schPlaylistMapper);
        ReflectionTestUtils.setField(traPlaylistResource, "corChannelService", corChannelService);


        this.restSchPlaylistMockMvc = MockMvcBuilders.standaloneSetup(traPlaylistResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        corOrganization = new CorOrganization().shortcut(TEST_ORGANIZATION_SHORTCUT);
        corOrganization.setId(TEST_ORGANIZATION_ID);
        corChannel = new CorChannel().shortcut(TEST_CHANNEL_SHORTCUT);
        corChannel.setId(TEST_CHANNEL_ID);
        schPlaylist = createEntity(em).channel(corChannel);
    }

    @Test
    @Transactional
    public void createSchPlaylist() throws Exception {
        int databaseSizeBeforeCreate = schPlaylistRepository.findAll().size();

        // Create the SchPlaylist
        SchPlaylistDTO schPlaylistDTO = schPlaylistMapper.DB2DTO(schPlaylist);

        restSchPlaylistMockMvc.perform(post("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/playlist", corOrganization.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(schPlaylistDTO)))
                .andExpect(status().isCreated());

        // Validate the SchPlaylist in the database
        List<SchPlaylist> traPlaylistList = schPlaylistRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeCreate + 1);
        SchPlaylist testSchPlaylist = traPlaylistList.get(traPlaylistList.size() - 1);
        assertThat(testSchPlaylist.getDate()).isEqualTo(DEFAULT_PLAYLIST_DATE);
    }


    @Test
    @Transactional
    public void createSchPlaylistWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schPlaylistRepository.findAll().size();

        // Create the SchPlaylist with an existing ID
        SchPlaylist existingSchPlaylist = new SchPlaylist();
        existingSchPlaylist.setId(1L);
        SchPlaylistDTO existingSchPlaylistDTO = schPlaylistMapper.DB2DTO(existingSchPlaylist);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchPlaylistMockMvc.perform(post("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/playlist", corOrganization.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(existingSchPlaylistDTO)))
                .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SchPlaylist> traPlaylistList = schPlaylistRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSchPlaylists() throws Exception {
        // Initialize the database
        schPlaylistRepository.saveAndFlush(schPlaylist.channel(corChannel));

        // Get all the traPlaylistList
        restSchPlaylistMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/playlist?sort=id,desc", corOrganization.getShortcut(), corChannel.getShortcut()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(schPlaylist.getId().intValue())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_PLAYLIST_DATE.toString())));
    }

    @Test
    @Transactional
    public void getSchPlaylist() throws Exception {
        // Initialize the database
        schPlaylistRepository.saveAndFlush(schPlaylist.channel(corChannel));

        // Get the schPlaylist
        restSchPlaylistMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/playlist/{date}", corOrganization.getShortcut(), corChannel.getShortcut(), DEFAULT_PLAYLIST_DATE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(schPlaylist.getId().intValue()))
                .andExpect(jsonPath("$.date").value(DEFAULT_PLAYLIST_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSchPlaylist() throws Exception {
        // Get the schPlaylist
        LocalDate localDate = LocalDate.now();
        restSchPlaylistMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/playlist/{date}", corOrganization.getShortcut(), corChannel.getShortcut(), localDate))
                .andExpect(status().isNotFound());

    }

    @Test
    @Transactional
    public void updateSchPlaylist() throws Exception {
        // Initialize the database
        schPlaylistRepository.saveAndFlush(schPlaylist.channel(corChannel));
        int databaseSizeBeforeUpdate = schPlaylistRepository.findAll().size();

        // Update the schPlaylist
        SchPlaylist updatedSchPlaylist = schPlaylistRepository.findOne(schPlaylist.getId());
        updatedSchPlaylist
                .date(UPDATED_PLAYLIST_DATE);
        SchPlaylistDTO schPlaylistDTO = schPlaylistMapper.DB2DTO(updatedSchPlaylist);

        restSchPlaylistMockMvc.perform(put("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/playlist", corOrganization.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(schPlaylistDTO)))
                .andExpect(status().isOk());

        // Validate the SchPlaylist in the database
        List<SchPlaylist> traPlaylistList = schPlaylistRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeUpdate);
        SchPlaylist testSchPlaylist = traPlaylistList.get(traPlaylistList.size() - 1);
        assertThat(testSchPlaylist.getDate()).isEqualTo(UPDATED_PLAYLIST_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingSchPlaylist() throws Exception {
        int databaseSizeBeforeUpdate = schPlaylistRepository.findAll().size();

        // Create the SchPlaylist
        SchPlaylistDTO schPlaylistDTO = schPlaylistMapper.DB2DTO(schPlaylist);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSchPlaylistMockMvc.perform(put("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/playlist", corOrganization.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(schPlaylistDTO)))
                .andExpect(status().isCreated());

        // Validate the SchPlaylist in the database
        List<SchPlaylist> traPlaylistList = schPlaylistRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSchPlaylist() throws Exception {
        // Initialize the database
        schPlaylistRepository.saveAndFlush(schPlaylist.channel(corChannel));
        int databaseSizeBeforeDelete = schPlaylistRepository.findAll().size();

        // Get the schPlaylist
        restSchPlaylistMockMvc.perform(delete("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/playlist/{date}", corOrganization.getShortcut(), corChannel.getShortcut(), DEFAULT_PLAYLIST_DATE)
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SchPlaylist> traPlaylistList = schPlaylistRepository.findAll();
        assertThat(traPlaylistList).hasSize(databaseSizeBeforeDelete - 1);
    }


    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        schPlaylist = createEntity(em).channel(corChannel);

        int databaseSizeBeforeTest = schPlaylistRepository.findAll().size();
        // set the field null
        schPlaylist.date(null);

        // Create the TraOrder, which fails.
        SchPlaylistDTO schPlaylistDTO = schPlaylistMapper.DB2DTO(schPlaylist);

        restSchPlaylistMockMvc.perform(post("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/playlist", corOrganization.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(schPlaylistDTO)))
                .andExpect(status().isBadRequest());

        List<SchPlaylist> traOrderList = schPlaylistRepository.findAll();
        assertThat(traOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchPlaylist.class);
    }

}
