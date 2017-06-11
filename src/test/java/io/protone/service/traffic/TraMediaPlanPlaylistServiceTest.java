package io.protone.service.traffic;

import io.protone.ProtoneApp;
import io.protone.domain.CorChannel;
import io.protone.domain.CorNetwork;
import io.protone.domain.TraMediaPlanPlaylist;
import io.protone.repository.cor.CorChannelRepository;
import io.protone.repository.cor.CorNetworkRepository;
import io.protone.repository.crm.CrmAccountRepository;
import io.protone.repository.library.LibMediaItemRepository;
import io.protone.repository.traffic.TraAdvertisementRepository;
import io.protone.repository.traffic.TraMediaPlanRepository;
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

    private CorNetwork corNetwork;

    private CorChannel corChannel;

    private PodamFactory factory;

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
    }

    @Test
    public void savePlaylist() throws Exception {
        //when
        TraMediaPlanPlaylist traPlaylist = factory.manufacturePojo(TraMediaPlanPlaylist.class);
        Set<TraMediaPlanPlaylist> traMediaPlanPlaylistSet = Sets.newSet(traPlaylist);
        traPlaylist.setNetwork(corNetwork);
        traPlaylist.setChannel(corChannel);
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
