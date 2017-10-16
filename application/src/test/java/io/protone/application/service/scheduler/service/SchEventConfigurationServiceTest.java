package io.protone.application.service.scheduler.service;

import io.protone.application.ProtoneApp;
import io.protone.application.web.api.cor.CorNetworkResourceIntTest;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorDictionary;
import io.protone.core.domain.CorNetwork;
import io.protone.scheduler.domain.SchEventConfiguration;
import io.protone.scheduler.repository.SchEventConfigurationRepository;
import io.protone.scheduler.service.SchEventConfigurationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by lukaszozimek on 30/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchEventConfigurationServiceTest {
    private static final String TEST_EVENT_CATEGORY = "News";

    private PodamFactory factory = new PodamFactoryImpl();

    @Autowired
    private SchEventConfigurationService schEventConfigurationService;

    @Autowired
    private SchEventConfigurationRepository schEventConfigurationRepository;

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
    public void shouldGetEventConfigurations() throws Exception {
        //when
        SchEventConfiguration schEventConfiguration = factory.manufacturePojo(SchEventConfiguration.class);
        schEventConfiguration.setNetwork(corNetwork);
        schEventConfiguration.setChannel(corChannel);
        schEventConfiguration = schEventConfigurationRepository.save(schEventConfiguration);

        //then
        Slice<SchEventConfiguration> fetchedEntity = schEventConfigurationService.findSchEventConfigurationsForNetworkAndChannel(corNetwork.getShortcut(), corChannel.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(schEventConfiguration.getId(), fetchedEntity.getContent().get(0).getId());
        assertEquals(schEventConfiguration.getNetwork(), fetchedEntity.getContent().get(0).getNetwork());

    }


    @Test
    public void shouldGetEventConfigurationsGroupedByType() throws Exception {
        //when
        SchEventConfiguration schEventConfiguration = factory.manufacturePojo(SchEventConfiguration.class);
        schEventConfiguration.setNetwork(corNetwork);
        schEventConfiguration.setChannel(corChannel);
        CorDictionary corDictionary = new CorDictionary();
        corDictionary.setId(43L);
        schEventConfiguration.setEventCategory(corDictionary);
        schEventConfiguration = schEventConfigurationRepository.save(schEventConfiguration);

        //then
        Slice<SchEventConfiguration> fetchedEntity = schEventConfigurationService.findAllEventsByCategoryName(corNetwork.getShortcut(), corChannel.getShortcut(), TEST_EVENT_CATEGORY, new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(schEventConfiguration.getId(), fetchedEntity.getContent().get(0).getId());
        assertEquals(schEventConfiguration.getNetwork(), fetchedEntity.getContent().get(0).getNetwork());

    }

    @Test
    public void shouldSaveEventConfiguration() throws Exception {
        //when
        SchEventConfiguration schEventConfiguration = factory.manufacturePojo(SchEventConfiguration.class);
        schEventConfiguration.setNetwork(corNetwork);
        schEventConfiguration.setChannel(corChannel);
        //then
        SchEventConfiguration fetchedEntity = schEventConfigurationService.saveEventConfiguration(schEventConfiguration);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
        assertEquals(schEventConfiguration.getNetwork(), fetchedEntity.getNetwork());
    }

    @Test
    public void shouldDeleteEventConfiguration() throws Exception {
        //when
        SchEventConfiguration schEventConfiguration = factory.manufacturePojo(SchEventConfiguration.class);
        schEventConfiguration.setNetwork(corNetwork);
        schEventConfiguration.setChannel(corChannel);
        schEventConfiguration = schEventConfigurationRepository.saveAndFlush(schEventConfiguration);
        //then
        schEventConfigurationService.deleteSchEventConfigurationByNetworkAndChannelAndShortName(corNetwork.getShortcut(), corChannel.getShortcut(), schEventConfiguration.getShortName());
        SchEventConfiguration fetchedEntity = schEventConfigurationRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndShortName(corNetwork.getShortcut(), corChannel.getShortcut(), schEventConfiguration.getShortName());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void shouldDeleteEventConfigurationWithRecusriveStrategy() throws Exception {

    }


    @Test
    public void shouldGetEventConfiguration() throws Exception {
        //when
        SchEventConfiguration schEventConfiguration = factory.manufacturePojo(SchEventConfiguration.class);
        schEventConfiguration.setNetwork(corNetwork);
        schEventConfiguration.setChannel(corChannel);
        schEventConfiguration = schEventConfigurationRepository.save(schEventConfiguration);

        //then
        SchEventConfiguration fetchedEntity = schEventConfigurationService.findSchEventConfigurationsForNetworkAndChannelAndShortName(corNetwork.getShortcut(), corChannel.getShortcut(), schEventConfiguration.getShortName());
        //assert
        assertNotNull(fetchedEntity);
        assertEquals(schEventConfiguration.getId(), fetchedEntity.getId());

        assertEquals(schEventConfiguration.getNetwork(), fetchedEntity.getNetwork());

    }

}