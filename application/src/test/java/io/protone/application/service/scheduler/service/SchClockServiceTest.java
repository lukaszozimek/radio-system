package io.protone.application.service.scheduler.service;

import io.protone.application.service.scheduler.base.SchedulerBaseTest;

/**
 * Created by lukaszozimek on 28/08/2017.
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = ProtoneApp.class)
//@Transactional
//TODO: Refactoring Test to fit new Transaction (Propagation.Required_NEW) strategies
public class SchClockServiceTest extends SchedulerBaseTest {
//    @Autowired
//    private SchClockService schClockService;
//
//    @Autowired
//    private SchClockRepository schClockRepository;
//
//    @Autowired
//    private SchBlockRepository schBlockRepository;
//
//    @Autowired
//    private SchEmissionRepository schEmissionRepository;
//
//    @Autowired
//    private SchAttachmentRepository schAttachmentRepository;
//
//    @Before
//    public void setUp() throws Exception {
//        super.setUp();
//
//    }
//
//
//    @Test
//    public void shouldGetClocks() throws Exception {
//        //when
//        SchClock schClock = factory.manufacturePojo(SchClock.class);
//        schClock.setNetwork(corNetwork);
//        schClock.setChannel(corChannel);
//        schClock = schClockRepository.save(schClock);
//
//        //then
//        Slice<SchClock> fetchedEntity = schClockService.findSchClocksForNetworkAndChannel(corNetwork.getShortcut(), corChannel.getShortcut(), new PageRequest(0, 10));
//
//        //assert
//        assertNotNull(fetchedEntity.getContent());
//        assertEquals(1, fetchedEntity.getContent().size());
//        assertEquals(schClock.getId(), fetchedEntity.getContent().get(0).getId());
//        assertEquals(schClock.getNetwork(), fetchedEntity.getContent().get(0).getNetwork());
//
//    }
//
//    @Test
//    public void shouldSaveClock() throws Exception {
//        //when
//        SchClock schClock = factory.manufacturePojo(SchClock.class);
//        schClock.setNetwork(corNetwork);
//        schClock.setChannel(corChannel);
//        //then
//        SchClock fetchedEntity = schClockService.saveClock(schClock, null);
//
//        //assert
//        assertNotNull(fetchedEntity);
//        assertNotNull(fetchedEntity.getId());
//        assertEquals(schClock.getNetwork(), fetchedEntity.getNetwork());
//    }
//
//    @Test
//    public void shouldSaveClockWithRecursiveStrategy() throws Exception {
//        //when
//        SchClock schClock = factory.manufacturePojo(SchClock.class);
//        schClock.setBlocks(buildNestedSetBlocks());
//        schClock.setEmissions(Sets.newHashSet(buildEmissionForWithAttachment(), buildEmissionForWithAttachment(), buildEmissionForWithAttachment()));
//        schClock.setNetwork(corNetwork);
//        schClock.setChannel(corChannel);
//        //then
//        SchClock fetchedEntity = schClockService.saveClock(schClock, null);
//
//        //assert
//        assertNotNull(fetchedEntity);
//        assertNotNull(fetchedEntity.getId());
//        assertEquals(schClock.getNetwork(), fetchedEntity.getNetwork());
//    }
//
//    @Test
//    public void shouldDeleteClock() throws Exception {
//        //when
//        SchClock schClock = factory.manufacturePojo(SchClock.class);
//        schClock.setNetwork(corNetwork);
//        schClock.setChannel(corChannel);
//        schClock = schClockRepository.saveAndFlush(schClock);
//        //then
//        schClockService.deleteSchClockByNetworkAndChannelAndShortName(corNetwork.getShortcut(), corChannel.getShortcut(), schClock.getShortName());
//        SchClock fetchedEntity = schClockRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndShortName(corNetwork.getShortcut(), corChannel.getShortcut(), schClock.getShortName());
//
//        //assert
//        assertNull(fetchedEntity);
//    }
//
//    @Test
//    public void shouldDeleteClockWithBlock() throws Exception {
//        SchClock schClock = factory.manufacturePojo(SchClock.class);
//        schClock.setBlocks(buildNestedSetBlocks());
//        schClock.shortName("tesSSSSSSSSSCCCCXXX");
//        schClock.setEmissions(Sets.newHashSet(buildEmissionForWithAttachment(), buildEmissionForWithAttachment(), buildEmissionForWithAttachment()));
//        schClock.setNetwork(corNetwork);
//        schClock.setChannel(corChannel);
//        schClock = schClockService.saveClock(schClock, null);
//        long clockNumberAfterSave = schClockRepository.count();
//        long blockNumberAfterSave = schBlockRepository.count();
//        long emissionNumberAfterSave = schEmissionRepository.count();
//        long attachmentNumberAfterSave = schAttachmentRepository.count();
//        schClockRepository.flush();
//
//        //then
//        schClockService.deleteSchClockByNetworkAndChannelAndShortName(schClock.getNetwork().getShortcut(), schClock.getChannel().getShortcut(), schClock.getShortName());
//
//
//        assertEquals(clockNumberAfterSave - 1, schClockRepository.count());
//        assertEquals(blockNumberAfterSave - 9, schBlockRepository.count());
//        assertEquals(emissionNumberAfterSave - 12, schEmissionRepository.count());
//        assertEquals(attachmentNumberAfterSave - 12, schAttachmentRepository.count());
//
//    }
//
//    @Test
//    public void shouldGetClock() throws Exception {
//        SchClock schClock = factory.manufacturePojo(SchClock.class);
//        schClock.setBlocks(buildNestedSetBlocks());
//        schClock.setEmissions(Sets.newHashSet(buildEmissionForWithAttachment(), buildEmissionForWithAttachment(), buildEmissionForWithAttachment()));
//        schClock.setId(null);
//        schClock.setNetwork(corNetwork);
//        schClock.setChannel(corChannel);
//        SchClock saveClock = schClockService.saveClock(schClock, null);
//
//        //then
//        SchClock fetchedEntity = schClockService.findSchClockForNetworkAndChannelAndShortName(corNetwork.getShortcut(), corChannel.getShortcut(), schClock.getShortName());
//
//        //assert
//        assertNotNull(fetchedEntity);
//        assertEquals(saveClock.getId(), fetchedEntity.getId());
//        assertEquals(schClock.getNetwork(), fetchedEntity.getNetwork());
//
//    }


}