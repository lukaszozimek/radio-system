package io.protone.traffic.mapper;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.library.mapper.LibMediaItemThinMapper;
import io.protone.traffic.api.dto.TraEmissionDTO;
import io.protone.traffic.domain.TraEmission;
import io.protone.traffic.domain.TraOrder;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 15/05/2017.
 */
@Mapper(componentModel = "spring", uses = {TraAdvertisementMapper.class, LibMediaItemThinMapper.class})
public interface TraEmissionMapper {
    @Mapping(source = "order", target = "orderId")
    TraEmissionDTO DB2DTO(TraEmission traEmission);

    List<TraEmissionDTO> DBs2DTOs(List<TraEmission> traEmissions);

    @Mapping(source = "orderId", target = "order")
    TraEmission DTO2DB(TraEmissionDTO traEmissionDTO, @Context CorNetwork network, @Context CorChannel corChannel);

    default List<TraEmission> DTOs2DBs(List<TraEmissionDTO> traEmissionDTOS, @Context CorNetwork network, @Context CorChannel corChannel) {
        List<TraEmission> traEmissions = new ArrayList<>();
        if (traEmissionDTOS.isEmpty() || traEmissionDTOS == null) {
            return null;
        }
        for (TraEmissionDTO dto : traEmissionDTOS) {
            traEmissions.add(DTO2DB(dto, network, corChannel));
        }
        return traEmissions;
    }


    default TraOrder TraOrderFromLong(Long orderId) {
        TraOrder traOrder = new TraOrder();
        traOrder.setId(orderId);
        return traOrder;
    }

    default Long LongFromTraOrder(TraOrder traOrder) {
        if (traOrder == null) {
            return null;
        }
        return traOrder.getId();
    }

    @AfterMapping
    default void traEmissionDTOToTraEmissionAfterMapping(TraEmissionDTO dto, @MappingTarget TraEmission entity, @Context CorNetwork corNetwork, @Context CorChannel corChannel) {
        entity.setNetwork(corNetwork);
        entity.setChannel(corChannel);
    }
}
