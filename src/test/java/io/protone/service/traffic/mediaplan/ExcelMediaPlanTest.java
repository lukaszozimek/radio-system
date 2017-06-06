package io.protone.service.traffic.mediaplan;

import java.time.LocalDate;
import java.util.*;

import io.protone.ProtoneApp;
import io.protone.domain.TraBlock;
import io.protone.domain.TraEmission;
import io.protone.domain.TraPlaylist;
import io.protone.domain.enumeration.CorDayOfWeekEnum;
import io.protone.service.traffic.base.TraPlaylistBasedTest;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
import java.util.stream.Collectors;

import static io.protone.util.TestUtil.parseInputStream;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

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
        //   ReflectionTestUtils.setField(excelMediaPlan, "s3Client", s3Client);
        // ReflectionTestUtils.setField(excelMediaPlan, "corUserService", corUserService);

        //when
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(parseInputStream(inputStream).toByteArray());
        ByteArrayInputStream secondStream = new ByteArrayInputStream(parseInputStream(Thread.currentThread().getContextClassLoader().getResourceAsStream("mediaplan/SAMPLE_MEDIAPLAN_1.xls")).toByteArray());
        Workbook workbook = new HSSFWorkbook(secondStream);
        Sheet sheet = workbook.getSheetAt(0);
        Map<Integer, LocalDate> dateHashMap = excelMediaPlan.findPlaylistInExcel(sheet);
        List<TraPlaylist> paredFromMediaPlan = excelMediaPlan.buildPlaylist(sheet, dateHashMap, advertisementToShuffle, corNetwork, corChannel);

        //then
        List<TraPlaylist> traPlaylistList = excelMediaPlan.saveMediaPlan(byteArrayInputStream, crmAccount, "", advertisementToShuffle, corNetwork, corChannel);

        List<TraEmission> entityEmssionFlatList = Lists.newArrayList();
        List<TraEmission> parsedEmssionFlatList = Lists.newArrayList();

        traPlaylistList.stream().forEach(entityPlaylist -> entityPlaylist.getPlaylists().stream().forEach(entityTraBlock -> entityEmssionFlatList.addAll(entityTraBlock.getEmissions())));
        paredFromMediaPlan.stream().forEach(parsedPlaylist -> parsedPlaylist.getPlaylists().stream().forEach(traBlock -> parsedEmssionFlatList.addAll(traBlock.getEmissions())));

    }


}
