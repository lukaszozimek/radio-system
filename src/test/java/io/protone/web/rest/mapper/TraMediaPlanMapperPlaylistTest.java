package io.protone.web.rest.mapper;

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
        traMediaPlanMapperPlaylist = factory.manufacturePojo(TraMediaPlanMapperPlaylist.class);
        traMediaPlanPlaylists.add(traMediaPlanPlaylist);
        traPlaylist = factory.manufacturePojo(TraPlaylist.class);
        traPlaylists.add(traPlaylist);
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corChannel = factory.manufacturePojo(CorChannel.class);
    }

    @Test
    public void mediaPlanPlaylistToTraPlaylist() throws Exception {
    }

    @Test
    public void mediaPlanPlaylistToTraPlaylist1() throws Exception {
    }

    @Test
    public void traPlaylistToTraMediaPlanPlaylist() throws Exception {
    }

    @Test
    public void traPlaylistsToTraMediaPlanPlaylists() throws Exception {
    }


}
