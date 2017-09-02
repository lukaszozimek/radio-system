package io.protone.application.service.scheduler.service;

import io.protone.application.ProtoneApp;
import io.protone.application.service.scheduler.base.SchedulerBaseTest;
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

import javax.transaction.Transactional;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 30/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchEventEmissionAttachmentServiceTest extends SchedulerBaseTest {
    @Autowired
    private SchEventEmissionAttachmentService schEventEmissionAttachmentService;

    @Autowired
    private SchEventEmissionAttachmentRepository schEventEmissionAttachmentRepository;


    @Before
    public void setUp() throws Exception {
        super.setUp();

    }


    @Test
    public void shouldSaveSchEventEmissionAttachment() throws Exception {
        //when
        SchEventEmissionAttachment schEventEmissionAttachment = factory.manufacturePojo(SchEventEmissionAttachment.class);
        schEventEmissionAttachment.setNetwork(corNetwork);
        schEventEmissionAttachment.setChannel(corChannel);
        //then
        Set<SchEventEmissionAttachment> fetchedEntity = schEventEmissionAttachmentService.saveAttachmenst(Sets.newSet(schEventEmissionAttachment), null);

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