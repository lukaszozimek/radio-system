package io.protone.web.api.traffic;

import io.protone.ProtoneApp;
import io.protone.domain.CorChannel;
import io.protone.domain.CorNetwork;
import io.protone.domain.TraBlockConfiguration;
import io.protone.domain.enumeration.CorDayOfWeekEnum;
import io.protone.repository.traffic.TraBlockConfigurationRepository;
import io.protone.service.cor.CorChannelService;
import io.protone.service.cor.CorNetworkService;
import io.protone.service.traffic.TraBlockConfigurationService;
import io.protone.util.TestUtil;
import io.protone.web.api.cor.CorNetworkResourceIntTest;
import io.protone.web.api.traffic.impl.TraBlockConfigurationResourceImpl;
import io.protone.web.rest.dto.traffic.TraBlockConfigurationDTO;
import io.protone.web.rest.errors.ExceptionTranslator;
import io.protone.web.rest.mapper.TraBlockConfigurationMapper;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by lukaszozimek on 15/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TraBlockConfigurationResourceImplTest {

    private static final CorDayOfWeekEnum DEFAULT_DAY = CorDayOfWeekEnum.DW_MONDAY;
    private static final CorDayOfWeekEnum UPDATED_DAY = CorDayOfWeekEnum.DW_TUESDAY;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_LENGTH = 1L;
    private static final Long UPDATED_LENGTH = 2L;

    private static final Long DEFAULT_START_BLOCK = 1L;
    private static final Long UPDATED_START_BLOCK = 2L;

    private static final Long DEFAULT_STOP_BLOCK = 1L;
    private static final Long UPDATED_STOP_BLOCK = 2L;

    @Autowired
    private CorNetworkService corNetworkService;

    @Autowired
    private CorChannelService corChannelService;
    @Autowired
    private TraBlockConfigurationService traBlockConfigurationService;

    @Autowired
    private TraBlockConfigurationRepository traBlockConfigurationRepository;

    @Autowired
    private TraBlockConfigurationMapper traBlockConfigurationMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTraBlockConfigurationMockMvc;

    private TraBlockConfiguration traBlockConfiguration;
    private CorNetwork corNetwork;
    private CorChannel corChannel;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TraBlockConfiguration createEntity(EntityManager em) {
        TraBlockConfiguration traBlockConfiguration = new TraBlockConfiguration()
            .day(DEFAULT_DAY)
            .name(DEFAULT_NAME)
            .length(DEFAULT_LENGTH)
            .startBlock(DEFAULT_START_BLOCK)
            .stopBlock(DEFAULT_STOP_BLOCK);
        return traBlockConfiguration;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TraBlockConfigurationResourceImpl traBlockConfigurationResource = new TraBlockConfigurationResourceImpl();
        ReflectionTestUtils.setField(traBlockConfigurationResource, "traBlockConfigurationService", traBlockConfigurationService);
        ReflectionTestUtils.setField(traBlockConfigurationResource, "traBlockConfigurationMapper", traBlockConfigurationMapper);
        ReflectionTestUtils.setField(traBlockConfigurationResource, "corNetworkService", corNetworkService);
        ReflectionTestUtils.setField(traBlockConfigurationResource, "corChannelService", corChannelService);
        corNetwork = new CorNetwork().shortcut(CorNetworkResourceIntTest.TEST_NETWORK);
        corNetwork.setId(1L);

        corChannel = new CorChannel().shortcut("tes");
        corChannel.setId(1L);
        this.restTraBlockConfigurationMockMvc = MockMvcBuilders.standaloneSetup(traBlockConfigurationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        traBlockConfiguration = createEntity(em).network(corNetwork).channel(corChannel);
    }

    @Test
    @Transactional
    public void createTraBlockConfiguration() throws Exception {
        int databaseSizeBeforeCreate = traBlockConfigurationRepository.findAll().size();

        // Create the TraBlockConfiguration
        TraBlockConfigurationDTO traBlockConfigurationDTO = traBlockConfigurationMapper.DB2DTO(traBlockConfiguration);

        restTraBlockConfigurationMockMvc.perform(post("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/block", corNetwork.getShortcut(), corChannel.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traBlockConfigurationDTO)))
            .andExpect(status().isCreated());

        // Validate the TraBlockConfiguration in the database
        List<TraBlockConfiguration> traBlockConfigurationList = traBlockConfigurationRepository.findAll();
        assertThat(traBlockConfigurationList).hasSize(databaseSizeBeforeCreate + 1);
        TraBlockConfiguration testTraBlockConfiguration = traBlockConfigurationList.get(traBlockConfigurationList.size() - 1);
        assertThat(testTraBlockConfiguration.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testTraBlockConfiguration.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTraBlockConfiguration.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testTraBlockConfiguration.getStartBlock()).isEqualTo(DEFAULT_START_BLOCK);
        assertThat(testTraBlockConfiguration.getStopBlock()).isEqualTo(DEFAULT_STOP_BLOCK);
    }

    @Test
    @Transactional
    public void createTraBlockConfigurationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = traBlockConfigurationRepository.findAll().size();

        // Create the TraBlockConfiguration with an existing ID
        TraBlockConfiguration existingTraBlockConfiguration = new TraBlockConfiguration();
        existingTraBlockConfiguration.setId(1L);
        TraBlockConfigurationDTO existingTraBlockConfigurationDTO = traBlockConfigurationMapper.DB2DTO(existingTraBlockConfiguration);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTraBlockConfigurationMockMvc.perform(post("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/block", corNetwork.getShortcut(), corChannel.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTraBlockConfigurationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TraBlockConfiguration> traBlockConfigurationList = traBlockConfigurationRepository.findAll();
        assertThat(traBlockConfigurationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTraBlockConfigurations() throws Exception {
        // Initialize the database
        traBlockConfigurationRepository.saveAndFlush(traBlockConfiguration.network(corNetwork).channel(corChannel));

        // Get all the traBlockConfigurationList
        restTraBlockConfigurationMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/block?sort=id,desc", corNetwork.getShortcut(), corChannel.getShortcut()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(traBlockConfiguration.getId().intValue())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH.intValue())))
            .andExpect(jsonPath("$.[*].startBlock").value(hasItem(DEFAULT_START_BLOCK.intValue())))
            .andExpect(jsonPath("$.[*].stopBlock").value(hasItem(DEFAULT_STOP_BLOCK.intValue())));
    }

    @Test
    @Transactional
    public void getTraBlockConfiguration() throws Exception {
        // Initialize the database
        traBlockConfigurationRepository.saveAndFlush(traBlockConfiguration.network(corNetwork).channel(corChannel));

        // Get the traBlockConfiguration
        restTraBlockConfigurationMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/block/{id}", corNetwork.getShortcut(), corChannel.getShortcut(), traBlockConfiguration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(traBlockConfiguration.getId().intValue()))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH.intValue()))
            .andExpect(jsonPath("$.startBlock").value(DEFAULT_START_BLOCK.intValue()))
            .andExpect(jsonPath("$.stopBlock").value(DEFAULT_STOP_BLOCK.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTraBlockConfiguration() throws Exception {
        // Get the traBlockConfiguration
        restTraBlockConfigurationMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/block/{id}", corNetwork.getShortcut(), corChannel.getShortcut(), Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTraBlockConfiguration() throws Exception {
        // Initialize the database
        traBlockConfigurationRepository.saveAndFlush(traBlockConfiguration.network(corNetwork).channel(corChannel));
        int databaseSizeBeforeUpdate = traBlockConfigurationRepository.findAll().size();

        // Update the traBlockConfiguration
        TraBlockConfiguration updatedTraBlockConfiguration = traBlockConfigurationRepository.findOne(traBlockConfiguration.getId());
        updatedTraBlockConfiguration
            .day(UPDATED_DAY)
            .name(UPDATED_NAME)
            .length(UPDATED_LENGTH)
            .startBlock(UPDATED_START_BLOCK)
            .stopBlock(UPDATED_STOP_BLOCK);
        TraBlockConfigurationDTO traBlockConfigurationDTO = traBlockConfigurationMapper.DB2DTO(updatedTraBlockConfiguration);

        restTraBlockConfigurationMockMvc.perform(put("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/block", corNetwork.getShortcut(), corChannel.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traBlockConfigurationDTO)))
            .andExpect(status().isOk());

        // Validate the TraBlockConfiguration in the database
        List<TraBlockConfiguration> traBlockConfigurationList = traBlockConfigurationRepository.findAll();
        assertThat(traBlockConfigurationList).hasSize(databaseSizeBeforeUpdate);
        TraBlockConfiguration testTraBlockConfiguration = traBlockConfigurationList.get(traBlockConfigurationList.size() - 1);
        assertThat(testTraBlockConfiguration.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testTraBlockConfiguration.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTraBlockConfiguration.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testTraBlockConfiguration.getStartBlock()).isEqualTo(UPDATED_START_BLOCK);
        assertThat(testTraBlockConfiguration.getStopBlock()).isEqualTo(UPDATED_STOP_BLOCK);
    }

    @Test
    @Transactional
    public void updateNonExistingTraBlockConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = traBlockConfigurationRepository.findAll().size();

        // Create the TraBlockConfiguration
        TraBlockConfigurationDTO traBlockConfigurationDTO = traBlockConfigurationMapper.DB2DTO(traBlockConfiguration);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTraBlockConfigurationMockMvc.perform(put("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/block", corNetwork.getShortcut(), corChannel.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traBlockConfigurationDTO)))
            .andExpect(status().isCreated());

        // Validate the TraBlockConfiguration in the database
        List<TraBlockConfiguration> traBlockConfigurationList = traBlockConfigurationRepository.findAll();
        assertThat(traBlockConfigurationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTraBlockConfiguration() throws Exception {
        // Initialize the database
        traBlockConfigurationRepository.saveAndFlush(traBlockConfiguration.network(corNetwork).channel(corChannel));
        int databaseSizeBeforeDelete = traBlockConfigurationRepository.findAll().size();

        // Get the traBlockConfiguration
        restTraBlockConfigurationMockMvc.perform(delete("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/block/{id}", corNetwork.getShortcut(), corChannel.getShortcut(), traBlockConfiguration.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TraBlockConfiguration> traBlockConfigurationList = traBlockConfigurationRepository.findAll();
        assertThat(traBlockConfigurationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TraBlockConfiguration.class);
    }
}
