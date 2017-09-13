package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchAttachmentConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lukaszozimek on 30/08/2017.
 */
public interface SchAttachmentConfigurationRepository extends JpaRepository<SchAttachmentConfiguration, Long> {
    void deleteAllByEmission_Id(Long emissionId);
}
