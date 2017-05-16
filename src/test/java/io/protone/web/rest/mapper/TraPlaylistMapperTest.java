package io.protone.web.rest.mapper;

import io.protone.ProtoneApp;
import io.protone.domain.CorNetwork;
import io.protone.domain.TraPlaylist;
import io.protone.web.rest.dto.traffic.TraPlaylistDTO;
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

import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 15/05/2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TraPlaylistMapperTest {

    @Autowired
    private TraPlaylistMapper traPlaylistMapper;

    private TraPlaylist traPlaylist;

    private TraPlaylistDTO traPlaylistDTO;

    private List<TraPlaylistDTO> traPlaylistDTOS = new ArrayList<>();

    private List<TraPlaylist> traPlaylists = new ArrayList<>();

    private CorNetwork corNetwork;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        traPlaylist = factory.manufacturePojo(TraPlaylist.class);
        traPlaylists.add(traPlaylist);
        traPlaylistDTO = factory.manufacturePojo(TraPlaylistDTO.class);
        traPlaylistDTOS.add(traPlaylistDTO);
        corNetwork = factory.manufacturePojo(CorNetwork.class);
    }


    @Test
    public void DB2DTO() throws Exception {
        TraPlaylistDTO dto = traPlaylistMapper.DB2DTO(traPlaylist);

        assertNotNull(dto.getId());


    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<TraPlaylistDTO> dtos = traPlaylistMapper.DBs2DTOs(traPlaylists);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getId());

        });
    }


    @Test
    public void DTO2DB() throws Exception {
        TraPlaylist entity = traPlaylistMapper.DTO2DB(traPlaylistDTO, corNetwork);
        assertNotNull(entity.getId());
        assertNotNull(entity.getNetwork());
    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<TraPlaylist> entities = traPlaylistMapper.DTOs2DBs(traPlaylistDTOS, corNetwork);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getNetwork());

        });
    }
}
