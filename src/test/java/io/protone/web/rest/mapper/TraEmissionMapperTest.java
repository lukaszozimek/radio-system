package io.protone.web.rest.mapper;

import io.protone.ProtoneApp;
import io.protone.domain.CorNetwork;
import io.protone.domain.TraEmission;
import io.protone.web.rest.dto.traffic.TraEmissionDTO;
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
 * Created by lukaszozimek on 16/05/2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TraEmissionMapperTest {
    @Autowired
    private TraEmissionMapper traEmissionMapper;

    private TraEmission traEmission;

    private TraEmissionDTO traEmissionDTO;

    private List<TraEmissionDTO> traEmissionDTOS = new ArrayList<>();

    private List<TraEmission> traEmissions = new ArrayList<>();

    private CorNetwork corNetwork;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        traEmission = factory.manufacturePojo(TraEmission.class);
        traEmissions.add(traEmission);
        traEmissionDTO = factory.manufacturePojo(TraEmissionDTO.class);
        traEmissionDTOS.add(traEmissionDTO);
        corNetwork = factory.manufacturePojo(CorNetwork.class);
    }


    @Test
    public void DB2DTO() throws Exception {
        TraEmissionDTO dto = traEmissionMapper.DB2DTO(traEmission);

        assertNotNull(dto.getId());


    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<TraEmissionDTO> dtos = traEmissionMapper.DBs2DTOs(traEmissions);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getId());
        });
    }


    @Test
    public void DTO2DB() throws Exception {
        TraEmission entity = traEmissionMapper.DTO2DB(traEmissionDTO, corNetwork);

        assertNotNull(entity.getId());
        assertNotNull(entity.getNetwork());
    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<TraEmission> entities = traEmissionMapper.DTOs2DBs(traEmissionDTOS, corNetwork);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {

            assertNotNull(entity.getId());
            assertNotNull(entity.getNetwork());

        });
    }

}
