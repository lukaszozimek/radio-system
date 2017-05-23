package io.protone.service.traffic;

import io.protone.ProtoneApp;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmAccount;
import io.protone.domain.TraOrder;
import io.protone.repository.cor.CorNetworkRepository;
import io.protone.repository.crm.CrmAccountRepository;
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

    private CorNetwork corNetwork;

    private CrmAccount crmAccount;

    private PodamFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new PodamFactoryImpl();
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corNetwork.setId(null);
        corNetwork = corNetworkRepository.saveAndFlush(corNetwork);

        crmAccount = factory.manufacturePojo(CrmAccount.class);
        crmAccount.setNetwork(corNetwork);
        crmAccount = crmAccountRepository.save(crmAccount);

    }

    @Test
    public void shouldGetOrders() throws Exception {
        //when
        TraOrder traOrder = factory.manufacturePojo(TraOrder.class);
        traOrder.setCustomer(crmAccount);
        traOrder.setNetwork(corNetwork);
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

        //then
        TraOrder fetchedEntity = traOrderService.saveOrder(traOrder);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
        assertNotNull(fetchedEntity.getName(), traOrder.getName());
        assertEquals(traOrder.getNetwork(), fetchedEntity.getNetwork());
    }

    @Test
    public void shouldDeleteOrder() throws Exception {
        //when
        TraOrder traOrder = factory.manufacturePojo(TraOrder.class);
        traOrder.setCustomer(crmAccount);
        traOrder.setNetwork(corNetwork);
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
        traOrder = customTraOrderRepository.save(traOrder);

        //then
        TraOrder fetchedEntity = traOrderService.getOrder(traOrder.getId(), corNetwork.getShortcut());

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(traOrder.getId(), fetchedEntity.getId());
        assertEquals(traOrder.getNetwork(), fetchedEntity.getNetwork());

    }

    @Test
    public void shouldGetOrderAssociatedWithCustomer() throws Exception {

        //when
        TraOrder traOrder = factory.manufacturePojo(TraOrder.class);
        traOrder.setNetwork(corNetwork);
        traOrder.setCustomer(crmAccount);
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

        TraOrder traOrderSecond = factory.manufacturePojo(TraOrder.class).name(TEST_NAME);
        traOrderSecond.setCustomer(crmAccount);
        traOrderSecond.setNetwork(corNetworkSecond);


        //then
        traOrder = traOrderService.saveOrder(traOrder);
        traOrderSecond = traOrderService.saveOrder(traOrderSecond);


    }
}
