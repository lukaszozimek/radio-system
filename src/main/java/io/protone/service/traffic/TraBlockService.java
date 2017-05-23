package io.protone.service.traffic;

import com.google.common.collect.Sets;
import io.protone.domain.TraBlock;
import io.protone.repository.traffic.TraBlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

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

    @Transactional
    public Set<TraBlock> traSaveBlockSet(Set<TraBlock> traBlocks) {
        Set<TraBlock> localTraBlock = traBlocks.stream().map(traBlock -> {

            traBlock.emissions(traEmissionService.saveTraEmissions(traBlock.getEmissions()));
            return traBlock;
        }).collect(toSet());
        return Sets.newHashSet(traBlockRepository.save(localTraBlock));
    }
}
