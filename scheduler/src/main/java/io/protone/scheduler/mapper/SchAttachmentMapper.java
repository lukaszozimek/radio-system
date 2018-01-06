package io.protone.scheduler.mapper;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.library.mapper.LibMediaItemThinMapper;
import io.protone.scheduler.api.dto.SchAttachmentDTO;
import io.protone.scheduler.domain.SchAttachment;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for the entity Attachment and its DTO AttachmentDTO.
 */
@Mapper(componentModel = "spring", uses = {LibMediaItemThinMapper.class, SchEmissionMapper.class})
public interface SchAttachmentMapper {
    SchAttachment DTO2DB(SchAttachmentDTO dto, @Context CorChannel corChannel);

    SchAttachmentDTO DB2DTO(SchAttachment entity);

    List<SchAttachmentDTO> DBs2DTOs(List<SchAttachment> entityList);

    default List<SchAttachment> DTOs2DBs(List<SchAttachmentDTO> dList, @Context CorChannel corChannel) {
        List<SchAttachment> eList = new ArrayList<>();
        if (dList.isEmpty() || dList == null) {
            return null;
        }
        for (SchAttachmentDTO dto : dList) {
            eList.add(DTO2DB(dto, corChannel));
        }
        return eList;
    }

    @AfterMapping
    default void SchAttachmentDTOToSchAttachmentAfterMapping(SchAttachmentDTO dto, @MappingTarget SchAttachment entity, @Context CorChannel corChannel) {
        entity.setChannel(corChannel);
    }
}
