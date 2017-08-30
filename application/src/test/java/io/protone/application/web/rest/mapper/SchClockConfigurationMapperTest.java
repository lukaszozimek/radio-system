package io.protone.application.web.rest.mapper;

import com.google.common.collect.Sets;
import io.protone.application.ProtoneApp;
import io.protone.core.api.dto.CorDictionaryDTO;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorDictionary;
import io.protone.core.domain.CorNetwork;
import io.protone.scheduler.api.dto.*;
import io.protone.scheduler.domain.*;
import io.protone.scheduler.mapper.SchClockConfigurationMapper;
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
public class SchClockConfigurationMapperTest {
    private static final PodamFactory factory = new PodamFactoryImpl();

    @Autowired
    private SchClockConfigurationMapper schClockConfigurationMapper;

    private SchClockConfiguration clockConfiguration;

    private SchClockConfigurationDTO schClockConfigurationDTO;

    private List<SchClockConfiguration> schClockConfigurations = new ArrayList<>();

    private List<SchClockConfigurationDTO> schClockConfigurationDTOS = new ArrayList<>();
    private CorNetwork network;
    private CorChannel corChannel;

    @Before
    public void initPojos() {
        corChannel = factory.manufacturePojo(CorChannel.class);
        network = factory.manufacturePojo(CorNetwork.class);
        clockConfiguration = factory.manufacturePojo(SchClockConfiguration.class);
        clockConfiguration.setEmissions(Sets.newHashSet(factory.manufacturePojo(SchEmissionConfiguration.class)));
        clockConfiguration.setEvents(Sets.newHashSet(factory.manufacturePojo(SchEvent.class)));
        clockConfiguration.setQueueParams(factory.manufacturePojo(SchQueueParams.class));
        clockConfiguration.setTimeParams(factory.manufacturePojo(SchTimeParams.class));
        clockConfiguration.setClockCategory(factory.manufacturePojo(CorDictionary.class));
        schClockConfigurations.add(clockConfiguration);
        schClockConfigurationDTO = factory.manufacturePojo(SchClockConfigurationDTO.class);
        schClockConfigurationDTO.setEmissions(Sets.newHashSet(factory.manufacturePojo(SchEmissionConfigurationDTO.class)));
        schClockConfigurationDTO.setEvents(Sets.newHashSet(factory.manufacturePojo(SchEventDTO.class)));
        schClockConfigurationDTO.setQueueParams(factory.manufacturePojo(SchQueueParamsDTO.class));
        schClockConfigurationDTO.setTimeParams(factory.manufacturePojo(SchTimeParamsDTO.class));
        schClockConfigurationDTO.setClockCategory(factory.manufacturePojo(CorDictionaryDTO.class));
        schClockConfigurationDTOS.add(schClockConfigurationDTO);
    }

    @Test
    public void toDTO() throws Exception {
        SchClockConfigurationDTO dto = schClockConfigurationMapper.DB2DTO(clockConfiguration);
        assertNotNull(dto.getName());
        assertNotNull(dto.getShortName());
        assertNotNull(dto.getEvents());
        assertNotNull(dto.getEmissions());
        assertNotNull(dto.getClockCategory());


    }

    @Test
    public void toDTOs() throws Exception {
        List<SchClockConfigurationDTO> dtos = schClockConfigurationMapper.DBs2DTOs(schClockConfigurations);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getName());
            assertNotNull(dto.getShortName());
            assertNotNull(dto.getEvents());
            assertNotNull(dto.getEmissions());
            assertNotNull(dto.getClockCategory());
        });
    }

    @Test
    public void DTO2DB() throws Exception {
        SchClockConfiguration entity = schClockConfigurationMapper.DTO2DB(schClockConfigurationDTO, network, corChannel);

        assertNotNull(entity.getName());
        assertNotNull(entity.getShortName());
        assertNotNull(entity.getEvents());
        assertNotNull(entity.getEmissions());
        assertNotNull(entity.getNetwork());
        assertNotNull(entity.getChannel());
        assertNotNull(entity.getClockCategory());
    }

    @Test
    public void toEntities() throws Exception {
        List<SchClockConfiguration> entities = schClockConfigurationMapper.DTOs2DBs(schClockConfigurationDTOS, network, corChannel);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getName());
            assertNotNull(entity.getShortName());
            assertNotNull(entity.getEvents());
            assertNotNull(entity.getEmissions());
            assertNotNull(entity.getNetwork());
            assertNotNull(entity.getChannel());
            assertNotNull(entity.getClockCategory());
        });
    }

}