package io.protone.application.web.rest.mapper;

import io.protone.application.ProtoneApp;
import io.protone.scheduler.api.dto.SchEmissionDTO;
import io.protone.scheduler.domain.SchEmission;
import io.protone.scheduler.mapper.SchEmissionMapper;
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
public class SchEmissionMapperTest {

    @Autowired
    private SchEmissionMapper emissionMapper;

    private SchEmission emission;

    private SchEmissionDTO emissionDTO;

    private List<SchEmission> emissions = new ArrayList<>();

    private List<SchEmissionDTO> emissionDTOs = new ArrayList<>();

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();

        // Fill entity instance
        emission = factory.manufacturePojo(SchEmission.class);
        emissions.add(emission);

        //Fill DTO instance
        emissionDTO = factory.manufacturePojo(SchEmissionDTO.class);
        emissionDTOs.add(emissionDTO);
    }

    @Test
    public void toDTO() throws Exception {
        SchEmissionDTO dto = emissionMapper.toDto(emission);

        assertNotNull(dto.getId());
        assertNotNull(dto.getSeq());
        assertNotNull(dto.getTimeParams());
        assertNotNull(dto.getQueueParams());
        //TODO: Consider assertions for attachments
    }

    @Test
    public void toDTOs() throws Exception {
        List<SchEmissionDTO> dtos = emissionMapper.toDto(emissions);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getId());
            assertNotNull(dto.getSeq());
            assertNotNull(dto.getTimeParams());
            assertNotNull(dto.getQueueParams());
            //TODO: Consider assertions for attachments
        });
    }

    @Test
    public void toEntity() throws Exception {
        SchEmission entity = emissionMapper.toEntity(emissionDTO);

        assertNotNull(entity.getId());
        assertNotNull(entity.getSeq());
        assertNotNull(entity.getTimeParams());
        assertNotNull(entity.getQueueParams());
        //TODO: Consider assertions for attachments
    }

    @Test
    public void toEntities() throws Exception {
        List<SchEmission> entities = emissionMapper.toEntity(emissionDTOs);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getSeq());
            assertNotNull(entity.getTimeParams());
            assertNotNull(entity.getQueueParams());
            //TODO: Consider assertions for attachments
        });
    }
}