package io.protone.web.rest.mapper;

import com.google.common.collect.Sets;
import io.protone.ProtoneApp;
import io.protone.domain.CorChannel;
import io.protone.domain.CorNetwork;
import io.protone.domain.TraMediaPlanPlaylist;
import io.protone.domain.TraPlaylist;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertNotNull;

/**
 * Created by lukaszozimek on 10/06/2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TraMediaPlanMapperPlaylistTest {
    @Autowired
    private TraMediaPlanMapperPlaylist traMediaPlanMapperPlaylist;

    private TraPlaylist traPlaylist;

    private TraMediaPlanPlaylist traMediaPlanPlaylist;

    private List<TraPlaylist> traPlaylists = new ArrayList<>();

    private List<TraMediaPlanPlaylist> traMediaPlanPlaylists = new ArrayList<>();

    private CorNetwork corNetwork;

    private CorChannel corChannel;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();

        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corChannel = factory.manufacturePojo(CorChannel.class);
        traMediaPlanPlaylist = factory.manufacturePojo(TraMediaPlanPlaylist.class);
        traMediaPlanPlaylist.setChannel(corChannel);
        traMediaPlanPlaylist.setNetwork(corNetwork);
        traMediaPlanPlaylists.add(traMediaPlanPlaylist);
        traPlaylist = factory.manufacturePojo(TraPlaylist.class);
        traPlaylist.setId(1L);
        traPlaylist.setChannel(corChannel);
        traPlaylist.setNetwork(corNetwork);
        traPlaylists.add(traPlaylist);
    }

    @Test
    public void mediaPlanPlaylistToTraPlaylist() throws Exception {

        TraPlaylist traPlaylist = traMediaPlanMapperPlaylist.mediaPlanPlaylistToTraPlaylist(traMediaPlanPlaylist);
        assertNotNull(traPlaylist.getId());
        assertNotNull(traPlaylist.getPlaylistDate());
        assertNotNull(traPlaylist.getNetwork());
        assertNotNull(traPlaylist.getChannel());
        assertNotNull(traPlaylist.getPlaylists());
    }

    @Test
    public void mediaPlanPlaylistsToTraPlaylists() throws Exception {
        List<TraPlaylist> traPlaylists = traMediaPlanMapperPlaylist.mediaPlanPlaylistsToTraPlaylists(Sets.newHashSet(traMediaPlanPlaylist));
        traPlaylists.stream().forEach(traPlaylist1 -> {
            assertNotNull(traPlaylist.getId());
            assertNotNull(traPlaylist.getPlaylistDate());
            assertNotNull(traPlaylist.getNetwork());
            assertNotNull(traPlaylist.getChannel());
            assertNotNull(traPlaylist.getPlaylists());
        });
    }

    @Test
    public void traPlaylistToTraMediaPlanPlaylist() throws Exception {
        TraMediaPlanPlaylist traMediaPlanPlaylist = traMediaPlanMapperPlaylist.traPlaylistToTraMediaPlanPlaylist(traPlaylist);

        assertNotNull(traMediaPlanPlaylist.getId());
        assertNotNull(traMediaPlanPlaylist.getPlaylistDate());
        assertNotNull(traMediaPlanPlaylist.getChannel());
        assertNotNull(traMediaPlanPlaylist.getNetwork());
        assertNotNull(traMediaPlanPlaylist.getPlaylists());
    }

    @Test
    public void traPlaylistsToTraMediaPlanPlaylists() throws Exception {
        Set<TraMediaPlanPlaylist> traMediaPlanPlaylists = traMediaPlanMapperPlaylist.traPlaylistsToTraMediaPlanPlaylists(traPlaylists);
        traMediaPlanPlaylists.stream().forEach(traMediaPlanPlaylist -> {
            assertNotNull(traMediaPlanPlaylist.getId());
            assertNotNull(traMediaPlanPlaylist.getPlaylistDate());
            assertNotNull(traMediaPlanPlaylist.getChannel());
            assertNotNull(traMediaPlanPlaylist.getNetwork());
            assertNotNull(traMediaPlanPlaylist.getPlaylists());
        });
    }


}
