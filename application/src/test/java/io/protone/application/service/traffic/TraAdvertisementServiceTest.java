package io.protone.application.service.traffic;

import com.google.common.collect.Sets;
import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorNetwork;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.crm.domain.CrmAccount;
import io.protone.crm.repostiory.CrmAccountRepository;
import io.protone.library.domain.LibLibrary;
import io.protone.library.domain.LibMediaItem;
import io.protone.library.domain.enumeration.LibItemTypeEnum;
import io.protone.library.repository.LibMediaItemRepository;
import io.protone.library.service.LibItemService;
import io.protone.traffic.domain.TraAdvertisement;
import io.protone.traffic.repository.TraAdvertisementRepository;
import io.protone.traffic.service.TraAdvertisementService;
import org.apache.commons.lang.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
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
    private TraAdvertisementRepository traAdvertisementRepository;

    @Autowired
    private CrmAccountRepository crmAccountRepository;

    @Autowired
    private LibMediaItemRepository libMediaItemRepository;

    @Mock
    private LibItemService libItemService;

    private CorNetwork corNetwork;

    private CrmAccount crmAccount;

    private LibMediaItem libMediaItem;

    private PodamFactory factory;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        factory = new PodamFactoryImpl();
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corNetwork.setId(null);
        LibLibrary libLibrary = new LibLibrary();
        libLibrary.setId(1L);
        libLibrary.setShortcut("tes");
        crmAccount = factory.manufacturePojo(CrmAccount.class);
        crmAccount = crmAccountRepository.save(crmAccount);
        corNetwork = corNetworkRepository.saveAndFlush(corNetwork);
        libMediaItem = factory.manufacturePojo(LibMediaItem.class);

        libMediaItem.setNetwork(corNetwork);
        libMediaItem.setLibrary(libLibrary);
        libMediaItem.setItemType(LibItemTypeEnum.IT_AUDIO);
        libMediaItem = libMediaItemRepository.save(libMediaItem);

        doNothing().when(libItemService).deleteItem(anyObject());
        ReflectionUtils.setField(FieldUtils.getField(TraAdvertisementService.class, "libItemService", true), traAdvertisementService, libItemService);

    }

    @Test
    public void shouldGetAllAdvertisement() throws Exception {
        //when
        TraAdvertisement traAdvertisement = factory.manufacturePojo(TraAdvertisement.class);
        traAdvertisement.setNetwork(corNetwork);
        traAdvertisement.customer(crmAccount);
        traAdvertisement.setMediaItem(Sets.newHashSet(libMediaItem));
        traAdvertisement = traAdvertisementRepository.save(traAdvertisement);

        //then
        Slice<TraAdvertisement> fetchedEntity = traAdvertisementService.getAllAdvertisement(corNetwork.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(traAdvertisement.getId(), fetchedEntity.getContent().get(0).getId());
        assertEquals(traAdvertisement.getNetwork(), fetchedEntity.getContent().get(0).getNetwork());
    }

    @Test
    public void saveAdvertisement() throws Exception {
        //when

        TraAdvertisement traAdvertisement = factory.manufacturePojo(TraAdvertisement.class);
        traAdvertisement.customer(crmAccount);
        traAdvertisement.setMediaItem(Sets.newHashSet(libMediaItem));
        traAdvertisement.setNetwork(corNetwork);
        traAdvertisement.setMediaItem(Sets.newHashSet(libMediaItem));

        //then
        TraAdvertisement fetchedEntity = traAdvertisementService.saveAdvertisement(traAdvertisement);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
        assertNotNull(fetchedEntity.getCreatedBy());
        assertEquals(traAdvertisement.getNetwork(), fetchedEntity.getNetwork());
        assertNotNull(fetchedEntity.getMediaItem());
        assertEquals(libMediaItem.getId(), fetchedEntity.getMediaItem().stream().findFirst().get().getId());
    }

    @Test
    public void deleteAdvertisement() throws Exception {
        //when
        TraAdvertisement traAdvertisement = factory.manufacturePojo(TraAdvertisement.class);
        traAdvertisement.setNetwork(corNetwork);
        traAdvertisement.setMediaItem(Sets.newHashSet(libMediaItem));
        traAdvertisement.customer(crmAccount);
        traAdvertisement = traAdvertisementRepository.save(traAdvertisement);

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
        traAdvertisement.setMediaItem(Sets.newHashSet(libMediaItem));
        traAdvertisement.customer(crmAccount);
        traAdvertisement = traAdvertisementRepository.save(traAdvertisement);

        //then
        TraAdvertisement fetchedEntity = traAdvertisementService.getAdvertisement(traAdvertisement.getId(), corNetwork.getShortcut());

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getCreatedBy());
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
        traAdvertisement.setMediaItem(Sets.newHashSet(libMediaItem));
        traAdvertisement = traAdvertisementRepository.save(traAdvertisement);

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
