package io.protone.scheduler.service;


import io.protone.scheduler.domain.SchEvent;
import io.protone.scheduler.repository.SchEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service
@Transactional
public class SchEventService {

    private final Logger log = LoggerFactory.getLogger(SchEventService.class);

    @Inject
    private SchEventRepository eventRepository;

    public SchEvent saveEvent(SchEvent schEvent) {
        log.debug("Persisting SchEvent: {}", schEvent);
        schEvent = eventRepository.saveAndFlush(schEvent);
        return schEvent;
    }
}
