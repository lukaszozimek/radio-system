package io.protone.scheduler.service;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.repository.CorChannelRepository;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.scheduler.domain.SchSchedule;
import io.protone.scheduler.repository.SchScheduleRepository;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by lukaszozimek on 28/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchScheduleServiceTest {
    @Autowired
    private SchScheduleService schScheduleService;

    @Autowired
    private CorNetworkRepository corNetworkRepository;

    @Autowired
    private CorChannelRepository corChannelRepository;

    @Autowired
    private SchScheduleRepository schScheduleRepository;


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
        corChannel.setShortcut("CEv");
        corChannel.network(corNetwork);
        corChannelRepository.saveAndFlush(corChannel);

    }


    @Test
    public void shouldGetGrids() throws Exception {
        //when
        SchSchedule schSchedule = factory.manufacturePojo(SchSchedule.class);
        schSchedule.setNetwork(corNetwork);
        schSchedule.setChannel(corChannel);
        schSchedule = schScheduleRepository.save(schSchedule);

        //then
        Slice<SchSchedule> fetchedEntity = schScheduleService.findSchSchedulesForNetworkAndChannelAndShortName(corNetwork.getShortcut(), corChannel.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(schSchedule.getId(), fetchedEntity.getContent().get(0).getId());
        assertEquals(schSchedule.getGridDate(), fetchedEntity.getContent().get(0).getGridDate());
        assertEquals(schSchedule.getNetwork(), fetchedEntity.getContent().get(0).getNetwork());

    }

    @Test
    public void shouldSaveGrid() throws Exception {
        //when
        SchSchedule schSchedule = factory.manufacturePojo(SchSchedule.class);
        schSchedule.setNetwork(corNetwork);
        schSchedule.setChannel(corChannel);
        //then
        SchSchedule fetchedEntity = schScheduleService.saveGrid(schSchedule);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
        assertNotNull(fetchedEntity.getGridDate().toString(), schSchedule.getGridDate().toString());
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
        schScheduleService.deleteOneSchScheduleList(schSchedule.getGridDate(), corNetwork.getShortcut(), corChannel.getShortcut());
        SchSchedule fetchedEntity = schScheduleRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndShortName(schSchedule.getGridDate(), corNetwork.getShortcut(), corChannel.getShortcut());

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
        SchSchedule fetchedEntity = schScheduleService.findSchSchedulesForNetworkAndChannelAndShortName(schSchedule.getGridDate(), corNetwork.getShortcut(), corChannel.getShortcut());

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(schSchedule.getId(), fetchedEntity.getId());
        assertEquals(schSchedule.getGridDate(), fetchedEntity.getGridDate());
        assertEquals(schSchedule.getNetwork(), fetchedEntity.getNetwork());

    }

}