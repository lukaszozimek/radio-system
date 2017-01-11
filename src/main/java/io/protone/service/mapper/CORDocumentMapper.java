package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CORDocumentDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CORDocument and its DTO CORDocumentDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CORDocumentMapper {

    @Mapping(source = "network.id", target = "networkId")
    CORDocumentDTO cORDocumentToCORDocumentDTO(CORDocument cORDocument);

    List<CORDocumentDTO> cORDocumentsToCORDocumentDTOs(List<CORDocument> cORDocuments);

    @Mapping(source = "networkId", target = "network")
    CORDocument cORDocumentDTOToCORDocument(CORDocumentDTO cORDocumentDTO);

    List<CORDocument> cORDocumentDTOsToCORDocuments(List<CORDocumentDTO> cORDocumentDTOs);

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
