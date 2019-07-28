package io.protone.application.service.traffic;

import io.protone.application.ProtoneApp;
import io.protone.application.service.traffic.base.TraPlaylistBasedTest;
import io.protone.application.util.TestUtil;
import io.protone.traffic.domain.*;
import io.protone.traffic.repository.TraMediaPlanBlockRepository;
import io.protone.traffic.repository.TraMediaPlanEmissionRepository;
import io.protone.traffic.repository.TraMediaPlanPlaylistDateRepository;
import io.protone.traffic.repository.TraMediaPlanRepository;
import io.protone.traffic.service.mediaplan.TraExcelMediaParserXlsPlan;
import io.protone.traffic.service.mediaplan.descriptor.TraMediaPlanDescriptor;
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
    private TraExcelMediaParserXlsPlan traExcelMediaXlsPlan;

    @Autowired
    private TraMediaPlanRepository traMediaPlanRepository;

    @Autowired
    private TraMediaPlanEmissionRepository traMediaPlanEmissionRepository;

    @Autowired
    private TraMediaPlanPlaylistDateRepository traMediaPlanPlaylistDateRepository;

    @Autowired
    private TraMediaPlanBlockRepository traMediaPlanBlockRepository;

    @Before
    public void setup() throws InterruptedException {
        buildMustHavePojos();
        traPlaylists = new ArrayList<>();
        mediaItemList = buildMediaItems();
        advertisements = buildAdvertisments();
        traMediaPlanEmissionRepository.deleteAllInBatch();
        traMediaPlanEmissionRepository.flush();
        traMediaPlanPlaylistDateRepository.deleteAllInBatch();
        traMediaPlanPlaylistDateRepository.flush();
        traMediaPlanBlockRepository.deleteAllInBatch();
        traMediaPlanBlockRepository.flush();
        traMediaPlanRepository.deleteAllInBatch();
        traMediaPlanRepository.flush();
    }

    @Test
    public void saveMediaPlan() throws Exception {
        final int NUMBER_OF_PLAYLISTS_IN_MEDIA_PLAN_1 = 35;
        final int NUMBER_OF_BLOCK_IN_MEDIA_PLAN_1 = 38;
        final int NUMBER_OF_EMISSIONS_IN_MEDIA_PLAN_1 = 75;

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
        TraMediaPlan traMediaPlan = factory.manufacturePojo(TraMediaPlan.class).account(crmAccount).network(corNetwork).fileItem(libFileItem).channel(corChannel).network(corNetwork);
        traMediaPlan.setId(null);
        traMediaPlan = traMediaPlanRepository.save(traMediaPlan);
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("mediaplan/SAMPLE_MEDIAPLAN_1.xls");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(TestUtil.parseInputStream(inputStream).toByteArray());

        //then
        traExcelMediaXlsPlan.parseMediaPlan(byteArrayInputStream, traMediaPlan, mediaPlanDescriptor, corNetwork, corChannel);
        List<TraMediaPlanEmission> mediaPlanEmissions = traMediaPlanEmissionRepository.findAllByNetwork_ShortcutAndChannel_ShortcutAndMediaPlan_Id(corNetwork.getShortcut(), corChannel.getShortcut(), traMediaPlan.getId());
        List<TraMediaPlanPlaylistDate> mediaPlanPlaylistDates = traMediaPlanPlaylistDateRepository.findAllByNetwork_ShortcutAndChannel_ShortcutAndMediaPlan_Id(corNetwork.getShortcut(), corChannel.getShortcut(), traMediaPlan.getId());
        List<TraMediaPlanBlock> traMediaPlanBlocks = traMediaPlanBlockRepository.findAllByNetwork_ShortcutAndChannel_ShortcutAndMediaPlan_Id(corNetwork.getShortcut(), corChannel.getShortcut(), traMediaPlan.getId());

        //assert
        assertNotNull(mediaPlanPlaylistDates);
        assertEquals(NUMBER_OF_PLAYLISTS_IN_MEDIA_PLAN_1, mediaPlanPlaylistDates.size());
        assertEquals(NUMBER_OF_EMISSIONS_IN_MEDIA_PLAN_1, mediaPlanEmissions.size());
        assertEquals(NUMBER_OF_BLOCK_IN_MEDIA_PLAN_1, traMediaPlanBlocks.size());
        inputStream.close();


    }

    @Test
    public void saveMediaPlanXlsxAndCommercialNumberGraterThan1() throws Exception {
        final int NUMBER_OF_PLAYLISTS_IN_MEDIA_PLAN_1 = 35;
        final int NUMBER_OF_BLOCK_IN_MEDIA_PLAN_1 = 38;
        final int NUMBER_OF_EMISSIONS_IN_MEDIA_PLAN_1 = 80;
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
        TraMediaPlan traMediaPlan = factory.manufacturePojo(TraMediaPlan.class).account(crmAccount).network(corNetwork).fileItem(libFileItem).channel(corChannel).network(corNetwork);
        traMediaPlan.setId(null);
        traMediaPlan = traMediaPlanRepository.save(traMediaPlan);
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("mediaplan/SAMPLE_MEDIAPLAN_2.xlsx");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(TestUtil.parseInputStream(inputStream).toByteArray());

        //then
        traExcelMediaXlsPlan.parseMediaPlan(byteArrayInputStream, traMediaPlan, mediaPlanDescriptor, corNetwork, corChannel);
        List<TraMediaPlanEmission> mediaPlanEmissions = traMediaPlanEmissionRepository.findAllByNetwork_ShortcutAndChannel_ShortcutAndMediaPlan_Id(corNetwork.getShortcut(), corChannel.getShortcut(), traMediaPlan.getId());
        List<TraMediaPlanPlaylistDate> mediaPlanPlaylistDates = traMediaPlanPlaylistDateRepository.findAllByNetwork_ShortcutAndChannel_ShortcutAndMediaPlan_Id(corNetwork.getShortcut(), corChannel.getShortcut(), traMediaPlan.getId());
        List<TraMediaPlanBlock> traMediaPlanBlocks = traMediaPlanBlockRepository.findAllByNetwork_ShortcutAndChannel_ShortcutAndMediaPlan_Id(corNetwork.getShortcut(), corChannel.getShortcut(), traMediaPlan.getId());


        assertNotNull(mediaPlanPlaylistDates);
        assertEquals(NUMBER_OF_PLAYLISTS_IN_MEDIA_PLAN_1, mediaPlanPlaylistDates.size());
        assertEquals(NUMBER_OF_EMISSIONS_IN_MEDIA_PLAN_1, mediaPlanEmissions.size());
        assertEquals(NUMBER_OF_BLOCK_IN_MEDIA_PLAN_1, traMediaPlanBlocks.size());

        inputStream.close();

    }

    @Test
    public void saveMediaPlan3() throws Exception {
        final int NUMBER_OF_PLAYLISTS_IN_MEDIA_PLAN_1 = 35;
        final int NUMBER_OF_BLOCK_IN_MEDIA_PLAN_1 = 38;
        final int NUMBER_OF_EMISSIONS_IN_MEDIA_PLAN_1 = 170;
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
        TraMediaPlan traMediaPlan = factory.manufacturePojo(TraMediaPlan.class).account(crmAccount).network(corNetwork).fileItem(libFileItem).channel(corChannel).network(corNetwork);
        traMediaPlan.setId(null);
        traMediaPlan = traMediaPlanRepository.save(traMediaPlan);
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("mediaplan/SAMPLE_MEDIAPLAN_3.xls");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(TestUtil.parseInputStream(inputStream).toByteArray());

        //then
        traExcelMediaXlsPlan.parseMediaPlan(byteArrayInputStream, traMediaPlan, mediaPlanDescriptor, corNetwork, corChannel);
        List<TraMediaPlanEmission> mediaPlanEmissions = traMediaPlanEmissionRepository.findAllByNetwork_ShortcutAndChannel_ShortcutAndMediaPlan_Id(corNetwork.getShortcut(), corChannel.getShortcut(), traMediaPlan.getId());
        List<TraMediaPlanPlaylistDate> mediaPlanPlaylistDates = traMediaPlanPlaylistDateRepository.findAllByNetwork_ShortcutAndChannel_ShortcutAndMediaPlan_Id(corNetwork.getShortcut(), corChannel.getShortcut(), traMediaPlan.getId());
        List<TraMediaPlanBlock> traMediaPlanBlocks = traMediaPlanBlockRepository.findAllByNetwork_ShortcutAndChannel_ShortcutAndMediaPlan_Id(corNetwork.getShortcut(), corChannel.getShortcut(), traMediaPlan.getId());


        assertNotNull(mediaPlanPlaylistDates);
        assertEquals(NUMBER_OF_PLAYLISTS_IN_MEDIA_PLAN_1, mediaPlanPlaylistDates.size());
        assertEquals(NUMBER_OF_EMISSIONS_IN_MEDIA_PLAN_1, mediaPlanEmissions.size());
        assertEquals(NUMBER_OF_BLOCK_IN_MEDIA_PLAN_1, traMediaPlanBlocks.size());
        inputStream.close();

    }

    @Test
    public void saveMediaPlan4() throws Exception {
        final int NUMBER_OF_PLAYLISTS_IN_MEDIA_PLAN_1 = 35;
        final int NUMBER_OF_BLOCK_IN_MEDIA_PLAN_1 = 38;
        final int NUMBER_OF_EMISSIONS_IN_MEDIA_PLAN_1 = 64;
        TraMediaPlanDescriptor mediaPlanDescriptor = new TraMediaPlanDescriptor().order(traOrder).libMediaItem(libMediaItemToShuffle);
        TraMediaPlanTemplate traMediaPlanTemplate = new TraMediaPlanTemplate()
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
        TraMediaPlan traMediaPlan = factory.manufacturePojo(TraMediaPlan.class).account(crmAccount).network(corNetwork).fileItem(libFileItem).channel(corChannel).network(corNetwork);
        traMediaPlan.setId(null);
        traMediaPlan = traMediaPlanRepository.save(traMediaPlan);
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("mediaplan/SAMPLE_MEDIAPLAN_4.xls");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(TestUtil.parseInputStream(inputStream).toByteArray());
        inputStream.close();
        //then
        traExcelMediaXlsPlan.parseMediaPlan(byteArrayInputStream, traMediaPlan, mediaPlanDescriptor, corNetwork, corChannel);
        List<TraMediaPlanEmission> mediaPlanEmissions = traMediaPlanEmissionRepository.findAllByNetwork_ShortcutAndChannel_ShortcutAndMediaPlan_Id(corNetwork.getShortcut(), corChannel.getShortcut(), traMediaPlan.getId());
        List<TraMediaPlanPlaylistDate> mediaPlanPlaylistDates = traMediaPlanPlaylistDateRepository.findAllByNetwork_ShortcutAndChannel_ShortcutAndMediaPlan_Id(corNetwork.getShortcut(), corChannel.getShortcut(), traMediaPlan.getId());
        List<TraMediaPlanBlock> traMediaPlanBlocks = traMediaPlanBlockRepository.findAllByNetwork_ShortcutAndChannel_ShortcutAndMediaPlan_Id(corNetwork.getShortcut(), corChannel.getShortcut(), traMediaPlan.getId());


        assertNotNull(mediaPlanPlaylistDates);
        assertEquals(NUMBER_OF_PLAYLISTS_IN_MEDIA_PLAN_1, mediaPlanPlaylistDates.size());
        assertEquals(NUMBER_OF_EMISSIONS_IN_MEDIA_PLAN_1, mediaPlanEmissions.size());
        assertEquals(NUMBER_OF_BLOCK_IN_MEDIA_PLAN_1, traMediaPlanBlocks.size());


    }


}
