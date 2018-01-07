package io.protone.application.web.rest.mapper;


import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorUser;
import io.protone.traffic.api.dto.TraBlockDTO;
import io.protone.traffic.api.dto.TraPlaylistDTO;
import io.protone.traffic.domain.TraBlock;
import io.protone.traffic.domain.TraPlaylist;
import io.protone.traffic.mapper.TraPlaylistMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
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

    private CorChannel corChannel;

    @Before
    public void initPojos() {
        PodamFactoryImpl factory = new PodamFactoryImpl();
        traPlaylist = factory.manufacturePojo(TraPlaylist.class);
        traPlaylist.setId(1L);

        TraBlock traBlock = factory.manufacturePojo(TraBlock.class);

        traPlaylist.addPlaylists(traBlock);
        traPlaylists.add(traPlaylist);
        traPlaylistDTO = factory.manufacturePojo(TraPlaylistDTO.class);
        traPlaylistDTO.setId(1L);
        traPlaylist.setCreatedBy(factory.manufacturePojo(CorUser.class));
        traPlaylist.setLastModifiedBy(factory.manufacturePojo(CorUser.class));

        TraBlockDTO traBlockDTO = factory.manufacturePojo(TraBlockDTO.class);
        traPlaylistDTO.addBlocksItem(traBlockDTO);
        traPlaylistDTOS.add(traPlaylistDTO);
        corChannel = factory.manufacturePojo(CorChannel.class);
        corChannel = factory.manufacturePojo(CorChannel.class);
    }


    @Test
    public void DB2DTO() throws Exception {
        TraPlaylistDTO dto = traPlaylistMapper.DB2DTO(traPlaylist);

        assertNotNull(dto.getId());
        assertNotNull(dto.getBlocks());
        assertNotEquals(0, dto.getBlocks().size());
        assertNotNull(dto.getCreatedBy());
        assertNotNull(dto.getCreatedDate());
        assertNotNull(dto.getLastModifiedBy());
        assertNotNull(dto.getLastModifiedDate());
        assertNotNull(dto.getPlaylistDate());


    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<TraPlaylistDTO> dtos = traPlaylistMapper.DBs2DTOs(traPlaylists);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getId());
            assertNotNull(dto.getBlocks());
            assertNotEquals(0, dto.getBlocks().size());
            assertNotNull(dto.getPlaylistDate());
            assertNotNull(dto.getCreatedBy());
            assertNotNull(dto.getCreatedDate());
            assertNotNull(dto.getLastModifiedBy());
            assertNotNull(dto.getLastModifiedDate());
        });
    }


    @Test
    public void DTO2DB() throws Exception {
        TraPlaylist entity = traPlaylistMapper.DTO2DB(traPlaylistDTO, corChannel);
        assertNotNull(entity.getId());
        assertNotNull(entity.getPlaylists());
        assertNotEquals(0, entity.getPlaylists().size());
        assertNotNull(entity.getPlaylistDate());
        assertNotNull(entity.getChannel());
    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<TraPlaylist> entities = traPlaylistMapper.DTOs2DBs(traPlaylistDTOS, corChannel);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getPlaylists());
            assertNotEquals(0, entity.getPlaylists().size());
            assertNotNull(entity.getPlaylistDate());
            assertNotNull(entity.getChannel());
        });
    }
}
