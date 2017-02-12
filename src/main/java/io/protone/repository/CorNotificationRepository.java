package io.protone.repository;

import io.protone.domain.CorNotification;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CorNotification entity.
 */
@SuppressWarnings("unused")
public interface CorNotificationRepository extends JpaRepository<CorNotification,Long> {

}
