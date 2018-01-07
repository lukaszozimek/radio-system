package io.protone.application.service.scheduler.service;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorOrganization;
import io.protone.scheduler.domain.SchAttachment;
import io.protone.scheduler.repository.SchAttachmentRepository;
import io.protone.scheduler.service.SchAttachmentService;
import org.assertj.core.util.Lists;
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
import java.util.List;

import static io.protone.application.util.TestConstans.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by lukaszozimek on 28/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchAttachmentServiceTest {
    protected PodamFactory factory = new PodamFactoryImpl();

    @Autowired
    private SchAttachmentService attachmentService;

    @Autowired
    private SchAttachmentRepository schAttachmentRepository;

    private CorChannel corChannel;

    private CorOrganization corOrganization;

    @Before
    public void setUp() throws Exception {
        factory = new PodamFactoryImpl();
        corOrganization = new CorOrganization().shortcut(TEST_ORGANIZATION_SHORTCUT);
        corOrganization.setId(TEST_ORGANIZATION_ID);
        corChannel = new CorChannel().shortcut(TEST_CHANNEL_SHORTCUT);
        corChannel.setId(TEST_CHANNEL_ID);
    }


    @Test
    public void shouldSaveSchAttachment() throws Exception {
        //when
        SchAttachment schAttachment = factory.manufacturePojo(SchAttachment.class);
        schAttachment.setChannel(corChannel);
        //then
        List<SchAttachment> fetchedEntity = attachmentService.saveAttachmenst(Lists.newArrayList(schAttachment));

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.stream().findFirst().get().getId());

    }

    @Test
    public void shouldDeleteSchAttachment() throws Exception {
        //when
        SchAttachment schAttachment = factory.manufacturePojo(SchAttachment.class);
        schAttachment.setChannel(corChannel);
        schAttachment = schAttachmentRepository.saveAndFlush(schAttachment);
        //then
        attachmentService.deleteAttachments(Sets.newSet(schAttachment));
        SchAttachment fetchedEntity = schAttachmentRepository.findOne(schAttachment.getId());

        //assert
        assertNull(fetchedEntity);
    }

}
