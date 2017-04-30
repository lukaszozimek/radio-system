package io.protone.web.rest.mapper;

import io.protone.custom.service.dto.ConfCommercialLogPT;
import io.protone.custom.service.dto.ConfMarkerConfigurationPT;
import io.protone.domain.CfgExternalSystemLog;
import io.protone.domain.CfgMarkerConfiguration;
import io.protone.domain.CorNetwork;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for the entity CfgExternalSystemLog and its DTO CfgExternalSystemLogDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConfCommercialLogMapper {

    ConfCommercialLogPT DB2DTO(CfgExternalSystemLog cfgExternalSystemLog);

    List<ConfCommercialLogPT> DBs2DTOs(List<CfgExternalSystemLog> cfgExternalSystemLogs);

    CfgExternalSystemLog DTO2DB(ConfCommercialLogPT cfgExternalSystemLogDTO, @Context CorNetwork corNetwork);

    default List<CfgExternalSystemLog> DTOs2DBs(List<ConfCommercialLogPT> cfgExternalSystemLogDTOs, CorNetwork networkId) {
        List<CfgExternalSystemLog> cfgExternalSystemLogs = new ArrayList<>();
        if (cfgExternalSystemLogDTOs.isEmpty() || cfgExternalSystemLogDTOs == null) {
            return null;
        }
        for (ConfCommercialLogPT dto : cfgExternalSystemLogDTOs) {
            cfgExternalSystemLogs.add(DTO2DB(dto, networkId));
        }
        return cfgExternalSystemLogs;
    }

    @AfterMapping
    default void confCommercialLogPTToCfgExternalSystemLogAfterMapping(ConfCommercialLogPT dto, @MappingTarget CfgExternalSystemLog entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }
}
