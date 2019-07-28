package io.protone.application.service.traffic;


import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.repository.CorChannelRepository;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.crm.domain.CrmAccount;
import io.protone.crm.repostiory.CrmAccountRepository;
import io.protone.library.domain.LibCloudObject;
import io.protone.library.domain.LibFileItem;
import io.protone.library.domain.LibFileLibrary;
import io.protone.library.repository.LibCloudObjectRepository;
import io.protone.library.repository.LibFileItemRepository;
import io.protone.library.repository.LibFileLibraryRepository;
import io.protone.traffic.domain.TraMediaPlan;
import io.protone.traffic.domain.TraMediaPlanPlaylistDate;
import io.protone.traffic.repository.TraAdvertisementRepository;
import io.protone.traffic.repository.TraMediaPlanRepository;
import io.protone.traffic.service.TraMediaPlanPlaylistDateService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by lukaszozimek on 10/06/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class TraMediaPlanPlaylistDateServiceTest {
    @Autowired
    private TraMediaPlanPlaylistDateService traMediaPlanPlaylistDateService;

    @Autowired
    private CorNetworkRepository corNetworkRepository;


    @Autowired
    private CorChannelRepository corChannelRepository;

    @Autowired
    private TraMediaPlanRepository traPlaylistRepository;

    @Autowired
    private TraAdvertisementRepository traAdvertisementRepository;

    @Autowired
    private CrmAccountRepository crmAccountRepository;

    @Autowired
    private LibFileItemRepository libFileItemRepository;

    @Autowired
    private LibFileLibraryRepository libFileLibraryRepository;

    @Autowired
    private TraMediaPlanRepository traMediaPlanRepository;

    @Autowired
    private LibCloudObjectRepository libCloudObjectRepository;


    private CorNetwork corNetwork;

    private CorChannel corChannel;

    private PodamFactory factory;

    private LibFileLibrary libFileLibrary;


    private CrmAccount crmAccount;

    private LibFileItem libFileItem;

    private LibCloudObject libCloudObject;

    private TraMediaPlan traMediaPlan;


    @Before
    public void setup() {

        factory = new PodamFactoryImpl();
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corNetwork.setId(null);
        corNetwork = corNetworkRepository.saveAndFlush(corNetwork);

        corChannel = factory.manufacturePojo(CorChannel.class);
        corChannel.setId(null);
        corChannel.setShortcut("HHH");
        corChannel.network(corNetwork);
        corChannelRepository.saveAndFlush(corChannel);
        libCloudObject = factory.manufacturePojo(LibCloudObject.class);
        libCloudObject.setNetwork(corNetwork);

        libCloudObject = libCloudObjectRepository.saveAndFlush(libCloudObject);
        libFileLibrary = factory.manufacturePojo(LibFileLibrary.class);
        libFileLibrary.setShortcut("ppp");
        libFileLibrary.network(corNetwork);
        libFileLibrary.addChannel(corChannel);
        libFileLibrary = libFileLibraryRepository.saveAndFlush(libFileLibrary);


        crmAccount = factory.manufacturePojo(CrmAccount.class);
        crmAccount.network(corNetwork);
        crmAccount = crmAccountRepository.saveAndFlush(crmAccount);
        libFileItem = factory.manufacturePojo(LibFileItem.class);
        libFileItem.library(libFileLibrary);
        libFileItem.network(corNetwork);
        libFileItem.setCloudObject(libCloudObject);
        libFileItem = libFileItemRepository.saveAndFlush(libFileItem);

        traMediaPlan = factory.manufacturePojo(TraMediaPlan.class);
        traMediaPlan.setId(null);
        traMediaPlan.channel(corChannel);
        traMediaPlan.network(corNetwork);
        traMediaPlan.account(crmAccount);
        traMediaPlan.fileItem(libFileItem);

        traMediaPlan = traMediaPlanRepository.saveAndFlush(traMediaPlan);

    }

    @Test
    public void savePlaylist() throws Exception {
        //when
        TraMediaPlanPlaylistDate traPlaylist = factory.manufacturePojo(TraMediaPlanPlaylistDate.class);
        traPlaylist.setNetwork(corNetwork);
        traPlaylist.setChannel(corChannel);
        traPlaylist.setMediaPlan(traMediaPlan);
        Set<TraMediaPlanPlaylistDate> traMediaPlanPlaylistDateSet = Sets.newSet(traPlaylist);

        //then
        Set<TraMediaPlanPlaylistDate> traMediaPlanPlaylistDates = traMediaPlanPlaylistDateService.savePlaylist(traMediaPlanPlaylistDateSet);

        //assert
        assertNotNull(traMediaPlanPlaylistDates);
        assertEquals(1, traMediaPlanPlaylistDates.size());
        traMediaPlanPlaylistDates.stream().forEach(fetchedEntity -> {
            assertNotNull(fetchedEntity.getId());
            assertNotNull(fetchedEntity.getPlaylistDate().toString(), traPlaylist.getPlaylistDate().toString());
            assertEquals(traPlaylist.getNetwork(), fetchedEntity.getNetwork());

        });
    }

}
