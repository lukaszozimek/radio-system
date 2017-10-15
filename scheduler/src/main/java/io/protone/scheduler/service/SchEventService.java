package io.protone.scheduler.service;

import com.google.common.collect.Sets;
import io.protone.scheduler.domain.SchClockConfiguration;
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
    public Set<SchEvent> saveEvent(Set<SchEvent> blocks, SchClockConfiguration schClockConfiguration) {
        if (blocks != null && !blocks.isEmpty()) {
            return blocks.stream().map(schBlock -> {
                SchEvent schEvent = schEventRepository.saveAndFlush(schBlock.clockConfiguration(schClockConfiguration));
                schBlock.emissions(schEventEmissionService.saveEmission(schBlock.getEmissions(), schEvent));
                if (!schBlock.getBlocks().isEmpty()) {
                    this.saveEvent(schBlock.getBlocks(), schEvent);
                }

                return schEventRepository.saveAndFlush(schEvent);
            }).collect(toSet());
        }
        return Sets.newHashSet();
    }

    @Transactional
    private Set<SchEvent> saveEvent(Set<SchEvent> blocks, SchEvent event) {
        if (blocks != null && !blocks.isEmpty()) {
            return blocks.stream().map(schBlock -> {
                if (!schBlock.getBlocks().isEmpty()) {
                    this.saveEvent(schBlock.getBlocks(), event);
                }
                SchEvent schEvent = schEventRepository.saveAndFlush(schBlock.event(event));
                schBlock.emissions(schEventEmissionService.saveEmission(schBlock.getEmissions(), schEvent));
                return schEventRepository.saveAndFlush(schEvent);
            }).collect(toSet());
        }
        return Sets.newHashSet();
    }


    @Transactional
    public void deleteEvent(Set<SchEvent> blocks) {
        if (blocks != null && !blocks.isEmpty()) {
            blocks.stream().forEach(schBlock -> {
                if (!schBlock.getBlocks().isEmpty()) {
                    this.deleteEvent(schBlock.getBlocks());
                }
                schEventEmissionService.deleteEmissions(schBlock.getEmissions());
                schEventRepository.delete(schBlock);
            });
        }

    }
}