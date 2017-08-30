package io.protone.application.service.scheduler.service;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.repository.CorChannelRepository;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.scheduler.domain.SchClockConfiguration;
import io.protone.scheduler.repository.SchClockConfigurationRepository;
import io.protone.scheduler.service.SchClockConfigurationService;
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

import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 30/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchClockConfigurationServiceTest {
    @Autowired
    private SchClockConfigurationService schClockConfigurationService;

    @Autowired
    private CorNetworkRepository corNetworkRepository;


    @Autowired
    private CorChannelRepository corChannelRepository;

    @Autowired
    private SchClockConfigurationRepository schClockConfigurationRepository;


    private CorNetwork corNetwork;

    private CorChannel corChannel;

    private PodamFactory factory;


    @Before
    public void setUp() throws Exception {
        factory = new PodamFactoryImpl();
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corNetwork.setId(null);
        corNetwork = corNetworkRepository.saveAndFlush(corNetwork);

        corChannel = factory.manufacturePojo(CorChannel.class);
        corChannel.setId(null);
        corChannel.setShortcut("Clc");
        corChannel.network(corNetwork);
        corChannelRepository.saveAndFlush(corChannel);

    }


    @Test
    public void shouldGetClockConfigurations() throws Exception {
        //when
        SchClockConfiguration schClockConfiguration = factory.manufacturePojo(SchClockConfiguration.class);
        schClockConfiguration.setNetwork(corNetwork);
        schClockConfiguration.setChannel(corChannel);
        schClockConfiguration = schClockConfigurationRepository.save(schClockConfiguration);

        //then
        Slice<SchClockConfiguration> fetchedEntity = schClockConfigurationService.findSchClockConfigurationsForNetworkAndChannel(corNetwork.getShortcut(), corChannel.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(schClockConfiguration.getId(), fetchedEntity.getContent().get(0).getId());
        assertEquals(schClockConfiguration.getNetwork(), fetchedEntity.getContent().get(0).getNetwork());

    }

    @Test
    public void shouldSaveClockConfiguration() throws Exception {
        //when
        SchClockConfiguration schClockConfiguration = factory.manufacturePojo(SchClockConfiguration.class);
        schClockConfiguration.setNetwork(corNetwork);
        schClockConfiguration.setChannel(corChannel);
        //then
        SchClockConfiguration fetchedEntity = schClockConfigurationService.saveClockConfiguration(schClockConfiguration);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
        assertEquals(schClockConfiguration.getNetwork(), fetchedEntity.getNetwork());
    }

    @Test
    public void shouldDeleteClockConfiguration() throws Exception {
        //when
        SchClockConfiguration schClockConfiguration = factory.manufacturePojo(SchClockConfiguration.class);
        schClockConfiguration.setNetwork(corNetwork);
        schClockConfiguration.setChannel(corChannel);
        schClockConfiguration = schClockConfigurationRepository.saveAndFlush(schClockConfiguration);
        //then
        schClockConfigurationService.deleteSchClockConfigurationByNetworkAndChannelAndShortName(corNetwork.getShortcut(), corChannel.getShortcut(), schClockConfiguration.getShortName());
        SchClockConfiguration fetchedEntity = schClockConfigurationRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndShortName(corNetwork.getShortcut(), corChannel.getShortcut(), schClockConfiguration.getShortName());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void shouldGetClockConfiguration() throws Exception {
        //when
        SchClockConfiguration schClockConfiguration = factory.manufacturePojo(SchClockConfiguration.class);
        schClockConfiguration.setNetwork(corNetwork);
        schClockConfiguration.setChannel(corChannel);
        schClockConfiguration = schClockConfigurationRepository.save(schClockConfiguration);

        //then
        SchClockConfiguration fetchedEntity = schClockConfigurationService.findSchClockConfigurationForNetworkAndChannelAndShortName(corNetwork.getShortcut(), corChannel.getShortcut(), schClockConfiguration.getShortName());

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(schClockConfiguration.getId(), fetchedEntity.getId());
        assertEquals(schClockConfiguration.getNetwork(), fetchedEntity.getNetwork());

    }
}