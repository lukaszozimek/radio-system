package io.protone.application.web.api.traffic;

import io.protone.application.ProtoneApp;
import io.protone.application.util.TestUtil;
import io.protone.application.web.api.cor.CorNetworkResourceIntTest;
import io.protone.application.web.api.traffic.impl.TraMediaPlanResourceImpl;
import io.protone.application.web.rest.errors.ExceptionTranslator;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorChannelService;
import io.protone.core.service.CorNetworkService;
import io.protone.crm.domain.CrmAccount;
import io.protone.crm.repostiory.CrmAccountRepository;
import io.protone.library.domain.LibFileItem;
import io.protone.library.domain.LibFileLibrary;
import io.protone.library.mapper.LibMediaItemMapper;
import io.protone.library.mapper.LibMediaItemThinMapper;
import io.protone.library.repository.LibFileItemRepository;
import io.protone.library.service.LibFileItemService;
import io.protone.traffic.api.dto.TraMediaPlanDTO;
import io.protone.traffic.api.dto.TraMediaPlanDescriptorDTO;
import io.protone.traffic.api.dto.TraMediaPlanTemplateDTO;
import io.protone.traffic.domain.TraAdvertisement;
import io.protone.traffic.domain.TraMediaPlan;
import io.protone.traffic.domain.TraOrder;
import io.protone.traffic.mapper.TraAdvertisementMapper;
import io.protone.traffic.mapper.TraMediaPlanDescriptorMapper;
import io.protone.traffic.mapper.TraMediaPlanMapper;
import io.protone.traffic.mapper.TraOrderMapper;
import io.protone.traffic.repository.TraAdvertisementRepository;
import io.protone.traffic.repository.TraMediaPlanRepository;
import io.protone.traffic.repository.TraOrderRepository;
import io.protone.traffic.service.TraMediaPlanBlockService;
import io.protone.traffic.service.TraMediaPlanEmissionService;
import io.protone.traffic.service.TraMediaPlanPlaylistDateService;
import io.protone.traffic.service.TraMediaPlanService;
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
import org.springframework.test.web.servlet.ResultActions;
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
    private TraMediaPlanBlockService traMediaPlanBlockService;

    @Inject
    private TraMediaPlanPlaylistDateService traMediaPlanPlaylistDateService;

    @Inject
    private TraMediaPlanEmissionService traMediaPlanEmissionService;


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
    private CrmAccountRepository crmAccountRepository;

    @Autowired
    private LibFileItemRepository libFileItemRepository;
    @Autowired
    private TraOrderRepository traOrderRepository;

    @Autowired
    private TraOrderMapper traOrderMapper;

    @Autowired
    private LibMediaItemMapper libMediaItemMapper;
    @Autowired
    private LibMediaItemThinMapper libMediaItemThinMapper;
    @Mock
    private LibFileItemService libFileItemService;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTraMediaPlanMockMvc;

    private TraMediaPlan traMediaPlan;
    private CorNetwork corNetwork;

    private CorChannel corChannel;

    private TraMediaPlanDescriptorDTO mediaPlanDescriptor;

    private LibFileLibrary libFileLibrary;

    private PodamFactory factory;

    private CrmAccount crmAccount;

    private LibFileItem libFileItem;

    private TraAdvertisement traAdvertisement;
    private TraOrder traOrder;

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
    public void setup() {
        MockitoAnnotations.initMocks(this);

        factory = new PodamFactoryImpl();

        TraMediaPlanResourceImpl traMediaPlanResource = new TraMediaPlanResourceImpl();
        libFileLibrary = new LibFileLibrary().shortcut("tes").network(corNetwork);
        libFileLibrary.setId(1L);

        crmAccount = factory.manufacturePojo(CrmAccount.class);
        crmAccount.network(corNetwork);
        crmAccount = crmAccountRepository.saveAndFlush(crmAccount);
        libFileItem = factory.manufacturePojo(LibFileItem.class);
        libFileItem.library(libFileLibrary);
        libFileItem.network(corNetwork);
        libFileItem = libFileItemRepository.saveAndFlush(libFileItem);

     //   traAdvertisement = TraAdvertisementResourceImplTest.createEntity(em).customer(crmAccount).network(corNetwork).mediaItem(Sets.newHashSet(libFileItem));
        traAdvertisement = traAdvertisementRepository.saveAndFlush(traAdvertisement);
        traOrder = factory.manufacturePojo(TraOrder.class);
        traOrder.setCustomer(crmAccount);
        traOrder.setAdvertisment(traAdvertisement);
        traOrder.setNetwork(corNetwork);
        traOrder = traOrderRepository.saveAndFlush(traOrder);
        TraMediaPlanTemplateDTO traMediaPlanTemplateDTO = new TraMediaPlanTemplateDTO();
        mediaPlanDescriptor = new TraMediaPlanDescriptorDTO()
                .order(traOrderMapper.DB2ThinDTO(traOrder));
        //.libMediaItemThinDTO(libMediaItemThinMapper.DB2DTO(libFileItem));
        traMediaPlanTemplateDTO.sheetIndexOfMediaPlan(0)
                .playlistDatePattern("dd-MMM-yyyy")
                .playlistDateStartColumn("G")
                .playlistDateEndColumn("CW")
                .playlistFirsValueCell("G8")
                .blockStartCell("A10")
                .blockEndCell("A47")
                .blockStartColumn("A")
                .blockHourSeparator("-")
                .firstEmissionValueCell("G10")
                .lastEmissionValueCell("CW47").name("test");
        mediaPlanDescriptor.setTraMediaPlanTemplateDTO(traMediaPlanTemplateDTO);


        ReflectionTestUtils.setField(traMediaPlanService, "libFileItemService", libFileItemService);
        ReflectionTestUtils.setField(traMediaPlanResource, "traMediaPlanBlockService", traMediaPlanBlockService);
        ReflectionTestUtils.setField(traMediaPlanResource, "traMediaPlanPlaylistDateService", traMediaPlanPlaylistDateService);
        ReflectionTestUtils.setField(traMediaPlanResource, "traMediaPlanEmissionService", traMediaPlanEmissionService);
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
        when(libFileItemService.uploadFileItem(anyString(), anyString(), any(MultipartFile.class))).thenReturn(libFileItem);

        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("mediaplan/SAMPLE_MEDIAPLAN_1.xls");
        // Create the TraMediaPlan
        MockMultipartFile firstFile = new MockMultipartFile("file", DEFAULT_NAME, "", inputStream);
        MockMultipartFile jsonFile = new MockMultipartFile("traMediaPlanDescriptorDTO", "",
                "application/json", TestUtil.convertObjectToJsonBytes(mediaPlanDescriptor));

        ResultActions resultActions = restTraMediaPlanMockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/mediaplan", corNetwork.getShortcut(), corChannel.getShortcut())
                .file(firstFile)
                .file(jsonFile))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

        // Validate the TraMediaPlan in the database
        List<TraMediaPlan> traMediaPlanList = traMediaPlanRepository.findAll();
        assertThat(traMediaPlanList).hasSize(databaseSizeBeforeCreate + 1);
        TraMediaPlan testTraMediaPlan = traMediaPlanList.get(traMediaPlanList.size() - 1);
        assertThat(testTraMediaPlan.getName()).isEqualTo(DEFAULT_NAME);
        inputStream.close();
    }


    @Test
    @Transactional
    public void getAllTraMediaPlans() throws Exception {
        // Initialize the database
        traMediaPlanRepository.saveAndFlush(traMediaPlan.fileItem(libFileItem).channel(corChannel).network(corNetwork).account(crmAccount));

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
        traMediaPlanRepository.saveAndFlush(traMediaPlan.fileItem(libFileItem).channel(corChannel).network(corNetwork).account(crmAccount));

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
        traMediaPlanRepository.saveAndFlush(traMediaPlan.fileItem(libFileItem).channel(corChannel).network(corNetwork).account(crmAccount));
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
        doNothing().when(libFileItemService).deleteFile(any(LibFileItem.class));
        traMediaPlanRepository.saveAndFlush(traMediaPlan.fileItem(libFileItem).channel(corChannel).network(corNetwork).account(crmAccount));
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
