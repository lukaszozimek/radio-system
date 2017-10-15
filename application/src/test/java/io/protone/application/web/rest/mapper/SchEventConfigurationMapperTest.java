package io.protone.application.web.rest.mapper;

import io.protone.application.ProtoneApp;
import io.protone.core.api.dto.CorDictionaryDTO;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorDictionary;
import io.protone.core.domain.CorNetwork;
import io.protone.scheduler.api.dto.SchEmissionConfigurationDTO;
import io.protone.scheduler.api.dto.SchEventConfigurationDTO;
import io.protone.scheduler.api.dto.SchEventDTO;
import io.protone.scheduler.api.dto.SchLogConfigurationDTO;
import io.protone.scheduler.domain.SchEmissionConfiguration;
import io.protone.scheduler.domain.SchEvent;
import io.protone.scheduler.domain.SchEventConfiguration;
import io.protone.scheduler.domain.SchLogConfiguration;
import io.protone.scheduler.mapper.SchEventConfigurationMapper;
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

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * Created by lukaszozimek on 30/08/2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class SchEventConfigurationMapperTest {
    private static final PodamFactory factory = new PodamFactoryImpl();

    @Autowired
    private SchEventConfigurationMapper schEventConfigurationMapper;

    private SchEventConfiguration schEventConfiguration;

    private SchEventConfigurationDTO schEventConfigurationDTO;

    private List<SchEventConfiguration> schEventConfigurations = new ArrayList<>();

    private List<SchEventConfigurationDTO> schEventConfigurationDTOS = new ArrayList<>();
    private CorNetwork network;
    private CorChannel corChannel;

    @Before
    public void initPojos() {
        corChannel = factory.manufacturePojo(CorChannel.class);
        network = factory.manufacturePojo(CorNetwork.class);
        // Fill entity instance
        schEventConfiguration = factory.manufacturePojo(SchEventConfiguration.class);
        schEventConfiguration.addEventEmissions(factory.manufacturePojo(SchEmissionConfiguration.class));
        schEventConfiguration.addEventEmissions(factory.manufacturePojo(SchEmissionConfiguration.class)); 
        schEventConfiguration.addEventEmissions(factory.manufacturePojo(SchEmissionConfiguration.class)); 
        schEventConfiguration.addEvent(factory.manufacturePojo(SchEvent.class)); 
        schEventConfiguration.addEvent(factory.manufacturePojo(SchEvent.class));
        schEventConfiguration.addEvent(factory.manufacturePojo(SchEvent.class)); 
        schEventConfiguration.addEventConfiguration(factory.manufacturePojo(SchEventConfiguration.class)); 
        schEventConfiguration.addEventConfiguration(factory.manufacturePojo(SchEventConfiguration.class)); 
        schEventConfiguration.addEventConfiguration(factory.manufacturePojo(SchEventConfiguration.class)); 

        schEventConfiguration.setEventCategory(factory.manufacturePojo(CorDictionary.class));
        schEventConfiguration.setSchLogConfiguration(factory.manufacturePojo(SchLogConfiguration.class));

        schEventConfigurations.add(schEventConfiguration);

        //Fill DTO instance
        schEventConfigurationDTO = factory.manufacturePojo(SchEventConfigurationDTO.class);
        schEventConfigurationDTO.addEmission(factory.manufacturePojo(SchEmissionConfigurationDTO.class));
        schEventConfigurationDTO.addEmission(factory.manufacturePojo(SchEmissionConfigurationDTO.class));
        schEventConfigurationDTO.addEmission(factory.manufacturePojo(SchEmissionConfigurationDTO.class)); 
        schEventConfigurationDTO.addEventsConfiguration(factory.manufacturePojo(SchEventConfigurationDTO.class));
        schEventConfigurationDTO.addEventsConfiguration(factory.manufacturePojo(SchEventConfigurationDTO.class));
        schEventConfigurationDTO.addEventsConfiguration(factory.manufacturePojo(SchEventConfigurationDTO.class)); 
        schEventConfigurationDTO.addEvents(factory.manufacturePojo(SchEventDTO.class));
        schEventConfigurationDTO.addEvents(factory.manufacturePojo(SchEventDTO.class));
        schEventConfigurationDTO.addEvents(factory.manufacturePojo(SchEventDTO.class)); 

        schEventConfigurationDTO.setEventCategory(factory.manufacturePojo(CorDictionaryDTO.class));
        schEventConfigurationDTO.setSchLogConfiguration(factory.manufacturePojo(SchLogConfigurationDTO.class));
        schEventConfigurationDTOS.add(schEventConfigurationDTO);
    }

    @Test
    public void toDTO() throws Exception {
        SchEventConfigurationDTO dto = schEventConfigurationMapper.DB2DTO(schEventConfiguration);
        assertNotNull(dto.getName());
        assertNotNull(dto.getShortName());
        assertNotNull(dto.getEventType());
        assertNotNull(dto.getSchLogConfiguration());
        assertNotNull(dto.getEventCategory());
        assertNotNull(dto.getSchEventConfigurations());
        assertNotNull(dto.getEvents());
        assertNotNull(dto.getEmissions());
        assertNotNull(dto.getLength());
        assertNotNull(dto.getRelativeDelay());
        assertNotNull(dto.getSequence());

    }

    @Test
    public void toDTOs() throws Exception {
        List<SchEventConfigurationDTO> dtos = schEventConfigurationMapper.DBs2DTOs(schEventConfigurations);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getName());
            assertNotNull(dto.getShortName());
            assertNotNull(dto.getEventType());
            assertNotNull(dto.getEventCategory());
            assertNotNull(dto.getSchLogConfiguration());
            assertNotNull(dto.getSchEventConfigurations());
            assertNotNull(dto.getEvents());
            assertNotNull(dto.getEmissions());
            assertNotNull(dto.getLength());
            assertNotNull(dto.getRelativeDelay());
            assertNotNull(dto.getSequence());
        });
    }

    @Test
    public void DTO2DB() throws Exception {
        SchEventConfiguration entity = schEventConfigurationMapper.DTO2DB(schEventConfigurationDTO, network, corChannel);

        assertNotNull(entity.getName());
        assertNotNull(entity.getShortName());
        assertNotNull(entity.getEventType());
        assertNotNull(entity.getEventCategory());
        assertNotNull(entity.getSchLogConfiguration());
        assertNotNull(entity.getEmissions());
        assertNotNull(entity.getSchEventConfigurations());
        assertNotNull(entity.getSchEvents());
        assertNotNull(entity.getNetwork());
        assertNotNull(entity.getChannel());
        assertNotNull(entity.getLength());
        assertNotNull(entity.getRelativeDelay());
        assertNotNull(entity.getSequence());
    }

    @Test
    public void toEntities() throws Exception {
        List<SchEventConfiguration> entities = schEventConfigurationMapper.DTOs2DBs(schEventConfigurationDTOS, network, corChannel);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getName());
            assertNotNull(entity.getShortName());
            assertNotNull(entity.getEventType());
            assertNotNull(entity.getEventCategory());
            assertNotNull(entity.getSchEventConfigurations());
            assertNotNull(entity.getSchEvents());
            assertNotNull(entity.getSchLogConfiguration());
            assertNotNull(entity.getEmissions());
            assertNotNull(entity.getNetwork());
            assertNotNull(entity.getChannel());
            assertNotNull(entity.getLength());
            assertNotNull(entity.getRelativeDelay());
            assertNotNull(entity.getSequence());
        });
    }

}