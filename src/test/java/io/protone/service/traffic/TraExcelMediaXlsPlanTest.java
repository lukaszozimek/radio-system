package io.protone.service.traffic;

import io.protone.ProtoneApp;
import io.protone.domain.TraEmission;
import io.protone.domain.TraMediaPlanPlaylist;
import io.protone.service.traffic.base.TraPlaylistBasedTest;
import io.protone.traffic.service.mediaplan.descriptor.TraMediaPlanDescriptor;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static io.protone.util.TestUtil.parseInputStream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by lukaszozimek on 04/06/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class TraExcelMediaXlsPlanTest extends TraPlaylistBasedTest {

    @Autowired
    private TraExcelMediaXlsPlan traExcelMediaXlsPlan;

    @Before
    public void setup() throws InterruptedException {
        buildMustHavePojos();
        traPlaylists = new ArrayList<>();
        mediaItemList = buildMediaItems();
        advertisements = buildAdvertisments();
        buildBlockConfiguration();
    }

    @Test
    public void saveMediaPlan() throws Exception {
        final int NUMBER_OF_PLAYLISTS_IN_MEDIA_PLAN_1 = 35;
        final int NUMBER_OF_EMISSIONS_IN_MEDIA_PLAN_1 = 75;
        List<TraEmission> formPlaylistOverview = Lists.newArrayList();
        TraMediaPlanDescriptor mediaPlanDescriptor = new TraMediaPlanDescriptor()
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
            .lastEmissionValueCell("CW47")
            .order(traOrder);


        //when
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("mediaplan/SAMPLE_MEDIAPLAN_1.xls");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(parseInputStream(inputStream).toByteArray());

        //then
        Set<TraMediaPlanPlaylist> playlistOverview = traExcelMediaXlsPlan.parseMediaPlan(byteArrayInputStream, mediaPlanDescriptor, corNetwork, corChannel);

        //get Emission Flat structure
        playlistOverview.stream().forEach(parsedPlaylist -> parsedPlaylist.getPlaylists().stream().forEach(traBlock -> formPlaylistOverview.addAll(traBlock.getEmissions())));

        //assert
        assertNotNull(playlistOverview);
        assertEquals(NUMBER_OF_PLAYLISTS_IN_MEDIA_PLAN_1, playlistOverview.size());
        assertEquals(NUMBER_OF_EMISSIONS_IN_MEDIA_PLAN_1, formPlaylistOverview.size());
    }
    @Test
    public void saveMediaPlanXlsxAndCommercialNumberGraterThan1() throws Exception {
        final int NUMBER_OF_PLAYLISTS_IN_MEDIA_PLAN_1 = 35;
        final int NUMBER_OF_EMISSIONS_IN_MEDIA_PLAN_1 = 80;
        List<TraEmission> formPlaylistOverview = Lists.newArrayList();
        TraMediaPlanDescriptor mediaPlanDescriptor = new TraMediaPlanDescriptor()
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
            .lastEmissionValueCell("CW47")
            .order(traOrder);



        //when
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("mediaplan/SAMPLE_MEDIAPLAN_2.xlsx");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(parseInputStream(inputStream).toByteArray());

        //then
        Set<TraMediaPlanPlaylist> playlistOverview = traExcelMediaXlsPlan.parseMediaPlan(byteArrayInputStream, mediaPlanDescriptor, corNetwork, corChannel);

        //get Emission Flat structure
        playlistOverview.stream().forEach(parsedPlaylist -> parsedPlaylist.getPlaylists().stream().forEach(traBlock -> formPlaylistOverview.addAll(traBlock.getEmissions())));

        //assert
        assertNotNull(playlistOverview);
        assertEquals(NUMBER_OF_PLAYLISTS_IN_MEDIA_PLAN_1, playlistOverview.size());
        assertEquals(NUMBER_OF_EMISSIONS_IN_MEDIA_PLAN_1, formPlaylistOverview.size());
    }
    @Test
    public void saveMediaPlan3() throws Exception {
        final int NUMBER_OF_PLAYLISTS_IN_MEDIA_PLAN_1 = 35;
        final int NUMBER_OF_EMISSIONS_IN_MEDIA_PLAN_1 = 170;
        List<TraEmission> formPlaylistOverview = Lists.newArrayList();
        TraMediaPlanDescriptor mediaPlanDescriptor = new TraMediaPlanDescriptor()
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
            .lastEmissionValueCell("CW47")
            .order(traOrder);


        //when
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("mediaplan/SAMPLE_MEDIAPLAN_3.xls");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(parseInputStream(inputStream).toByteArray());

        //then
        Set<TraMediaPlanPlaylist> playlistOverview = traExcelMediaXlsPlan.parseMediaPlan(byteArrayInputStream, mediaPlanDescriptor, corNetwork, corChannel);

        //get Emission Flat structure
        playlistOverview.stream().forEach(parsedPlaylist -> parsedPlaylist.getPlaylists().stream().forEach(traBlock -> formPlaylistOverview.addAll(traBlock.getEmissions())));

        //assert
        assertNotNull(playlistOverview);
        assertEquals(NUMBER_OF_PLAYLISTS_IN_MEDIA_PLAN_1, playlistOverview.size());
        assertEquals(NUMBER_OF_EMISSIONS_IN_MEDIA_PLAN_1, formPlaylistOverview.size());
    }
    @Test
    public void saveMediaPlan4() throws Exception {
        final int NUMBER_OF_PLAYLISTS_IN_MEDIA_PLAN_1 = 35;
        final int NUMBER_OF_EMISSIONS_IN_MEDIA_PLAN_1 = 64;
        List<TraEmission> formPlaylistOverview = Lists.newArrayList();
        TraMediaPlanDescriptor mediaPlanDescriptor = new TraMediaPlanDescriptor()
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
            .lastEmissionValueCell("AV64")
            .order(traOrder);



        //when
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("mediaplan/SAMPLE_MEDIAPLAN_4.xls");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(parseInputStream(inputStream).toByteArray());

        //then
        Set<TraMediaPlanPlaylist> playlistOverview = traExcelMediaXlsPlan.parseMediaPlan(byteArrayInputStream, mediaPlanDescriptor, corNetwork, corChannel);

        //get Emission Flat structure
        playlistOverview.stream().forEach(parsedPlaylist -> parsedPlaylist.getPlaylists().stream().forEach(traBlock -> formPlaylistOverview.addAll(traBlock.getEmissions())));

        //assert
        assertNotNull(playlistOverview);
        assertEquals(NUMBER_OF_PLAYLISTS_IN_MEDIA_PLAN_1, playlistOverview.size());
        assertEquals(NUMBER_OF_EMISSIONS_IN_MEDIA_PLAN_1, formPlaylistOverview.size());
    }




}
