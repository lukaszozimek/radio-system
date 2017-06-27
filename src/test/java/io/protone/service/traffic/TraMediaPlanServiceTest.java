package io.protone.service.traffic;

import io.protone.ProtoneApp;
import io.protone.core.repository.cor.CorChannelRepository;
import io.protone.core.repository.cor.CorNetworkRepository;
import io.protone.domain.enumeration.LibItemTypeEnum;
import io.protone.repository.crm.CrmAccountRepository;
import io.protone.repository.library.LibLibraryRepository;
import io.protone.repository.library.LibMediaItemRepository;
import io.protone.repository.traffic.TraAdvertisementRepository;
import io.protone.repository.traffic.TraMediaPlanRepository;
import io.protone.repository.traffic.TraOrderRepository;
import io.protone.service.library.LibItemService;
import io.protone.traffic.service.mediaplan.descriptor.TraMediaPlanDescriptor;
import io.protone.web.rest.dto.traffic.thin.TraAdvertisementThinDTO;
import io.protone.web.rest.mapper.TraAdvertisementMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;
import java.util.List;

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
    private LibMediaItemRepository libMediaItemRepository;

    @Autowired
    private LibLibraryRepository libLibraryRepository;

    @Autowired
    private TraOrderRepository traOrderRepository;

    @Mock
    private LibItemService libItemService;

    private CorNetwork corNetwork;

    private CorChannel corChannel;

    private PodamFactory factory;

    private CrmAccount crmAccount;

    private LibMediaItem libMediaItem;

    private LibLibrary libLibrary;

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

        libLibrary = factory.manufacturePojo(LibLibrary.class);
        libLibrary.setShortcut("ppp");
        libLibrary.network(corNetwork);
        libLibrary.addChannel(corChannel);
        libLibrary = libLibraryRepository.saveAndFlush(libLibrary);


        crmAccount = factory.manufacturePojo(CrmAccount.class);
        crmAccount.network(corNetwork);
        crmAccount = crmAccountRepository.saveAndFlush(crmAccount);
        libMediaItem = factory.manufacturePojo(LibMediaItem.class);
        libMediaItem.setItemType(LibItemTypeEnum.IT_DOCUMENT);
        libMediaItem.library(libLibrary);
        libMediaItem.network(corNetwork);
        libMediaItem = libMediaItemRepository.saveAndFlush(libMediaItem);

        traOrder = factory.manufacturePojo(TraOrder.class);
        traOrder.network(corNetwork);

        ReflectionTestUtils.setField(traMediaPlanService, "libItemService", libItemService);

    }

    @Test
    public void shouldGetMediaPlan() throws Exception {
        //when
        TraMediaPlan mediaPlan = factory.manufacturePojo(TraMediaPlan.class);
        mediaPlan.setNetwork(corNetwork);
        mediaPlan.setChannel(corChannel);
        mediaPlan.mediaItem(libMediaItem);
        mediaPlan.account(crmAccount);
        mediaPlan = traMediaPlanRepository.save(mediaPlan);

        //then
        List<TraMediaPlan> fetchedEntity = traMediaPlanService.getMediaPlans(corNetwork.getShortcut(), corChannel.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(1, fetchedEntity.size());
        assertEquals(mediaPlan.getId(), fetchedEntity.get(0).getId());
        assertEquals(mediaPlan.getAccount(), fetchedEntity.get(0).getAccount());
        assertEquals(mediaPlan.getMediaItem(), fetchedEntity.get(0).getMediaItem());
        assertEquals(mediaPlan.getNetwork(), fetchedEntity.get(0).getNetwork());

    }

    @Test
    public void shouldSaveMediaPlan() throws Exception {

        //when
        MultipartFile multipartFile = new MockMultipartFile("test", "test", "", Thread.currentThread().getContextClassLoader().getResourceAsStream("mediaplan/SAMPLE_MEDIAPLAN_1.xls"));
        when(libItemService.upload(anyString(), anyString(), any(MultipartFile.class))).thenReturn(libMediaItem);

        LibMediaItem libMediaItemToShuffle = factory.manufacturePojo(LibMediaItem.class);
        libMediaItemToShuffle.setNetwork(corNetwork);
        libMediaItemToShuffle.setLibrary(libLibrary);
        libMediaItemToShuffle = libMediaItemRepository.saveAndFlush(libMediaItemToShuffle);

        TraAdvertisement advertisementToShuffle = factory.manufacturePojo(TraAdvertisement.class);
        advertisementToShuffle.setCustomer(crmAccount);
        advertisementToShuffle.setNetwork(corNetwork);
        advertisementToShuffle.setMediaItem(libMediaItemToShuffle);
        advertisementToShuffle = traAdvertisementRepository.saveAndFlush(advertisementToShuffle);
        traOrder.advertisment(advertisementToShuffle);
        traOrder.setNetwork(corNetwork);
        traOrder.setCustomer(crmAccount);
        traOrder = traOrderRepository.save(traOrder);

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

        //then
        TraMediaPlan fetchedEntity = traMediaPlanService.saveMediaPlan(multipartFile, mediaPlanDescriptor, corNetwork, corChannel);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
        assertNotNull(fetchedEntity.getCreatedBy());
        assertNotNull(fetchedEntity.getPlaylists().stream().findAny().get().getCreatedBy());
        assertNotNull(fetchedEntity.getName());
        assertEquals("test", fetchedEntity.getName());

    }

    @Test
    public void shouldDeleteMediaPlan() throws Exception {
        doNothing().when(libItemService).deleteItem(any(LibMediaItem.class));
        //when
        TraMediaPlan mediaPlan = factory.manufacturePojo(TraMediaPlan.class);
        mediaPlan.mediaItem(libMediaItem);
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
        traMediaPlan.mediaItem(libMediaItem);
        traMediaPlan.account(crmAccount);
        traMediaPlan.setNetwork(corNetwork);
        traMediaPlan.setChannel(corChannel);
        traMediaPlan = traMediaPlanRepository.save(traMediaPlan);

        //then
        TraMediaPlan fetchedEntity = traMediaPlanService.getMediaPlan(traMediaPlan.getId(), corNetwork.getShortcut(), corChannel.getShortcut());

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(traMediaPlan.getId(), fetchedEntity.getId());
        assertEquals(traMediaPlan.getMediaItem(), fetchedEntity.getMediaItem());
        assertEquals(traMediaPlan.getNetwork(), fetchedEntity.getNetwork());

    }


}
