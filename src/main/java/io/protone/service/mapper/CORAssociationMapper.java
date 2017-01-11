package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CORAssociationDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CORAssociation and its DTO CORAssociationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CORAssociationMapper {

    @Mapping(source = "network.id", target = "networkId")
    CORAssociationDTO cORAssociationToCORAssociationDTO(CORAssociation cORAssociation);

    List<CORAssociationDTO> cORAssociationsToCORAssociationDTOs(List<CORAssociation> cORAssociations);

    @Mapping(source = "networkId", target = "network")
    CORAssociation cORAssociationDTOToCORAssociation(CORAssociationDTO cORAssociationDTO);

    List<CORAssociation> cORAssociationDTOsToCORAssociations(List<CORAssociationDTO> cORAssociationDTOs);

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
