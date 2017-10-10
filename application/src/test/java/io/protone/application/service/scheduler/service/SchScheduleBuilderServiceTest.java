package io.protone.application.service.scheduler.service;

import io.protone.application.service.scheduler.base.SchedulerBuildSchedulerBaseTest;

/**
 * Created by lukaszozimek on 30/08/2017.
 */
////@RunWith(SpringRunner.class)
////@SpringBootTest(classes = ProtoneApp.class)
//@Transactional

//TODO: Refactoring Test to fit new Transaction (Propagation.Required_NEW) strategies
public class SchScheduleBuilderServiceTest extends SchedulerBuildSchedulerBaseTest {
//
//    @Autowired
//    private SchGridRepository schGridRepository;
//
//    @Autowired
//    private SchScheduleBuilderService schScheduleBuilderService;
//
//    @Mock
//    private LibFileItemService libFileItemService;
//
//    @Autowired
//    private SchParseLogService parseLogService;
//
//    @Autowired
//    private SchLogRepository schLogRepository;
//    @Autowired
//    private LibFileItemRepository libFileItemRepository;
//
//    @Before
//    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(this);
//        super.setUp();
//    }
//
//    @Test
//    public void shouldBuildScheduleWithEmptyDefaultGrid() {
//        //when
//        LocalDate localDate = LocalDate.now();
//        SchGrid schGrid = schGridRepository.saveAndFlush(buildGridForDay(corDayOfWeekEnumMap.get(localDate.getDayOfWeek()), true));
//        //then
//        SchSchedule schSchedule = schScheduleBuilderService.buildDefaultSchedule(localDate, corNetwork.getShortcut(), corChannel.getShortcut());
//        //assert
//        assertNotNull(schSchedule);
//        assertEquals(schSchedule.getNetwork(), schGrid.getNetwork());
//        assertEquals(schSchedule.getChannel(), schGrid.getChannel());
//        assertEquals(schSchedule.getDate(), localDate);
//        assertTrue(schSchedule.getClocks().isEmpty());
//    }
//
//
//    @Test(expected = BadRequestException.class)
//    public void shouldNotBuildScheduleWithEmptyGrid() {
//        //when
//        LocalDate localDate = LocalDate.now();
//        schGridRepository.saveAndFlush(buildGridForDay(corDayOfWeekEnumMap.get(localDate.getDayOfWeek()), false));
//
//        //then
//        schScheduleBuilderService.buildDefaultSchedule(localDate, corNetwork.getShortcut(), corChannel.getShortcut());
//    }
//
//    @Test
//    public void shouldBuildScheduleWithOneHourGrid() {
//        //when
//        LocalDate localDate = LocalDate.now();
//        SchGrid schGrid = schGridRepository.saveAndFlush(buildGridForDayWitClock(corDayOfWeekEnumMap.get(localDate.getDayOfWeek()), true));
//
//        //then
//        SchSchedule schSchedule = schScheduleBuilderService.buildDefaultSchedule(localDate, corNetwork.getShortcut(), corChannel.getShortcut());
//
//        //assert
//        assertNotNull(schSchedule);
//        assertEquals(schSchedule.getNetwork(), schGrid.getNetwork());
//        assertEquals(schSchedule.getChannel(), schGrid.getChannel());
//        assertEquals(schSchedule.getDate(), localDate);
//        assertTrue(!schSchedule.getClocks().isEmpty());
//        assertEquals(1, schSchedule.getClocks().size());
//        assertNotNull(schSchedule.getClocks().stream().findFirst().get());
//        assertNotNull(schSchedule.getClocks().stream().findFirst().get().getSequence());
//        assertNotNull(schSchedule.getClocks().stream().findFirst().get().getLength());
//        assertEquals(1L, schSchedule.getClocks().stream().findFirst().get().getSequence().longValue());
//        assertNotNull(schSchedule.getClocks().stream().findFirst().get().getEmissions());
//        assertEquals(4, schSchedule.getClocks().stream().findFirst().get().getEmissions().size());
//
//    }
//
//
//    @Test
//    public void shouldBuildScheduleWithOneHourContainingEventsGrid() {
//        //when
//        LocalDate localDate = LocalDate.now();
//        SchGrid schGrid = schGridRepository.saveAndFlush(buildGridForDayWitClockWithNestedEvents(corDayOfWeekEnumMap.get(localDate.getDayOfWeek()), true));
//
//        //then
//        SchSchedule schSchedule = schScheduleBuilderService.buildDefaultSchedule(localDate, corNetwork.getShortcut(), corChannel.getShortcut());
//
//        //assert
//        assertNotNull(schSchedule);
//        assertEquals(schSchedule.getNetwork(), schGrid.getNetwork());
//        assertEquals(schSchedule.getChannel(), schGrid.getChannel());
//        assertEquals(schSchedule.getDate(), localDate);
//        assertTrue(!schSchedule.getClocks().isEmpty());
//        assertEquals(1, schSchedule.getClocks().size());
//        assertNotNull(schSchedule.getClocks().stream().findFirst().get());
//        assertNotNull(schSchedule.getClocks().stream().findFirst().get().getSequence());
//        assertNotNull(schSchedule.getClocks().stream().findFirst().get().getLength());
//        assertEquals(1L, schSchedule.getClocks().stream().findFirst().get().getSequence().longValue());
//        assertNotNull(schSchedule.getClocks().stream().findFirst().get().getEmissions());
//        assertEquals(2, schSchedule.getClocks().stream().findFirst().get().getEmissions().size());
//        assertEquals(3, schSchedule.getClocks().stream().findFirst().get().getBlocks().size());
//        schSchedule.getClocks().stream().findFirst().get().getBlocks().stream().forEach(block -> {
//            assertEquals(1, block.getBlocks().size());
//            assertNotNull(block.getChannel());
//            assertNotNull(block.getNetwork());
//            block.getBlocks().stream().forEach(nestedBlock -> {
//                assertTrue(nestedBlock.getBlocks().isEmpty());
//                assertEquals(3, nestedBlock.getEmissions().size());
//                nestedBlock.getEmissions().stream().forEach(emissionsInNestedBlock -> {
//                    assertNotNull(emissionsInNestedBlock.getSequence());
//                    assertNotNull(emissionsInNestedBlock.getMediaItem());
//                    assertEquals(3, emissionsInNestedBlock.getAttachments().size());
//                    assertNotNull(emissionsInNestedBlock.getChannel());
//                    assertNotNull(emissionsInNestedBlock.getNetwork());
//                    emissionsInNestedBlock.getAttachments().stream().forEach(nestedAttachments -> {
//                        assertNotNull(nestedAttachments);
//                        assertNotNull(nestedAttachments.getSequence());
//                        assertNotNull(nestedAttachments.getNetwork());
//                        assertNotNull(nestedAttachments.getChannel());
//
//                    });
//                });
//            });
//            assertEquals(3, block.getEmissions().size());
//            block.getEmissions().stream().forEach(blockEmission -> {
//                assertNotNull(blockEmission.getSequence());
//                assertNotNull(blockEmission.getMediaItem());
//                assertNotNull(blockEmission.getNetwork());
//                assertNotNull(blockEmission.getChannel());
//                assertEquals(3, blockEmission.getAttachments().size());
//                blockEmission.getAttachments().stream().forEach(attachment -> {
//                    assertNotNull(attachment);
//                    assertNotNull(attachment.getSequence());
//                    assertNotNull(attachment.getNetwork());
//                    assertNotNull(attachment.getChannel());
//                });
//
//            });
//
//        });
//
//    }
//
//    @Test
//    public void shouldBuildScheduleWithOneHourContainingEventsImportAndEmissionsGrid() throws IOException {
//        //when
//        ReflectionTestUtils.setField(parseLogService, "libFileItemService", libFileItemService);
//        LibFileItem schLogMusFile = libFileItemRepository.saveAndFlush(new LibFileItem().idx("log").name("MUSLOG").network(corNetwork).channel(corChannel));
//        LibFileItem schLogOprFile = libFileItemRepository.saveAndFlush(new LibFileItem().idx("log1").name("OPRLOG").network(corNetwork).channel(corChannel));
//        SchLog schLogMusic = schLogRepository.saveAndFlush(new SchLog().fileItem(schLogMusFile).schLogConfiguration(buildMusLogConfigurationWithSeparator()).network(corNetwork).channel(corChannel).date(LocalDate.parse("20170826", DateTimeFormatter.ofPattern("yyyyMMdd"))));
//        SchLog schLogOpr = schLogRepository.saveAndFlush(new SchLog().fileItem(schLogOprFile).schLogConfiguration(buildOPRLogConfigurationWithSeparator()).network(corNetwork).channel(corChannel).date(LocalDate.parse("20170826", DateTimeFormatter.ofPattern("yyyyMMdd"))));
//        InputStream musLogStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("scheduler/mapping/musicLog/20170826.MUS");
//        InputStream oprLogStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("scheduler/mapping/newsCollect/20170826.OPR");
//        when(libFileItemService.download(schLogMusFile)).thenReturn(IOUtils.toByteArray(musLogStream));
//        when(libFileItemService.download(schLogOprFile)).thenReturn(IOUtils.toByteArray(oprLogStream));
//        LocalDate localDate = LocalDate.of(2017, 8, 26);
//        SchGrid schGrid = schGridRepository.saveAndFlush(buildGridForDayWitClockMusicAndImportEventsAndEmissionsConfiguration(corDayOfWeekEnumMap.get(localDate.getDayOfWeek()), true));
//
//        //then
//        SchSchedule schSchedule = schScheduleBuilderService.buildDefaultSchedule(localDate, corNetwork.getShortcut(), corChannel.getShortcut());
//        assertNotNull(schSchedule);
//        assertEquals(schSchedule.getNetwork(), schGrid.getNetwork());
//        assertEquals(schSchedule.getChannel(), schGrid.getChannel());
//        assertEquals(schSchedule.getDate(), localDate);
//        assertTrue(!schSchedule.getClocks().isEmpty());
//        assertEquals(1, schSchedule.getClocks().size());
//        assertNotNull(schSchedule.getClocks().stream().findFirst().get());
//        assertNotNull(schSchedule.getClocks().stream().findFirst().get().getSequence());
//        assertNotNull(schSchedule.getClocks().stream().findFirst().get().getLength());
//        assertNotNull(schSchedule.getClocks().stream().findFirst().get().getEmissions());
//        assertEquals(2, schSchedule.getClocks().stream().findFirst().get().getEmissions().size());
//        assertEquals(3, schSchedule.getClocks().stream().findFirst().get().getBlocks().size());
//        schSchedule.getClocks().stream().findFirst().get().getBlocks().stream().forEach(block -> {
//            if (block.getEventType() != null && block.getEventType().equals(EventTypeEnum.ET_IMPORT_LOG)) {
//                assertEquals(7, block.getEmissions().size());
//                assertEquals(2, block.getBlocks().size());
//
//                assertNotNull(block.getChannel());
//                assertNotNull(block.getNetwork());
//            }
//            if (block.getEventType() != null && block.getEventType().equals(EventTypeEnum.ET_MUSIC)) {
//                block.getBlocks().stream().forEach(nestedBlock -> {
//                    if (nestedBlock.getEventType() != null && nestedBlock.getEventType().equals(EventTypeEnum.ET_IMPORT_LOG)) {
//                        assertEquals(4, nestedBlock.getEmissions().size());
//                        assertEquals(0, nestedBlock.getBlocks().size());
//
//                        assertNotNull(nestedBlock.getChannel());
//                        assertNotNull(nestedBlock.getNetwork());
//                    } else {
//                        assertTrue(nestedBlock.getBlocks().isEmpty());
//                        assertEquals(3, nestedBlock.getEmissions().size());
//                        nestedBlock.getEmissions().stream().forEach(emissionsInNestedBlock -> {
//                            assertNotNull(emissionsInNestedBlock.getSequence());
//                            assertNotNull(emissionsInNestedBlock.getMediaItem());
//                            assertEquals(3, emissionsInNestedBlock.getAttachments().size());
//                            assertNotNull(emissionsInNestedBlock.getChannel());
//                            assertNotNull(emissionsInNestedBlock.getNetwork());
//                            emissionsInNestedBlock.getAttachments().stream().forEach(nestedAttachments -> {
//                                assertNotNull(nestedAttachments);
//                                assertNotNull(nestedAttachments.getSequence());
//                                assertNotNull(nestedAttachments.getNetwork());
//                                assertNotNull(nestedAttachments.getChannel());
//
//                            });
//                        });
//                    }
//
//                });
//            }
//        });
//
//    }

}