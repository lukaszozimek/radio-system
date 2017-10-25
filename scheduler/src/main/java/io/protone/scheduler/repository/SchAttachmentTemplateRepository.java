package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchAttachmentTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lukaszozimek on 30/08/2017.
 */
public interface SchAttachmentTemplateRepository extends JpaRepository<SchAttachmentTemplate, Long> {
    void deleteAllByEmission_Id(Long emissionId);
}
