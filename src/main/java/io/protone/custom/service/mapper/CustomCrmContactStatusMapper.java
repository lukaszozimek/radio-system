package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.ConfCrmContactStatusPT;
import io.protone.custom.service.dto.ConfCrmTaskStatusPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmContactStatus;
import io.protone.domain.CrmTaskStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity CorRange and its DTO CorRangeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomCrmContactStatusMapper {

    @Mapping(source = "networkId", target = "network")
    CrmContactStatus DTO2DB(ConfCrmContactStatusPT confCrmTaskStatusPT);

    @Mapping(source = "network.id", target = "networkId")
    ConfCrmContactStatusPT DB2DTO(CrmContactStatus crmTaskStatus);

    List<ConfCrmContactStatusPT> DBs2DTOs(List<CrmContactStatus> crmTaskStatuses);

    List<CrmContactStatus> DTOs2DBs(List<ConfCrmContactStatusPT> confCrmTaskStatusPTS);

    default CorNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork cORNetwork = new CorNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
