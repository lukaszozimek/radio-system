package io.protone.traffic.mapper;

import io.protone.core.domain.CorChannel;
import io.protone.traffic.api.dto.TraPriceDTO;
import io.protone.traffic.domain.TraPrice;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 28/06/2017.
 */
@Mapper(componentModel = "spring")
public interface TraPriceMapper {

    TraPriceDTO DB2DTO(TraPrice traPrice);

    List<TraPriceDTO> DBs2DTOs(List<TraPrice> traOrders);

    TraPrice DTO2DB(TraPriceDTO traPriceDTO, @Context CorChannel network);

    default List<TraPrice> DTOs2DBs(List<TraPriceDTO> dtos, @Context CorChannel networkId) {
        List<TraPrice> corPeople = new ArrayList<>();
        if (dtos.isEmpty() || dtos == null) {
            return null;
        }
        for (TraPriceDTO dto : dtos) {
            corPeople.add(DTO2DB(dto, networkId));
        }
        return corPeople;
    }


    @AfterMapping
    default void traPriceDTOToTraPriceAfterMapping(TraPriceDTO dto, @MappingTarget TraPrice entity, @Context CorChannel corNetwork) {
        entity.setChannel(corNetwork);
    }
}
