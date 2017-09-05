package io.protone.application.service.scheduler.service;

import io.protone.application.ProtoneApp;
import io.protone.application.service.scheduler.base.SchedulerBaseTest;
import io.protone.library.domain.LibFileItem;
import io.protone.library.service.LibFileItemService;
import io.protone.scheduler.domain.SchEmission;
import io.protone.scheduler.domain.SchLog;
import io.protone.scheduler.service.SchParseLogService;
import org.apache.commons.compress.utils.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by lukaszozimek on 30/08/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class SchParseLogServiceTest extends SchedulerBaseTest {

    @Autowired
    private SchParseLogService parseLogService;

    @Mock
    private LibFileItemService libFileItemService;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(parseLogService, "libFileItemService", libFileItemService);
        super.setUp();
    }

    @Test
    public void shouldParseRekLogWithoutSeparator() throws IOException {
        //when
        InputStream file = Thread.currentThread().getContextClassLoader().getResourceAsStream("scheduler/shortlogs/withoutseparator/commercialLog/20170826.rek");
        when(libFileItemService.download(any())).thenReturn(IOUtils.toByteArray(file));
        SchLog schLog = new SchLog().fileItem(new LibFileItem()).schLogConfiguration(buildRekLogConfiguration()).network(corNetwork).channel(corChannel).date(LocalDate.parse("20160703", DateTimeFormatter.ofPattern("yyyyMMdd")));
        //then
        List<SchEmission> schEmissions = parseLogService.parseLog(schLog);

        assertNotNull(schEmissions);
        assertEquals(3, schEmissions.size());
    }

    @Test
    public void shouldParseRekLogWithSeparator() throws IOException {
        //when
        InputStream file = Thread.currentThread().getContextClassLoader().getResourceAsStream("scheduler/shortlogs/withseparator/commercialLog/20170826.rek");
        when(libFileItemService.download(any())).thenReturn(IOUtils.toByteArray(file));
        SchLog schLog = new SchLog().fileItem(new LibFileItem()).schLogConfiguration(buildRekLogConfiguration()).network(corNetwork).channel(corChannel).date(LocalDate.parse("20160703", DateTimeFormatter.ofPattern("yyyyMMdd")));
        //then
        List<SchEmission> schEmissions = parseLogService.parseLog(schLog);

        assertNotNull(schEmissions);
        assertEquals(3, schEmissions.size());
    }

    @Test
    public void shouldParseMusLogWithoutSeparator() throws IOException {
        //when
        InputStream file = Thread.currentThread().getContextClassLoader().getResourceAsStream("scheduler/shortlogs/withoutseparator/musicLog/20160703.MUS");
        when(libFileItemService.download(any())).thenReturn(IOUtils.toByteArray(file));
        SchLog schLog = new SchLog().fileItem(new LibFileItem()).schLogConfiguration(buildMusLogConfiguration()).network(corNetwork).channel(corChannel).date(LocalDate.parse("20160703", DateTimeFormatter.ofPattern("yyyyMMdd")));
        //then
        List<SchEmission> schEmissions = parseLogService.parseLog(schLog);

        assertNotNull(schEmissions);
        assertEquals(3, schEmissions.size());
    }

    @Test
    public void shouldParseMusLogWithSeparator() throws IOException {
        //when
        InputStream file = Thread.currentThread().getContextClassLoader().getResourceAsStream("scheduler/shortlogs/withseparator/musicLog/20160703.MUS");
        when(libFileItemService.download(any())).thenReturn(IOUtils.toByteArray(file));
        SchLog schLog = new SchLog().fileItem(new LibFileItem()).schLogConfiguration(buildMusLogConfiguration()).network(corNetwork).channel(corChannel).date(LocalDate.parse("20160703", DateTimeFormatter.ofPattern("yyyyMMdd")));
        //then
        List<SchEmission> schEmissions = parseLogService.parseLog(schLog);

        assertNotNull(schEmissions);
        assertEquals(3, schEmissions.size());
    }

    @Test
    public void shouldParseOprLogWithoutSeparator() throws IOException {
        //when
        InputStream file = Thread.currentThread().getContextClassLoader().getResourceAsStream("scheduler/shortlogs/withoutseparator/newsCollect/20170828.OPR");
        when(libFileItemService.download(any())).thenReturn(IOUtils.toByteArray(file));
        SchLog schLog = new SchLog().fileItem(new LibFileItem()).schLogConfiguration(buildOPRLogConfiguration()).network(corNetwork).channel(corChannel).date(LocalDate.parse("20170828", DateTimeFormatter.ofPattern("yyyyMMdd")));
        //then
        List<SchEmission> schEmissions = parseLogService.parseLog(schLog);

        assertNotNull(schEmissions);
        assertEquals(3, schEmissions.size());
    }

    @Test
    public void shouldParseOprLogWithSeparator() throws IOException {
        //when
        InputStream file = Thread.currentThread().getContextClassLoader().getResourceAsStream("scheduler/shortlogs/withseparator/newsCollect/20170828.OPR");
        when(libFileItemService.download(any())).thenReturn(IOUtils.toByteArray(file));
        SchLog schLog = new SchLog().fileItem(new LibFileItem()).schLogConfiguration(buildMusLogConfiguration()).network(corNetwork).channel(corChannel).date(LocalDate.parse("20170828", DateTimeFormatter.ofPattern("yyyyMMdd")));
        //then
        List<SchEmission> schEmissions = parseLogService.parseLog(schLog);

        assertNotNull(schEmissions);
        assertEquals(3, schEmissions.size());
    }

}