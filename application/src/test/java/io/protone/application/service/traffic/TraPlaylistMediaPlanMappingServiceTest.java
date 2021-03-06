package io.protone.application.service.traffic;


import io.protone.application.ProtoneApp;
import io.protone.application.service.traffic.base.TraPlaylistBasedTest;
import io.protone.application.util.TestUtil;
import io.protone.library.service.LibFileItemService;
import io.protone.traffic.domain.*;
import io.protone.traffic.repository.TraMediaPlanBlockRepository;
import io.protone.traffic.repository.TraMediaPlanEmissionRepository;
import io.protone.traffic.repository.TraMediaPlanPlaylistDateRepository;
import io.protone.traffic.repository.TraMediaPlanRepository;
import io.protone.traffic.service.TraMediaPlanService;
import io.protone.traffic.service.TraPlaylistService;
import io.protone.traffic.service.mediaplan.descriptor.TraMediaPlanDescriptor;
import io.protone.traffic.service.mediaplan.diff.TraPlaylistDiff;
import io.protone.traffic.service.mediaplan.mapping.TraMediaPlanMapping;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.tika.exception.TikaException;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TraPlaylistMediaPlanMappingServiceTest extends TraPlaylistBasedTest {

    @Autowired
    private TraMediaPlanService traMediaPlanService;

    @Autowired
    private TraPlaylistService traPlaylistService;

    @Autowired
    @Qualifier("traDefaultMediaPlanMapping")
    private TraMediaPlanMapping traDefaultMediaPlanMapping;

    @Mock
    private LibFileItemService libFileItemService;

    @Autowired
    private TraMediaPlanEmissionRepository traMediaPlanEmissionRepository;

    @Autowired
    private TraMediaPlanBlockRepository traMediaPlanBlockRepository;

    @Autowired
    private TraMediaPlanRepository traMediaPlanRepository;

    @Autowired
    private TraMediaPlanPlaylistDateRepository traMediaPlanPlaylistDateRepository;

    @Before
    public void setup() throws InterruptedException {
        traMediaPlanEmissionRepository.deleteAllInBatch();
        traMediaPlanEmissionRepository.flush();
        traMediaPlanPlaylistDateRepository.deleteAllInBatch();
        traMediaPlanPlaylistDateRepository.flush();
        traMediaPlanBlockRepository.deleteAllInBatch();
        traMediaPlanBlockRepository.flush();
        traMediaPlanRepository.deleteAllInBatch();
        traMediaPlanRepository.flush();
        this.traEmissionRepository.deleteAllInBatch();
        this.traEmissionRepository.flush();
        this.trablockRepository.deleteAllInBatch();
        this.trablockRepository.flush();
        this.traPlaylistRepository.deleteAllInBatch();
        this.traPlaylistRepository.flush();
        this.trablockConfigurationRepository.deleteAllInBatch();
        this.trablockConfigurationRepository.flush();

        MockitoAnnotations.initMocks(this);
        buildMustHavePojos();
        traPlaylists = new ArrayList<>();
        mediaItemList = buildMediaItems();
        advertisements = buildAdvertisments();
        buildBlockConfiguration();
        ReflectionTestUtils.setField(traMediaPlanService, "libFileItemService", libFileItemService);

    }

    @Test
    public void bshouldMapFullMediaPlanWithPlaylistWhenPlaylistIsEmpty() throws Exception {
        when(libFileItemService.uploadFileItem(anyString(), anyString(), any(MultipartFile.class))).thenReturn(libFileItem);
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
        TraPlaylistDiff playlistOverview = traDefaultMediaPlanMapping.mapToEntityPlaylist(entiyPlaylists, mediaPlanEmissions, libMediaItemToShuffle);

        //transform to flat emission structure
        playlistOverview.getEntityPlaylist().stream().forEach(entityPlaylist -> entityPlaylist.getPlaylists().stream().forEach(entityTraBlock -> entityEmssionFlatList.addAll(entityTraBlock.getEmissions())));

        inputStream.close();
        //assert
        assertEquals(playlistOverview.getEntityPlaylist().size(), mediaPlanPlaylistDates.size());
        assertEquals(mediaPlanEmissions.size(), entityEmssionFlatList.size());
        assertTrue(playlistOverview.getParsedFromExcel().isEmpty());

    }

    @Test
    public void cshouldMapFullMediaPlanXlsxWithPlaylistWhenPlaylistIsEmptyAndInMediaPlanWeHaveMoreThanTwoCommercialInBlock() throws Exception {
        when(libFileItemService.uploadFileItem(anyString(), anyString(), any(MultipartFile.class))).thenReturn(libFileItem);
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
        inputStream.close();
        //collect emissions number from parsed excel
        List<TraMediaPlanEmission> mediaPlanEmissions = traMediaPlanEmissionRepository.findAllByNetwork_ShortcutAndChannel_ShortcutAndMediaPlan_Id(corNetwork.getShortcut(), corChannel.getShortcut(), traMediaPlan.getId());
        List<TraMediaPlanPlaylistDate> mediaPlanPlaylistDates = traMediaPlanPlaylistDateRepository.findAllByNetwork_ShortcutAndChannel_ShortcutAndMediaPlan_Id(corNetwork.getShortcut(), corChannel.getShortcut(), traMediaPlan.getId());


        List<LocalDate> playListsDates = mediaPlanPlaylistDates.stream().map(TraMediaPlanPlaylistDate::getPlaylistDate).sorted(Comparator.comparing(LocalDate::toString)).collect(Collectors.toList());
        List<TraPlaylist> entiyPlaylists = traPlaylistService.getTraPlaylistListInRange(playListsDates.get(0), playListsDates.get(playListsDates.size() - 1).plusDays(1), corNetwork.getShortcut(), corChannel.getShortcut());

        //then
        TraPlaylistDiff playlistOverview = traDefaultMediaPlanMapping.mapToEntityPlaylist(entiyPlaylists, mediaPlanEmissions, libMediaItemToShuffle);

        //transform to flat emission structure
        playlistOverview.getEntityPlaylist().stream().forEach(entityPlaylist -> entityPlaylist.getPlaylists().stream().forEach(entityTraBlock -> entityEmssionFlatList.addAll(entityTraBlock.getEmissions())));

        //assert
        assertEquals(playlistOverview.getEntityPlaylist().size(), mediaPlanPlaylistDates.size());
        assertEquals(mediaPlanEmissions.size(), entityEmssionFlatList.size());
        assertTrue(playlistOverview.getParsedFromExcel().isEmpty());

    }

    @Test
    public void ashouldMapFullMediaPlanEurozetWithPlaylistWhenPlaylistIsEmpty() throws Exception {
        when(libFileItemService.uploadFileItem(anyString(), anyString(), any(MultipartFile.class))).thenReturn(libFileItem);
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


        TraMediaPlan traMediaPlan = traMediaPlanService.saveMediaPlan(multipartFile, mediaPlanDescriptor, corNetwork, corChannel);
        //collect emissions number from parsed excel

        List<TraMediaPlanEmission> mediaPlanEmissions = traMediaPlanEmissionRepository.findAllByNetwork_ShortcutAndChannel_ShortcutAndMediaPlan_Id(corNetwork.getShortcut(), corChannel.getShortcut(), traMediaPlan.getId());
        List<TraMediaPlanPlaylistDate> mediaPlanPlaylistDates = traMediaPlanPlaylistDateRepository.findAllByNetwork_ShortcutAndChannel_ShortcutAndMediaPlan_Id(corNetwork.getShortcut(), corChannel.getShortcut(), traMediaPlan.getId());


        List<LocalDate> playListsDates = mediaPlanPlaylistDates.stream().map(TraMediaPlanPlaylistDate::getPlaylistDate).sorted(Comparator.comparing(LocalDate::toString)).collect(Collectors.toList());
        List<TraPlaylist> entiyPlaylists = traPlaylistService.getTraPlaylistListInRange(playListsDates.get(0), playListsDates.get(playListsDates.size() - 1).plusDays(1), corNetwork.getShortcut(), corChannel.getShortcut());

        //then
        TraPlaylistDiff playlistOverview = traDefaultMediaPlanMapping.mapToEntityPlaylist(entiyPlaylists, mediaPlanEmissions, libMediaItemToShuffle);

        //transform to flat emission structure
        playlistOverview.getEntityPlaylist().stream().forEach(entityPlaylist -> entityPlaylist.getPlaylists().stream().forEach(entityTraBlock -> entityEmssionFlatList.addAll(entityTraBlock.getEmissions())));

        inputStream.close();
        //assert
        assertEquals(playlistOverview.getEntityPlaylist().size(), mediaPlanPlaylistDates.size());
        assertEquals(mediaPlanEmissions.size(), entityEmssionFlatList.size());
        assertTrue(playlistOverview.getParsedFromExcel().isEmpty());

    }


    @Test
    public void shouldMapMediaPlanXlsxWithNumberOfCommerciaLargerThan1InCellWithNoteEmptyBlocks() throws IOException, TikaException, SAXException, InvalidFormatException {

        LocalDate localDate = LocalDate.of(2013, 10, 10);
        List<TraPlaylist> entiyPlaylists = Lists.newArrayList();
        for (int i = 0; i < 35; i++) {
            entiyPlaylists.add(buildTraPlaylistWithEmissions(localDate.plusDays(i)));

        }
        when(libFileItemService.uploadFileItem(anyString(), anyString(), any(MultipartFile.class))).thenReturn(libFileItem);
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
        inputStream.close();

        TraMediaPlan traMediaPlan = traMediaPlanService.saveMediaPlan(multipartFile, mediaPlanDescriptor, corNetwork, corChannel);
        //collect emissions number from parsed excel

        List<TraMediaPlanEmission> mediaPlanEmissions = traMediaPlanEmissionRepository.findAllByNetwork_ShortcutAndChannel_ShortcutAndMediaPlan_Id(corNetwork.getShortcut(), corChannel.getShortcut(), traMediaPlan.getId());
        //then
        TraPlaylistDiff playlistOverview = traDefaultMediaPlanMapping.mapToEntityPlaylist(entiyPlaylists, mediaPlanEmissions, libMediaItemToShuffle);

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
        assertFalse(playlistOverview.getParsedFromExcel().isEmpty());
    }
}
