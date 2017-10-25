package io.protone.application.web.rest.mapper;

import com.google.common.collect.Sets;
import io.protone.application.ProtoneApp;
import io.protone.core.api.dto.CorDictionaryDTO;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorDictionary;
import io.protone.core.domain.CorNetwork;
import io.protone.scheduler.api.dto.SchClockTemplateDTO;
import io.protone.scheduler.api.dto.SchEmissionTemplateDTO;
import io.protone.scheduler.api.dto.SchEventTemplateDTO;
import io.protone.scheduler.domain.SchClockTemplate;
import io.protone.scheduler.domain.SchEmissionTemplate;
import io.protone.scheduler.domain.SchEventTemplate;
import io.protone.scheduler.mapper.SchClockTemplateMapper;
import org.assertj.core.util.Lists;
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
public class SchClockTemplateMapperTest {
    private static final PodamFactory factory = new PodamFactoryImpl();

    @Autowired
    private SchClockTemplateMapper schClockTemplateMapper;

    private SchClockTemplate clockConfiguration;

    private SchClockTemplateDTO schClockTemplateDTO;

    private List<SchClockTemplate> schClockTemplates = new ArrayList<>();

    private List<SchClockTemplateDTO> schClockTemplateDTOS = new ArrayList<>();

    private CorNetwork network;

    private CorChannel corChannel;

    @Before
    public void initPojos() {
        corChannel = factory.manufacturePojo(CorChannel.class);
        network = factory.manufacturePojo(CorNetwork.class);
        clockConfiguration = factory.manufacturePojo(SchClockTemplate.class);
        clockConfiguration.setEmissions(Lists.newArrayList(factory.manufacturePojo(SchEmissionTemplate.class)));
        clockConfiguration.setSchEventTemplates(Lists.newArrayList(factory.manufacturePojo(SchEventTemplate.class)));


        clockConfiguration.setClockCategory(factory.manufacturePojo(CorDictionary.class));
        schClockTemplates.add(clockConfiguration);
        schClockTemplateDTO = factory.manufacturePojo(SchClockTemplateDTO.class);
        schClockTemplateDTO.setEmissions(Sets.newHashSet(factory.manufacturePojo(SchEmissionTemplateDTO.class)));
        schClockTemplateDTO.setSchEventTemplateDTOS(Sets.newHashSet(factory.manufacturePojo(SchEventTemplateDTO.class)));
        schClockTemplateDTO.setClockCategory(factory.manufacturePojo(CorDictionaryDTO.class));
        schClockTemplateDTOS.add(schClockTemplateDTO);
    }

    @Test
    public void toDTO() throws Exception {
        SchClockTemplateDTO dto = schClockTemplateMapper.DB2DTO(clockConfiguration);
        assertNotNull(dto.getName());
        assertNotNull(dto.getShortName());
        assertNotNull(dto.getSchEventTemplateDTOS());
        assertNotNull(dto.getEmissions());
        assertNotNull(dto.getClockCategory());
        assertNotNull(dto.getLength());
        assertNotNull(dto.getRelativeDelay());
        assertNotNull(dto.getSequence());


    }

    @Test
    public void toDTOs() throws Exception {
        List<SchClockTemplateDTO> dtos = schClockTemplateMapper.DBs2DTOs(schClockTemplates);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getName());
            assertNotNull(dto.getShortName());

            assertNotNull(dto.getSchEventTemplateDTOS());
            assertNotNull(dto.getEmissions());
            assertNotNull(dto.getClockCategory());
            assertNotNull(dto.getLength());
            assertNotNull(dto.getRelativeDelay());
            assertNotNull(dto.getSequence());
        });
    }

    @Test
    public void DTO2DB() throws Exception {
        SchClockTemplate entity = schClockTemplateMapper.DTO2DB(schClockTemplateDTO, network, corChannel);

        assertNotNull(entity.getName());
        assertNotNull(entity.getShortName());
        assertNotNull(entity.getSchEventTemplates());
        assertNotNull(entity.getEmissions());
        assertNotNull(entity.getNetwork());
        assertNotNull(entity.getChannel());
        assertNotNull(entity.getLength());
        assertNotNull(entity.getRelativeDelay());
        assertNotNull(entity.getSequence());
        assertNotNull(entity.getClockCategory());
    }

    @Test
    public void toEntities() throws Exception {
        List<SchClockTemplate> entities = schClockTemplateMapper.DTOs2DBs(schClockTemplateDTOS, network, corChannel);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getName());
            assertNotNull(entity.getShortName());
            assertNotNull(entity.getSchEventTemplates());
            assertNotNull(entity.getEmissions());
            assertNotNull(entity.getLength());
            assertNotNull(entity.getRelativeDelay());
            assertNotNull(entity.getSequence());
            assertNotNull(entity.getNetwork());
            assertNotNull(entity.getChannel());
            assertNotNull(entity.getClockCategory());
        });
    }

}