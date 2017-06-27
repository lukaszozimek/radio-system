package io.protone.application.web.rest.mapper;

import io.protone.custom.service.dto.ConfMusicLogPT;
import io.protone.domain.CfgExternalSystemLog;
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
public interface ConfMusicLogMapper {

    ConfMusicLogPT DB2DTO(CfgExternalSystemLog cfgExternalSystemLog);

    List<ConfMusicLogPT> DBs2DTOs(List<CfgExternalSystemLog> cfgExternalSystemLogs);

    CfgExternalSystemLog DTO2DB(ConfMusicLogPT cfgExternalSystemLogDTO, @Context CorNetwork corNetwork);


    default List<CfgExternalSystemLog> DTOs2DBs(List<ConfMusicLogPT> cfgExternalSystemLogDTOs, CorNetwork networkId) {
        List<CfgExternalSystemLog> cfgExternalSystemLogs = new ArrayList<>();
        if (cfgExternalSystemLogDTOs.isEmpty() || cfgExternalSystemLogDTOs == null) {
            return null;
        }
        for (ConfMusicLogPT dto : cfgExternalSystemLogDTOs) {
            cfgExternalSystemLogs.add(DTO2DB(dto, networkId));
        }
        return cfgExternalSystemLogs;
    }

    @AfterMapping
    default void confMusicLogPTToCfgExternalSystemLogAfterMapping(ConfMusicLogPT dto, @MappingTarget CfgExternalSystemLog entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }
}
