package io.protone.application.service.scheduler.base;

import com.google.common.collect.Sets;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.library.api.dto.thin.LibMediaItemThinDTO;
import io.protone.library.domain.LibMediaItem;
import io.protone.scheduler.api.dto.SchAttachmentDTO;
import io.protone.scheduler.api.dto.SchBlockDTO;
import io.protone.scheduler.api.dto.SchEmissionDTO;
import io.protone.scheduler.domain.*;
import io.protone.scheduler.domain.enumeration.AttachmentTypeEnum;
import io.protone.scheduler.domain.enumeration.EventTypeEnum;
import org.assertj.core.util.Lists;
import uk.co.jemos.podam.api.PodamFactory;

import java.util.Set;

/**
 * Created by lukaszozimek on 14/08/2017.
 */
public class SchedulerBaseTest {
    public static final Long LIBRARY_ID = 1L;

    public static SchEmission buildEmissionForBlock(LibMediaItem libMediaItem, CorChannel corChannel, CorNetwork corNetwork) {
        return new SchEmission().mediaItem(libMediaItem).channel(corChannel).network(corNetwork);
    }

    public static SchEmission buildEmissionForWithAttachment(LibMediaItem libMediaItem, CorChannel corChannel, CorNetwork corNetwork) {
        return new SchEmission().mediaItem(libMediaItem).channel(corChannel).network(corNetwork).attachments(Sets.newHashSet(new SchAttachment().channel(corChannel).network(corNetwork).mediaItem(libMediaItem)));
    }

    public static SchBlockDTO buildBlockDTOWithEmissionAndAttachmentsAndNestedBlock(long sequence, LibMediaItemThinDTO libMediaItemThinDTO) {
        SchBlockDTO schBlockDTO = new SchBlockDTO()
                .sequence(sequence)
                .eventType(EventTypeEnum.ET_MUSIC)
                .length(600000L);


        schBlockDTO.blocks(Lists.newArrayList(
                buildBlockDTOWithEmissionAndAttachmentsLenghtSequence(1, libMediaItemThinDTO),
                buildBlockDTOWithEmissionAndAttachmentsLenghtSequence(2, libMediaItemThinDTO),
                buildBlockDTOWithEmissionAndAttachmentsLenghtSequence(3, libMediaItemThinDTO)
        ))
                .emissions(Lists.newArrayList(
                        buildEmissionDTO(4, libMediaItemThinDTO),
                        buildEmissionDTO(5, libMediaItemThinDTO),
                        buildEmissionDTO(6, libMediaItemThinDTO))
                );
        return schBlockDTO;
    }

    public static SchBlockDTO buildBlockDTOWithEmissionAndAttachmentsLenghtSequence(long sequence, LibMediaItemThinDTO libMediaItemThinDTO) {
        SchBlockDTO schBlock = new SchBlockDTO()
                .eventType(EventTypeEnum.ET_MUSIC).sequence(sequence);
        schBlock.emissions(Lists.newArrayList(
                buildEmissionDTO(1, libMediaItemThinDTO),
                buildEmissionDTO(2, libMediaItemThinDTO),
                buildEmissionDTO(3, libMediaItemThinDTO))
        );
        return schBlock;
    }

    public static SchBlockDTO buildBlockDTOWithEmissionAndAttachmentsLenght(LibMediaItemThinDTO libMediaItemThinDTO) {
        SchBlockDTO schBlock = new SchBlockDTO()
                .eventType(EventTypeEnum.ET_MUSIC);

        schBlock.emissions(Lists.newArrayList(
                buildEmissionDTO(1, libMediaItemThinDTO),
                buildEmissionDTO(2, libMediaItemThinDTO),
                buildEmissionDTO(3, libMediaItemThinDTO))
        );
        return schBlock;
    }

    public static SchEmissionDTO buildEmissionDTO(long sequence, LibMediaItemThinDTO libMediaItem) {
        SchEmissionDTO schEmissionDTO = new SchEmissionDTO().mediaItem(libMediaItem)
                .sequence(sequence);
        schEmissionDTO.attachment(Lists.newArrayList(buildAttachmenDTOWithSequence(1, libMediaItem),
                buildAttachmenDTOWithSequence(2, libMediaItem),
                buildAttachmenDTOWithSequence(3, libMediaItem)));
        return schEmissionDTO;
    }

    public static SchAttachmentDTO buildAttachmenDTOWithSequence(long sequence, LibMediaItemThinDTO libMediaItem) {
        return new SchAttachmentDTO().attachmentType(AttachmentTypeEnum.AT_OTHER)
                .sequence(sequence)
                .mediaItem(libMediaItem);
    }

    public static SchEmissionConfiguration buildEmissionConfigurationForWithAttachment(LibMediaItem libMediaItem, CorChannel corChannel, CorNetwork corNetwork) {
        return new SchEmissionConfiguration().mediaItem(libMediaItem).channel(corChannel).network(corNetwork).attachments(Sets.newHashSet(new SchAttachmentConfiguration().channel(corChannel).network(corNetwork).mediaItem(libMediaItem)));
    }

    public static Set<SchEvent> buildNestedSetEvents(PodamFactory factory, LibMediaItem libMediaItem, CorNetwork corNetwork, CorChannel corChannel) {
        //ROOT Chil Child
        SchEvent schEventRootChildChild = factory.manufacturePojo(SchEvent.class);
        schEventRootChildChild.addEventEmissions(buildSchEventEmissionForSchEventWithAttachment(libMediaItem, corChannel, corNetwork));
        schEventRootChildChild.setId(null);
        schEventRootChildChild.setNetwork(corNetwork);
        schEventRootChildChild.setChannel(corChannel);

        SchEvent schEventRootChildChild1 = factory.manufacturePojo(SchEvent.class);

        schEventRootChildChild1.addEventEmissions(buildSchEventEmissionForSchEventWithAttachment(libMediaItem, corChannel, corNetwork));
        schEventRootChildChild1.setId(null);
        schEventRootChildChild1.setNetwork(corNetwork);
        schEventRootChildChild1.setChannel(corChannel);

        SchEvent schEventRootChildChild2 = factory.manufacturePojo(SchEvent.class);

        schEventRootChildChild2.addEventEmissions(buildSchEventEmissionForSchEventWithAttachment(libMediaItem, corChannel, corNetwork));
        schEventRootChildChild2.setId(null);
        schEventRootChildChild2.setNetwork(corNetwork);
        schEventRootChildChild2.setChannel(corChannel);

        //ROOTS Childs
        SchEvent schEventRootChild = factory.manufacturePojo(SchEvent.class);
        schEventRootChild.addEventEmissions(buildSchEventEmissionForSchEventWithAttachment(libMediaItem, corChannel, corNetwork));
        schEventRootChild.setId(null);
        schEventRootChild.setNetwork(corNetwork);
        schEventRootChild.setChannel(corChannel);
        schEventRootChild.addSchEvents(schEventRootChildChild);

        SchEvent schEventRootChild1 = factory.manufacturePojo(SchEvent.class);
        schEventRootChild1.addEventEmissions(buildSchEventEmissionForSchEventWithAttachment(libMediaItem, corChannel, corNetwork));
        schEventRootChild1.setId(null);
        schEventRootChild1.setNetwork(corNetwork);
        schEventRootChild1.setChannel(corChannel);
        schEventRootChild1.addSchEvents(schEventRootChildChild1);

        SchEvent schEventRootChild2 = factory.manufacturePojo(SchEvent.class);
        schEventRootChild2.addEventEmissions(buildSchEventEmissionForSchEventWithAttachment(libMediaItem, corChannel, corNetwork));
        schEventRootChild2.setId(null);
        schEventRootChild2.setNetwork(corNetwork);
        schEventRootChild2.setChannel(corChannel);
        schEventRootChild2.addSchEvents(schEventRootChildChild2);

        ///ROOTS
        SchEvent schEventRoot = factory.manufacturePojo(SchEvent.class);
        schEventRoot.addEventEmissions(buildSchEventEmissionForSchEventWithAttachment(libMediaItem, corChannel, corNetwork));
        schEventRoot.setId(null);
        schEventRoot.setNetwork(corNetwork);
        schEventRoot.setChannel(corChannel);
        schEventRoot.addSchEvents(schEventRootChild);

        SchEvent schEventRoot1 = factory.manufacturePojo(SchEvent.class);
        schEventRoot1.addEventEmissions(buildSchEventEmissionForSchEventWithAttachment(libMediaItem, corChannel, corNetwork));
        schEventRoot1.setId(null);
        schEventRoot1.setNetwork(corNetwork);
        schEventRoot1.setChannel(corChannel);
        schEventRoot1.addSchEvents(schEventRootChild1);

        SchEvent schEventRoot2 = factory.manufacturePojo(SchEvent.class);
        schEventRoot2.addEventEmissions(buildSchEventEmissionForSchEventWithAttachment(libMediaItem, corChannel, corNetwork));
        schEventRoot2.setId(null);
        schEventRoot2.setNetwork(corNetwork);
        schEventRoot2.setChannel(corChannel);
        schEventRoot2.addSchEvents(schEventRootChild2);
        return Sets.newHashSet(Sets.newHashSet(schEventRoot, schEventRoot1, schEventRoot2));
    }

    public static SchEventEmission buildSchEventEmissionForSchEventWithAttachment(LibMediaItem libMediaItem, CorChannel corChannel, CorNetwork corNetwork) {
        return new SchEventEmission().mediaItem(libMediaItem).channel(corChannel).network(corNetwork).attachments(Sets.newHashSet(new SchEventEmissionAttachment().channel(corChannel).network(corNetwork).mediaItem(libMediaItem)));
    }

    public static SchEventEmission buildSchEventEmissionForSchEvent(LibMediaItem libMediaItem, CorChannel corChannel, CorNetwork corNetwork) {
        return new SchEventEmission().mediaItem(libMediaItem).channel(corChannel).network(corNetwork);
    }


    public static Set<SchBlock> buildNestedSetBlocks(PodamFactory factory, LibMediaItem libMediaItem, CorChannel corChannel, CorNetwork corNetwork) {
        //ROOT Chil Child
        SchBlock schBlockRootChildChild = factory.manufacturePojo(SchBlock.class);
        schBlockRootChildChild.addEmission(buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork));
        schBlockRootChildChild.setId(null);
        schBlockRootChildChild.setNetwork(corNetwork);
        schBlockRootChildChild.setChannel(corChannel);

        SchBlock schBlockRootChildChild1 = factory.manufacturePojo(SchBlock.class);

        schBlockRootChildChild1.addEmission(buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork));
        schBlockRootChildChild1.setId(null);
        schBlockRootChildChild1.setNetwork(corNetwork);
        schBlockRootChildChild1.setChannel(corChannel);

        SchBlock schBlockRootChildChild2 = factory.manufacturePojo(SchBlock.class);

        schBlockRootChildChild2.addEmission(buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork));
        schBlockRootChildChild2.setId(null);
        schBlockRootChildChild2.setNetwork(corNetwork);
        schBlockRootChildChild2.setChannel(corChannel);

        //ROOTS Childs
        SchBlock schBlockRootChild = factory.manufacturePojo(SchBlock.class);
        schBlockRootChild.addEmission(buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork));
        schBlockRootChild.setId(null);
        schBlockRootChild.setNetwork(corNetwork);
        schBlockRootChild.setChannel(corChannel);
        schBlockRootChild.addBlock(schBlockRootChildChild);

        SchBlock schBlockRootChild1 = factory.manufacturePojo(SchBlock.class);
        schBlockRootChild1.addEmission(buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork));
        schBlockRootChild1.setId(null);
        schBlockRootChild1.setNetwork(corNetwork);
        schBlockRootChild1.setChannel(corChannel);
        schBlockRootChild1.addBlock(schBlockRootChildChild1);

        SchBlock schBlockRootChild2 = factory.manufacturePojo(SchBlock.class);
        schBlockRootChild2.addEmission(buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork));
        schBlockRootChild2.setId(null);
        schBlockRootChild2.setNetwork(corNetwork);
        schBlockRootChild2.setChannel(corChannel);
        schBlockRootChild2.addBlock(schBlockRootChildChild2);

        ///ROOTS
        SchBlock schBlockRoot = factory.manufacturePojo(SchBlock.class);
        schBlockRoot.addEmission(buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork));
        schBlockRoot.setId(null);
        schBlockRoot.setNetwork(corNetwork);
        schBlockRoot.setChannel(corChannel);
        schBlockRoot.addBlock(schBlockRootChild);

        SchBlock schBlockRoot1 = factory.manufacturePojo(SchBlock.class);
        schBlockRoot1.addEmission(buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork));
        schBlockRoot1.setId(null);
        schBlockRoot1.setNetwork(corNetwork);
        schBlockRoot1.setChannel(corChannel);
        schBlockRoot1.addBlock(schBlockRootChild1);

        SchBlock schBlockRoot2 = factory.manufacturePojo(SchBlock.class);
        schBlockRoot2.addEmission(buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork));
        schBlockRoot2.setId(null);
        schBlockRoot2.setNetwork(corNetwork);
        schBlockRoot2.setChannel(corChannel);
        schBlockRoot2.addBlock(schBlockRootChild2);
        return Sets.newHashSet(Sets.newHashSet(schBlockRoot, schBlockRoot1, schBlockRoot2));
    }


}