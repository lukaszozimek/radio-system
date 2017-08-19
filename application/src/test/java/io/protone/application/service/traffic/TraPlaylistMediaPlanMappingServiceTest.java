package io.protone.application.service.traffic;


import io.protone.application.ProtoneApp;
import io.protone.application.service.traffic.base.TraPlaylistBasedTest;
import io.protone.application.util.TestUtil;
import io.protone.library.service.LibItemService;
import io.protone.traffic.domain.*;
import io.protone.traffic.mapper.TraMediaPlanBlockMapper;
import io.protone.traffic.repository.TraMediaPlanBlockRepository;
import io.protone.traffic.repository.TraMediaPlanEmissionRepository;
import io.protone.traffic.repository.TraMediaPlanPlaylistDateRepository;
import io.protone.traffic.service.TraMediaPlanService;
import io.protone.traffic.service.TraPlaylistService;
import io.protone.traffic.service.mediaplan.TraPlaylistMediaPlanMappingService;
import io.protone.traffic.service.mediaplan.descriptor.TraMediaPlanDescriptor;
import io.protone.traffic.service.mediaplan.diff.TraPlaylistDiff;
import io.protone.traffic.service.mediaplan.mapping.TraMediaPlanMapping;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.tika.exception.TikaException;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by lukaszozimek on 12/06/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@ActiveProfiles("dev")
public class TraPlaylistMediaPlanMappingServiceTest extends TraPlaylistBasedTest {

    @Autowired
    private TraMediaPlanService traMediaPlanService;

    @Autowired
    private TraPlaylistService traPlaylistService;

    @Autowired
    private TraMediaPlanBlockMapper traMediaPlanMapperPlaylistMapper;
    @Autowired
    private TraPlaylistMediaPlanMappingService traPlaylistMediaPlanMappingService;

    @Autowired
    @Qualifier("traDefaultMediaPlanMapping")
    private TraMediaPlanMapping traDefaultMediaPlanMapping;
    @Mock
    private LibItemService libItemService;
    @Autowired
    private TraMediaPlanEmissionRepository traMediaPlanEmissionRepository;
    @Autowired
    private TraMediaPlanPlaylistDateRepository traMediaPlanPlaylistDateRepository;
    @Autowired
    private TraMediaPlanBlockRepository traMediaPlanBlockRepository;

    @Before
    public void setup() throws InterruptedException {

        MockitoAnnotations.initMocks(this);
        buildMustHavePojos();
        traPlaylists = new ArrayList<>();
        mediaItemList = buildMediaItems();
        advertisements = buildAdvertisments();
        buildBlockConfiguration();
        ReflectionTestUtils.setField(traMediaPlanService, "libItemService", libItemService);

    }

    @Test
    public void shouldMapFullMediaPlanWithPlaylistWhenPlaylistIsEmpty() throws Exception {

        when(libItemService.upload(anyString(), anyString(), any(MultipartFile.class))).thenReturn(libMediaItemToShuffle);
        TraMediaPlanDescriptor mediaPlanDescriptor = new TraMediaPlanDescriptor().order(traOrder).libMediaItem(libMediaItemToShuffle);
        TraMediaPlanTemplate traMediaPlanTemplate = new TraMediaPlanTemplate()
                .sheetIndexOfMediaPlan(0)
                .playlistDatePattern("dd-MMM-yyyy")
                .playlistDateStartColumn("G")
                .playlistDateEndColumn("CW")
                .playlistFirsValueCell("G8")
                .blockStartCell("A10")
                .blockEndCell("A47")
                .blockStartColumn("A")
                .blockHourSeparator("-")
                .firstEmissionValueCell("G10")
                .lastEmissionValueCell("CW47");
        mediaPlanDescriptor.setTraMediaPlanTemplate(traMediaPlanTemplate);

        //when
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("mediaplan/SAMPLE_MEDIAPLAN_1.xls");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(TestUtil.parseInputStream(inputStream).toByteArray());
        MultipartFile multipartFile = new MockMultipartFile("test", byteArrayInputStream);
        List<TraEmission> entityEmssionFlatList = Lists.newArrayList();


        TraMediaPlan traMediaPlan = traMediaPlanService.saveMediaPlan(multipartFile, mediaPlanDescriptor, corNetwork, corChannel);
        //collect emissions number from parsed excel

        List<TraMediaPlanEmission> mediaPlanEmissions = traMediaPlanEmissionRepository.findAllByNetwork_ShortcutAndChannel_ShortcutAndMediaPlan_Id(corNetwork.getShortcut(), corChannel.getShortcut(), traMediaPlan.getId());
        List<TraMediaPlanPlaylistDate> mediaPlanPlaylistDates = traMediaPlanPlaylistDateRepository.findAllByNetwork_ShortcutAndChannel_ShortcutAndMediaPlan_Id(corNetwork.getShortcut(), corChannel.getShortcut(), traMediaPlan.getId());


        List<LocalDate> playListsDates = mediaPlanPlaylistDates.stream().map(TraMediaPlanPlaylistDate::getPlaylistDate).sorted(Comparator.comparing(LocalDate::toString)).collect(Collectors.toList());
        List<TraPlaylist> entiyPlaylists = traPlaylistService.getTraPlaylistListInRange(playListsDates.get(0), playListsDates.get(playListsDates.size() - 1).plusDays(1), corNetwork.getShortcut(), corChannel.getShortcut());


        //then
        TraPlaylistDiff playlistOverview = traDefaultMediaPlanMapping.mapToEntityPlaylist(entiyPlaylists,mediaPlanEmissions , libMediaItemToShuffle);

        //transform to flat emission structure
        playlistOverview.getEntityPlaylist().stream().forEach(entityPlaylist -> entityPlaylist.getPlaylists().stream().forEach(entityTraBlock -> entityEmssionFlatList.addAll(entityTraBlock.getEmissions())));

        //assert
        assertEquals(playlistOverview.getEntityPlaylist().size(), mediaPlanPlaylistDates.size());
        assertEquals(entityEmssionFlatList.size(), mediaPlanEmissions.size());
        assertTrue(playlistOverview.getParsedFromExcel().isEmpty());
    }

    @Test
    public void shouldMapFullMediaPlanXlsxWithPlaylistWhenPlaylistIsEmptyAndInMediaPlanWeHaveMoreThanTwoCommercialInBlock() throws Exception {
        when(libItemService.upload(anyString(), anyString(), any(MultipartFile.class))).thenReturn(libMediaItemToShuffle);
        TraMediaPlanDescriptor mediaPlanDescriptor = new TraMediaPlanDescriptor().order(traOrder).libMediaItem(libMediaItemToShuffle);
        TraMediaPlanTemplate traMediaPlanTemplate = new TraMediaPlanTemplate()
                .sheetIndexOfMediaPlan(0)
                .playlistDatePattern("dd-MMM-yyyy")
                .playlistDateStartColumn("G")
                .playlistDateEndColumn("CW")
                .playlistFirsValueCell("G8")
                .blockStartCell("A10")
                .blockEndCell("A47")
                .blockStartColumn("A")
                .blockHourSeparator("-")
                .firstEmissionValueCell("G10")
                .lastEmissionValueCell("CW47");
        mediaPlanDescriptor.setTraMediaPlanTemplate(traMediaPlanTemplate);
        //when
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("mediaplan/SAMPLE_MEDIAPLAN_2.xlsx");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(TestUtil.parseInputStream(inputStream).toByteArray());
        MultipartFile multipartFile = new MockMultipartFile("test", byteArrayInputStream);
        List<TraEmission> entityEmssionFlatList = Lists.newArrayList();

        TraMediaPlan traMediaPlan = traMediaPlanService.saveMediaPlan(multipartFile, mediaPlanDescriptor, corNetwork, corChannel);
        //collect emissions number from parsed excel

        List<TraMediaPlanEmission> mediaPlanEmissions = traMediaPlanEmissionRepository.findAllByNetwork_ShortcutAndChannel_ShortcutAndMediaPlan_Id(corNetwork.getShortcut(), corChannel.getShortcut(), traMediaPlan.getId());
        List<TraMediaPlanPlaylistDate> mediaPlanPlaylistDates = traMediaPlanPlaylistDateRepository.findAllByNetwork_ShortcutAndChannel_ShortcutAndMediaPlan_Id(corNetwork.getShortcut(), corChannel.getShortcut(), traMediaPlan.getId());


        List<LocalDate> playListsDates = mediaPlanPlaylistDates.stream().map(TraMediaPlanPlaylistDate::getPlaylistDate).sorted(Comparator.comparing(LocalDate::toString)).collect(Collectors.toList());
        List<TraPlaylist> entiyPlaylists = traPlaylistService.getTraPlaylistListInRange(playListsDates.get(0), playListsDates.get(playListsDates.size() - 1).plusDays(1), corNetwork.getShortcut(), corChannel.getShortcut());

        //then
        TraPlaylistDiff playlistOverview = traDefaultMediaPlanMapping.mapToEntityPlaylist(entiyPlaylists,mediaPlanEmissions , libMediaItemToShuffle);

        //transform to flat emission structure
        playlistOverview.getEntityPlaylist().stream().forEach(entityPlaylist -> entityPlaylist.getPlaylists().stream().forEach(entityTraBlock -> entityEmssionFlatList.addAll(entityTraBlock.getEmissions())));

        //assert
        assertEquals(playlistOverview.getEntityPlaylist().size(), mediaPlanPlaylistDates.size());
        assertEquals(entityEmssionFlatList.size(), mediaPlanEmissions.size());
        assertTrue(playlistOverview.getParsedFromExcel().isEmpty());
    }

    @Test
    public void shouldMapFullMediaPlanEurozetWithPlaylistWhenPlaylistIsEmpty() throws Exception {
        traPlaylistService.deleteAllPlaylist();
        when(libItemService.upload(anyString(), anyString(), any(MultipartFile.class))).thenReturn(libMediaItemToShuffle.length(0.0));
        TraMediaPlanDescriptor mediaPlanDescriptor = new TraMediaPlanDescriptor().order(traOrder).libMediaItem(libMediaItemToShuffle.length(0.0));
        TraMediaPlanTemplate traMediaPlanTemplate = new TraMediaPlanTemplate().sheetIndexOfMediaPlan(0)
                .playlistDatePattern("dd-MMM-yyyy")
                .playlistDateStartColumn("G")
                .playlistDateEndColumn("AW")
                .playlistFirsValueCell("G22")
                .blockStartCell("A26")
                .blockEndCell("A63")
                .blockStartColumn("A")
                .blockHourSeparator("-")
                .firstEmissionValueCell("G26")
                .lastEmissionValueCell("AV64");

        mediaPlanDescriptor.setTraMediaPlanTemplate(traMediaPlanTemplate);

        //when
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("mediaplan/SAMPLE_MEDIAPLAN_4.xls");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(TestUtil.parseInputStream(inputStream).toByteArray());
        MultipartFile multipartFile = new MockMultipartFile("test", byteArrayInputStream);
        List<TraEmission> entityEmssionFlatList = Lists.newArrayList();


        TraMediaPlan traMediaPlan = traMediaPlanService.saveMediaPlan(multipartFile, mediaPlanDescriptor, corNetwork, corChannel);
        //collect emissions number from parsed excel

        List<TraMediaPlanEmission> mediaPlanEmissions = traMediaPlanEmissionRepository.findAllByNetwork_ShortcutAndChannel_ShortcutAndMediaPlan_Id(corNetwork.getShortcut(), corChannel.getShortcut(), traMediaPlan.getId());
        List<TraMediaPlanPlaylistDate> mediaPlanPlaylistDates = traMediaPlanPlaylistDateRepository.findAllByNetwork_ShortcutAndChannel_ShortcutAndMediaPlan_Id(corNetwork.getShortcut(), corChannel.getShortcut(), traMediaPlan.getId());


        List<LocalDate> playListsDates = mediaPlanPlaylistDates.stream().map(TraMediaPlanPlaylistDate::getPlaylistDate).sorted(Comparator.comparing(LocalDate::toString)).collect(Collectors.toList());
        List<TraPlaylist> entiyPlaylists = traPlaylistService.getTraPlaylistListInRange(playListsDates.get(0), playListsDates.get(playListsDates.size() - 1).plusDays(1), corNetwork.getShortcut(), corChannel.getShortcut());

        //then
        TraPlaylistDiff playlistOverview = traDefaultMediaPlanMapping.mapToEntityPlaylist(entiyPlaylists,mediaPlanEmissions , libMediaItemToShuffle);

        //transform to flat emission structure
        playlistOverview.getEntityPlaylist().stream().forEach(entityPlaylist -> entityPlaylist.getPlaylists().stream().forEach(entityTraBlock -> entityEmssionFlatList.addAll(entityTraBlock.getEmissions())));

        //assert
        assertEquals(playlistOverview.getEntityPlaylist().size(), mediaPlanPlaylistDates.size());
        assertEquals(entityEmssionFlatList.size(), mediaPlanEmissions.size());
        assertTrue(playlistOverview.getParsedFromExcel().isEmpty());
    }

    @Test
    public void shouldMapMediaPlanWithNoteEmptyBlocks() throws IOException, TikaException, SAXException, InvalidFormatException {
        LocalDate localDate = LocalDate.of(2017, 06, 12);
        List<TraPlaylist> entiyPlaylists = Lists.newArrayList();
        for (int i = 0; i < 35; i++) {
            entiyPlaylists.add(buildTraPlaylistWithEmissions(localDate.plusDays(i)));

        }
        when(libItemService.upload(anyString(), anyString(), any(MultipartFile.class))).thenReturn(libMediaItemToShuffle);
        TraMediaPlanDescriptor mediaPlanDescriptor = new TraMediaPlanDescriptor().order(traOrder).libMediaItem(libMediaItemToShuffle);
        TraMediaPlanTemplate traMediaPlanTemplate = new TraMediaPlanTemplate().sheetIndexOfMediaPlan(0)
                .sheetIndexOfMediaPlan(0)
                .playlistDatePattern("dd-MMM-yyyy")
                .playlistDateStartColumn("G")
                .playlistDateEndColumn("AW")
                .playlistFirsValueCell("G22")
                .blockStartCell("A26")
                .blockEndCell("A63")
                .blockStartColumn("A")
                .blockHourSeparator("-")
                .firstEmissionValueCell("G26")
                .lastEmissionValueCell("AV64");
        mediaPlanDescriptor.setTraMediaPlanTemplate(traMediaPlanTemplate);

        //when
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("mediaplan/SAMPLE_MEDIAPLAN_4.xls");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(TestUtil.parseInputStream(inputStream).toByteArray());
        MultipartFile multipartFile = new MockMultipartFile("test", byteArrayInputStream);
        List<TraEmission> entityEmssionFlatList = Lists.newArrayList();

        TraMediaPlan traMediaPlan = traMediaPlanService.saveMediaPlan(multipartFile, mediaPlanDescriptor, corNetwork, corChannel);
        //collect emissions number from parsed excel

        List<TraMediaPlanEmission> mediaPlanEmissions = traMediaPlanEmissionRepository.findAllByNetwork_ShortcutAndChannel_ShortcutAndMediaPlan_Id(corNetwork.getShortcut(), corChannel.getShortcut(), traMediaPlan.getId());

        //then
        TraPlaylistDiff playlistOverview = traDefaultMediaPlanMapping.mapToEntityPlaylist(entiyPlaylists, mediaPlanEmissions, libMediaItemToShuffle);

        //transform to flat emission structure
        playlistOverview.getEntityPlaylist().stream().forEach(entityPlaylist -> entityPlaylist.getPlaylists().stream().forEach(entityTraBlock -> entityEmssionFlatList.addAll(entityTraBlock.getEmissions())));

        //assert
        assertFalse(playlistOverview.getParsedFromExcel().isEmpty());

    }

    @Test
    public void shouldMapMediaPlanXlsxWithNumberOfCommerciaLargerThan1InCellWithNoteEmptyBlocks() throws IOException, TikaException, SAXException, InvalidFormatException {
        LocalDate localDate = LocalDate.of(2013, 10, 10);
        List<TraPlaylist> entiyPlaylists = Lists.newArrayList();
        for (int i = 0; i < 35; i++) {
            entiyPlaylists.add(buildTraPlaylistWithEmissions(localDate.plusDays(i)));

        }
        when(libItemService.upload(anyString(), anyString(), any(MultipartFile.class))).thenReturn(libMediaItemToShuffle);
        TraMediaPlanDescriptor mediaPlanDescriptor = new TraMediaPlanDescriptor().order(traOrder).libMediaItem(libMediaItemToShuffle);
        TraMediaPlanTemplate traMediaPlanTemplate = new TraMediaPlanTemplate().sheetIndexOfMediaPlan(0).sheetIndexOfMediaPlan(0)
                .playlistDatePattern("dd-MMM-yyyy")
                .playlistDateStartColumn("G")
                .playlistDateEndColumn("CW")
                .playlistFirsValueCell("G8")
                .blockStartCell("A10")
                .blockEndCell("A47")
                .blockStartColumn("A")
                .blockHourSeparator("-")
                .firstEmissionValueCell("G10")
                .lastEmissionValueCell("CW47");
        mediaPlanDescriptor.setTraMediaPlanTemplate(traMediaPlanTemplate);

        //when
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("mediaplan/SAMPLE_MEDIAPLAN_2.xlsx");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(TestUtil.parseInputStream(inputStream).toByteArray());
        MultipartFile multipartFile = new MockMultipartFile("test", byteArrayInputStream);
        List<TraEmission> entityEmssionFlatList = Lists.newArrayList();


        TraMediaPlan traMediaPlan = traMediaPlanService.saveMediaPlan(multipartFile, mediaPlanDescriptor, corNetwork, corChannel);
        //collect emissions number from parsed excel

        List<TraMediaPlanEmission> mediaPlanEmissions = traMediaPlanEmissionRepository.findAllByNetwork_ShortcutAndChannel_ShortcutAndMediaPlan_Id(corNetwork.getShortcut(), corChannel.getShortcut(), traMediaPlan.getId());
        List<TraMediaPlanPlaylistDate> mediaPlanPlaylistDates = traMediaPlanPlaylistDateRepository.findAllByNetwork_ShortcutAndChannel_ShortcutAndMediaPlan_Id(corNetwork.getShortcut(), corChannel.getShortcut(), traMediaPlan.getId());

        //then
        TraPlaylistDiff playlistOverview = traDefaultMediaPlanMapping.mapToEntityPlaylist(entiyPlaylists,mediaPlanEmissions , libMediaItemToShuffle);

        //transform to flat emission structure
        playlistOverview.getEntityPlaylist().stream().forEach(entityPlaylist -> entityPlaylist.getPlaylists().stream().forEach(entityTraBlock -> entityEmssionFlatList.addAll(entityTraBlock.getEmissions())));

        //transform to flat emission structure
        playlistOverview.getEntityPlaylist().stream().forEach(entityPlaylist -> entityPlaylist.getPlaylists().stream().forEach(entityTraBlock -> entityEmssionFlatList.addAll(entityTraBlock.getEmissions())));
        List<TraBlock> traBlockList = Lists.newArrayList();
        playlistOverview.getEntityPlaylist().stream().forEach(entityPlaylist -> entityPlaylist.getPlaylists().stream().forEach(entityTraBlock -> {
            if (entityTraBlock.getEmissions().stream().filter(traEmission -> traEmission.getAdvertiment().getId() == advertisementToShuffle.getId()).count() > 1) {
                traBlockList.add(entityTraBlock);
            }
        }));

        //assert
        assertTrue(playlistOverview.getParsedFromExcel().isEmpty());

    }
}
