package io.protone.application.web.rest.mapper;

import com.google.common.collect.Lists;
import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.scheduler.api.dto.SchAttachmentTemplateDTO;
import io.protone.scheduler.api.dto.SchEmissionTemplateDTO;
import io.protone.scheduler.domain.SchAttachmentTemplate;
import io.protone.scheduler.domain.SchEmissionTemplate;
import io.protone.scheduler.mapper.SchEmissionTemplateMapper;
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
public class SchEmissionTemplateMapperTest {

    @Autowired
    private SchEmissionTemplateMapper emissionMapper;

    private SchEmissionTemplate emission;

    private SchEmissionTemplateDTO emissionDTO;

    private List<SchEmissionTemplate> emissions = new ArrayList<>();

    private List<SchEmissionTemplateDTO> emissionDTOs = new ArrayList<>();
    private CorChannel corChannel;


    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        corChannel = factory.manufacturePojo(CorChannel.class);
        emission = factory.manufacturePojo(SchEmissionTemplate.class);
        emission.setAttachments(Lists.newArrayList(factory.manufacturePojo(SchAttachmentTemplate.class)));
        emissions.add(emission);
        emissionDTO = factory.manufacturePojo(SchEmissionTemplateDTO.class);
        emissionDTO.setAttachments(Lists.newArrayList(factory.manufacturePojo(SchAttachmentTemplateDTO.class)));
        emissionDTOs.add(emissionDTO);
    }

    @Test
    public void toDTO() throws Exception {
        SchEmissionTemplateDTO dto = emissionMapper.DB2DTO(emission);

        assertNotNull(dto.getId());
        assertNotNull(dto.getLength());
        assertNotNull(dto.getRelativeDelay());
        assertNotNull(dto.getSequence());

        assertNotNull(dto.getAttachments());
    }

    @Test
    public void toDTOs() throws Exception {
        List<SchEmissionTemplateDTO> dtos = emissionMapper.DBs2DTOs(emissions);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getId());
            assertNotNull(dto.getSequence());

            assertNotNull(dto.getLength());
            assertNotNull(dto.getRelativeDelay());
            assertNotNull(dto.getSequence());

            assertNotNull(dto.getAttachments());
        });
    }

    @Test
    public void DTO2DB() throws Exception {
        SchEmissionTemplate entity = emissionMapper.DTO2DB(emissionDTO, corChannel);

        assertNotNull(entity.getId());
        assertNotNull(entity.getSequence());

        assertNotNull(entity.getLength());
        assertNotNull(entity.getRelativeDelay());
        assertNotNull(entity.getSequence());
        assertNotNull(entity.getChannel());
        assertNotNull(entity.getAttachments());
    }

    @Test
    public void toEntities() throws Exception {
        List<SchEmissionTemplate> entities = emissionMapper.DTOs2DBs(emissionDTOs, corChannel);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());

            assertNotNull(entity.getLength());
            assertNotNull(entity.getRelativeDelay());
            assertNotNull(entity.getSequence());
            assertNotNull(entity.getChannel());
            assertNotNull(entity.getAttachments());
        });
    }
}