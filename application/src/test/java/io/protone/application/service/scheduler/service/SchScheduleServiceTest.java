package io.protone.application.service.scheduler.service;

import io.protone.application.ProtoneApp;
import io.protone.application.web.api.cor.CorNetworkResourceIntTest;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
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
    public void shouldGetSchedule() throws Exception {
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