package io.protone.application.service.scheduler.service.time;

import com.google.common.collect.Lists;
import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorOrganization;
import io.protone.library.api.dto.thin.LibMediaItemThinDTO;
import io.protone.library.domain.LibMediaItem;
import io.protone.library.domain.LibMediaLibrary;
import io.protone.library.mapper.LibMediaItemThinMapper;
import io.protone.library.repository.LibMediaItemRepository;
import io.protone.scheduler.api.dto.SchBlockDTO;
import io.protone.scheduler.api.dto.SchClockDTO;
import io.protone.scheduler.service.time.SchClockDTOTimeCalculatorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Random;

import static io.protone.application.service.scheduler.base.SchedulerBaseTest.*;
import static io.protone.application.util.TestConstans.*;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * Created by lukaszozimek on 08/09/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchClockDTOTimeCalculatorServiceTest {
    @Autowired
    private SchClockDTOTimeCalculatorService schClockDTOTimeCalculatorService;
    @Autowired
    private LibMediaItemThinMapper libMediaItemThinMapper;
    @Autowired
    private LibMediaItemRepository libMediaItemRepository;

    private LibMediaItem libMediaItem;

    private LibMediaItemThinDTO libMediaItemThinDTO;

    private LibMediaLibrary libMediaLibrary;

    private CorChannel corChannel;

    private CorOrganization corOrganization;
    @Before
    public void setUp() throws Exception {
        corOrganization = new CorOrganization().shortcut(TEST_ORGANIZATION_SHORTCUT);
        corOrganization.setId(TEST_ORGANIZATION_ID);
        corChannel = new CorChannel().shortcut(TEST_CHANNEL_SHORTCUT);
        corChannel.setId(TEST_CHANNEL_ID);
        libMediaLibrary = new LibMediaLibrary();
        libMediaLibrary.setId(LIBRARY_ID);
        libMediaItem = new LibMediaItem().name(String.valueOf(new Random().nextLong())).library(libMediaLibrary).idx(String.valueOf(new Random().nextLong())).length(40.0).channel(corChannel);

        libMediaItem = libMediaItemRepository.saveAndFlush(libMediaItem);
        libMediaItemThinDTO = libMediaItemThinMapper.DB2DTO(libMediaItem);

    }

    @Test
    public void shouldCalculateTimeForBlockNestedBlocks() {
        SchBlockDTO schBlockDTO = buildBlockDTOWithEmissionAndAttachmentsAndNestedBlock(1, libMediaItemThinDTO);
        SchClockDTO schClockDTO = new SchClockDTO().addBlocksItem(schBlockDTO);
        schClockDTO.setEmissions(Lists.newArrayList(buildEmissionDTO(2, libMediaItemThinDTO), buildEmissionDTO(3, libMediaItemThinDTO), buildEmissionDTO(4, libMediaItemThinDTO)));
        SchClockDTO resultDTO = schClockDTOTimeCalculatorService.calculateTimeInClockDTO(schClockDTO, LocalDateTime.of(2017, 9, 8, 0, 0, 0), null);
        assertEquals(schBlockDTO.getEmissions().size(), resultDTO.getEmissions().size());
        resultDTO.getBlocks().stream().forEach(blockDTO -> {
            schBlockDTO.getEmissions().stream().forEach(schEmissionDTO -> {
                assertNotNull(schEmissionDTO.getStartTime());
                assertNotNull(schEmissionDTO.getEndTime());
                assertNotNull(schEmissionDTO.getSequence());
            });
            assertNotNull(blockDTO.getStartTime());
            assertNotNull(blockDTO.getEndTime());
        });
        resultDTO.getEmissions().stream().forEach(schEmissionDTO -> {
            assertNotNull(schEmissionDTO.getStartTime());
            assertNotNull(schEmissionDTO.getEndTime());
            assertNotNull(schEmissionDTO.getSequence());
        });
    }
}