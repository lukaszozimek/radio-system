package io.protone.application.service.traffic;


import com.google.common.collect.Sets;
import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorOrganization;
import io.protone.crm.domain.CrmAccount;
import io.protone.crm.repostiory.CrmAccountRepository;
import io.protone.library.domain.*;
import io.protone.library.repository.LibCloudObjectRepository;
import io.protone.library.repository.LibFileItemRepository;
import io.protone.library.repository.LibMediaItemRepository;
import io.protone.library.service.LibFileItemService;
import io.protone.traffic.domain.TraAdvertisement;
import io.protone.traffic.domain.TraMediaPlan;
import io.protone.traffic.domain.TraMediaPlanTemplate;
import io.protone.traffic.domain.TraOrder;
import io.protone.traffic.repository.TraAdvertisementRepository;
import io.protone.traffic.repository.TraMediaPlanRepository;
import io.protone.traffic.repository.TraOrderRepository;
import io.protone.traffic.service.TraMediaPlanService;
import io.protone.traffic.service.mediaplan.descriptor.TraMediaPlanDescriptor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;

import static io.protone.application.util.TestConstans.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by lukaszozimek on 10/06/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class TraMediaPlanServiceTest {

    @Autowired
    private TraMediaPlanService traMediaPlanService;

    @Autowired
    private TraMediaPlanRepository traMediaPlanRepository;

    @Autowired
    private TraAdvertisementRepository traAdvertisementRepository;

    @Autowired
    private CrmAccountRepository crmAccountRepository;

    @Autowired
    private LibFileItemRepository libFileItemRepository;

    @Autowired
    private LibMediaItemRepository libMediaItemRepository;

    @Autowired
    private TraOrderRepository traOrderRepository;

    @Autowired
    private LibCloudObjectRepository libCloudObjectRepository;

    @Mock
    private LibFileItemService libFileItemService;

    private CorChannel corChannel;

    private CorOrganization corOrganization;

    private PodamFactory factory;

    private CrmAccount crmAccount;

    private LibFileItem libFileItem;

    private LibFileLibrary libFileLibrary;

    private LibCloudObject libCloudObject;

    private LibMediaLibrary libMediaLibrary;

    private TraOrder traOrder;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        factory = new PodamFactoryImpl();

        corOrganization = new CorOrganization().shortcut(TEST_ORGANIZATION_SHORTCUT);
        corOrganization.setId(TEST_ORGANIZATION_ID);
        corChannel = new CorChannel().shortcut(TEST_CHANNEL_SHORTCUT);
        corChannel.setId(TEST_CHANNEL_ID);
        corChannel.setOrganization(corOrganization);
        libMediaLibrary = new LibMediaLibrary();
        libMediaLibrary.setId(2L);
        libMediaLibrary.setShortcut("com");

        libFileLibrary = new LibFileLibrary();
        libFileLibrary.setId(8L);
        libFileLibrary.setShortcut("mpl");

        crmAccount = factory.manufacturePojo(CrmAccount.class);
        crmAccount.channel(corChannel);
        crmAccount = crmAccountRepository.saveAndFlush(crmAccount);
        libCloudObject = factory.manufacturePojo(LibCloudObject.class);
        libCloudObject = libCloudObjectRepository.saveAndFlush(libCloudObject);

        libFileItem = factory.manufacturePojo(LibFileItem.class);
        libFileItem.library(libFileLibrary);
        libFileItem.setCloudObject(libCloudObject);
        libFileItem = libFileItemRepository.saveAndFlush(libFileItem);

        traOrder = factory.manufacturePojo(TraOrder.class);

        ReflectionTestUtils.setField(traMediaPlanService, "libFileItemService", libFileItemService);

    }

    @Test
    public void shouldGetMediaPlan() throws Exception {
        //when
        TraMediaPlan mediaPlan = factory.manufacturePojo(TraMediaPlan.class);
        mediaPlan.setChannel(corChannel);
        mediaPlan.fileItem(libFileItem);
        mediaPlan.account(crmAccount);
        mediaPlan = traMediaPlanRepository.save(mediaPlan);

        //then
        Slice<TraMediaPlan> fetchedEntity = traMediaPlanService.getMediaPlans(corOrganization.getShortcut(), corChannel.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(mediaPlan.getId(), fetchedEntity.getContent().get(0).getId());
        assertEquals(mediaPlan.getAccount(), fetchedEntity.getContent().get(0).getAccount());
        assertEquals(mediaPlan.getLibFileItem(), fetchedEntity.getContent().get(0).getLibFileItem());

    }

    @Test
    public void shouldSaveMediaPlan() throws Exception {

        //when
        MultipartFile multipartFile = new MockMultipartFile("test", "test", "", Thread.currentThread().getContextClassLoader().getResourceAsStream("mediaplan/SAMPLE_MEDIAPLAN_1.xls"));
        when(libFileItemService.uploadFileItem(anyString(), anyString(), anyString(), any(MultipartFile.class))).thenReturn(libFileItem);

        LibMediaItem libMediaItemToShuffle = factory.manufacturePojo(LibMediaItem.class);
        libMediaItemToShuffle.setLibrary(libMediaLibrary);
        libMediaItemToShuffle = libMediaItemRepository.saveAndFlush(libMediaItemToShuffle);

        TraAdvertisement advertisementToShuffle = factory.manufacturePojo(TraAdvertisement.class);
        advertisementToShuffle.setCustomer(crmAccount);
        advertisementToShuffle.setChannel(corChannel);
        advertisementToShuffle.setMediaItem(Sets.newHashSet(libMediaItemToShuffle));
        advertisementToShuffle = traAdvertisementRepository.saveAndFlush(advertisementToShuffle);
        traOrder.advertisment(advertisementToShuffle);
        traOrder.setChannel(corChannel);
        traOrder.setCustomer(crmAccount);
        traOrder = traOrderRepository.save(traOrder);

        TraMediaPlanDescriptor mediaPlanDescriptor = new TraMediaPlanDescriptor().order(traOrder).libMediaItem(libMediaItemToShuffle);
        TraMediaPlanTemplate traMediaPlanTemplate = new TraMediaPlanTemplate().sheetIndexOfMediaPlan(0)
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


        //then
        TraMediaPlan fetchedEntity = traMediaPlanService.saveMediaPlan(multipartFile, mediaPlanDescriptor, corChannel);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
        assertNotNull(fetchedEntity.getCreatedBy());
        assertNotNull(fetchedEntity.getName());
        assertEquals("test", fetchedEntity.getName());

    }

    @Test
    public void shouldDeleteMediaPlan() throws Exception {
        doNothing().when(libFileItemService).deleteFile(any(LibFileItem.class));
        //when
        TraMediaPlan mediaPlan = factory.manufacturePojo(TraMediaPlan.class);
        mediaPlan.fileItem(libFileItem);
        mediaPlan.account(crmAccount);
        mediaPlan.setChannel(corChannel);
        mediaPlan = traMediaPlanRepository.saveAndFlush(mediaPlan);
        //then
        traMediaPlanService.deleteMediaPlan(mediaPlan.getId(), corOrganization.getShortcut(), corChannel.getShortcut());
        TraMediaPlan fetchedEntity = traMediaPlanService.getMediaPlan(mediaPlan.getId(), corChannel.getShortcut(), corChannel.getShortcut());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void shouldGetPlaylist() throws Exception {
        //when
        TraMediaPlan traMediaPlan = factory.manufacturePojo(TraMediaPlan.class);
        traMediaPlan.fileItem(libFileItem);
        traMediaPlan.account(crmAccount);
        traMediaPlan.setChannel(corChannel);
        traMediaPlan = traMediaPlanRepository.save(traMediaPlan);

        //then
        TraMediaPlan fetchedEntity = traMediaPlanService.getMediaPlan(traMediaPlan.getId(), corOrganization.getShortcut(), corChannel.getShortcut());

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(traMediaPlan.getId(), fetchedEntity.getId());
        assertEquals(traMediaPlan.getLibFileItem(), fetchedEntity.getLibFileItem());

    }


}
