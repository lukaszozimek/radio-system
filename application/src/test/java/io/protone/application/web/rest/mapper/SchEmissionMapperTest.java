package io.protone.application.web.rest.mapper;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.scheduler.api.dto.SchEmissionDTO;
import io.protone.scheduler.domain.SchEmission;
import io.protone.scheduler.mapper.SchEmissionMapper;
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
    private CorNetwork network;
    private CorChannel corChannel;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        corChannel = factory.manufacturePojo(CorChannel.class);
        network = factory.manufacturePojo(CorNetwork.class);
        // Fill entity instance
        // Fill entity instance
        emission = factory.manufacturePojo(SchEmission.class);
        emissions.add(emission);

        //Fill DTO instance
        emissionDTO = factory.manufacturePojo(SchEmissionDTO.class);
        emissionDTOs.add(emissionDTO);
    }

    @Test
    public void toDTO() throws Exception {
        SchEmissionDTO dto = emissionMapper.DB2DTO(emission);

        assertNotNull(dto.getId());
        assertNotNull(dto.getSeq());
        assertNotNull(dto.getTimeParams());
        assertNotNull(dto.getQueueParams());
        //TODO: Consider assertions for attachments
    }

    @Test
    public void toDTOs() throws Exception {
        List<SchEmissionDTO> dtos = emissionMapper.DBs2DTOs(emissions);

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
    public void DTO2DB() throws Exception {
        SchEmission entity = emissionMapper.DTO2DB(emissionDTO, network, corChannel);

        assertNotNull(entity.getId());
        assertNotNull(entity.getSeq());
        assertNotNull(entity.getTimeParams());
        assertNotNull(entity.getQueueParams());

        assertNotNull(entity.getNetwork());
        assertNotNull(entity.getChannel());
        //TODO: Consider assertions for attachments
    }

    @Test
    public void toEntities() throws Exception {
        List<SchEmission> entities = emissionMapper.DTOs2DBs(emissionDTOs, network, corChannel);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getSeq());
            assertNotNull(entity.getTimeParams());
            assertNotNull(entity.getQueueParams());

            assertNotNull(entity.getNetwork());
            assertNotNull(entity.getChannel());
            //TODO: Consider assertions for attachments
        });
    }
}