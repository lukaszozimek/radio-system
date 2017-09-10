package io.protone.scheduler.service.schedule.factory;

import com.google.common.collect.Sets;
import io.protone.scheduler.domain.SchClock;
import io.protone.scheduler.domain.SchClockConfiguration;
import io.protone.scheduler.domain.SchPlaylist;
import io.protone.scheduler.service.schedule.mapper.SchClockBlockMapper;
import io.protone.scheduler.service.schedule.mapper.SchEmissionConfigurationSchEmissionMapper;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * Created by lukaszozimek on 05.09.2017.
 */
@Service
public class SchClockBuilder {
    @Inject
    private SchEmissionConfigurationSchEmissionMapper schEmissionConfigurationSchEmissionMapper;

    @Inject
    private SchClockBlockMapper schClockBlockMapper;

    public Set<SchClock> buildClocks(Set<SchClockConfiguration> clocks, SchPlaylist schPlaylist) {
        if (clocks != null) {
            return clocks.stream().map(clock -> new SchClock()
                    .sequence(clock.getSequence())
                    .length(clock.getLength())
                    .network(clock.getNetwork())
                    .channel(clock.getChannel())
                    .emissions(schEmissionConfigurationSchEmissionMapper.mapEmissionConfigurationToEmission(clock.getEmissions(),schPlaylist))
                    .blocks(schClockBlockMapper.buildClockBlocks(clock,schPlaylist))).collect(toSet());
        }
        return Sets.newHashSet();
    }

}
