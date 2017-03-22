package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.ConfCommercialLogPT;
import io.protone.custom.service.dto.ConfMusicLogPT;
import io.protone.domain.CfgExternalSystemLog;
import io.protone.domain.CorChannel;
import io.protone.domain.CorNetwork;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity CfgExternalSystemLog and its DTO CfgExternalSystemLogDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomConfCommercialLogMapper {

    @Mapping(source = "network.id", target = "networkId")
    @Mapping(source = "channel.id", target = "channelId")
    ConfCommercialLogPT DB2DTO(CfgExternalSystemLog cfgExternalSystemLog);

    List<ConfCommercialLogPT> DBs2DTOs(List<CfgExternalSystemLog> cfgExternalSystemLogs);

    @Mapping(source = "networkId", target = "network")
    @Mapping(source = "channelId", target = "channel")
    CfgExternalSystemLog DTO2DB(ConfCommercialLogPT cfgExternalSystemLogDTO);

    List<CfgExternalSystemLog> DTOs2DBs(List<ConfCommercialLogPT> cfgExternalSystemLogDTOs);

    default CorNetwork corNetworkFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorNetwork corNetwork = new CorNetwork();
        corNetwork.setId(id);
        return corNetwork;
    }

    default CorChannel corChannelFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorChannel corChannel = new CorChannel();
        corChannel.setId(id);
        return corChannel;
    }
}
