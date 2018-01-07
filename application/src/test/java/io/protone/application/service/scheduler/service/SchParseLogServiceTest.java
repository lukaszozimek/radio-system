package io.protone.application.service.scheduler.service;

import com.google.common.collect.Sets;
import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorOrganization;
import io.protone.library.domain.LibFileItem;
import io.protone.library.service.LibFileItemService;
import io.protone.scheduler.domain.SchEmission;
import io.protone.scheduler.domain.SchLog;
import io.protone.scheduler.domain.SchLogColumn;
import io.protone.scheduler.domain.SchLogConfiguration;
import io.protone.scheduler.domain.enumeration.LogColumnTypEnum;
import io.protone.scheduler.repository.SchLogConfigurationRepository;
import io.protone.scheduler.service.SchParseLogService;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static io.protone.application.util.TestConstans.*;
import static io.protone.scheduler.domain.enumeration.LogColumnTypEnum.*;
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
public class SchParseLogServiceTest {

    protected PodamFactory factory = new PodamFactoryImpl();

    @Autowired
    private SchParseLogService parseLogService;

    @Autowired
    private SchLogConfigurationRepository schLogConfigurationRepository;

    @Mock
    private LibFileItemService libFileItemService;

    private CorChannel corChannel;

    private CorOrganization corOrganization;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        corOrganization = new CorOrganization().shortcut(TEST_ORGANIZATION_SHORTCUT);
        corOrganization.setId(TEST_ORGANIZATION_ID);
        corChannel = new CorChannel().shortcut(TEST_CHANNEL_SHORTCUT);
        corChannel.setId(TEST_CHANNEL_ID);

        ReflectionTestUtils.setField(parseLogService, "libFileItemService", libFileItemService);
    }

    @Test
    public void shouldParseRekLogWithoutSeparator() throws IOException {
        //when
        InputStream file = Thread.currentThread().getContextClassLoader().getResourceAsStream("scheduler/shortlogs/withoutseparator/commercialLog/20170826.rek");
        when(libFileItemService.download(any())).thenReturn(IOUtils.toByteArray(file));
        SchLog schLog = new SchLog().fileItem(new LibFileItem()).schLogConfiguration(buildRekLogConfiguration(factory, schLogConfigurationRepository, corChannel)).channel(corChannel).date(LocalDate.parse("20160703", DateTimeFormatter.ofPattern("yyyyMMdd")));
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
        SchLog schLog = new SchLog().fileItem(new LibFileItem()).schLogConfiguration(buildRekLogConfigurationWithSeparator(factory, schLogConfigurationRepository,  corChannel)).channel(corChannel).date(LocalDate.parse("20160703", DateTimeFormatter.ofPattern("yyyyMMdd")));
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
        SchLog schLog = new SchLog().fileItem(new LibFileItem()).schLogConfiguration(buildMusLogConfiguration(factory, schLogConfigurationRepository, corChannel)).channel(corChannel).date(LocalDate.parse("20160703", DateTimeFormatter.ofPattern("yyyyMMdd")));
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
        SchLog schLog = new SchLog().fileItem(new LibFileItem()).schLogConfiguration(buildMusLogConfigurationWithSeparator(factory, schLogConfigurationRepository, corChannel)).channel(corChannel).date(LocalDate.parse("20160703", DateTimeFormatter.ofPattern("yyyyMMdd")));
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
        SchLog schLog = new SchLog().fileItem(new LibFileItem()).schLogConfiguration(buildOPRLogConfiguration(factory, schLogConfigurationRepository, corChannel)).channel(corChannel).date(LocalDate.parse("20170828", DateTimeFormatter.ofPattern("yyyyMMdd")));
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
        SchLog schLog = new SchLog().fileItem(new LibFileItem()).schLogConfiguration(buildOPRLogConfigurationWithSeparator(factory, schLogConfigurationRepository, corChannel)).channel(corChannel).date(LocalDate.parse("20170828", DateTimeFormatter.ofPattern("yyyyMMdd")));
        //then
        List<SchEmission> schEmissions = parseLogService.parseLog(schLog);

        assertNotNull(schEmissions);
        assertEquals(3, schEmissions.size());
    }

    @Test
    public void shouldParseOneLineRekLogWithoutSeparator() throws IOException {
        //when
        InputStream file = Thread.currentThread().getContextClassLoader().getResourceAsStream("scheduler/oneline/withoutseparator/commercialLog/20170826.rek");
        when(libFileItemService.download(any())).thenReturn(IOUtils.toByteArray(file));
        SchLog schLog = new SchLog().fileItem(new LibFileItem()).schLogConfiguration(buildRekLogConfiguration(factory, schLogConfigurationRepository, corChannel)).channel(corChannel).date(LocalDate.parse("20170826", DateTimeFormatter.ofPattern("yyyyMMdd")));
        //then
        List<SchEmission> schEmissions = parseLogService.parseLog(schLog);

        assertNotNull(schEmissions);
        assertEquals(1, schEmissions.size());
        assertNotNull(schEmissions.get(0).getMediaItem());
        assertNotNull(schEmissions.get(0));
        assertEquals(LocalDateTime.of(2017, 8, 26, 8, 40, 0), schEmissions.get(0).getStartTime());
        assertEquals("com", schEmissions.get(0).getLibraryElementShortCut());
        assertEquals("REKLAMA1", schEmissions.get(0).getMediaItem().getIdx());

    }

    @Test
    public void shouldParseOneLineRekLogWithSeparator() throws IOException {
        //when
        InputStream file = Thread.currentThread().getContextClassLoader().getResourceAsStream("scheduler/oneline/withseparator/commercialLog/20170826.rek");
        when(libFileItemService.download(any())).thenReturn(IOUtils.toByteArray(file));
        SchLog schLog = new SchLog().fileItem(new LibFileItem()).schLogConfiguration(buildRekLogConfigurationWithSeparator(factory, schLogConfigurationRepository, corChannel)).channel(corChannel).date(LocalDate.parse("20170826", DateTimeFormatter.ofPattern("yyyyMMdd")));
        //then
        List<SchEmission> schEmissions = parseLogService.parseLog(schLog);

        assertNotNull(schEmissions);
        assertEquals(1, schEmissions.size());
        assertNotNull(schEmissions.get(0).getMediaItem());
        assertNotNull(schEmissions.get(0));
        assertEquals(LocalDateTime.of(2017, 8, 26, 8, 40, 0), schEmissions.get(0).getStartTime());
        assertEquals("com", schEmissions.get(0).getLibraryElementShortCut());
        assertEquals("REKLAMA1", schEmissions.get(0).getMediaItem().getIdx());
    }

    @Test
    public void shouldParseOneLineMusLogWithoutSeparator() throws IOException {
        //when
        InputStream file = Thread.currentThread().getContextClassLoader().getResourceAsStream("scheduler/oneline/withoutseparator/musicLog/20160703.MUS");
        when(libFileItemService.download(any())).thenReturn(IOUtils.toByteArray(file));
        SchLog schLog = new SchLog().fileItem(new LibFileItem()).schLogConfiguration(buildMusLogConfiguration(factory, schLogConfigurationRepository, corChannel)).channel(corChannel).date(LocalDate.parse("20160703", DateTimeFormatter.ofPattern("yyyyMMdd")));
        //then
        List<SchEmission> schEmissions = parseLogService.parseLog(schLog);

        assertNotNull(schEmissions);
        assertEquals(1, schEmissions.size());
        assertNotNull(schEmissions.get(0).getMediaItem());
        assertNotNull(schEmissions.get(0));
        assertEquals(LocalDateTime.of(2016, 7, 3, 0, 0, 0), schEmissions.get(0).getStartTime());
        assertEquals("mus", schEmissions.get(0).getLibraryElementShortCut());
        assertEquals("MPI_003", schEmissions.get(0).getMediaItem().getIdx());
    }

    @Test
    public void shouldParseOneLineMusLogWithSeparator() throws IOException {
        //when
        InputStream file = Thread.currentThread().getContextClassLoader().getResourceAsStream("scheduler/oneline/withseparator/musicLog/20160703.MUS");
        when(libFileItemService.download(any())).thenReturn(IOUtils.toByteArray(file));
        SchLog schLog = new SchLog().fileItem(new LibFileItem()).schLogConfiguration(buildMusLogConfigurationWithSeparator(factory, schLogConfigurationRepository, corChannel)).channel(corChannel).date(LocalDate.parse("20160703", DateTimeFormatter.ofPattern("yyyyMMdd")));
        //then
        List<SchEmission> schEmissions = parseLogService.parseLog(schLog);

        assertNotNull(schEmissions);
        assertEquals(1, schEmissions.size());
        assertNotNull(schEmissions.get(0).getMediaItem());
        assertNotNull(schEmissions.get(0));
        assertEquals(LocalDateTime.of(2016, 7, 3, 0, 0, 0), schEmissions.get(0).getStartTime());
        assertEquals("mus", schEmissions.get(0).getLibraryElementShortCut());
        assertEquals("MPI_003", schEmissions.get(0).getMediaItem().getIdx());

    }

    @Test
    public void shouldParseOneLineOprLogWithoutSeparator() throws IOException {
        //when
        InputStream file = Thread.currentThread().getContextClassLoader().getResourceAsStream("scheduler/oneline/withoutseparator/newsCollect/20170828.OPR");
        when(libFileItemService.download(any())).thenReturn(IOUtils.toByteArray(file));
        SchLog schLog = new SchLog().fileItem(new LibFileItem()).schLogConfiguration(buildOPRLogConfiguration(factory, schLogConfigurationRepository, corChannel)).channel(corChannel).date(LocalDate.parse("20170828", DateTimeFormatter.ofPattern("yyyyMMdd")));
        //then
        List<SchEmission> schEmissions = parseLogService.parseLog(schLog);

        assertNotNull(schEmissions);
        assertEquals(1, schEmissions.size());
        assertNotNull(schEmissions.get(0).getMediaItem());
        assertNotNull(schEmissions.get(0));
        assertEquals(LocalDateTime.of(2017, 8, 28, 07, 33, 54), schEmissions.get(0).getStartTime());
        assertEquals("tes", schEmissions.get(0).getLibraryElementShortCut());
        assertEquals("DROGI", schEmissions.get(0).getMediaItem().getIdx());

    }

    @Test
    public void shouldParseOneLineOprLogWithSeparator() throws IOException {
        //when
        InputStream file = Thread.currentThread().getContextClassLoader().getResourceAsStream("scheduler/oneline/withseparator/newsCollect/20170828.OPR");
        when(libFileItemService.download(any())).thenReturn(IOUtils.toByteArray(file));
        SchLog schLog = new SchLog().fileItem(new LibFileItem()).schLogConfiguration(buildOPRLogConfigurationWithSeparator(factory, schLogConfigurationRepository, corChannel)).channel(corChannel).date(LocalDate.parse("20170828", DateTimeFormatter.ofPattern("yyyyMMdd")));
        //then
        List<SchEmission> schEmissions = parseLogService.parseLog(schLog);

        assertNotNull(schEmissions);
        assertEquals(1, schEmissions.size());

        assertNotNull(schEmissions.get(0).getMediaItem());
        assertNotNull(schEmissions.get(0));
        assertEquals(LocalDateTime.of(2017, 8, 28, 7, 33, 54), schEmissions.get(0).getStartTime());
        assertEquals("tes", schEmissions.get(0).getLibraryElementShortCut());
        assertEquals("DROGI", schEmissions.get(0).getMediaItem().getIdx());

    }

    public static SchLogConfiguration buildRekLogConfiguration(PodamFactory factory, SchLogConfigurationRepository schLogConfigurationRepository, CorChannel corChannel) {
        //configuration
        SchLogConfiguration schLogConfiguration = factory.manufacturePojo(SchLogConfiguration.class);
        schLogConfiguration.setExtension("rek");
        schLogConfiguration.setSpearator(null);
        schLogConfiguration.setPattern("yyyyMMdd");
        schLogConfiguration.setChannel(corChannel);
        schLogConfiguration = schLogConfigurationRepository.saveAndFlush(schLogConfiguration);
        //columnConfiguration
        SchLogColumn schLogColumnTime = buildLogColumn(LCT_START_TIME, 8, 0, schLogConfiguration, corChannel);
        SchLogColumn schLogColumnIdx = buildLogColumn(LCT_IDX, 14, 2, schLogConfiguration, corChannel);
        SchLogColumn schLogColumnLibrary = buildLogColumn(LCT_LIBRARY, 3, 1, schLogConfiguration, corChannel);
        SchLogColumn schLogColumnLenght = buildLogColumn(LCT_LENGHT, 5, 3, schLogConfiguration, corChannel);
        SchLogColumn schLogColumnName = buildLogColumn(LCT_NAME, 14, 4, schLogConfiguration, corChannel);
        schLogConfiguration.setLogColumns(Sets.newHashSet(schLogColumnTime, schLogColumnIdx, schLogColumnLenght, schLogColumnName, schLogColumnLibrary));
        return schLogConfiguration;
    }

    public static SchLogConfiguration buildMusLogConfiguration(PodamFactory factory, SchLogConfigurationRepository schLogConfigurationRepository, CorChannel corChannel) {
        //configuration
        SchLogConfiguration schLogConfiguration = factory.manufacturePojo(SchLogConfiguration.class);
        schLogConfiguration.setExtension("MUS");
        schLogConfiguration.setSpearator(null);
        schLogConfiguration.setPattern("yyyyMMdd");
        schLogConfiguration.setChannel(corChannel);
        schLogConfiguration = schLogConfigurationRepository.saveAndFlush(schLogConfiguration);
        //columnConfiguration
        SchLogColumn schLogColumnTime = buildLogColumn(LCT_START_TIME, 8, 0, schLogConfiguration, corChannel);
        SchLogColumn schLogColumnIdx = buildLogColumn(LCT_IDX, 16, 2, schLogConfiguration, corChannel);
        SchLogColumn schLogColumnLibrary = buildLogColumn(LCT_LIBRARY, 3, 1, schLogConfiguration, corChannel);
        SchLogColumn schLogColumnLenght = buildLogColumn(LCT_LENGHT, 5, 4, schLogConfiguration, corChannel);
        SchLogColumn schLogColumnName = buildLogColumn(LCT_NAME, 21, 3, schLogConfiguration, corChannel);
        schLogConfiguration.setLogColumns(Sets.newHashSet(schLogColumnTime, schLogColumnIdx, schLogColumnLenght, schLogColumnName, schLogColumnLibrary));
        return schLogConfiguration;
    }

    public static SchLogConfiguration buildOPRLogConfiguration(PodamFactory factory, SchLogConfigurationRepository schLogConfigurationRepository, CorChannel corChannel) {
        //configuration
        SchLogConfiguration schLogConfiguration = factory.manufacturePojo(SchLogConfiguration.class);
        schLogConfiguration.setExtension("opr");
        schLogConfiguration.setSpearator(null);
        schLogConfiguration.setPattern("yyyyMMdd");
        schLogConfiguration.setChannel(corChannel);
        schLogConfiguration = schLogConfigurationRepository.saveAndFlush(schLogConfiguration);
        //columnConfiguration
        SchLogColumn schLogColumnTime = buildLogColumn(LCT_START_TIME, 8, 0, schLogConfiguration, corChannel);
        SchLogColumn schLogColumnIdx = buildLogColumn(LCT_IDX, 9, 2, schLogConfiguration, corChannel);
        SchLogColumn schLogColumnLibrary = buildLogColumn(LCT_LIBRARY, 3, 1, schLogConfiguration, corChannel);
        SchLogColumn schLogColumnLenght = buildLogColumn(LCT_LENGHT, 5, 4, schLogConfiguration, corChannel);
        SchLogColumn schLogColumnName = buildLogColumn(LCT_NAME, 10, 3, schLogConfiguration, corChannel);
        schLogConfiguration.setLogColumns(Sets.newHashSet(schLogColumnTime, schLogColumnIdx, schLogColumnLenght, schLogColumnName, schLogColumnLibrary));
        return schLogConfiguration;
    }

    public static SchLogConfiguration buildRekLogConfigurationWithSeparator(PodamFactory factory, SchLogConfigurationRepository schLogConfigurationRepository, CorChannel corChannel) {
        //configuration
        SchLogConfiguration schLogConfiguration = factory.manufacturePojo(SchLogConfiguration.class);
        schLogConfiguration.setExtension("rek");
        schLogConfiguration.setPattern("yyyyMMdd");
        schLogConfiguration.setSpearator(";");
        schLogConfiguration.setChannel(corChannel);
        schLogConfiguration = schLogConfigurationRepository.saveAndFlush(schLogConfiguration);
        //columnConfiguration
        SchLogColumn schLogColumnTime = buildLogColumnWithoutLenght(LCT_START_TIME, 0, schLogConfiguration, corChannel);
        SchLogColumn schLogColumnIdx = buildLogColumnWithoutLenght(LCT_IDX, 2, schLogConfiguration, corChannel);
        SchLogColumn schLogColumnLibrary = buildLogColumnWithoutLenght(LCT_LIBRARY, 1, schLogConfiguration, corChannel);
        SchLogColumn schLogColumnLenght = buildLogColumnWithoutLenght(LCT_LENGHT, 3, schLogConfiguration, corChannel);
        SchLogColumn schLogColumnName = buildLogColumnWithoutLenght(LCT_NAME, 4, schLogConfiguration, corChannel);
        schLogConfiguration.setLogColumns(Sets.newHashSet(schLogColumnTime, schLogColumnIdx, schLogColumnLenght, schLogColumnName, schLogColumnLibrary));
        return schLogConfiguration;
    }

    public static SchLogConfiguration buildMusLogConfigurationWithSeparator(PodamFactory factory, SchLogConfigurationRepository schLogConfigurationRepository, CorChannel corChannel) {
        //configuration
        SchLogConfiguration schLogConfiguration = factory.manufacturePojo(SchLogConfiguration.class);
        schLogConfiguration.setExtension("MUS");
        schLogConfiguration.setPattern("yyyyMMdd");
        schLogConfiguration.setSpearator(";");
        schLogConfiguration.setChannel(corChannel);
        schLogConfiguration = schLogConfigurationRepository.saveAndFlush(schLogConfiguration);
        //columnConfiguration
        SchLogColumn schLogColumnTime = buildLogColumnWithoutLenght(LCT_START_TIME, 0, schLogConfiguration, corChannel);
        SchLogColumn schLogColumnIdx = buildLogColumnWithoutLenght(LCT_IDX, 2, schLogConfiguration, corChannel);
        SchLogColumn schLogColumnLibrary = buildLogColumnWithoutLenght(LCT_LIBRARY, 1, schLogConfiguration, corChannel);
        SchLogColumn schLogColumnLenght = buildLogColumnWithoutLenght(LCT_LENGHT, 6, schLogConfiguration, corChannel);
        SchLogColumn schLogColumnName = buildLogColumnWithoutLenght(LCT_NAME, 4, schLogConfiguration, corChannel);
        schLogConfiguration.setLogColumns(Sets.newHashSet(schLogColumnTime, schLogColumnIdx, schLogColumnLenght, schLogColumnName, schLogColumnLibrary));
        return schLogConfiguration;
    }

    public static SchLogConfiguration buildOPRLogConfigurationWithSeparator(PodamFactory factory, SchLogConfigurationRepository schLogConfigurationRepository, CorChannel corChannel) {
        //configuration
        SchLogConfiguration schLogConfiguration = factory.manufacturePojo(SchLogConfiguration.class);
        schLogConfiguration.setExtension("opr");
        schLogConfiguration.setPattern("yyyyMMdd");
        schLogConfiguration.setSpearator(";");
        schLogConfiguration.setChannel(corChannel);
        schLogConfiguration = schLogConfigurationRepository.saveAndFlush(schLogConfiguration);
        //columnConfiguration
        SchLogColumn schLogColumnTime = buildLogColumnWithoutLenght(LCT_START_TIME, 0, schLogConfiguration, corChannel);
        SchLogColumn schLogColumnIdx = buildLogColumnWithoutLenght(LCT_IDX, 2, schLogConfiguration, corChannel);
        SchLogColumn schLogColumnLibrary = buildLogColumnWithoutLenght(LCT_LIBRARY, 1, schLogConfiguration, corChannel);
        SchLogColumn schLogColumnLenght = buildLogColumnWithoutLenght(LCT_LENGHT, 6, schLogConfiguration, corChannel);
        SchLogColumn schLogColumnName = buildLogColumnWithoutLenght(LCT_NAME, 4, schLogConfiguration, corChannel);
        schLogConfiguration.setLogColumns(Sets.newHashSet(schLogColumnTime, schLogColumnIdx, schLogColumnLenght, schLogColumnName, schLogColumnLibrary));
        return schLogConfiguration;
    }

    public static SchLogColumn buildLogColumn(LogColumnTypEnum logColumnTypEnum, Integer lenght, Integer sequence, SchLogConfiguration schLogConfiguration, CorChannel corChannel) {
        SchLogColumn schLogColumn = new SchLogColumn();
        schLogColumn.setColumnSequence(sequence);
        schLogColumn.setName(logColumnTypEnum);
        schLogColumn.setLength(lenght);
        schLogColumn.setSchLogConfiguration(schLogConfiguration);
        schLogColumn.channel(corChannel);

        return schLogColumn;
    }

    public static SchLogColumn buildLogColumnWithoutLenght(LogColumnTypEnum logColumnTypEnum, Integer sequence, SchLogConfiguration schLogConfiguration, CorChannel corChannel) {
        SchLogColumn schLogColumn = new SchLogColumn();
        schLogColumn.setColumnSequence(sequence);
        schLogColumn.setName(logColumnTypEnum);
        schLogColumn.setSchLogConfiguration(schLogConfiguration);
        schLogColumn.channel(corChannel);
        return schLogColumn;
    }

}