package io.protone.application.web.rest.mapper;

import io.protone.application.ProtoneApp;
import io.protone.core.api.dto.CorDictionaryDTO;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorDictionary;
import io.protone.core.domain.CorNetwork;
import io.protone.scheduler.api.dto.SchEmissionTemplateDTO;
import io.protone.scheduler.api.dto.SchEventTemplateDTO;
import io.protone.scheduler.api.dto.SchLogConfigurationDTO;
import io.protone.scheduler.domain.SchEmissionTemplate;
import io.protone.scheduler.domain.SchEventTemplate;
import io.protone.scheduler.domain.SchLogConfiguration;
import io.protone.scheduler.mapper.SchEventTemplateMapper;
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

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * Created by lukaszozimek on 30/08/2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class SchEventTemplateMapperTest {
    private static final PodamFactory factory = new PodamFactoryImpl();

    @Autowired
    private SchEventTemplateMapper schEventTemplateMapper;

    private SchEventTemplate schEventConfiguration;

    private SchEventTemplateDTO schEventTemplateDTO;

    private List<SchEventTemplate> schEventConfigurations = new ArrayList<>();

    private List<SchEventTemplateDTO> schEventTemplateDTOS = new ArrayList<>();
    private CorNetwork network;
    private CorChannel corChannel;

    @Before
    public void initPojos() {
        corChannel = factory.manufacturePojo(CorChannel.class);
        network = factory.manufacturePojo(CorNetwork.class);
        // Fill entity instance
        schEventConfiguration = factory.manufacturePojo(SchEventTemplate.class);
        schEventConfiguration.addEmissionTemplate(factory.manufacturePojo(SchEmissionTemplate.class));
        schEventConfiguration.addEmissionTemplate(factory.manufacturePojo(SchEmissionTemplate.class));
        schEventConfiguration.addEmissionTemplate(factory.manufacturePojo(SchEmissionTemplate.class));

//        schEventConfiguration.addEventTemplate(factory.manufacturePojo(SchEventTemplate.class));
//        schEventConfiguration.addEventTemplate(factory.manufacturePojo(SchEventTemplate.class));
//        schEventConfiguration.addEventTemplate(factory.manufacturePojo(SchEventTemplate.class));

        schEventConfiguration.setEventCategory(factory.manufacturePojo(CorDictionary.class));
        schEventConfiguration.setSchLogConfiguration(factory.manufacturePojo(SchLogConfiguration.class));

        schEventConfigurations.add(schEventConfiguration);

        //Fill DTO instance
        schEventTemplateDTO = factory.manufacturePojo(SchEventTemplateDTO.class);
        schEventTemplateDTO.addEmission(factory.manufacturePojo(SchEmissionTemplateDTO.class));
        schEventTemplateDTO.addEmission(factory.manufacturePojo(SchEmissionTemplateDTO.class));
        schEventTemplateDTO.addEmission(factory.manufacturePojo(SchEmissionTemplateDTO.class));
        schEventTemplateDTO.addEventsConfiguration(factory.manufacturePojo(SchEventTemplateDTO.class));
        schEventTemplateDTO.addEventsConfiguration(factory.manufacturePojo(SchEventTemplateDTO.class));
        schEventTemplateDTO.addEventsConfiguration(factory.manufacturePojo(SchEventTemplateDTO.class));


        schEventTemplateDTO.setEventCategory(factory.manufacturePojo(CorDictionaryDTO.class));
        schEventTemplateDTO.setSchLogConfiguration(factory.manufacturePojo(SchLogConfigurationDTO.class));
        schEventTemplateDTOS.add(schEventTemplateDTO);
    }

    @Test
    public void toDTO() throws Exception {
        SchEventTemplateDTO dto = schEventTemplateMapper.DB2DTO(schEventConfiguration);
        assertNotNull(dto.getName());
        assertNotNull(dto.getShortName());
        assertNotNull(dto.getEventType());
        assertNotNull(dto.getSchLogConfiguration());
        assertNotNull(dto.getEventCategory());
        assertNotNull(dto.getSchEventTemplateDTOS());
        assertNotNull(dto.getSchEventTemplateDTOS());
        assertNotNull(dto.getEmissions());
        assertNotNull(dto.getLength());
        assertNotNull(dto.getRelativeDelay());
        assertNotNull(dto.getSequence());

    }

    @Test
    public void toDTOs() throws Exception {
        List<SchEventTemplateDTO> dtos = schEventTemplateMapper.DBs2DTOs(schEventConfigurations);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getName());
            assertNotNull(dto.getShortName());
            assertNotNull(dto.getEventType());
            assertNotNull(dto.getEventCategory());
            assertNotNull(dto.getSchLogConfiguration());
            assertNotNull(dto.getSchEventTemplateDTOS());
            assertNotNull(dto.getSchEventTemplateDTOS());
            assertNotNull(dto.getEmissions());
            assertNotNull(dto.getLength());
            assertNotNull(dto.getRelativeDelay());
            assertNotNull(dto.getSequence());
        });
    }

    @Test
    public void DTO2DB() throws Exception {
        SchEventTemplate entity = schEventTemplateMapper.DTO2DB(schEventTemplateDTO, network, corChannel);

        assertNotNull(entity.getName());
        assertNotNull(entity.getShortName());
        assertNotNull(entity.getEventType());
        assertNotNull(entity.getEventCategory());
        assertNotNull(entity.getSchLogConfiguration());
        assertNotNull(entity.getEmissions());
        assertNotNull(entity.getSchEventTemplates());
        assertNotNull(entity.getNetwork());
        assertNotNull(entity.getChannel());
        assertNotNull(entity.getLength());
        assertNotNull(entity.getRelativeDelay());
        assertNotNull(entity.getSequence());
    }

    @Test
    public void toEntities() throws Exception {
        List<SchEventTemplate> entities = schEventTemplateMapper.DTOs2DBs(schEventTemplateDTOS, network, corChannel);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getName());
            assertNotNull(entity.getShortName());
            assertNotNull(entity.getEventType());
            assertNotNull(entity.getEventCategory());
            assertNotNull(entity.getSchEventTemplates());
            assertNotNull(entity.getSchLogConfiguration());
            assertNotNull(entity.getEmissions());
            assertNotNull(entity.getNetwork());
            assertNotNull(entity.getChannel());
            assertNotNull(entity.getLength());
            assertNotNull(entity.getRelativeDelay());
            assertNotNull(entity.getSequence());
        });
    }

}