package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.LibItemPT;
import io.protone.custom.service.dto.SchEmissionPT;
import io.protone.domain.LibMediaItem;
import io.protone.domain.SchBlock;
import io.protone.domain.SchEmission;
import io.protone.domain.TraCampaign;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity SchEmission and its DTO SchEmissionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomSchEmissionMapperV2 {

    @Mapping(source = "block.id", target = "blockId")
    @Mapping(source = "campaings.id", target = "campaingsId")
    SchEmissionPT DB2DTO(SchEmission schEmission);

    List<SchEmissionPT> DBs2DTOs(List<SchEmission> schEmissions);

    @Mapping(source = "blockId", target = "block")
    @Mapping(source = "campaingsId", target = "campaings")
    SchEmission DTO2DB(SchEmissionPT schEmissionDTO);

    List<SchEmission> DTOs2DBs(List<SchEmissionPT> schEmissionDTOs);

    default LibItemPT mapLibItemPT(LibMediaItem value) {

        //TODO: Implement mapper
        return null;
    }

    default LibMediaItem mapLibMediaItem(LibItemPT value) {

        //TODO: Implement mapper
        return null;
    }

    default LibMediaItem mapLibMediaItem(Long id) {
        if (id == null) {
            return null;
        }
        LibMediaItem libMediaItem = new LibMediaItem();
        libMediaItem.setId(id);
        return libMediaItem;
    }

    default SchBlock mapSchBlock(Long id) {
        if (id == null) {
            return null;
        }
        SchBlock schBlock = new SchBlock();
        schBlock.setId(id);
        return schBlock;
    }

    default TraCampaign mapTraCampaign(Long id) {
        if (id == null) {
            return null;
        }
        TraCampaign traCampaign = new TraCampaign();
        traCampaign.setId(id);
        return traCampaign;
    }
}
