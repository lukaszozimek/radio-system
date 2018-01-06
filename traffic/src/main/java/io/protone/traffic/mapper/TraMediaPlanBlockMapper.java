package io.protone.traffic.mapper;


import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.traffic.api.dto.TraMediaPlanBlockDTO;
import io.protone.traffic.domain.TraMediaPlanBlock;
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
public interface TraMediaPlanBlockMapper {


    TraMediaPlanBlockDTO DB2DTO(TraMediaPlanBlock traMediaPlanBlock);

    List<TraMediaPlanBlockDTO> DBs2DTOs(List<TraMediaPlanBlock> traInvtraMediaPlanBlocksices);

    TraMediaPlanBlock DTO2DB(TraMediaPlanBlockDTO traMediaPlanBlockDTO, @Context CorChannel corChannel);

    default List<TraMediaPlanBlock> DTOs2DBs(List<TraMediaPlanBlockDTO> traMediaPlanBlockDTOS, @Context CorChannel corChannel) {
        List<TraMediaPlanBlock> traOrders = new ArrayList<>();
        if (traMediaPlanBlockDTOS.isEmpty() || traMediaPlanBlockDTOS == null) {
            return null;
        }
        for (TraMediaPlanBlockDTO dto : traMediaPlanBlockDTOS) {
            traOrders.add(DTO2DB(dto, corChannel));
        }
        return traOrders;
    }

    @AfterMapping
    default void traInvoicePTToTraMediaPlanBlockAfterMapping(TraMediaPlanBlockDTO dto, @MappingTarget TraMediaPlanBlock entity, @Context CorChannel corChannel) {
        entity.setChannel(corChannel);
    }

}
