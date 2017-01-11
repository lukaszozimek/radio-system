package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.SCHBlockDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity SCHBlock and its DTO SCHBlockDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SCHBlockMapper {

    @Mapping(source = "parentBlock.id", target = "parentBlockId")
    @Mapping(source = "channel.id", target = "channelId")
    @Mapping(source = "template.id", target = "templateId")
    SCHBlockDTO sCHBlockToSCHBlockDTO(SCHBlock sCHBlock);

    List<SCHBlockDTO> sCHBlocksToSCHBlockDTOs(List<SCHBlock> sCHBlocks);

    @Mapping(source = "parentBlockId", target = "parentBlock")
    @Mapping(source = "channelId", target = "channel")
    @Mapping(source = "templateId", target = "template")
    SCHBlock sCHBlockDTOToSCHBlock(SCHBlockDTO sCHBlockDTO);

    List<SCHBlock> sCHBlockDTOsToSCHBlocks(List<SCHBlockDTO> sCHBlockDTOs);

    default SCHBlock sCHBlockFromId(Long id) {
        if (id == null) {
            return null;
        }
        SCHBlock sCHBlock = new SCHBlock();
        sCHBlock.setId(id);
        return sCHBlock;
    }

    default CORChannel cORChannelFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORChannel cORChannel = new CORChannel();
        cORChannel.setId(id);
        return cORChannel;
    }

    default SCHTemplate sCHTemplateFromId(Long id) {
        if (id == null) {
            return null;
        }
        SCHTemplate sCHTemplate = new SCHTemplate();
        sCHTemplate.setId(id);
        return sCHTemplate;
    }
}
