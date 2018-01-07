package io.protone.application.service.traffic;

import com.google.common.collect.Sets;
import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorOrganization;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.crm.domain.CrmAccount;
import io.protone.crm.repostiory.CrmAccountRepository;
import io.protone.library.domain.LibMediaItem;
import io.protone.library.domain.LibMediaLibrary;
import io.protone.library.domain.enumeration.LibItemTypeEnum;
import io.protone.library.repository.LibMediaItemRepository;
import io.protone.library.service.LibMediaItemService;
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

import static io.protone.application.util.TestConstans.*;
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
    private LibMediaItemService libMediaItemService;

    private CorChannel corChannel;

    private CorOrganization corOrganization;

    private CrmAccount crmAccount;

    private LibMediaItem libMediaItem;

    private PodamFactory factory;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        factory = new PodamFactoryImpl();
        corOrganization = new CorOrganization().shortcut(TEST_ORGANIZATION_SHORTCUT);
        corOrganization.setId(TEST_ORGANIZATION_ID);
        corChannel = new CorChannel().shortcut(TEST_CHANNEL_SHORTCUT);
        corChannel.setId(TEST_CHANNEL_ID);

        LibMediaLibrary libMediaLibrary = new LibMediaLibrary();
        libMediaLibrary.setId(1L);
        libMediaLibrary.setShortcut("tes");
        crmAccount = factory.manufacturePojo(CrmAccount.class);
        crmAccount = crmAccountRepository.save(crmAccount);
        libMediaItem = factory.manufacturePojo(LibMediaItem.class);

        libMediaItem.setLibrary(libMediaLibrary);
        libMediaItem.setItemType(LibItemTypeEnum.IT_AUDIO);
        libMediaItem = libMediaItemRepository.save(libMediaItem);

        doNothing().when(libMediaItemService).deleteItem(anyObject());
        ReflectionUtils.setField(FieldUtils.getField(TraAdvertisementService.class, "libMediaItemService", true), traAdvertisementService, libMediaItemService);

    }

    @Test
    public void shouldGetAllAdvertisement() throws Exception {
        //when
        TraAdvertisement traAdvertisement = factory.manufacturePojo(TraAdvertisement.class);
        traAdvertisement.setChannel(corChannel);
        traAdvertisement.customer(crmAccount);
        traAdvertisement.setMediaItem(Sets.newHashSet(libMediaItem));
        traAdvertisement = traAdvertisementRepository.save(traAdvertisement);

        //then
        Slice<TraAdvertisement> fetchedEntity = traAdvertisementService.getAllAdvertisement(corOrganization.getShortcut(), corChannel.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(traAdvertisement.getId(), fetchedEntity.getContent().get(0).getId());
        assertEquals(traAdvertisement.getChannel(), fetchedEntity.getContent().get(0).getChannel());
    }

    @Test
    public void saveAdvertisement() throws Exception {
        //when

        TraAdvertisement traAdvertisement = factory.manufacturePojo(TraAdvertisement.class);
        traAdvertisement.customer(crmAccount);
        traAdvertisement.setMediaItem(Sets.newHashSet(libMediaItem));
        traAdvertisement.setChannel(corChannel);
        traAdvertisement.setMediaItem(Sets.newHashSet(libMediaItem));

        //then
        TraAdvertisement fetchedEntity = traAdvertisementService.saveAdvertisement(traAdvertisement);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
        assertNotNull(fetchedEntity.getCreatedBy());
        assertEquals(traAdvertisement.getChannel(), fetchedEntity.getChannel());
        assertNotNull(fetchedEntity.getMediaItem());
        assertEquals(libMediaItem.getId(), fetchedEntity.getMediaItem().stream().findFirst().get().getId());
    }

    @Test
    public void deleteAdvertisement() throws Exception {
        //when
        TraAdvertisement traAdvertisement = factory.manufacturePojo(TraAdvertisement.class);
        traAdvertisement.setChannel(corChannel);
        traAdvertisement.setMediaItem(Sets.newHashSet(libMediaItem));
        traAdvertisement.customer(crmAccount);
        traAdvertisement = traAdvertisementRepository.save(traAdvertisement);

        //then
        traAdvertisementService.deleteAdvertisement(traAdvertisement.getId(), corOrganization.getShortcut(), corChannel.getShortcut());
        TraAdvertisement fetchedEntity = traAdvertisementService.getAdvertisement(traAdvertisement.getId(), corOrganization.getShortcut(), corChannel.getShortcut());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void shouldGetAdvertisement() throws Exception {
        //when
        TraAdvertisement traAdvertisement = factory.manufacturePojo(TraAdvertisement.class);
        traAdvertisement.setChannel(corChannel);
        traAdvertisement.setMediaItem(Sets.newHashSet(libMediaItem));
        traAdvertisement.customer(crmAccount);
        traAdvertisement = traAdvertisementRepository.save(traAdvertisement);

        //then
        TraAdvertisement fetchedEntity = traAdvertisementService.getAdvertisement(traAdvertisement.getId(), corOrganization.getShortcut(), corChannel.getShortcut());

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getCreatedBy());
        assertEquals(traAdvertisement.getId(), fetchedEntity.getId());
        assertEquals(traAdvertisement.getChannel(), fetchedEntity.getChannel());
    }

    @Test
    public void shouldGetCustomerAdvertisements() throws Exception {
        //when
        CrmAccount crmAccount = factory.manufacturePojo(CrmAccount.class);
        crmAccount.setChannel(corChannel);
        crmAccount = crmAccountRepository.save(crmAccount);
        TraAdvertisement traAdvertisement = factory.manufacturePojo(TraAdvertisement.class);
        traAdvertisement.setChannel(corChannel);
        traAdvertisement.setCustomer(crmAccount);
        traAdvertisement.setMediaItem(Sets.newHashSet(libMediaItem));
        traAdvertisement = traAdvertisementRepository.save(traAdvertisement);

        //then
        List<TraAdvertisement> fetchedEntity = traAdvertisementService.getCustomerAdvertisements(crmAccount.getShortName(), corOrganization.getShortcut(), corChannel.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(1, fetchedEntity.size());
        assertEquals(traAdvertisement.getId(), fetchedEntity.get(0).getId());
        assertEquals(traAdvertisement.getCustomer(), fetchedEntity.get(0).getCustomer());
        assertEquals(traAdvertisement.getChannel(), fetchedEntity.get(0).getChannel());
    }

}
