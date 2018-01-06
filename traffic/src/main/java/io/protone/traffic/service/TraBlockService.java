package io.protone.traffic.service;

import com.google.common.collect.Sets;
import io.protone.core.domain.enumeration.CorDayOfWeekEnum;
import io.protone.traffic.domain.TraBlock;
import io.protone.traffic.domain.TraBlockConfiguration;
import io.protone.traffic.repository.TraBlockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

import static java.util.stream.Collectors.toSet;

/**
 * Created by lukaszozimek on 15/05/2017.
 */
@Service
public class TraBlockService {

    @Inject
    private TraBlockRepository traBlockRepository;

    @Inject
    private TraEmissionService traEmissionService;

    @Inject
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
        traBlockRepository.save(traBlocks);
        Set<TraBlock> localTraBlock = traBlocks.stream().map(traBlock -> {
            traBlock.setEmissions(traBlock.getEmissions().stream().map(traEmission -> traEmissionService.saveTraEmission(traEmission.block(traBlock))).collect(toSet()));
            return traBlock;
        }).collect(toSet());
        return Sets.newHashSet(traBlockRepository.save(localTraBlock));
    }

    @Transactional
    public void deleteBlockSet(Set<TraBlock> traBlocks) {
        traBlocks.stream().forEach(traBlock -> traEmissionService.deleteTraEmissions(traBlock.getEmissions()));
        traBlockRepository.deleteInBatch(traBlocks);
    }

    @Transactional
    public Set<TraBlock> buildBlocks(LocalDate localDate, String organizationShortcut, String channelShortcut) {
        List<TraBlockConfiguration> traBlockConfigurations = traBlockConfigurationService.getAllBlockConfigurationsByDay(organizationShortcut, channelShortcut, corDayOfWeekEnumMap.get(localDate.getDayOfWeek()));
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
