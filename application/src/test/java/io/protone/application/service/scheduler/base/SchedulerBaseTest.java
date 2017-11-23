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

import java.util.List;
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
        return new SchEmission().mediaItem(libMediaItem).channel(corChannel).network(corNetwork).attachments(Lists.newArrayList(new SchAttachment().channel(corChannel).network(corNetwork).mediaItem(libMediaItem)));
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

    public static SchEmissionTemplate buildEmissionConfigurationForWithAttachment(LibMediaItem libMediaItem, CorChannel corChannel, CorNetwork corNetwork) {
        return new SchEmissionTemplate().mediaItem(libMediaItem).channel(corChannel).network(corNetwork).attachments(Lists.newArrayList(new SchAttachmentTemplate().channel(corChannel).network(corNetwork).mediaItem(libMediaItem)));
    }

    public static List<SchEventTemplate> buildNestedSetEvents(PodamFactory factory, LibMediaItem libMediaItem, CorNetwork corNetwork, CorChannel corChannel) {
        //ROOT Chil Child
        SchEventTemplate schEventTemplateRootChildChild = factory.manufacturePojo(SchEventTemplate.class);
        schEventTemplateRootChildChild.addEmissionTemplate(buildSchEventEmissionForSchEventWithAttachment(libMediaItem, corChannel, corNetwork));
        schEventTemplateRootChildChild.setId(null);
        schEventTemplateRootChildChild.setNetwork(corNetwork);
        schEventTemplateRootChildChild.setChannel(corChannel);

        SchEventTemplate schEventTemplateRootChildChild1 = factory.manufacturePojo(SchEventTemplate.class);

        schEventTemplateRootChildChild1.addEmissionTemplate(buildSchEventEmissionForSchEventWithAttachment(libMediaItem, corChannel, corNetwork));
        schEventTemplateRootChildChild1.setId(null);
        schEventTemplateRootChildChild1.setNetwork(corNetwork);
        schEventTemplateRootChildChild1.setChannel(corChannel);

        SchEventTemplate schEventTemplateRootChildChild2 = factory.manufacturePojo(SchEventTemplate.class);

        schEventTemplateRootChildChild2.addEmissionTemplate(buildSchEventEmissionForSchEventWithAttachment(libMediaItem, corChannel, corNetwork));
        schEventTemplateRootChildChild2.setId(null);
        schEventTemplateRootChildChild2.setNetwork(corNetwork);
        schEventTemplateRootChildChild2.setChannel(corChannel);

        //ROOTS Childs
        SchEventTemplate schEventTemplateRootChild = factory.manufacturePojo(SchEventTemplate.class);
        schEventTemplateRootChild.addEmissionTemplate(buildSchEventEmissionForSchEventWithAttachment(libMediaItem, corChannel, corNetwork));
        schEventTemplateRootChild.setId(null);
        schEventTemplateRootChild.setNetwork(corNetwork);
        schEventTemplateRootChild.setChannel(corChannel);
        //  schEventTemplateRootChild.addEventTemplate(schEventTemplateRootChildChild);

        SchEventTemplate schEventTemplateRootChild1 = factory.manufacturePojo(SchEventTemplate.class);
        schEventTemplateRootChild1.addEmissionTemplate(buildSchEventEmissionForSchEventWithAttachment(libMediaItem, corChannel, corNetwork));
        schEventTemplateRootChild1.setId(null);
        schEventTemplateRootChild1.setNetwork(corNetwork);
        schEventTemplateRootChild1.setChannel(corChannel);
        // schEventTemplateRootChild1.addEventTemplate(schEventTemplateRootChildChild1);

        SchEventTemplate schEventTemplateRootChild2 = factory.manufacturePojo(SchEventTemplate.class);
        schEventTemplateRootChild2.addEmissionTemplate(buildSchEventEmissionForSchEventWithAttachment(libMediaItem, corChannel, corNetwork));
        schEventTemplateRootChild2.setId(null);
        schEventTemplateRootChild2.setNetwork(corNetwork);
        schEventTemplateRootChild2.setChannel(corChannel);
        //    schEventTemplateRootChild2.addEventTemplate(schEventTemplateRootChildChild2);

        ///ROOTS
        SchEventTemplate schEventTemplateRoot = factory.manufacturePojo(SchEventTemplate.class);
        schEventTemplateRoot.addEmissionTemplate(buildSchEventEmissionForSchEventWithAttachment(libMediaItem, corChannel, corNetwork));
        schEventTemplateRoot.setId(null);
        schEventTemplateRoot.setNetwork(corNetwork);
        schEventTemplateRoot.setChannel(corChannel);
        //  schEventTemplateRoot.addEventTemplate(schEventTemplateRootChild);

        SchEventTemplate schEventTemplateRoot1 = factory.manufacturePojo(SchEventTemplate.class);
        schEventTemplateRoot1.addEmissionTemplate(buildSchEventEmissionForSchEventWithAttachment(libMediaItem, corChannel, corNetwork));
        schEventTemplateRoot1.setId(null);
        schEventTemplateRoot1.setNetwork(corNetwork);
        schEventTemplateRoot1.setChannel(corChannel);
        //     schEventTemplateRoot1.addEventTemplate(schEventTemplateRootChild1);

        SchEventTemplate schEventTemplateRoot2 = factory.manufacturePojo(SchEventTemplate.class);
        schEventTemplateRoot2.addEmissionTemplate(buildSchEventEmissionForSchEventWithAttachment(libMediaItem, corChannel, corNetwork));
        schEventTemplateRoot2.setId(null);
        schEventTemplateRoot2.setNetwork(corNetwork);
        schEventTemplateRoot2.setChannel(corChannel);
        //    schEventTemplateRoot2.addEventTemplate(schEventTemplateRootChild2);
        return Lists.newArrayList(Lists.newArrayList(schEventTemplateRoot, schEventTemplateRoot1, schEventTemplateRoot2));
    }

    public static SchEmissionTemplate buildSchEventEmissionForSchEventWithAttachment(LibMediaItem libMediaItem, CorChannel corChannel, CorNetwork corNetwork) {
        return new SchEmissionTemplate().mediaItem(libMediaItem).channel(corChannel).network(corNetwork).attachments(Lists.newArrayList(new SchAttachmentTemplate().channel(corChannel).network(corNetwork).mediaItem(libMediaItem)));
    }

    public static SchEmissionTemplate buildSchEventEmissionForSchEvent(LibMediaItem libMediaItem, CorChannel corChannel, CorNetwork corNetwork) {
        return new SchEmissionTemplate().mediaItem(libMediaItem).channel(corChannel).network(corNetwork);
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
        schBlockRootChild.addBlock(new SchBlockSchBlock().parent(schBlockRootChild).child(schBlockRootChildChild));

        SchBlock schBlockRootChild1 = factory.manufacturePojo(SchBlock.class);
        schBlockRootChild1.addEmission(buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork));
        schBlockRootChild1.setId(null);
        schBlockRootChild1.setNetwork(corNetwork);
        schBlockRootChild1.setChannel(corChannel);
        schBlockRootChild1.addBlock(new SchBlockSchBlock().parent(schBlockRootChild1).child(schBlockRootChildChild1));

        SchBlock schBlockRootChild2 = factory.manufacturePojo(SchBlock.class);
        schBlockRootChild2.addEmission(buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork));
        schBlockRootChild2.setId(null);
        schBlockRootChild2.setNetwork(corNetwork);
        schBlockRootChild2.setChannel(corChannel);
        schBlockRootChild2.addBlock(new SchBlockSchBlock().parent(schBlockRootChild2).child(schBlockRootChildChild2));

        ///ROOTS
        SchBlock schBlockRoot = factory.manufacturePojo(SchBlock.class);
        schBlockRoot.addEmission(buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork));
        schBlockRoot.setId(null);
        schBlockRoot.setNetwork(corNetwork);
        schBlockRoot.setChannel(corChannel);
        schBlockRoot.addBlock(new SchBlockSchBlock().parent(schBlockRoot).child(schBlockRootChild));

        SchBlock schBlockRoot1 = factory.manufacturePojo(SchBlock.class);
        schBlockRoot1.addEmission(buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork));
        schBlockRoot1.setId(null);
        schBlockRoot1.setNetwork(corNetwork);
        schBlockRoot1.setChannel(corChannel);
        schBlockRoot1.addBlock(new SchBlockSchBlock().parent(schBlockRoot1).child(schBlockRootChild1));

        SchBlock schBlockRoot2 = factory.manufacturePojo(SchBlock.class);
        schBlockRoot2.addEmission(buildEmissionForWithAttachment(libMediaItem, corChannel, corNetwork));
        schBlockRoot2.setId(null);
        schBlockRoot2.setNetwork(corNetwork);
        schBlockRoot2.setChannel(corChannel);
        schBlockRoot2.addBlock(new SchBlockSchBlock().parent(schBlockRoot2).child(schBlockRootChild2));
        return Sets.newHashSet(Sets.newHashSet(schBlockRoot, schBlockRoot1, schBlockRoot2));
    }


}