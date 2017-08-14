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

/**
 * Mapper for the entity Attachment and its DTO AttachmentDTO.
 */
@Mapper(componentModel = "spring", uses = {LibMediaItemThinMapper.class, SchEmissionMapper.class,})
public interface SchAttachmentMapper extends SchEntityMapper<SchAttachmentDTO, SchAttachment> {
    @AfterMapping
    default void SchAttachmentDTOToSchAttachmentAfterMapping(SchAttachmentDTO dto, @MappingTarget SchAttachment entity,  @Context CorNetwork network, @Context CorChannel corChannel) {
        entity.setNetwork(network);
        entity.setChannel(corChannel);
    }
}
