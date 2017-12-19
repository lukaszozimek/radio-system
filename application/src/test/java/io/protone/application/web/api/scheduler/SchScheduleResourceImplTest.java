package io.protone.application.web.api.scheduler;


import io.protone.application.ProtoneApp;
import io.protone.application.util.TestUtil;
import io.protone.application.web.api.cor.CorNetworkResourceIntTest;
import io.protone.application.web.api.scheduler.impl.SchScheduleResourceImpl;
import io.protone.application.web.rest.errors.ExceptionTranslator;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorChannelService;
import io.protone.core.service.CorNetworkService;
import io.protone.scheduler.api.dto.SchScheduleDTO;
import io.protone.scheduler.domain.SchSchedule;
import io.protone.scheduler.mapper.SchScheduleMapper;
import io.protone.scheduler.repository.SchScheduleRepository;
import io.protone.scheduler.service.SchScheduleService;
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
public class SchScheduleResourceImplTest {

    private static final LocalDate DEFAULT_PLAYLIST_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PLAYLIST_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SchScheduleRepository schScheduleRepository;


    @Autowired
    private SchScheduleService schScheduleService;

    @Autowired
    private SchScheduleMapper schScheduleMapper;

    @Autowired
    private CorChannelService corChannelService;

    @Autowired
    private CorNetworkService corNetworkService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSchScheduleMockMvc;

    private SchSchedule schSchedule;

    private CorNetwork corNetwork;

    private CorChannel corChannel;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchSchedule createEntity(EntityManager em) {
        SchSchedule traPlaylist = new SchSchedule()
                .date(DEFAULT_PLAYLIST_DATE);
        return traPlaylist;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SchScheduleResourceImpl schScheduleResource = new SchScheduleResourceImpl();
        ReflectionTestUtils.setField(schScheduleResource, "schScheduleService", schScheduleService);
        ReflectionTestUtils.setField(schScheduleResource, "schScheduleMapper", schScheduleMapper);
        ReflectionTestUtils.setField(schScheduleResource, "corNetworkService", corNetworkService);
        ReflectionTestUtils.setField(schScheduleResource, "corChannelService", corChannelService);


        this.restSchScheduleMockMvc = MockMvcBuilders.standaloneSetup(schScheduleResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        corNetwork = new CorNetwork().shortcut(CorNetworkResourceIntTest.TEST_NETWORK);
        corNetwork.setId(1L);
        corChannel = new CorChannel().shortcut("tes");
        corChannel.setId(1L);
        schSchedule = createEntity(em).network(corNetwork).channel(corChannel);
    }

    @Test
    @Transactional
    public void createSchSchedule() throws Exception {
        int databaseSizeBeforeCreate = schScheduleRepository.findAll().size();

        // Create the SchSchedule
        SchScheduleDTO traPlaylistDTO = schScheduleMapper.DB2DTO(schSchedule);

        restSchScheduleMockMvc.perform(post("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/schedule", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traPlaylistDTO)))
                .andExpect(status().isCreated());

        // Validate the SchSchedule in the database
        List<SchSchedule> schSchedules = schScheduleRepository.findAll();
        assertThat(schSchedules).hasSize(databaseSizeBeforeCreate + 1);
        SchSchedule testSchSchedule = schSchedules.get(schSchedules.size() - 1);
    }


    @Test
    @Transactional
    public void createSchScheduleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schScheduleRepository.findAll().size();

        // Create the SchSchedule with an existing ID
        SchSchedule existingSchSchedule = new SchSchedule();
        existingSchSchedule.setId(1L);
        SchScheduleDTO existingSchScheduleDTO = schScheduleMapper.DB2DTO(existingSchSchedule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchScheduleMockMvc.perform(post("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/schedule", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(existingSchScheduleDTO)))
                .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SchSchedule> schSchedules = schScheduleRepository.findAll();
        assertThat(schSchedules).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSchSchedules() throws Exception {
        // Initialize the database
        schScheduleRepository.saveAndFlush(schSchedule.network(corNetwork).channel(corChannel));

        // Get all the schSchedules
        restSchScheduleMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/schedule?sort=id,desc", corNetwork.getShortcut(), corChannel.getShortcut()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(schSchedule.getId().intValue())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_PLAYLIST_DATE.toString())));
    }

    @Test
    @Transactional
    public void getAllSchSchedulesForDate() throws Exception {
        // Initialize the database
        schScheduleRepository.saveAndFlush(schSchedule.network(corNetwork).channel(corChannel));

        // Get all the schSchedules
        restSchScheduleMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/schedule/from/{from}/to/{to}", corNetwork.getShortcut(), corChannel.getShortcut(), schSchedule.getDate(), LocalDate.now().plusYears(1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(schSchedule.getId().intValue())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_PLAYLIST_DATE.toString())));
    }

    @Test
    @Transactional
    public void getSchSchedule() throws Exception {
        // Initialize the database
        schScheduleRepository.saveAndFlush(schSchedule.network(corNetwork).channel(corChannel));

        // Get the schSchedule
        restSchScheduleMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/schedule/{date}", corNetwork.getShortcut(), corChannel.getShortcut(), DEFAULT_PLAYLIST_DATE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(schSchedule.getId().intValue()))
                .andExpect(jsonPath("$.date").value(DEFAULT_PLAYLIST_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSchSchedule() throws Exception {
        // Get the schSchedule
        LocalDate localDate = LocalDate.now();
        restSchScheduleMockMvc.perform(get("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/schedule/{date}", corNetwork.getShortcut(), corChannel.getShortcut(), localDate))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchSchedule() throws Exception {
        // Initialize the database
        schScheduleRepository.saveAndFlush(schSchedule.network(corNetwork).channel(corChannel));
        int databaseSizeBeforeUpdate = schScheduleRepository.findAll().size();

        // Update the schSchedule
        SchSchedule updatedSchSchedule = schScheduleRepository.findOne(schSchedule.getId());
        updatedSchSchedule
                .date(UPDATED_PLAYLIST_DATE);
        SchScheduleDTO traPlaylistDTO = schScheduleMapper.DB2DTO(updatedSchSchedule);

        restSchScheduleMockMvc.perform(put("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/schedule", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traPlaylistDTO)))
                .andExpect(status().isOk());

        // Validate the SchSchedule in the database
        List<SchSchedule> schSchedules = schScheduleRepository.findAll();
        assertThat(schSchedules).hasSize(databaseSizeBeforeUpdate);
        SchSchedule testSchSchedule = schSchedules.get(schSchedules.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingSchSchedule() throws Exception {
        int databaseSizeBeforeUpdate = schScheduleRepository.findAll().size();

        // Create the SchSchedule
        SchScheduleDTO schScheduleDTO = schScheduleMapper.DB2DTO(schSchedule);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSchScheduleMockMvc.perform(put("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/schedule", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(schScheduleDTO)))
                .andExpect(status().isCreated());

        // Validate the SchSchedule in the database
        List<SchSchedule> schSchedules = schScheduleRepository.findAll();
        assertThat(schSchedules).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSchSchedule() throws Exception {
        // Initialize the database
        schScheduleRepository.saveAndFlush(schSchedule.network(corNetwork).channel(corChannel));
        int databaseSizeBeforeDelete = schScheduleRepository.findAll().size();

        // Get the schSchedule
        restSchScheduleMockMvc.perform(delete("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/schedule/{date}", corNetwork.getShortcut(), corChannel.getShortcut(), DEFAULT_PLAYLIST_DATE)
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SchSchedule> schSchedules = schScheduleRepository.findAll();
        assertThat(schSchedules).hasSize(databaseSizeBeforeDelete - 1);
    }


    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        schSchedule = createEntity(em).network(corNetwork).channel(corChannel);

        int databaseSizeBeforeTest = schScheduleRepository.findAll().size();
        // set the field null
        schSchedule.date(null);
        // Create the TraOrder, which fails.
        SchScheduleDTO schScheduleDTO = schScheduleMapper.DB2DTO(schSchedule);

        restSchScheduleMockMvc.perform(post("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/scheduler/schedule", corNetwork.getShortcut(), corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(schScheduleDTO)))
                .andExpect(status().isBadRequest());

        List<SchSchedule> traOrderList = schScheduleRepository.findAll();
        assertThat(traOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchSchedule.class);
    }
}
