package io.protone.application.service.scheduler.service;

import io.protone.application.ProtoneApp;
import io.protone.application.service.scheduler.base.SchedulerBaseTest;
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

import javax.transaction.Transactional;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 28/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchEventServiceTest extends SchedulerBaseTest {
    @Autowired
    private SchEventService schEventService;

    @Autowired
    private SchEventRepository schEventRepository;

    @Autowired
    private SchEventEmissionRepository schEventEmissionRepository;

    @Autowired
    private SchEventEmissionAttachmentRepository schEventEmissionAttachmentRepository;

    @Before
    public void setUp() throws Exception {
        super.setUp();

    }


    @Test
    public void shouldSaveSchEvent() throws Exception {
        //when
        SchEvent schEvent = factory.manufacturePojo(SchEvent.class);
        schEvent.setNetwork(corNetwork);
        schEvent.setChannel(corChannel);
        //then
        Set<SchEvent> fetchedEntity = schEventService.saveEvent(Sets.newSet(schEvent), null);

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
        schEventRootChild.addBlock(schEventRootChildChild);

        SchEvent schEventRootChild1 = factory.manufacturePojo(SchEvent.class);
        schEventRootChild1.setId(null);
        schEventRootChild1.setNetwork(corNetwork);
        schEventRootChild1.setChannel(corChannel);
        schEventRootChild1.addBlock(schEventRootChildChild1);

        SchEvent schEventRootChild2 = factory.manufacturePojo(SchEvent.class);
        schEventRootChild2.setId(null);
        schEventRootChild2.setNetwork(corNetwork);
        schEventRootChild2.setChannel(corChannel);
        schEventRootChild2.addBlock(schEventRootChildChild2);

        ///ROOTS
        SchEvent schEventRoot = factory.manufacturePojo(SchEvent.class);
        schEventRoot.setId(null);
        schEventRoot.setNetwork(corNetwork);
        schEventRoot.setChannel(corChannel);
        schEventRoot.addBlock(schEventRootChild);

        SchEvent schEventRoot1 = factory.manufacturePojo(SchEvent.class);
        schEventRoot1.setId(null);
        schEventRoot1.setNetwork(corNetwork);
        schEventRoot1.setChannel(corChannel);
        schEventRoot1.addBlock(schEventRootChild1);

        SchEvent schEventRoot2 = factory.manufacturePojo(SchEvent.class);
        schEventRoot2.setId(null);
        schEventRoot2.setNetwork(corNetwork);
        schEventRoot2.setChannel(corChannel);
        schEventRoot2.addBlock(schEventRootChild2);


        //then
        Set<SchEvent> fetchedEntity = schEventService.saveEvent(Sets.newSet(schEventRoot, schEventRoot1, schEventRoot2), null);

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(fetchedEntity.size(), 3);
        fetchedEntity.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getBlocks());
            assertEquals(entity.getBlocks().size(), 1);
            entity.getBlocks().stream().forEach(entityBlock -> {
                assertNotNull(entityBlock.getId());
                assertNotNull(entityBlock.getBlocks());
                assertEquals(entityBlock.getBlocks().size(), 1);
            });
        });

    }

    @Test
    public void shouldSaveSchEventContainigEmissionsWithRecusiveStrategy() throws Exception {
        //ROOT Chil Child
        SchEvent schEventRootChildChild = factory.manufacturePojo(SchEvent.class);
        schEventRootChildChild.addEmission(buildSchEventEmissionForSchEvent());
        schEventRootChildChild.setId(null);
        schEventRootChildChild.setNetwork(corNetwork);
        schEventRootChildChild.setChannel(corChannel);

        SchEvent schEventRootChildChild1 = factory.manufacturePojo(SchEvent.class);

        schEventRootChildChild1.addEmission(buildSchEventEmissionForSchEvent());
        schEventRootChildChild1.setId(null);
        schEventRootChildChild1.setNetwork(corNetwork);
        schEventRootChildChild1.setChannel(corChannel);

        SchEvent schEventRootChildChild2 = factory.manufacturePojo(SchEvent.class);

        schEventRootChildChild2.addEmission(buildSchEventEmissionForSchEvent());
        schEventRootChildChild2.setId(null);
        schEventRootChildChild2.setNetwork(corNetwork);
        schEventRootChildChild2.setChannel(corChannel);

        //ROOTS Childs
        SchEvent schEventRootChild = factory.manufacturePojo(SchEvent.class);
        schEventRootChild.addEmission(buildSchEventEmissionForSchEvent());
        schEventRootChild.setId(null);
        schEventRootChild.setNetwork(corNetwork);
        schEventRootChild.setChannel(corChannel);
        schEventRootChild.addBlock(schEventRootChildChild);

        SchEvent schEventRootChild1 = factory.manufacturePojo(SchEvent.class);
        schEventRootChild1.addEmission(buildSchEventEmissionForSchEvent());
        schEventRootChild1.setId(null);
        schEventRootChild1.setNetwork(corNetwork);
        schEventRootChild1.setChannel(corChannel);
        schEventRootChild1.addBlock(schEventRootChildChild1);

        SchEvent schEventRootChild2 = factory.manufacturePojo(SchEvent.class);
        schEventRootChild2.addEmission(buildSchEventEmissionForSchEvent());
        schEventRootChild2.setId(null);
        schEventRootChild2.setNetwork(corNetwork);
        schEventRootChild2.setChannel(corChannel);
        schEventRootChild2.addBlock(schEventRootChildChild2);

        ///ROOTS
        SchEvent schEventRoot = factory.manufacturePojo(SchEvent.class);
        schEventRoot.addEmission(buildSchEventEmissionForSchEvent());
        schEventRoot.setId(null);
        schEventRoot.setNetwork(corNetwork);
        schEventRoot.setChannel(corChannel);
        schEventRoot.addBlock(schEventRootChild);

        SchEvent schEventRoot1 = factory.manufacturePojo(SchEvent.class);
        schEventRoot1.addEmission(buildSchEventEmissionForSchEvent());
        schEventRoot1.setId(null);
        schEventRoot1.setNetwork(corNetwork);
        schEventRoot1.setChannel(corChannel);
        schEventRoot1.addBlock(schEventRootChild1);

        SchEvent schEventRoot2 = factory.manufacturePojo(SchEvent.class);
        schEventRoot2.addEmission(buildSchEventEmissionForSchEvent());
        schEventRoot2.setId(null);
        schEventRoot2.setNetwork(corNetwork);
        schEventRoot2.setChannel(corChannel);
        schEventRoot2.addBlock(schEventRootChild2);


        //then
        Set<SchEvent> fetchedEntity = schEventService.saveEvent(Sets.newSet(schEventRoot, schEventRoot1, schEventRoot2), null);

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(fetchedEntity.size(), 3);
        fetchedEntity.stream().forEach(entity -> {
            assertEquals(1, entity.getEmissions().size());
            assertNotNull(entity.getEmissions().stream().findFirst().get().getId());
            assertNotNull(entity.getId());
            assertNotNull(entity.getEmissions());
            assertNotNull(entity.getBlocks());
            assertEquals(entity.getBlocks().size(), 1);
            entity.getBlocks().stream().forEach(entityBlock -> {
                assertEquals(1, entityBlock.getEmissions().size());
                assertNotNull(entityBlock.getEmissions().stream().findFirst().get().getId());
                assertNotNull(entityBlock.getId());
                assertNotNull(entityBlock.getBlocks());
                assertEquals(entityBlock.getBlocks().size(), 1);
            });
        });
    }

    @Test
    public void shouldSaveSchEventContainigEmissionsAttachmentWithRecusiveStrategy() throws Exception {
        //ROOT Chil Child
        SchEvent schEventRootChildChild = factory.manufacturePojo(SchEvent.class);
        schEventRootChildChild.addEmission(buildSchEventEmissionForSchEventWithAttachment());
        schEventRootChildChild.setId(null);
        schEventRootChildChild.setNetwork(corNetwork);
        schEventRootChildChild.setChannel(corChannel);

        SchEvent schEventRootChildChild1 = factory.manufacturePojo(SchEvent.class);

        schEventRootChildChild1.addEmission(buildSchEventEmissionForSchEventWithAttachment());
        schEventRootChildChild1.setId(null);
        schEventRootChildChild1.setNetwork(corNetwork);
        schEventRootChildChild1.setChannel(corChannel);

        SchEvent schEventRootChildChild2 = factory.manufacturePojo(SchEvent.class);

        schEventRootChildChild2.addEmission(buildSchEventEmissionForSchEventWithAttachment());
        schEventRootChildChild2.setId(null);
        schEventRootChildChild2.setNetwork(corNetwork);
        schEventRootChildChild2.setChannel(corChannel);

        //ROOTS Childs
        SchEvent schEventRootChild = factory.manufacturePojo(SchEvent.class);
        schEventRootChild.addEmission(buildSchEventEmissionForSchEventWithAttachment());
        schEventRootChild.setId(null);
        schEventRootChild.setNetwork(corNetwork);
        schEventRootChild.setChannel(corChannel);
        schEventRootChild.addBlock(schEventRootChildChild);

        SchEvent schEventRootChild1 = factory.manufacturePojo(SchEvent.class);
        schEventRootChild1.addEmission(buildSchEventEmissionForSchEventWithAttachment());
        schEventRootChild1.setId(null);
        schEventRootChild1.setNetwork(corNetwork);
        schEventRootChild1.setChannel(corChannel);
        schEventRootChild1.addBlock(schEventRootChildChild1);

        SchEvent schEventRootChild2 = factory.manufacturePojo(SchEvent.class);
        schEventRootChild2.addEmission(buildSchEventEmissionForSchEventWithAttachment());
        schEventRootChild2.setId(null);
        schEventRootChild2.setNetwork(corNetwork);
        schEventRootChild2.setChannel(corChannel);
        schEventRootChild2.addBlock(schEventRootChildChild2);

        ///ROOTS
        SchEvent schEventRoot = factory.manufacturePojo(SchEvent.class);
        schEventRoot.addEmission(buildSchEventEmissionForSchEventWithAttachment());
        schEventRoot.setId(null);
        schEventRoot.setNetwork(corNetwork);
        schEventRoot.setChannel(corChannel);
        schEventRoot.addBlock(schEventRootChild);

        SchEvent schEventRoot1 = factory.manufacturePojo(SchEvent.class);
        schEventRoot1.addEmission(buildSchEventEmissionForSchEventWithAttachment());
        schEventRoot1.setId(null);
        schEventRoot1.setNetwork(corNetwork);
        schEventRoot1.setChannel(corChannel);
        schEventRoot1.addBlock(schEventRootChild1);

        SchEvent schEventRoot2 = factory.manufacturePojo(SchEvent.class);
        schEventRoot2.addEmission(buildSchEventEmissionForSchEventWithAttachment());
        schEventRoot2.setId(null);
        schEventRoot2.setNetwork(corNetwork);
        schEventRoot2.setChannel(corChannel);
        schEventRoot2.addBlock(schEventRootChild2);


        //then
        Set<SchEvent> fetchedEntity = schEventService.saveEvent(Sets.newSet(schEventRoot, schEventRoot1, schEventRoot2), null);

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
            assertNotNull(entity.getBlocks());
            assertEquals(entity.getBlocks().size(), 1);
            entity.getBlocks().stream().forEach(entityBlock -> {
                assertNotNull(entityBlock.getEmissions().stream().findFirst().get().getId());
                assertEquals(1, entityBlock.getEmissions().size());
                assertEquals(1, entityBlock.getEmissions().stream().findFirst().get().getAttachments().size());
                assertNotNull(entityBlock.getEmissions().stream().findFirst().get().getAttachments().stream().findFirst().get().getId());
                assertNotNull(entityBlock.getId());
                assertNotNull(entityBlock.getBlocks());
                assertEquals(entityBlock.getBlocks().size(), 1);
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
        schEventRootChild.addBlock(schEventRootChildChild);

        SchEvent schEventRootChild1 = factory.manufacturePojo(SchEvent.class);
        schEventRootChild1.setId(null);
        schEventRootChild1.setNetwork(corNetwork);
        schEventRootChild1.setChannel(corChannel);
        schEventRootChild1.addBlock(schEventRootChildChild1);

        SchEvent schEventRootChild2 = factory.manufacturePojo(SchEvent.class);
        schEventRootChild2.setId(null);
        schEventRootChild2.setNetwork(corNetwork);
        schEventRootChild2.setChannel(corChannel);
        schEventRootChild2.addBlock(schEventRootChildChild2);

        ///ROOTS
        SchEvent schEventRoot = factory.manufacturePojo(SchEvent.class);
        schEventRoot.setId(null);
        schEventRoot.setNetwork(corNetwork);
        schEventRoot.setChannel(corChannel);
        schEventRoot.addBlock(schEventRootChild);

        SchEvent schEventRoot1 = factory.manufacturePojo(SchEvent.class);
        schEventRoot1.setId(null);
        schEventRoot1.setNetwork(corNetwork);
        schEventRoot1.setChannel(corChannel);
        schEventRoot1.addBlock(schEventRootChild1);

        SchEvent schEventRoot2 = factory.manufacturePojo(SchEvent.class);
        schEventRoot2.setId(null);
        schEventRoot2.setNetwork(corNetwork);
        schEventRoot2.setChannel(corChannel);
        schEventRoot2.addBlock(schEventRootChild2);


        //then
        Set<SchEvent> fetchedEntity = schEventService.saveEvent(Sets.newSet(schEventRoot, schEventRoot1, schEventRoot2), null);

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
        schEventRootChildChild.addEmission(buildSchEventEmissionForSchEvent());
        schEventRootChildChild.setId(null);
        schEventRootChildChild.setNetwork(corNetwork);
        schEventRootChildChild.setChannel(corChannel);

        SchEvent schEventRootChildChild1 = factory.manufacturePojo(SchEvent.class);

        schEventRootChildChild1.addEmission(buildSchEventEmissionForSchEvent());
        schEventRootChildChild1.setId(null);
        schEventRootChildChild1.setNetwork(corNetwork);
        schEventRootChildChild1.setChannel(corChannel);

        SchEvent schEventRootChildChild2 = factory.manufacturePojo(SchEvent.class);

        schEventRootChildChild2.addEmission(buildSchEventEmissionForSchEvent());
        schEventRootChildChild2.setId(null);
        schEventRootChildChild2.setNetwork(corNetwork);
        schEventRootChildChild2.setChannel(corChannel);

        //ROOTS Childs
        SchEvent schEventRootChild = factory.manufacturePojo(SchEvent.class);
        schEventRootChild.addEmission(buildSchEventEmissionForSchEvent());
        schEventRootChild.setId(null);
        schEventRootChild.setNetwork(corNetwork);
        schEventRootChild.setChannel(corChannel);
        schEventRootChild.addBlock(schEventRootChildChild);

        SchEvent schEventRootChild1 = factory.manufacturePojo(SchEvent.class);
        schEventRootChild1.addEmission(buildSchEventEmissionForSchEvent());
        schEventRootChild1.setId(null);
        schEventRootChild1.setNetwork(corNetwork);
        schEventRootChild1.setChannel(corChannel);
        schEventRootChild1.addBlock(schEventRootChildChild1);

        SchEvent schEventRootChild2 = factory.manufacturePojo(SchEvent.class);
        schEventRootChild2.addEmission(buildSchEventEmissionForSchEvent());
        schEventRootChild2.setId(null);
        schEventRootChild2.setNetwork(corNetwork);
        schEventRootChild2.setChannel(corChannel);
        schEventRootChild2.addBlock(schEventRootChildChild2);

        ///ROOTS
        SchEvent schEventRoot = factory.manufacturePojo(SchEvent.class);
        schEventRoot.addEmission(buildSchEventEmissionForSchEvent());
        schEventRoot.setId(null);
        schEventRoot.setNetwork(corNetwork);
        schEventRoot.setChannel(corChannel);
        schEventRoot.addBlock(schEventRootChild);

        SchEvent schEventRoot1 = factory.manufacturePojo(SchEvent.class);
        schEventRoot1.addEmission(buildSchEventEmissionForSchEvent());
        schEventRoot1.setId(null);
        schEventRoot1.setNetwork(corNetwork);
        schEventRoot1.setChannel(corChannel);
        schEventRoot1.addBlock(schEventRootChild1);

        SchEvent schEventRoot2 = factory.manufacturePojo(SchEvent.class);
        schEventRoot2.addEmission(buildSchEventEmissionForSchEvent());
        schEventRoot2.setId(null);
        schEventRoot2.setNetwork(corNetwork);
        schEventRoot2.setChannel(corChannel);
        schEventRoot2.addBlock(schEventRootChild2);


        Set<SchEvent> fetchedEntity = schEventService.saveEvent(Sets.newSet(schEventRoot, schEventRoot1, schEventRoot2), null);
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
        schEventRootChildChild.addEmission(buildSchEventEmissionForSchEventWithAttachment());
        schEventRootChildChild.setId(null);
        schEventRootChildChild.setNetwork(corNetwork);
        schEventRootChildChild.setChannel(corChannel);

        SchEvent schEventRootChildChild1 = factory.manufacturePojo(SchEvent.class);

        schEventRootChildChild1.addEmission(buildSchEventEmissionForSchEventWithAttachment());
        schEventRootChildChild1.setId(null);
        schEventRootChildChild1.setNetwork(corNetwork);
        schEventRootChildChild1.setChannel(corChannel);

        SchEvent schEventRootChildChild2 = factory.manufacturePojo(SchEvent.class);

        schEventRootChildChild2.addEmission(buildSchEventEmissionForSchEventWithAttachment());
        schEventRootChildChild2.setId(null);
        schEventRootChildChild2.setNetwork(corNetwork);
        schEventRootChildChild2.setChannel(corChannel);

        //ROOTS Childs
        SchEvent schEventRootChild = factory.manufacturePojo(SchEvent.class);
        schEventRootChild.addEmission(buildSchEventEmissionForSchEventWithAttachment());
        schEventRootChild.setId(null);
        schEventRootChild.setNetwork(corNetwork);
        schEventRootChild.setChannel(corChannel);
        schEventRootChild.addBlock(schEventRootChildChild);

        SchEvent schEventRootChild1 = factory.manufacturePojo(SchEvent.class);
        schEventRootChild1.addEmission(buildSchEventEmissionForSchEventWithAttachment());
        schEventRootChild1.setId(null);
        schEventRootChild1.setNetwork(corNetwork);
        schEventRootChild1.setChannel(corChannel);
        schEventRootChild1.addBlock(schEventRootChildChild1);

        SchEvent schEventRootChild2 = factory.manufacturePojo(SchEvent.class);
        schEventRootChild2.addEmission(buildSchEventEmissionForSchEventWithAttachment());
        schEventRootChild2.setId(null);
        schEventRootChild2.setNetwork(corNetwork);
        schEventRootChild2.setChannel(corChannel);
        schEventRootChild2.addBlock(schEventRootChildChild2);

        ///ROOTS
        SchEvent schEventRoot = factory.manufacturePojo(SchEvent.class);
        schEventRoot.addEmission(buildSchEventEmissionForSchEventWithAttachment());
        schEventRoot.setId(null);
        schEventRoot.setNetwork(corNetwork);
        schEventRoot.setChannel(corChannel);
        schEventRoot.addBlock(schEventRootChild);

        SchEvent schEventRoot1 = factory.manufacturePojo(SchEvent.class);
        schEventRoot1.addEmission(buildSchEventEmissionForSchEventWithAttachment());
        schEventRoot1.setId(null);
        schEventRoot1.setNetwork(corNetwork);
        schEventRoot1.setChannel(corChannel);
        schEventRoot1.addBlock(schEventRootChild1);

        SchEvent schEventRoot2 = factory.manufacturePojo(SchEvent.class);
        schEventRoot2.addEmission(buildSchEventEmissionForSchEventWithAttachment());
        schEventRoot2.setId(null);
        schEventRoot2.setNetwork(corNetwork);
        schEventRoot2.setChannel(corChannel);
        schEventRoot2.addBlock(schEventRootChild2);


        Set<SchEvent> fetchedEntity = schEventService.saveEvent(Sets.newSet(schEventRoot, schEventRoot1, schEventRoot2), null);
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