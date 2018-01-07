package io.protone.application.web.rest.mapper;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.library.api.dto.thin.LibFileItemThinDTO;
import io.protone.library.domain.LibFileItem;
import io.protone.scheduler.api.dto.SchLogConfigurationDTO;
import io.protone.scheduler.api.dto.SchLogDTO;
import io.protone.scheduler.domain.SchLog;
import io.protone.scheduler.domain.SchLogConfiguration;
import io.protone.scheduler.mapper.SchLogMapper;
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
public class SchLogMapperTest {
    private static final PodamFactory factory = new PodamFactoryImpl();

    @Autowired
    private SchLogMapper schLogMapper;

    private SchLog schLog;

    private SchLogDTO schLogDTO;

    private List<SchLog> schLogs = new ArrayList<>();

    private List<SchLogDTO> schLogDTOS = new ArrayList<>();
    private CorChannel corChannel;


    @Before
    public void initPojos() {
        corChannel = factory.manufacturePojo(CorChannel.class);
        // Fill entity instance
        schLog = factory.manufacturePojo(SchLog.class);
        schLog.setLibFileItem(factory.manufacturePojo(LibFileItem.class));
        schLog.setSchLogConfiguration(factory.manufacturePojo(SchLogConfiguration.class));
        schLogs.add(schLog);
        schLogDTO = factory.manufacturePojo(SchLogDTO.class);
        schLogDTO.setLibFileItem(factory.manufacturePojo(LibFileItemThinDTO.class));
        schLogDTO.setSchLogConfiguration(factory.manufacturePojo(SchLogConfigurationDTO.class));
        schLogDTOS.add(schLogDTO);
    }

    @Test
    public void toDTO() throws Exception {
        SchLogDTO dto = schLogMapper.DB2DTO(schLog);
        assertNotNull(dto.getDate());
        assertNotNull(dto.getLibFileItem());
        assertNotNull(dto.getSchLogConfiguration());

    }

    @Test
    public void toDTOs() throws Exception {
        List<SchLogDTO> dtos = schLogMapper.DBs2DTOs(schLogs);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getDate());
            assertNotNull(dto.getLibFileItem());
            assertNotNull(dto.getSchLogConfiguration());

        });
    }

    @Test
    public void DTO2DB() throws Exception {
        SchLog entity = schLogMapper.DTO2DB(schLogDTO, corChannel);

        assertNotNull(entity.getDate());
        assertNotNull(entity.getLibFileItem());
        assertNotNull(entity.getSchLogConfiguration());
        assertNotNull(entity.getChannel());
    }

    @Test
    public void toEntities() throws Exception {
        List<SchLog> entities = schLogMapper.DTOs2DBs(schLogDTOS, corChannel);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getDate());
            assertNotNull(entity.getLibFileItem());
            assertNotNull(entity.getSchLogConfiguration());
            assertNotNull(entity.getChannel());
        });
    }

}