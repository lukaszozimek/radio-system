package io.protone.application.service.scheduler.base;

import com.google.common.collect.Sets;
import io.protone.application.service.library.util.LibraryGenerator;
import io.protone.library.domain.LibMediaItem;
import io.protone.scheduler.domain.*;
import io.protone.scheduler.repository.SchLogColumnRepostiory;
import io.protone.scheduler.repository.SchLogConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

import static io.protone.scheduler.domain.enumeration.LogColumnTypEnum.*;

/**
 * Created by lukaszozimek on 14/08/2017.
 */
public class SchedulerBaseTest extends LibraryGenerator {
    @Autowired
    private SchLogColumnRepostiory schLogColumnRepostiory;

    @Autowired
    private SchLogConfigurationRepository schLogConfigurationRepository;

    private List<LibMediaItem> libMediaItemList;

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

    public void buildRekLogConfiguration() {
        //configuration
        SchLogConfiguration schLogConfiguration = factory.manufacturePojo(SchLogConfiguration.class);
        schLogConfiguration.setExtension("rek");
        schLogConfiguration.setPattern("yyyyMMdd");
        schLogConfiguration.setChannel(corChannel);
        schLogConfiguration.setNetwork(corNetwork);
        schLogConfiguration = schLogConfigurationRepository.saveAndFlush(schLogConfiguration);
        //columnConfiguration
        SchLogColumn schLogColumnTime = new SchLogColumn();
        schLogColumnTime.setColumnSequence(1);
        schLogColumnTime.setName(LCT_START_TIME);
        schLogColumnTime.setLength(9);
        schLogColumnTime.channel(corChannel);
        schLogColumnTime.setSchLogConfiguration(schLogConfiguration);
        schLogColumnTime.network(corNetwork);

        SchLogColumn schLogColumnIdx = new SchLogColumn();
        schLogColumnIdx.setColumnSequence(2);
        schLogColumnIdx.setName(LCT_IDX);
        schLogColumnIdx.setLength(14);
        schLogColumnIdx.channel(corChannel);
        schLogColumnIdx.setSchLogConfiguration(schLogConfiguration);
        schLogColumnIdx.network(corNetwork);

        SchLogColumn schLogColumnLibrary = new SchLogColumn();
        schLogColumnLibrary.setColumnSequence(3);
        schLogColumnLibrary.setName(LCT_LIBRARY);
        schLogColumnLibrary.setLength(14);
        schLogColumnLibrary.setSchLogConfiguration(schLogConfiguration);
        schLogColumnLibrary.channel(corChannel);
        schLogColumnLibrary.network(corNetwork);

        SchLogColumn schLogColumnLenght = new SchLogColumn();
        schLogColumnLenght.setColumnSequence(4);
        schLogColumnLenght.setName(LCT_LENGHT);
        schLogColumnLenght.setLength(14);
        schLogColumnLenght.setSchLogConfiguration(schLogConfiguration);
        schLogColumnLenght.channel(corChannel);
        schLogColumnLenght.network(corNetwork);

        SchLogColumn schLogColumnName = new SchLogColumn();
        schLogColumnName.setColumnSequence(5);
        schLogColumnName.setName(LCT_NAME);
        schLogColumnName.setLength(22);
        schLogColumnName.setSchLogConfiguration(schLogConfiguration);
        schLogColumnName.channel(corChannel);
        schLogColumnName.network(corNetwork);

        schLogConfiguration.setLogColumns(Sets.newHashSet(schLogColumnTime, schLogColumnIdx, schLogColumnLenght, schLogColumnName, schLogColumnLibrary));

    }
}
