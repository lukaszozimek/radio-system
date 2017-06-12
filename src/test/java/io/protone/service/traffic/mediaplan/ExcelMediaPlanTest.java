package io.protone.service.traffic.mediaplan;

import io.protone.ProtoneApp;
import io.protone.domain.TraEmission;
import io.protone.domain.TraMediaPlanPlaylist;
import io.protone.service.traffic.TraPlaylistService;
import io.protone.service.traffic.base.TraPlaylistBasedTest;
import io.protone.service.traffic.mediaplan.descriptor.TraMediaPlanDescriptor;
import io.protone.web.rest.mapper.TraMediaPlanMapperPlaylist;
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
public class ExcelMediaPlanTest extends TraPlaylistBasedTest {


    @Autowired
    private ExcelMediaPlan excelMediaPlan;

    @Autowired
    private TraPlaylistService traPlaylistService;

    @Autowired
    private TraMediaPlanMapperPlaylist traMediaPlanMapperPlaylistMapper;

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
            .traAdvertisment(advertisementToShuffle);


        //when
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("mediaplan/SAMPLE_MEDIAPLAN_1.xls");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(parseInputStream(inputStream).toByteArray());

        //then
        Set<TraMediaPlanPlaylist> playlistOverview = excelMediaPlan.parseMediaPlan(byteArrayInputStream, mediaPlanDescriptor, corNetwork, corChannel);

        //get Emission Flat structure
        playlistOverview.stream().forEach(parsedPlaylist -> parsedPlaylist.getPlaylists().stream().forEach(traBlock -> formPlaylistOverview.addAll(traBlock.getEmissions())));

        //assert
        assertNotNull(playlistOverview);
        assertEquals(NUMBER_OF_PLAYLISTS_IN_MEDIA_PLAN_1, playlistOverview.size());
        assertEquals(NUMBER_OF_EMISSIONS_IN_MEDIA_PLAN_1, formPlaylistOverview.size());
    }



}
