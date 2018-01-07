package io.protone.application.service.traffic;


import com.google.common.collect.Sets;
import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorOrganization;
import io.protone.core.repository.CorChannelRepository;
import io.protone.core.repository.CorOrganizationRepository;
import io.protone.crm.domain.CrmAccount;
import io.protone.crm.repostiory.CrmAccountRepository;
import io.protone.library.domain.LibMediaItem;
import io.protone.library.domain.LibMediaLibrary;
import io.protone.library.repository.LibLibraryRepository;
import io.protone.library.repository.LibMediaItemRepository;
import io.protone.traffic.domain.TraAdvertisement;
import io.protone.traffic.domain.TraOrder;
import io.protone.traffic.repository.TraAdvertisementRepository;
import io.protone.traffic.repository.TraOrderRepository;
import io.protone.traffic.service.TraOrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;

import static io.protone.application.util.TestConstans.*;
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
    private CrmAccountRepository crmAccountRepository;

    @Autowired
    private LibLibraryRepository libLibraryRepository;

    @Autowired
    private LibMediaItemRepository libMediaItemRepository;

    @Autowired
    private TraAdvertisementRepository traAdvertisementRepository;

    @Autowired
    private CorOrganizationRepository corOrganizationRepository;

    @Autowired
    private CorChannelRepository corChannelRepository;

    private CrmAccount crmAccount;

    private PodamFactory factory;

    private CorChannel corChannel;

    private CorOrganization corOrganization;

    private LibMediaLibrary libMediaLibrary;

    private TraAdvertisement traAdvertisement;

    @Before
    public void setUp() throws Exception {
        factory = new PodamFactoryImpl();

        corOrganization = new CorOrganization().shortcut(TEST_ORGANIZATION_SHORTCUT);
        corOrganization.setId(TEST_ORGANIZATION_ID);
        corChannel = new CorChannel().shortcut(TEST_CHANNEL_SHORTCUT);
        corChannel.setId(TEST_CHANNEL_ID);
        corChannel.setOrganization(corOrganization);

        crmAccount = factory.manufacturePojo(CrmAccount.class);
        crmAccount.setChannel(corChannel);
        crmAccount = crmAccountRepository.save(crmAccount);
        crmAccount = factory.manufacturePojo(CrmAccount.class);
        crmAccount.setChannel(corChannel);
        crmAccount = crmAccountRepository.save(crmAccount);


        libMediaLibrary = libLibraryRepository.findOne(2L);
        LibMediaItem libMediaItemToShuffle = factory.manufacturePojo(LibMediaItem.class);
        libMediaItemToShuffle.setLibrary(libMediaLibrary);
        libMediaItemToShuffle = libMediaItemRepository.saveAndFlush(libMediaItemToShuffle);

        traAdvertisement = factory.manufacturePojo(TraAdvertisement.class);
        traAdvertisement.setCustomer(crmAccount);
        traAdvertisement.setChannel(corChannel);
        traAdvertisement.setMediaItem(Sets.newHashSet(libMediaItemToShuffle));
        traAdvertisement = traAdvertisementRepository.saveAndFlush(traAdvertisement);
    }

    @Test
    public void shouldGetOrders() throws Exception {
        //when
        TraOrder traOrder = factory.manufacturePojo(TraOrder.class);
        traOrder.setCustomer(crmAccount);
        traOrder.setChannel(corChannel);
        traOrder.setAdvertisment(traAdvertisement);
        traOrder = customTraOrderRepository.save(traOrder);

        //then
        Slice<TraOrder> fetchedEntity = traOrderService.getAllOrders(corOrganization.getShortcut(), corChannel.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(traOrder.getId(), fetchedEntity.getContent().get(0).getId());
        assertEquals(traOrder.getChannel(), fetchedEntity.getContent().get(0).getChannel());

    }

    @Test
    public void shouldSaveOrder() throws Exception {
        //when
        TraOrder traOrder = factory.manufacturePojo(TraOrder.class);
        traOrder.setCustomer(crmAccount);
        traOrder.setChannel(corChannel);
        traOrder.setAdvertisment(traAdvertisement);

        //then
        TraOrder fetchedEntity = traOrderService.saveOrder(traOrder);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
        assertNotNull(fetchedEntity.getCreatedBy());

        assertNotNull(fetchedEntity.getName(), traOrder.getName());
        assertEquals(traOrder.getChannel(), fetchedEntity.getChannel());
    }

    @Test
    public void shouldDeleteOrder() throws Exception {
        //when
        TraOrder traOrder = factory.manufacturePojo(TraOrder.class);
        traOrder.setCustomer(crmAccount);
        traOrder.setChannel(corChannel);
        traOrder.setAdvertisment(traAdvertisement);
        traOrder = customTraOrderRepository.save(traOrder);
        //then
        traOrderService.deleteOrder(traOrder.getId(), corOrganization.getShortcut(), corChannel.getShortcut());
        TraOrder fetchedEntity = traOrderService.getOrder(traOrder.getId(), corOrganization.getShortcut(), corChannel.getShortcut());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void shouldGetOrder() throws Exception {
        //when
        TraOrder traOrder = factory.manufacturePojo(TraOrder.class);

        traOrder.setCustomer(crmAccount);
        traOrder.setChannel(corChannel);
        traOrder.setAdvertisment(traAdvertisement);
        traOrder = customTraOrderRepository.save(traOrder);

        //then
        TraOrder fetchedEntity = traOrderService.getOrder(traOrder.getId(), corOrganization.getShortcut(), corChannel.getShortcut());

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getCreatedBy());
        assertEquals(traOrder.getId(), fetchedEntity.getId());
        assertEquals(traOrder.getChannel(), fetchedEntity.getChannel());

    }

    @Test
    public void shouldGetOrderAssociatedWithCustomer() throws Exception {

        //when
        TraOrder traOrder = factory.manufacturePojo(TraOrder.class);

        traOrder.setCustomer(crmAccount);
        traOrder.setChannel(corChannel);
        traOrder.setAdvertisment(traAdvertisement);
        traOrder = customTraOrderRepository.save(traOrder);

        //then
        Slice<TraOrder> fetchedEntity = traOrderService.getCustomerOrders(crmAccount.getShortName(), corOrganization.getShortcut(), corChannel.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(traOrder.getId(), fetchedEntity.getContent().get(0).getId());
        assertEquals(traOrder.getCustomer(), fetchedEntity.getContent().get(0).getCustomer());
        assertEquals(traOrder.getChannel(), fetchedEntity.getContent().get(0).getChannel());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveTwoOrdersWithSameNameInOneNetwork() throws Exception {

        //when
        TraOrder traOrder = factory.manufacturePojo(TraOrder.class).name(TEST_NAME);

        traOrder.setCustomer(crmAccount);
        traOrder.setChannel(corChannel);
        traOrder.setAdvertisment(traAdvertisement);

        TraOrder traOrderSecond = factory.manufacturePojo(TraOrder.class).name(TEST_NAME);
        traOrderSecond.setCustomer(crmAccount);
        traOrderSecond.setChannel(corChannel);


        //then
        traOrder = traOrderService.saveOrder(traOrder);
        traOrderSecond = traOrderService.saveOrder(traOrderSecond);

    }

    @Test
    public void shouldSaveTwoOrdersWithSameNameInDifferentNetworks() throws Exception {
        //given
        CorOrganization corOrganizationkSecond = factory.manufacturePojo(CorOrganization.class);
        corOrganizationkSecond.setId(null);
        corOrganizationkSecond = corOrganizationRepository.saveAndFlush(corOrganizationkSecond);

        CorChannel corChannelSecond = factory.manufacturePojo(CorChannel.class);
        corChannelSecond.setId(null);
        corChannelSecond.setOrganization(corOrganizationkSecond);
        corChannelSecond = corChannelRepository.save(corChannelSecond);

        //when
        TraOrder traOrder = factory.manufacturePojo(TraOrder.class).name(TEST_NAME);
        traOrder.setCustomer(crmAccount);
        traOrder.setChannel(corChannel);
        traOrder.setAdvertisment(traAdvertisement);

        TraOrder traOrderSecond = factory.manufacturePojo(TraOrder.class).name(TEST_NAME);
        traOrderSecond.setCustomer(crmAccount);
        traOrderSecond.setChannel(corChannelSecond);
        traOrderSecond.setAdvertisment(traAdvertisement);


        //then
        traOrder = traOrderService.saveOrder(traOrder);
        traOrderSecond = traOrderService.saveOrder(traOrderSecond);


    }
}
