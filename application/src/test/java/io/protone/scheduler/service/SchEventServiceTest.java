package io.protone.scheduler.service;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.repository.CorChannelRepository;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.scheduler.domain.SchEvent;
import io.protone.scheduler.repository.SchEventRepository;
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
 * Created by lukaszozimek on 28/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchEventServiceTest {
    @Autowired
    private SchEventService schEventService;

    @Autowired
    private CorNetworkRepository corNetworkRepository;

    @Autowired
    private CorChannelRepository corChannelRepository;

    @Autowired
    private SchEventRepository schEventRepository;


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
    public void shouldGetEvents() throws Exception {
        //when
        SchEvent schEvent = factory.manufacturePojo(SchEvent.class);
        schEvent.setNetwork(corNetwork);
        schEvent.setChannel(corChannel);
        schEvent = schEventRepository.save(schEvent);

        //then
        Slice<SchEvent> fetchedEntity = schEventService.findSchEventsForNetworkAndChannel(corNetwork.getShortcut(), corChannel.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(schEvent.getId(), fetchedEntity.getContent().get(0).getId());
        assertEquals(schEvent.getNetwork(), fetchedEntity.getContent().get(0).getNetwork());

    }

    @Test
    public void shouldSaveEvent() throws Exception {
        //when
        SchEvent schEvent = factory.manufacturePojo(SchEvent.class);
        schEvent.setNetwork(corNetwork);
        schEvent.setChannel(corChannel);
        //then
        SchEvent fetchedEntity = schEventService.saveEvent(schEvent);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
        assertEquals(schEvent.getNetwork(), fetchedEntity.getNetwork());
    }

    @Test
    public void shouldDeleteEvent() throws Exception {
        //when
        SchEvent schEvent = factory.manufacturePojo(SchEvent.class);
        schEvent.setNetwork(corNetwork);
        schEvent.setChannel(corChannel);
        schEvent = schEventRepository.saveAndFlush(schEvent);
        //then
        schEventService.deleteSchEventByNetworkAndChannelAndShortName(corNetwork.getShortcut(), corChannel.getShortcut(), schEvent.getShortName());
        SchEvent fetchedEntity = schEventRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndShortName(corNetwork.getShortcut(), corChannel.getShortcut(), schEvent.getShortName());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void shouldGetEvent() throws Exception {
        //when
        SchEvent schEvent = factory.manufacturePojo(SchEvent.class);
        schEvent.setNetwork(corNetwork);
        schEvent.setChannel(corChannel);
        schEvent = schEventRepository.save(schEvent);

        //then
        SchEvent fetchedEntity = schEventService.findSchEventsForNetworkAndChannelAndShortName(corNetwork.getShortcut(), corChannel.getShortcut(), schEvent.getShortName());

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(schEvent.getId(), fetchedEntity.getId());

        assertEquals(schEvent.getNetwork(), fetchedEntity.getNetwork());

    }


}