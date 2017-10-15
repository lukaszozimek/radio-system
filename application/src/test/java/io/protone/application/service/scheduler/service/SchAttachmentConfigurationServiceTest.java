package io.protone.application.service.scheduler.service;

import io.protone.application.ProtoneApp;
import io.protone.application.web.api.cor.CorNetworkResourceIntTest;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
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
public class SchAttachmentConfigurationServiceTest {
    protected PodamFactory factory = new PodamFactoryImpl();
    @Autowired
    private SchAttachmentConfigurationService schAttachmentConfigurationService;

    @Autowired
    private SchAttachmentConfigurationRepository schAttachmentConfigurationRepository;
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