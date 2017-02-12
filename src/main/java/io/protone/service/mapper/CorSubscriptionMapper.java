package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CorSubscriptionDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CorSubscription and its DTO CorSubscriptionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CorSubscriptionMapper {

    CorSubscriptionDTO corSubscriptionToCorSubscriptionDTO(CorSubscription corSubscription);

    List<CorSubscriptionDTO> corSubscriptionsToCorSubscriptionDTOs(List<CorSubscription> corSubscriptions);

    CorSubscription corSubscriptionDTOToCorSubscription(CorSubscriptionDTO corSubscriptionDTO);

    List<CorSubscription> corSubscriptionDTOsToCorSubscriptions(List<CorSubscriptionDTO> corSubscriptionDTOs);
}
