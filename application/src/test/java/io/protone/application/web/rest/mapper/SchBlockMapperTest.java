package io.protone.application.web.rest.mapper;

import io.protone.application.ProtoneApp;
import io.protone.scheduler.api.dto.SchBlockDTO;
import io.protone.scheduler.api.dto.SchEmissionDTO;
import io.protone.scheduler.domain.SchBlock;
import io.protone.scheduler.domain.SchEmission;
import io.protone.scheduler.mapper.SchBlockMapper;
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
public class SchBlockMapperTest {

    @Autowired
    private SchBlockMapper blockMapper;

    private SchBlock block;

    private SchBlockDTO blockDTO;

    private List<SchBlock> blocks = new ArrayList<>();

    private List<SchBlockDTO> blockDTOs = new ArrayList<>();

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();

        // Fill entity instance
        block = factory.manufacturePojo(SchBlock.class);

        SchBlock childBlock = factory.manufacturePojo(SchBlock.class);
        childBlock.addEmission(factory.manufacturePojo(SchEmission.class)); //Emission 1 @ childBlock
        block.addBlock(childBlock);

        block.addEmission(factory.manufacturePojo(SchEmission.class)); //Emission 1 @ rootBlock
        block.addEmission(factory.manufacturePojo(SchEmission.class)); //Emission 2 @ rootBlock
        block.addEmission(factory.manufacturePojo(SchEmission.class)); //Emission 3 @ rootBlock

        blocks.add(block);

        //Fill DTO instance
        blockDTO = factory.manufacturePojo(SchBlockDTO.class);

        SchBlockDTO childBlockDTO = factory.manufacturePojo(SchBlockDTO.class);
        childBlockDTO.addEmissionsItem(factory.manufacturePojo(SchEmissionDTO.class)); //Emission 1 @ childBlock
        blockDTO.addBlocksItem(childBlockDTO);

        blockDTO.addEmissionsItem(factory.manufacturePojo(SchEmissionDTO.class)); //Emission 1 @ rootBlock
        blockDTO.addEmissionsItem(factory.manufacturePojo(SchEmissionDTO.class)); //Emission 2 @ rootBlock
        blockDTO.addEmissionsItem(factory.manufacturePojo(SchEmissionDTO.class)); //Emission 3 @ rootBlock

        blockDTOs.add(blockDTO);
    }

    @Test
    public void toDTO() throws Exception {
        SchBlockDTO dto = blockMapper.toDto(block);

        assertNotNull(dto.getBlocks());
        assertNotNull(dto.getEmissions());
        assertNotNull(dto.getLength());
        assertNotNull(dto.getName());
        //assertNotNull(dto.getQueueParams());
        assertNotNull(dto.getEventType());
        //assertNotNull(dto.getTimeParams());
    }

    @Test
    public void toDTOs() throws Exception {
        List<SchBlockDTO> dtos = blockMapper.toDto(blocks);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getBlocks());
            assertNotNull(dto.getEmissions());
            assertNotNull(dto.getLength());
            assertNotNull(dto.getName());
            //assertNotNull(dto.getQueueParams());
            assertNotNull(dto.getEventType());
            //assertNotNull(dto.getTimeParams());
        });
    }

    @Test
    public void toEntity() throws Exception {
        SchBlock entity = blockMapper.toEntity(blockDTO);

        assertNotNull(entity.getBlocks());
        assertNotNull(entity.getEmissions());
        assertNotNull(entity.getLength());
        assertNotNull(entity.getName());
        assertNotNull(entity.getQueueParams());
        assertNotNull(entity.getEventType());
        assertNotNull(entity.getTimeParams());
    }

    @Test
    public void toEntities() throws Exception {
        List<SchBlock> entities = blockMapper.toEntity(blockDTOs);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getBlocks());
            assertNotNull(entity.getEmissions());
            assertNotNull(entity.getLength());
            assertNotNull(entity.getName());
            assertNotNull(entity.getQueueParams());
            assertNotNull(entity.getEventType());
            assertNotNull(entity.getTimeParams());
        });
    }

}