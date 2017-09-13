package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchEventEmission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lukaszozimek on 30/08/2017.
 */
public interface SchEventEmissionRepository extends JpaRepository<SchEventEmission, Long> {
}
