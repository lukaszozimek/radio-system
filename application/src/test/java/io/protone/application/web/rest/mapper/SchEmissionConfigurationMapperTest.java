package io.protone.application.web.rest.mapper;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.scheduler.api.dto.SchAttachmentConfigurationDTO;
import io.protone.scheduler.api.dto.SchConfigurationTimeParamsDTO;
import io.protone.scheduler.api.dto.SchEmissionConfigurationDTO;
import io.protone.scheduler.api.dto.SchQueueParamsDTO;
import io.protone.scheduler.domain.SchAttachmentConfiguration;
import io.protone.scheduler.domain.SchConfigurationTimeParams;
import io.protone.scheduler.domain.SchEmissionConfiguration;
import io.protone.scheduler.domain.SchQueueParams;
import io.protone.scheduler.mapper.SchEmissionConfigurationMapper;
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
public class SchEmissionConfigurationMapperTest {

    @Autowired
    private SchEmissionConfigurationMapper emissionMapper;

    private SchEmissionConfiguration emission;

    private SchEmissionConfigurationDTO emissionDTO;

    private List<SchEmissionConfiguration> emissions = new ArrayList<>();

    private List<SchEmissionConfigurationDTO> emissionDTOs = new ArrayList<>();
    private CorNetwork network;
    private CorChannel corChannel;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        corChannel = factory.manufacturePojo(CorChannel.class);
        network = factory.manufacturePojo(CorNetwork.class);
        // Fill entity instance
        // Fill entity instance
        emission = factory.manufacturePojo(SchEmissionConfiguration.class);
        emission.setQueueParams(factory.manufacturePojo(SchQueueParams.class));
        emission.setTimeParams(factory.manufacturePojo(SchConfigurationTimeParams.class));
        emission.setAttachments(Sets.newHashSet(factory.manufacturePojo(SchAttachmentConfiguration.class)));
        emissions.add(emission);

        //Fill DTO instance
        emissionDTO = factory.manufacturePojo(SchEmissionConfigurationDTO.class);
        emissionDTO.setAttachment(Lists.newArrayList(factory.manufacturePojo(SchAttachmentConfigurationDTO.class)));
        emissionDTO.setQueueParams(factory.manufacturePojo(SchQueueParamsDTO.class));
        emissionDTO.setTimeParams(factory.manufacturePojo(SchConfigurationTimeParamsDTO.class));
        emissionDTOs.add(emissionDTO);
    }

    @Test
    public void toDTO() throws Exception {
        SchEmissionConfigurationDTO dto = emissionMapper.DB2DTO(emission);

        assertNotNull(dto.getId());
        assertNotNull(dto.getSeq());
        assertNotNull(dto.getTimeParams());
        assertNotNull(dto.getQueueParams());
        assertNotNull(dto.getAttachment());
    }

    @Test
    public void toDTOs() throws Exception {
        List<SchEmissionConfigurationDTO> dtos = emissionMapper.DBs2DTOs(emissions);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getId());
            assertNotNull(dto.getSeq());
            assertNotNull(dto.getTimeParams());
            assertNotNull(dto.getQueueParams());
            assertNotNull(dto.getAttachment());
        });
    }

    @Test
    public void DTO2DB() throws Exception {
        SchEmissionConfiguration entity = emissionMapper.DTO2DB(emissionDTO, network, corChannel);

        assertNotNull(entity.getId());
        assertNotNull(entity.getSeq());
        assertNotNull(entity.getTimeParams());
        assertNotNull(entity.getQueueParams());

        assertNotNull(entity.getNetwork());
        assertNotNull(entity.getChannel());
        assertNotNull(entity.getAttachments());
    }

    @Test
    public void toEntities() throws Exception {
        List<SchEmissionConfiguration> entities = emissionMapper.DTOs2DBs(emissionDTOs, network, corChannel);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getSeq());
            assertNotNull(entity.getTimeParams());
            assertNotNull(entity.getQueueParams());
            assertNotNull(entity.getNetwork());
            assertNotNull(entity.getChannel());
            assertNotNull(entity.getAttachments());
        });
    }
}