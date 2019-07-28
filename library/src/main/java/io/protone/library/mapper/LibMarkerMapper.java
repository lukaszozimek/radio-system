package io.protone.library.mapper;


import io.protone.core.domain.CorNetwork;
import io.protone.library.api.dto.LibMarkerDTO;
import io.protone.library.domain.LibMarker;
import io.protone.library.domain.LibMediaItem;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for the entity LibMarker and its DTO LibMarkerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LibMarkerMapper {

    @Mapping(source = "mediaItem.id", target = "mediaItemId")
    LibMarkerDTO DB2DTO(LibMarker lIBMarker);

    List<LibMarkerDTO> DBs2DTOs(List<LibMarker> lIBMarkers);

    @Mapping(source = "mediaItemId", target = "mediaItem")
    LibMarker DTO2DB(LibMarkerDTO lIBMarkerDTO, @Context CorNetwork network);

    default List<LibMarker> DTOs2DBs(List<LibMarkerDTO> lIBMarkerDTOs, @Context CorNetwork network) {
        List<LibMarker> corPeople = new ArrayList<>();
        if (lIBMarkerDTOs.isEmpty() || lIBMarkerDTOs == null) {
            return null;
        }
        for (LibMarkerDTO dto : lIBMarkerDTOs) {
            corPeople.add(DTO2DB(dto, network));
        }
        return corPeople;
    }

    @AfterMapping
    default void libMarkerPTToLibMarkerAfterMapping(LibMarkerDTO dto, @MappingTarget LibMarker entity, @Context CorNetwork corNetwork) {
       //  entity.setNetwork(corNetwork);
    }
    default LibMediaItem longToLibMediaItem (Long itemId){

        if(itemId ==null){
            return null;
        }
        LibMediaItem libMediaItem = new LibMediaItem();
        libMediaItem.setId(itemId);
        return libMediaItem;
    }


}
