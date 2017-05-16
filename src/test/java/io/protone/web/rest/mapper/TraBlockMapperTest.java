package io.protone.web.rest.mapper;

import io.protone.ProtoneApp;
import io.protone.domain.CorNetwork;
import io.protone.domain.TraBlock;
import io.protone.web.rest.dto.traffic.TraBlockDTO;
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

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        traBlock = factory.manufacturePojo(TraBlock.class);
        traBlocks.add(traBlock);
        traBlockDTO = factory.manufacturePojo(TraBlockDTO.class);
        traBlockDTOS.add(traBlockDTO);
        corNetwork = factory.manufacturePojo(CorNetwork.class);
    }


    @Test
    public void DB2DTO() throws Exception {
        TraBlockDTO dto = traBlockMapper.DB2DTO(traBlock);

        assertNotNull(dto.getId());


    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<TraBlockDTO> dtos = traBlockMapper.DBs2DTOs(traBlocks);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getId());
        });
    }


    @Test
    public void DTO2DB() throws Exception {
        TraBlock entity = traBlockMapper.DTO2DB(traBlockDTO, corNetwork);
        assertNotNull(entity.getId());
        assertNotNull(entity.getNetwork());
    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<TraBlock> entities = traBlockMapper.DTOs2DBs(traBlockDTOS, corNetwork);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getNetwork());

        });
    }

}
