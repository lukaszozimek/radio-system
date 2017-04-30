package io.protone.service.traffic;

import io.protone.ProtoneApp;
import io.protone.domain.*;
import io.protone.repository.cor.CorNetworkRepository;
import io.protone.repository.crm.CrmAccountRepository;
import io.protone.repository.traffic.TraCampaignRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by lukaszozimek on 30/04/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class TraCampaignServiceTest {
    @Autowired
    private TraCampaignService traCampaignService;

    @Autowired
    private CorNetworkRepository corNetworkRepository;

    @Autowired
    private TraCampaignRepository traCampaignRepository;

    @Autowired
    private CrmAccountRepository crmAccountRepository;

    private CorNetwork corNetwork;

    private PodamFactory factory;
    private TraOrderService traOrderService;

    @Before
    public void setUp() throws Exception {
        factory = new PodamFactoryImpl();
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corNetwork.setId(null);
        corNetwork = corNetworkRepository.saveAndFlush(corNetwork);

    }

    @Test
    public void shouldGetAllCampaign() throws Exception {
        //when
        TraCampaign campaign = factory.manufacturePojo(TraCampaign.class);
        campaign.setNetwork(corNetwork);
        campaign = traCampaignRepository.save(campaign);

        //then
        List<TraCampaign> fetchedEntity = traCampaignService.getAllCampaign(corNetwork.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(1, fetchedEntity.size());
        assertEquals(campaign.getId(), fetchedEntity.get(0).getId());
        assertEquals(campaign.getNetwork(), fetchedEntity.get(0).getNetwork());
    }

    @Test
    public void shouldSaveCampaignWithOutOrder() throws Exception {
        //when
        TraCampaign campaign = factory.manufacturePojo(TraCampaign.class);
        campaign.setNetwork(corNetwork);

        //then
        TraCampaign fetchedEntity = traCampaignService.saveCampaign(campaign);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
        assertEquals(true, fetchedEntity.getOrders().isEmpty());
        assertEquals(campaign.getNetwork(), fetchedEntity.getNetwork());
    }

    @Test
    public void shouldSaveCampaignWithOrders() throws Exception {
        //when
        TraOrder traOrder = factory.manufacturePojo(TraOrder.class);
        traOrder.setNetwork(corNetwork);
        TraCampaign campaign = factory.manufacturePojo(TraCampaign.class);
        campaign.setNetwork(corNetwork);
        campaign.addOrders(traOrder);

        //then
        TraCampaign fetchedEntity = traCampaignService.saveCampaign(campaign);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
        assertEquals(campaign.getNetwork(), fetchedEntity.getNetwork());

        assertNotNull(fetchedEntity.getOrders());
        assertEquals(1, fetchedEntity.getOrders().size());
        assertNotNull(fetchedEntity.getOrders().stream().findFirst().get().getId());

    }

    @Test
    public void shouldDeleteCampaign() throws Exception {
        //when
        TraCampaign campaign = factory.manufacturePojo(TraCampaign.class);
        campaign.setNetwork(corNetwork);
        campaign = traCampaignRepository.save(campaign);
        //then
        traCampaignService.deleteCampaign(campaign.getName(), corNetwork.getShortcut());
        TraCampaign fetchedEntity = traCampaignService.getCampaign(campaign.getName(), corNetwork.getShortcut());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void shoulGetCampaign() throws Exception {
        //when
        TraCampaign campaign = factory.manufacturePojo(TraCampaign.class);
        campaign.setNetwork(corNetwork);
        campaign = traCampaignRepository.save(campaign);

        //then
        TraCampaign fetchedEntity = traCampaignService.getCampaign(campaign.getName(), corNetwork.getShortcut());

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(campaign.getId(), fetchedEntity.getId());
        assertEquals(campaign.getNetwork(), fetchedEntity.getNetwork());
    }

    @Test
    public void shoulGetCustomerCampaign() throws Exception {

        //when
        CrmAccount crmAccount = factory.manufacturePojo(CrmAccount.class);
        crmAccount.setNetwork(corNetwork);
        crmAccount = crmAccountRepository.save(crmAccount);
        TraCampaign traCampaign = factory.manufacturePojo(TraCampaign.class);
        traCampaign.setNetwork(corNetwork);
        traCampaign.setCustomer(crmAccount);
        traCampaign = traCampaignRepository.save(traCampaign);

        //then
        List<TraCampaign> fetchedEntity = traCampaignService.getCustomerCampaing(crmAccount.getShortName(), corNetwork.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(1, fetchedEntity.size());
        assertEquals(traCampaign.getId(), fetchedEntity.get(0).getId());
        assertEquals(traCampaign.getCustomer(), fetchedEntity.get(0).getCustomer());
        assertEquals(traCampaign.getNetwork(), fetchedEntity.get(0).getNetwork());
    }

}
