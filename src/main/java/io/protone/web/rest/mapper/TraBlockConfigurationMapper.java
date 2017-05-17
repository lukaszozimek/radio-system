package io.protone.web.rest.mapper;

import io.protone.domain.*;
import io.protone.web.rest.dto.traffic.TraBlockConfigurationDTO;
import io.protone.web.rest.dto.traffic.TraDiscountDTO;
import io.protone.web.rest.dto.traffic.TraPlaylistDTO;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 15/05/2017.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TraBlockConfigurationMapper {

    @Mapping(source = "day", target = "day")
    TraBlockConfigurationDTO DB2DTO(TraBlockConfiguration traBlockConfiguration);

    List<TraBlockConfigurationDTO> DBs2DTOs(List<TraBlockConfiguration> traBlockConfigurations);

    @Mapping(source = "day", target = "day")
    TraBlockConfiguration DTO2DB(TraBlockConfigurationDTO traBlockConfigurationDTO, @Context CorNetwork network, @Context CorChannel corChannel);

    default List<TraBlockConfiguration> DTOs2DBs(List<TraBlockConfigurationDTO> traBlockConfigurationDTOS, @Context CorNetwork network, @Context CorChannel corChannel) {
        List<TraBlockConfiguration> traBlockConfigurations = new ArrayList<>();
        if (traBlockConfigurationDTOS.isEmpty() || traBlockConfigurationDTOS == null) {
            return null;
        }
        for (TraBlockConfigurationDTO dto : traBlockConfigurationDTOS) {
            traBlockConfigurations.add(DTO2DB(dto, network, corChannel));
        }
        return traBlockConfigurations;
    }

    @AfterMapping
    default void traBlockConfigurationDTOToTraBlockConfigurationAfterMapping(TraBlockConfigurationDTO dto, @MappingTarget TraBlockConfiguration entity, @Context CorNetwork corNetwork, @Context CorChannel corChannel) {
        entity.setNetwork(corNetwork);
        entity.setChannel(corChannel);
    }
}
