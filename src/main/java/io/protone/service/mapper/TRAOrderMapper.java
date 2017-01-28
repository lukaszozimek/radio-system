package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.TRAOrderDTO;

import org.mapstruct.*;

import java.util.List;

/**
 * Mapper for the entity TRAOrder and its DTO TRAOrderDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TRAOrderMapper {

    TRAOrderDTO tRAOrderToTRAOrderDTO(TRAOrder tRAOrder);

    List<TRAOrderDTO> tRAOrdersToTRAOrderDTOs(List<TRAOrder> tRAOrders);

    TRAOrder tRAOrderDTOToTRAOrder(TRAOrderDTO tRAOrderDTO);

    List<TRAOrder> tRAOrderDTOsToTRAOrders(List<TRAOrderDTO> tRAOrderDTOs);

}
