package io.protone.traffic.mapper;


import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.traffic.api.dto.TraMediaPlanPlaylistDateDTO;
import io.protone.traffic.domain.TraMediaPlanPlaylistDate;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 10/06/2017.
 */
@Mapper(componentModel = "spring")
public interface TraMediaPlanPlaylistDateMapper {


    TraMediaPlanPlaylistDateDTO DB2DTO(TraMediaPlanPlaylistDate mediaPlanPlaylistDate);

    List<TraMediaPlanPlaylistDateDTO> DBs2DTOs(List<TraMediaPlanPlaylistDate> traMediaPlanPlaylistDates);

    TraMediaPlanPlaylistDate DTO2DB(TraMediaPlanPlaylistDateDTO traMediaPlanPlaylistDateDTO, @Context CorNetwork network, @Context CorChannel corChannel);

    default List<TraMediaPlanPlaylistDate> DTOs2DBs(List<TraMediaPlanPlaylistDateDTO> traMediaPlanPlaylistDateDTOS, @Context CorNetwork network, @Context CorChannel corChannel) {
        List<TraMediaPlanPlaylistDate> traOrders = new ArrayList<>();
        if (traMediaPlanPlaylistDateDTOS.isEmpty() || traMediaPlanPlaylistDateDTOS == null) {
            return null;
        }
        for (TraMediaPlanPlaylistDateDTO dto : traMediaPlanPlaylistDateDTOS) {
            traOrders.add(DTO2DB(dto, network, corChannel));
        }
        return traOrders;
    }

    @AfterMapping
    default void traInvoicePTToTraMediaPlanPlaylistDateAfterMapping(TraMediaPlanPlaylistDateDTO dto, @MappingTarget TraMediaPlanPlaylistDate entity, @Context CorNetwork network, @Context CorChannel corChannel) {
        entity.setNetwork(network);
        entity.setChannel(corChannel);
    }

}
