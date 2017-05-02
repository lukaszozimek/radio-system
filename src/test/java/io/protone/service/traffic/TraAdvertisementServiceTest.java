package io.protone.service.traffic;

import io.protone.ProtoneApp;
import io.protone.custom.service.LibItemService;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmAccount;
import io.protone.domain.LibMediaItem;
import io.protone.domain.TraAdvertisement;
import io.protone.repository.cor.CorNetworkRepository;
import io.protone.repository.crm.CrmAccountRepository;
import io.protone.repository.library.LibMediaItemRepository;
import io.protone.repository.traffic.TraAdvertisementRepository;
import org.apache.commons.lang.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ReflectionUtils;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doNothing;

/**
 * Created by lukaszozimek on 30/04/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class TraAdvertisementServiceTest {
    @Autowired
    private TraAdvertisementService traAdvertisementService;

    @Autowired
    private CorNetworkRepository corNetworkRepository;

    @Autowired
    private TraAdvertisementRepository traCampaignRepository;

    @Autowired
    private CrmAccountRepository crmAccountRepository;

    @Autowired
    private LibMediaItemRepository libMediaItemRepository;

    @Mock
    private LibItemService libItemService;

    private CorNetwork corNetwork;

    private PodamFactory factory;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        factory = new PodamFactoryImpl();
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corNetwork.setId(null);
        corNetwork = corNetworkRepository.saveAndFlush(corNetwork);

        doNothing().when(libItemService).deleteItem(anyObject());
        ReflectionUtils.setField(FieldUtils.getField(TraAdvertisementService.class, "libItemService", true), traAdvertisementService, libItemService);

    }

    @Test
    public void shouldGetAllAdvertisement() throws Exception {
        //when
        TraAdvertisement traAdvertisement = factory.manufacturePojo(TraAdvertisement.class);
        traAdvertisement.setNetwork(corNetwork);
        traAdvertisement = traCampaignRepository.save(traAdvertisement);

        //then
        List<TraAdvertisement> fetchedEntity = traAdvertisementService.getAllAdvertisement(corNetwork.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(1, fetchedEntity.size());
        assertEquals(traAdvertisement.getId(), fetchedEntity.get(0).getId());
        assertEquals(traAdvertisement.getNetwork(), fetchedEntity.get(0).getNetwork());
    }

    @Test
    public void saveAdvertisement() throws Exception {
        //when
        //TODO: Provide network constraint for LibMediaItem
        LibMediaItem libMediaItem = factory.manufacturePojo(LibMediaItem.class);
        libMediaItem = libMediaItemRepository.save(libMediaItem);
        TraAdvertisement traAdvertisement = factory.manufacturePojo(TraAdvertisement.class);
        traAdvertisement.setNetwork(corNetwork);
        traAdvertisement.setMediaItem(libMediaItem);

        //then
        TraAdvertisement fetchedEntity = traAdvertisementService.saveAdvertisement(traAdvertisement);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
        assertEquals(traAdvertisement.getNetwork(), fetchedEntity.getNetwork());
        assertNotNull(fetchedEntity.getMediaItem());
        assertEquals(libMediaItem.getId(), fetchedEntity.getMediaItem().getId());
    }

    @Test
    public void deleteAdvertisement() throws Exception {
        //when
        TraAdvertisement traAdvertisement = factory.manufacturePojo(TraAdvertisement.class);
        traAdvertisement.setNetwork(corNetwork);
        traAdvertisement = traCampaignRepository.save(traAdvertisement);

        //then
        traAdvertisementService.deleteAdvertisement(traAdvertisement.getId(), corNetwork.getShortcut());
        TraAdvertisement fetchedEntity = traAdvertisementService.getAdvertisement(traAdvertisement.getId(), corNetwork.getShortcut());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void shouldGetAdvertisement() throws Exception {
        //when
        TraAdvertisement traAdvertisement = factory.manufacturePojo(TraAdvertisement.class);
        traAdvertisement.setNetwork(corNetwork);
        traAdvertisement = traCampaignRepository.save(traAdvertisement);

        //then
        TraAdvertisement fetchedEntity = traAdvertisementService.getAdvertisement(traAdvertisement.getId(), corNetwork.getShortcut());

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(traAdvertisement.getId(), fetchedEntity.getId());
        assertEquals(traAdvertisement.getNetwork(), fetchedEntity.getNetwork());
    }

    @Test
    public void shouldGetCustomerAdvertisements() throws Exception {
        //when
        CrmAccount crmAccount = factory.manufacturePojo(CrmAccount.class);
        crmAccount.setNetwork(corNetwork);
        crmAccount = crmAccountRepository.save(crmAccount);
        TraAdvertisement traAdvertisement = factory.manufacturePojo(TraAdvertisement.class);
        traAdvertisement.setNetwork(corNetwork);
        traAdvertisement.setCustomer(crmAccount);
        traAdvertisement = traCampaignRepository.save(traAdvertisement);

        //then
        List<TraAdvertisement> fetchedEntity = traAdvertisementService.getCustomerAdvertisements(crmAccount.getShortName(), corNetwork.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(1, fetchedEntity.size());
        assertEquals(traAdvertisement.getId(), fetchedEntity.get(0).getId());
        assertEquals(traAdvertisement.getCustomer(), fetchedEntity.get(0).getCustomer());
        assertEquals(traAdvertisement.getNetwork(), fetchedEntity.get(0).getNetwork());
    }

}