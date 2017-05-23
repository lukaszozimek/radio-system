package io.protone.web.rest.mapper;

import io.protone.domain.*;
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
@Mapper(componentModel = "spring", uses = {TraAdvertisementMapper.class})
public interface TraEmissionMapper {

    TraEmissionDTO DB2DTO(TraEmission traEmission);

    List<TraEmissionDTO> DBs2DTOs(List<TraEmission> traEmissions);

    TraEmission DTO2DB(TraEmissionDTO traEmissionDTO, @Context CorNetwork network, @Context CorChannel corChannel);

    default List<TraEmission> DTOs2DBs(List<TraEmissionDTO> traEmissionDTOS, @Context CorNetwork network, @Context CorChannel corChannel) {
        List<TraEmission> traEmissions = new ArrayList<>();
        if (traEmissionDTOS.isEmpty() || traEmissionDTOS == null) {
            return null;
        }
        for (TraEmissionDTO dto : traEmissionDTOS) {
            traEmissions.add(DTO2DB(dto, network,corChannel));
        }
        return traEmissions;
    }

    @AfterMapping
    default void traEmissionDTOToTraEmissionAfterMapping(TraEmissionDTO dto, @MappingTarget TraEmission entity, @Context CorNetwork corNetwork, @Context CorChannel corChannel) {
        entity.setNetwork(corNetwork);
        entity.setChannel(corChannel);
    }
}
