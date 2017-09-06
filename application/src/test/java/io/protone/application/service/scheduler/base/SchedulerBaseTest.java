package io.protone.application.service.scheduler.base;

import com.google.common.collect.Sets;
import io.protone.application.service.library.util.LibraryGenerator;
import io.protone.library.domain.LibMediaItem;
import io.protone.scheduler.domain.*;
import io.protone.scheduler.domain.enumeration.AttachmentTypeEnum;
import io.protone.scheduler.domain.enumeration.EventTypeEnum;
import io.protone.scheduler.domain.enumeration.LogColumnTypEnum;
import io.protone.scheduler.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

import static io.protone.scheduler.domain.enumeration.LogColumnTypEnum.*;

/**
 * Created by lukaszozimek on 14/08/2017.
 */
public class SchedulerBaseTest extends LibraryGenerator {
    protected List<LibMediaItem> libMediaItemList;
    @Autowired
    private SchLogColumnRepostiory schLogColumnRepostiory;
    @Autowired
    private SchLogConfigurationRepository schLogConfigurationRepository;
    @Autowired
    private SchEventEmissionRepository schEmissionRepository;
    @Autowired
    private SchEventEmissionAttachmentRepository schEventEmissionAttachmentRepository;
    @Autowired
    private SchEventRepository schEventRepository;


    public void setUp() throws Exception {
        initializeLibarary();
        libMediaItemList = generateFullItemListWithLenghtInRange(30000, 500000, 10L);
    }

    protected SchEmission buildEmissionForBlock() {
        return new SchEmission().mediaItem(libMediaItemList.get(0)).channel(corChannel).network(corNetwork);
    }

    protected SchEmission buildEmissionForWithAttachment() {
        return new SchEmission().mediaItem(libMediaItemList.get(0)).channel(corChannel).network(corNetwork).attachments(Sets.newHashSet(new SchAttachment().channel(corChannel).network(corNetwork).mediaItem(libMediaItemList.get(2))));
    }

    protected SchEmissionConfiguration buildEmissionConfigurationForWithAttachment() {
        return new SchEmissionConfiguration().mediaItem(libMediaItemList.get(0)).channel(corChannel).network(corNetwork).attachments(Sets.newHashSet(new SchAttachmentConfiguration().channel(corChannel).network(corNetwork).mediaItem(libMediaItemList.get(2))));
    }

    protected SchEventEmission buildSchEventEmissionForSchEvent() {
        return new SchEventEmission().mediaItem(libMediaItemList.get(0)).channel(corChannel).network(corNetwork);
    }

    protected SchEventEmission buildSchEventEmissionForSchEventWithAttachment() {
        return new SchEventEmission().mediaItem(libMediaItemList.get(0)).channel(corChannel).network(corNetwork).attachments(Sets.newHashSet(new SchEventEmissionAttachment().channel(corChannel).network(corNetwork).mediaItem(libMediaItemList.get(2))));
    }

    protected Set<SchBlock> buildNestedSetBlocks() {
        //ROOT Chil Child
        SchBlock schBlockRootChildChild = factory.manufacturePojo(SchBlock.class);
        schBlockRootChildChild.addEmission(buildEmissionForWithAttachment());
        schBlockRootChildChild.setId(null);
        schBlockRootChildChild.setNetwork(corNetwork);
        schBlockRootChildChild.setChannel(corChannel);

        SchBlock schBlockRootChildChild1 = factory.manufacturePojo(SchBlock.class);

        schBlockRootChildChild1.addEmission(buildEmissionForWithAttachment());
        schBlockRootChildChild1.setId(null);
        schBlockRootChildChild1.setNetwork(corNetwork);
        schBlockRootChildChild1.setChannel(corChannel);

        SchBlock schBlockRootChildChild2 = factory.manufacturePojo(SchBlock.class);

        schBlockRootChildChild2.addEmission(buildEmissionForWithAttachment());
        schBlockRootChildChild2.setId(null);
        schBlockRootChildChild2.setNetwork(corNetwork);
        schBlockRootChildChild2.setChannel(corChannel);

        //ROOTS Childs
        SchBlock schBlockRootChild = factory.manufacturePojo(SchBlock.class);
        schBlockRootChild.addEmission(buildEmissionForWithAttachment());
        schBlockRootChild.setId(null);
        schBlockRootChild.setNetwork(corNetwork);
        schBlockRootChild.setChannel(corChannel);
        schBlockRootChild.addBlock(schBlockRootChildChild);

        SchBlock schBlockRootChild1 = factory.manufacturePojo(SchBlock.class);
        schBlockRootChild1.addEmission(buildEmissionForWithAttachment());
        schBlockRootChild1.setId(null);
        schBlockRootChild1.setNetwork(corNetwork);
        schBlockRootChild1.setChannel(corChannel);
        schBlockRootChild1.addBlock(schBlockRootChildChild1);

        SchBlock schBlockRootChild2 = factory.manufacturePojo(SchBlock.class);
        schBlockRootChild2.addEmission(buildEmissionForWithAttachment());
        schBlockRootChild2.setId(null);
        schBlockRootChild2.setNetwork(corNetwork);
        schBlockRootChild2.setChannel(corChannel);
        schBlockRootChild2.addBlock(schBlockRootChildChild2);

        ///ROOTS
        SchBlock schBlockRoot = factory.manufacturePojo(SchBlock.class);
        schBlockRoot.addEmission(buildEmissionForWithAttachment());
        schBlockRoot.setId(null);
        schBlockRoot.setNetwork(corNetwork);
        schBlockRoot.setChannel(corChannel);
        schBlockRoot.addBlock(schBlockRootChild);

        SchBlock schBlockRoot1 = factory.manufacturePojo(SchBlock.class);
        schBlockRoot1.addEmission(buildEmissionForWithAttachment());
        schBlockRoot1.setId(null);
        schBlockRoot1.setNetwork(corNetwork);
        schBlockRoot1.setChannel(corChannel);
        schBlockRoot1.addBlock(schBlockRootChild1);

        SchBlock schBlockRoot2 = factory.manufacturePojo(SchBlock.class);
        schBlockRoot2.addEmission(buildEmissionForWithAttachment());
        schBlockRoot2.setId(null);
        schBlockRoot2.setNetwork(corNetwork);
        schBlockRoot2.setChannel(corChannel);
        schBlockRoot2.addBlock(schBlockRootChild2);
        return Sets.newHashSet(Sets.newHashSet(schBlockRoot, schBlockRoot1, schBlockRoot2));
    }

    protected Set<SchEvent> buildNestedSetEvents() {
        //ROOT Chil Child
        SchEvent schEventRootChildChild = factory.manufacturePojo(SchEvent.class);
        schEventRootChildChild.addEmission(buildSchEventEmissionForSchEventWithAttachment());
        schEventRootChildChild.setId(null);
        schEventRootChildChild.setNetwork(corNetwork);
        schEventRootChildChild.setChannel(corChannel);

        SchEvent schEventRootChildChild1 = factory.manufacturePojo(SchEvent.class);

        schEventRootChildChild1.addEmission(buildSchEventEmissionForSchEventWithAttachment());
        schEventRootChildChild1.setId(null);
        schEventRootChildChild1.setNetwork(corNetwork);
        schEventRootChildChild1.setChannel(corChannel);

        SchEvent schEventRootChildChild2 = factory.manufacturePojo(SchEvent.class);

        schEventRootChildChild2.addEmission(buildSchEventEmissionForSchEventWithAttachment());
        schEventRootChildChild2.setId(null);
        schEventRootChildChild2.setNetwork(corNetwork);
        schEventRootChildChild2.setChannel(corChannel);

        //ROOTS Childs
        SchEvent schEventRootChild = factory.manufacturePojo(SchEvent.class);
        schEventRootChild.addEmission(buildSchEventEmissionForSchEventWithAttachment());
        schEventRootChild.setId(null);
        schEventRootChild.setNetwork(corNetwork);
        schEventRootChild.setChannel(corChannel);
        schEventRootChild.addBlock(schEventRootChildChild);

        SchEvent schEventRootChild1 = factory.manufacturePojo(SchEvent.class);
        schEventRootChild1.addEmission(buildSchEventEmissionForSchEventWithAttachment());
        schEventRootChild1.setId(null);
        schEventRootChild1.setNetwork(corNetwork);
        schEventRootChild1.setChannel(corChannel);
        schEventRootChild1.addBlock(schEventRootChildChild1);

        SchEvent schEventRootChild2 = factory.manufacturePojo(SchEvent.class);
        schEventRootChild2.addEmission(buildSchEventEmissionForSchEventWithAttachment());
        schEventRootChild2.setId(null);
        schEventRootChild2.setNetwork(corNetwork);
        schEventRootChild2.setChannel(corChannel);
        schEventRootChild2.addBlock(schEventRootChildChild2);

        ///ROOTS
        SchEvent schEventRoot = factory.manufacturePojo(SchEvent.class);
        schEventRoot.addEmission(buildSchEventEmissionForSchEventWithAttachment());
        schEventRoot.setId(null);
        schEventRoot.setNetwork(corNetwork);
        schEventRoot.setChannel(corChannel);
        schEventRoot.addBlock(schEventRootChild);

        SchEvent schEventRoot1 = factory.manufacturePojo(SchEvent.class);
        schEventRoot1.addEmission(buildSchEventEmissionForSchEventWithAttachment());
        schEventRoot1.setId(null);
        schEventRoot1.setNetwork(corNetwork);
        schEventRoot1.setChannel(corChannel);
        schEventRoot1.addBlock(schEventRootChild1);

        SchEvent schEventRoot2 = factory.manufacturePojo(SchEvent.class);
        schEventRoot2.addEmission(buildSchEventEmissionForSchEventWithAttachment());
        schEventRoot2.setId(null);
        schEventRoot2.setNetwork(corNetwork);
        schEventRoot2.setChannel(corChannel);
        schEventRoot2.addBlock(schEventRootChild2);
        return Sets.newHashSet(Sets.newHashSet(schEventRoot, schEventRoot1, schEventRoot2));
    }

    protected Set<SchEvent> buildNestedSetEventsWithLenght30minoutes(SchClockConfiguration schClockConfiguration) {
        return Sets.newHashSet(buildEventWithEmissionAndAttachmentsLenght10minoutes(schClockConfiguration, 1),
                buildEventWithEmissionAndAttachmentsLenght10minoutes(schClockConfiguration, 2),
                buildEventWithEmissionAndAttachmentsLenght10minoutes(schClockConfiguration, 3));

    }

    protected Set<SchEvent> buildNestedSetEventsWithLenght30minoutesWithMusicImport(SchClockConfiguration schClockConfiguration) {
        return Sets.newHashSet(buildEventWithsLenght20minoutesMusicImport(schClockConfiguration,1),
                buildEventWithsEmissionsAndOprImport20minoutes(schClockConfiguration,2),
                buildEventWithEmissionAndAttachmentsLenght10minoutes(schClockConfiguration, 3));

    }

    protected SchEvent buildEventWithsEmissionsAndOprImport20minoutes(SchClockConfiguration schClockConfiguration, long sequence) {
        SchEvent schEvent = new SchEvent()
                .eventType(EventTypeEnum.ET_MUSIC)
                .sequence(sequence)
                .timeParams(new SchConfigurationTimeParams().length(12000000L ))
                .clockConfiguration(schClockConfiguration)
                .channel(corChannel)
                .network(corNetwork);


        schEventRepository.save(schEvent);
        schEvent.blocks(Sets.newHashSet(buildOprImportEvent(1, 100000L, schEvent)))
                .emissions(Sets.newHashSet(
                        buildEmissionWithAttachment(2, 100000L, schEvent),
                        buildEmissionWithAttachment(3, 100000L, schEvent))
                );
        return schEvent;
    }

    private SchEvent buildOprImportEvent(long i, long l, SchEvent schEventParent) {
        SchEvent schEvent = new SchEvent()
                .sequence(i)
                .eventType(EventTypeEnum.ET_IMPORT_LOG)
                .schLogConfiguration(buildOPRLogConfigurationWithSeparator())
                .timeParams(new SchConfigurationTimeParams().length(l))
                .event(schEventParent)
                .schLogConfiguration(buildOPRLogConfigurationWithSeparator())
                .channel(corChannel)
                .network(corNetwork);
        schEventRepository.save(schEvent);
        return schEvent;
    }


    protected SchEvent buildEventWithsLenght20minoutesMusicImport(SchClockConfiguration schClockConfiguration, long sequence) {
        SchEvent schEvent = new SchEvent()
                .eventType(EventTypeEnum.ET_IMPORT_LOG)
                .sequence(sequence)
                .timeParams(new SchConfigurationTimeParams().length(1200000L))
                .clockConfiguration(schClockConfiguration)
                .schLogConfiguration(buildMusLogConfigurationWithSeparator())
                .channel(corChannel)
                .network(corNetwork);
        schEventRepository.save(schEvent);
        return schEvent;
    }

    protected SchEvent buildEventWithEmissionAndAttachmentsLenght10minoutes(SchClockConfiguration schClockConfiguration, long sequence) {
        SchEvent schEvent = new SchEvent()
                .sequence(sequence)
                .eventType(EventTypeEnum.ET_MUSIC)
                .timeParams(new SchConfigurationTimeParams().length(600000L))
                .clockConfiguration(schClockConfiguration)
                .channel(corChannel)
                .network(corNetwork);


        schEventRepository.save(schEvent);
        schEvent.blocks(Sets.newHashSet(
                buildEventWithEmissionAndAttachmentsLenght(300000L, schEvent)))
                .emissions(Sets.newHashSet(
                        buildEmissionWithAttachment(1, 100000L, schEvent),
                        buildEmissionWithAttachment(2, 100000L, schEvent),
                        buildEmissionWithAttachment(3, 100000L, schEvent))
                );
        return schEvent;
    }

    protected SchEvent buildEventWithEmissionAndAttachmentsLenght(long lenght, SchEvent schEvent) {
        SchEvent schEvent1 = new SchEvent()
                .eventType(EventTypeEnum.ET_MUSIC)
                .timeParams(new SchConfigurationTimeParams().length(lenght))
                .event(schEvent).channel(corChannel)
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
        SchEventEmission schEventEmission = new SchEventEmission().mediaItem(libMediaItemList.get(0))
                .seq(sequence)
                .event(schEvent)
                .timeParams(new SchConfigurationTimeParams().length(lenght))
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
                .mediaItem(libMediaItemList.get(0))
                .channel(corChannel)
                .network(corNetwork));
    }

    protected SchLogConfiguration buildRekLogConfiguration() {
        //configuration
        SchLogConfiguration schLogConfiguration = factory.manufacturePojo(SchLogConfiguration.class);
        schLogConfiguration.setExtension("rek");
        schLogConfiguration.setSpearator(null);
        schLogConfiguration.setPattern("yyyyMMdd");
        schLogConfiguration.setChannel(corChannel);
        schLogConfiguration.setNetwork(corNetwork);
        schLogConfiguration = schLogConfigurationRepository.saveAndFlush(schLogConfiguration);
        //columnConfiguration
        SchLogColumn schLogColumnTime = buildLogColumn(LCT_START_TIME, 8, 0, schLogConfiguration);
        SchLogColumn schLogColumnIdx = buildLogColumn(LCT_IDX, 14, 2, schLogConfiguration);
        SchLogColumn schLogColumnLibrary = buildLogColumn(LCT_LIBRARY, 3, 1, schLogConfiguration);
        SchLogColumn schLogColumnLenght = buildLogColumn(LCT_LENGHT, 5, 3, schLogConfiguration);
        SchLogColumn schLogColumnName = buildLogColumn(LCT_NAME, 14, 4, schLogConfiguration);
        schLogConfiguration.setLogColumns(Sets.newHashSet(schLogColumnTime, schLogColumnIdx, schLogColumnLenght, schLogColumnName, schLogColumnLibrary));
        return schLogConfiguration;
    }

    protected SchLogConfiguration buildMusLogConfiguration() {
        //configuration
        SchLogConfiguration schLogConfiguration = factory.manufacturePojo(SchLogConfiguration.class);
        schLogConfiguration.setExtension("MUS");
        schLogConfiguration.setSpearator(null);
        schLogConfiguration.setPattern("yyyyMMdd");
        schLogConfiguration.setChannel(corChannel);
        schLogConfiguration.setNetwork(corNetwork);
        schLogConfiguration = schLogConfigurationRepository.saveAndFlush(schLogConfiguration);
        //columnConfiguration
        SchLogColumn schLogColumnTime = buildLogColumn(LCT_START_TIME, 8, 0, schLogConfiguration);
        SchLogColumn schLogColumnIdx = buildLogColumn(LCT_IDX, 16, 2, schLogConfiguration);
        SchLogColumn schLogColumnLibrary = buildLogColumn(LCT_LIBRARY, 3, 1, schLogConfiguration);
        SchLogColumn schLogColumnLenght = buildLogColumn(LCT_LENGHT, 5, 4, schLogConfiguration);
        SchLogColumn schLogColumnName = buildLogColumn(LCT_NAME, 21, 3, schLogConfiguration);
        schLogConfiguration.setLogColumns(Sets.newHashSet(schLogColumnTime, schLogColumnIdx, schLogColumnLenght, schLogColumnName, schLogColumnLibrary));
        return schLogConfiguration;
    }

    protected SchLogConfiguration buildOPRLogConfiguration() {
        //configuration
        SchLogConfiguration schLogConfiguration = factory.manufacturePojo(SchLogConfiguration.class);
        schLogConfiguration.setExtension("opr");
        schLogConfiguration.setSpearator(null);
        schLogConfiguration.setPattern("yyyyMMdd");
        schLogConfiguration.setChannel(corChannel);
        schLogConfiguration.setNetwork(corNetwork);
        schLogConfiguration = schLogConfigurationRepository.saveAndFlush(schLogConfiguration);
        //columnConfiguration
        SchLogColumn schLogColumnTime = buildLogColumn(LCT_START_TIME, 8, 0, schLogConfiguration);
        SchLogColumn schLogColumnIdx = buildLogColumn(LCT_IDX, 9, 2, schLogConfiguration);
        SchLogColumn schLogColumnLibrary = buildLogColumn(LCT_LIBRARY, 3, 1, schLogConfiguration);
        SchLogColumn schLogColumnLenght = buildLogColumn(LCT_LENGHT, 5, 4, schLogConfiguration);
        SchLogColumn schLogColumnName = buildLogColumn(LCT_NAME, 10, 3, schLogConfiguration);
        schLogConfiguration.setLogColumns(Sets.newHashSet(schLogColumnTime, schLogColumnIdx, schLogColumnLenght, schLogColumnName, schLogColumnLibrary));
        return schLogConfiguration;
    }

    protected SchLogConfiguration buildRekLogConfigurationWithSeparator() {
        //configuration
        SchLogConfiguration schLogConfiguration = factory.manufacturePojo(SchLogConfiguration.class);
        schLogConfiguration.setExtension("rek");
        schLogConfiguration.setPattern("yyyyMMdd");
        schLogConfiguration.setSpearator(";");
        schLogConfiguration.setChannel(corChannel);
        schLogConfiguration.setNetwork(corNetwork);
        schLogConfiguration = schLogConfigurationRepository.saveAndFlush(schLogConfiguration);
        //columnConfiguration
        SchLogColumn schLogColumnTime = buildLogColumnWithoutLenght(LCT_START_TIME, 0, schLogConfiguration);
        SchLogColumn schLogColumnIdx = buildLogColumnWithoutLenght(LCT_IDX, 2, schLogConfiguration);
        SchLogColumn schLogColumnLibrary = buildLogColumnWithoutLenght(LCT_LIBRARY, 1, schLogConfiguration);
        SchLogColumn schLogColumnLenght = buildLogColumnWithoutLenght(LCT_LENGHT, 3, schLogConfiguration);
        SchLogColumn schLogColumnName = buildLogColumnWithoutLenght(LCT_NAME, 4, schLogConfiguration);
        schLogConfiguration.setLogColumns(Sets.newHashSet(schLogColumnTime, schLogColumnIdx, schLogColumnLenght, schLogColumnName, schLogColumnLibrary));
        return schLogConfiguration;
    }

    protected SchLogConfiguration buildMusLogConfigurationWithSeparator() {
        //configuration
        SchLogConfiguration schLogConfiguration = factory.manufacturePojo(SchLogConfiguration.class);
        schLogConfiguration.setExtension("MUS");
        schLogConfiguration.setPattern("yyyyMMdd");
        schLogConfiguration.setSpearator(";");
        schLogConfiguration.setChannel(corChannel);
        schLogConfiguration.setNetwork(corNetwork);
        schLogConfiguration = schLogConfigurationRepository.saveAndFlush(schLogConfiguration);
        //columnConfiguration
        SchLogColumn schLogColumnTime = buildLogColumnWithoutLenght(LCT_START_TIME, 0, schLogConfiguration);
        SchLogColumn schLogColumnIdx = buildLogColumnWithoutLenght(LCT_IDX, 2, schLogConfiguration);
        SchLogColumn schLogColumnLibrary = buildLogColumnWithoutLenght(LCT_LIBRARY, 1, schLogConfiguration);
        SchLogColumn schLogColumnLenght = buildLogColumnWithoutLenght(LCT_LENGHT, 6, schLogConfiguration);
        SchLogColumn schLogColumnName = buildLogColumnWithoutLenght(LCT_NAME, 4, schLogConfiguration);
        schLogConfiguration.setLogColumns(Sets.newHashSet(schLogColumnTime, schLogColumnIdx, schLogColumnLenght, schLogColumnName, schLogColumnLibrary));
        return schLogConfiguration;
    }

    protected SchLogConfiguration buildOPRLogConfigurationWithSeparator() {
        //configuration
        SchLogConfiguration schLogConfiguration = factory.manufacturePojo(SchLogConfiguration.class);
        schLogConfiguration.setExtension("opr");
        schLogConfiguration.setPattern("yyyyMMdd");
        schLogConfiguration.setSpearator(";");
        schLogConfiguration.setChannel(corChannel);
        schLogConfiguration.setNetwork(corNetwork);
        schLogConfiguration = schLogConfigurationRepository.saveAndFlush(schLogConfiguration);
        //columnConfiguration
        SchLogColumn schLogColumnTime = buildLogColumnWithoutLenght(LCT_START_TIME, 0, schLogConfiguration);
        SchLogColumn schLogColumnIdx = buildLogColumnWithoutLenght(LCT_IDX, 2, schLogConfiguration);
        SchLogColumn schLogColumnLibrary = buildLogColumnWithoutLenght(LCT_LIBRARY, 1, schLogConfiguration);
        SchLogColumn schLogColumnLenght = buildLogColumnWithoutLenght(LCT_LENGHT, 6, schLogConfiguration);
        SchLogColumn schLogColumnName = buildLogColumnWithoutLenght(LCT_NAME, 4, schLogConfiguration);
        schLogConfiguration.setLogColumns(Sets.newHashSet(schLogColumnTime, schLogColumnIdx, schLogColumnLenght, schLogColumnName, schLogColumnLibrary));
        return schLogConfiguration;
    }

    protected SchLogColumn buildLogColumn(LogColumnTypEnum logColumnTypEnum, Integer lenght, Integer sequence, SchLogConfiguration schLogConfiguration) {
        SchLogColumn schLogColumn = new SchLogColumn();
        schLogColumn.setColumnSequence(sequence);
        schLogColumn.setName(logColumnTypEnum);
        schLogColumn.setLength(lenght);
        schLogColumn.setSchLogConfiguration(schLogConfiguration);
        schLogColumn.channel(corChannel);
        schLogColumn.network(corNetwork);
        return schLogColumn;
    }

    protected SchLogColumn buildLogColumnWithoutLenght(LogColumnTypEnum logColumnTypEnum, Integer sequence, SchLogConfiguration schLogConfiguration) {
        SchLogColumn schLogColumn = new SchLogColumn();
        schLogColumn.setColumnSequence(sequence);
        schLogColumn.setName(logColumnTypEnum);
        schLogColumn.setSchLogConfiguration(schLogConfiguration);
        schLogColumn.channel(corChannel);
        schLogColumn.network(corNetwork);
        return schLogColumn;
    }


}
