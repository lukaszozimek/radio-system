package io.protone.web.rest.mapper;

import io.protone.domain.*;
import io.protone.web.rest.dto.traffic.TraBlockConfigurationDTO;
import io.protone.web.rest.dto.traffic.TraBlockDTO;
import io.protone.web.rest.dto.traffic.TraDiscountDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 15/05/2017.
 */
@Mapper(componentModel = "spring", uses = {TraEmissionMapper.class})
public interface TraBlockMapper {

    TraBlockDTO DB2DTO(TraBlock traBlock);

    List<TraBlockDTO> DBs2DTOs(List<TraBlock> traBlocks);

    TraBlock DTO2DB(TraBlockDTO traDiscountDTO, @Context CorNetwork network, @Context CorChannel corChannel);

    default List<TraBlock> DTOs2DBs(List<TraBlockDTO> traBlockDTOS, @Context CorNetwork network, @Context CorChannel corChannel) {
        List<TraBlock> traBlocks = new ArrayList<>();
        if (traBlockDTOS.isEmpty() || traBlockDTOS == null) {
            return null;
        }
        for (TraBlockDTO dto : traBlockDTOS) {
            traBlocks.add(DTO2DB(dto, network,corChannel));
        }
        return traBlocks;
    }

    @AfterMapping
    default void traBlockDTOToTraBlockAfterMapping(TraBlockDTO dto, @MappingTarget TraBlock entity, @Context CorNetwork corNetwork, @Context CorChannel corChannel) {
        entity.setNetwork(corNetwork);
        entity.setChannel(corChannel);
    }
}
