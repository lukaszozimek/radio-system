package io.protone.application.service.traffic;


import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.repository.CorChannelRepository;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.crm.domain.CrmAccount;
import io.protone.crm.repostiory.CrmAccountRepository;
import io.protone.library.domain.LibLibrary;
import io.protone.library.domain.LibMediaItem;
import io.protone.library.domain.enumeration.LibItemTypeEnum;
import io.protone.library.repository.LibLibraryRepository;
import io.protone.library.repository.LibMediaItemRepository;
import io.protone.traffic.domain.TraMediaPlan;
import io.protone.traffic.domain.TraMediaPlanPlaylist;
import io.protone.traffic.repository.TraAdvertisementRepository;
import io.protone.traffic.repository.TraMediaPlanRepository;
import io.protone.traffic.service.TraMediaPlanPlaylistService;
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
public class TraMediaPlanPlaylistServiceTest {
    @Autowired
    private TraMediaPlanPlaylistService traMediaPlanPlaylistService;

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
    private LibMediaItemRepository libMediaItemRepository;

    @Autowired
    private LibLibraryRepository libLibraryRepository;

    @Autowired
    private TraMediaPlanRepository traMediaPlanRepository;


    private CorNetwork corNetwork;

    private CorChannel corChannel;

    private PodamFactory factory;

    private LibLibrary libLibrary;


    private CrmAccount crmAccount;

    private LibMediaItem libMediaItem;

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
        libLibrary = factory.manufacturePojo(LibLibrary.class);
        libLibrary.setShortcut("ppp");
        libLibrary.network(corNetwork);
        libLibrary.addChannel(corChannel);
        libLibrary = libLibraryRepository.saveAndFlush(libLibrary);


        crmAccount = factory.manufacturePojo(CrmAccount.class);
        crmAccount.network(corNetwork);
        crmAccount = crmAccountRepository.saveAndFlush(crmAccount);
        libMediaItem = factory.manufacturePojo(LibMediaItem.class);
        libMediaItem.setItemType(LibItemTypeEnum.IT_DOCUMENT);
        libMediaItem.library(libLibrary);
        libMediaItem.network(corNetwork);
        libMediaItem = libMediaItemRepository.saveAndFlush(libMediaItem);

        traMediaPlan = factory.manufacturePojo(TraMediaPlan.class);
        traMediaPlan.setId(null);
        traMediaPlan.channel(corChannel);
        traMediaPlan.network(corNetwork);
        traMediaPlan.account(crmAccount);
        traMediaPlan.mediaItem(libMediaItem);

        traMediaPlan = traMediaPlanRepository.saveAndFlush(traMediaPlan);

    }

    @Test
    public void savePlaylist() throws Exception {
        //when
        TraMediaPlanPlaylist traPlaylist = factory.manufacturePojo(TraMediaPlanPlaylist.class);
        traPlaylist.setNetwork(corNetwork);
        traPlaylist.setChannel(corChannel);
        traPlaylist.setMediaPlan(traMediaPlan);
        Set<TraMediaPlanPlaylist> traMediaPlanPlaylistSet = Sets.newSet(traPlaylist);

        //then
        Set<TraMediaPlanPlaylist> traMediaPlanPlaylists = traMediaPlanPlaylistService.savePlaylist(traMediaPlanPlaylistSet);

        //assert
        assertNotNull(traMediaPlanPlaylists);
        assertEquals(1, traMediaPlanPlaylists.size());
        traMediaPlanPlaylists.stream().forEach(fetchedEntity -> {
            assertNotNull(fetchedEntity.getId());
            assertNotNull(fetchedEntity.getPlaylistDate().toString(), traPlaylist.getPlaylistDate().toString());
            assertEquals(traPlaylist.getNetwork(), fetchedEntity.getNetwork());

        });
    }

}
