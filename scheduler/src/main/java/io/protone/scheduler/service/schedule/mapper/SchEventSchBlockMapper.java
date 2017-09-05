package io.protone.scheduler.service.schedule.mapper;

import io.protone.scheduler.domain.SchBlock;
import io.protone.scheduler.domain.SchEvent;
import io.protone.scheduler.service.schedule.factory.SchBlockFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * Created by lukaszozimek on 05.09.2017.
 */
@Service
public class SchEventSchBlockMapper {
    @Inject
    private SchBlockFactory schBlockFactory;

    public Set<SchBlock> mapEventsToBlock(Set<SchEvent> events) {
        Set<SchBlock> schBlocks = new HashSet<>();
        return events.stream().map(event -> {
            if (!event.getBlocks().isEmpty()) {
                schBlocks.addAll(this.mapEventsToBlock(event.getBlocks()));
            }
            return schBlockFactory.blockFactoryMethod(event).blocks(schBlocks);
        }).collect(toSet());
    }
}
