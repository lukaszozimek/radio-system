package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * An Attachment repository.
 */
public interface SchAttachmentRepository extends JpaRepository<SchAttachment, Long> {
}