package io.protone.application.web.rest.mapper;


import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorNetwork;
import io.protone.library.api.dto.LibArtistDTO;
import io.protone.library.domain.LibArtist;
import io.protone.library.mapper.LibArtistMapper;
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
 * Created by lukaszozimek on 27/04/2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LibArtistMapperTest {
    @Autowired
    private LibArtistMapper libArtistMapper;

    private LibArtist libArtist;

    private LibArtistDTO libArtistDTO;

    private List<LibArtistDTO> libArtistDTOS = new ArrayList<>();

    private List<LibArtist> libArtists = new ArrayList<>();
    private CorNetwork corNetwork;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        libArtist = factory.manufacturePojo(LibArtist.class);
        libArtists.add(libArtist);
        libArtistDTO = factory.manufacturePojo(LibArtistDTO.class);
        libArtistDTOS.add(libArtistDTO);


        corNetwork = factory.manufacturePojo(CorNetwork.class);
    }

    @Test
    public void DB2DTO() throws Exception {
        LibArtistDTO dto = libArtistMapper.DB2DTO(libArtist);

        assertNotNull(dto.getId());
        assertNotNull(dto.getName());
        assertNotNull(dto.getType());
        assertNotNull(dto.getPublicUrl());

        assertNotNull(dto.getDescription());

    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<LibArtist> entities = libArtistMapper.DTOs2DBs(libArtistDTOS, corNetwork);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getDescription());
            assertNotNull(entity.getId());
            assertNotNull(entity.getName());
            assertNotNull(entity.getType());

            assertNotNull(entity.getNetwork());


        });
    }

    @Test
    public void DTO2DB() throws Exception {
        LibArtist entity = libArtistMapper.DTO2DB(libArtistDTO, corNetwork);


        assertNotNull(entity.getDescription());
        assertNotNull(entity.getId());
        assertNotNull(entity.getName());
        assertNotNull(entity.getType());

        assertNotNull(entity.getNetwork());
    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<LibArtistDTO> dtos = libArtistMapper.DBs2DTOs(libArtists);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {

            assertNotNull(dto.getId());
            assertNotNull(dto.getName());
            assertNotNull(dto.getType());
            assertNotNull(dto.getPublicUrl());
            assertNotNull(dto.getDescription());

        });
    }


}
