package io.protone.core.service;


import io.protone.core.domain.CorPersistentAuditEvent;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.util.*;

@Component
public class AuditEventConverter {

    /**
     * Convert a list of CorPersistentAuditEvent to a list of AuditEvent
     *
     * @param persistentAuditEvents the list to convert
     * @return the converted list.
     */
    public List<AuditEvent> convertToAuditEvent(Iterable<CorPersistentAuditEvent> persistentAuditEvents) {
        if (persistentAuditEvents == null) {
            return Collections.emptyList();
        }
        List<AuditEvent> auditEvents = new ArrayList<>();
        for (CorPersistentAuditEvent corPersistentAuditEvent : persistentAuditEvents) {
            auditEvents.add(convertToAuditEvent(corPersistentAuditEvent));
        }
        return auditEvents;
    }

    /**
     * Convert a CorPersistentAuditEvent to an AuditEvent
     *
     * @param corPersistentAuditEvent the event to convert
     * @return the converted list.
     */
    public AuditEvent convertToAuditEvent(CorPersistentAuditEvent corPersistentAuditEvent) {
        Instant instant = corPersistentAuditEvent.getAuditEventDate().atZone(ZoneId.systemDefault()).toInstant();
        return new AuditEvent(Date.from(instant), corPersistentAuditEvent.getPrincipal(),
            corPersistentAuditEvent.getAuditEventType(), convertDataToObjects(corPersistentAuditEvent.getData()));
    }

    /**
     * Internal conversion. This is needed to support the current SpringBoot actuator AuditEventRepository interface
     *
     * @param data the data to convert
     * @return a map of String, Object
     */
    public Map<String, Object> convertDataToObjects(Map<String, String> data) {
        Map<String, Object> results = new HashMap<>();

        if (data != null) {
            for (Map.Entry<String, String> entry : data.entrySet()) {
                results.put(entry.getKey(), entry.getValue());
            }
        }
        return results;
    }

    /**
     * Internal conversion. This method will allow to saveCorContact additional data.
     * By default, it will saveCorContact the object as string
     *
     * @param data the data to convert
     * @return a map of String, String
     */
    public Map<String, String> convertDataToStrings(Map<String, Object> data) {
        Map<String, String> results = new HashMap<>();

        if (data != null) {
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                Object object = entry.getValue();

                // Extract the data that will be saved.
                if (object instanceof WebAuthenticationDetails) {
                    WebAuthenticationDetails authenticationDetails = (WebAuthenticationDetails) object;
                    results.put("remoteAddress", authenticationDetails.getRemoteAddress());
                    results.put("sessionId", authenticationDetails.getSessionId());
                } else if (object != null) {
                    results.put(entry.getKey(), object.toString());
                } else {
                    results.put(entry.getKey(), "null");
                }
            }
        }

        return results;
    }
}
