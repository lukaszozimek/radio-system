package io.protone.service.traffic;

import io.protone.ProtoneApp;
import io.protone.domain.*;
import io.protone.repository.cor.CorChannelRepository;
import io.protone.repository.cor.CorNetworkRepository;
import io.protone.repository.crm.CrmAccountRepository;
import io.protone.repository.library.LibLibraryRepository;
import io.protone.repository.library.LibMediaItemRepository;
import io.protone.repository.traffic.TraAdvertisementRepository;
import io.protone.repository.traffic.TraCampaignRepository;
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
 * Created by lukaszozimek on 30/04/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class TraCampaignServiceTest {
    private static final String TEST_SHORTNAME = "test";
    @Autowired
    private TraCampaignService traCampaignService;

    @Autowired
    private CorNetworkRepository corNetworkRepository;

    @Autowired
    private TraCampaignRepository traCampaignRepository;

    @Autowired
    private CrmAccountRepository crmAccountRepository;

    @Autowired
    private LibMediaItemRepository libMediaItemRepository;

    @Autowired
    private TraAdvertisementRepository traAdvertisementRepository;
    @Autowired
    private CorChannelRepository corChannelRepository;
    @Autowired
    private LibLibraryRepository libLibraryRepository;

    private CorNetwork corNetwork;

    private CrmAccount crmAccount;

    private LibLibrary libLibrary;

    private CorChannel corChannel;

    private TraAdvertisement traAdvertisement;

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
    public void shouldGetAllCampaign() throws Exception {
        //when
        TraCampaign campaign = factory.manufacturePojo(TraCampaign.class);
        campaign.setNetwork(corNetwork);
        campaign.setCustomer(crmAccount);
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

        campaign.setCustomer(crmAccount);
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
        traOrder.setCustomer(crmAccount);
        traOrder.setNetwork(corNetwork);
        traOrder.setAdvertisment(traAdvertisement);
        TraCampaign campaign = factory.manufacturePojo(TraCampaign.class);
        campaign.setCustomer(crmAccount);
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
        campaign.setCustomer(crmAccount);
        campaign.setNetwork(corNetwork);
        campaign = traCampaignRepository.save(campaign);
        //then
        traCampaignService.deleteCampaign(campaign.getShortName(), corNetwork.getShortcut());
        TraCampaign fetchedEntity = traCampaignService.getCampaign(campaign.getShortName(), corNetwork.getShortcut());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void shoulGetCampaign() throws Exception {
        //when
        TraCampaign campaign = factory.manufacturePojo(TraCampaign.class);
        campaign.setCustomer(crmAccount);
        campaign.setNetwork(corNetwork);
        campaign = traCampaignRepository.save(campaign);

        //then
        TraCampaign fetchedEntity = traCampaignService.getCampaign(campaign.getShortName(), corNetwork.getShortcut());

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(campaign.getId(), fetchedEntity.getId());
        assertEquals(campaign.getNetwork(), fetchedEntity.getNetwork());
    }

    @Test
    public void shoulGetCustomerCampaign() throws Exception {

        //when
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

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveTwoTraCampaignWithSameShortNameInOneNetwork() {
        //when
        TraCampaign campaign = factory.manufacturePojo(TraCampaign.class);
        campaign.setShortName(TEST_SHORTNAME);
        campaign.setCustomer(crmAccount);
        campaign.setNetwork(corNetwork);
        TraCampaign campaignSecond = factory.manufacturePojo(TraCampaign.class);
        campaignSecond.setShortName(TEST_SHORTNAME);
        campaignSecond.setCustomer(crmAccount);
        campaignSecond.setNetwork(corNetwork);


        //then
        campaign = traCampaignService.saveCampaign(campaign);
        campaignSecond = traCampaignService.saveCampaign(campaignSecond);


    }

    @Test
    public void shouldSaveTwoTraCampaignWithSameShortNameInDifferentNetwork() {
        //given
        CorNetwork corNetworkSecond = factory.manufacturePojo(CorNetwork.class);
        corNetworkSecond.setId(null);
        corNetworkSecond = corNetworkRepository.save(corNetworkSecond);

        //when
        TraCampaign campaign = factory.manufacturePojo(TraCampaign.class);
        campaign.setShortName(TEST_SHORTNAME);
        campaign.setCustomer(crmAccount);
        campaign.setNetwork(corNetwork);

        TraCampaign campaignSecond = factory.manufacturePojo(TraCampaign.class);
        campaignSecond.setShortName(TEST_SHORTNAME);
        campaignSecond.setCustomer(crmAccount);
        campaignSecond.setNetwork(corNetworkSecond);


        //then
        campaign = traCampaignService.saveCampaign(campaign);
        campaignSecond = traCampaignService.saveCampaign(campaignSecond);

    }
}
