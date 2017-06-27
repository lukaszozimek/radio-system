package io.protone.repository;

import io.protone.domain.CorPersistentAuditEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for the CorPersistentAuditEvent entity.
 */
public interface PersistenceAuditEventRepository extends JpaRepository<CorPersistentAuditEvent, Long> {

    List<CorPersistentAuditEvent> findByPrincipal(String principal);

    List<CorPersistentAuditEvent> findByAuditEventDateAfter(LocalDateTime after);

    List<CorPersistentAuditEvent> findByPrincipalAndAuditEventDateAfter(String principal, LocalDateTime after);

    List<CorPersistentAuditEvent> findByPrincipalAndAuditEventDateAfterAndAuditEventType(String principle, LocalDateTime after, String type);

    Page<CorPersistentAuditEvent> findAllByAuditEventDateBetween(LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable);
}
