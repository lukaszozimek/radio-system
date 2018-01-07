package io.protone.application.service.scheduler.service;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorOrganization;
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
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;

import static io.protone.application.util.TestConstans.*;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by lukaszozimek on 28/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchScheduleServiceTest {
    private PodamFactory factory = new PodamFactoryImpl();

    @Autowired
    private SchScheduleService schScheduleService;

    @Autowired
    private SchScheduleRepository schScheduleRepository;

    protected CorChannel corChannel;

    protected CorOrganization corOrganization;

    @Before
    public void setUp() throws Exception {

        corOrganization = new CorOrganization().shortcut(TEST_ORGANIZATION_SHORTCUT);
        corOrganization.setId(TEST_ORGANIZATION_ID);
        corChannel = new CorChannel().shortcut(TEST_CHANNEL_SHORTCUT);
        corChannel.setId(TEST_CHANNEL_ID);

    }

    @Test
    public void shouldGetSchedule() throws Exception {
        //when
        SchSchedule schSchedule = factory.manufacturePojo(SchSchedule.class);
        schSchedule.setChannel(corChannel);
        schSchedule = schScheduleRepository.save(schSchedule);

        //then
        Slice<SchSchedule> fetchedEntity = schScheduleService.findSchSchedulesForNetworkAndChannel(corOrganization.getShortcut(), corChannel.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(schSchedule.getId(), fetchedEntity.getContent().get(0).getId());

    }

    @Test
    public void shouldSaveGrid() throws Exception {
        //when
        SchSchedule schSchedule = factory.manufacturePojo(SchSchedule.class);
        schSchedule.setChannel(corChannel);
        //then
        SchSchedule fetchedEntity = schScheduleService.saveSchedule(schSchedule);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
    }

    @Test
    public void shouldDeleteGrid() throws Exception {
        //when
        SchSchedule schSchedule = factory.manufacturePojo(SchSchedule.class);
        schSchedule.setChannel(corChannel);
        schSchedule = schScheduleRepository.saveAndFlush(schSchedule);
        //then
        schScheduleService.deleteSchScheduleByNetworkAndChannelAndDate(corOrganization.getShortcut(), corChannel.getShortcut(), schSchedule.getDate());
        SchSchedule fetchedEntity = schScheduleRepository.findOneByChannel_Organization_ShortcutAndChannel_ShortcutAndDate(corOrganization.getShortcut(), corChannel.getShortcut(), schSchedule.getDate());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void shouldGetGrid() throws Exception {
        //when
        SchSchedule schSchedule = factory.manufacturePojo(SchSchedule.class);
        schSchedule.setChannel(corChannel);
        schSchedule = schScheduleRepository.save(schSchedule);

        //then
        SchScheduleDTO fetchedEntity = schScheduleService.findSchScheduleForNetworkAndChannelAndDate(corOrganization.getShortcut(), corChannel.getShortcut(), schSchedule.getDate());

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(schSchedule.getId(), fetchedEntity.getId());

    }

}