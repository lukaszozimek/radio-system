package io.protone.scheduler.service;

import com.google.common.collect.Sets;
import io.protone.scheduler.domain.SchEvent;
import io.protone.scheduler.repository.SchEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Set;

import static java.util.stream.Collectors.toSet;


@Service
public class SchEventService {
    private final Logger log = LoggerFactory.getLogger(SchEventService.class);

    @Inject
    private SchEventRepository schEventRepository;

    @Inject
    private SchEventEmissionService schEventEmissionService;


    @Transactional
    public Set<SchEvent> saveEvent(Set<SchEvent> blocks) {
        log.debug("Start Saving ");
        if (blocks != null && !blocks.isEmpty()) {
            return blocks.stream().map(schBlock -> {
                schEventEmissionService.saveEmission(schBlock.getEmissions()).stream().forEach(schEventEmission -> schBlock.addEventEmissions(schEventEmission));
                if (!schBlock.getSchEvents().isEmpty()) {
                    this.saveEvent(schBlock.getSchEvents()).stream().forEach(schEvent1 -> schBlock.addSchEvents(schEvent1));
                }
                return schEventRepository.save(schBlock);
            }).collect(toSet());
        }
        return Sets.newHashSet();
    }

    @Transactional
    public void deleteEvent(Set<SchEvent> blocks) {
        if (blocks != null && !blocks.isEmpty()) {
            blocks.stream().forEach(schBlock -> {
                if (!schBlock.getSchEvents().isEmpty()) {
                    this.deleteEvent(schBlock.getSchEvents());
                }
                schEventEmissionService.deleteEmissions(schBlock.getEmissions());
                schEventRepository.delete(schBlock);
            });
        }

    }
}
