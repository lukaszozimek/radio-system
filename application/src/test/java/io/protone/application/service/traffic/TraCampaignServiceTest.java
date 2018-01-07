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
import io.protone.traffic.domain.TraCampaign;
import io.protone.traffic.domain.TraOrder;
import io.protone.traffic.repository.TraAdvertisementRepository;
import io.protone.traffic.repository.TraCampaignRepository;
import io.protone.traffic.service.TraCampaignService;
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

    @Autowired
    private CorOrganizationRepository corOrganizationRepository;

    private CrmAccount crmAccount;

    private LibMediaLibrary libMediaLibrary;

    private CorChannel corChannel;

    private CorOrganization corOrganization;

    private TraAdvertisement traAdvertisement;

    private PodamFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new PodamFactoryImpl();
        corOrganization = new CorOrganization().shortcut(TEST_ORGANIZATION_SHORTCUT);
        corOrganization.setId(TEST_ORGANIZATION_ID);
        corChannel = new CorChannel().shortcut(TEST_CHANNEL_SHORTCUT);
        corChannel.setId(TEST_CHANNEL_ID);

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
    public void shouldGetAllCampaign() throws Exception {
        //when
        TraCampaign campaign = factory.manufacturePojo(TraCampaign.class);
        campaign.setChannel(corChannel);
        campaign.setCustomer(crmAccount);
        campaign = traCampaignRepository.save(campaign);

        //then
        Slice<TraCampaign> fetchedEntity = traCampaignService.getAllCampaign(corOrganization.getShortcut(), corChannel.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(campaign.getId(), fetchedEntity.getContent().get(0).getId());
        assertEquals(campaign.getChannel(), fetchedEntity.getContent().get(0).getChannel());
    }

    @Test
    public void shouldSaveCampaignWithOutOrder() throws Exception {
        //when
        TraCampaign campaign = factory.manufacturePojo(TraCampaign.class);

        campaign.setCustomer(crmAccount);
        campaign.setChannel(corChannel);

        //then
        TraCampaign fetchedEntity = traCampaignService.saveCampaign(campaign);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
        assertNotNull(fetchedEntity.getCreatedBy());
        assertEquals(true, fetchedEntity.getOrders().isEmpty());
        assertEquals(campaign.getChannel(), fetchedEntity.getChannel());
    }

    @Test
    public void shouldSaveCampaignWithOrders() throws Exception {
        //when


        TraOrder traOrder = factory.manufacturePojo(TraOrder.class);
        traOrder.setCustomer(crmAccount);
        traOrder.setChannel(corChannel);
        traOrder.setAdvertisment(traAdvertisement);
        TraCampaign campaign = factory.manufacturePojo(TraCampaign.class);
        campaign.setCustomer(crmAccount);
        campaign.setChannel(corChannel);
        campaign.addOrders(traOrder);

        //then
        TraCampaign fetchedEntity = traCampaignService.saveCampaign(campaign);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
        assertNotNull(fetchedEntity.getCreatedBy());
        assertEquals(campaign.getChannel(), fetchedEntity.getChannel());

        assertNotNull(fetchedEntity.getOrders());
        assertEquals(1, fetchedEntity.getOrders().size());
        assertNotNull(fetchedEntity.getOrders().stream().findFirst().get().getId());

    }

    @Test
    public void shouldDeleteCampaign() throws Exception {
        //when
        TraCampaign campaign = factory.manufacturePojo(TraCampaign.class);
        campaign.setCustomer(crmAccount);
        campaign.setChannel(corChannel);
        campaign = traCampaignRepository.save(campaign);
        //then
        traCampaignService.deleteCampaign(campaign.getShortName(), corOrganization.getShortcut(), corChannel.getShortcut());
        TraCampaign fetchedEntity = traCampaignService.getCampaign(campaign.getShortName(), corOrganization.getShortcut(), corChannel.getShortcut());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void shoulGetCampaign() throws Exception {
        //when
        TraCampaign campaign = factory.manufacturePojo(TraCampaign.class);
        campaign.setCustomer(crmAccount);
        campaign.setChannel(corChannel);
        campaign = traCampaignRepository.save(campaign);

        //then
        TraCampaign fetchedEntity = traCampaignService.getCampaign(campaign.getShortName(), corOrganization.getShortcut(), corChannel.getShortcut());

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getCreatedBy());
        assertEquals(campaign.getId(), fetchedEntity.getId());
        assertEquals(campaign.getChannel(), fetchedEntity.getChannel());
    }

    @Test
    public void shoulGetCustomerCampaign() throws Exception {

        //when
        TraCampaign traCampaign = factory.manufacturePojo(TraCampaign.class);
        traCampaign.setChannel(corChannel);
        traCampaign.setCustomer(crmAccount);
        traCampaign = traCampaignRepository.save(traCampaign);

        //then
        Slice<TraCampaign> fetchedEntity = traCampaignService.getCustomerCampaing(crmAccount.getShortName(), corOrganization.getShortcut(), corChannel.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(traCampaign.getId(), fetchedEntity.getContent().get(0).getId());
        assertEquals(traCampaign.getCustomer(), fetchedEntity.getContent().get(0).getCustomer());
        assertEquals(traCampaign.getChannel(), fetchedEntity.getContent().get(0).getChannel());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveTwoTraCampaignWithSameShortNameInOneNetwork() {
        //when
        TraCampaign campaign = factory.manufacturePojo(TraCampaign.class);
        campaign.setShortName(TEST_SHORTNAME);
        campaign.setCustomer(crmAccount);
        campaign.setChannel(corChannel);
        TraCampaign campaignSecond = factory.manufacturePojo(TraCampaign.class);
        campaignSecond.setShortName(TEST_SHORTNAME);
        campaignSecond.setCustomer(crmAccount);
        campaignSecond.setChannel(corChannel);


        //then
        campaign = traCampaignService.saveCampaign(campaign);
        campaignSecond = traCampaignService.saveCampaign(campaignSecond);


    }

    @Test
    public void shouldSaveTwoTraCampaignWithSameShortNameInDifferentNetwork() {
        //given
        CorOrganization corOrganizationkSecond = factory.manufacturePojo(CorOrganization.class);
        corOrganizationkSecond.setId(null);
        corOrganizationkSecond = corOrganizationRepository.saveAndFlush(corOrganizationkSecond);
        CorChannel corChannelSecond = factory.manufacturePojo(CorChannel.class);
        corChannelSecond.setId(null);
        corChannelSecond.setOrganization(corOrganizationkSecond);
        corChannelSecond = corChannelRepository.save(corChannelSecond);

        //when
        TraCampaign campaign = factory.manufacturePojo(TraCampaign.class);
        campaign.setShortName(TEST_SHORTNAME);
        campaign.setCustomer(crmAccount);
        campaign.setChannel(corChannel);

        TraCampaign campaignSecond = factory.manufacturePojo(TraCampaign.class);
        campaignSecond.setShortName(TEST_SHORTNAME);
        campaignSecond.setCustomer(crmAccount);
        campaignSecond.setChannel(corChannelSecond);


        //then
        campaign = traCampaignService.saveCampaign(campaign);
        campaignSecond = traCampaignService.saveCampaign(campaignSecond);

    }
}
