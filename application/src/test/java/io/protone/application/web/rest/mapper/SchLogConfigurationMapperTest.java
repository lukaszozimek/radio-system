package io.protone.application.web.rest.mapper;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.scheduler.api.dto.SchLogConfigurationDTO;
import io.protone.scheduler.domain.SchLogConfiguration;
import io.protone.scheduler.mapper.SchLogConfigurationMapper;
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
public class SchLogConfigurationMapperTest {
    private static final PodamFactory factory = new PodamFactoryImpl();

    @Autowired
    private SchLogConfigurationMapper schLogMapper;

    private SchLogConfiguration schLog;

    private SchLogConfigurationDTO schLogDTO;

    private List<SchLogConfiguration> schLogs = new ArrayList<>();

    private List<SchLogConfigurationDTO> schLogDTOS = new ArrayList<>();
    private CorNetwork network;
    private CorChannel corChannel;

    @Before
    public void initPojos() {
        corChannel = factory.manufacturePojo(CorChannel.class);
        network = factory.manufacturePojo(CorNetwork.class);
        // Fill entity instance
        schLog = factory.manufacturePojo(SchLogConfiguration.class);
        schLogs.add(schLog);
        schLogDTO = factory.manufacturePojo(SchLogConfigurationDTO.class);
        schLogDTOS.add(schLogDTO);
    }

    @Test
    public void toDTO() throws Exception {
        SchLogConfigurationDTO dto = schLogMapper.DB2DTO(schLog);
        assertNotNull(dto.getLogColumns());
        assertNotNull(dto.getName());
        assertNotNull(dto.getSpearator());
        assertNotNull(dto.getExtension());
        assertNotNull(dto.getPattern());
    }

    @Test
    public void toDTOs() throws Exception {
        List<SchLogConfigurationDTO> dtos = schLogMapper.DBs2DTOs(schLogs);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getLogColumns());
            assertNotNull(dto.getName());
            assertNotNull(dto.getSpearator());
            assertNotNull(dto.getExtension());
            assertNotNull(dto.getPattern());
        });
    }

    @Test
    public void DTO2DB() throws Exception {
        SchLogConfiguration entity = schLogMapper.DTO2DB(schLogDTO, network, corChannel);
        assertNotNull(entity.getLogColumns());
        assertNotNull(entity.getName());
        assertNotNull(entity.getSpearator());
        assertNotNull(entity.getExtension());
        assertNotNull(entity.getPattern());
        assertNotNull(entity.getNetwork());
        assertNotNull(entity.getChannel());
    }

    @Test
    public void toEntities() throws Exception {
        List<SchLogConfiguration> entities = schLogMapper.DTOs2DBs(schLogDTOS, network, corChannel);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getLogColumns());
            assertNotNull(entity.getName());
            assertNotNull(entity.getSpearator());
            assertNotNull(entity.getExtension());
            assertNotNull(entity.getPattern());
            assertNotNull(entity.getNetwork());
            assertNotNull(entity.getChannel());
        });
    }
}