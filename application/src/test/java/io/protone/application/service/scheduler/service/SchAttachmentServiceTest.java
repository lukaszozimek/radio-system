package io.protone.application.service.scheduler.service;

import io.protone.application.ProtoneApp;
import io.protone.application.service.scheduler.base.SchedulerBaseTest;
import io.protone.scheduler.domain.SchAttachment;
import io.protone.scheduler.repository.SchAttachmentRepository;
import io.protone.scheduler.service.SchAttachmentService;
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
 * Created by lukaszozimek on 28/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchAttachmentServiceTest extends SchedulerBaseTest {

    @Autowired
    private SchAttachmentService attachmentService;

    @Autowired
    private SchAttachmentRepository schAttachmentRepository;


    @Before
    public void setUp() throws Exception {
       super.setUp();

    }


    @Test
    public void shouldSaveSchAttachment() throws Exception {
        //when
        SchAttachment schAttachment = factory.manufacturePojo(SchAttachment.class);
        schAttachment.setNetwork(corNetwork);
        schAttachment.setChannel(corChannel);
        //then
        Set<SchAttachment> fetchedEntity = attachmentService.saveAttachmenst(Sets.newSet(schAttachment));

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.stream().findFirst().get().getId());
        assertEquals(schAttachment.getNetwork(), fetchedEntity.stream().findFirst().get().getNetwork());
    }

    @Test
    public void shouldDeleteSchAttachment() throws Exception {
        //when
        SchAttachment schAttachment = factory.manufacturePojo(SchAttachment.class);
        schAttachment.setNetwork(corNetwork);
        schAttachment.setChannel(corChannel);
        schAttachment = schAttachmentRepository.saveAndFlush(schAttachment);
        //then
        attachmentService.deleteAttachments(Sets.newSet(schAttachment));
        SchAttachment fetchedEntity = schAttachmentRepository.findOne(schAttachment.getId());

        //assert
        assertNull(fetchedEntity);
    }

}
