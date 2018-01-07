package io.protone.application.service.scheduler.base;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorOrganization;
import io.protone.core.domain.enumeration.CorDayOfWeekEnum;
import io.protone.library.domain.LibMediaItem;
import io.protone.library.domain.LibMediaLibrary;
import io.protone.library.repository.LibMediaItemRepository;
import io.protone.scheduler.domain.*;
import io.protone.scheduler.domain.enumeration.AttachmentTypeEnum;
import io.protone.scheduler.domain.enumeration.EventTypeEnum;
import io.protone.scheduler.repository.*;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import static io.protone.application.service.scheduler.base.SchedulerBaseTest.LIBRARY_ID;
import static io.protone.application.service.scheduler.service.SchParseLogServiceTest.buildMusLogConfigurationWithSeparator;
import static io.protone.application.service.scheduler.service.SchParseLogServiceTest.buildOPRLogConfigurationWithSeparator;
import static io.protone.application.util.TestConstans.*;

/**
 * Created by lukaszozimek on 14/08/2017.
 */
public class SchedulerBuildSchedulerBaseTest {
    protected PodamFactory factory = new PodamFactoryImpl();

    protected Map<DayOfWeek, CorDayOfWeekEnum> corDayOfWeekEnumMap = new HashMap<>();

    @Autowired
    private SchEmissionTemplateRepository schEmissionTemplateRepository;

    @Autowired
    private SchClockTemplateRepository schClockTemplateRepository;

    @Autowired
    private SchAttachmentTemplateRepository schEventEmissionAttachmentRepository;

    @Autowired
    private SchEventTemplateRepository schEventRepository;

    @Autowired
    private SchEmissionTemplateRepository schEmissionRepository;

    @Autowired
    protected SchLogConfigurationRepository schLogConfigurationRepository;

    @Autowired
    private LibMediaItemRepository libMediaItemRepository;

    @Autowired
    private SchEventTemplateEvnetTemplateRepostiory schEventTemplateEvnetTemplateRepostiory;

    protected CorChannel corChannel;

    protected CorOrganization corOrganization;

    private LibMediaLibrary libMediaLibrary;

    private LibMediaItem libMediaItem;
    @Autowired
    private SchGridRepository schGridRepository;

    public void setUp() throws Exception {

        corOrganization = new CorOrganization().shortcut(TEST_ORGANIZATION_SHORTCUT);
        corOrganization.setId(TEST_ORGANIZATION_ID);
        corChannel = new CorChannel().shortcut(TEST_CHANNEL_SHORTCUT);
        corChannel.setId(TEST_CHANNEL_ID);
        corChannel.setOrganization(corOrganization);
        libMediaLibrary = new LibMediaLibrary();
        libMediaLibrary.setId(LIBRARY_ID);
        libMediaItem = new LibMediaItem().name(String.valueOf(new Random().nextLong())).library(libMediaLibrary).idx(String.valueOf(new Random().nextLong())).length(40.0).channel(corChannel);
        libMediaItem = libMediaItemRepository.saveAndFlush(libMediaItem);
        corDayOfWeekEnumMap = new HashMap<>();
        corDayOfWeekEnumMap.put(DayOfWeek.MONDAY, CorDayOfWeekEnum.DW_MONDAY);
        corDayOfWeekEnumMap.put(DayOfWeek.TUESDAY, CorDayOfWeekEnum.DW_TUESDAY);
        corDayOfWeekEnumMap.put(DayOfWeek.WEDNESDAY, CorDayOfWeekEnum.DW_WEDNESDAY);
        corDayOfWeekEnumMap.put(DayOfWeek.THURSDAY, CorDayOfWeekEnum.DW_THURSDAY);
        corDayOfWeekEnumMap.put(DayOfWeek.FRIDAY, CorDayOfWeekEnum.DW_FRIDAY);
        corDayOfWeekEnumMap.put(DayOfWeek.SATURDAY, CorDayOfWeekEnum.DW_SATURDAY);
        corDayOfWeekEnumMap.put(DayOfWeek.SUNDAY, CorDayOfWeekEnum.DW_SUNDAY);
    }

    protected SchGrid buildGridForDay(CorDayOfWeekEnum dayOfWeekEnum, boolean defaultGrid) {
        SchGrid schGrid = new SchGrid();
        schGrid.setDayOfWeek(dayOfWeekEnum);
        schGrid.setDefaultGrid(defaultGrid);
        schGrid.channel(corChannel);
        return schGrid;
    }

    protected SchGrid buildGridForDayWitClock(CorDayOfWeekEnum dayOfWeekEnum, boolean defaultGrid) {
        SchGrid schGrid = new SchGrid();
        schGrid.setDayOfWeek(dayOfWeekEnum);
        schGrid.setDefaultGrid(defaultGrid);
        schGrid.channel(corChannel);
        schGrid = schGridRepository.saveAndFlush(schGrid);
        schGrid.addEventTemplate(this.schEventTemplateEvnetTemplateRepostiory.saveAndFlush(new SchEventTemplateEvnetTemplate().parent(schGrid).sequence(0L).child(buildClockConfiguration())));
        return schGrid;
    }

    protected SchGrid buildGridForDayWitClockWithNestedEvents(CorDayOfWeekEnum dayOfWeekEnum, boolean defaultGrid) {
        SchGrid schGrid = new SchGrid();
        schGrid.setDayOfWeek(dayOfWeekEnum);
        schGrid.setDefaultGrid(defaultGrid);
        schGrid.channel(corChannel);
        schGridRepository.saveAndFlush(schGrid);
        schGrid.addEventTemplate(this.schEventTemplateEvnetTemplateRepostiory.saveAndFlush(new SchEventTemplateEvnetTemplate().parent(schGrid).sequence(0L).child(buildClockWithNestedEventsConfiguration())));

        return schGrid;
    }


    protected SchGrid buildGridForDayWitClockMusicAndImportEventsAndEmissionsConfiguration(CorDayOfWeekEnum dayOfWeekEnum, boolean defaultGrid) {
        SchGrid schGrid = new SchGrid();
        schGrid.setDayOfWeek(dayOfWeekEnum);
        schGrid.setDefaultGrid(defaultGrid);
        schGrid.channel(corChannel);
        schGridRepository.saveAndFlush(schGrid);
        schGrid.addEventTemplate(this.schEventTemplateEvnetTemplateRepostiory.saveAndFlush(new SchEventTemplateEvnetTemplate().parent(schGrid).sequence(0L).child(buildClockWithNestedMusicAndImportEventsAndEmissionsConfiguration())));

        return schGrid;
    }

    protected SchClockTemplate buildClockConfiguration() {
        SchClockTemplate schClockTemplate = new SchClockTemplate();
        schClockTemplate.setSequence(0L);
        schClockTemplate.length(3600000L);
        schClockTemplate.channel(corChannel);
        schClockTemplate.setEmissions(buildQuatreEmissionConfiguration(schClockTemplate, 4, 0));
        return schClockTemplateRepository.saveAndFlush(schClockTemplate);
    }

    protected SchClockTemplate buildClockWithNestedEventsConfiguration() {
        SchClockTemplate schClockTemplate = new SchClockTemplate();
        schClockTemplate.length(3600000L);
        schClockTemplate.setSequence(0L);
        schClockTemplate.channel(corChannel);
        schClockTemplate.emissions(buildQuatreEmissionConfiguration(schClockTemplate, 2, 3));
        schClockTemplate = schClockTemplateRepository.saveAndFlush(schClockTemplate);
        schClockTemplate.schEventTemplates(buildNestedSetEventsWithLenght30minoutes(schClockTemplate));
        return schClockTemplateRepository.saveAndFlush(schClockTemplate);
    }


    protected SchClockTemplate buildClockWithNestedMusicAndImportEventsAndEmissionsConfiguration() {
        SchClockTemplate schClockTemplate = new SchClockTemplate();
        schClockTemplate.setSequence(0L);
        schClockTemplate.length(7200000L);
        schClockTemplate.channel(corChannel);
        schClockTemplate.emissions(buildQuatreEmissionConfiguration(schClockTemplate, 2, 3));
        schClockTemplate = schClockTemplateRepository.saveAndFlush(schClockTemplate);
        schClockTemplate.schEventTemplates(buildNestedSetEventsWithLenght30minoutesWithMusicImport(schClockTemplate));
        return schClockTemplateRepository.saveAndFlush(schClockTemplate);
    }


    private List<SchEmissionTemplate> buildQuatreEmissionConfiguration(SchClockTemplate schClockTemplate, int quaterNumber, long startSequence) {
        List<SchEmissionTemplate> emissionConfigurations = Lists.newArrayList();
        for (int i = 0; i < quaterNumber; i++) {
            SchEmissionTemplate schEmissionTemplate = new SchEmissionTemplate();
            schEmissionTemplate.length(900000L);
            schEmissionTemplate.mediaItem(libMediaItem);
            schEmissionTemplate.sequence(startSequence + i);
            schEmissionTemplate.channel(corChannel);
            emissionConfigurations.add(schEmissionTemplate);
        }
        return emissionConfigurations;
    }


    protected List<SchEventTemplateEvnetTemplate> buildNestedSetEventsWithLenght30minoutes(SchClockTemplate schClockTemplate) {
        return Lists.newArrayList(new SchEventTemplateEvnetTemplate().parent(schClockTemplate).sequence(0L).child(buildEventWithEmissionAndAttachmentsLenght10minoutes()),
                new SchEventTemplateEvnetTemplate().parent(schClockTemplate).sequence(1L).child(buildEventWithEmissionAndAttachmentsLenght10minoutes()),
                new SchEventTemplateEvnetTemplate().parent(schClockTemplate).sequence(2L).child(buildEventWithEmissionAndAttachmentsLenght10minoutes())).stream().map(schEventTemplateEvnetTemplate -> schEventTemplateEvnetTemplateRepostiory.saveAndFlush(schEventTemplateEvnetTemplate)).collect(Collectors.toList());

    }

    protected List<SchEventTemplateEvnetTemplate> buildNestedSetEventsWithLenght30minoutesWithMusicImport(SchClockTemplate schClockTemplate) {
        return Lists.newArrayList(new SchEventTemplateEvnetTemplate().parent(schClockTemplate).sequence(0L).child(buildEventWithsLenght20minoutesMusicImport()),
                new SchEventTemplateEvnetTemplate().parent(schClockTemplate).sequence(1L).child(buildEventWithsEmissionsAndOprImport20minoutes()),
                new SchEventTemplateEvnetTemplate().parent(schClockTemplate).sequence(2L).child(buildEventWithEmissionAndAttachmentsLenght10minoutes())).stream().map(schEventTemplateEvnetTemplate -> schEventTemplateEvnetTemplateRepostiory.saveAndFlush(schEventTemplateEvnetTemplate)).collect(Collectors.toList());

    }

    protected SchEventTemplate buildEventWithsEmissionsAndOprImport20minoutes() {
        SchEventTemplate schEventTemplate = new SchEventTemplate()
                .eventType(EventTypeEnum.ET_MUSIC)
                .length(12000000L)
                .channel(corChannel);
        schEventRepository.saveAndFlush(schEventTemplate);
        schEventTemplate.schEventTemplates(Lists.newArrayList(buildOprImportEvent(0, 1000000L, schEventTemplate)))
                .emissions(Lists.newArrayList(
                        buildEmissionWithAttachment(1, 100000L, schEventTemplate),
                        buildEmissionWithAttachment(2, 100000L, schEventTemplate))
                );
        return schEventTemplate;
    }

    private SchEventTemplateEvnetTemplate buildOprImportEvent(long i, long l, SchEventTemplate parent) {
        SchEventTemplate schEventTemplate = new SchEventTemplate()
                .eventType(EventTypeEnum.ET_IMPORT_LOG)
                .schLogConfiguration(buildOPRLogConfigurationWithSeparator(factory, schLogConfigurationRepository, corChannel))
                .length(l)
                .channel(corChannel);
        schEventRepository.saveAndFlush(schEventTemplate);
        return schEventTemplateEvnetTemplateRepostiory.saveAndFlush(new SchEventTemplateEvnetTemplate().child(schEventTemplate).parent(parent).sequence(i));
    }


    protected SchEventTemplate buildEventWithsLenght20minoutesMusicImport() {
        SchEventTemplate schEventTemplate = new SchEventTemplate()
                .eventType(EventTypeEnum.ET_IMPORT_LOG)
                .length(1200000L)
                .schLogConfiguration(buildMusLogConfigurationWithSeparator(factory, schLogConfigurationRepository, corChannel))
                .channel(corChannel);

        return schEventRepository.saveAndFlush(schEventTemplate);
    }

    protected SchEventTemplate buildEventWithEmissionAndAttachmentsLenght10minoutes() {
        SchEventTemplate schEventTemplate = new SchEventTemplate()
                .eventType(EventTypeEnum.ET_MUSIC)
                .length(600000L)
                .channel(corChannel);


        schEventRepository.saveAndFlush(schEventTemplate);
        schEventTemplate.schEventTemplates(Lists.newArrayList(
                buildEventWithEmissionAndAttachmentsLenght(300000L, schEventTemplate, 0)))
                .emissions(Lists.newArrayList(
                        buildEmissionWithAttachment(1, 100000L, schEventTemplate),
                        buildEmissionWithAttachment(2, 100000L, schEventTemplate),
                        buildEmissionWithAttachment(3, 100000L, schEventTemplate))
                );
        return schEventTemplate;
    }

    protected SchEventTemplateEvnetTemplate buildEventWithEmissionAndAttachmentsLenght(long lenght, SchEventTemplate schEventTemplate, long sequence) {
        SchEventTemplate schEventTemplate1 = new SchEventTemplate()
                .eventType(EventTypeEnum.ET_MUSIC)
                .length(lenght)
                .sequence(sequence)
                .channel(corChannel);
        schEventRepository.saveAndFlush(schEventTemplate1);
        schEventTemplate1.emissions(Lists.newArrayList(
                buildEmissionWithAttachment(0, lenght / 3, schEventTemplate1),
                buildEmissionWithAttachment(1, lenght / 3, schEventTemplate1),
                buildEmissionWithAttachment(2, lenght / 3, schEventTemplate1))
        );
        return schEventTemplateEvnetTemplateRepostiory.saveAndFlush(new SchEventTemplateEvnetTemplate().sequence(sequence).parent(schEventTemplate).child(schEventTemplate1));
    }

    protected SchEmissionTemplate buildEmissionWithAttachment(long sequence, long lenght, SchEventTemplate schEventTemplate) {
        SchEmissionTemplate schEventEmission = new SchEmissionTemplate().mediaItem(libMediaItem)
                .sequence(sequence)
                .length(lenght)
                .channel(corChannel);

        schEventEmission = schEmissionRepository.saveAndFlush(schEventEmission);
        schEventEmission.attachments(Lists.newArrayList(buildAttachmentWithSequence(0, schEventEmission),
                buildAttachmentWithSequence(1, schEventEmission),
                buildAttachmentWithSequence(2, schEventEmission)));
        return schEventEmission;
    }

    protected SchAttachmentTemplate buildAttachmentWithSequence(long sequence, SchEmissionTemplate schEventEmission) {
        return new SchAttachmentTemplate().attachmentType(AttachmentTypeEnum.AT_OTHER)
                .emission(schEventEmission)
                .sequence(sequence)
                .mediaItem(libMediaItem)
                .channel(corChannel);
    }

}
