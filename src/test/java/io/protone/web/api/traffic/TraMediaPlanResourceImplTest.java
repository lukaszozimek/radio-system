package io.protone.web.api.traffic;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.protone.ProtoneApp;
import io.protone.domain.*;
import io.protone.domain.enumeration.LibItemTypeEnum;
import io.protone.repository.crm.CrmAccountRepository;
import io.protone.repository.library.LibLibraryRepository;
import io.protone.repository.library.LibMediaItemRepository;
import io.protone.repository.traffic.TraAdvertisementRepository;
import io.protone.repository.traffic.TraMediaPlanRepository;
import io.protone.service.cor.CorChannelService;
import io.protone.service.cor.CorNetworkService;
import io.protone.service.library.LibItemService;
import io.protone.service.traffic.TraMediaPlanService;
import io.protone.util.TestUtil;
import io.protone.web.api.cor.CorNetworkResourceIntTest;
import io.protone.web.api.traffic.impl.TraMediaPlanResourceImpl;
import io.protone.web.rest.dto.traffic.TraMediaPlanDTO;
import io.protone.web.rest.dto.traffic.TraMediaPlanDescriptorDTO;
import io.protone.web.rest.errors.ExceptionTranslator;
import io.protone.web.rest.mapper.TraAdvertisementMapper;
import io.protone.web.rest.mapper.TraMediaPlanDescriptorMapper;
import io.protone.web.rest.mapper.TraMediaPlanMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by lukaszozimek on 11/06/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TraMediaPlanResourceImplTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private TraMediaPlanRepository traMediaPlanRepository;

    @Inject
    private TraMediaPlanService traMediaPlanService;

    @Inject
    private TraMediaPlanMapper traMediaPlanMapper;

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CorChannelService corChannelService;

    @Inject
    private TraMediaPlanDescriptorMapper traMediaPlanDescriptorMapper;

    @Inject
    private TraAdvertisementMapper traAdvertisementMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private TraAdvertisementRepository traAdvertisementRepository;

    @Autowired
    private LibLibraryRepository libLibraryRepository;

    @Autowired
    private CrmAccountRepository crmAccountRepository;

    @Autowired
    private LibMediaItemRepository libMediaItemRepository;

    @Mock
    private LibItemService libItemService;
    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTraMediaPlanMockMvc;

    private TraMediaPlan traMediaPlan;
    private CorNetwork corNetwork;

    private CorChannel corChannel;

    private TraMediaPlanDescriptorDTO mediaPlanDescriptor;

    private LibLibrary libLibrary;

    private PodamFactory factory;

    private CrmAccount crmAccount;

    private LibMediaItem libMediaItem;

    private TraAdvertisement traAdvertisement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        factory = new PodamFactoryImpl();

        TraMediaPlanResourceImpl traMediaPlanResource = new TraMediaPlanResourceImpl();
        libLibrary = new LibLibrary().shortcut("tes").network(corNetwork);
        libLibrary.setId(1L);

        crmAccount = factory.manufacturePojo(CrmAccount.class);
        crmAccount.network(corNetwork);
        crmAccount = crmAccountRepository.saveAndFlush(crmAccount);
        libMediaItem = factory.manufacturePojo(LibMediaItem.class);
        libMediaItem.setItemType(LibItemTypeEnum.IT_DOCUMENT);
        libMediaItem.library(libLibrary);
        libMediaItem.network(corNetwork);
        libMediaItem = libMediaItemRepository.saveAndFlush(libMediaItem);

        traAdvertisement = TraAdvertisementResourceImplTest.createEntity(em).customer(crmAccount).network(corNetwork).mediaItem(libMediaItem);
        traAdvertisement = traAdvertisementRepository.saveAndFlush(traAdvertisement);

        mediaPlanDescriptor = new TraMediaPlanDescriptorDTO()
            .sheetIndexOfMediaPlan(0)
            .playlistDatePattern("dd-MMM-yyyy")
            .playlistDateStartColumn("G")
            .playlistDateEndColumn("CW")
            .playlistFirsValueCell("G8")
            .blockStartCell("A10")
            .blockEndCell("A47")
            .blockStartColumn("A")
            .blockHourSeparator("-")
            .firstEmissionValueCell("G10")
            .lastEmissionValueCell("CW47")
            .traAdvertisment(traAdvertisementMapper.traAdvertisementThinPTFromTraAdvertisement(traAdvertisement));

        ReflectionTestUtils.setField(traMediaPlanService, "libItemService", libItemService);
        ReflectionTestUtils.setField(traMediaPlanResource, "traMediaPlanService", traMediaPlanService);
        ReflectionTestUtils.setField(traMediaPlanResource, "traMediaPlanMapper", traMediaPlanMapper);
        ReflectionTestUtils.setField(traMediaPlanResource, "corNetworkService", corNetworkService);
        ReflectionTestUtils.setField(traMediaPlanResource, "corChannelService", corChannelService);
        ReflectionTestUtils.setField(traMediaPlanResource, "traMediaPlanDescriptorMapper", traMediaPlanDescriptorMapper);
        this.restTraMediaPlanMockMvc = MockMvcBuilders.standaloneSetup(traMediaPlanResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TraMediaPlan createEntity(EntityManager em) {
        TraMediaPlan traMediaPlan = new TraMediaPlan()
            .name(DEFAULT_NAME);
        return traMediaPlan;
    }

    @Before
    public void initTest() {
        corNetwork = new CorNetwork().shortcut(CorNetworkResourceIntTest.TEST_NETWORK);
        corNetwork.setId(1L);
        corChannel = new CorChannel().shortcut("tes");
        corChannel.setId(1L);
        traMediaPlan = createEntity(em);
        traMediaPlan.setChannel(corChannel);
        traMediaPlan.setNetwork(corNetwork);
    }

    @Test
    @Transactional
    public void createTraMediaPlan() throws Exception {
        int databaseSizeBeforeCreate = traMediaPlanRepository.findAll().size();
        when(libItemService.upload(anyString(), anyString(), any(MultipartFile.class))).thenReturn(libMediaItem);

        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("mediaplan/SAMPLE_MEDIAPLAN_1.xls");
        // Create the TraMediaPlan
        MockMultipartFile firstFile = new MockMultipartFile("file", DEFAULT_NAME, "", inputStream);
        ObjectMapper objectMapper = new ObjectMapper();

        restTraMediaPlanMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/mediaplan", corNetwork.getShortcut(), corChannel.getShortcut())
            .file(firstFile).param("traMediaPlanDescriptorDTO", objectMapper.writeValueAsString(mediaPlanDescriptor)))
            .andExpect(status().is(200))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

        // Validate the TraMediaPlan in the database
        List<TraMediaPlan> traMediaPlanList = traMediaPlanRepository.findAll();
        assertThat(traMediaPlanList).hasSize(databaseSizeBeforeCreate + 1);
        TraMediaPlan testTraMediaPlan = traMediaPlanList.get(traMediaPlanList.size() - 1);
        assertThat(testTraMediaPlan.getName()).isEqualTo(DEFAULT_NAME);
    }


    @Test
    @Transactional
    public void getAllTraMediaPlans() throws Exception {
        // Initialize the database
        traMediaPlanRepository.saveAndFlush(traMediaPlan.mediaItem(libMediaItem).channel(corChannel).network(corNetwork).account(crmAccount));

        // Get all the traMediaPlanList
        restTraMediaPlanMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/mediaplan?sort=id,desc", corNetwork.getShortcut(), corChannel.getShortcut()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(traMediaPlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getTraMediaPlan() throws Exception {
        // Initialize the database
        traMediaPlanRepository.saveAndFlush(traMediaPlan.mediaItem(libMediaItem).channel(corChannel).network(corNetwork).account(crmAccount));

        // Get the traMediaPlan
        restTraMediaPlanMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/mediaplan/{id}", corNetwork.getShortcut(), corChannel.getShortcut(), traMediaPlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(traMediaPlan.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTraMediaPlan() throws Exception {
        // Get the traMediaPlan
        restTraMediaPlanMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/mediaplan/{id}", corNetwork.getShortcut(), corChannel.getShortcut(), Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTraMediaPlan() throws Exception {
        // Initialize the database
        traMediaPlanRepository.saveAndFlush(traMediaPlan.mediaItem(libMediaItem).channel(corChannel).network(corNetwork).account(crmAccount));
        int databaseSizeBeforeUpdate = traMediaPlanRepository.findAll().size();

        // Update the traMediaPlan
        TraMediaPlan updatedTraMediaPlan = traMediaPlanRepository.findOne(traMediaPlan.getId());
        updatedTraMediaPlan
            .name(UPDATED_NAME);
        TraMediaPlanDTO traMediaPlanDTO = traMediaPlanMapper.DB2DTO(updatedTraMediaPlan);

        restTraMediaPlanMockMvc.perform(put("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/mediaplan", corNetwork.getShortcut(), corChannel.getShortcut())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traMediaPlanDTO)))
            .andExpect(status().isOk());

        // Validate the TraMediaPlan in the database
        List<TraMediaPlan> traMediaPlanList = traMediaPlanRepository.findAll();
        assertThat(traMediaPlanList).hasSize(databaseSizeBeforeUpdate);
        TraMediaPlan testTraMediaPlan = traMediaPlanList.get(traMediaPlanList.size() - 1);
        assertThat(testTraMediaPlan.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteTraMediaPlan() throws Exception {
        // Initialize the database
        doNothing().when(libItemService).deleteItem(any(LibMediaItem.class));
        traMediaPlanRepository.saveAndFlush(traMediaPlan.mediaItem(libMediaItem).channel(corChannel).network(corNetwork).account(crmAccount));
        int databaseSizeBeforeDelete = traMediaPlanRepository.findAll().size();

        // Get the traMediaPlan
        restTraMediaPlanMockMvc.perform(delete("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/mediaplan/{id}", corNetwork.getShortcut(), corChannel.getShortcut(), traMediaPlan.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TraMediaPlan> traMediaPlanList = traMediaPlanRepository.findAll();
        assertThat(traMediaPlanList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TraMediaPlan.class);
    }
}
