package io.protone.repository;

import io.protone.domain.CorSubscription;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CorSubscription entity.
 */
@SuppressWarnings("unused")
public interface CorSubscriptionRepository extends JpaRepository<CorSubscription,Long> {

}
