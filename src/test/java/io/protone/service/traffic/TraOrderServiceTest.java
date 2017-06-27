package io.protone.service.traffic;

import io.protone.ProtoneApp;
import io.protone.core.repository.cor.CorChannelRepository;
import io.protone.core.repository.cor.CorNetworkRepository;
import io.protone.repository.crm.CrmAccountRepository;
import io.protone.repository.library.LibLibraryRepository;
import io.protone.repository.library.LibMediaItemRepository;
import io.protone.repository.traffic.TraAdvertisementRepository;
import io.protone.repository.traffic.TraOrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 29/04/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class TraOrderServiceTest {
    private static final String TEST_NAME = "TEST";
    @Autowired
    private TraOrderService traOrderService;

    @Autowired
    private TraOrderRepository customTraOrderRepository;

    @Autowired
    private CorNetworkRepository corNetworkRepository;

    @Autowired
    private CrmAccountRepository crmAccountRepository;

    @Autowired
    private LibLibraryRepository libLibraryRepository;

    @Autowired
    private LibMediaItemRepository libMediaItemRepository;

    @Autowired
    private TraAdvertisementRepository traAdvertisementRepository;

    @Autowired
    private CorChannelRepository corChannelRepository;

    private CorNetwork corNetwork;

    private CrmAccount crmAccount;

    private PodamFactory factory;
    private CorChannel corChannel;
    private LibLibrary libLibrary;
    private TraAdvertisement traAdvertisement;

    @Before
    public void setUp() throws Exception {
        factory = new PodamFactoryImpl();
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corNetwork.setId(null);
        corNetwork = corNetworkRepository.saveAndFlush(corNetwork);

        crmAccount = factory.manufacturePojo(CrmAccount.class);
        crmAccount.setNetwork(corNetwork);
        crmAccount = crmAccountRepository.save(crmAccount);
        crmAccount = factory.manufacturePojo(CrmAccount.class);
        crmAccount.setNetwork(corNetwork);
        crmAccount = crmAccountRepository.save(crmAccount);

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
        LibMediaItem libMediaItemToShuffle = factory.manufacturePojo(LibMediaItem.class);
        libMediaItemToShuffle.setNetwork(corNetwork);
        libMediaItemToShuffle.setLibrary(libLibrary);
        libMediaItemToShuffle = libMediaItemRepository.saveAndFlush(libMediaItemToShuffle);

        traAdvertisement = factory.manufacturePojo(TraAdvertisement.class);
        traAdvertisement.setCustomer(crmAccount);
        traAdvertisement.setNetwork(corNetwork);
        traAdvertisement.setMediaItem(libMediaItemToShuffle);
        traAdvertisement = traAdvertisementRepository.saveAndFlush(traAdvertisement);
    }

    @Test
    public void shouldGetOrders() throws Exception {
        //when
        TraOrder traOrder = factory.manufacturePojo(TraOrder.class);
        traOrder.setCustomer(crmAccount);
        traOrder.setNetwork(corNetwork);
        traOrder.setAdvertisment(traAdvertisement);
        traOrder = customTraOrderRepository.save(traOrder);

        //then
        List<TraOrder> fetchedEntity = traOrderService.getAllOrders(corNetwork.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(1, fetchedEntity.size());
        assertEquals(traOrder.getId(), fetchedEntity.get(0).getId());
        assertEquals(traOrder.getNetwork(), fetchedEntity.get(0).getNetwork());

    }

    @Test
    public void shouldSaveOrder() throws Exception {
        //when
        TraOrder traOrder = factory.manufacturePojo(TraOrder.class);

        traOrder.setCustomer(crmAccount);
        traOrder.setNetwork(corNetwork);
        traOrder.setAdvertisment(traAdvertisement);

        //then
        TraOrder fetchedEntity = traOrderService.saveOrder(traOrder);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
        assertNotNull(fetchedEntity.getCreatedBy());

        assertNotNull(fetchedEntity.getName(), traOrder.getName());
        assertEquals(traOrder.getNetwork(), fetchedEntity.getNetwork());
    }

    @Test
    public void shouldDeleteOrder() throws Exception {
        //when
        TraOrder traOrder = factory.manufacturePojo(TraOrder.class);
        traOrder.setCustomer(crmAccount);
        traOrder.setNetwork(corNetwork);
        traOrder.setAdvertisment(traAdvertisement);
        traOrder = customTraOrderRepository.save(traOrder);
        //then
        traOrderService.deleteOrder(traOrder.getId(), corNetwork.getShortcut());
        TraOrder fetchedEntity = traOrderService.getOrder(traOrder.getId(), corNetwork.getShortcut());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void shouldGetOrder() throws Exception {
        //when
        TraOrder traOrder = factory.manufacturePojo(TraOrder.class);

        traOrder.setCustomer(crmAccount);
        traOrder.setNetwork(corNetwork);
        traOrder.setAdvertisment(traAdvertisement);
        traOrder = customTraOrderRepository.save(traOrder);

        //then
        TraOrder fetchedEntity = traOrderService.getOrder(traOrder.getId(), corNetwork.getShortcut());

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getCreatedBy());
        assertEquals(traOrder.getId(), fetchedEntity.getId());
        assertEquals(traOrder.getNetwork(), fetchedEntity.getNetwork());

    }

    @Test
    public void shouldGetOrderAssociatedWithCustomer() throws Exception {

        //when
        TraOrder traOrder = factory.manufacturePojo(TraOrder.class);

        traOrder.setCustomer(crmAccount);
        traOrder.setNetwork(corNetwork);
        traOrder.setAdvertisment(traAdvertisement);
        traOrder = customTraOrderRepository.save(traOrder);

        //then
        List<TraOrder> fetchedEntity = traOrderService.getCustomerOrders(crmAccount.getShortName(), corNetwork.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(1, fetchedEntity.size());
        assertEquals(traOrder.getId(), fetchedEntity.get(0).getId());
        assertEquals(traOrder.getCustomer(), fetchedEntity.get(0).getCustomer());
        assertEquals(traOrder.getNetwork(), fetchedEntity.get(0).getNetwork());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveTwoOrdersWithSameNameInOneNetwork() throws Exception {

        //when
        TraOrder traOrder = factory.manufacturePojo(TraOrder.class).name(TEST_NAME);

        traOrder.setCustomer(crmAccount);
        traOrder.setNetwork(corNetwork);
        traOrder.setAdvertisment(traAdvertisement);

        TraOrder traOrderSecond = factory.manufacturePojo(TraOrder.class).name(TEST_NAME);
        traOrderSecond.setCustomer(crmAccount);
        traOrderSecond.setNetwork(corNetwork);


        //then
        traOrder = traOrderService.saveOrder(traOrder);
        traOrderSecond = traOrderService.saveOrder(traOrderSecond);

    }

    @Test
    public void shouldSaveTwoOrdersWithSameNameInDifferentNetworks() throws Exception {
        //given


        CorNetwork corNetworkSecond = factory.manufacturePojo(CorNetwork.class);
        corNetworkSecond.setId(null);
        corNetworkSecond = corNetworkRepository.saveAndFlush(corNetworkSecond);
        //when
        TraOrder traOrder = factory.manufacturePojo(TraOrder.class).name(TEST_NAME);
        traOrder.setCustomer(crmAccount);
        traOrder.setNetwork(corNetwork);
        traOrder.setAdvertisment(traAdvertisement);

        TraOrder traOrderSecond = factory.manufacturePojo(TraOrder.class).name(TEST_NAME);
        traOrderSecond.setCustomer(crmAccount);
        traOrderSecond.setNetwork(corNetworkSecond);
        traOrderSecond.setAdvertisment(traAdvertisement);


        //then
        traOrder = traOrderService.saveOrder(traOrder);
        traOrderSecond = traOrderService.saveOrder(traOrderSecond);


    }
}
