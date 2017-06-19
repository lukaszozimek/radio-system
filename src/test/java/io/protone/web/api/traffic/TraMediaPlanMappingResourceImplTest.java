package io.protone.web.api.traffic;

import io.protone.ProtoneApp;
import io.protone.domain.*;
import io.protone.domain.enumeration.LibItemTypeEnum;
import io.protone.repository.crm.CrmAccountRepository;
import io.protone.repository.library.LibMediaItemRepository;
import io.protone.repository.traffic.TraAdvertisementRepository;
import io.protone.repository.traffic.TraOrderRepository;
import io.protone.service.cor.CorChannelService;
import io.protone.service.cor.CorNetworkService;
import io.protone.service.library.LibItemService;
import io.protone.service.traffic.TraMediaPlanService;
import io.protone.service.traffic.TraPlaylistMediaPlanMappingService;
import io.protone.service.traffic.mediaplan.descriptor.TraMediaPlanDescriptor;
import io.protone.web.api.cor.CorNetworkResourceIntTest;
import io.protone.web.api.traffic.impl.TraMediaPlanMappingResourceImpl;
import io.protone.web.rest.errors.ExceptionTranslator;
import io.protone.web.rest.mapper.TraAdvertisementMapper;
import io.protone.web.rest.mapper.TraMediaPlanDescriptorMapper;
import io.protone.web.rest.mapper.TraMediaPlanMapper;
import io.protone.web.rest.mapper.TraPlaylistMapper;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.InputStream;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by lukaszozimek on 11/06/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TraMediaPlanMappingResourceImplTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";

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
    private CrmAccountRepository crmAccountRepository;

    @Autowired
    private LibMediaItemRepository libMediaItemRepository;

    @Mock
    private LibItemService libItemService;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private TraPlaylistMediaPlanMappingService traPlaylistMediaPlanMappingService;

    @Autowired
    private EntityManager em;

    @Autowired
    private TraPlaylistMapper traPlaylistMapper;

    @Autowired
    private TraOrderRepository traOrderRepository;

    private MockMvc restTraMediaPlanMappingMockMvc;

    private TraMediaPlan traMediaPlan;

    private CorNetwork corNetwork;

    private CorChannel corChannel;

    private LibLibrary libLibrary;

    private PodamFactory factory;

    private CrmAccount crmAccount;

    private LibMediaItem libMediaItem;

    private TraAdvertisement traAdvertisement;

    private TraOrder traOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        factory = new PodamFactoryImpl();

        TraMediaPlanMappingResourceImpl traMediaPlanMappingResource = new TraMediaPlanMappingResourceImpl();
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

        traOrder = factory.manufacturePojo(TraOrder.class);
        traOrder.setCustomer(crmAccount);
        traOrder.setAdvertisment(traAdvertisement);
        traOrder.setNetwork(corNetwork);
        traOrder = traOrderRepository.saveAndFlush(traOrder);

        ReflectionTestUtils.setField(traMediaPlanService, "libItemService", libItemService);
        ReflectionTestUtils.setField(traMediaPlanMappingResource, "traPlaylistMediaPlanMappingService", traPlaylistMediaPlanMappingService);
        ReflectionTestUtils.setField(traMediaPlanMappingResource, "traPlaylistMediaPlanMappingService", traPlaylistMediaPlanMappingService);
        ReflectionTestUtils.setField(traMediaPlanMappingResource, "traPlaylistMapper", traPlaylistMapper);

        this.restTraMediaPlanMappingMockMvc = MockMvcBuilders.standaloneSetup(traMediaPlanMappingResource)
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
    public void shouldMapMediaPlanWithPlaylist() throws Exception {
        when(libItemService.upload(anyString(), anyString(), any(MultipartFile.class))).thenReturn(libMediaItem);
        TraMediaPlanDescriptor mediaPlanDescriptor = new TraMediaPlanDescriptor()
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
            .order(traOrder);

        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("mediaplan/SAMPLE_MEDIAPLAN_1.xls");
        // Create the TraMediaPlan
        MockMultipartFile firstFile = new MockMultipartFile("file", DEFAULT_NAME, "", inputStream);

        TraMediaPlan traMediaPlan = traMediaPlanService.saveMediaPlan(firstFile, mediaPlanDescriptor, corNetwork, corChannel);

        restTraMediaPlanMappingMockMvc.perform(get("/api/v1/network/{networkShortcut}/channel/{channelShortcut}/traffic/playlist/assigne/mediaplan/{mediaPlanId}/advertisement/{advertisementId}",
            corNetwork.getShortcut(),
            corChannel.getShortcut(),
            traMediaPlan.getId(), traAdvertisement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

    }


}
