package io.protone.application.service.scheduler.service;

import io.protone.application.ProtoneApp;
import io.protone.application.web.api.cor.CorNetworkResourceIntTest;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.scheduler.domain.SchEmissionTemplate;
import io.protone.scheduler.repository.SchEmissionTemplateRepository;
import io.protone.scheduler.service.SchEmissionTemplateService;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 30/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchEmissionTemplateServiceTest {
    private PodamFactory factory = new PodamFactoryImpl();

    @Autowired
    private SchEmissionTemplateService schEmissionTemplateService;

    @Autowired
    private SchEmissionTemplateRepository schEmissionTemplateRepository;

    private CorNetwork corNetwork;

    private CorChannel corChannel;

    @Before
    public void setUp() throws Exception {
        corNetwork = new CorNetwork().shortcut(CorNetworkResourceIntTest.TEST_NETWORK);
        corNetwork.setId(1L);
        corChannel = new CorChannel().shortcut("tes");
        corChannel.setId(1L);
    }


    @Test
    public void shouldSaveSchEmissionConfiguration() throws Exception {
        //when
        SchEmissionTemplate schEmissionTemplate = factory.manufacturePojo(SchEmissionTemplate.class);
        schEmissionTemplate.setNetwork(corNetwork);
        schEmissionTemplate.setChannel(corChannel);
        //then
        List<SchEmissionTemplate> fetchedEntity = schEmissionTemplateService.saveEmissionEvent(Lists.newArrayList(schEmissionTemplate), null);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.stream().findFirst().get().getId());
        assertEquals(schEmissionTemplate.getNetwork(), fetchedEntity.stream().findFirst().get().getNetwork());
    }

    @Test
    public void shouldSaveSchEmissionConfigurationWithAttachment() throws Exception {

    }

    @Test
    public void shouldDeleteSchEmissionConfiguration() throws Exception {
        //when
        SchEmissionTemplate schEmissionTemplate = factory.manufacturePojo(SchEmissionTemplate.class);
        schEmissionTemplate.setNetwork(corNetwork);
        schEmissionTemplate.setChannel(corChannel);
        schEmissionTemplate = schEmissionTemplateRepository.saveAndFlush(schEmissionTemplate);
        //then
        schEmissionTemplateService.deleteEmissions(Lists.newArrayList(schEmissionTemplate));
        SchEmissionTemplate fetchedEntity = schEmissionTemplateRepository.findOne(schEmissionTemplate.getId());

        //assert
        assertNull(fetchedEntity);
    }


    @Test
    public void shouldDeleteSchEmissionConfigurationWithAttachment() throws Exception {

    }

}