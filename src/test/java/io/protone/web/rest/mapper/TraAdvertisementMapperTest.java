package io.protone.web.rest.mapper;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.TraAdvertisementPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.TraAdvertisement;
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
public class TraAdvertisementMapperTest {


    @Autowired
    private TraAdvertisementMapper customTRAAdvertisementMapper;

    private TraAdvertisement traAdvertisement;

    private TraAdvertisementPT traAdvertisementPT;

    private List<TraAdvertisementPT> traAdvertisementPTS = new ArrayList<>();

    private List<TraAdvertisement> traAdvertisements = new ArrayList<>();

    private CorNetwork corNetwork;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        traAdvertisement = factory.manufacturePojo(TraAdvertisement.class);
        traAdvertisements.add(traAdvertisement);
        traAdvertisementPT = factory.manufacturePojo(TraAdvertisementPT.class);
        traAdvertisementPTS.add(traAdvertisementPT);
        corNetwork = factory.manufacturePojo(CorNetwork.class);

    }

    @Test
    public void DB2DTO() throws Exception {
        TraAdvertisementPT dto = customTRAAdvertisementMapper.DB2DTO(traAdvertisement);

        assertNotNull(dto.getId());
        assertNotNull(dto.getMediaItemId());
        assertNotNull(dto.getIndustryId());
        assertNotNull(dto.getTypeId());
        assertNotNull(dto.getCustomerId());
        assertNotNull(dto.getName());
        assertNotNull(dto.getDescription());
    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<TraAdvertisementPT> dtos = customTRAAdvertisementMapper.DBs2DTOs(traAdvertisements);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {

            assertNotNull(dto.getId());
            assertNotNull(dto.getMediaItemId());
            assertNotNull(dto.getIndustryId());
            assertNotNull(dto.getTypeId());
            assertNotNull(dto.getCustomerId());
            assertNotNull(dto.getName());
            assertNotNull(dto.getDescription());
        });
    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<TraAdvertisement> entities = customTRAAdvertisementMapper.DTOs2DBs(traAdvertisementPTS,corNetwork);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {

            assertNotNull(entity.getId());
            assertNotNull(entity.getCustomer());
            assertNotNull(entity.getMediaItem());
            assertNotNull(entity.getType());
            assertNotNull(entity.getDescription());
            assertNotNull(entity.getId());
            assertNotNull(entity.getName());

            assertNull(entity.getNetwork());
        });
    }

    @Test
    public void DTO2DB() throws Exception {
        TraAdvertisement entity = customTRAAdvertisementMapper.DTO2DB(traAdvertisementPT,corNetwork);


        assertNotNull(entity.getId());
        assertNotNull(entity.getCustomer());
        assertNotNull(entity.getMediaItem());
        assertNotNull(entity.getType());
        assertNotNull(entity.getDescription());
        assertNotNull(entity.getId());
        assertNotNull(entity.getName());

        assertNull(entity.getNetwork());
    }
}
