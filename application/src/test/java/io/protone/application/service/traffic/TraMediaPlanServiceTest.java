package io.protone.application.service.traffic;


import com.google.common.collect.Sets;
import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.repository.CorChannelRepository;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.crm.domain.CrmAccount;
import io.protone.crm.repostiory.CrmAccountRepository;
import io.protone.library.domain.LibFileItem;
import io.protone.library.domain.LibFileLibrary;
import io.protone.library.domain.LibMediaItem;
import io.protone.library.domain.LibMediaLibrary;
import io.protone.library.repository.LibFileItemRepository;
import io.protone.library.repository.LibFileLibraryRepository;
import io.protone.library.repository.LibMediaItemRepository;
import io.protone.library.service.LibFileItemService;
import io.protone.traffic.api.dto.thin.TraAdvertisementThinDTO;
import io.protone.traffic.domain.TraAdvertisement;
import io.protone.traffic.domain.TraMediaPlan;
import io.protone.traffic.domain.TraMediaPlanTemplate;
import io.protone.traffic.domain.TraOrder;
import io.protone.traffic.mapper.TraAdvertisementMapper;
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
    private TraAdvertisementMapper traAdvertisementMapper;

    @Autowired
    private CorNetworkRepository corNetworkRepository;


    @Autowired
    private CorChannelRepository corChannelRepository;

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
    private LibFileLibraryRepository libFileLibraryRepository;

    @Autowired
    private TraOrderRepository traOrderRepository;

    @Mock
    private LibFileItemService libFileItemService;

    private CorNetwork corNetwork;

    private CorChannel corChannel;

    private PodamFactory factory;

    private CrmAccount crmAccount;

    private LibFileItem libFileItem;

    private LibFileLibrary libFileLibrary;

    private LibMediaLibrary libMediaLibrary;

    private LibMediaItem libMediaItem;

    private TraOrder traOrder;

    private TraAdvertisementThinDTO traAdvertisementThinDTO;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        factory = new PodamFactoryImpl();
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corNetwork.setId(null);
        corNetwork = corNetworkRepository.saveAndFlush(corNetwork);

        corChannel = factory.manufacturePojo(CorChannel.class);
        corChannel.setId(null);
        corChannel.setShortcut("HHH");
        corChannel.network(corNetwork);
        corChannelRepository.saveAndFlush(corChannel);

        libFileLibrary = factory.manufacturePojo(LibFileLibrary.class);
        libFileLibrary.setShortcut("ppp");
        libFileLibrary.network(corNetwork);
        libFileLibrary.addChannel(corChannel);
        libFileLibrary = libFileLibraryRepository.saveAndFlush(libFileLibrary);


        crmAccount = factory.manufacturePojo(CrmAccount.class);
        crmAccount.network(corNetwork);
        crmAccount = crmAccountRepository.saveAndFlush(crmAccount);
        libFileItem = factory.manufacturePojo(LibFileItem.class);
        libFileItem.library(libFileLibrary);
        libFileItem.network(corNetwork);
        libFileItem = libFileItemRepository.saveAndFlush(libFileItem);

        traOrder = factory.manufacturePojo(TraOrder.class);
        traOrder.network(corNetwork);

        ReflectionTestUtils.setField(traMediaPlanService, "libFileItemService", libFileItemService);

    }

    @Test
    public void shouldGetMediaPlan() throws Exception {
        //when
        TraMediaPlan mediaPlan = factory.manufacturePojo(TraMediaPlan.class);
        mediaPlan.setNetwork(corNetwork);
        mediaPlan.setChannel(corChannel);
        mediaPlan.fileItem(libFileItem);
        mediaPlan.account(crmAccount);
        mediaPlan = traMediaPlanRepository.save(mediaPlan);

        //then
        Slice<TraMediaPlan> fetchedEntity = traMediaPlanService.getMediaPlans(corNetwork.getShortcut(), corChannel.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(mediaPlan.getId(), fetchedEntity.getContent().get(0).getId());
        assertEquals(mediaPlan.getAccount(), fetchedEntity.getContent().get(0).getAccount());
        assertEquals(mediaPlan.getLibFileItem(), fetchedEntity.getContent().get(0).getLibFileItem());
        assertEquals(mediaPlan.getNetwork(), fetchedEntity.getContent().get(0).getNetwork());

    }

    @Test
    public void shouldSaveMediaPlan() throws Exception {

        //when
        MultipartFile multipartFile = new MockMultipartFile("test", "test", "", Thread.currentThread().getContextClassLoader().getResourceAsStream("mediaplan/SAMPLE_MEDIAPLAN_1.xls"));
        when(libFileItemService.uploadFileItem(anyString(), anyString(), any(MultipartFile.class))).thenReturn(libFileItem);

        LibMediaItem libMediaItemToShuffle = factory.manufacturePojo(LibMediaItem.class);
        libMediaItemToShuffle.setNetwork(corNetwork);
        libMediaItemToShuffle.setLibrary(libMediaLibrary);
        libMediaItemToShuffle = libMediaItemRepository.saveAndFlush(libMediaItemToShuffle);

        TraAdvertisement advertisementToShuffle = factory.manufacturePojo(TraAdvertisement.class);
        advertisementToShuffle.setCustomer(crmAccount);
        advertisementToShuffle.setNetwork(corNetwork);
        advertisementToShuffle.setMediaItem(Sets.newHashSet(libMediaItemToShuffle));
        advertisementToShuffle = traAdvertisementRepository.saveAndFlush(advertisementToShuffle);
        traOrder.advertisment(advertisementToShuffle);
        traOrder.setNetwork(corNetwork);
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
        TraMediaPlan fetchedEntity = traMediaPlanService.saveMediaPlan(multipartFile, mediaPlanDescriptor, corNetwork, corChannel);

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
        mediaPlan.setNetwork(corNetwork);
        mediaPlan.setChannel(corChannel);
        mediaPlan = traMediaPlanRepository.saveAndFlush(mediaPlan);
        //then
        traMediaPlanService.deleteMediaPlan(mediaPlan.getId(), corNetwork.getShortcut(), corChannel.getShortcut());
        TraMediaPlan fetchedEntity = traMediaPlanService.getMediaPlan(mediaPlan.getId(), corNetwork.getShortcut(), corChannel.getShortcut());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void shouldGetPlaylist() throws Exception {
        //when
        TraMediaPlan traMediaPlan = factory.manufacturePojo(TraMediaPlan.class);
        traMediaPlan.fileItem(libFileItem);
        traMediaPlan.account(crmAccount);
        traMediaPlan.setNetwork(corNetwork);
        traMediaPlan.setChannel(corChannel);
        traMediaPlan = traMediaPlanRepository.save(traMediaPlan);

        //then
        TraMediaPlan fetchedEntity = traMediaPlanService.getMediaPlan(traMediaPlan.getId(), corNetwork.getShortcut(), corChannel.getShortcut());

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(traMediaPlan.getId(), fetchedEntity.getId());
        assertEquals(traMediaPlan.getLibFileItem(), fetchedEntity.getLibFileItem());
        assertEquals(traMediaPlan.getNetwork(), fetchedEntity.getNetwork());

    }


}
