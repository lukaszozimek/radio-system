package io.protone.application.service.scheduler.service;

import io.protone.application.ProtoneApp;
import io.protone.application.service.scheduler.base.SchedulerBaseTest;
import io.protone.scheduler.api.dto.SchScheduleDTO;
import io.protone.scheduler.domain.SchSchedule;
import io.protone.scheduler.repository.SchScheduleRepository;
import io.protone.scheduler.service.SchScheduleService;
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
 * Created by lukaszozimek on 28/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchScheduleServiceTest extends SchedulerBaseTest {
    @Autowired
    private SchScheduleService schScheduleService;

    @Autowired
    private SchScheduleRepository schScheduleRepository;

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }
    @Test
    public void shouldGetGrids() throws Exception {
        //when
        SchSchedule schSchedule = factory.manufacturePojo(SchSchedule.class);
        schSchedule.setNetwork(corNetwork);
        schSchedule.setChannel(corChannel);
        schSchedule = schScheduleRepository.save(schSchedule);

        //then
        Slice<SchSchedule> fetchedEntity = schScheduleService.findSchSchedulesForNetworkAndChannel(corNetwork.getShortcut(), corChannel.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(schSchedule.getId(), fetchedEntity.getContent().get(0).getId());
        assertEquals(schSchedule.getNetwork(), fetchedEntity.getContent().get(0).getNetwork());

    }

    @Test
    public void shouldSaveGrid() throws Exception {
        //when
        SchSchedule schSchedule = factory.manufacturePojo(SchSchedule.class);
        schSchedule.setNetwork(corNetwork);
        schSchedule.setChannel(corChannel);
        //then
        SchSchedule fetchedEntity = schScheduleService.saveSchedule(schSchedule);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
        assertEquals(schSchedule.getNetwork(), fetchedEntity.getNetwork());
    }

    @Test
    public void shouldDeleteGrid() throws Exception {
        //when
        SchSchedule schSchedule = factory.manufacturePojo(SchSchedule.class);
        schSchedule.setNetwork(corNetwork);
        schSchedule.setChannel(corChannel);
        schSchedule = schScheduleRepository.saveAndFlush(schSchedule);
        //then
        schScheduleService.deleteSchScheduleByNetworkAndChannelAndDate(corNetwork.getShortcut(), corChannel.getShortcut(), schSchedule.getDate());
        SchSchedule fetchedEntity = schScheduleRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndDate(corNetwork.getShortcut(), corChannel.getShortcut(), schSchedule.getDate());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void shouldGetGrid() throws Exception {
        //when
        SchSchedule schSchedule = factory.manufacturePojo(SchSchedule.class);
        schSchedule.setNetwork(corNetwork);
        schSchedule.setChannel(corChannel);
        schSchedule = schScheduleRepository.save(schSchedule);

        //then
        SchScheduleDTO fetchedEntity = schScheduleService.findSchScheduleForNetworkAndChannelAndDate(corNetwork.getShortcut(), corChannel.getShortcut(), schSchedule.getDate());

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(schSchedule.getId(), fetchedEntity.getId());

    }

}