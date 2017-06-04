package io.protone.service.traffic;

import com.google.common.collect.Sets;
import io.protone.domain.TraBlock;
import io.protone.domain.TraBlockConfiguration;
import io.protone.domain.enumeration.CorDayOfWeekEnum;
import io.protone.repository.traffic.TraBlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

import static java.util.stream.Collectors.toSet;

/**
 * Created by lukaszozimek on 15/05/2017.
 */
@Service
public class TraBlockService {

    @Autowired
    private TraBlockRepository traBlockRepository;

    @Autowired
    private TraEmissionService traEmissionService;

    @Autowired
    private TraBlockConfigurationService traBlockConfigurationService;

    private Map<DayOfWeek, CorDayOfWeekEnum> corDayOfWeekEnumMap;

    @PostConstruct
    public void initialize() {
        corDayOfWeekEnumMap = new HashMap<>();
        corDayOfWeekEnumMap.put(DayOfWeek.MONDAY, CorDayOfWeekEnum.DW_MONDAY);
        corDayOfWeekEnumMap.put(DayOfWeek.TUESDAY, CorDayOfWeekEnum.DW_TUESDAY);
        corDayOfWeekEnumMap.put(DayOfWeek.WEDNESDAY, CorDayOfWeekEnum.DW_WEDNESDAY);
        corDayOfWeekEnumMap.put(DayOfWeek.THURSDAY, CorDayOfWeekEnum.DW_THURSDAY);
        corDayOfWeekEnumMap.put(DayOfWeek.FRIDAY, CorDayOfWeekEnum.DW_FRIDAY);
        corDayOfWeekEnumMap.put(DayOfWeek.SATURDAY, CorDayOfWeekEnum.DW_SATURDAY);
        corDayOfWeekEnumMap.put(DayOfWeek.SUNDAY, CorDayOfWeekEnum.DW_SUNDAY);
    }

    @Transactional
    public Set<TraBlock> traSaveBlockSet(Set<TraBlock> traBlocks) {
        Set<TraBlock> localTraBlock = traBlocks.stream().map(traBlock -> {

            traBlock.emissions(traEmissionService.saveTraEmissions(traBlock.getEmissions()));
            return traBlock;
        }).collect(toSet());
        return Sets.newHashSet(traBlockRepository.save(localTraBlock));
    }

    @Transactional
    public Set<TraBlock> buildBlocks(LocalDate localDate, String networkshortcut) {
        List<TraBlockConfiguration> traBlockConfigurations = traBlockConfigurationService.getAllBlockConfigurationsByDay(networkshortcut, corDayOfWeekEnumMap.get(localDate.getDayOfWeek()));
        if (traBlockConfigurations.isEmpty()) {
            return new HashSet<>();
        }
        return buildBlocksBasedOnConfigurations(traBlockConfigurations);
    }

    private Set<TraBlock> buildBlocksBasedOnConfigurations(List<TraBlockConfiguration> traBlockConfigurations) {
        Set<TraBlock> traBlocks = new HashSet<>();
        traBlockConfigurations.stream().forEach(traBlockConfiguration -> {
            traBlocks.add(buildBlockBasedOnConfiguration(traBlockConfiguration));
        });
        return traBlocks;
    }

    private TraBlock buildBlockBasedOnConfiguration(TraBlockConfiguration traBlockConfiguration) {
        return new TraBlock()
            .network(traBlockConfiguration.getNetwork())
            .channel(traBlockConfiguration.getChannel())
            .length(traBlockConfiguration.getLength())
            .sequence(traBlockConfiguration.getSequence())
            .blockStartSound(traBlockConfiguration.getBlockStartSound())
            .blockEndSound(traBlockConfiguration.getBlockEndSound())
            .startBlock(traBlockConfiguration.getStartBlock())
            .stopBlock(traBlockConfiguration.getStopBlock())
            .name(traBlockConfiguration.getName());
    }
}
