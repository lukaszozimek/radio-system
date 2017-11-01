package io.protone.application.service.scheduler.service;

import com.google.common.collect.Lists;
import io.protone.application.ProtoneApp;
import io.protone.application.web.api.cor.CorNetworkResourceIntTest;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorDictionary;
import io.protone.core.domain.CorNetwork;
import io.protone.library.domain.LibMediaItem;
import io.protone.library.domain.LibMediaLibrary;
import io.protone.library.repository.LibMediaItemRepository;
import io.protone.scheduler.api.dto.SchClockTemplateDTO;
import io.protone.scheduler.domain.SchClockTemplate;
import io.protone.scheduler.repository.SchAttachmentTemplateRepository;
import io.protone.scheduler.repository.SchClockTemplateRepository;
import io.protone.scheduler.repository.SchEmissionTemplateRepository;
import io.protone.scheduler.repository.SchEventTemplateRepository;
import io.protone.scheduler.service.SchClockTemplateService;
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
import java.util.Random;

import static io.protone.application.service.scheduler.base.SchedulerBaseTest.*;
import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 30/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchClockTemplateServiceTest {
    private static final String CLOCK_TEST_CATEGORY = "Czwartkowe";

    private PodamFactory factory = new PodamFactoryImpl();

    @Autowired
    private SchClockTemplateService schClockTemplateService;

    @Autowired
    private SchClockTemplateRepository schClockTemplateRepository;

    @Autowired
    private SchEventTemplateRepository schEventRepository;

    @Autowired
    private SchEmissionTemplateRepository schEmissionTemplateRepository;

    @Autowired
    private SchAttachmentTemplateRepository schAttachmentTemplateRepository;
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
        SchClockTemplate schClock = factory.manufacturePojo(SchClockTemplate.class);
        schClock.setNetwork(corNetwork);
        schClock.setChannel(corChannel);
        schClock = schClockTemplateRepository.save(schClock);

        //then
        Slice<SchClockTemplate> fetchedEntity = schClockTemplateService.findSchClockConfigurationsForNetworkAndChannel(corNetwork.getShortcut(), corChannel.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(schClock.getId(), fetchedEntity.getContent().get(0).getId());
        assertEquals(schClock.getNetwork(), fetchedEntity.getContent().get(0).getNetwork());

    }

    @Test
    public void shouldGetClocksGroupedByCategory() throws Exception {
        //when
        SchClockTemplate schClock = factory.manufacturePojo(SchClockTemplate.class);
        schClock.setNetwork(corNetwork);
        schClock.setChannel(corChannel);
        CorDictionary corDictionary = new CorDictionary();
        corDictionary.setId(48L);
        schClock.setClockCategory(corDictionary);
        schClock = schClockTemplateRepository.save(schClock);

        //then
        Slice<SchClockTemplate> fetchedEntity = schClockTemplateService.findAllClocksByCategoryName(corNetwork.getShortcut(), corChannel.getShortcut(), CLOCK_TEST_CATEGORY, new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(schClock.getId(), fetchedEntity.getContent().get(0).getId());
        assertEquals(schClock.getNetwork(), fetchedEntity.getContent().get(0).getNetwork());

    }

    @Test
    public void shouldSaveClock() throws Exception {
        //when
        SchClockTemplate schClock = factory.manufacturePojo(SchClockTemplate.class);
        schClock.setNetwork(corNetwork);
        schClock.setChannel(corChannel);
        //then
        SchClockTemplateDTO fetchedEntity = schClockTemplateService.saveClockConfiguration(schClock);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
    }

    @Test
    public void shouldSaveClockWithRecursiveStrategy() throws Exception {
        //when
        SchClockTemplate schClock = factory.manufacturePojo(SchClockTemplate.class);
        schClock.schEvents(buildNestedSetEvents(factory, libMediaItem, corNetwork, corChannel));
        schClock.setEmissions(Lists.newArrayList(buildEmissionConfigurationForWithAttachment(libMediaItem, corChannel, corNetwork), buildEmissionConfigurationForWithAttachment(libMediaItem, corChannel, corNetwork), buildEmissionConfigurationForWithAttachment(libMediaItem, corChannel, corNetwork)));
        schClock.setNetwork(corNetwork);
        schClock.setChannel(corChannel);
        //then
        SchClockTemplateDTO fetchedEntity = schClockTemplateService.saveClockConfiguration(schClock);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
    }

    @Test
    public void shouldDeleteClock() throws Exception {
        //when
        SchClockTemplate schClock = factory.manufacturePojo(SchClockTemplate.class);
        schClock.setNetwork(corNetwork);
        schClock.setChannel(corChannel);
        schClock = schClockTemplateRepository.saveAndFlush(schClock);
        //then
        schClockTemplateService.deleteSchClockConfigurationByNetworkAndChannelAndShortName(corNetwork.getShortcut(), corChannel.getShortcut(), schClock.getShortName());
        SchClockTemplate fetchedEntity = schClockTemplateRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndShortName(corNetwork.getShortcut(), corChannel.getShortcut(), schClock.getShortName());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void shouldDeleteClockWithBlock() throws Exception {
        SchClockTemplate schClock = factory.manufacturePojo(SchClockTemplate.class);
        schClock.schEvents(buildNestedSetEvents(factory, libMediaItem, corNetwork, corChannel));
        schClock.setEmissions(Lists.newArrayList(buildEmissionConfigurationForWithAttachment(libMediaItem, corChannel, corNetwork), buildEmissionConfigurationForWithAttachment(libMediaItem, corChannel, corNetwork), buildEmissionConfigurationForWithAttachment(libMediaItem, corChannel, corNetwork)));
        schClock.setNetwork(corNetwork);
        schClock.setChannel(corChannel);
        SchClockTemplateDTO fetchedEntity = schClockTemplateService.saveClockConfiguration(schClock);
        long clockNumberAfterSave = schClockTemplateRepository.count();
        long blockNumberAfterSave = schEventRepository.count();
        long emissionNumberAfterSave = schEmissionTemplateRepository.count();
        long attachmentNumberAfterSave = schAttachmentTemplateRepository.count();
        //then
        schClockTemplateService.deleteSchClockConfigurationByNetworkAndChannelAndShortName(schClock.getNetwork().getShortcut(), schClock.getChannel().getShortcut(), fetchedEntity.getShortName());


        assertEquals(clockNumberAfterSave - 1, schClockTemplateRepository.count());
        assertEquals(9, schEventRepository.count());
        assertEquals(3, schEmissionTemplateRepository.count());
        assertEquals(3, schAttachmentTemplateRepository.count());

    }

    @Test
    public void shouldGetClock() throws Exception {
        SchClockTemplate schClock = factory.manufacturePojo(SchClockTemplate.class);
        schClock.schEvents(buildNestedSetEvents(factory, libMediaItem, corNetwork, corChannel));
        schClock.shortName("EEEEEWWWSSS");
        schClock.setEmissions(Lists.newArrayList(buildEmissionConfigurationForWithAttachment(libMediaItem, corChannel, corNetwork), buildEmissionConfigurationForWithAttachment(libMediaItem, corChannel, corNetwork), buildEmissionConfigurationForWithAttachment(libMediaItem, corChannel, corNetwork)));
        schClock.setNetwork(corNetwork);
        schClock.setChannel(corChannel);
        schClock.setId(null);
        SchClockTemplateDTO   templateDTO = schClockTemplateService.saveClockConfiguration(schClock);

        //then
        SchClockTemplate fetchedEntity = schClockTemplateService.findSchClockConfigurationForNetworkAndChannelAndShortName(corNetwork.getShortcut(), corChannel.getShortcut(), schClock.getShortName());

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(templateDTO.getId(), fetchedEntity.getId());

    }
}