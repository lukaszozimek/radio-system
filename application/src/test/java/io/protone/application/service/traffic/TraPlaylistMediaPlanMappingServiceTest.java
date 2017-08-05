package io.protone.application.service.traffic;


import io.protone.application.ProtoneApp;
import io.protone.application.service.traffic.base.TraPlaylistBasedTest;
import io.protone.application.util.TestUtil;
import io.protone.library.service.LibItemService;
import io.protone.traffic.domain.*;
import io.protone.traffic.mapper.TraMediaPlanMapperPlaylist;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.transaction.Transactional;
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
@Transactional
public class TraPlaylistMediaPlanMappingServiceTest extends TraPlaylistBasedTest {

    @Autowired
    private TraMediaPlanService traMediaPlanService;

    @Autowired
    private TraPlaylistService traPlaylistService;

    @Autowired
    private TraMediaPlanMapperPlaylist traMediaPlanMapperPlaylistMapper;
    @Autowired
    private TraPlaylistMediaPlanMappingService traPlaylistMediaPlanMappingService;

    @Autowired
    @Qualifier("traDefaultMediaPlanMapping")
    private TraMediaPlanMapping traDefaultMediaPlanMapping;
    @Mock
    private LibItemService libItemService;

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
        List<TraEmission> parsedEmssionFlatList = Lists.newArrayList();
        List<TraEmission> formPlaylistOverview = Lists.newArrayList();

        TraMediaPlan traMediaPlan = traMediaPlanService.saveMediaPlan(multipartFile, mediaPlanDescriptor, corNetwork, corChannel);
        //collect emissions number from parsed excel
        traMediaPlan.getPlaylists().stream().forEach(parsedPlaylist -> parsedPlaylist.getPlaylists().stream().forEach(traBlock -> parsedEmssionFlatList.addAll(traBlock.getEmissions())));
        List<LocalDate> playListsDates = traMediaPlan.getPlaylists().stream().map(TraMediaPlanPlaylist::getPlaylistDate).sorted(Comparator.comparing(LocalDate::toString)).collect(Collectors.toList());
        List<TraPlaylist> entiyPlaylists = traPlaylistService.getTraPlaylistListInRange(playListsDates.get(0), playListsDates.get(playListsDates.size() - 1).plusDays(1), corNetwork.getShortcut(), corChannel.getShortcut());

        //then
        TraPlaylistDiff playlistOverview = traDefaultMediaPlanMapping.mapToEntityPlaylist(entiyPlaylists, traMediaPlanMapperPlaylistMapper.mediaPlanPlaylistsToTraPlaylists(traMediaPlan.getPlaylists()), libMediaItemToShuffle);

        //transform to flat emission structure
        playlistOverview.getEntityPlaylist().stream().forEach(entityPlaylist -> entityPlaylist.getPlaylists().stream().forEach(entityTraBlock -> entityEmssionFlatList.addAll(entityTraBlock.getEmissions())));
        playlistOverview.getParsedFromExcel().stream().forEach(parsedPlaylist -> parsedPlaylist.getPlaylists().stream().forEach(traBlock -> formPlaylistOverview.addAll(traBlock.getEmissions())));

        //assert
        assertEquals(playlistOverview.getEntityPlaylist().size(), playlistOverview.getParsedFromExcel().size());
        assertEquals(parsedEmssionFlatList.size(), entityEmssionFlatList.size());
        assertTrue(formPlaylistOverview.isEmpty());
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
        List<TraEmission> parsedEmssionFlatList = Lists.newArrayList();
        List<TraEmission> formPlaylistOverview = Lists.newArrayList();

        TraMediaPlan traMediaPlan = traMediaPlanService.saveMediaPlan(multipartFile, mediaPlanDescriptor, corNetwork, corChannel);
        //collect emissions number from parsed excel
        traMediaPlan.getPlaylists().stream().forEach(parsedPlaylist -> parsedPlaylist.getPlaylists().stream().forEach(traBlock -> parsedEmssionFlatList.addAll(traBlock.getEmissions())));
        List<LocalDate> playListsDates = traMediaPlan.getPlaylists().stream().map(TraMediaPlanPlaylist::getPlaylistDate).sorted(Comparator.comparing(LocalDate::toString)).collect(Collectors.toList());
        List<TraPlaylist> entiyPlaylists = traPlaylistService.getTraPlaylistListInRange(playListsDates.get(0), playListsDates.get(playListsDates.size() - 1).plusDays(1), corNetwork.getShortcut(), corChannel.getShortcut());

        //then
        TraPlaylistDiff playlistOverview = traDefaultMediaPlanMapping.mapToEntityPlaylist(entiyPlaylists, traMediaPlanMapperPlaylistMapper.mediaPlanPlaylistsToTraPlaylists(traMediaPlan.getPlaylists()), libMediaItemToShuffle);

        //transform to flat emission structure
        playlistOverview.getEntityPlaylist().stream().forEach(entityPlaylist -> entityPlaylist.getPlaylists().stream().forEach(entityTraBlock -> entityEmssionFlatList.addAll(entityTraBlock.getEmissions())));
        playlistOverview.getParsedFromExcel().stream().forEach(parsedPlaylist -> parsedPlaylist.getPlaylists().stream().forEach(traBlock -> formPlaylistOverview.addAll(traBlock.getEmissions())));
        //assert
        assertEquals(playlistOverview.getEntityPlaylist().size(), playlistOverview.getParsedFromExcel().size());
        assertEquals(parsedEmssionFlatList.size(), entityEmssionFlatList.size());
        assertTrue(formPlaylistOverview.isEmpty());
    }

    @Test
    public void shouldMapFullMediaPlanEurozetWithPlaylistWhenPlaylistIsEmpty() throws Exception {
        when(libItemService.upload(anyString(), anyString(), any(MultipartFile.class))).thenReturn(libMediaItemToShuffle);
        TraMediaPlanDescriptor mediaPlanDescriptor = new TraMediaPlanDescriptor().order(traOrder).libMediaItem(libMediaItemToShuffle);
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
        List<TraEmission> parsedEmssionFlatList = Lists.newArrayList();
        List<TraEmission> formPlaylistOverview = Lists.newArrayList();

        TraMediaPlan traMediaPlan = traMediaPlanService.saveMediaPlan(multipartFile, mediaPlanDescriptor, corNetwork, corChannel);
        //collect emissions number from parsed excel
        traMediaPlan.getPlaylists().stream().forEach(parsedPlaylist -> parsedPlaylist.getPlaylists().stream().forEach(traBlock -> parsedEmssionFlatList.addAll(traBlock.getEmissions())));
        List<LocalDate> playListsDates = traMediaPlan.getPlaylists().stream().map(TraMediaPlanPlaylist::getPlaylistDate).sorted(Comparator.comparing(LocalDate::toString)).collect(Collectors.toList());
        List<TraPlaylist> entiyPlaylists = traPlaylistService.getTraPlaylistListInRange(playListsDates.get(0), playListsDates.get(playListsDates.size() - 1).plusDays(1), corNetwork.getShortcut(), corChannel.getShortcut());

        //then
        TraPlaylistDiff playlistOverview = traDefaultMediaPlanMapping.mapToEntityPlaylist(entiyPlaylists, traMediaPlanMapperPlaylistMapper.mediaPlanPlaylistsToTraPlaylists(traMediaPlan.getPlaylists()), libMediaItemToShuffle);

        //transform to flat emission structure
        playlistOverview.getEntityPlaylist().stream().forEach(entityPlaylist -> entityPlaylist.getPlaylists().stream().forEach(entityTraBlock -> entityEmssionFlatList.addAll(entityTraBlock.getEmissions())));
        playlistOverview.getParsedFromExcel().stream().forEach(parsedPlaylist -> parsedPlaylist.getPlaylists().stream().forEach(traBlock -> formPlaylistOverview.addAll(traBlock.getEmissions())));

        //assert
        assertEquals(playlistOverview.getEntityPlaylist().size(), playlistOverview.getParsedFromExcel().size());
        assertEquals(parsedEmssionFlatList.size(), entityEmssionFlatList.size());
        assertTrue(formPlaylistOverview.isEmpty());
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
        List<TraEmission> parsedEmssionFlatList = Lists.newArrayList();
        List<TraEmission> formPlaylistOverview = Lists.newArrayList();

        TraMediaPlan traMediaPlan = traMediaPlanService.saveMediaPlan(multipartFile, mediaPlanDescriptor, corNetwork, corChannel);
        //collect emissions number from parsed excel
        traMediaPlan.getPlaylists().stream().forEach(parsedPlaylist -> parsedPlaylist.getPlaylists().stream().forEach(traBlock -> parsedEmssionFlatList.addAll(traBlock.getEmissions())));

        //then
        TraPlaylistDiff playlistOverview = traDefaultMediaPlanMapping.mapToEntityPlaylist(entiyPlaylists, traMediaPlanMapperPlaylistMapper.mediaPlanPlaylistsToTraPlaylists(traMediaPlan.getPlaylists()), libMediaItemToShuffle);

        //transform to flat emission structure
        playlistOverview.getEntityPlaylist().stream().forEach(entityPlaylist -> entityPlaylist.getPlaylists().stream().forEach(entityTraBlock -> entityEmssionFlatList.addAll(entityTraBlock.getEmissions())));
        playlistOverview.getParsedFromExcel().stream().forEach(parsedPlaylist -> parsedPlaylist.getPlaylists().stream().forEach(traBlock -> formPlaylistOverview.addAll(traBlock.getEmissions())));

        //assert
        assertFalse(formPlaylistOverview.isEmpty());

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
        List<TraEmission> parsedEmssionFlatList = Lists.newArrayList();
        List<TraEmission> formPlaylistOverview = Lists.newArrayList();

        TraMediaPlan traMediaPlan = traMediaPlanService.saveMediaPlan(multipartFile, mediaPlanDescriptor, corNetwork, corChannel);
        //collect emissions number from parsed excel
        traMediaPlan.getPlaylists().stream().forEach(parsedPlaylist -> parsedPlaylist.getPlaylists().stream().forEach(traBlock -> parsedEmssionFlatList.addAll(traBlock.getEmissions())));

        //then
        TraPlaylistDiff playlistOverview = traDefaultMediaPlanMapping.mapToEntityPlaylist(entiyPlaylists, traMediaPlanMapperPlaylistMapper.mediaPlanPlaylistsToTraPlaylists(traMediaPlan.getPlaylists()), libMediaItemToShuffle);

        //transform to flat emission structure
        playlistOverview.getEntityPlaylist().stream().forEach(entityPlaylist -> entityPlaylist.getPlaylists().stream().forEach(entityTraBlock -> entityEmssionFlatList.addAll(entityTraBlock.getEmissions())));
        playlistOverview.getParsedFromExcel().stream().forEach(parsedPlaylist -> parsedPlaylist.getPlaylists().stream().forEach(traBlock -> formPlaylistOverview.addAll(traBlock.getEmissions())));
        List<TraBlock> traBlockList = Lists.newArrayList();
        playlistOverview.getEntityPlaylist().stream().forEach(entityPlaylist -> entityPlaylist.getPlaylists().stream().forEach(entityTraBlock -> {
            if (entityTraBlock.getEmissions().stream().filter(traEmission -> traEmission.getAdvertiment().getId() == advertisementToShuffle.getId()).count() > 1) {
                traBlockList.add(entityTraBlock);
            }
        }));

        //assert
        assertFalse(formPlaylistOverview.isEmpty());

    }
}
