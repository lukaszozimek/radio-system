package io.protone.application.service.scheduler.service;

import io.protone.application.ProtoneApp;
import io.protone.application.service.scheduler.base.SchedulerBaseTest;
import io.protone.scheduler.domain.SchAttachmentConfiguration;
import io.protone.scheduler.repository.SchAttachmentConfigurationRepository;
import io.protone.scheduler.service.SchAttachmentConfigurationService;
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
public class SchAttachmentConfigurationServiceTest extends SchedulerBaseTest {
    @Autowired
    private SchAttachmentConfigurationService schAttachmentConfigurationService;

    @Autowired
    private SchAttachmentConfigurationRepository schAttachmentConfigurationRepository;


    @Before
    public void setUp() throws Exception {
        super.setUp();

    }


    @Test
    public void shouldSaveSchAttachmentConfiguration() throws Exception {
        //when
        SchAttachmentConfiguration schAttachmentConfiguration = factory.manufacturePojo(SchAttachmentConfiguration.class);
        schAttachmentConfiguration.setNetwork(corNetwork);
        schAttachmentConfiguration.setChannel(corChannel);
        //then
        Set<SchAttachmentConfiguration> fetchedEntity = schAttachmentConfigurationService.saveAttachmenst(Sets.newSet(schAttachmentConfiguration), null);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.stream().findFirst().get().getId());
        assertEquals(schAttachmentConfiguration.getNetwork(), fetchedEntity.stream().findFirst().get().getNetwork());
    }

    @Test
    public void shouldDeleteSchAttachmentConfiguration() throws Exception {
        //when
        SchAttachmentConfiguration schAttachmentConfiguration = factory.manufacturePojo(SchAttachmentConfiguration.class);
        schAttachmentConfiguration.setNetwork(corNetwork);
        schAttachmentConfiguration.setChannel(corChannel);
        schAttachmentConfiguration = schAttachmentConfigurationRepository.saveAndFlush(schAttachmentConfiguration);
        //then
        schAttachmentConfigurationService.deleteAttachments(Sets.newSet(schAttachmentConfiguration));
        SchAttachmentConfiguration fetchedEntity = schAttachmentConfigurationRepository.findOne(schAttachmentConfiguration.getId());

        //assert
        assertNull(fetchedEntity);
    }


}