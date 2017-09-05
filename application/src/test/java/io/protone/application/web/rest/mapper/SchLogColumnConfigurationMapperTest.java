package io.protone.application.web.rest.mapper;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.scheduler.api.dto.SchLogConfigurationColumnDTO;
import io.protone.scheduler.domain.SchLogColumn;
import io.protone.scheduler.domain.enumeration.LogColumnTypEnum;
import io.protone.scheduler.mapper.SchLogColumnConfigurationMapper;
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
public class SchLogColumnConfigurationMapperTest {
    private static final PodamFactory factory = new PodamFactoryImpl();

    @Autowired
    private SchLogColumnConfigurationMapper schLogColumnConfigurationMapper;

    private SchLogColumn schLogColumn;

    private SchLogConfigurationColumnDTO schLogConfigurationColumnDTO;

    private List<SchLogColumn> schLogColumns = new ArrayList<>();

    private List<SchLogConfigurationColumnDTO> schLogConfigurationColumnDTOS = new ArrayList<>();
    private CorNetwork network;
    private CorChannel corChannel;

    @Before
    public void initPojos() {
        corChannel = factory.manufacturePojo(CorChannel.class);
        network = factory.manufacturePojo(CorNetwork.class);
        // Fill entity instance
        schLogColumn = factory.manufacturePojo(SchLogColumn.class);
        schLogColumn.setName(LogColumnTypEnum.LCT_IDX);
        schLogColumns.add(schLogColumn);
        schLogConfigurationColumnDTO = factory.manufacturePojo(SchLogConfigurationColumnDTO.class);
        schLogConfigurationColumnDTO.setName(LogColumnTypEnum.LCT_IDX);
        schLogConfigurationColumnDTOS.add(schLogConfigurationColumnDTO);
    }

    @Test
    public void toDTO() throws Exception {
        SchLogConfigurationColumnDTO dto = schLogColumnConfigurationMapper.DB2DTO(schLogColumn);
        assertNotNull(dto.getName());
        assertNotNull(dto.getColumnSequence());
        assertNotNull(dto.getLenght());

    }

    @Test
    public void toDTOs() throws Exception {
        List<SchLogConfigurationColumnDTO> dtos = schLogColumnConfigurationMapper.DBs2DTOs(schLogColumns);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getName());
            assertNotNull(dto.getColumnSequence());
            assertNotNull(dto.getLenght());
        });
    }

    @Test
    public void DTO2DB() throws Exception {
        SchLogColumn entity = schLogColumnConfigurationMapper.DTO2DB(schLogConfigurationColumnDTO, network, corChannel);
        assertNotNull(entity.getName());
        assertNotNull(entity.getColumnSequence());
        assertNotNull(entity.getLength());
        assertNotNull(entity.getNetwork());
        assertNotNull(entity.getChannel());
    }

    @Test
    public void toEntities() throws Exception {
        List<SchLogColumn> entities = schLogColumnConfigurationMapper.DTOs2DBs(schLogConfigurationColumnDTOS, network, corChannel);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getName());
            assertNotNull(entity.getColumnSequence());
            assertNotNull(entity.getLength());
            assertNotNull(entity.getNetwork());
            assertNotNull(entity.getChannel());
        });
    }
}