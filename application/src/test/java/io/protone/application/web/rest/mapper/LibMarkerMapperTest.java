package io.protone.application.web.rest.mapper;


import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorNetwork;
import io.protone.library.api.dto.LibMarkerDTO;
import io.protone.library.domain.LibMarker;
import io.protone.library.domain.LibMediaItem;
import io.protone.library.mapper.LibMarkerMapper;
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
public class LibMarkerMapperTest {
    @Autowired
    private LibMarkerMapper customLibMarkerMapperExt;

    private LibMarker libMarker;

    private LibMarkerDTO libMarkerDTO;

    private List<LibMarkerDTO> libMarkerDTOS = new ArrayList<>();

    private List<LibMarker> libMarkers = new ArrayList<>();

    private CorNetwork corNetwork;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        libMarker = factory.manufacturePojo(LibMarker.class);
        libMarker.setMediaItem(factory.manufacturePojo(LibMediaItem.class));
        libMarker.getMediaItem().setId(1L);
        libMarker.setId(1L);
        libMarkers.add(libMarker);
        libMarkerDTO = factory.manufacturePojo(LibMarkerDTO.class);
        libMarkerDTOS.add(libMarkerDTO);

        corNetwork = factory.manufacturePojo(CorNetwork.class);
    }

    @Test
    public void DB2DTO() throws Exception {
        LibMarkerDTO dto = customLibMarkerMapperExt.DB2DTO(libMarker);

        assertNotNull(dto.getMediaItemId());
        assertNotNull(dto.getId());
        assertNotNull(dto.getMarkerType());
        assertNotNull(dto.getName());
        assertNotNull(dto.getStartTime());
    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<LibMarkerDTO> dtos = customLibMarkerMapperExt.DBs2DTOs(libMarkers);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getMediaItemId());
            assertNotNull(dto.getId());
            assertNotNull(dto.getMarkerType());
            assertNotNull(dto.getName());
            assertNotNull(dto.getStartTime());
        });
    }

    @Test
    public void DTO2DB() throws Exception {
        //TODO: add Network
        LibMarker entity = customLibMarkerMapperExt.DTO2DB(libMarkerDTO, corNetwork);

        assertNotNull(entity.getMediaItem());
        assertNotNull(entity.getId());
        assertNotNull(entity.getMarkerType());
        assertNotNull(entity.getName());
        assertNotNull(entity.getStartTime());


    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<LibMarker> entities = customLibMarkerMapperExt.DTOs2DBs(libMarkerDTOS, corNetwork);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getMediaItem());
            assertNotNull(entity.getId());
            assertNotNull(entity.getMarkerType());
            assertNotNull(entity.getName());
            assertNotNull(entity.getStartTime());
        });
    }
}
