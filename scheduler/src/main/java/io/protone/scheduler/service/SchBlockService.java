package io.protone.scheduler.service;

import io.protone.scheduler.domain.SchBlock;
import io.protone.scheduler.repository.SchBlockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Set;

import static java.util.stream.Collectors.toSet;


@Service
public class SchBlockService {
    @Inject
    private SchEmissionService schEmissionService;
    @Inject
    private SchBlockRepository schBlockRepository;

    @Transactional
    public Set<SchBlock> saveBlocks(Set<SchBlock> blocks) {
        return blocks.stream().map(schBlock -> {
            if (!schBlock.getBlocks().isEmpty()) {
                this.saveBlocks(schBlock.getBlocks());
            }
            schBlock.emissions(schEmissionService.saveEmission(schBlock.getEmissions()));
            return schBlockRepository.saveAndFlush(schBlock);
        }).collect(toSet());
    }
    @Transactional
    public void deleteBlock(Set<SchBlock> blocks){

    }
}
