package io.protone.application.web.rest.mapper;

import io.protone.application.ProtoneApp;
import io.protone.core.api.dto.CorDictionaryDTO;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorDictionary;
import io.protone.core.domain.CorNetwork;
import io.protone.scheduler.api.dto.*;
import io.protone.scheduler.domain.*;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
        schEventConfiguration.addEmission(factory.manufacturePojo(SchEmissionConfiguration.class)); //Emission 1 @ rootEvent
        schEventConfiguration.addEmission(factory.manufacturePojo(SchEmissionConfiguration.class)); //Emission 2 @ rootEvent
        schEventConfiguration.addEmission(factory.manufacturePojo(SchEmissionConfiguration.class)); //Emission 3 @ rootEvent
        schEventConfiguration.setQueueParams(factory.manufacturePojo(SchQueueParams.class));
        schEventConfiguration.setTimeParams(factory.manufacturePojo(SchTimeParams.class));
        schEventConfiguration.setEventCategory(factory.manufacturePojo(CorDictionary.class));
        schEventConfiguration.setSchLogConfiguration(factory.manufacturePojo(SchLogConfiguration.class));

        schEventConfigurations.add(schEventConfiguration);

        //Fill DTO instance
        schEventConfigurationDTO = factory.manufacturePojo(SchEventConfigurationDTO.class);
        schEventConfigurationDTO.addEmission(factory.manufacturePojo(SchEmissionConfigurationDTO.class)); //Emission 1 @ rootEvent
        schEventConfigurationDTO.addEmission(factory.manufacturePojo(SchEmissionConfigurationDTO.class)); //Emission 2 @ rootEvent
        schEventConfigurationDTO.addEmission(factory.manufacturePojo(SchEmissionConfigurationDTO.class)); //Emission 3 @ rootEvent
        schEventConfigurationDTO.setQueueParams(factory.manufacturePojo(SchQueueParamsDTO.class));
        schEventConfigurationDTO.setTimeParams(factory.manufacturePojo(SchTimeParamsDTO.class));
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
        assertNotNull(dto.getEmissions());

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
            assertNotNull(dto.getEmissions());
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
        assertNotNull(entity.getNetwork());
        assertNotNull(entity.getChannel());
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
            assertNotNull(entity.getSchLogConfiguration());
            assertNotNull(entity.getEmissions());
            assertNotNull(entity.getNetwork());
            assertNotNull(entity.getChannel());
        });
    }

}