package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.SchTemplateDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity SchTemplate and its DTO SchTemplateDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SchTemplateMapper {

    @Mapping(source = "channel.id", target = "channelId")
    SchTemplateDTO schTemplateToSchTemplateDTO(SchTemplate schTemplate);

    List<SchTemplateDTO> schTemplatesToSchTemplateDTOs(List<SchTemplate> schTemplates);

    @Mapping(source = "channelId", target = "channel")
    SchTemplate schTemplateDTOToSchTemplate(SchTemplateDTO schTemplateDTO);

    List<SchTemplate> schTemplateDTOsToSchTemplates(List<SchTemplateDTO> schTemplateDTOs);

    default CorChannel corChannelFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorChannel corChannel = new CorChannel();
        corChannel.setId(id);
        return corChannel;
    }
}
