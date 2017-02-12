package io.protone.service.mapper;

import io.protone.domain.*;
import io.protone.service.dto.CorNotificationDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CorNotification and its DTO CorNotificationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CorNotificationMapper {

    @Mapping(source = "user.id", target = "userId")
    CorNotificationDTO corNotificationToCorNotificationDTO(CorNotification corNotification);

    List<CorNotificationDTO> corNotificationsToCorNotificationDTOs(List<CorNotification> corNotifications);

    @Mapping(source = "userId", target = "user")
    CorNotification corNotificationDTOToCorNotification(CorNotificationDTO corNotificationDTO);

    List<CorNotification> corNotificationDTOsToCorNotifications(List<CorNotificationDTO> corNotificationDTOs);

    default CorUser corUserFromId(Long id) {
        if (id == null) {
            return null;
        }
        CorUser corUser = new CorUser();
        corUser.setId(id);
        return corUser;
    }
}
