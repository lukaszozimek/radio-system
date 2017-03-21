package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.ConfCrmTaskStatusPT;
import io.protone.custom.service.dto.ConfCurrencyPT;
import io.protone.domain.CorCurrency;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmTaskStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity CorRange and its DTO CorRangeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomCrmTaskStatusMapper {

    @Mapping(source = "networkId", target = "network")
    CrmTaskStatus DTO2DB(ConfCrmTaskStatusPT confCrmTaskStatusPT);

    @Mapping(source = "network.id", target = "networkId")
    ConfCrmTaskStatusPT DB2DTO(CrmTaskStatus crmTaskStatus);

    List<ConfCrmTaskStatusPT> DBs2DTOs(List<CrmTaskStatus> crmTaskStatuses);

    List<CrmTaskStatus> DTOs2DBs(List<ConfCrmTaskStatusPT> confCrmTaskStatusPTS);

    default CorNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork cORNetwork = new CorNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
