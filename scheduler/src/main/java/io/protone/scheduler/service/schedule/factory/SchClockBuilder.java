package io.protone.scheduler.service.schedule.factory;

import com.google.common.collect.Sets;
import io.protone.application.scheduler.service.schedule.mapper.SchClockBlockMapper;
import io.protone.application.scheduler.service.schedule.mapper.SchEmissionConfigurationSchEmissionMapper;
import io.protone.scheduler.domain.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

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

    public Set<SchClock> buildClocks(List<SchClockTemplate> clocks, LocalDateTime endTime, SchPlaylist schPlaylist) {
        if (clocks != null) {
            List<SchClockTemplate> clockConfigurations = clocks.stream().sorted(Comparator.comparing(SchClockTemplate::getSequence)).collect(toList());
            Set<SchClock> clockSets = Sets.newHashSet();
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
        return Sets.newHashSet();
    }

    private SchClock mapClock(SchClockTemplate schClockTemplate, SchPlaylist schPlaylist) {
        SchClock clock = new SchClock().sequence(schClockTemplate.getSequence()).startTime(schClockTemplate.getStartTime());
        List<SchTimeParams> schTimeParams = new ArrayList<>();
        schTimeParams.addAll(schClockTemplate.getEmissions());
        schTimeParams.addAll(schClockTemplate.getSchEventTemplates());
        schTimeParams = schTimeParams.stream().sorted(Comparator.comparing(SchTimeParams::getSequence)).collect(toList());
        for (int i = 0; i < schTimeParams.size(); i++) {
            if (i == 0) {
                if (schTimeParams.get(i) instanceof SchEventTemplate) {
                    schTimeParams.get(i).setStartTime(clock.getStartTime());
                    SchBlock schBlockDTO = schClockBlockMapper.buildBlocks((SchEventTemplate) schTimeParams.get(i), schPlaylist);
                    schTimeParams.get(i).endTime(schBlockDTO.getEndTime());
                    clock.endTime(schBlockDTO.getEndTime());
                    clock.addBlock(schBlockDTO);
                }
                if (schTimeParams.get(i) instanceof SchEmissionTemplate) {
                    schTimeParams.get(i).setStartTime(clock.getStartTime());
                    SchEmission emission = schEmissionConfigurationSchEmissionMapper.mapSchEmission((SchEmissionTemplate) schTimeParams.get(i), schPlaylist);
                    schTimeParams.get(i).endTime(emission.getEndTime());
                    clock.endTime(emission.getEndTime());
                    clock.addEmission(emission);
                }
            } else {
                if (schTimeParams.get(i) instanceof SchEventTemplate) {
                    schTimeParams.get(i).setStartTime(schTimeParams.get(i - 1).getEndTime());
                    SchBlock schBlock = schClockBlockMapper.buildBlocks((SchEventTemplate) schTimeParams.get(i), schPlaylist);
                    if (schBlock.getEndTime() != null) {
                        schTimeParams.get(i).endTime(schBlock.getEndTime());
                        clock.endTime(schBlock.getEndTime());
                    } else {
                        schTimeParams.get(i).endTime(schTimeParams.get(i).getStartTime());
                        clock.endTime(schTimeParams.get(i).getStartTime());
                    }
                    clock.addBlock(schBlock);
                }
                if (schTimeParams.get(i) instanceof SchEmissionTemplate) {
                    schTimeParams.get(i).setStartTime(schTimeParams.get(i - 1).getEndTime());
                    SchEmission schEmission = schEmissionConfigurationSchEmissionMapper.mapSchEmission((SchEmissionTemplate) schTimeParams.get(i), schPlaylist);
                    schTimeParams.get(i).endTime(schEmission.getEndTime());
                    clock.endTime(schEmission.getEndTime());
                    clock.addEmission(schEmission);
                }
            }
        }
        if (clock.getEndTime() != null) {
            return clock.length((long) Duration.between(clock.getStartTime(), clock.getEndTime()).getNano()).network(schClockTemplate.getNetwork()).channel(schClockTemplate.getChannel());
        } else {
            return clock.length((long) Duration.between(clock.getStartTime(), clock.getStartTime()).getNano()).network(schClockTemplate.getNetwork()).channel(schClockTemplate.getChannel());
        }
    }
}
