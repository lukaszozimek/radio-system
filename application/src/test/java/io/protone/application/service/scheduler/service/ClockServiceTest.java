package io.protone.application.service.scheduler.service;

import java.util.List;

import io.protone.application.ProtoneApp;
import io.protone.application.service.scheduler.base.SchedulerBaseTest;
import io.protone.library.domain.LibMediaItem;
import io.protone.scheduler.service.SchClockService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import javax.transaction.Transactional;

import static org.junit.Assert.assertNotNull;

/**
 * Created by lukaszozimek on 14/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class ClockServiceTest extends SchedulerBaseTest {
    @Inject
    private SchClockService schClockService;

    private List<LibMediaItem> libMediaItemList;

    @Before
    public void initialize() throws InterruptedException {
        initializeLibarary();
        this.libMediaItemList = generateFullItemListWithLenghtInRange(10.0, 40000.0, 50L);///TODO: change to long in near futher Change was made bacause audiovalut expception should be resolved when resolving audio lenght resolving service will be created
    }

    @Test
    public void dumyTest() {
        assertNotNull(libMediaItemList);
    }
}
