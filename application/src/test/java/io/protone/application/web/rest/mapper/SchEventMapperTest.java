package io.protone.application.web.rest.mapper;

import io.protone.application.ProtoneApp;
import io.protone.core.api.dto.CorDictionaryDTO;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorDictionary;
import io.protone.core.domain.CorNetwork;
import io.protone.scheduler.api.dto.SchEventConfigurationDTO;
import io.protone.scheduler.api.dto.SchEventDTO;
import io.protone.scheduler.api.dto.SchEventEmissionDTO;
import io.protone.scheduler.domain.SchEvent;
import io.protone.scheduler.domain.SchEventConfiguration;
import io.protone.scheduler.domain.SchEventEmission;
import io.protone.scheduler.mapper.SchEventMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

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
        event = factory.manufacturePojo(SchEvent.class);
        event.addEventEmissions(factory.manufacturePojo(SchEventEmission.class)); //Emission 1 @ rootEvent
        event.addEventEmissions(factory.manufacturePojo(SchEventEmission.class)); //Emission 2 @ rootEvent
        event.addEventEmissions(factory.manufacturePojo(SchEventEmission.class)); //Emission 3 @ rootEvent
        event.setSchEvents(Sets.newSet(factory.manufacturePojo(SchEvent.class)));
        event.setSchEventConfigurations(Sets.newSet(factory.manufacturePojo(SchEventConfiguration.class)));
        event.setEventCategory(factory.manufacturePojo(CorDictionary.class));
        events.add(event);
        //Fill DTO instance
        eventDTO = factory.manufacturePojo(SchEventDTO.class);
        eventDTO.addEmissionsItem(factory.manufacturePojo(SchEventEmissionDTO.class)); //Emission 1 @ rootEvent
        eventDTO.addEmissionsItem(factory.manufacturePojo(SchEventEmissionDTO.class)); //Emission 2 @ rootEvent
        eventDTO.addEmissionsItem(factory.manufacturePojo(SchEventEmissionDTO.class)); //Emission 3 @ rootEvent
        eventDTO.setEvents(Sets.newSet(factory.manufacturePojo(SchEventDTO.class)));
        eventDTO.setEventConfigurationDTOS(Sets.newSet(factory.manufacturePojo(SchEventConfigurationDTO.class)));
        eventDTO.setEventCategory(factory.manufacturePojo(CorDictionaryDTO.class));

        eventDTOs.add(eventDTO);
    }

    @Test
    public void toDTO() throws Exception {
        SchEventDTO dto = eventMapper.DB2DTO(event);
        assertNotNull(dto.getEmissions());
        assertNotNull(dto.getName());
        assertNotNull(dto.getEventConfigurationDTOS());
        assertNotNull(dto.getEvents());
        assertNotNull(dto.getSequence());
        assertNotNull(dto.getEventType());
    }

    @Test
    public void toDTOs() throws Exception {
        List<SchEventDTO> dtos = eventMapper.DBs2DTOs(events);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getEmissions());
            assertNotNull(dto.getName());
            assertNotNull(dto.getSequence());
            assertNotNull(dto.getEventType());
            assertNotNull(dto.getEventConfigurationDTOS());
            assertNotNull(dto.getEvents());
            assertNotNull(dto.getEventCategory());
            assertNotNull(dto.getEvents());
        });
    }

    @Test
    public void DTO2DB() throws Exception {
        SchEvent entity = eventMapper.DTO2DB(eventDTO, network, corChannel);

        assertNotNull(entity.getEmissions());
        assertNotNull(entity.getName());
        assertNotNull(entity.getSequence());
        assertNotNull(entity.getEventType());
        assertNotNull(entity.getLength());
        assertNotNull(entity.getRelativeDelay());
        assertNotNull(entity.getSequence());
        assertNotNull(entity.getSchEvents());
        assertNotNull(entity.getSchEventConfigurations());
        assertNotNull(entity.getEventCategory());
        assertNotNull(entity.getSchEvents());
        assertNotNull(entity.getNetwork());
        assertNotNull(entity.getChannel());
    }

    @Test
    public void toEntities() throws Exception {
        List<SchEvent> entities = eventMapper.DTOs2DBs(eventDTOs, network, corChannel);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getEmissions());
            assertNotNull(entity.getName());
            assertNotNull(entity.getSequence());
            assertNotNull(entity.getEventType());
            assertNotNull(entity.getLength());
            assertNotNull(entity.getRelativeDelay());
            assertNotNull(entity.getSequence());
            assertNotNull(entity.getEventCategory());
            assertNotNull(entity.getSchEvents());
            assertNotNull(entity.getSchEventConfigurations());
            assertNotNull(entity.getSchEvents());
            assertNotNull(entity.getNetwork());
            assertNotNull(entity.getChannel());
        });
    }

}