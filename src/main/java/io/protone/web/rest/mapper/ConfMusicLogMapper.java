package io.protone.web.rest.mapper;

import io.protone.custom.service.dto.ConfMusicLogPT;
import io.protone.domain.CfgExternalSystemLog;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for the entity CfgExternalSystemLog and its DTO CfgExternalSystemLogDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConfMusicLogMapper {

    ConfMusicLogPT DB2DTO(CfgExternalSystemLog cfgExternalSystemLog);

    List<ConfMusicLogPT> DBs2DTOs(List<CfgExternalSystemLog> cfgExternalSystemLogs);

    CfgExternalSystemLog DTO2DB(ConfMusicLogPT cfgExternalSystemLogDTO);

    List<CfgExternalSystemLog> DTOs2DBs(List<ConfMusicLogPT> cfgExternalSystemLogDTOs);
}
