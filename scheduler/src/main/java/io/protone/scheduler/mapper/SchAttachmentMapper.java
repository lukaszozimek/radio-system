package io.protone.scheduler.mapper;

import io.protone.library.api.dto.LibAlbumDTO;
import io.protone.library.domain.enumeration.LibAlbumTypeEnum;
import io.protone.library.mapper.LibItemMapper;
import io.protone.scheduler.api.dto.SchAttachmentDTO;
import io.protone.scheduler.domain.*;

import io.protone.scheduler.domain.enumeration.FadeTypeEnum;
import org.mapstruct.*;

/**
 * Mapper for the entity Attachment and its DTO AttachmentDTO.
 */
@Mapper(componentModel = "spring", uses = {LibItemMapper.class, SchEmissionMapper.class, })
public interface SchAttachmentMapper extends SchEntityMapper<SchAttachmentDTO, SchAttachment> {

    SchAttachmentDTO toDto(SchAttachment attachment);

    SchAttachment toEntity(SchAttachmentDTO attachmentDTO);
    default SchAttachment fromId(Long id) {
        if (id == null) {
            return null;
        }
        SchAttachment attachment = new SchAttachment();
        attachment.setId(id);
        return attachment;
    }

    default SchAttachmentDTO.FadeTypeEnum mapFadeTypeEnum(FadeTypeEnum value) {

        if (value.compareTo(FadeTypeEnum.FT_LINEAR) == 0)
            return SchAttachmentDTO.FadeTypeEnum.LINEAR;
        else if (value.compareTo(FadeTypeEnum.FT_LOGARITMIC) == 0)
            return SchAttachmentDTO.FadeTypeEnum.LOGARITMIC;
        else
            return SchAttachmentDTO.FadeTypeEnum.OTHER;
    }

    default FadeTypeEnum mapFadeTypeEnum(SchAttachmentDTO.FadeTypeEnum value) {
        if (value.compareTo(SchAttachmentDTO.FadeTypeEnum.LINEAR) == 0)
            return FadeTypeEnum.FT_LINEAR;
        else if (value.compareTo(SchAttachmentDTO.FadeTypeEnum.LOGARITMIC) == 0)
            return FadeTypeEnum.FT_LOGARITMIC;
        else
            return FadeTypeEnum.FT_OTHER;
    }
}
