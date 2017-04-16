package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.SchEventPT;
import io.protone.domain.CorChannel;
import io.protone.domain.SchTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity SchTemplate and its DTO SchTemplateDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomSchTemplateMapper {

    @Mapping(source = "channel.id", target = "channelId")
    SchEventPT DBToDTO(SchTemplate schTemplate);

    List<SchEventPT> DBsToDTOs(List<SchTemplate> schTemplates);

    @Mapping(source = "channelId", target = "channel")
    SchTemplate DTOToDB(SchEventPT schTemplateDTO);

    List<SchTemplate> DTOsToDBs(List<SchEventPT> schTemplateDTOs);

    default CorChannel mapCorChannel(Long id) {
        if (id == null) {
            return null;
        }
        CorChannel corChannel = new CorChannel();
        corChannel.setId(id);
        return corChannel;
    }
}
