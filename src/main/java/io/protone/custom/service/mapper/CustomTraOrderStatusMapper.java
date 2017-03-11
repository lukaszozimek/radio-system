package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.ConfInvoiceStatusPT;
import io.protone.custom.service.dto.ConfTraOrderStatusPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.TraInvoiceStatus;
import io.protone.domain.TraOrderStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity CorRange and its DTO CorRangeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomTraOrderStatusMapper {

    @Mapping(source = "networkId", target = "network")
    TraOrderStatus DTO2DB(ConfTraOrderStatusPT confCrmTaskStatusPT);

    @Mapping(source = "network.id", target = "networkId")
    ConfTraOrderStatusPT DB2DTO(TraOrderStatus crmTaskStatus);

    List<ConfTraOrderStatusPT> DBs2DTOs(List<TraOrderStatus> crmTaskStatuses);

    List<TraOrderStatus> DTOs2DBs(List<ConfTraOrderStatusPT> confCrmTaskStatusPTS);

    default CorNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork cORNetwork = new CorNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
