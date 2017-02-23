package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.SchBlockDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity SchBlock and its DTO SchBlockDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SchBlockMapper {

    @Mapping(source = "playlist.id", target = "playlistId")
    @Mapping(source = "template.id", target = "templateId")
    @Mapping(source = "parentBlock.id", target = "parentBlockId")
    SchBlockDTO schBlockToSchBlockDTO(SchBlock schBlock);

    List<SchBlockDTO> schBlocksToSchBlockDTOs(List<SchBlock> schBlocks);

    @Mapping(source = "playlistId", target = "playlist")
    @Mapping(source = "templateId", target = "template")
    @Mapping(source = "parentBlockId", target = "parentBlock")
    SchBlock schBlockDTOToSchBlock(SchBlockDTO schBlockDTO);

    List<SchBlock> schBlockDTOsToSchBlocks(List<SchBlockDTO> schBlockDTOs);

    default SchPlaylist schPlaylistFromId(Long id) {
        if (id == null) {
            return null;
        }
        SchPlaylist schPlaylist = new SchPlaylist();
        schPlaylist.setId(id);
        return schPlaylist;
    }

    default SchTemplate schTemplateFromId(Long id) {
        if (id == null) {
            return null;
        }
        SchTemplate schTemplate = new SchTemplate();
        schTemplate.setId(id);
        return schTemplate;
    }

    default SchBlock schBlockFromId(Long id) {
        if (id == null) {
            return null;
        }
        SchBlock schBlock = new SchBlock();
        schBlock.setId(id);
        return schBlock;
    }
}
