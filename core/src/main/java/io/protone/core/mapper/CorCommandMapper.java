package io.protone.core.mapper;


import io.protone.core.api.dto.CorChannelDTO;
import io.protone.core.api.dto.CorCommandDTO;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorCommand;
import io.protone.core.domain.CorNetwork;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {})
public interface CorCommandMapper {
    @Mapping(source = "corImageItem.publicUrl", target = "publicUrl")
    CorChannelDTO DB2DTO(CorChannel cORAddress);

    List<CorCommandDTO> DBs2DTOs(List<CorCommand> corCommands);

    List<CorCommandDTO> DBs2DTOs(Set<CorCommand> corCommands);

    CorCommand DTO2DB(CorCommandDTO corCommandDTO, @Context CorNetwork corNetwork);

    default List<CorCommand> DTOs2DBs(List<CorCommandDTO> corCommandDTOS, CorNetwork networkId) {
        List<CorCommand> corAddresses = new ArrayList<>();
        if (corCommandDTOS.isEmpty() || corCommandDTOS == null) {
            return null;
        }
        for (CorCommandDTO dto : corCommandDTOS) {
            corAddresses.add(DTO2DB(dto, networkId));
        }
        return corAddresses;
    }

    default Set<CorCommand> DTOs2DBsSet(List<CorCommandDTO> corCommandDTOS, CorNetwork networkId) {
        Set<CorCommand> corAddresses = new HashSet<>();
        if (corCommandDTOS.isEmpty() || corCommandDTOS == null) {
            return null;
        }
        for (CorCommandDTO dto : corCommandDTOS) {
            corAddresses.add(DTO2DB(dto, networkId));
        }
        return corAddresses;
    }

    @AfterMapping
    default void coreChannelPTToCorChannelAfterMapping(CorCommandDTO dto, @MappingTarget CorChannel entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }
}
