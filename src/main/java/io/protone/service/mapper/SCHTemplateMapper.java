package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.SCHTemplateDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity SCHTemplate and its DTO SCHTemplateDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SCHTemplateMapper {

    @Mapping(source = "channel.id", target = "channelId")
    @Mapping(source = "parentTemplate.id", target = "parentTemplateId")
    SCHTemplateDTO sCHTemplateToSCHTemplateDTO(SCHTemplate sCHTemplate);

    List<SCHTemplateDTO> sCHTemplatesToSCHTemplateDTOs(List<SCHTemplate> sCHTemplates);

    @Mapping(source = "channelId", target = "channel")
    @Mapping(source = "parentTemplateId", target = "parentTemplate")
    SCHTemplate sCHTemplateDTOToSCHTemplate(SCHTemplateDTO sCHTemplateDTO);

    List<SCHTemplate> sCHTemplateDTOsToSCHTemplates(List<SCHTemplateDTO> sCHTemplateDTOs);

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
