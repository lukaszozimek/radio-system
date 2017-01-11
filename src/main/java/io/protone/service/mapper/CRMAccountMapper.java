package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CRMAccountDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CRMAccount and its DTO CRMAccountDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CRMAccountMapper {

    @Mapping(source = "network.id", target = "networkId")
    CRMAccountDTO cRMAccountToCRMAccountDTO(CRMAccount cRMAccount);

    List<CRMAccountDTO> cRMAccountsToCRMAccountDTOs(List<CRMAccount> cRMAccounts);

    @Mapping(source = "networkId", target = "network")
    CRMAccount cRMAccountDTOToCRMAccount(CRMAccountDTO cRMAccountDTO);

    List<CRMAccount> cRMAccountDTOsToCRMAccounts(List<CRMAccountDTO> cRMAccountDTOs);

    default CORNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CORNetwork cORNetwork = new CORNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
