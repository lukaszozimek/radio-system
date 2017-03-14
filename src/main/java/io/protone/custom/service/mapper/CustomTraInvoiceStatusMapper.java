package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.ConfCrmTaskStatusPT;
import io.protone.custom.service.dto.ConfInvoiceStatusPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmTaskStatus;
import io.protone.domain.TraInvoiceStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity CorRange and its DTO CorRangeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomTraInvoiceStatusMapper {
/*
    @Mapping(source = "networkId", target = "network")
    TraInvoiceStatus DTO2DB(ConfInvoiceStatusPT confCrmTaskStatusPT);

    @Mapping(source = "network.id", target = "networkId")
    ConfInvoiceStatusPT DB2DTO(TraInvoiceStatus crmTaskStatus);

    List<ConfInvoiceStatusPT> DBs2DTOs(List<TraInvoiceStatus> crmTaskStatuses);

    List<TraInvoiceStatus> DTOs2DBs(List<ConfInvoiceStatusPT> confCrmTaskStatusPTS);

    default CorNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork cORNetwork = new CorNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
    */
}
