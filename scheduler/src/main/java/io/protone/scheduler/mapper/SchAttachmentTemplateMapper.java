package io.protone.scheduler.mapper;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.library.mapper.LibMediaItemThinMapper;
import io.protone.scheduler.api.dto.SchAttachmentTemplateDTO;
import io.protone.scheduler.domain.SchAttachmentTemplate;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for the entity Attachment and its SchAttachmentDTOTO AttachmentSchAttachmentDTOTO.
 */
@Mapper(componentModel = "spring", uses = {LibMediaItemThinMapper.class, SchEmissionTemplateMapper.class})
public interface SchAttachmentTemplateMapper {
    SchAttachmentTemplate DTO2DB(SchAttachmentTemplateDTO dto, @Context CorNetwork network, @Context CorChannel corChannel);

    SchAttachmentTemplateDTO DB2DTO(SchAttachmentTemplate entity);

    List<SchAttachmentTemplateDTO> DBs2DTOs(List<SchAttachmentTemplate> entityList);

    default List<SchAttachmentTemplate> DTOs2DBs(List<SchAttachmentTemplateDTO> dList, @Context CorNetwork network, @Context CorChannel corChannel) {
        List<SchAttachmentTemplate> eList = new ArrayList<>();
        if (dList.isEmpty() || dList == null) {
            return null;
        }
        for (SchAttachmentTemplateDTO dto : dList) {
            eList.add(DTO2DB(dto, network, corChannel));
        }
        return eList;
    }

    @AfterMapping
    default void schAttachmentConfigurationDTOToSchAttachmentConfigurationAfterMapping(SchAttachmentTemplateDTO dto, @MappingTarget SchAttachmentTemplate entity, @Context CorNetwork network, @Context CorChannel corChannel) {
        entity.setNetwork(network);
        entity.setChannel(corChannel);
    }
}
