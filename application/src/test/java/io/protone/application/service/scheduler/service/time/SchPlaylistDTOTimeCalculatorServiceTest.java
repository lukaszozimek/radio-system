package io.protone.application.service.scheduler.service.time;

import io.protone.application.ProtoneApp;
import io.protone.application.service.scheduler.base.SchedulerBaseTest;
import io.protone.scheduler.api.dto.SchPlaylistDTO;
import io.protone.scheduler.domain.SchPlaylist;
import io.protone.scheduler.service.time.SchPlaylistDTOTimeCalculatorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

/**
 * Created by lukaszozimek on 08/09/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchPlaylistDTOTimeCalculatorServiceTest extends SchedulerBaseTest {

    @Autowired
    private SchPlaylistDTOTimeCalculatorService schPlaylistDTOTimeCalculatorService;

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void shouldCalculateTimeForBlockNestedBlocks() {
        SchPlaylist schPlaylist = new SchPlaylist();

        SchPlaylistDTO resultDTO = schPlaylistDTOTimeCalculatorService.calculateTimeInSchPlaylistDTO(schPlaylist);

    }

}