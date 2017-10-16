package io.protone.application.service.scheduler.service;

import io.protone.application.ProtoneApp;
import io.protone.application.web.api.cor.CorNetworkResourceIntTest;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.scheduler.domain.SchEventEmissionAttachment;
import io.protone.scheduler.repository.SchEventEmissionAttachmentRepository;
import io.protone.scheduler.service.SchEventEmissionAttachmentService;
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
public class SchEventEmissionAttachmentServiceTest {
    private PodamFactory factory = new PodamFactoryImpl();

    @Autowired
    private SchEventEmissionAttachmentService schEventEmissionAttachmentService;

    @Autowired
    private SchEventEmissionAttachmentRepository schEventEmissionAttachmentRepository;

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
    public void shouldSaveSchEventEmissionAttachment() throws Exception {
        //when
        SchEventEmissionAttachment schEventEmissionAttachment = factory.manufacturePojo(SchEventEmissionAttachment.class);
        schEventEmissionAttachment.setNetwork(corNetwork);
        schEventEmissionAttachment.setChannel(corChannel);
        //then
        Set<SchEventEmissionAttachment> fetchedEntity = schEventEmissionAttachmentService.saveAttachmenst(Sets.newSet(schEventEmissionAttachment));

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.stream().findFirst().get().getId());
        assertEquals(schEventEmissionAttachment.getNetwork(), fetchedEntity.stream().findFirst().get().getNetwork());
    }

    @Test
    public void shouldDeleteSchEventEmissionAttachment() throws Exception {
        //when
        SchEventEmissionAttachment schEventEmissionAttachment = factory.manufacturePojo(SchEventEmissionAttachment.class);
        schEventEmissionAttachment.setNetwork(corNetwork);
        schEventEmissionAttachment.setChannel(corChannel);
        schEventEmissionAttachment = schEventEmissionAttachmentRepository.saveAndFlush(schEventEmissionAttachment);
        //then
        schEventEmissionAttachmentService.deleteAttachments(Sets.newSet(schEventEmissionAttachment));
        SchEventEmissionAttachment fetchedEntity = schEventEmissionAttachmentRepository.findOne(schEventEmissionAttachment.getId());

        //assert
        assertNull(fetchedEntity);
    }

}