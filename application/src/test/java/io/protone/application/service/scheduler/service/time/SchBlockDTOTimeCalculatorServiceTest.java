package io.protone.application.service.scheduler.service.time;

import io.protone.application.ProtoneApp;
import io.protone.application.web.api.cor.CorNetworkResourceIntTest;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.library.api.dto.thin.LibMediaItemThinDTO;
import io.protone.library.domain.LibMediaItem;
import io.protone.library.domain.LibMediaLibrary;
import io.protone.library.mapper.LibMediaItemThinMapper;
import io.protone.library.repository.LibMediaItemRepository;
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
import java.util.Random;

import static io.protone.application.service.scheduler.base.SchedulerBaseTest.*;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * Created by lukaszozimek on 08/09/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchBlockDTOTimeCalculatorServiceTest {

    @Autowired
    private SchBlockDTOTimeCalculatorService schBlockDTOTimeCalculatorService;

    @Autowired
    private LibMediaItemThinMapper libMediaItemThinMapper;
    @Autowired
    private LibMediaItemRepository libMediaItemRepository;

    private LibMediaItem libMediaItem;

    private LibMediaItemThinDTO libMediaItemThinDTO;

    private LibMediaLibrary libMediaLibrary;

    private CorNetwork corNetwork;

    private CorChannel corChannel;

    @Before
    public void setUp() throws Exception {
        corNetwork = new CorNetwork().shortcut(CorNetworkResourceIntTest.TEST_NETWORK);
        corNetwork.setId(1L);
        corChannel = new CorChannel().shortcut("tes");
        corChannel.setId(1L);
        libMediaLibrary = new LibMediaLibrary();
        libMediaLibrary.setId(LIBRARY_ID);
        libMediaItem = new LibMediaItem().name(String.valueOf(new Random().nextLong())).library(libMediaLibrary).idx(String.valueOf(new Random().nextLong())).length(40.0).network(corNetwork);

        libMediaItem = libMediaItemRepository.saveAndFlush(libMediaItem);
        libMediaItemThinDTO = libMediaItemThinMapper.DB2DTO(libMediaItem);

    }


    @Test
    public void shouldCalculateTimeForBlock() {
        //when
        SchBlockDTO schBlockDTO = buildBlockDTOWithEmissionAndAttachmentsLenght(libMediaItemThinDTO);
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
        SchBlockDTO schBlockDTO = buildBlockDTOWithEmissionAndAttachmentsAndNestedBlock(1, libMediaItemThinDTO);
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