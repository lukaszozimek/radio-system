package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.ConfTraAdvertismentTypePT;
import io.protone.custom.service.dto.ConfTraOrderStatusPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.TraAdvertisement;
import io.protone.domain.TraAdvertismentType;
import io.protone.domain.TraOrderStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity CorRange and its DTO CorRangeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomTraAdvertismentTypeMapper {

    TraAdvertismentType DTO2DB(ConfTraAdvertismentTypePT confCrmTaskStatusPT);


    ConfTraAdvertismentTypePT DB2DTO(TraAdvertismentType crmTaskStatus);

    List<ConfTraAdvertismentTypePT> DBs2DTOs(List<TraAdvertismentType> crmTaskStatuses);

    List<TraAdvertismentType> DTOs2DBs(List<ConfTraAdvertismentTypePT> confCrmTaskStatusPTS);

    default CorNetwork cORNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork cORNetwork = new CorNetwork();
        cORNetwork.setId(id);
        return cORNetwork;
    }
}
