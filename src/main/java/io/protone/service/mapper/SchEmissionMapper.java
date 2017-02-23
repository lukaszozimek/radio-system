package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.SchEmissionDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity SchEmission and its DTO SchEmissionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SchEmissionMapper {

    @Mapping(source = "mediaItem.id", target = "mediaItemId")
    @Mapping(source = "block.id", target = "blockId")
    @Mapping(source = "campaings.id", target = "campaingsId")
    SchEmissionDTO schEmissionToSchEmissionDTO(SchEmission schEmission);

    List<SchEmissionDTO> schEmissionsToSchEmissionDTOs(List<SchEmission> schEmissions);

    @Mapping(source = "mediaItemId", target = "mediaItem")
    @Mapping(source = "blockId", target = "block")
    @Mapping(source = "campaingsId", target = "campaings")
    SchEmission schEmissionDTOToSchEmission(SchEmissionDTO schEmissionDTO);

    List<SchEmission> schEmissionDTOsToSchEmissions(List<SchEmissionDTO> schEmissionDTOs);

    default LibMediaItem libMediaItemFromId(Long id) {
        if (id == null) {
            return null;
        }
        LibMediaItem libMediaItem = new LibMediaItem();
        libMediaItem.setId(id);
        return libMediaItem;
    }

    default SchBlock schBlockFromId(Long id) {
        if (id == null) {
            return null;
        }
        SchBlock schBlock = new SchBlock();
        schBlock.setId(id);
        return schBlock;
    }

    default TraCampaign traCampaignFromId(Long id) {
        if (id == null) {
            return null;
        }
        TraCampaign traCampaign = new TraCampaign();
        traCampaign.setId(id);
        return traCampaign;
    }
}
