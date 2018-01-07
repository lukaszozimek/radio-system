package io.protone.application.web.rest.mapper;


import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.traffic.api.dto.TraBlockConfigurationDTO;
import io.protone.traffic.domain.TraBlockConfiguration;
import io.protone.traffic.mapper.TraBlockConfigurationMapper;
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
 * Created by lukaszozimek on 15/05/2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TraBlockConfigurationMapperTest {

    @Autowired
    private TraBlockConfigurationMapper traBlockConfigurationMapper;

    private TraBlockConfiguration traBlockConfiguration;

    private TraBlockConfigurationDTO traDitraBlockConfigurationDTO;

    private List<TraBlockConfigurationDTO> traBlockConfigurationDTOS = new ArrayList<>();

    private List<TraBlockConfiguration> traBlockConfigurations = new ArrayList<>();

    private CorChannel corChannel;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        traBlockConfiguration = factory.manufacturePojo(TraBlockConfiguration.class);
        traBlockConfiguration.setId(1L);
        traBlockConfigurations.add(traBlockConfiguration);
        traDitraBlockConfigurationDTO = factory.manufacturePojo(TraBlockConfigurationDTO.class);
        traDitraBlockConfigurationDTO.setId(1L);
        traBlockConfigurationDTOS.add(traDitraBlockConfigurationDTO);
        corChannel = factory.manufacturePojo(CorChannel.class);
        corChannel = factory.manufacturePojo(CorChannel.class);
    }


    @Test
    public void DB2DTO() throws Exception {
        TraBlockConfigurationDTO dto = traBlockConfigurationMapper.DB2DTO(traBlockConfiguration);

        assertNotNull(dto.getId());
        assertNotNull(dto.getStartBlock());
        assertNotNull(dto.getStopBlock());
        assertNotNull(dto.getSequence());
        assertNotNull(dto.getName());
        assertNotNull(dto.getLength());
        assertNotNull(dto.getDay());

    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<TraBlockConfigurationDTO> dtos = traBlockConfigurationMapper.DBs2DTOs(traBlockConfigurations);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getId());
            assertNotNull(dto.getStartBlock());
            assertNotNull(dto.getStopBlock());
            assertNotNull(dto.getSequence());
            assertNotNull(dto.getName());
            assertNotNull(dto.getLength());
            assertNotNull(dto.getDay());
        });
    }

    @Test
    public void DTO2DB() throws Exception {
        TraBlockConfiguration entity = traBlockConfigurationMapper.DTO2DB(traDitraBlockConfigurationDTO, corChannel);
        assertNotNull(entity.getId());
        assertNotNull(entity.getStartBlock());
        assertNotNull(entity.getStopBlock());
        assertNotNull(entity.getName());
        assertNotNull(entity.getLength());
        assertNotNull(entity.getChannel());
        assertNotNull(entity.getDay());
        assertNotNull(entity.getSequence());
    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<TraBlockConfiguration> entities = traBlockConfigurationMapper.DTOs2DBs(traBlockConfigurationDTOS, corChannel);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());

            assertNotNull(entity.getStartBlock());
            assertNotNull(entity.getStopBlock());
            assertNotNull(entity.getName());
            assertNotNull(entity.getLength());
            assertNotNull(entity.getSequence());
            assertNotNull(entity.getChannel());
            assertNotNull(entity.getDay());

        });
    }

}
