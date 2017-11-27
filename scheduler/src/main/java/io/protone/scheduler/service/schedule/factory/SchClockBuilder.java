package io.protone.scheduler.service.schedule.factory;

import com.google.common.collect.Lists;
import io.protone.application.scheduler.service.schedule.mapper.SchClockBlockMapper;
import io.protone.application.scheduler.service.schedule.mapper.SchEmissionConfigurationSchEmissionMapper;
import io.protone.scheduler.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 05.09.2017.
 */
@Service
public class SchClockBuilder {
    @Inject
    private SchEmissionConfigurationSchEmissionMapper schEmissionConfigurationSchEmissionMapper;

    @Inject
    private SchClockBlockMapper schClockBlockMapper;

    @Transactional
    public List<SchClock> buildClocks(List<SchClockTemplate> clocks, LocalDateTime endTime, SchPlaylist schPlaylist) {
        if (clocks != null) {
            List<SchClockTemplate> clockConfigurations = clocks.stream().sorted(Comparator.comparing(SchClockTemplate::getSequence)).collect(toList());
            List<SchClock> clockSets = Lists.newArrayList();
            for (int i = 0; i < clocks.size(); i++) {
                if (i == 0) {
                    clockConfigurations.get(i).setStartTime(endTime);
                    clockSets.add(mapClock(clockConfigurations.get(i), schPlaylist));

                } else {
                    clockConfigurations.get(i).setStartTime(clockSets.stream().max(Comparator.comparing(SchClock::getSequence)).get().getEndTime());
                    clockSets.add(mapClock(clockConfigurations.get(i), schPlaylist));
                }
            }
            return clockSets;
        }
        return Lists.newArrayList();
    }
    @Transactional
    private SchClock mapClock(SchClockTemplate schClockTemplate, SchPlaylist schPlaylist) {
        SchClock clock = new SchClock().sequence(schClockTemplate.getSequence()).startTime(schClockTemplate.getStartTime()).name(schClockTemplate.getName()).clockCategory(schClockTemplate.getClockCategory());
        Map<Long, SchTimeParams> schTimeParamsMap = schClockTemplate.getChildsTimeParams();
        schTimeParamsMap.putAll(schClockTemplate.getEmissionsMap());
        schTimeParamsMap.keySet().stream().forEach(sequence -> {
            if (sequence == 0) {
                if (schTimeParamsMap.get(sequence) instanceof SchEventTemplate) {
                    schTimeParamsMap.get(sequence).setStartTime(clock.getStartTime());
                    SchBlock schBlockDTO = schClockBlockMapper.buildBlocks((SchEventTemplate) schTimeParamsMap.get(sequence), schPlaylist);
                    schTimeParamsMap.get(sequence).endTime(schBlockDTO.getEndTime());
                    clock.endTime(schBlockDTO.getEndTime());
                    clock.addBlock(new SchBlockSchBlock().sequence(sequence).parent(clock).child(schBlockDTO));
                }
                if (schTimeParamsMap.get(sequence) instanceof SchEmissionTemplate) {
                    schTimeParamsMap.get(sequence).setStartTime(clock.getStartTime());
                    SchEmission emission = schEmissionConfigurationSchEmissionMapper.mapSchEmission((SchEmissionTemplate) schTimeParamsMap.get(sequence), schPlaylist).sequence(sequence);
                    schTimeParamsMap.get(sequence).endTime(emission.getEndTime());
                    clock.endTime(emission.getEndTime());
                    clock.addEmission(emission);
                }
            } else {
                if (schTimeParamsMap.get(sequence) instanceof SchEventTemplate) {
                    schTimeParamsMap.get(sequence).setStartTime(schTimeParamsMap.get(sequence - 1).getEndTime());
                    SchBlock schBlock = schClockBlockMapper.buildBlocks((SchEventTemplate) schTimeParamsMap.get(sequence), schPlaylist);
                    if (schBlock.getEndTime() != null) {
                        schTimeParamsMap.get(sequence).endTime(schBlock.getEndTime());
                        clock.endTime(schBlock.getEndTime());
                    } else {
                        schTimeParamsMap.get(sequence).endTime(schTimeParamsMap.get(sequence).getStartTime());
                        clock.endTime(schTimeParamsMap.get(sequence).getStartTime());
                    }
                    clock.addBlock(new SchBlockSchBlock().sequence(sequence).parent(clock).child(schBlock));
                }
                if (schTimeParamsMap.get(sequence) instanceof SchEmissionTemplate) {
                    schTimeParamsMap.get(sequence).setStartTime(schTimeParamsMap.get(sequence - 1).getEndTime());
                    SchEmission schEmission = schEmissionConfigurationSchEmissionMapper.mapSchEmission((SchEmissionTemplate) schTimeParamsMap.get(sequence), schPlaylist).sequence(sequence);
                    schTimeParamsMap.get(sequence).endTime(schEmission.getEndTime());
                    clock.endTime(schEmission.getEndTime());
                    clock.addEmission(schEmission);
                }
            }
        });
        if (clock.getEndTime() != null) {
            return clock.length((long) Duration.between(clock.getStartTime(), clock.getEndTime()).getNano()).network(schClockTemplate.getNetwork()).channel(schClockTemplate.getChannel());
        } else {
            return clock.length((long) Duration.between(clock.getStartTime(), clock.getStartTime()).getNano()).network(schClockTemplate.getNetwork()).channel(schClockTemplate.getChannel());
        }
    }
}
