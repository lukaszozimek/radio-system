package io.protone.traffic.mapper;


import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.traffic.api.dto.TraMediaPlanEmissionDTO;
import io.protone.traffic.domain.TraMediaPlanEmission;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 10/06/2017.
 */
@Mapper(componentModel = "spring",  uses ={TraMediaPlanBlockMapper.class, TraMediaPlanPlaylistDateMapper.class})
public interface TraMediaPlanEmissionMapper {


    TraMediaPlanEmissionDTO DB2DTO(TraMediaPlanEmission traMediaPlanEmission);

    List<TraMediaPlanEmissionDTO> DBs2DTOs(List<TraMediaPlanEmission> traMediaPlanEmissions);

    TraMediaPlanEmission DTO2DB(TraMediaPlanEmissionDTO traMediaPlanEmissionDTO, @Context CorNetwork network, @Context CorChannel corChannel);

    default List<TraMediaPlanEmission> DTOs2DBs(List<TraMediaPlanEmissionDTO> mediaPlanEmissionDTOS, @Context CorNetwork network, @Context CorChannel corChannel) {
        List<TraMediaPlanEmission> traOrders = new ArrayList<>();
        if (mediaPlanEmissionDTOS.isEmpty() || mediaPlanEmissionDTOS == null) {
            return null;
        }
        for (TraMediaPlanEmissionDTO dto : mediaPlanEmissionDTOS) {
            traOrders.add(DTO2DB(dto, network, corChannel));
        }
        return traOrders;
    }

    @AfterMapping
    default void traInvoicePTToTraMediaPlanEmissionAfterMapping(TraMediaPlanEmissionDTO dto, @MappingTarget TraMediaPlanEmission entity, @Context CorNetwork network, @Context CorChannel corChannel) {
        entity.setNetwork(network);
        entity.setChannel(corChannel);
    }

}
