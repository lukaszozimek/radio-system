package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchEventEmissionAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lukaszozimek on 30/08/2017.
 */
public interface SchEventEmissionAttachmentRepository extends JpaRepository<SchEventEmissionAttachment, Long> {
}
