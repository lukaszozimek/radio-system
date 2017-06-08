package io.protone.service.traffic.mediaplan;

import io.protone.ProtoneApp;
import io.protone.domain.TraEmission;
import io.protone.domain.TraPlaylist;
import io.protone.domain.enumeration.CorDayOfWeekEnum;
import io.protone.service.traffic.TraPlaylistService;
import io.protone.service.traffic.base.TraPlaylistBasedTest;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static io.protone.util.TestUtil.parseInputStream;
import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 04/06/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class ExcelMediaPlanTest extends TraPlaylistBasedTest {

    private InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("mediaplan/SAMPLE_MEDIAPLAN_1.xls");

    @Autowired
    private ExcelMediaPlan excelMediaPlan;

    @Autowired
    private TraPlaylistService traPlaylistService;


    @Before
    public void setup() throws InterruptedException {
        buildMustHavePojos();
        traPlaylists = new ArrayList<>();
        mediaItemList = buildMediaItems();
        advertisements = buildAdvertisments();
        trablockConfigurationRepository.save(buildBlockConfiguration(CorDayOfWeekEnum.DW_MONDAY));
        trablockConfigurationRepository.save(buildBlockConfiguration(CorDayOfWeekEnum.DW_TUESDAY));
        trablockConfigurationRepository.save(buildBlockConfiguration(CorDayOfWeekEnum.DW_WEDNESDAY));
        trablockConfigurationRepository.save(buildBlockConfiguration(CorDayOfWeekEnum.DW_THURSDAY));
        trablockConfigurationRepository.save(buildBlockConfiguration(CorDayOfWeekEnum.DW_FRIDAY));
        trablockConfigurationRepository.save(buildBlockConfiguration(CorDayOfWeekEnum.DW_SATURDAY));
        trablockConfigurationRepository.save(buildBlockConfiguration(CorDayOfWeekEnum.DW_SUNDAY));
    }

    @Test
    public void saveMediaPlan() throws Exception {
        final int NUMBER_OF_PLAYLISTS_IN_MEDIA_PLAN_1 = 35;
        final int NUMBER_OF_EMISSIONS_IN_MEDIA_PLAN_1 = 75;
        List<TraEmission> formPlaylistOverview = Lists.newArrayList();

        //when
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(parseInputStream(inputStream).toByteArray());

        //then
        List<TraPlaylist> playlistOverview = excelMediaPlan.parseMediaPlan(byteArrayInputStream, advertisementToShuffle, corNetwork, corChannel);

        //get Emission Flat structure
        playlistOverview.stream().forEach(parsedPlaylist -> parsedPlaylist.getPlaylists().stream().forEach(traBlock -> formPlaylistOverview.addAll(traBlock.getEmissions())));

        //assert
        assertNotNull(playlistOverview);
        assertEquals(NUMBER_OF_PLAYLISTS_IN_MEDIA_PLAN_1, playlistOverview.size());
        assertEquals(NUMBER_OF_EMISSIONS_IN_MEDIA_PLAN_1, formPlaylistOverview.size());
    }

    @Test
    public void shouldMapFullMediaPlanWithPlaylistWhenPlaylistIsEmpty() throws Exception {
        //when
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(parseInputStream(inputStream).toByteArray());


        List<TraEmission> entityEmssionFlatList = Lists.newArrayList();
        List<TraEmission> parsedEmssionFlatList = Lists.newArrayList();
        List<TraEmission> formPlaylistOverview = Lists.newArrayList();


        List<TraPlaylist> parsedMediaPlanPlaylist = excelMediaPlan.parseMediaPlan(byteArrayInputStream, advertisementToShuffle, corNetwork, corChannel);
        //collect emissions number from parsed excel
        parsedMediaPlanPlaylist.stream().forEach(parsedPlaylist -> parsedPlaylist.getPlaylists().stream().forEach(traBlock -> parsedEmssionFlatList.addAll(traBlock.getEmissions())));
        List<LocalDate> playListsDates = parsedMediaPlanPlaylist.stream().map(TraPlaylist::getPlaylistDate).sorted(Comparator.comparing(LocalDate::toString)).collect(Collectors.toList());
        List<TraPlaylist> entiyPlaylists = traPlaylistService.getTraPlaylistListInRange(playListsDates.get(0), playListsDates.get(playListsDates.size() - 1).plusDays(1), corNetwork.getShortcut(), corChannel.getShortcut());

        //then
        ExcelMediaPlan.PlaylistDiff playlistOverview = excelMediaPlan.mapToEntityPlaylist(entiyPlaylists, parsedMediaPlanPlaylist, advertisementToShuffle);

        //transform to flat emission structure
        playlistOverview.getEntityPlaylist().stream().forEach(entityPlaylist -> entityPlaylist.getPlaylists().stream().forEach(entityTraBlock -> entityEmssionFlatList.addAll(entityTraBlock.getEmissions())));
        playlistOverview.getParsedFromExcel().stream().forEach(parsedPlaylist -> parsedPlaylist.getPlaylists().stream().forEach(traBlock -> formPlaylistOverview.addAll(traBlock.getEmissions())));

        //assert
        assertEquals(playlistOverview.getEntityPlaylist().size(), playlistOverview.getParsedFromExcel().size());
        assertEquals(parsedEmssionFlatList.size(), entityEmssionFlatList.size());
        assertTrue(formPlaylistOverview.isEmpty());
    }


}
