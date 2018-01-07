package io.protone.application.web.api.traffic;

import com.google.common.collect.Sets;
import io.protone.application.ProtoneApp;
import io.protone.application.util.TestUtil;
import io.protone.application.web.api.traffic.impl.TraMediaPlanMappingResourceImpl;
import io.protone.application.web.rest.errors.ExceptionTranslator;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorOrganization;
import io.protone.core.service.CorChannelService;
import io.protone.core.service.CorNetworkService;
import io.protone.crm.domain.CrmAccount;
import io.protone.crm.repostiory.CrmAccountRepository;
import io.protone.library.domain.*;
import io.protone.library.domain.enumeration.LibItemTypeEnum;
import io.protone.library.repository.LibCloudObjectRepository;
import io.protone.library.repository.LibFileItemRepository;
import io.protone.library.repository.LibMediaItemRepository;
import io.protone.library.service.LibFileItemService;
import io.protone.library.service.LibMediaItemService;
import io.protone.traffic.api.dto.TraMediaPlanAdvertisementAssigneDTO;
import io.protone.traffic.domain.TraAdvertisement;
import io.protone.traffic.domain.TraMediaPlan;
import io.protone.traffic.domain.TraMediaPlanTemplate;
import io.protone.traffic.domain.TraOrder;
import io.protone.traffic.mapper.*;
import io.protone.traffic.repository.TraAdvertisementRepository;
import io.protone.traffic.repository.TraOrderRepository;
import io.protone.traffic.service.TraMediaPlanService;
import io.protone.traffic.service.mediaplan.TraPlaylistMediaPlanMappingService;
import io.protone.traffic.service.mediaplan.descriptor.TraMediaPlanDescriptor;
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

import static io.protone.application.util.TestConstans.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    private TraMediaPlanEmissionMapper traMediaPlanEmissionMapper;
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
    private LibMediaItemService libMediaItemService;
    @Mock
    private LibFileItemService libFileItemService;

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

    private CorOrganization corOrganization;

    private CorChannel corChannel;


    private LibMediaLibrary libMediaLibrary;

    private PodamFactory factory;

    private CrmAccount crmAccount;

    private LibMediaItem libMediaItem;

    private TraAdvertisement traAdvertisement;

    private TraOrder traOrder;
    private LibCloudObject libCloudObject;
    private LibFileLibrary libFileLibrary;
    @Autowired
    private LibCloudObjectRepository libCloudObjectRepository;
    private LibFileItem libFileItem;
    @Autowired
    private LibFileItemRepository libFileItemRepository;

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
        corOrganization = new CorOrganization().shortcut(TEST_ORGANIZATION_SHORTCUT);
        corOrganization.setId(TEST_ORGANIZATION_ID);
        corChannel = new CorChannel().shortcut(TEST_CHANNEL_SHORTCUT);
        corChannel.setId(TEST_CHANNEL_ID);
        corChannel.organization(corOrganization);

        TraMediaPlanMappingResourceImpl traMediaPlanMappingResource = new TraMediaPlanMappingResourceImpl();
        libMediaLibrary = new LibMediaLibrary().shortcut("tes").channel(corChannel);
        libMediaLibrary.setId(1L);
        libFileLibrary = new LibFileLibrary().shortcut("mpl").prefix("u");
        libFileLibrary.setId(8L);
        libCloudObject = factory.manufacturePojo(LibCloudObject.class);
        libCloudObject = libCloudObjectRepository.saveAndFlush(libCloudObject);

        libFileItem = factory.manufacturePojo(LibFileItem.class);
        libFileItem.library(libFileLibrary);
        libFileItem.setCloudObject(libCloudObject);
        libFileItem.channel(corChannel);
        libFileItem = libFileItemRepository.saveAndFlush(libFileItem);

        crmAccount = factory.manufacturePojo(CrmAccount.class);
        crmAccount.channel(corChannel);
        crmAccount = crmAccountRepository.saveAndFlush(crmAccount);
        libMediaItem = factory.manufacturePojo(LibMediaItem.class);
        libMediaItem.setItemType(LibItemTypeEnum.IT_DOCUMENT);
        libMediaItem.library(libMediaLibrary);
        libMediaItem.channel(corChannel);
        libMediaItem = libMediaItemRepository.saveAndFlush(libMediaItem);

        traAdvertisement = TraAdvertisementResourceImplTest.createEntity(em).customer(crmAccount).channel(corChannel).mediaItem(Sets.newHashSet(libMediaItem));
        traAdvertisement = traAdvertisementRepository.saveAndFlush(traAdvertisement);

        traOrder = factory.manufacturePojo(TraOrder.class);
        traOrder.setCustomer(crmAccount);
        traOrder.setAdvertisment(traAdvertisement);
        traOrder.setChannel(corChannel);
        traOrder = traOrderRepository.saveAndFlush(traOrder);

        ReflectionTestUtils.setField(traMediaPlanService, "libFileItemService", libFileItemService);
        ReflectionTestUtils.setField(traPlaylistMediaPlanMappingService, "libMediaItemService", libMediaItemService);

        ReflectionTestUtils.setField(traMediaPlanMappingResource, "traPlaylistMediaPlanMappingService", traPlaylistMediaPlanMappingService);
        ReflectionTestUtils.setField(traMediaPlanMappingResource, "traPlaylistMediaPlanMappingService", traPlaylistMediaPlanMappingService);
        ReflectionTestUtils.setField(traMediaPlanMappingResource, "traMediaPlanEmissionMapper", traMediaPlanEmissionMapper);
        ReflectionTestUtils.setField(traMediaPlanMappingResource, "traPlaylistMapper", traPlaylistMapper);

        this.restTraMediaPlanMappingMockMvc = MockMvcBuilders.standaloneSetup(traMediaPlanMappingResource)
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
        corChannel.setOrganization(corOrganization);
        traMediaPlan = createEntity(em);
        traMediaPlan.setChannel(corChannel);
    }

    @Test
    @Transactional
    public void shouldMapMediaPlanWithPlaylist() throws Exception {
        when(libMediaItemService.getMediaItem(libMediaItem.getChannel().getOrganization().getShortcut(), libMediaItem.getChannel().getShortcut(), "com", libMediaItem.getIdx())).thenReturn(libMediaItem);
        when(libFileItemService.uploadFileItem(anyString(), anyString(), anyString(), any(MultipartFile.class))).thenReturn(libFileItem);
        TraMediaPlanDescriptor mediaPlanDescriptor = new TraMediaPlanDescriptor().order(traOrder).libMediaItem(libMediaItem);
        ReflectionTestUtils.setField(traMediaPlanService, "libFileItemService", libFileItemService);
        TraMediaPlanTemplate traMediaPlanTemplate = new TraMediaPlanTemplate()
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
                .lastEmissionValueCell("CW47");
        mediaPlanDescriptor.setTraMediaPlanTemplate(traMediaPlanTemplate);


        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("mediaplan/SAMPLE_MEDIAPLAN_1.xls");
        // Create the TraMediaPlan
        MockMultipartFile firstFile = new MockMultipartFile("file", DEFAULT_NAME, "", inputStream);

        TraMediaPlan traMediaPlan = traMediaPlanService.saveMediaPlan(firstFile, mediaPlanDescriptor, corChannel);
        TraMediaPlanAdvertisementAssigneDTO traMediaPlanAdvertisementAssigneDTO = new TraMediaPlanAdvertisementAssigneDTO().mediaPlanId(traMediaPlan.getId()).libMediaItemIdx(libMediaItem.getIdx());
        restTraMediaPlanMappingMockMvc.perform(post("/api/v1/organization/{organizationShortcut}/channel/{channelShortcut}/traffic/playlist/assigne/mediaplan",
                corOrganization.getShortcut(),
                corChannel.getShortcut())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(traMediaPlanAdvertisementAssigneDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
        inputStream.close();
    }


}
