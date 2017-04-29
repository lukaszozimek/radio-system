package io.protone.web.rest.mapper;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.LibArtistPT;
import io.protone.domain.LibArtist;
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
import static org.junit.Assert.assertNull;

/**
 * Created by lukaszozimek on 27/04/2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LibArtistMapperTest {
    @Autowired
    private LibArtistMapper libArtistMapper;

    private LibArtist libArtist;

    private LibArtistPT libArtistPT;

    private List<LibArtistPT> libArtistPTS = new ArrayList<>();

    private List<LibArtist> libArtists = new ArrayList<>();

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        libArtist = factory.manufacturePojo(LibArtist.class);
        libArtists.add(libArtist);
        libArtistPT = factory.manufacturePojo(LibArtistPT.class);
        libArtistPTS.add(libArtistPT);

    }

    @Test
    public void DB2DTO() throws Exception {
        LibArtistPT dto = libArtistMapper.DB2DTO(libArtist);

        assertNotNull(dto.getId());
        assertNotNull(dto.getName());
        assertNotNull(dto.getType());
        assertNotNull(dto.getDescription());

    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<LibArtist> entities = libArtistMapper.DTOs2DBs(libArtistPTS);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getDescription());
            assertNotNull(entity.getId());
            assertNotNull(entity.getName());
            assertNotNull(entity.getType());

            assertNull(entity.getNetwork());


        });
    }

    @Test
    public void DTO2DB() throws Exception {
        LibArtist entity = libArtistMapper.DTO2DB(libArtistPT);


        assertNotNull(entity.getDescription());
        assertNotNull(entity.getId());
        assertNotNull(entity.getName());
        assertNotNull(entity.getType());

        assertNull(entity.getNetwork());
    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<LibArtistPT> dtos = libArtistMapper.DBs2DTOs(libArtists);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {

            assertNotNull(dto.getId());
            assertNotNull(dto.getName());
            assertNotNull(dto.getType());
            assertNotNull(dto.getDescription());

        });
    }

    @Test
    public void mapLibArtistPT_TypeEnum() throws Exception {
    }

    @Test
    public void mapLibArtistTypeEnum() throws Exception {
    }

}
