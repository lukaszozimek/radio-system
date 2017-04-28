package io.protone.custom.service.mapper;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.LibMarkerPT;
import io.protone.custom.service.dto.TraAdvertisementPT;
import io.protone.custom.service.dto.TraOrderPT;
import io.protone.domain.LibMarker;
import io.protone.domain.TraAdvertisement;
import io.protone.domain.TraOrder;
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
 * Created by lukaszozimek on 28.04.2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CustomTRAAdvertismentMapperTest {


    @Autowired
    private CustomTRAAdvertismentMapper customTRAAdvertismentMapper;

    private TraAdvertisement traAdvertisement;

    private TraAdvertisementPT traAdvertisementPT;

    private List<TraAdvertisementPT> traAdvertisementPTS = new ArrayList<>();

    private List<TraAdvertisement> traAdvertisements = new ArrayList<>();

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        traAdvertisement = factory.manufacturePojo(TraAdvertisement.class);
        traAdvertisements.add(traAdvertisement);
        traAdvertisementPT = factory.manufacturePojo(TraAdvertisementPT.class);
        traAdvertisementPTS.add(traAdvertisementPT);

    }

    @Test
    public void DB2DTO() throws Exception {
        TraAdvertisementPT dto = customTRAAdvertismentMapper.DB2DTO(traAdvertisement);

        assertNotNull(dto.getId());
        assertNotNull(dto.getName());

    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<TraAdvertisementPT> dtos = customTRAAdvertismentMapper.DBs2DTOs(traAdvertisements);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {

            assertNotNull(dto.getId());
            assertNotNull(dto.getName());

        });
    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<TraAdvertisement> entities = customTRAAdvertismentMapper.DTOs2DBs(traAdvertisementPTS);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getName());

            assertNull(entity.getNetwork());


        });
    }

    @Test
    public void DTO2DB() throws Exception {
        TraAdvertisement entity = customTRAAdvertismentMapper.DTO2DB(traAdvertisementPT);


        assertNotNull(entity.getId());
        assertNotNull(entity.getName());

        assertNull(entity.getNetwork());
    }

    @Test
    public void traAdvertisementFromTraAdvertisementThinPT() throws Exception {
    }

    @Test
    public void traAdvertisementThinPTFromTraAdvertisement() throws Exception {
    }
}
