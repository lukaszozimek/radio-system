package io.protone.application.service.scheduler.base;

import io.protone.application.web.api.cor.CorNetworkResourceIntTest;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
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

import static io.protone.application.service.scheduler.base.SchedulerBaseTest.LIBRARY_ID;
import static io.protone.application.service.scheduler.service.SchParseLogServiceTest.buildMusLogConfigurationWithSeparator;
import static io.protone.application.service.scheduler.service.SchParseLogServiceTest.buildOPRLogConfigurationWithSeparator;

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

    protected CorNetwork corNetwork;

    protected CorChannel corChannel;

    private LibMediaLibrary libMediaLibrary;

    private LibMediaItem libMediaItem;

    public void setUp() throws Exception {
        corNetwork = new CorNetwork().shortcut(CorNetworkResourceIntTest.TEST_NETWORK);
        corNetwork.setId(1L);
        corChannel = new CorChannel().shortcut("tes");
        corChannel.setId(1L);
        libMediaLibrary = new LibMediaLibrary();
        libMediaLibrary.setId(LIBRARY_ID);
        libMediaItem = new LibMediaItem().name(String.valueOf(new Random().nextLong())).library(libMediaLibrary).idx(String.valueOf(new Random().nextLong())).length(40.0).network(corNetwork);
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
        schGrid.network(corNetwork);
        return schGrid;
    }

    protected SchGrid buildGridForDayWitClock(CorDayOfWeekEnum dayOfWeekEnum, boolean defaultGrid) {
        SchGrid schGrid = new SchGrid();
        schGrid.setDayOfWeek(dayOfWeekEnum);
        schGrid.setDefaultGrid(defaultGrid);
        schGrid.addClock(new SchGridClockTemplate().sequence(1L).schClockConfiguration(buildClockConfiguration()));
        schGrid.channel(corChannel);
        schGrid.network(corNetwork);
        return schGrid;
    }

    protected SchGrid buildGridForDayWitClockWithNestedEvents(CorDayOfWeekEnum dayOfWeekEnum, boolean defaultGrid) {
        SchGrid schGrid = new SchGrid();
        schGrid.setDayOfWeek(dayOfWeekEnum);
        schGrid.setDefaultGrid(defaultGrid);
        schGrid.addClock(new SchGridClockTemplate().sequence(1L).schClockConfiguration(buildClockWithNestedEventsConfiguration()));
        schGrid.channel(corChannel);
        schGrid.network(corNetwork);
        return schGrid;
    }


    protected SchGrid buildGridForDayWitClockMusicAndImportEventsAndEmissionsConfiguration(CorDayOfWeekEnum dayOfWeekEnum, boolean defaultGrid) {
        SchGrid schGrid = new SchGrid();
        schGrid.setDayOfWeek(dayOfWeekEnum);
        schGrid.setDefaultGrid(defaultGrid);
        schGrid.addClock(new SchGridClockTemplate().sequence(1L).schClockConfiguration(buildClockWithNestedMusicAndImportEventsAndEmissionsConfiguration()));
        schGrid.channel(corChannel);
        schGrid.network(corNetwork);
        return schGrid;
    }

    protected SchClockTemplate buildClockConfiguration() {
        SchClockTemplate schClockTemplate = new SchClockTemplate();
        schClockTemplate.setSequence(1L);
        schClockTemplate.length(3600000L);
        schClockTemplate.channel(corChannel);
        schClockTemplate.network(corNetwork);
        schClockTemplate = schClockTemplateRepository.saveAndFlush(schClockTemplate);
        schClockTemplate.emissions(buildQuatreEmissionConfiguration(schClockTemplate, 4, 1));
        schClockTemplateRepository.saveAndFlush(schClockTemplate);
        return schClockTemplate;
    }

    protected SchClockTemplate buildClockWithNestedEventsConfiguration() {
        SchClockTemplate schClockTemplate = new SchClockTemplate();
        schClockTemplate.length(3600000L);
        schClockTemplate.setSequence(1L);
        schClockTemplate.channel(corChannel);
        schClockTemplate.network(corNetwork);
        schClockTemplate = schClockTemplateRepository.saveAndFlush(schClockTemplate);
        schClockTemplate.emissions(buildQuatreEmissionConfiguration(schClockTemplate, 2, 4));
        schClockTemplate.schEvents(buildNestedSetEventsWithLenght30minoutes(schClockTemplate));
        return schClockTemplateRepository.saveAndFlush(schClockTemplate);
    }


    protected SchClockTemplate buildClockWithNestedMusicAndImportEventsAndEmissionsConfiguration() {
        SchClockTemplate schClockTemplate = new SchClockTemplate();
        schClockTemplate.setSequence(1L);
        schClockTemplate.length(7200000L);
        schClockTemplate.channel(corChannel);
        schClockTemplate.network(corNetwork);
        schClockTemplate = schClockTemplateRepository.saveAndFlush(schClockTemplate);
        schClockTemplate.emissions(buildQuatreEmissionConfiguration(schClockTemplate, 2, 4));
        schClockTemplate.schEvents(buildNestedSetEventsWithLenght30minoutesWithMusicImport(schClockTemplate));
        schClockTemplate = schClockTemplateRepository.saveAndFlush(schClockTemplate);

        return schClockTemplate;
    }


    private List<SchEmissionTemplate> buildQuatreEmissionConfiguration(SchClockTemplate schClockTemplate, int quaterNumber, long startSequence) {
        List<SchEmissionTemplate> emissionConfigurations = Lists.newArrayList();
        for (int i = 0; i < quaterNumber; i++) {
            SchEmissionTemplate schEmissionTemplate = new SchEmissionTemplate();
            schEmissionTemplate.length(900000L);
            schEmissionTemplate.mediaItem(libMediaItem);
            schEmissionTemplate.seq(startSequence + i);
            schEmissionTemplate.channel(corChannel);
            schEmissionTemplate.network(corNetwork);
            emissionConfigurations.add(schEmissionTemplateRepository.saveAndFlush(schEmissionTemplate));
        }
        return emissionConfigurations;
    }

    protected List<SchEventTemplate> buildNestedSetEventsWithLenght30minoutes(SchClockTemplate schClockTemplate) {
        return Lists.newArrayList(buildEventWithEmissionAndAttachmentsLenght10minoutes(schClockTemplate, 1),
                buildEventWithEmissionAndAttachmentsLenght10minoutes(schClockTemplate, 2),
                buildEventWithEmissionAndAttachmentsLenght10minoutes(schClockTemplate, 3));

    }

    protected List<SchEventTemplate> buildNestedSetEventsWithLenght30minoutesWithMusicImport(SchClockTemplate schClockTemplate) {
        return Lists.newArrayList(buildEventWithsLenght20minoutesMusicImport(schClockTemplate, 1),
                buildEventWithsEmissionsAndOprImport20minoutes(schClockTemplate, 2),
                buildEventWithEmissionAndAttachmentsLenght10minoutes(schClockTemplate, 3));

    }

    protected SchEventTemplate buildEventWithsEmissionsAndOprImport20minoutes(SchClockTemplate schClockTemplate, long sequence) {
        SchEventTemplate schEventTemplate = new SchEventTemplate()
                .eventType(EventTypeEnum.ET_MUSIC)
                .sequence(sequence)
                .length(12000000L)
                .channel(corChannel)
                .network(corNetwork);


        schEventRepository.save(schEventTemplate);
        schEventTemplate.schEventTemplates(Lists.newArrayList(buildOprImportEvent(1, 1000000L)))
                .emissions(Lists.newArrayList(
                        buildEmissionWithAttachment(2, 100000L, schEventTemplate),
                        buildEmissionWithAttachment(3, 100000L, schEventTemplate))
                );
        return schEventTemplate;
    }

    private SchEventTemplate buildOprImportEvent(long i, long l) {
        SchEventTemplate schEventTemplate = new SchEventTemplate()
                .sequence(i)
                .eventType(EventTypeEnum.ET_IMPORT_LOG)
                .schLogConfiguration(buildOPRLogConfigurationWithSeparator(factory, schLogConfigurationRepository, corNetwork, corChannel))
                .length(l)
                .schLogConfiguration(buildOPRLogConfigurationWithSeparator(factory, schLogConfigurationRepository, corNetwork, corChannel))
                .channel(corChannel)
                .network(corNetwork);
        schEventRepository.save(schEventTemplate);
        return schEventTemplate;
    }


    protected SchEventTemplate buildEventWithsLenght20minoutesMusicImport(SchClockTemplate schClockTemplate, long sequence) {
        SchEventTemplate schEventTemplate = new SchEventTemplate()
                .eventType(EventTypeEnum.ET_IMPORT_LOG)
                .sequence(sequence)
                .length(1200000L)
                .schLogConfiguration(buildMusLogConfigurationWithSeparator(factory, schLogConfigurationRepository, corNetwork, corChannel))
                .channel(corChannel)
                .network(corNetwork);
        schEventRepository.save(schEventTemplate);
        return schEventTemplate;
    }

    protected SchEventTemplate buildEventWithEmissionAndAttachmentsLenght10minoutes(SchClockTemplate schClockTemplate, long sequence) {
        SchEventTemplate schEventTemplate = new SchEventTemplate()
                .sequence(sequence)
                .eventType(EventTypeEnum.ET_MUSIC)
                .length(600000L)
                .channel(corChannel)
                .network(corNetwork);


        schEventRepository.save(schEventTemplate);
        schEventTemplate.schEventTemplates(Lists.newArrayList(
                buildEventWithEmissionAndAttachmentsLenght(300000L, schEventTemplate, 1)))
                .emissions(Lists.newArrayList(
                        buildEmissionWithAttachment(2, 100000L, schEventTemplate),
                        buildEmissionWithAttachment(3, 100000L, schEventTemplate),
                        buildEmissionWithAttachment(4, 100000L, schEventTemplate))
                );
        return schEventTemplate;
    }

    protected SchEventTemplate buildEventWithEmissionAndAttachmentsLenght(long lenght, SchEventTemplate schEventTemplate, long sequence) {
        SchEventTemplate schEventTemplate1 = new SchEventTemplate()
                .eventType(EventTypeEnum.ET_MUSIC)
                .length(lenght)
                .sequence(sequence)
                .channel(corChannel)
                .network(corNetwork);
        schEventRepository.save(schEventTemplate1);
        schEventTemplate1.emissions(Lists.newArrayList(
                buildEmissionWithAttachment(1, lenght / 3, schEventTemplate1),
                buildEmissionWithAttachment(2, lenght / 3, schEventTemplate1),
                buildEmissionWithAttachment(3, lenght / 3, schEventTemplate1))
        );
        return schEventTemplate1;
    }

    protected SchEmissionTemplate buildEmissionWithAttachment(long sequence, long lenght, SchEventTemplate schEventTemplate) {
        SchEmissionTemplate schEventEmission = new SchEmissionTemplate().mediaItem(libMediaItem)
                .seq(sequence)
                .length(lenght)
                .channel(corChannel)
                .network(corNetwork);

        schEventEmission = schEmissionRepository.saveAndFlush(schEventEmission);
        schEventEmission.attachments(Lists.newArrayList(buildAttachmentWithSequence(1, schEventEmission),
                buildAttachmentWithSequence(2, schEventEmission),
                buildAttachmentWithSequence(3, schEventEmission)));
        return schEventEmission;
    }

    protected SchAttachmentTemplate buildAttachmentWithSequence(long sequence, SchEmissionTemplate schEventEmission) {
        return schEventEmissionAttachmentRepository.saveAndFlush(new SchAttachmentTemplate().attachmentType(AttachmentTypeEnum.AT_OTHER)
                .emission(schEventEmission)
                .sequence(sequence)
                .mediaItem(libMediaItem)
                .channel(corChannel)
                .network(corNetwork));
    }

}
