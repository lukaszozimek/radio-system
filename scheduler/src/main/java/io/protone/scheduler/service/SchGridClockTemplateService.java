package io.protone.scheduler.service;

import io.protone.scheduler.domain.SchGrid;
import io.protone.scheduler.domain.SchGridClockTemplate;
import io.protone.scheduler.repository.SchGridClockConfigurationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Set;

import static java.util.stream.Collectors.toSet;


@Service
public class SchGridClockTemplateService {
    private final Logger log = LoggerFactory.getLogger(SchGridClockTemplateService.class);
    @Inject
    private SchGridClockConfigurationRepository schGridRepository;


    @Transactional
    public Set<SchGridClockTemplate> saveGridClockConfiguration(Set<SchGridClockTemplate> schGridClockTemplateSet, SchGrid schGrid) {
        if (schGridClockTemplateSet != null || !schGridClockTemplateSet.isEmpty()) {
            return schGridClockTemplateSet.stream().map(schGridClockConfiguration -> schGridRepository.saveAndFlush(schGridClockConfiguration.grid(schGrid))).collect(toSet());
        }
        return null;

    }


}