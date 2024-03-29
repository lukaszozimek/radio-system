package io.protone.application.web.rest.mapper;


import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorNetwork;
import io.protone.library.api.dto.LibTrackDTO;
import io.protone.library.domain.LibTrack;
import io.protone.library.mapper.LibTrackMapper;
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

/**
 * Created by lukaszozimek on 28.04.2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LibTrackMapperTest {

    @Autowired
    private LibTrackMapper customLibTrackMapperExt;

    private LibTrack libTrack;

    private LibTrackDTO libTrackDTO;

    private List<LibTrackDTO> libTrackDTOS = new ArrayList<>();

    private List<LibTrack> libTracks = new ArrayList<>();
    private CorNetwork corNetwork;


    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        libTrack = factory.manufacturePojo(LibTrack.class);
        libTracks.add(libTrack);
        libTrackDTO = factory.manufacturePojo(LibTrackDTO.class);
        libTrackDTOS.add(libTrackDTO);
        corNetwork = factory.manufacturePojo(CorNetwork.class);
    }


    @Test
    public void DB2DTO() throws Exception {
        LibTrackDTO dto = customLibTrackMapperExt.DB2DTO(libTrack);

        assertNotNull(dto.getArtistId());
        assertNotNull(dto.getAlbumId());
        assertNotNull(dto.getId());
        assertNotNull(dto.getDiscNo());
        assertNotNull(dto.getTrackNo());
        assertNotNull(dto.getName());
        assertNotNull(dto.getLength());
        assertNotNull(dto.getDescription());
        assertNotNull(dto.getId());


    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<LibTrackDTO> dtos = customLibTrackMapperExt.DBs2DTOs(libTracks);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {

            assertNotNull(dto.getArtistId());
            assertNotNull(dto.getAlbumId());
            assertNotNull(dto.getId());
            assertNotNull(dto.getDiscNo());
            assertNotNull(dto.getTrackNo());
            assertNotNull(dto.getName());
            assertNotNull(dto.getLength());
            assertNotNull(dto.getDescription());
            assertNotNull(dto.getId());

        });
    }


    @Test
    public void DTO2DB() throws Exception {
        LibTrack entity = customLibTrackMapperExt.DTO2DB(libTrackDTO, corNetwork);

        assertNotNull(entity.getAlbum());
        assertNotNull(entity.getArtist());
        assertNotNull(entity.getDescription());
        assertNotNull(entity.getDiscNo());
        assertNotNull(entity.getId());
        assertNotNull(entity.getLength());
        assertNotNull(entity.getName());
        assertNotNull(entity.getTrackNo());
        assertNotNull(entity.getNetwork());
    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<LibTrack> entities = customLibTrackMapperExt.DTOs2DBs(libTrackDTOS, corNetwork);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getAlbum());
            assertNotNull(entity.getArtist());
            assertNotNull(entity.getDescription());
            assertNotNull(entity.getDiscNo());
            assertNotNull(entity.getId());
            assertNotNull(entity.getLength());
            assertNotNull(entity.getName());
            assertNotNull(entity.getTrackNo());
            assertNotNull(entity.getNetwork());
        });
    }

}
