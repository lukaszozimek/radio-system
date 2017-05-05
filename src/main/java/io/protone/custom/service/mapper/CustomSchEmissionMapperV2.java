package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.SchEmissionPT;
import io.protone.custom.service.dto.thin.SchLibItemThinPT;
import io.protone.domain.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity SchEmission and its DTO SchEmissionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomSchEmissionMapperV2 {

    @Mapping(source = "block.id", target = "blockId")
    SchEmissionPT DB2DTO(SchEmission schEmission);

    List<SchEmissionPT> DBs2DTOs(List<SchEmission> schEmissions);

    @Mapping(source = "blockId", target = "block")
    SchEmission DTO2DB(SchEmissionPT schEmissionDTO);

    List<SchEmission> DTOs2DBs(List<SchEmissionPT> schEmissionDTOs);

    default SchLibItemThinPT mapLibItemPT(LibMediaItem value) {

        //TODO: Implement mapper
        return null;
    }

    default LibMediaItem mapLibMediaItem(SchLibItemThinPT value) {

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

    default TraOrder mapTraCampaign(Long id) {
        if (id == null) {
            return null;
        }
        TraOrder traOrder = new TraOrder();
        traOrder.setId(id);
        return traOrder;
    }
}
