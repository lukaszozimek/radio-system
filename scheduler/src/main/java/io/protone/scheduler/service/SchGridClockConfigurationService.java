package io.protone.scheduler.service;

import io.protone.scheduler.domain.SchGrid;
import io.protone.scheduler.domain.SchGridClockConfiguration;
import io.protone.scheduler.repository.SchGridClockConfigurationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Set;

import static java.util.stream.Collectors.toSet;


@Service
public class SchGridClockConfigurationService {
    private final Logger log = LoggerFactory.getLogger(SchGridClockConfigurationService.class);
    @Inject
    private SchGridClockConfigurationRepository schGridRepository;


    @Transactional
    public Set<SchGridClockConfiguration> saveGridClockConfiguration(Set<SchGridClockConfiguration> schGridClockConfigurationSet, SchGrid schGrid) {
        if (schGridClockConfigurationSet != null || !schGridClockConfigurationSet.isEmpty()) {
            return schGridClockConfigurationSet.stream().map(schGridClockConfiguration -> schGridRepository.saveAndFlush(schGridClockConfiguration.grid(schGrid))).collect(toSet());
        }
        return null;

    }


}