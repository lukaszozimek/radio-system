package io.protone.scheduler.service.schedule.factory;

import io.protone.scheduler.domain.SchClock;
import io.protone.scheduler.domain.SchClockConfiguration;
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

    public Set<SchClock> buildClocks(Set<SchClockConfiguration> clocks) {
        return clocks.stream().map(clock -> new SchClock()
                .sequence(clock.getSequence())
                .network(clock.getNetwork())
                .channel(clock.getChannel())
                .emissions(schEmissionConfigurationSchEmissionMapper.mapEmissionConfigurationToEmission(clock.getEmissions()))
                .blocks(schClockBlockMapper.buildClockBlocks(clock))).collect(toSet());
    }

}
