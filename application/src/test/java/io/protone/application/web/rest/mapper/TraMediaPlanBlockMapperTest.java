package io.protone.application.web.rest.mapper;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.traffic.api.dto.TraMediaPlanBlockDTO;
import io.protone.traffic.domain.TraMediaPlanBlock;
import io.protone.traffic.mapper.TraMediaPlanBlockMapper;
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
 * Created by lukaszozimek on 10/06/2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TraMediaPlanBlockMapperTest {
    @Autowired
    private TraMediaPlanBlockMapper traMediaPlanBlockMapper;

    private TraMediaPlanBlock mediaPlanBlock;

    private TraMediaPlanBlockDTO traMediaPlanBlockDTO;

    private List<TraMediaPlanBlock> traMediaPlanBlocks = new ArrayList<>();

    private List<TraMediaPlanBlockDTO> traMediaPlanBlockDTOS = new ArrayList<>();

    private CorChannel corChannel;


    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();

        corChannel = factory.manufacturePojo(CorChannel.class);
        corChannel = factory.manufacturePojo(CorChannel.class);
        traMediaPlanBlockDTO = factory.manufacturePojo(TraMediaPlanBlockDTO.class);
        traMediaPlanBlockDTOS.add(traMediaPlanBlockDTO);
        mediaPlanBlock = factory.manufacturePojo(TraMediaPlanBlock.class);
        mediaPlanBlock.setId(1L);
        mediaPlanBlock.setChannel(corChannel);
        traMediaPlanBlocks.add(mediaPlanBlock);
    }

    @Test
    public void DB2DTO() throws Exception {
        TraMediaPlanBlockDTO dto = traMediaPlanBlockMapper.DB2DTO(mediaPlanBlock);

        assertNotNull(dto.getId());
        assertNotNull(dto.getName());
        assertNotNull(dto.getStartBlock());
        assertNotNull(dto.getStopBlock());
        assertNotNull(dto.getSequence());

    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<TraMediaPlanBlockDTO> dtos = traMediaPlanBlockMapper.DBs2DTOs(traMediaPlanBlocks);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {

            assertNotNull(dto.getId());
            assertNotNull(dto.getName());
            assertNotNull(dto.getStartBlock());
            assertNotNull(dto.getStopBlock());
            assertNotNull(dto.getSequence());

        });
    }


    @Test
    public void DTO2DB() throws Exception {
        TraMediaPlanBlock entity = traMediaPlanBlockMapper.DTO2DB(traMediaPlanBlockDTO, corChannel);
        assertNotNull(entity.getId());
        assertNotNull(entity.getName());
        assertNotNull(entity.getChannel());
        assertNotNull(entity.getSequence());
    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<TraMediaPlanBlock> entities = traMediaPlanBlockMapper.DTOs2DBs(traMediaPlanBlockDTOS, corChannel);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getName());
            assertNotNull(entity.getSequence());

            assertNotNull(entity.getChannel());

        });
    }


}
