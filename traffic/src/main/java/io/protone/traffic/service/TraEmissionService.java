package io.protone.traffic.service;

import com.google.common.collect.Sets;
import io.protone.traffic.domain.TraEmission;
import io.protone.traffic.repository.TraEmissionRepository;
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

    @Transactional
    public TraEmission saveTraEmission(TraEmission traEmission) {
        return traEmissionRepository.saveAndFlush(traEmission);
    }

    @Transactional
    public void deleteTraEmissions(Set<TraEmission> traEmissions) {
        traEmissionRepository.deleteInBatch(traEmissions);
    }
}
