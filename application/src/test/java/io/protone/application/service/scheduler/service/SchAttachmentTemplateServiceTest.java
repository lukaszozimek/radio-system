package io.protone.application.service.scheduler.service;

import io.protone.application.ProtoneApp;
import io.protone.application.web.api.cor.CorNetworkResourceIntTest;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.scheduler.domain.SchAttachmentTemplate;
import io.protone.scheduler.repository.SchAttachmentTemplateRepository;
import io.protone.scheduler.service.SchAttachmentTemplateService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;
import java.util.Set;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by lukaszozimek on 30/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchAttachmentTemplateServiceTest {
    protected PodamFactory factory = new PodamFactoryImpl();
    @Autowired
    private SchAttachmentTemplateService schAttachmentTemplateService;

    @Autowired
    private SchAttachmentTemplateRepository schAttachmentTemplateRepository;
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
    public void shouldSaveSchAttachmentConfiguration() throws Exception {
        //when
        SchAttachmentTemplate schAttachmentTemplate = factory.manufacturePojo(SchAttachmentTemplate.class);
        schAttachmentTemplate.setNetwork(corNetwork);
        schAttachmentTemplate.setChannel(corChannel);
        //then
        Set<SchAttachmentTemplate> fetchedEntity = schAttachmentTemplateService.saveAttachmenst(Sets.newSet(schAttachmentTemplate), null);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.stream().findFirst().get().getId());
        assertEquals(schAttachmentTemplate.getNetwork(), fetchedEntity.stream().findFirst().get().getNetwork());
    }

    @Test
    public void shouldDeleteSchAttachmentConfiguration() throws Exception {
        //when
        SchAttachmentTemplate schAttachmentTemplate = factory.manufacturePojo(SchAttachmentTemplate.class);
        schAttachmentTemplate.setNetwork(corNetwork);
        schAttachmentTemplate.setChannel(corChannel);
        schAttachmentTemplate = schAttachmentTemplateRepository.saveAndFlush(schAttachmentTemplate);
        //then
        schAttachmentTemplateService.deleteAttachments(Sets.newSet(schAttachmentTemplate));
        SchAttachmentTemplate fetchedEntity = schAttachmentTemplateRepository.findOne(schAttachmentTemplate.getId());

        //assert
        assertNull(fetchedEntity);
    }


}