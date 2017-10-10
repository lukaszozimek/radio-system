package io.protone.application.service.scheduler.service.time;

import io.protone.application.service.scheduler.base.SchedulerBuildSchedulerBaseTest;

/**
 * Created by lukaszozimek on 08/09/2017.
 */
////@RunWith(SpringRunner.class)
//@SpringBootTest(classes = ProtoneApp.class)
//TODO: Refactoring Test to fit new Transaction (Propagation.Required_NEW) strategies
public class SchPlaylistDTOTimeCalculatorServiceTest extends SchedulerBuildSchedulerBaseTest {
//
//    @Autowired
//    private SchPlaylistDTOTimeCalculatorService schPlaylistDTOTimeCalculatorService;
//
//    @Autowired
//    private SchParseLogService parseLogService;
//
//    @Mock
//    private LibFileItemService libFileItemService;
//
//    @Autowired
//    private LibFileItemRepository libFileItemRepository;
//
//    @Autowired
//    private SchLogRepository schLogRepository;
//
//    @Autowired
//    private SchGridRepository schGridRepository;
//
//    @Autowired
//    private SchPlaylistRepository schPlaylistRepository;
//
//    @Autowired
//    private SchScheduleServiceWrapper schScheduleService;
//    @PersistenceContext
//    private EntityManager entityManager;
//    @Before
//
//    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(this);
//        super.setUp();
//
//    }
//
//    @Test
//    public void shouldCalculateTimeForBlockNestedBlocks() throws IOException {
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
//        entityManager.flush();
//        //then
//        SchSchedule schSchedule = schScheduleService.buildDefaultSchedule(localDate, corNetwork.getShortcut(), corChannel.getShortcut());
//        SchPlaylist schPlaylist = schPlaylistRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndDate(corNetwork.getShortcut(), corChannel.getShortcut(), schSchedule.getDate());
//        SchPlaylistDTO resultDTO = schPlaylistDTOTimeCalculatorService.calculateTimeInSchPlaylistDTO(schPlaylist);
//        assertNotNull(resultDTO);
//        assertEquals(21, resultDTO.getEmissions().size());
//    }

}