package io.protone.web.rest.mapper;

import io.protone.custom.service.dto.ConfCommercialLogPT;
import io.protone.domain.CfgExternalSystemLog;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for the entity CfgExternalSystemLog and its DTO CfgExternalSystemLogDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConfCommercialLogMapper {

    ConfCommercialLogPT DB2DTO(CfgExternalSystemLog cfgExternalSystemLog);

    List<ConfCommercialLogPT> DBs2DTOs(List<CfgExternalSystemLog> cfgExternalSystemLogs);

    CfgExternalSystemLog DTO2DB(ConfCommercialLogPT cfgExternalSystemLogDTO);

    List<CfgExternalSystemLog> DTOs2DBs(List<ConfCommercialLogPT> cfgExternalSystemLogDTOs);
}
