package io.protone.application.web.rest.mapper;

import io.protone.application.ProtoneApp;
import io.protone.scheduler.api.dto.SchPlaylistDTO;
import io.protone.scheduler.domain.SchPlaylist;
import io.protone.scheduler.mapper.SchPlaylistMapper;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class SchPlaylistMapperTest {

    @Autowired
    private SchPlaylistMapper playlistMapper;

    private SchPlaylist playlist;

    private SchPlaylistDTO playlistDTO;

    private List<SchPlaylist> playlists = new ArrayList<>();

    private List<SchPlaylistDTO> playlistDTOs = new ArrayList<>();
    
    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        playlist = factory.manufacturePojo(SchPlaylist.class);
        playlists.add(playlist);
        playlistDTO = factory.manufacturePojo(SchPlaylistDTO.class);
        playlistDTOs.add(playlistDTO);
    }

    @Test
    public void toDTO() throws Exception {
        SchPlaylistDTO dto = playlistMapper.toDto(playlist);

        assertNotNull(dto.getDate());
        //assertNotNull(dto.getEmissions()); //TODO: test emission instances mapping
    }

    @Test
    public void toDTOs() throws Exception {
        List<SchPlaylistDTO> dtos = playlistMapper.toDto(playlists);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getDate());
            //assertNotNull(dto.getEmissions()); //TODO: test emission instances mapping
        });
    }

    @Test
    public void toEntity() throws Exception {
        SchPlaylist entity = playlistMapper.toEntity(playlistDTO);

        assertNotNull(entity.getDate());
        //assertNotNull(entity.getEmissions()); //TODO: test emission instances mapping
    }

    @Test
    public void toEntities() throws Exception {
        List<SchPlaylist> entities = playlistMapper.toEntity(playlistDTOs);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getDate());
            //assertNotNull(entity.getEmissions()); //TODO: test emission instances mapping
        });
    }

}