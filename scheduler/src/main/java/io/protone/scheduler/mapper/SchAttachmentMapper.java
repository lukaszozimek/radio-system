package io.protone.scheduler.mapper;

import io.protone.scheduler.api.dto.SchAttachmentDTO;
import io.protone.scheduler.domain.SchAttachment;
import org.mapstruct.Mapper;
import org.mapstruct.ValueMapping;
import org.mapstruct.ValueMappings;

/**
 * Mapper for the entity Attachment and its DTO AttachmentDTO.
 */
@Mapper(componentModel = "spring", uses = {SchMediaItemMapper.class, SchEmissionMapper.class, })
public interface SchAttachmentMapper extends SchEntityMapper<SchAttachmentDTO, SchAttachment> {

    default SchAttachment fromId(Long id) {
        if (id == null) {
            return null;
        }
        SchAttachment attachment = new SchAttachment();
        attachment.setId(id);
        return attachment;
    }

    @ValueMappings({
            @ValueMapping(source = "AT_VOICE_TRACK", target = "VOICE_TRACK"),
            @ValueMapping(source = "AT_OTHER", target = "OTHER")
    })
    SchAttachmentDTO.AttachmentTypeEnum map(io.protone.scheduler.domain.enumeration.AttachmentTypeEnum source);

    @ValueMappings({
            @ValueMapping(source = "VOICE_TRACK", target = "AT_VOICE_TRACK"),
            @ValueMapping(source = "OTHER", target = "AT_OTHER")
    })
    io.protone.scheduler.domain.enumeration.AttachmentTypeEnum map(SchAttachmentDTO.AttachmentTypeEnum source);

    @ValueMappings({
            @ValueMapping(source = "FT_LINEAR", target = "LINEAR"),
            @ValueMapping(source = "FT_OTHER", target = "OTHER"),
            @ValueMapping(source = "FT_LOGARITHMIC", target = "LOGARITHMIC")
    })
    SchAttachmentDTO.FadeTypeEnum map(io.protone.scheduler.domain.enumeration.FadeTypeEnum source);

    @ValueMappings({
            @ValueMapping(source = "LINEAR", target = "FT_LINEAR"),
            @ValueMapping(source = "LOGARITHMIC", target = "FT_LOGARITHMIC"),
            @ValueMapping(source = "OTHER", target = "FT_OTHER")
    })
    io.protone.scheduler.domain.enumeration.FadeTypeEnum map(SchAttachmentDTO.FadeTypeEnum source);

}
