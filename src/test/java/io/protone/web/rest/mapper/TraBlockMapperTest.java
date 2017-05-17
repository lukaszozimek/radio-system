package io.protone.web.rest.mapper;

import io.protone.ProtoneApp;
import io.protone.domain.CorChannel;
import io.protone.domain.CorNetwork;
import io.protone.domain.TraBlock;
import io.protone.domain.TraEmission;
import io.protone.web.rest.dto.traffic.TraBlockDTO;
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
public class TraBlockMapperTest {

    @Autowired
    private TraBlockMapper traBlockMapper;

    private TraBlock traBlock;

    private TraBlockDTO traBlockDTO;

    private List<TraBlockDTO> traBlockDTOS = new ArrayList<>();

    private List<TraBlock> traBlocks = new ArrayList<>();

    private CorNetwork corNetwork;

    private CorChannel corChannel;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        traBlock = factory.manufacturePojo(TraBlock.class);
        traBlock.setId(1L);

        TraEmission traEmission = factory.manufacturePojo(TraEmission.class);
        traBlock.addEmissions(traEmission);
        traBlocks.add(traBlock);
        traBlockDTO = factory.manufacturePojo(TraBlockDTO.class);
        traBlockDTO.setId(1L);
        TraEmissionDTO traEmissionDTO = factory.manufacturePojo(TraEmissionDTO.class);
        traBlockDTO.addEmissionsItem(traEmissionDTO);
        traBlockDTOS.add(traBlockDTO);
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corChannel = factory.manufacturePojo(CorChannel.class);
    }


    @Test
    public void DB2DTO() throws Exception {
        TraBlockDTO dto = traBlockMapper.DB2DTO(traBlock);

        assertNotNull(dto.getId());
        assertNotNull(dto.getName());
        assertNotNull(dto.getStartBlock());
        assertNotNull(dto.getStopBlock());
        assertNotNull(dto.getEmissions());
        assertEquals(1, dto.getEmissions().size());


    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<TraBlockDTO> dtos = traBlockMapper.DBs2DTOs(traBlocks);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {

            assertNotNull(dto.getId());
            assertNotNull(dto.getName());
            assertNotNull(dto.getStartBlock());
            assertNotNull(dto.getStopBlock());
            assertNotNull(dto.getEmissions());
            assertEquals(1, dto.getEmissions().size());

        });
    }


    @Test
    public void DTO2DB() throws Exception {
        TraBlock entity = traBlockMapper.DTO2DB(traBlockDTO, corNetwork, corChannel);
        assertNotNull(entity.getId());
        assertNotNull(entity.getName());
        assertNotNull(entity.getEmissions());
        assertEquals(1, entity.getEmissions().size());
        assertNotNull(entity.getChannel());
        assertNotNull(entity.getNetwork());
    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<TraBlock> entities = traBlockMapper.DTOs2DBs(traBlockDTOS, corNetwork, corChannel);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getName());
            assertNotNull(entity.getEmissions());

            assertEquals(1, entity.getEmissions().size());
            assertNotNull(entity.getChannel());
            assertNotNull(entity.getNetwork());

        });
    }

}
