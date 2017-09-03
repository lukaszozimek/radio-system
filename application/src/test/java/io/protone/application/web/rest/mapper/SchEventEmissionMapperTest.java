package io.protone.application.web.rest.mapper;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.scheduler.api.dto.SchConfigurationTimeParamsDTO;
import io.protone.scheduler.api.dto.SchEventEmissionAttachmentDTO;
import io.protone.scheduler.api.dto.SchEventEmissionDTO;
import io.protone.scheduler.domain.SchConfigurationTimeParams;
import io.protone.scheduler.domain.SchEventEmission;
import io.protone.scheduler.domain.SchEventEmissionAttachment;
import io.protone.scheduler.mapper.SchEventEmissionMapper;
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
public class SchEventEmissionMapperTest {

    @Autowired
    private SchEventEmissionMapper emissionMapper;

    private SchEventEmission emission;

    private SchEventEmissionDTO emissionDTO;

    private List<SchEventEmission> emissions = new ArrayList<>();

    private List<SchEventEmissionDTO> emissionDTOs = new ArrayList<>();
    private CorNetwork network;
    private CorChannel corChannel;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        corChannel = factory.manufacturePojo(CorChannel.class);
        network = factory.manufacturePojo(CorNetwork.class);
        // Fill entity instance
        // Fill entity instance
        emission = factory.manufacturePojo(SchEventEmission.class);
        emission.setTimeParams(factory.manufacturePojo(SchConfigurationTimeParams.class));
        emission.setAttachments(Sets.newHashSet(factory.manufacturePojo(SchEventEmissionAttachment.class)));
        emissions.add(emission);

        //Fill DTO instance
        emissionDTO = factory.manufacturePojo(SchEventEmissionDTO.class);
        emissionDTO.setAttachments(Lists.newArrayList(factory.manufacturePojo(SchEventEmissionAttachmentDTO.class)));

        emissionDTO.setTimeParams(factory.manufacturePojo(SchConfigurationTimeParamsDTO.class));
        emissionDTOs.add(emissionDTO);
    }

    @Test
    public void toDTO() throws Exception {
        SchEventEmissionDTO dto = emissionMapper.DB2DTO(emission);

        assertNotNull(dto.getId());
        assertNotNull(dto.getSequence());
        assertNotNull(dto.getTimeParams());
        assertNotNull(dto.getAttachments());
    }

    @Test
    public void toDTOs() throws Exception {
        List<SchEventEmissionDTO> dtos = emissionMapper.DBs2DTOs(emissions);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getId());
            assertNotNull(dto.getSequence());
            assertNotNull(dto.getTimeParams());
            assertNotNull(dto.getAttachments());
        });
    }

    @Test
    public void DTO2DB() throws Exception {
        SchEventEmission entity = emissionMapper.DTO2DB(emissionDTO, network, corChannel);

        assertNotNull(entity.getId());
        assertNotNull(entity.getSequence());
        assertNotNull(entity.getTimeParams());

        assertNotNull(entity.getNetwork());
        assertNotNull(entity.getChannel());
        assertNotNull(entity.getAttachments());
    }

    @Test
    public void toEntities() throws Exception {
        List<SchEventEmission> entities = emissionMapper.DTOs2DBs(emissionDTOs, network, corChannel);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getSequence());
            assertNotNull(entity.getTimeParams());
            assertNotNull(entity.getNetwork());
            assertNotNull(entity.getChannel());
            assertNotNull(entity.getAttachments());
        });
    }
}