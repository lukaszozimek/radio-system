package io.protone.application.service.scheduler.service;

import io.protone.application.ProtoneApp;
import io.protone.application.web.api.cor.CorNetworkResourceIntTest;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.library.domain.LibMediaItem;
import io.protone.library.domain.LibMediaLibrary;
import io.protone.library.repository.LibMediaItemRepository;
import io.protone.scheduler.domain.SchEvent;
import io.protone.scheduler.repository.SchEventEmissionAttachmentRepository;
import io.protone.scheduler.repository.SchEventEmissionRepository;
import io.protone.scheduler.repository.SchEventRepository;
import io.protone.scheduler.service.SchEventService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;
import java.util.Set;

import static io.protone.application.service.scheduler.base.SchedulerBaseTest.*;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by lukaszozimek on 28/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchEventServiceTest {
    private PodamFactory factory = new PodamFactoryImpl();

    @Autowired
    private SchEventService schEventService;

    @Autowired
    private SchEventRepository schEventRepository;

    @Autowired
    private SchEventEmissionRepository schEventEmissionRepository;

    @Autowired
    private SchEventEmissionAttachmentRepository schEventEmissionAttachmentRepository;
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
        libMediaItem = new LibMediaItem().name("test").library(libMediaLibrary).idx("test").length(40.0).network(corNetwork);
        libMediaItem = libMediaItemRepository.saveAndFlush(libMediaItem);
    }


    @Test
    public void shouldSaveSchEvent() throws Exception {
        //when
        SchEvent schEvent = factory.manufacturePojo(SchEvent.class);
        schEvent.setNetwork(corNetwork);
        schEvent.setChannel(corChannel);
        //then
        Set<SchEvent> fetchedEntity = schEventService.saveEvent(Sets.newSet(schEvent));

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.stream().findFirst().get().getId());
        assertEquals(schEvent.getNetwork(), fetchedEntity.stream().findFirst().get().getNetwork());
    }

    @Test
    public void shouldSaveSchEventWithRecusiveStrategy() throws Exception {
        //ROOT Chil Child
        SchEvent schEventRootChildChild = factory.manufacturePojo(SchEvent.class);
        schEventRootChildChild.setId(null);
        schEventRootChildChild.setNetwork(corNetwork);
        schEventRootChildChild.setChannel(corChannel);

        SchEvent schEventRootChildChild1 = factory.manufacturePojo(SchEvent.class);
        schEventRootChildChild1.setId(null);
        schEventRootChildChild1.setNetwork(corNetwork);
        schEventRootChildChild1.setChannel(corChannel);

        SchEvent schEventRootChildChild2 = factory.manufacturePojo(SchEvent.class);
        schEventRootChildChild2.setId(null);
        schEventRootChildChild2.setNetwork(corNetwork);
        schEventRootChildChild2.setChannel(corChannel);

        //ROOTS Childs
        SchEvent schEventRootChild = factory.manufacturePojo(SchEvent.class);
        schEventRootChild.setId(null);
        schEventRootChild.setNetwork(corNetwork);
        schEventRootChild.setChannel(corChannel);
        schEventRootChild.addSchEvents(schEventRootChildChild);

        SchEvent schEventRootChild1 = factory.manufacturePojo(SchEvent.class);
        schEventRootChild1.setId(null);
        schEventRootChild1.setNetwork(corNetwork);
        schEventRootChild1.setChannel(corChannel);
        schEventRootChild1.addSchEvents(schEventRootChildChild1);

        SchEvent schEventRootChild2 = factory.manufacturePojo(SchEvent.class);
        schEventRootChild2.setId(null);
        schEventRootChild2.setNetwork(corNetwork);
        schEventRootChild2.setChannel(corChannel);
        schEventRootChild2.addSchEvents(schEventRootChildChild2);

        ///ROOTS
        SchEvent schEventRoot = factory.manufacturePojo(SchEvent.class);
        schEventRoot.setId(null);
        schEventRoot.setNetwork(corNetwork);
        schEventRoot.setChannel(corChannel);
        schEventRoot.addSchEvents(schEventRootChild);

        SchEvent schEventRoot1 = factory.manufacturePojo(SchEvent.class);
        schEventRoot1.setId(null);
        schEventRoot1.setNetwork(corNetwork);
        schEventRoot1.setChannel(corChannel);
        schEventRoot1.addSchEvents(schEventRootChild1);

        SchEvent schEventRoot2 = factory.manufacturePojo(SchEvent.class);
        schEventRoot2.setId(null);
        schEventRoot2.setNetwork(corNetwork);
        schEventRoot2.setChannel(corChannel);
        schEventRoot2.addSchEvents(schEventRootChild2);


        //then
        Set<SchEvent> fetchedEntity = schEventService.saveEvent(Sets.newSet(schEventRoot, schEventRoot1, schEventRoot2));

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(fetchedEntity.size(), 3);
        fetchedEntity.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getSchEvents());
            assertEquals(entity.getSchEvents().size(), 1);
            entity.getSchEvents().stream().forEach(entityBlock -> {
                assertNotNull(entityBlock.getId());
                assertNotNull(entityBlock.getSchEvents());
                assertEquals(entityBlock.getSchEvents().size(), 1);
            });
        });

    }

    @Test
    public void shouldSaveSchEventContainigEmissionsWithRecusiveStrategy() throws Exception {
        //ROOT Chil Child
        SchEvent schEventRootChildChild = factory.manufacturePojo(SchEvent.class);
        schEventRootChildChild.addEventEmissions(buildSchEventEmissionForSchEvent(libMediaItem, corChannel, corNetwork));
        schEventRootChildChild.setId(null);
        schEventRootChildChild.setNetwork(corNetwork);
        schEventRootChildChild.setChannel(corChannel);

        SchEvent schEventRootChildChild1 = factory.manufacturePojo(SchEvent.class);

        schEventRootChildChild1.addEventEmissions(buildSchEventEmissionForSchEvent(libMediaItem, corChannel, corNetwork));
        schEventRootChildChild1.setId(null);
        schEventRootChildChild1.setNetwork(corNetwork);
        schEventRootChildChild1.setChannel(corChannel);

        SchEvent schEventRootChildChild2 = factory.manufacturePojo(SchEvent.class);

        schEventRootChildChild2.addEventEmissions(buildSchEventEmissionForSchEvent(libMediaItem, corChannel, corNetwork));
        schEventRootChildChild2.setId(null);
        schEventRootChildChild2.setNetwork(corNetwork);
        schEventRootChildChild2.setChannel(corChannel);

        //ROOTS Childs
        SchEvent schEventRootChild = factory.manufacturePojo(SchEvent.class);
        schEventRootChild.addEventEmissions(buildSchEventEmissionForSchEvent(libMediaItem, corChannel, corNetwork));
        schEventRootChild.setId(null);
        schEventRootChild.setNetwork(corNetwork);
        schEventRootChild.setChannel(corChannel);
        schEventRootChild.addSchEvents(schEventRootChildChild);

        SchEvent schEventRootChild1 = factory.manufacturePojo(SchEvent.class);
        schEventRootChild1.addEventEmissions(buildSchEventEmissionForSchEvent(libMediaItem, corChannel, corNetwork));
        schEventRootChild1.setId(null);
        schEventRootChild1.setNetwork(corNetwork);
        schEventRootChild1.setChannel(corChannel);
        schEventRootChild1.addSchEvents(schEventRootChildChild1);

        SchEvent schEventRootChild2 = factory.manufacturePojo(SchEvent.class);
        schEventRootChild2.addEventEmissions(buildSchEventEmissionForSchEvent(libMediaItem, corChannel, corNetwork));
        schEventRootChild2.setId(null);
        schEventRootChild2.setNetwork(corNetwork);
        schEventRootChild2.setChannel(corChannel);
        schEventRootChild2.addSchEvents(schEventRootChildChild2);

        ///ROOTS
        SchEvent schEventRoot = factory.manufacturePojo(SchEvent.class);
        schEventRoot.addEventEmissions(buildSchEventEmissionForSchEvent(libMediaItem, corChannel, corNetwork));
        schEventRoot.setId(null);
        schEventRoot.setNetwork(corNetwork);
        schEventRoot.setChannel(corChannel);
        schEventRoot.addSchEvents(schEventRootChild);

        SchEvent schEventRoot1 = factory.manufacturePojo(SchEvent.class);
        schEventRoot1.addEventEmissions(buildSchEventEmissionForSchEvent(libMediaItem, corChannel, corNetwork));
        schEventRoot1.setId(null);
        schEventRoot1.setNetwork(corNetwork);
        schEventRoot1.setChannel(corChannel);
        schEventRoot1.addSchEvents(schEventRootChild1);

        SchEvent schEventRoot2 = factory.manufacturePojo(SchEvent.class);
        schEventRoot2.addEventEmissions(buildSchEventEmissionForSchEvent(libMediaItem, corChannel, corNetwork));
        schEventRoot2.setId(null);
        schEventRoot2.setNetwork(corNetwork);
        schEventRoot2.setChannel(corChannel);
        schEventRoot2.addSchEvents(schEventRootChild2);


        //then
        Set<SchEvent> fetchedEntity = schEventService.saveEvent(Sets.newSet(schEventRoot, schEventRoot1, schEventRoot2));

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(fetchedEntity.size(), 3);
        fetchedEntity.stream().forEach(entity -> {
            assertEquals(1, entity.getEmissions().size());
            assertNotNull(entity.getEmissions().stream().findFirst().get().getId());
            assertNotNull(entity.getId());
            assertNotNull(entity.getEmissions());
            assertNotNull(entity.getSchEvents());
            assertEquals(entity.getSchEvents().size(), 1);
            entity.getSchEvents().stream().forEach(entityBlock -> {
                assertEquals(1, entityBlock.getEmissions().size());
                assertNotNull(entityBlock.getEmissions().stream().findFirst().get().getId());
                assertNotNull(entityBlock.getId());
                assertNotNull(entityBlock.getSchEvents());
                assertEquals(entityBlock.getSchEvents().size(), 1);
            });
        });
    }

    @Test
    public void shouldSaveSchEventContainigEmissionsAttachmentWithRecusiveStrategy() throws Exception {
        //ROOT Chil Child
        SchEvent schEventRootChildChild = factory.manufacturePojo(SchEvent.class);
        schEventRootChildChild.addEventEmissions(buildSchEventEmissionForSchEventWithAttachment(libMediaItem, corChannel, corNetwork));
        schEventRootChildChild.setId(null);
        schEventRootChildChild.setNetwork(corNetwork);
        schEventRootChildChild.setChannel(corChannel);

        SchEvent schEventRootChildChild1 = factory.manufacturePojo(SchEvent.class);

        schEventRootChildChild1.addEventEmissions(buildSchEventEmissionForSchEventWithAttachment(libMediaItem, corChannel, corNetwork));
        schEventRootChildChild1.setId(null);
        schEventRootChildChild1.setNetwork(corNetwork);
        schEventRootChildChild1.setChannel(corChannel);

        SchEvent schEventRootChildChild2 = factory.manufacturePojo(SchEvent.class);

        schEventRootChildChild2.addEventEmissions(buildSchEventEmissionForSchEventWithAttachment(libMediaItem, corChannel, corNetwork));
        schEventRootChildChild2.setId(null);
        schEventRootChildChild2.setNetwork(corNetwork);
        schEventRootChildChild2.setChannel(corChannel);

        //ROOTS Childs
        SchEvent schEventRootChild = factory.manufacturePojo(SchEvent.class);
        schEventRootChild.addEventEmissions(buildSchEventEmissionForSchEventWithAttachment(libMediaItem, corChannel, corNetwork));
        schEventRootChild.setId(null);
        schEventRootChild.setNetwork(corNetwork);
        schEventRootChild.setChannel(corChannel);
        schEventRootChild.addSchEvents(schEventRootChildChild);

        SchEvent schEventRootChild1 = factory.manufacturePojo(SchEvent.class);
        schEventRootChild1.addEventEmissions(buildSchEventEmissionForSchEventWithAttachment(libMediaItem, corChannel, corNetwork));
        schEventRootChild1.setId(null);
        schEventRootChild1.setNetwork(corNetwork);
        schEventRootChild1.setChannel(corChannel);
        schEventRootChild1.addSchEvents(schEventRootChildChild1);

        SchEvent schEventRootChild2 = factory.manufacturePojo(SchEvent.class);
        schEventRootChild2.addEventEmissions(buildSchEventEmissionForSchEventWithAttachment(libMediaItem, corChannel, corNetwork));
        schEventRootChild2.setId(null);
        schEventRootChild2.setNetwork(corNetwork);
        schEventRootChild2.setChannel(corChannel);
        schEventRootChild2.addSchEvents(schEventRootChildChild2);

        ///ROOTS
        SchEvent schEventRoot = factory.manufacturePojo(SchEvent.class);
        schEventRoot.addEventEmissions(buildSchEventEmissionForSchEventWithAttachment(libMediaItem, corChannel, corNetwork));
        schEventRoot.setId(null);
        schEventRoot.setNetwork(corNetwork);
        schEventRoot.setChannel(corChannel);
        schEventRoot.addSchEvents(schEventRootChild);

        SchEvent schEventRoot1 = factory.manufacturePojo(SchEvent.class);
        schEventRoot1.addEventEmissions(buildSchEventEmissionForSchEventWithAttachment(libMediaItem, corChannel, corNetwork));
        schEventRoot1.setId(null);
        schEventRoot1.setNetwork(corNetwork);
        schEventRoot1.setChannel(corChannel);
        schEventRoot1.addSchEvents(schEventRootChild1);

        SchEvent schEventRoot2 = factory.manufacturePojo(SchEvent.class);
        schEventRoot2.addEventEmissions(buildSchEventEmissionForSchEventWithAttachment(libMediaItem, corChannel, corNetwork));
        schEventRoot2.setId(null);
        schEventRoot2.setNetwork(corNetwork);
        schEventRoot2.setChannel(corChannel);
        schEventRoot2.addSchEvents(schEventRootChild2);


        //then
        Set<SchEvent> fetchedEntity = schEventService.saveEvent(Sets.newSet(schEventRoot, schEventRoot1, schEventRoot2));

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(fetchedEntity.size(), 3);
        fetchedEntity.stream().forEach(entity -> {
            assertEquals(1, entity.getEmissions().size());
            assertNotNull(entity.getEmissions().stream().findFirst().get().getId());
            assertEquals(1, entity.getEmissions().stream().findFirst().get().getAttachments().size());
            assertNotNull(entity.getEmissions().stream().findFirst().get().getAttachments().stream().findFirst().get().getId());
            assertNotNull(entity.getId());
            assertNotNull(entity.getEmissions());
            assertNotNull(entity.getSchEvents());
            assertEquals(entity.getSchEvents().size(), 1);
            entity.getSchEvents().stream().forEach(entityBlock -> {
                assertNotNull(entityBlock.getEmissions().stream().findFirst().get().getId());
                assertEquals(1, entityBlock.getEmissions().size());
                assertEquals(1, entityBlock.getEmissions().stream().findFirst().get().getAttachments().size());
                assertNotNull(entityBlock.getEmissions().stream().findFirst().get().getAttachments().stream().findFirst().get().getId());
                assertNotNull(entityBlock.getId());
                assertNotNull(entityBlock.getSchEvents());
                assertEquals(entityBlock.getSchEvents().size(), 1);
            });
        });
    }


    @Test
    public void shouldDeleteSchEvent() throws Exception {
        //when
        SchEvent schEvent = factory.manufacturePojo(SchEvent.class);
        schEvent.setNetwork(corNetwork);
        schEvent.setChannel(corChannel);
        schEvent = schEventRepository.save(schEvent);
        //then
        schEventService.deleteEvent(Sets.newSet(schEvent));
        SchEvent fetchedEntity = schEventRepository.findOne(schEvent.getId());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void shouldDeleteSchEventWithRecursiveStrategy() throws Exception {

        //ROOT Chil Child
        SchEvent schEventRootChildChild = factory.manufacturePojo(SchEvent.class);
        schEventRootChildChild.setId(null);
        schEventRootChildChild.setNetwork(corNetwork);
        schEventRootChildChild.setChannel(corChannel);

        SchEvent schEventRootChildChild1 = factory.manufacturePojo(SchEvent.class);
        schEventRootChildChild1.setId(null);
        schEventRootChildChild1.setNetwork(corNetwork);
        schEventRootChildChild1.setChannel(corChannel);

        SchEvent schEventRootChildChild2 = factory.manufacturePojo(SchEvent.class);
        schEventRootChildChild2.setId(null);
        schEventRootChildChild2.setNetwork(corNetwork);
        schEventRootChildChild2.setChannel(corChannel);

        //ROOTS Childs
        SchEvent schEventRootChild = factory.manufacturePojo(SchEvent.class);
        schEventRootChild.setId(null);
        schEventRootChild.setNetwork(corNetwork);
        schEventRootChild.setChannel(corChannel);
        schEventRootChild.addSchEvents(schEventRootChildChild);

        SchEvent schEventRootChild1 = factory.manufacturePojo(SchEvent.class);
        schEventRootChild1.setId(null);
        schEventRootChild1.setNetwork(corNetwork);
        schEventRootChild1.setChannel(corChannel);
        schEventRootChild1.addSchEvents(schEventRootChildChild1);

        SchEvent schEventRootChild2 = factory.manufacturePojo(SchEvent.class);
        schEventRootChild2.setId(null);
        schEventRootChild2.setNetwork(corNetwork);
        schEventRootChild2.setChannel(corChannel);
        schEventRootChild2.addSchEvents(schEventRootChildChild2);

        ///ROOTS
        SchEvent schEventRoot = factory.manufacturePojo(SchEvent.class);
        schEventRoot.setId(null);
        schEventRoot.setNetwork(corNetwork);
        schEventRoot.setChannel(corChannel);
        schEventRoot.addSchEvents(schEventRootChild);

        SchEvent schEventRoot1 = factory.manufacturePojo(SchEvent.class);
        schEventRoot1.setId(null);
        schEventRoot1.setNetwork(corNetwork);
        schEventRoot1.setChannel(corChannel);
        schEventRoot1.addSchEvents(schEventRootChild1);

        SchEvent schEventRoot2 = factory.manufacturePojo(SchEvent.class);
        schEventRoot2.setId(null);
        schEventRoot2.setNetwork(corNetwork);
        schEventRoot2.setChannel(corChannel);
        schEventRoot2.addSchEvents(schEventRootChild2);


        //then
        Set<SchEvent> fetchedEntity = schEventService.saveEvent(Sets.newSet(schEventRoot, schEventRoot1, schEventRoot2));

        //assert
        long dbSize = schEventRepository.count();
        schEventService.deleteEvent(fetchedEntity);
        long dbSizeAfterDelete = schEventRepository.count();
        assertEquals(dbSize - 9, dbSizeAfterDelete);
    }

    @Test
    public void shouldDeleteSchEventContainigEmissionsWithRecusiveStrategy() throws Exception {
        //ROOT Chil Child
        SchEvent schEventRootChildChild = factory.manufacturePojo(SchEvent.class);
        schEventRootChildChild.addEventEmissions(buildSchEventEmissionForSchEvent(libMediaItem, corChannel, corNetwork));
        schEventRootChildChild.setId(null);
        schEventRootChildChild.setNetwork(corNetwork);
        schEventRootChildChild.setChannel(corChannel);

        SchEvent schEventRootChildChild1 = factory.manufacturePojo(SchEvent.class);

        schEventRootChildChild1.addEventEmissions(buildSchEventEmissionForSchEvent(libMediaItem, corChannel, corNetwork));
        schEventRootChildChild1.setId(null);
        schEventRootChildChild1.setNetwork(corNetwork);
        schEventRootChildChild1.setChannel(corChannel);

        SchEvent schEventRootChildChild2 = factory.manufacturePojo(SchEvent.class);

        schEventRootChildChild2.addEventEmissions(buildSchEventEmissionForSchEvent(libMediaItem, corChannel, corNetwork));
        schEventRootChildChild2.setId(null);
        schEventRootChildChild2.setNetwork(corNetwork);
        schEventRootChildChild2.setChannel(corChannel);

        //ROOTS Childs
        SchEvent schEventRootChild = factory.manufacturePojo(SchEvent.class);
        schEventRootChild.addEventEmissions(buildSchEventEmissionForSchEvent(libMediaItem, corChannel, corNetwork));
        schEventRootChild.setId(null);
        schEventRootChild.setNetwork(corNetwork);
        schEventRootChild.setChannel(corChannel);
        schEventRootChild.addSchEvents(schEventRootChildChild);

        SchEvent schEventRootChild1 = factory.manufacturePojo(SchEvent.class);
        schEventRootChild1.addEventEmissions(buildSchEventEmissionForSchEvent(libMediaItem, corChannel, corNetwork));
        schEventRootChild1.setId(null);
        schEventRootChild1.setNetwork(corNetwork);
        schEventRootChild1.setChannel(corChannel);
        schEventRootChild1.addSchEvents(schEventRootChildChild1);

        SchEvent schEventRootChild2 = factory.manufacturePojo(SchEvent.class);
        schEventRootChild2.addEventEmissions(buildSchEventEmissionForSchEvent(libMediaItem, corChannel, corNetwork));
        schEventRootChild2.setId(null);
        schEventRootChild2.setNetwork(corNetwork);
        schEventRootChild2.setChannel(corChannel);
        schEventRootChild2.addSchEvents(schEventRootChildChild2);

        ///ROOTS
        SchEvent schEventRoot = factory.manufacturePojo(SchEvent.class);
        schEventRoot.addEventEmissions(buildSchEventEmissionForSchEvent(libMediaItem, corChannel, corNetwork));
        schEventRoot.setId(null);
        schEventRoot.setNetwork(corNetwork);
        schEventRoot.setChannel(corChannel);
        schEventRoot.addSchEvents(schEventRootChild);

        SchEvent schEventRoot1 = factory.manufacturePojo(SchEvent.class);
        schEventRoot1.addEventEmissions(buildSchEventEmissionForSchEvent(libMediaItem, corChannel, corNetwork));
        schEventRoot1.setId(null);
        schEventRoot1.setNetwork(corNetwork);
        schEventRoot1.setChannel(corChannel);
        schEventRoot1.addSchEvents(schEventRootChild1);

        SchEvent schEventRoot2 = factory.manufacturePojo(SchEvent.class);
        schEventRoot2.addEventEmissions(buildSchEventEmissionForSchEvent(libMediaItem, corChannel, corNetwork));
        schEventRoot2.setId(null);
        schEventRoot2.setNetwork(corNetwork);
        schEventRoot2.setChannel(corChannel);
        schEventRoot2.addSchEvents(schEventRootChild2);


        Set<SchEvent> fetchedEntity = schEventService.saveEvent(Sets.newSet(schEventRoot, schEventRoot1, schEventRoot2));
        long dbSizeBlocks = schEventRepository.count();
        long emissionDbSize = schEventEmissionRepository.count();
        //then
        schEventService.deleteEvent(fetchedEntity);
        long dbSizeAfterDelete = schEventRepository.count();
        long emissionDbSizeAfterDelete = schEventEmissionRepository.count();

        //assert
        assertEquals(dbSizeBlocks - 9, dbSizeAfterDelete);
        assertEquals(emissionDbSize - 9, dbSizeAfterDelete);
    }

    @Test
    public void shouldDeleteSchEventContainigEmissionsAttachmentWithRecusiveStrategy() throws Exception {
        //ROOT Chil Child
        SchEvent schEventRootChildChild = factory.manufacturePojo(SchEvent.class);
        schEventRootChildChild.addEventEmissions(buildSchEventEmissionForSchEventWithAttachment(libMediaItem, corChannel, corNetwork));
        schEventRootChildChild.setId(null);
        schEventRootChildChild.setNetwork(corNetwork);
        schEventRootChildChild.setChannel(corChannel);

        SchEvent schEventRootChildChild1 = factory.manufacturePojo(SchEvent.class);

        schEventRootChildChild1.addEventEmissions(buildSchEventEmissionForSchEventWithAttachment(libMediaItem, corChannel, corNetwork));
        schEventRootChildChild1.setId(null);
        schEventRootChildChild1.setNetwork(corNetwork);
        schEventRootChildChild1.setChannel(corChannel);

        SchEvent schEventRootChildChild2 = factory.manufacturePojo(SchEvent.class);

        schEventRootChildChild2.addEventEmissions(buildSchEventEmissionForSchEventWithAttachment(libMediaItem, corChannel, corNetwork));
        schEventRootChildChild2.setId(null);
        schEventRootChildChild2.setNetwork(corNetwork);
        schEventRootChildChild2.setChannel(corChannel);

        //ROOTS Childs
        SchEvent schEventRootChild = factory.manufacturePojo(SchEvent.class);
        schEventRootChild.addEventEmissions(buildSchEventEmissionForSchEventWithAttachment(libMediaItem, corChannel, corNetwork));
        schEventRootChild.setId(null);
        schEventRootChild.setNetwork(corNetwork);
        schEventRootChild.setChannel(corChannel);
        schEventRootChild.addSchEvents(schEventRootChildChild);

        SchEvent schEventRootChild1 = factory.manufacturePojo(SchEvent.class);
        schEventRootChild1.addEventEmissions(buildSchEventEmissionForSchEventWithAttachment(libMediaItem, corChannel, corNetwork));
        schEventRootChild1.setId(null);
        schEventRootChild1.setNetwork(corNetwork);
        schEventRootChild1.setChannel(corChannel);
        schEventRootChild1.addSchEvents(schEventRootChildChild1);

        SchEvent schEventRootChild2 = factory.manufacturePojo(SchEvent.class);
        schEventRootChild2.addEventEmissions(buildSchEventEmissionForSchEventWithAttachment(libMediaItem, corChannel, corNetwork));
        schEventRootChild2.setId(null);
        schEventRootChild2.setNetwork(corNetwork);
        schEventRootChild2.setChannel(corChannel);
        schEventRootChild2.addSchEvents(schEventRootChildChild2);

        ///ROOTS
        SchEvent schEventRoot = factory.manufacturePojo(SchEvent.class);
        schEventRoot.addEventEmissions(buildSchEventEmissionForSchEventWithAttachment(libMediaItem, corChannel, corNetwork));
        schEventRoot.setId(null);
        schEventRoot.setNetwork(corNetwork);
        schEventRoot.setChannel(corChannel);
        schEventRoot.addSchEvents(schEventRootChild);

        SchEvent schEventRoot1 = factory.manufacturePojo(SchEvent.class);
        schEventRoot1.addEventEmissions(buildSchEventEmissionForSchEventWithAttachment(libMediaItem, corChannel, corNetwork));
        schEventRoot1.setId(null);
        schEventRoot1.setNetwork(corNetwork);
        schEventRoot1.setChannel(corChannel);
        schEventRoot1.addSchEvents(schEventRootChild1);

        SchEvent schEventRoot2 = factory.manufacturePojo(SchEvent.class);
        schEventRoot2.addEventEmissions(buildSchEventEmissionForSchEventWithAttachment(libMediaItem, corChannel, corNetwork));
        schEventRoot2.setId(null);
        schEventRoot2.setNetwork(corNetwork);
        schEventRoot2.setChannel(corChannel);
        schEventRoot2.addSchEvents(schEventRootChild2);


        Set<SchEvent> fetchedEntity = schEventService.saveEvent(Sets.newSet(schEventRoot, schEventRoot1, schEventRoot2));
        long dbSizeBlocks = schEventRepository.count();
        long emissionDbSize = schEventEmissionRepository.count();
        long attachmentDbSize = schEventEmissionAttachmentRepository.count();

        //then
        schEventService.deleteEvent(fetchedEntity);

        long dbSizeAfterDelete = schEventRepository.count();
        long emissionDbSizeAfterDelete = schEventEmissionRepository.count();
        long attachmentDbSizeAfterDelete = schEventEmissionAttachmentRepository.count();
        //assert
        assertEquals(dbSizeBlocks - 9, dbSizeAfterDelete);
        assertEquals(emissionDbSize - 9, dbSizeAfterDelete);
        assertEquals(attachmentDbSize - 9, dbSizeAfterDelete);

    }


}