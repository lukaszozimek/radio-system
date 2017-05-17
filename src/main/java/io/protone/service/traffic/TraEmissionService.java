package io.protone.service.traffic;

import com.google.common.collect.Sets;
import io.protone.domain.TraEmission;
import io.protone.repository.traffic.TraEmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Created by lukaszozimek on 15/05/2017.
 */
@Service
public class TraEmissionService {
    @Autowired
    private TraEmissionRepository traEmissionRepository;


    @Transactional
    public Set<TraEmission> saveTraEmissions(Set<TraEmission> traEmissions) {

        return Sets.newHashSet(traEmissionRepository.save(traEmissions));
    }
}
