package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.ConfCampaingStatusPT;
import io.protone.custom.service.dto.ConfTraOrderStatusPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.TraCampaingStatus;
import io.protone.domain.TraOrderStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity CorRange and its DTO CorRangeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomTraCampaingStatusMapper {

    @Mapping(source = "networkId", target = "network")
    TraCampaingStatus DTO2DB(ConfCampaingStatusPT confCrmTaskStatusPT);

    @Mapping(source = "network.id", target = "networkId")
    ConfCampaingStatusPT DB2DTO(TraCampaingStatus crmTaskStatus);

    List<ConfCampaingStatusPT> DBs2DTOs(List<TraCampaingStatus> crmTaskStatuses);

    List<TraCampaingStatus> DTOs2DBs(List<ConfCampaingStatusPT> confCrmTaskStatusPTS);

    default CorNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork cORNetwork = new CorNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
