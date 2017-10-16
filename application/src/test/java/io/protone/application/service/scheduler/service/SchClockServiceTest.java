package io.protone.application.service.scheduler.service;

import com.google.common.collect.Sets;
import io.protone.application.ProtoneApp;
import io.protone.application.web.api.cor.CorNetworkResourceIntTest;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.library.domain.LibMediaItem;
import io.protone.library.domain.LibMediaLibrary;
import io.protone.library.repository.LibMediaItemRepository;
import io.protone.scheduler.domain.SchClock;
import io.protone.scheduler.repository.SchAttachmentRepository;
import io.protone.scheduler.repository.SchBlockRepository;
import io.protone.scheduler.repository.SchClockRepository;
import io.protone.scheduler.repository.SchEmissionRepository;
import io.protone.scheduler.service.SchClockService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.Random;

import static io.protone.application.service.scheduler.base.SchedulerBaseTest.*;
import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 28/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchClockServiceTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Autowired
    private SchClockService schClockService;

    @Autowired
    private SchClockRepository schClockRepository;

    @Autowired
    private SchBlockRepository schBlockRepository;

    @Autowired
    private SchEmissionRepository schEmissionRepository;

    @Autowired
    private SchAttachmentRepository schAttachmentRepository;

    @Autowired
    private LibMediaItemRepository libMediaItemRepository;

    private LibMediaItem libMediaItem;

    private LibMediaLibrary libMediaLibrary;

    private CorNetwork corNetwork;

    private CorChannel corChannel;

    @Before
    public void setUp() throws Exception {
        corNetwork = new CorNetwork().shortcut(CorNetworkResourceIntTest.TEST_NETWORK);
        corNetwork.setId(1L);
        corChannel = new CorChannel().shortcut("tes");
        corChannel.setId(1L);
        libMediaLibrary = new LibMediaLibrary();
        libMediaLibrary.setId(LIBRARY_ID);
        libMediaItem = new LibMediaItem().name(String.valueOf(new Random().nextLong())).library(libMediaLibrary).idx(String.valueOf(new Random().nextLong())).length(40.0).network(corNetwork);
        libMediaItem = libMediaItemRepository.saveAndFlush(libMediaItem);

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
        SchClock fetchedEntity = schClockService.saveClock(schClock, null);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
        assertEquals(schClock.getNetwork(), fetchedEntity.getNetwork());
    }

    @Test
    public void shouldSaveClockWithRecursiveStrategy() throws Exception {
        //when
        SchClock schClock = factory.manufacturePojo(SchClock.class);
        schClock.setBlocks(buildNestedSetBlocks(factory, libMediaItem, corChannel, corNetwork));
        schClock.setEmissions(Sets.newHashSet(buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork), buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork), buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork)));
        schClock.setNetwork(corNetwork);
        schClock.setChannel(corChannel);
        //then
        SchClock fetchedEntity = schClockService.saveClock(schClock, null);

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
        schClockService.deleteSchClockByNetworkAndChannelAndShortName(corNetwork.getShortcut(), corChannel.getShortcut(), schClock.getShortName());
        SchClock fetchedEntity = schClockRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndShortName(corNetwork.getShortcut(), corChannel.getShortcut(), schClock.getShortName());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void shouldDeleteClockWithBlock() throws Exception {
        SchClock schClock = factory.manufacturePojo(SchClock.class);
        schClock.setBlocks(buildNestedSetBlocks(factory, libMediaItem, corChannel, corNetwork));
        schClock.shortName("tesSSSSSSSSSCCCCXXX");
        schClock.setEmissions(Sets.newHashSet(buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork), buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork), buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork)));
        schClock.setNetwork(corNetwork);
        schClock.setChannel(corChannel);
        schClock = schClockService.saveClock(schClock, null);
        long clockNumberAfterSave = schClockRepository.count();
        long blockNumberAfterSave = schBlockRepository.count();
        long emissionNumberAfterSave = schEmissionRepository.count();
        long attachmentNumberAfterSave = schAttachmentRepository.count();
        schClockRepository.flush();

        //then
        schClockService.deleteSchClockByNetworkAndChannelAndShortName(schClock.getNetwork().getShortcut(), schClock.getChannel().getShortcut(), schClock.getShortName());


        assertEquals(clockNumberAfterSave - 1, schClockRepository.count());
        assertEquals(blockNumberAfterSave - 9, schBlockRepository.count());
        assertEquals(emissionNumberAfterSave - 12, schEmissionRepository.count());
        assertEquals(attachmentNumberAfterSave - 12, schAttachmentRepository.count());

    }

    @Test
    public void shouldGetClock() throws Exception {
        SchClock schClock = factory.manufacturePojo(SchClock.class);
        schClock.setBlocks(buildNestedSetBlocks(factory, libMediaItem, corChannel, corNetwork));
        schClock.setEmissions(Sets.newHashSet(buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork), buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork), buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork)));
        schClock.setId(null);
        schClock.setNetwork(corNetwork);
        schClock.setChannel(corChannel);
        SchClock saveClock = schClockService.saveClock(schClock, null);

        //then
        SchClock fetchedEntity = schClockService.findSchClockForNetworkAndChannelAndShortName(corNetwork.getShortcut(), corChannel.getShortcut(), schClock.getShortName());

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(saveClock.getId(), fetchedEntity.getId());
        assertEquals(schClock.getNetwork(), fetchedEntity.getNetwork());

    }


}