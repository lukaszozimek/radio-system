package io.protone.core.repository;


import io.protone.core.domain.CorPersistentAuditEvent;
import io.protone.core.service.AuditEventConverter;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * An implementation of Spring Boot's AuditEventRepository.
 */
@Repository
public class CustomAuditEventRepository implements AuditEventRepository {

    private static final String AUTHORIZATION_FAILURE = "AUTHORIZATION_FAILURE";

    private static final String ANONYMOUS_USER = "anonymoususer";

    private final PersistenceAuditEventRepository persistenceAuditEventRepository;

    private final AuditEventConverter auditEventConverter;

    public CustomAuditEventRepository(PersistenceAuditEventRepository persistenceAuditEventRepository,
            AuditEventConverter auditEventConverter) {

        this.persistenceAuditEventRepository = persistenceAuditEventRepository;
        this.auditEventConverter = auditEventConverter;
    }

    @Override
    public List<AuditEvent> find(Date after) {
        Iterable<CorPersistentAuditEvent> persistentAuditEvents =
            persistenceAuditEventRepository.findByAuditEventDateAfter(LocalDateTime.from(after.toInstant()));
        return auditEventConverter.convertToAuditEvent(persistentAuditEvents);
    }

    @Override
    public List<AuditEvent> find(String principal, Date after) {
        Iterable<CorPersistentAuditEvent> persistentAuditEvents;
        if (principal == null && after == null) {
            persistentAuditEvents = persistenceAuditEventRepository.findAll();
        } else if (after == null) {
            persistentAuditEvents = persistenceAuditEventRepository.findByPrincipal(principal);
        } else {
            persistentAuditEvents =
                persistenceAuditEventRepository.findByPrincipalAndAuditEventDateAfter(principal, LocalDateTime.from(after.toInstant()));
        }
        return auditEventConverter.convertToAuditEvent(persistentAuditEvents);
    }

    @Override
    public List<AuditEvent> find(String principal, Date after, String type) {
        Iterable<CorPersistentAuditEvent> persistentAuditEvents =
            persistenceAuditEventRepository.findByPrincipalAndAuditEventDateAfterAndAuditEventType(principal, LocalDateTime.from(after.toInstant()), type);
        return auditEventConverter.convertToAuditEvent(persistentAuditEvents);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void add(AuditEvent event) {
        if (!AUTHORIZATION_FAILURE.equals(event.getType()) &&
            !ANONYMOUS_USER.equals(event.getPrincipal())) {

            CorPersistentAuditEvent corPersistentAuditEvent = new CorPersistentAuditEvent();
            corPersistentAuditEvent.setPrincipal(event.getPrincipal());
            corPersistentAuditEvent.setAuditEventType(event.getType());
            Instant instant = Instant.ofEpochMilli(event.getTimestamp().getTime());
            corPersistentAuditEvent.setAuditEventDate(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()));
            corPersistentAuditEvent.setData(auditEventConverter.convertDataToStrings(event.getData()));
            persistenceAuditEventRepository.save(corPersistentAuditEvent);
        }
    }
}
