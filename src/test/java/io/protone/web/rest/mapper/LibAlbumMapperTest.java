package io.protone.web.rest.mapper;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.LibAlbumPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.LibAlbum;
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
 * Created by lukaszozimek on 27/04/2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LibAlbumMapperTest {
    @Autowired
    private LibAlbumMapper customLibAlbumMapper;

    private LibAlbum libAlbum;

    private LibAlbumPT libAlbumPT;

    private List<LibAlbumPT> libAlbumPTS = new ArrayList<>();

    private List<LibAlbum> libAlbums = new ArrayList<>();

    private CorNetwork corNetwork;


    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        libAlbum = factory.manufacturePojo(LibAlbum.class);
        libAlbums.add(libAlbum);
        libAlbumPT = factory.manufacturePojo(LibAlbumPT.class);
        libAlbumPTS.add(libAlbumPT);
        corNetwork = factory.manufacturePojo(CorNetwork.class);
    }

    @Test
    public void DB2DTO() throws Exception {
        LibAlbumPT dto = customLibAlbumMapper.DB2DTO(libAlbum);


        assertNotNull(dto.getArtistId());
        assertNotNull(dto.getCoverId());
        assertNotNull(dto.getLabelId());
        assertNotNull(dto.getAlbumType());
        assertNotNull(dto.getDescription());
        assertNotNull(dto.getId());
        assertNotNull(dto.getName());
        assertNotNull(dto.getReleaseDate());


    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<LibAlbumPT> dtos = customLibAlbumMapper.DBs2DTOs(libAlbums);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {

            assertNotNull(dto.getArtistId());
            assertNotNull(dto.getCoverId());
            assertNotNull(dto.getLabelId());
            assertNotNull(dto.getAlbumType());
            assertNotNull(dto.getDescription());
            assertNotNull(dto.getId());
            assertNotNull(dto.getName());
            assertNotNull(dto.getReleaseDate());

        });
    }


    @Test
    public void DTO2DB() throws Exception {
        LibAlbum entity = customLibAlbumMapper.DTO2DB(libAlbumPT,corNetwork);

        assertNotNull(entity.getCover());
        assertNotNull(entity.getLabel());
        assertNotNull(entity.getArtist());
        assertNotNull(entity.getId());
        assertNotNull(entity.getAlbumType());
        assertNotNull(entity.getName());
        assertNotNull(entity.getReleaseDate());
        assertNotNull(entity.getDescription());
        assertNotNull(entity.getId());

        assertNotNull(entity.getNetwork());
    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<LibAlbum> entities = customLibAlbumMapper.DTOs2DBs(libAlbumPTS,corNetwork);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {

            assertNotNull(entity.getCover());
            assertNotNull(entity.getLabel());
            assertNotNull(entity.getArtist());
            assertNotNull(entity.getId());
            assertNotNull(entity.getAlbumType());
            assertNotNull(entity.getName());
            assertNotNull(entity.getReleaseDate());
            assertNotNull(entity.getDescription());
            assertNotNull(entity.getId());

            assertNotNull(entity.getNetwork());
        });
    }

}
