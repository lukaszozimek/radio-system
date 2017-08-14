package io.protone.application.web.rest.mapper;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.scheduler.api.dto.SchEventDTO;
import io.protone.scheduler.api.dto.SchEmissionDTO;
import io.protone.scheduler.domain.SchEvent;
import io.protone.scheduler.domain.SchEmission;
import io.protone.scheduler.mapper.SchEventMapper;
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
public class SchEventMapperTest {

    @Autowired
    private SchEventMapper eventMapper;

    private SchEvent event;

    private SchEventDTO eventDTO;

    private List<SchEvent> events = new ArrayList<>();

    private List<SchEventDTO> eventDTOs = new ArrayList<>();
    private CorNetwork network;
    private CorChannel corChannel;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        corChannel = factory.manufacturePojo(CorChannel.class);
        network = factory.manufacturePojo(CorNetwork.class);
        // Fill entity instance
        // Fill entity instance
        event = factory.manufacturePojo(SchEvent.class);

        event.addEmission(factory.manufacturePojo(SchEmission.class)); //Emission 1 @ rootEvent
        event.addEmission(factory.manufacturePojo(SchEmission.class)); //Emission 2 @ rootEvent
        event.addEmission(factory.manufacturePojo(SchEmission.class)); //Emission 3 @ rootEvent

        events.add(event);

        //Fill DTO instance
        eventDTO = factory.manufacturePojo(SchEventDTO.class);

        eventDTO.addEmissionsItem(factory.manufacturePojo(SchEmissionDTO.class)); //Emission 1 @ rootEvent
        eventDTO.addEmissionsItem(factory.manufacturePojo(SchEmissionDTO.class)); //Emission 2 @ rootEvent
        eventDTO.addEmissionsItem(factory.manufacturePojo(SchEmissionDTO.class)); //Emission 3 @ rootEvent

        eventDTOs.add(eventDTO);
    }

    @Test
    public void toDTO() throws Exception {
        SchEventDTO dto = eventMapper.toDto(event);
        assertNotNull(dto.getEmissions());
        assertNotNull(dto.getName());
        assertNotNull(dto.getQueueParams());
        assertNotNull(dto.getEventType());
        assertNotNull(dto.getTimeParams());
    }

    @Test
    public void toDTOs() throws Exception {
        List<SchEventDTO> dtos = eventMapper.toDto(events);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getEmissions());
            assertNotNull(dto.getName());
            assertNotNull(dto.getQueueParams());
            assertNotNull(dto.getEventType());
            assertNotNull(dto.getTimeParams());
        });
    }

    @Test
    public void toEntity() throws Exception {
        SchEvent entity = eventMapper.toEntity(eventDTO, network, corChannel);

        assertNotNull(entity.getEmissions());
        assertNotNull(entity.getName());
        assertNotNull(entity.getQueueParams());
        assertNotNull(entity.getEventType());
        assertNotNull(entity.getTimeParams());

        assertNotNull(entity.getNetwork());
        assertNotNull(entity.getChannel());
    }

    @Test
    public void toEntities() throws Exception {
        List<SchEvent> entities = eventMapper.toEntity(eventDTOs, network, corChannel);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getEmissions());
            assertNotNull(entity.getName());
            assertNotNull(entity.getQueueParams());
            assertNotNull(entity.getEventType());
            assertNotNull(entity.getTimeParams());

            assertNotNull(entity.getNetwork());
            assertNotNull(entity.getChannel());
        });
    }

}