package io.protone.web.rest.mapper;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.LibTrackPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.LibTrack;
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
 * Created by lukaszozimek on 28.04.2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LibTrackMapperTest {

    @Autowired
    private LibTrackMapper customLibTrackMapperExt;

    private LibTrack libTrack;

    private LibTrackPT libTrackPT;

    private List<LibTrackPT> libTrackPTS = new ArrayList<>();

    private List<LibTrack> libTracks = new ArrayList<>();
    private CorNetwork corNetwork;


    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        libTrack = factory.manufacturePojo(LibTrack.class);
        libTracks.add(libTrack);
        libTrackPT = factory.manufacturePojo(LibTrackPT.class);
        libTrackPTS.add(libTrackPT);
        corNetwork = factory.manufacturePojo(CorNetwork.class);
    }


    @Test
    public void DB2DTO() throws Exception {
        LibTrackPT dto = customLibTrackMapperExt.DB2DTO(libTrack);

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
        List<LibTrackPT> dtos = customLibTrackMapperExt.DBs2DTOs(libTracks);

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
        LibTrack entity = customLibTrackMapperExt.DTO2DB(libTrackPT);

        assertNotNull(entity.getAlbum());
        assertNotNull(entity.getArtist());
        assertNotNull(entity.getDescription());
        assertNotNull(entity.getDiscNo());
        assertNotNull(entity.getId());
        assertNotNull(entity.getLength());
        assertNotNull(entity.getName());
        assertNotNull(entity.getTrackNo());
        assertNull(entity.getNetwork());
    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<LibTrack> entities = customLibTrackMapperExt.DTOs2DBs(libTrackPTS);

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
            assertNull(entity.getNetwork());
        });
    }

}
