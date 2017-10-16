package io.protone.application.service.scheduler.base;

import com.google.common.collect.Sets;
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
import org.springframework.beans.factory.annotation.Autowired;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.time.DayOfWeek;
import java.util.*;

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
    private SchEmissionConfigurationRepository schEmissionConfigurationRepository;

    @Autowired
    private SchClockConfigurationRepository schClockConfigurationRepository;

    @Autowired
    private SchEventEmissionAttachmentRepository schEventEmissionAttachmentRepository;

    @Autowired
    private SchEventRepository schEventRepository;

    @Autowired
    private SchEventEmissionRepository schEmissionRepository;

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
        schGrid.addClock(new SchGridClockConfiguration().sequence(1L).schClockConfiguration(buildClockConfiguration()));
        schGrid.channel(corChannel);
        schGrid.network(corNetwork);
        return schGrid;
    }

    protected SchGrid buildGridForDayWitClockWithNestedEvents(CorDayOfWeekEnum dayOfWeekEnum, boolean defaultGrid) {
        SchGrid schGrid = new SchGrid();
        schGrid.setDayOfWeek(dayOfWeekEnum);
        schGrid.setDefaultGrid(defaultGrid);
        schGrid.addClock(new SchGridClockConfiguration().sequence(1L).schClockConfiguration(buildClockWithNestedEventsConfiguration()));
        schGrid.channel(corChannel);
        schGrid.network(corNetwork);
        return schGrid;
    }


    protected SchGrid buildGridForDayWitClockMusicAndImportEventsAndEmissionsConfiguration(CorDayOfWeekEnum dayOfWeekEnum, boolean defaultGrid) {
        SchGrid schGrid = new SchGrid();
        schGrid.setDayOfWeek(dayOfWeekEnum);
        schGrid.setDefaultGrid(defaultGrid);
        schGrid.addClock(new SchGridClockConfiguration().sequence(1L).schClockConfiguration(buildClockWithNestedMusicAndImportEventsAndEmissionsConfiguration()));
        schGrid.channel(corChannel);
        schGrid.network(corNetwork);
        return schGrid;
    }

    protected SchClockConfiguration buildClockConfiguration() {
        SchClockConfiguration schClockConfiguration = new SchClockConfiguration();
        schClockConfiguration.setSequence(1L);
        schClockConfiguration.length(3600000L);
        schClockConfiguration.channel(corChannel);
        schClockConfiguration.network(corNetwork);
        schClockConfiguration = schClockConfigurationRepository.saveAndFlush(schClockConfiguration);
        schClockConfiguration.emissions(buildQuatreEmissionConfiguration(schClockConfiguration, 4, 1));
        schClockConfigurationRepository.saveAndFlush(schClockConfiguration);
        return schClockConfiguration;
    }

    protected SchClockConfiguration buildClockWithNestedEventsConfiguration() {
        SchClockConfiguration schClockConfiguration = new SchClockConfiguration();
        schClockConfiguration.length(3600000L);
        schClockConfiguration.setSequence(1L);
        schClockConfiguration.channel(corChannel);
        schClockConfiguration.network(corNetwork);
        schClockConfiguration = schClockConfigurationRepository.saveAndFlush(schClockConfiguration);
        schClockConfiguration.emissions(buildQuatreEmissionConfiguration(schClockConfiguration, 2, 4));
        schClockConfiguration.schEvents(buildNestedSetEventsWithLenght30minoutes(schClockConfiguration));
        return schClockConfigurationRepository.saveAndFlush(schClockConfiguration);
    }


    protected SchClockConfiguration buildClockWithNestedMusicAndImportEventsAndEmissionsConfiguration() {
        SchClockConfiguration schClockConfiguration = new SchClockConfiguration();
        schClockConfiguration.setSequence(1L);
        schClockConfiguration.length(7200000L);
        schClockConfiguration.channel(corChannel);
        schClockConfiguration.network(corNetwork);
        schClockConfiguration = schClockConfigurationRepository.saveAndFlush(schClockConfiguration);
        schClockConfiguration.emissions(buildQuatreEmissionConfiguration(schClockConfiguration, 2, 4));
        schClockConfiguration.schEvents(buildNestedSetEventsWithLenght30minoutesWithMusicImport(schClockConfiguration));
        schClockConfiguration = schClockConfigurationRepository.saveAndFlush(schClockConfiguration);

        return schClockConfiguration;
    }


    private Set<SchEmissionConfiguration> buildQuatreEmissionConfiguration(SchClockConfiguration schClockConfiguration, int quaterNumber, long startSequence) {
        Set<SchEmissionConfiguration> emissionConfigurations = new HashSet<>();
        for (int i = 0; i < quaterNumber; i++) {
            SchEmissionConfiguration schEmissionConfiguration = new SchEmissionConfiguration();
            schEmissionConfiguration.length(900000L);
            schEmissionConfiguration.mediaItem(libMediaItem);
            schEmissionConfiguration.seq(startSequence + i);
            schEmissionConfiguration.setClock(schClockConfiguration);
            schEmissionConfiguration.channel(corChannel);
            schEmissionConfiguration.network(corNetwork);
            emissionConfigurations.add(schEmissionConfigurationRepository.saveAndFlush(schEmissionConfiguration));
        }
        return emissionConfigurations;
    }

    protected Set<SchEvent> buildNestedSetEventsWithLenght30minoutes(SchClockConfiguration schClockConfiguration) {
        return Sets.newHashSet(buildEventWithEmissionAndAttachmentsLenght10minoutes(schClockConfiguration, 1),
                buildEventWithEmissionAndAttachmentsLenght10minoutes(schClockConfiguration, 2),
                buildEventWithEmissionAndAttachmentsLenght10minoutes(schClockConfiguration, 3));

    }

    protected Set<SchEvent> buildNestedSetEventsWithLenght30minoutesWithMusicImport(SchClockConfiguration schClockConfiguration) {
        return Sets.newHashSet(buildEventWithsLenght20minoutesMusicImport(schClockConfiguration, 1),
                buildEventWithsEmissionsAndOprImport20minoutes(schClockConfiguration, 2),
                buildEventWithEmissionAndAttachmentsLenght10minoutes(schClockConfiguration, 3));

    }

    protected SchEvent buildEventWithsEmissionsAndOprImport20minoutes(SchClockConfiguration schClockConfiguration, long sequence) {
        SchEvent schEvent = new SchEvent()
                .eventType(EventTypeEnum.ET_MUSIC)
                .sequence(sequence)
                .length(12000000L)
                .channel(corChannel)
                .network(corNetwork);


        schEventRepository.save(schEvent);
        schEvent.schEvents(Sets.newHashSet(buildOprImportEvent(1, 1000000L)))
                .emissions(Sets.newHashSet(
                        buildEmissionWithAttachment(2, 100000L, schEvent),
                        buildEmissionWithAttachment(3, 100000L, schEvent))
                );
        return schEvent;
    }

    private SchEvent buildOprImportEvent(long i, long l) {
        SchEvent schEvent = new SchEvent()
                .sequence(i)
                .eventType(EventTypeEnum.ET_IMPORT_LOG)
                .schLogConfiguration(buildOPRLogConfigurationWithSeparator(factory, schLogConfigurationRepository, corNetwork, corChannel))
                .length(l)
                .schLogConfiguration(buildOPRLogConfigurationWithSeparator(factory, schLogConfigurationRepository, corNetwork, corChannel))
                .channel(corChannel)
                .network(corNetwork);
        schEventRepository.save(schEvent);
        return schEvent;
    }


    protected SchEvent buildEventWithsLenght20minoutesMusicImport(SchClockConfiguration schClockConfiguration, long sequence) {
        SchEvent schEvent = new SchEvent()
                .eventType(EventTypeEnum.ET_IMPORT_LOG)
                .sequence(sequence)
                .length(1200000L)
                .schLogConfiguration(buildMusLogConfigurationWithSeparator(factory, schLogConfigurationRepository, corNetwork, corChannel))
                .channel(corChannel)
                .network(corNetwork);
        schEventRepository.save(schEvent);
        return schEvent;
    }

    protected SchEvent buildEventWithEmissionAndAttachmentsLenght10minoutes(SchClockConfiguration schClockConfiguration, long sequence) {
        SchEvent schEvent = new SchEvent()
                .sequence(sequence)
                .eventType(EventTypeEnum.ET_MUSIC)
                .length(600000L)
                .channel(corChannel)
                .network(corNetwork);


        schEventRepository.save(schEvent);
        schEvent.schEvents(Sets.newHashSet(
                buildEventWithEmissionAndAttachmentsLenght(300000L, schEvent, 1)))
                .emissions(Sets.newHashSet(
                        buildEmissionWithAttachment(2, 100000L, schEvent),
                        buildEmissionWithAttachment(3, 100000L, schEvent),
                        buildEmissionWithAttachment(4, 100000L, schEvent))
                );
        return schEvent;
    }

    protected SchEvent buildEventWithEmissionAndAttachmentsLenght(long lenght, SchEvent schEvent, long sequence) {
        SchEvent schEvent1 = new SchEvent()
                .eventType(EventTypeEnum.ET_MUSIC)
                .length(lenght)
                .sequence(sequence)
                .channel(corChannel)
                .network(corNetwork);
        schEventRepository.save(schEvent1);
        schEvent1.emissions(Sets.newHashSet(
                buildEmissionWithAttachment(1, lenght / 3, schEvent1),
                buildEmissionWithAttachment(2, lenght / 3, schEvent1),
                buildEmissionWithAttachment(3, lenght / 3, schEvent1))
        );
        return schEvent1;
    }

    protected SchEventEmission buildEmissionWithAttachment(long sequence, long lenght, SchEvent schEvent) {
        SchEventEmission schEventEmission = new SchEventEmission().mediaItem(libMediaItem)
                .seq(sequence)
                .event(schEvent)
                .length(lenght)
                .channel(corChannel)
                .network(corNetwork);

        schEventEmission = schEmissionRepository.saveAndFlush(schEventEmission);
        schEventEmission.attachments(Sets.newHashSet(buildAttachmentWithSequence(1, schEventEmission),
                buildAttachmentWithSequence(2, schEventEmission),
                buildAttachmentWithSequence(3, schEventEmission)));
        return schEventEmission;
    }

    protected SchEventEmissionAttachment buildAttachmentWithSequence(long sequence, SchEventEmission schEventEmission) {
        return schEventEmissionAttachmentRepository.saveAndFlush(new SchEventEmissionAttachment().attachmentType(AttachmentTypeEnum.AT_OTHER)
                .emission(schEventEmission)
                .sequence(sequence)
                .mediaItem(libMediaItem)
                .channel(corChannel)
                .network(corNetwork));
    }

}
