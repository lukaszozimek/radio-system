package io.protone.application.web.rest.mapper;

import io.protone.application.ProtoneApp;
import io.protone.scheduler.api.dto.SchBlockDTO;
import io.protone.scheduler.api.dto.SchClockDTO;
import io.protone.scheduler.api.dto.SchEmissionDTO;
import io.protone.scheduler.domain.SchBlock;
import io.protone.scheduler.domain.SchClock;
import io.protone.scheduler.domain.SchEmission;
import io.protone.scheduler.mapper.SchClockMapper;
import org.junit.After;
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

@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class SchClockMapperTest {

    private static final PodamFactory factory = new PodamFactoryImpl();

    @Autowired
    private SchClockMapper clockMapper;

    private SchClock clock;

    private SchClockDTO clockDTO;

    private List<SchClock> clocks = new ArrayList<>();

    private List<SchClockDTO> clockDTOs = new ArrayList<>();

    @Before
    public void initPojos() {

        // Fill entity
        clock = factory.manufacturePojo(SchClock.class);

        clock.addBlock(sampleBlock()); //Block 1 @ clock
        clock.addBlock(sampleBlock()); //Block 2 @ clock
        clock.addBlock(sampleBlock()); //Block 3 @ clock

        clock.addEmission(factory.manufacturePojo(SchEmission.class)); //Emission 1 @ clock
        clock.addEmission(factory.manufacturePojo(SchEmission.class)); //Emission 2 @ clock
        clock.addEmission(factory.manufacturePojo(SchEmission.class)); //Emission 3 @ clock

        clocks.add(clock);

        // Fill DTO
        clockDTO = factory.manufacturePojo(SchClockDTO.class);

        clockDTO.addBlocksItem(sampleBlockDTO()); //Block 1 @ clock
        clockDTO.addBlocksItem(sampleBlockDTO()); //Block 2 @ clock
        clockDTO.addBlocksItem(sampleBlockDTO()); //Block 3 @ clock

        clockDTO.addEmissionsItem(factory.manufacturePojo(SchEmissionDTO.class)); //Emission 1 @ clock
        clockDTO.addEmissionsItem(factory.manufacturePojo(SchEmissionDTO.class)); //Emission 2 @ clock
        clockDTO.addEmissionsItem(factory.manufacturePojo(SchEmissionDTO.class)); //Emission 3 @ clock

        clockDTOs.add(clockDTO);
    }

    private SchBlockDTO sampleBlockDTO() {

        SchBlockDTO sampleBlock = factory.manufacturePojo(SchBlockDTO.class);

        sampleBlock.addEmissionsItem(factory.manufacturePojo(SchEmissionDTO.class)); //Emission 1 @ sampleBlock
        sampleBlock.addEmissionsItem(factory.manufacturePojo(SchEmissionDTO.class)); //Emission 2 @ sampleBlock
        sampleBlock.addEmissionsItem(factory.manufacturePojo(SchEmissionDTO.class)); //Emission 3 @ sampleBlock

        return sampleBlock;

    }

    private SchBlock sampleBlock() {

        SchBlock sampleBlock = factory.manufacturePojo(SchBlock.class);

        sampleBlock.addEmission(factory.manufacturePojo(SchEmission.class)); //Emission 1 @ sampleBlock
        sampleBlock.addEmission(factory.manufacturePojo(SchEmission.class)); //Emission 2 @ sampleBlock
        sampleBlock.addEmission(factory.manufacturePojo(SchEmission.class)); //Emission 3 @ sampleBlock

        return sampleBlock;
    }

    @Test
    public void toDTO() throws Exception {
        SchClockDTO dto = clockMapper.toDto(clock);

        assertEquals(dto.getBlocks().size(), 3);
        assertEquals(dto.getEmissions().size(), 3);
        assertNotNull(dto.getName());
        assertNotNull(dto.getName());
        //assertNotNull(dto.getQueueParams());
        //assertNotNull(dto.getTimeParams());
    }

    @Test
    public void toDTOs() throws Exception {
        List<SchClockDTO> dtos = clockMapper.toDto(clocks);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertEquals(dto.getBlocks().size(), 3);
            assertEquals(dto.getEmissions().size(), 3);
            assertNotNull(dto.getName());
            assertNotNull(dto.getName());
            //assertNotNull(dto.getQueueParams());
            //assertNotNull(dto.getTimeParams());
        });
    }

    @Test
    public void toEntity() throws Exception {
        SchClock entity = clockMapper.toEntity(clockDTO);

        assertEquals(entity.getBlocks().size(), 3);
        assertEquals(entity.getEmissions().size(), 3);
        assertNotNull(entity.getName());
        assertNotNull(entity.getName());
        assertNotNull(entity.getQueueParams());
        assertNotNull(entity.getTimeParams());
    }

    @Test
    public void toEntities() throws Exception {
        List<SchClock> entities = clockMapper.toEntity(clockDTOs);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertEquals(entity.getBlocks().size(), 3);
            assertEquals(entity.getEmissions().size(), 3);
            assertNotNull(entity.getName());
            assertNotNull(entity.getName());
            assertNotNull(entity.getQueueParams());
            assertNotNull(entity.getTimeParams());
        });
    }

}