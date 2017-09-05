package io.protone.scheduler.service.schedule.mapper;

import io.protone.scheduler.domain.SchBlock;
import io.protone.scheduler.domain.SchClockConfiguration;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Set;

/**
 * Created by lukaszozimek on 05.09.2017.
 */
@Service
public class SchClockBlockMapper {
    @Inject
    private SchEventSchBlockMapper schEventSchBlockMapper;

    public Set<SchBlock> buildClockBlocks(SchClockConfiguration clock) {
        return schEventSchBlockMapper.mapEventsToBlock(clock.getEvents());
    }


}
