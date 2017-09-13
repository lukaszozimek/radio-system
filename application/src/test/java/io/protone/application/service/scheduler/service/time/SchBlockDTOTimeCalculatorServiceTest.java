package io.protone.application.service.scheduler.service.time;

import io.protone.application.ProtoneApp;
import io.protone.application.service.scheduler.base.SchedulerBaseTest;
import io.protone.scheduler.api.dto.SchBlockDTO;
import io.protone.scheduler.service.time.SchBlockDTOTimeCalculatorService;
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
public class SchBlockDTOTimeCalculatorServiceTest extends SchedulerBaseTest {

    @Autowired
    private SchBlockDTOTimeCalculatorService schBlockDTOTimeCalculatorService;

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void shouldCalculateTimeForBlock() {
        //when
        SchBlockDTO schBlockDTO = buildBlockDTOWithEmissionAndAttachmentsLenght();
        SchBlockDTO resultDTO = schBlockDTOTimeCalculatorService.calculateTimeInBlockDTO(schBlockDTO, LocalDateTime.of(2017, 9, 8, 0, 0, 0), null);
        assertEquals(schBlockDTO.getEmissions().size(), resultDTO.getEmissions().size());
        resultDTO.getEmissions().stream().forEach(schEmissionDTO -> {
            assertNotNull(schEmissionDTO.getStartTime());
            assertNotNull(schEmissionDTO.getEndTime());
            assertNotNull(schEmissionDTO.getSequence());
        });
    }

    @Test
    public void shouldCalculateTimeForBlockNestedBlocks() {
        SchBlockDTO schBlockDTO = buildBlockDTOWithEmissionAndAttachmentsAndNestedBlock(1);
        SchBlockDTO resultDTO = schBlockDTOTimeCalculatorService.calculateTimeInBlockDTO(schBlockDTO, LocalDateTime.of(2017, 9, 8, 0, 0, 0), null);
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