package io.protone.scheduler.mapper;

import io.protone.library.domain.LibAlbum;
import io.protone.library.domain.LibArtist;
import io.protone.library.domain.LibLibrary;
import io.protone.library.mapper.LibItemMapper;
import io.protone.scheduler.api.dto.SchAttachmentDTO;
import io.protone.scheduler.domain.SchAttachment;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for the entity Attachment and its DTO AttachmentDTO.
 */
@Mapper(componentModel = "spring", uses = {SchItemThinMapper.class})
public interface SchAttachmentMapper {

    SchAttachment toEntity(SchAttachmentDTO dto);

    SchAttachmentDTO toDto(SchAttachment entity);

    List<SchAttachment> toEntity(List<SchAttachmentDTO> dtoList);

    List<SchAttachmentDTO> toDto(List<SchAttachment> entityList);

    default String map(LibAlbum value) {
        return value.getName();
    }

    default String map(LibArtist value) {
        return value.getName();
    }
}
