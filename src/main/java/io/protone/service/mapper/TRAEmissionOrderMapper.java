package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.TRAEmissionOrderDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity TRAEmissionOrder and its DTO TRAEmissionOrderDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TRAEmissionOrderMapper {

    TRAEmissionOrderDTO tRAEmissionOrderToTRAEmissionOrderDTO(TRAEmissionOrder tRAEmissionOrder);

    List<TRAEmissionOrderDTO> tRAEmissionOrdersToTRAEmissionOrderDTOs(List<TRAEmissionOrder> tRAEmissionOrders);

    TRAEmissionOrder tRAEmissionOrderDTOToTRAEmissionOrder(TRAEmissionOrderDTO tRAEmissionOrderDTO);

    List<TRAEmissionOrder> tRAEmissionOrderDTOsToTRAEmissionOrders(List<TRAEmissionOrderDTO> tRAEmissionOrderDTOs);
}
