package io.protone.custom.service.mapper;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.LibMarkerPT;
import io.protone.domain.CorPropertyKey;
import io.protone.domain.LibMarker;
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
public class CustomLibMarkerMapperExtTest {
    @Autowired
    private CustomLibMarkerMapperExt customLibMarkerMapperExt;

    private LibMarker libMarker;

    private LibMarkerPT libMarkerPT;

    private List<LibMarkerPT> libMarkerPTS = new ArrayList<>();

    private List<LibMarker> libMarkers = new ArrayList<>();

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        libMarker = factory.manufacturePojo(LibMarker.class);
        libMarkers.add(libMarker);
        libMarkerPT = factory.manufacturePojo(LibMarkerPT.class);
        libMarkerPTS.add(libMarkerPT);

    }

    @Test
    public void DB2DTO() throws Exception {
        LibMarkerPT dto = customLibMarkerMapperExt.DB2DTO(libMarker);

        assertNotNull(dto.getId());
        assertNotNull(dto.getName());

    }

    @Test
    public void DBs2DTOs() throws Exception {
    }

    @Test
    public void DTO2DB() throws Exception {
        LibMarker entity = customLibMarkerMapperExt.DTO2DB(libMarkerPT);

        assertNotNull(entity.getId());

    }

    @Test
    public void DTOs2DBs() throws Exception {
    }

    @Test
    public void mapLibMediaItem() throws Exception {
    }

    @Test
    public void mapLibMarkerPT_MarkerTypeEnum() throws Exception {
    }

    @Test
    public void mapLibMarkerTypeEnum() throws Exception {
    }

}
