package io.protone.application.service.scheduler.service;

import com.google.common.collect.Sets;
import io.protone.application.ProtoneApp;
import io.protone.application.service.scheduler.base.SchedulerBaseTest;
import io.protone.scheduler.domain.SchLogColumn;
import io.protone.scheduler.domain.SchLogConfiguration;
import io.protone.scheduler.repository.SchLogConfigurationRepository;
import io.protone.scheduler.service.SchLogConfigurationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 04.09.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchLogConfigurationServiceTest extends SchedulerBaseTest {


    @Autowired
    private SchLogConfigurationService schLogConfigurationService;

    @Autowired
    private SchLogConfigurationRepository schLogConfigurationRepository;


    @Before
    public void setUp() throws Exception {
        super.setUp();

    }

    @Test
    public void shouldGetAllLogConfigurations() throws Exception {
        //when

        SchLogColumn schLogColumn = factory.manufacturePojo(SchLogColumn.class);
        schLogColumn.setId(null);
        schLogColumn.setChannel(corChannel);
        schLogColumn.setNetwork(corNetwork);

        SchLogConfiguration schLogConfiguration = factory.manufacturePojo(SchLogConfiguration.class);
        schLogConfiguration.setId(null);
        schLogConfiguration.setLogColumns(Sets.newHashSet(schLogColumn));
        schLogConfiguration.channel(corChannel);
        schLogConfiguration.setNetwork(corNetwork);
        schLogConfiguration = schLogConfigurationRepository.save(schLogConfiguration);

        //then
        Slice<SchLogConfiguration> fetchedEntity = schLogConfigurationService.findSchLogConfigurationForNetworkAndChannel(corNetwork.getShortcut(), corChannel.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(schLogConfiguration.getId(), fetchedEntity.getContent().get(0).getId());
        assertEquals(schLogConfiguration.getName(), fetchedEntity.getContent().get(0).getName());
        assertEquals(schLogConfiguration.getNetwork(), fetchedEntity.getContent().get(0).getNetwork());
        assertEquals(schLogConfiguration.getChannel(), fetchedEntity.getContent().get(0).getChannel());
        assertEquals(schLogConfiguration.getLogColumns(), fetchedEntity.getContent().get(0).getLogColumns());


    }

    @Test
    public void shouldSaveLogConfigurations() throws Exception {
        //when
        SchLogColumn schLogColumn = factory.manufacturePojo(SchLogColumn.class);
        schLogColumn.setId(null);
        schLogColumn.setChannel(corChannel);
        schLogColumn.setNetwork(corNetwork);

        SchLogConfiguration schLogConfiguration = factory.manufacturePojo(SchLogConfiguration.class);
        schLogConfiguration.setId(null);
        schLogConfiguration.setLogColumns(Sets.newHashSet(schLogColumn));
        schLogConfiguration.channel(corChannel);
        schLogConfiguration.setNetwork(corNetwork);
        schLogConfiguration = schLogConfigurationRepository.save(schLogConfiguration);

        //then
        SchLogConfiguration fetchedEntity = schLogConfigurationService.saveSchLogConfiguration(schLogConfiguration);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
        assertNotNull(fetchedEntity.getCreatedBy());
        assertNotNull(schLogConfiguration.getName());
        assertEquals(schLogConfiguration.getNetwork(), fetchedEntity.getNetwork());
    }

    @Test
    public void shouldDeleteLogConfigurations() throws Exception {
        //when
        SchLogColumn schLogColumn = factory.manufacturePojo(SchLogColumn.class);
        schLogColumn.setId(null);
        schLogColumn.setChannel(corChannel);
        schLogColumn.setNetwork(corNetwork);

        SchLogConfiguration schLogConfiguration = factory.manufacturePojo(SchLogConfiguration.class);
        schLogConfiguration.setId(null);
        schLogConfiguration.setLogColumns(Sets.newHashSet(schLogColumn));
        schLogConfiguration.channel(corChannel);
        schLogConfiguration.setNetwork(corNetwork);
        schLogConfiguration = schLogConfigurationRepository.save(schLogConfiguration);
        //then
        schLogConfigurationService.deleteSchLogConfigurationByNetworkAndChannelAndId(corNetwork.getShortcut(), corChannel.getShortcut(), schLogConfiguration.getExtension());
        SchLogConfiguration fetchedEntity = schLogConfigurationService.findOneSchlogConfigurationByNetworkAndChannelAndExtension(corNetwork.getShortcut(), corChannel.getShortcut(), schLogConfiguration.getExtension());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void shouldGetLogConfigurations() throws Exception {
        //when
        SchLogColumn schLogColumn = factory.manufacturePojo(SchLogColumn.class);
        schLogColumn.setId(null);
        schLogColumn.setChannel(corChannel);
        schLogColumn.setNetwork(corNetwork);

        SchLogConfiguration schLogConfiguration = factory.manufacturePojo(SchLogConfiguration.class);
        schLogConfiguration.setId(null);
        schLogConfiguration.setLogColumns(Sets.newHashSet(schLogColumn));
        schLogConfiguration.channel(corChannel);
        schLogConfiguration.setNetwork(corNetwork);
        schLogConfiguration = schLogConfigurationRepository.save(schLogConfiguration);

        //then
        SchLogConfiguration fetchedEntity = schLogConfigurationService.findOneSchlogConfigurationByNetworkAndChannelAndExtension(corNetwork.getShortcut(), corChannel.getShortcut(), schLogConfiguration.getExtension());

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getCreatedBy());
        assertEquals(schLogConfiguration.getId(), fetchedEntity.getId());
        assertEquals(schLogConfiguration.getName(), fetchedEntity.getName());
        assertEquals(schLogConfiguration.getNetwork(), fetchedEntity.getNetwork());
    }
}
