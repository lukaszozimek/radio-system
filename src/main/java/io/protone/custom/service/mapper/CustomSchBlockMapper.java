package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.SchBlockPT;
import io.protone.domain.SchBlock;
import io.protone.domain.SchPlaylist;
import io.protone.domain.SchTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity SchBlock and its DTO SchBlockDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomSchBlockMapper {

    @Mapping(source = "parentBlock.id", target = "parentBlockId")
    SchBlockPT DB2DTO(SchBlock schBlock);

    List<SchBlockPT> DBs2DTOs(List<SchBlock> schBlocks);

    @Mapping(source = "parentBlockId", target = "parentBlock")
    SchBlock DTO2DB(SchBlockPT schBlockDTO);

    List<SchBlock> DTOs2DBs(List<SchBlockPT> schBlockDTOs);

    default SchPlaylist mapSchPlaylist(Long id) {
        if (id == null) {
            return null;
        }
        SchPlaylist schPlaylist = new SchPlaylist();
        schPlaylist.setId(id);
        return schPlaylist;
    }

    default SchTemplate mapSchTemplate(Long id) {
        if (id == null) {
            return null;
        }
        SchTemplate schTemplate = new SchTemplate();
        schTemplate.setId(id);
        return schTemplate;
    }

    default SchBlock mapSchBlock(Long id) {
        if (id == null) {
            return null;
        }
        SchBlock schBlock = new SchBlock();
        schBlock.setId(id);
        return schBlock;
    }
}
