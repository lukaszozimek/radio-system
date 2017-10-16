package io.protone.application.service.scheduler.service;

import io.protone.application.ProtoneApp;
import io.protone.application.web.api.cor.CorNetworkResourceIntTest;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.library.domain.LibMediaItem;
import io.protone.library.domain.LibMediaLibrary;
import io.protone.library.repository.LibMediaItemRepository;
import io.protone.scheduler.domain.SchBlock;
import io.protone.scheduler.repository.SchAttachmentRepository;
import io.protone.scheduler.repository.SchBlockRepository;
import io.protone.scheduler.repository.SchEmissionRepository;
import io.protone.scheduler.service.SchBlockService;
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
import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 28/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchBlockServiceTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Autowired
    private SchBlockService schBlockService;

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
        libMediaItem = new LibMediaItem().name("test").library(libMediaLibrary).idx("test").length(40.0).network(corNetwork);

        libMediaItem = libMediaItemRepository.saveAndFlush(libMediaItem);
    }


    @Test
    public void shouldSaveSchBlock() throws Exception {
        //when
        SchBlock schBlock = factory.manufacturePojo(SchBlock.class);
        schBlock.setNetwork(corNetwork);
        schBlock.setChannel(corChannel);
        //then
        Set<SchBlock> fetchedEntity = schBlockService.saveBlocks(Sets.newSet(schBlock));

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.stream().findFirst().get().getId());
        assertEquals(schBlock.getNetwork(), fetchedEntity.stream().findFirst().get().getNetwork());
    }

    @Test
    public void shouldSaveSchBlockWithRecusiveStrategy() throws Exception {
        //ROOT Chil Child
        SchBlock schBlockRootChildChild = factory.manufacturePojo(SchBlock.class);
        schBlockRootChildChild.setId(null);
        schBlockRootChildChild.setNetwork(corNetwork);
        schBlockRootChildChild.setChannel(corChannel);

        SchBlock schBlockRootChildChild1 = factory.manufacturePojo(SchBlock.class);
        schBlockRootChildChild1.setId(null);
        schBlockRootChildChild1.setNetwork(corNetwork);
        schBlockRootChildChild1.setChannel(corChannel);

        SchBlock schBlockRootChildChild2 = factory.manufacturePojo(SchBlock.class);
        schBlockRootChildChild2.setId(null);
        schBlockRootChildChild2.setNetwork(corNetwork);
        schBlockRootChildChild2.setChannel(corChannel);

        //ROOTS Childs
        SchBlock schBlockRootChild = factory.manufacturePojo(SchBlock.class);
        schBlockRootChild.setId(null);
        schBlockRootChild.setNetwork(corNetwork);
        schBlockRootChild.setChannel(corChannel);
        schBlockRootChild.addBlock(schBlockRootChildChild);

        SchBlock schBlockRootChild1 = factory.manufacturePojo(SchBlock.class);
        schBlockRootChild1.setId(null);
        schBlockRootChild1.setNetwork(corNetwork);
        schBlockRootChild1.setChannel(corChannel);
        schBlockRootChild1.addBlock(schBlockRootChildChild1);

        SchBlock schBlockRootChild2 = factory.manufacturePojo(SchBlock.class);
        schBlockRootChild2.setId(null);
        schBlockRootChild2.setNetwork(corNetwork);
        schBlockRootChild2.setChannel(corChannel);
        schBlockRootChild2.addBlock(schBlockRootChildChild2);

        ///ROOTS
        SchBlock schBlockRoot = factory.manufacturePojo(SchBlock.class);
        schBlockRoot.setId(null);
        schBlockRoot.setNetwork(corNetwork);
        schBlockRoot.setChannel(corChannel);
        schBlockRoot.addBlock(schBlockRootChild);

        SchBlock schBlockRoot1 = factory.manufacturePojo(SchBlock.class);
        schBlockRoot1.setId(null);
        schBlockRoot1.setNetwork(corNetwork);
        schBlockRoot1.setChannel(corChannel);
        schBlockRoot1.addBlock(schBlockRootChild1);

        SchBlock schBlockRoot2 = factory.manufacturePojo(SchBlock.class);
        schBlockRoot2.setId(null);
        schBlockRoot2.setNetwork(corNetwork);
        schBlockRoot2.setChannel(corChannel);
        schBlockRoot2.addBlock(schBlockRootChild2);


        //then
        Set<SchBlock> fetchedEntity = schBlockService.saveBlocks(Sets.newSet(schBlockRoot, schBlockRoot1, schBlockRoot2));

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
    public void shouldSaveSchBlockContainigEmissionsWithRecusiveStrategy() throws Exception {
        //ROOT Chil Child
        SchBlock schBlockRootChildChild = factory.manufacturePojo(SchBlock.class);
        schBlockRootChildChild.addEmission(buildEmissionForBlock(libMediaItem, corChannel, corNetwork));
        schBlockRootChildChild.setId(null);
        schBlockRootChildChild.setNetwork(corNetwork);
        schBlockRootChildChild.setChannel(corChannel);

        SchBlock schBlockRootChildChild1 = factory.manufacturePojo(SchBlock.class);

        schBlockRootChildChild1.addEmission(buildEmissionForBlock(libMediaItem, corChannel, corNetwork));
        schBlockRootChildChild1.setId(null);
        schBlockRootChildChild1.setNetwork(corNetwork);
        schBlockRootChildChild1.setChannel(corChannel);

        SchBlock schBlockRootChildChild2 = factory.manufacturePojo(SchBlock.class);

        schBlockRootChildChild2.addEmission(buildEmissionForBlock(libMediaItem, corChannel, corNetwork));
        schBlockRootChildChild2.setId(null);
        schBlockRootChildChild2.setNetwork(corNetwork);
        schBlockRootChildChild2.setChannel(corChannel);

        //ROOTS Childs
        SchBlock schBlockRootChild = factory.manufacturePojo(SchBlock.class);
        schBlockRootChild.addEmission(buildEmissionForBlock(libMediaItem, corChannel, corNetwork));
        schBlockRootChild.setId(null);
        schBlockRootChild.setNetwork(corNetwork);
        schBlockRootChild.setChannel(corChannel);
        schBlockRootChild.addBlock(schBlockRootChildChild);

        SchBlock schBlockRootChild1 = factory.manufacturePojo(SchBlock.class);
        schBlockRootChild1.addEmission(buildEmissionForBlock(libMediaItem, corChannel, corNetwork));
        schBlockRootChild1.setId(null);
        schBlockRootChild1.setNetwork(corNetwork);
        schBlockRootChild1.setChannel(corChannel);
        schBlockRootChild1.addBlock(schBlockRootChildChild1);

        SchBlock schBlockRootChild2 = factory.manufacturePojo(SchBlock.class);
        schBlockRootChild2.addEmission(buildEmissionForBlock(libMediaItem, corChannel, corNetwork));
        schBlockRootChild2.setId(null);
        schBlockRootChild2.setNetwork(corNetwork);
        schBlockRootChild2.setChannel(corChannel);
        schBlockRootChild2.addBlock(schBlockRootChildChild2);

        ///ROOTS
        SchBlock schBlockRoot = factory.manufacturePojo(SchBlock.class);
        schBlockRoot.addEmission(buildEmissionForBlock(libMediaItem, corChannel, corNetwork));
        schBlockRoot.setId(null);
        schBlockRoot.setNetwork(corNetwork);
        schBlockRoot.setChannel(corChannel);
        schBlockRoot.addBlock(schBlockRootChild);

        SchBlock schBlockRoot1 = factory.manufacturePojo(SchBlock.class);
        schBlockRoot1.addEmission(buildEmissionForBlock(libMediaItem, corChannel, corNetwork));
        schBlockRoot1.setId(null);
        schBlockRoot1.setNetwork(corNetwork);
        schBlockRoot1.setChannel(corChannel);
        schBlockRoot1.addBlock(schBlockRootChild1);

        SchBlock schBlockRoot2 = factory.manufacturePojo(SchBlock.class);
        schBlockRoot2.addEmission(buildEmissionForBlock(libMediaItem, corChannel, corNetwork));
        schBlockRoot2.setId(null);
        schBlockRoot2.setNetwork(corNetwork);
        schBlockRoot2.setChannel(corChannel);
        schBlockRoot2.addBlock(schBlockRootChild2);


        //then
        Set<SchBlock> fetchedEntity = schBlockService.saveBlocks(Sets.newSet(schBlockRoot, schBlockRoot1, schBlockRoot2));

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
    public void shouldSaveSchBlockContainigEmissionsAttachmentWithRecusiveStrategy() throws Exception {
        //ROOT Chil Child
        SchBlock schBlockRootChildChild = factory.manufacturePojo(SchBlock.class);
        schBlockRootChildChild.addEmission(buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork));
        schBlockRootChildChild.setId(null);
        schBlockRootChildChild.setNetwork(corNetwork);
        schBlockRootChildChild.setChannel(corChannel);

        SchBlock schBlockRootChildChild1 = factory.manufacturePojo(SchBlock.class);

        schBlockRootChildChild1.addEmission(buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork));
        schBlockRootChildChild1.setId(null);
        schBlockRootChildChild1.setNetwork(corNetwork);
        schBlockRootChildChild1.setChannel(corChannel);

        SchBlock schBlockRootChildChild2 = factory.manufacturePojo(SchBlock.class);

        schBlockRootChildChild2.addEmission(buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork));
        schBlockRootChildChild2.setId(null);
        schBlockRootChildChild2.setNetwork(corNetwork);
        schBlockRootChildChild2.setChannel(corChannel);

        //ROOTS Childs
        SchBlock schBlockRootChild = factory.manufacturePojo(SchBlock.class);
        schBlockRootChild.addEmission(buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork));
        schBlockRootChild.setId(null);
        schBlockRootChild.setNetwork(corNetwork);
        schBlockRootChild.setChannel(corChannel);
        schBlockRootChild.addBlock(schBlockRootChildChild);

        SchBlock schBlockRootChild1 = factory.manufacturePojo(SchBlock.class);
        schBlockRootChild1.addEmission(buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork));
        schBlockRootChild1.setId(null);
        schBlockRootChild1.setNetwork(corNetwork);
        schBlockRootChild1.setChannel(corChannel);
        schBlockRootChild1.addBlock(schBlockRootChildChild1);

        SchBlock schBlockRootChild2 = factory.manufacturePojo(SchBlock.class);
        schBlockRootChild2.addEmission(buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork));
        schBlockRootChild2.setId(null);
        schBlockRootChild2.setNetwork(corNetwork);
        schBlockRootChild2.setChannel(corChannel);
        schBlockRootChild2.addBlock(schBlockRootChildChild2);

        ///ROOTS
        SchBlock schBlockRoot = factory.manufacturePojo(SchBlock.class);
        schBlockRoot.addEmission(buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork));
        schBlockRoot.setId(null);
        schBlockRoot.setNetwork(corNetwork);
        schBlockRoot.setChannel(corChannel);
        schBlockRoot.addBlock(schBlockRootChild);

        SchBlock schBlockRoot1 = factory.manufacturePojo(SchBlock.class);
        schBlockRoot1.addEmission(buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork));
        schBlockRoot1.setId(null);
        schBlockRoot1.setNetwork(corNetwork);
        schBlockRoot1.setChannel(corChannel);
        schBlockRoot1.addBlock(schBlockRootChild1);

        SchBlock schBlockRoot2 = factory.manufacturePojo(SchBlock.class);
        schBlockRoot2.addEmission(buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork));
        schBlockRoot2.setId(null);
        schBlockRoot2.setNetwork(corNetwork);
        schBlockRoot2.setChannel(corChannel);
        schBlockRoot2.addBlock(schBlockRootChild2);


        //then
        Set<SchBlock> fetchedEntity = schBlockService.saveBlocks(Sets.newSet(schBlockRoot, schBlockRoot1, schBlockRoot2));

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
    public void shouldDeleteSchBlock() throws Exception {
        //when
        SchBlock schBlock = factory.manufacturePojo(SchBlock.class);
        schBlock.setNetwork(corNetwork);
        schBlock.setChannel(corChannel);
        schBlock = schBlockRepository.save(schBlock);
        //then
        schBlockService.deleteBlock(Sets.newSet(schBlock));
        SchBlock fetchedEntity = schBlockRepository.findOne(schBlock.getId());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void shouldDeleteSchBlockWithRecursiveStrategy() throws Exception {

        //ROOT Chil Child
        SchBlock schBlockRootChildChild = factory.manufacturePojo(SchBlock.class);
        schBlockRootChildChild.setId(null);
        schBlockRootChildChild.setNetwork(corNetwork);
        schBlockRootChildChild.setChannel(corChannel);

        SchBlock schBlockRootChildChild1 = factory.manufacturePojo(SchBlock.class);
        schBlockRootChildChild1.setId(null);
        schBlockRootChildChild1.setNetwork(corNetwork);
        schBlockRootChildChild1.setChannel(corChannel);

        SchBlock schBlockRootChildChild2 = factory.manufacturePojo(SchBlock.class);
        schBlockRootChildChild2.setId(null);
        schBlockRootChildChild2.setNetwork(corNetwork);
        schBlockRootChildChild2.setChannel(corChannel);

        //ROOTS Childs
        SchBlock schBlockRootChild = factory.manufacturePojo(SchBlock.class);
        schBlockRootChild.setId(null);
        schBlockRootChild.setNetwork(corNetwork);
        schBlockRootChild.setChannel(corChannel);
        schBlockRootChild.addBlock(schBlockRootChildChild);

        SchBlock schBlockRootChild1 = factory.manufacturePojo(SchBlock.class);
        schBlockRootChild1.setId(null);
        schBlockRootChild1.setNetwork(corNetwork);
        schBlockRootChild1.setChannel(corChannel);
        schBlockRootChild1.addBlock(schBlockRootChildChild1);

        SchBlock schBlockRootChild2 = factory.manufacturePojo(SchBlock.class);
        schBlockRootChild2.setId(null);
        schBlockRootChild2.setNetwork(corNetwork);
        schBlockRootChild2.setChannel(corChannel);
        schBlockRootChild2.addBlock(schBlockRootChildChild2);

        ///ROOTS
        SchBlock schBlockRoot = factory.manufacturePojo(SchBlock.class);
        schBlockRoot.setId(null);
        schBlockRoot.setNetwork(corNetwork);
        schBlockRoot.setChannel(corChannel);
        schBlockRoot.addBlock(schBlockRootChild);

        SchBlock schBlockRoot1 = factory.manufacturePojo(SchBlock.class);
        schBlockRoot1.setId(null);
        schBlockRoot1.setNetwork(corNetwork);
        schBlockRoot1.setChannel(corChannel);
        schBlockRoot1.addBlock(schBlockRootChild1);

        SchBlock schBlockRoot2 = factory.manufacturePojo(SchBlock.class);
        schBlockRoot2.setId(null);
        schBlockRoot2.setNetwork(corNetwork);
        schBlockRoot2.setChannel(corChannel);
        schBlockRoot2.addBlock(schBlockRootChild2);


        //then
        Set<SchBlock> fetchedEntity = schBlockService.saveBlocks(Sets.newSet(schBlockRoot, schBlockRoot1, schBlockRoot2));

        //assert
        long dbSize = schBlockRepository.count();
        schBlockService.deleteBlock(fetchedEntity);
        long dbSizeAfterDelete = schBlockRepository.count();
        assertEquals(dbSize - 9, dbSizeAfterDelete);
    }

    @Test
    public void shouldDeleteSchBlockContainigEmissionsWithRecusiveStrategy() throws Exception {
        //ROOT Chil Child
        SchBlock schBlockRootChildChild = factory.manufacturePojo(SchBlock.class);
        schBlockRootChildChild.addEmission(buildEmissionForBlock(libMediaItem, corChannel, corNetwork));
        schBlockRootChildChild.setId(null);
        schBlockRootChildChild.setNetwork(corNetwork);
        schBlockRootChildChild.setChannel(corChannel);

        SchBlock schBlockRootChildChild1 = factory.manufacturePojo(SchBlock.class);

        schBlockRootChildChild1.addEmission(buildEmissionForBlock(libMediaItem, corChannel, corNetwork));
        schBlockRootChildChild1.setId(null);
        schBlockRootChildChild1.setNetwork(corNetwork);
        schBlockRootChildChild1.setChannel(corChannel);

        SchBlock schBlockRootChildChild2 = factory.manufacturePojo(SchBlock.class);

        schBlockRootChildChild2.addEmission(buildEmissionForBlock(libMediaItem, corChannel, corNetwork));
        schBlockRootChildChild2.setId(null);
        schBlockRootChildChild2.setNetwork(corNetwork);
        schBlockRootChildChild2.setChannel(corChannel);

        //ROOTS Childs
        SchBlock schBlockRootChild = factory.manufacturePojo(SchBlock.class);
        schBlockRootChild.addEmission(buildEmissionForBlock(libMediaItem, corChannel, corNetwork));
        schBlockRootChild.setId(null);
        schBlockRootChild.setNetwork(corNetwork);
        schBlockRootChild.setChannel(corChannel);
        schBlockRootChild.addBlock(schBlockRootChildChild);

        SchBlock schBlockRootChild1 = factory.manufacturePojo(SchBlock.class);
        schBlockRootChild1.addEmission(buildEmissionForBlock(libMediaItem, corChannel, corNetwork));
        schBlockRootChild1.setId(null);
        schBlockRootChild1.setNetwork(corNetwork);
        schBlockRootChild1.setChannel(corChannel);
        schBlockRootChild1.addBlock(schBlockRootChildChild1);

        SchBlock schBlockRootChild2 = factory.manufacturePojo(SchBlock.class);
        schBlockRootChild2.addEmission(buildEmissionForBlock(libMediaItem, corChannel, corNetwork));
        schBlockRootChild2.setId(null);
        schBlockRootChild2.setNetwork(corNetwork);
        schBlockRootChild2.setChannel(corChannel);
        schBlockRootChild2.addBlock(schBlockRootChildChild2);

        ///ROOTS
        SchBlock schBlockRoot = factory.manufacturePojo(SchBlock.class);
        schBlockRoot.addEmission(buildEmissionForBlock(libMediaItem, corChannel, corNetwork));
        schBlockRoot.setId(null);
        schBlockRoot.setNetwork(corNetwork);
        schBlockRoot.setChannel(corChannel);
        schBlockRoot.addBlock(schBlockRootChild);

        SchBlock schBlockRoot1 = factory.manufacturePojo(SchBlock.class);
        schBlockRoot1.addEmission(buildEmissionForBlock(libMediaItem, corChannel, corNetwork));
        schBlockRoot1.setId(null);
        schBlockRoot1.setNetwork(corNetwork);
        schBlockRoot1.setChannel(corChannel);
        schBlockRoot1.addBlock(schBlockRootChild1);

        SchBlock schBlockRoot2 = factory.manufacturePojo(SchBlock.class);
        schBlockRoot2.addEmission(buildEmissionForBlock(libMediaItem, corChannel, corNetwork));
        schBlockRoot2.setId(null);
        schBlockRoot2.setNetwork(corNetwork);
        schBlockRoot2.setChannel(corChannel);
        schBlockRoot2.addBlock(schBlockRootChild2);


        Set<SchBlock> fetchedEntity = schBlockService.saveBlocks(Sets.newSet(schBlockRoot, schBlockRoot1, schBlockRoot2));
        long dbSizeBlocks = schBlockRepository.count();
        long emissionDbSize = schEmissionRepository.count();
        //then
        schBlockService.deleteBlock(fetchedEntity);
        long dbSizeAfterDelete = schBlockRepository.count();
        long emissionDbSizeAfterDelete = schEmissionRepository.count();

        //assert
        assertEquals(dbSizeBlocks - 9, dbSizeAfterDelete);
        assertEquals(emissionDbSize - 9, dbSizeAfterDelete);
    }

    @Test
    public void shouldDeleteSchBlockContainigEmissionsAttachmentWithRecusiveStrategy() throws Exception {
        //ROOT Chil Child
        SchBlock schBlockRootChildChild = factory.manufacturePojo(SchBlock.class);
        schBlockRootChildChild.addEmission(buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork));
        schBlockRootChildChild.setId(null);
        schBlockRootChildChild.setNetwork(corNetwork);
        schBlockRootChildChild.setChannel(corChannel);

        SchBlock schBlockRootChildChild1 = factory.manufacturePojo(SchBlock.class);

        schBlockRootChildChild1.addEmission(buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork));
        schBlockRootChildChild1.setId(null);
        schBlockRootChildChild1.setNetwork(corNetwork);
        schBlockRootChildChild1.setChannel(corChannel);

        SchBlock schBlockRootChildChild2 = factory.manufacturePojo(SchBlock.class);

        schBlockRootChildChild2.addEmission(buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork));
        schBlockRootChildChild2.setId(null);
        schBlockRootChildChild2.setNetwork(corNetwork);
        schBlockRootChildChild2.setChannel(corChannel);

        //ROOTS Childs
        SchBlock schBlockRootChild = factory.manufacturePojo(SchBlock.class);
        schBlockRootChild.addEmission(buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork));
        schBlockRootChild.setId(null);
        schBlockRootChild.setNetwork(corNetwork);
        schBlockRootChild.setChannel(corChannel);
        schBlockRootChild.addBlock(schBlockRootChildChild);

        SchBlock schBlockRootChild1 = factory.manufacturePojo(SchBlock.class);
        schBlockRootChild1.addEmission(buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork));
        schBlockRootChild1.setId(null);
        schBlockRootChild1.setNetwork(corNetwork);
        schBlockRootChild1.setChannel(corChannel);
        schBlockRootChild1.addBlock(schBlockRootChildChild1);

        SchBlock schBlockRootChild2 = factory.manufacturePojo(SchBlock.class);
        schBlockRootChild2.addEmission(buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork));
        schBlockRootChild2.setId(null);
        schBlockRootChild2.setNetwork(corNetwork);
        schBlockRootChild2.setChannel(corChannel);
        schBlockRootChild2.addBlock(schBlockRootChildChild2);

        ///ROOTS
        SchBlock schBlockRoot = factory.manufacturePojo(SchBlock.class);
        schBlockRoot.addEmission(buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork));
        schBlockRoot.setId(null);
        schBlockRoot.setNetwork(corNetwork);
        schBlockRoot.setChannel(corChannel);
        schBlockRoot.addBlock(schBlockRootChild);

        SchBlock schBlockRoot1 = factory.manufacturePojo(SchBlock.class);
        schBlockRoot1.addEmission(buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork));
        schBlockRoot1.setId(null);
        schBlockRoot1.setNetwork(corNetwork);
        schBlockRoot1.setChannel(corChannel);
        schBlockRoot1.addBlock(schBlockRootChild1);

        SchBlock schBlockRoot2 = factory.manufacturePojo(SchBlock.class);
        schBlockRoot2.addEmission(buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork));
        schBlockRoot2.setId(null);
        schBlockRoot2.setNetwork(corNetwork);
        schBlockRoot2.setChannel(corChannel);
        schBlockRoot2.addBlock(schBlockRootChild2);


        Set<SchBlock> fetchedEntity = schBlockService.saveBlocks(Sets.newSet(schBlockRoot, schBlockRoot1, schBlockRoot2));
        long dbSizeBlocks = schBlockRepository.count();
        long emissionDbSize = schEmissionRepository.count();
        long attachmentDbSize = schAttachmentRepository.count();

        //then
        schBlockService.deleteBlock(fetchedEntity);

        long dbSizeAfterDelete = schBlockRepository.count();
        long emissionDbSizeAfterDelete = schEmissionRepository.count();
        long attachmentDbSizeAfterDelete = schAttachmentRepository.count();
        //assert
        assertEquals(dbSizeBlocks - 9, dbSizeAfterDelete);
        assertEquals(emissionDbSize - 9, dbSizeAfterDelete);
        assertEquals(attachmentDbSize - 9, dbSizeAfterDelete);

    }


}
