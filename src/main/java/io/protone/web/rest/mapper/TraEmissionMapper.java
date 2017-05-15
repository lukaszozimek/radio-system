package io.protone.web.rest.mapper;

import io.protone.domain.CorNetwork;
import io.protone.domain.TraBlockConfiguration;
import io.protone.domain.TraDiscount;
import io.protone.domain.TraEmission;
import io.protone.web.rest.dto.traffic.TraBlockConfigurationDTO;
import io.protone.web.rest.dto.traffic.TraDiscountDTO;
import io.protone.web.rest.dto.traffic.TraEmissionDTO;
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
public interface TraEmissionMapper {

    TraEmissionDTO DB2DTO(TraEmission traEmission);

    List<TraEmissionDTO> DBs2DTOs(List<TraEmission> traEmissions);

    TraEmission DTO2DB(TraEmissionDTO traEmissionDTO, @Context CorNetwork network);

    default List<TraEmission> DTOs2DBs(List<TraEmissionDTO> traEmissionDTOS, @Context CorNetwork network) {
        List<TraEmission> traEmissions = new ArrayList<>();
        if (traEmissionDTOS.isEmpty() || traEmissionDTOS == null) {
            return null;
        }
        for (TraEmissionDTO dto : traEmissionDTOS) {
            traEmissions.add(DTO2DB(dto, network));
        }
        return traEmissions;
    }

    @AfterMapping
    default void traEmissionDTOToTraEmissionAfterMapping(TraEmissionDTO dto, @MappingTarget TraEmission entity, @Context CorNetwork corNetwork) {
        entity.setNetwork(corNetwork);
    }
}
