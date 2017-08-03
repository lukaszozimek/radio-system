package io.protone.scheduler.mapper;

import io.protone.scheduler.api.dto.SchAttachmentDTO;
import io.protone.scheduler.domain.SchAttachment;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Attachment and its DTO AttachmentDTO.
 */
@Mapper(componentModel = "spring", uses = {SchMediaItemMapper.class, SchEmissionMapper.class,})
public interface SchAttachmentMapper extends SchEntityMapper<SchAttachmentDTO, SchAttachment> {
}
