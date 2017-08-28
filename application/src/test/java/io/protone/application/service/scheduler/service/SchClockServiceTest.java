package io.protone.application.service.scheduler.service;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.repository.CorChannelRepository;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.scheduler.domain.SchClock;
import io.protone.scheduler.repository.SchClockRepository;
import io.protone.scheduler.service.SchClockService;
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
public class SchClockServiceTest {
    @Autowired
    private SchClockService schClockService;

    @Autowired
    private CorNetworkRepository corNetworkRepository;


    @Autowired
    private CorChannelRepository corChannelRepository;

    @Autowired
    private SchClockRepository schClockRepository;


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
    public void shouldGetClocks() throws Exception {
        //when
        SchClock schClock = factory.manufacturePojo(SchClock.class);
        schClock.setNetwork(corNetwork);
        schClock.setChannel(corChannel);
        schClock = schClockRepository.save(schClock);

        //then
        Slice<SchClock> fetchedEntity = schClockService.findSchClocksForNetworkAndChannel(corNetwork.getShortcut(), corChannel.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(schClock.getId(), fetchedEntity.getContent().get(0).getId());
        assertEquals(schClock.getNetwork(), fetchedEntity.getContent().get(0).getNetwork());

    }

    @Test
    public void shouldSaveClock() throws Exception {
        //when
        SchClock schClock = factory.manufacturePojo(SchClock.class);
        schClock.setNetwork(corNetwork);
        schClock.setChannel(corChannel);
        //then
        SchClock fetchedEntity = schClockService.saveClock(schClock);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
        assertEquals(schClock.getNetwork(), fetchedEntity.getNetwork());
    }

    @Test
    public void shouldDeleteClock() throws Exception {
        //when
        SchClock schClock = factory.manufacturePojo(SchClock.class);
        schClock.setNetwork(corNetwork);
        schClock.setChannel(corChannel);
        schClock = schClockRepository.saveAndFlush(schClock);
        //then
        schClockService.deleteSchClockByNetworkAndChannelAndShortName( corNetwork.getShortcut(), corChannel.getShortcut(),schClock.getShortName());
        SchClock fetchedEntity = schClockRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndShortName(corNetwork.getShortcut(), corChannel.getShortcut(), schClock.getShortName());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void shouldGetClock() throws Exception {
        //when
        SchClock schClock = factory.manufacturePojo(SchClock.class);
        schClock.setNetwork(corNetwork);
        schClock.setChannel(corChannel);
        schClock = schClockRepository.save(schClock);

        //then
        SchClock fetchedEntity = schClockService.findSchClockForNetworkAndChannelAndShortName(corNetwork.getShortcut(), corChannel.getShortcut(), schClock.getShortName());

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(schClock.getId(), fetchedEntity.getId());
        assertEquals(schClock.getNetwork(), fetchedEntity.getNetwork());

    }


}