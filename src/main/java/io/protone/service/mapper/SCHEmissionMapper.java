package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.SCHEmissionDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity SCHEmission and its DTO SCHEmissionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SCHEmissionMapper {

    @Mapping(source = "block.id", target = "blockId")
    @Mapping(source = "mediaItem.id", target = "mediaItemId")
    @Mapping(source = "template.id", target = "templateId")
    SCHEmissionDTO sCHEmissionToSCHEmissionDTO(SCHEmission sCHEmission);

    List<SCHEmissionDTO> sCHEmissionsToSCHEmissionDTOs(List<SCHEmission> sCHEmissions);

    @Mapping(source = "blockId", target = "block")
    @Mapping(source = "mediaItemId", target = "mediaItem")
    @Mapping(source = "templateId", target = "template")
    SCHEmission sCHEmissionDTOToSCHEmission(SCHEmissionDTO sCHEmissionDTO);

    List<SCHEmission> sCHEmissionDTOsToSCHEmissions(List<SCHEmissionDTO> sCHEmissionDTOs);

    default SCHBlock sCHBlockFromId(Long id) {
        if (id == null) {
            return null;
        }
        SCHBlock sCHBlock = new SCHBlock();
        sCHBlock.setId(id);
        return sCHBlock;
    }

    default LIBMediaItem lIBMediaItemFromId(Long id) {
        if (id == null) {
            return null;
        }
        LIBMediaItem lIBMediaItem = new LIBMediaItem();
        lIBMediaItem.setId(id);
        return lIBMediaItem;
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
