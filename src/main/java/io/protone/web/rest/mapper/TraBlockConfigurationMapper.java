package io.protone.web.rest.mapper;

import io.protone.domain.CorNetwork;
import io.protone.domain.TraBlockConfiguration;
import io.protone.domain.TraDiscount;
import io.protone.domain.TraPlaylist;
import io.protone.web.rest.dto.traffic.TraBlockConfigurationDTO;
import io.protone.web.rest.dto.traffic.TraDiscountDTO;
import io.protone.web.rest.dto.traffic.TraPlaylistDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 15/05/2017.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TraBlockConfigurationMapper {

    TraBlockConfigurationDTO DB2DTO(TraBlockConfiguration traBlockConfiguration);

    List<TraBlockConfigurationDTO> DBs2DTOs(List<TraBlockConfiguration> traBlockConfigurations);

    TraBlockConfiguration DTO2DB(TraBlockConfigurationDTO traBlockConfigurationDTO, @Context CorNetwork network);

    default List<TraBlockConfiguration> DTOs2DBs(List<TraBlockConfigurationDTO> traBlockConfigurationDTOS, @Context CorNetwork network) {
        List<TraBlockConfiguration> traBlockConfigurations = new ArrayList<>();
        if (traBlockConfigurationDTOS.isEmpty() || traBlockConfigurationDTOS == null) {
            return null;
        }
        for (TraBlockConfigurationDTO dto : traBlockConfigurationDTOS) {
            traBlockConfigurations.add(DTO2DB(dto, network));
        }
        return traBlockConfigurations;
    }

    @AfterMapping
    default void traBlockConfigurationDTOToTraBlockConfigurationAfterMapping(TraBlockConfigurationDTO dto, @MappingTarget TraBlockConfiguration entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }
}
