package io.protone.application.service.scheduler.service;

import com.google.common.collect.Sets;
import io.protone.application.ProtoneApp;
import io.protone.application.service.scheduler.base.SchedulerBaseTest;
import io.protone.scheduler.domain.SchClockConfiguration;
import io.protone.scheduler.repository.*;
import io.protone.scheduler.service.SchClockConfigurationService;
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
 * Created by lukaszozimek on 30/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchClockConfigurationServiceTest extends SchedulerBaseTest {

    @Autowired
    private SchClockConfigurationService schClockConfigurationService;

    @Autowired
    private SchClockConfigurationRepository schClockConfigurationRepository;

    @Autowired
    private SchEventRepository schEventRepository;

    @Autowired
    private SchEventEmissionRepository schEventEmissionRepository;

    @Autowired
    private SchEventEmissionAttachmentRepository schEventEmissionAttachmentRepository;

    @Autowired
    private SchEmissionConfigurationRepository schEmissionConfigurationRepository;

    @Autowired
    private SchAttachmentConfigurationRepository schAttachmentConfigurationRepository;

    @Before
    public void setUp() throws Exception {
        super.setUp();

    }


    @Test
    public void shouldGetClocks() throws Exception {
        //when
        SchClockConfiguration schClock = factory.manufacturePojo(SchClockConfiguration.class);
        schClock.setNetwork(corNetwork);
        schClock.setChannel(corChannel);
        schClock = schClockConfigurationRepository.save(schClock);

        //then
        Slice<SchClockConfiguration> fetchedEntity = schClockConfigurationService.findSchClockConfigurationsForNetworkAndChannel(corNetwork.getShortcut(), corChannel.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(schClock.getId(), fetchedEntity.getContent().get(0).getId());
        assertEquals(schClock.getNetwork(), fetchedEntity.getContent().get(0).getNetwork());

    }

    @Test
    public void shouldSaveClock() throws Exception {
        //when
        SchClockConfiguration schClock = factory.manufacturePojo(SchClockConfiguration.class);
        schClock.setNetwork(corNetwork);
        schClock.setChannel(corChannel);
        //then
        SchClockConfiguration fetchedEntity = schClockConfigurationService.saveClockConfiguration(schClock);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
        assertEquals(schClock.getNetwork(), fetchedEntity.getNetwork());
    }

    @Test
    public void shouldSaveClockWithRecursiveStrategy() throws Exception {
        //when
        SchClockConfiguration schClock = factory.manufacturePojo(SchClockConfiguration.class);
        schClock.events(buildNestedSetEvents());
        schClock.setEmissions(Sets.newHashSet(buildEmissionConfigurationForWithAttachment(), buildEmissionConfigurationForWithAttachment(), buildEmissionConfigurationForWithAttachment()));
        schClock.setNetwork(corNetwork);
        schClock.setChannel(corChannel);
        //then
        SchClockConfiguration fetchedEntity = schClockConfigurationService.saveClockConfiguration(schClock);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
        assertEquals(schClock.getNetwork(), fetchedEntity.getNetwork());
    }

    @Test
    public void shouldDeleteClock() throws Exception {
        //when
        SchClockConfiguration schClock = factory.manufacturePojo(SchClockConfiguration.class);
        schClock.setNetwork(corNetwork);
        schClock.setChannel(corChannel);
        schClock = schClockConfigurationRepository.saveAndFlush(schClock);
        //then
        schClockConfigurationService.deleteSchClockConfigurationByNetworkAndChannelAndShortName(corNetwork.getShortcut(), corChannel.getShortcut(), schClock.getShortName());
        SchClockConfiguration fetchedEntity = schClockConfigurationRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndShortName(corNetwork.getShortcut(), corChannel.getShortcut(), schClock.getShortName());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void shouldDeleteClockWithBlock() throws Exception {
        SchClockConfiguration schClock = factory.manufacturePojo(SchClockConfiguration.class);
        schClock.setEvents(buildNestedSetEvents());
        schClock.setEmissions(Sets.newHashSet(buildEmissionConfigurationForWithAttachment(), buildEmissionConfigurationForWithAttachment(), buildEmissionConfigurationForWithAttachment()));
        schClock.setNetwork(corNetwork);
        schClock.setChannel(corChannel);
        SchClockConfiguration fetchedEntity = schClockConfigurationService.saveClockConfiguration(schClock);
        long clockNumberAfterSave = schClockConfigurationRepository.count();
        long blockNumberAfterSave = schEventRepository.count();
        long emissionNumberAfterSave = schEmissionConfigurationRepository.count();
        long attachmentNumberAfterSave = schAttachmentConfigurationRepository.count();
        //then
        schClockConfigurationService.deleteSchClockConfigurationByNetworkAndChannelAndShortName(schClock.getNetwork().getShortcut(), schClock.getChannel().getShortcut(), schClock.getShortName());


        assertEquals(clockNumberAfterSave - 1, schClockConfigurationRepository.count());
        assertEquals(blockNumberAfterSave - 9, schEventRepository.count());
        assertEquals(emissionNumberAfterSave - 3, schEmissionConfigurationRepository.count());
        assertEquals(attachmentNumberAfterSave - 9, schAttachmentConfigurationRepository.count());

    }

    @Test
    public void shouldGetClock() throws Exception {
        SchClockConfiguration schClock = factory.manufacturePojo(SchClockConfiguration.class);
        schClock.setEvents(buildNestedSetEvents());
        schClock.setEmissions(Sets.newHashSet(buildEmissionConfigurationForWithAttachment(), buildEmissionConfigurationForWithAttachment(), buildEmissionConfigurationForWithAttachment()));
        schClock.setNetwork(corNetwork);
        schClock.setChannel(corChannel);
        schClock.setId(null);
        schClock = schClockConfigurationService.saveClockConfiguration(schClock);

        //then
        SchClockConfiguration fetchedEntity = schClockConfigurationService.findSchClockConfigurationForNetworkAndChannelAndShortName(corNetwork.getShortcut(), corChannel.getShortcut(), schClock.getShortName());

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(schClock.getId(), fetchedEntity.getId());
        assertEquals(schClock.getNetwork(), fetchedEntity.getNetwork());

    }
}