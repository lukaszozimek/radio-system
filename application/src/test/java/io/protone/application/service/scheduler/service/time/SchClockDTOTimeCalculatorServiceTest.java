package io.protone.application.service.scheduler.service.time;

import com.google.common.collect.Lists;
import io.protone.application.ProtoneApp;
import io.protone.application.service.scheduler.base.SchedulerBaseTest;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by lukaszozimek on 08/09/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchClockDTOTimeCalculatorServiceTest extends SchedulerBaseTest {
    @Autowired
    private SchClockDTOTimeCalculatorService schClockDTOTimeCalculatorService;

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void shouldCalculateTimeForBlockNestedBlocks() {
        SchBlockDTO schBlockDTO = buildBlockDTOWithEmissionAndAttachmentsAndNestedBlock(1);
        SchClockDTO schClockDTO = new SchClockDTO().addBlocksItem(schBlockDTO);
        schClockDTO.setEmissions(Lists.newArrayList(buildEmissionDTO(2), buildEmissionDTO(3), buildEmissionDTO(4)));
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