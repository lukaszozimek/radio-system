package io.protone.scheduler.service;

import com.google.common.collect.Sets;
import io.protone.scheduler.domain.SchBlock;
import io.protone.scheduler.domain.SchClock;
import io.protone.scheduler.repository.SchBlockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.Set;

import static java.util.stream.Collectors.toSet;


@Service
public class SchBlockService {
    private final Logger log = LoggerFactory.getLogger(SchBlockService.class);
    @Inject
    private SchEmissionService schEmissionService;
    @Inject
    private SchBlockRepository schBlockRepository;

    @Transactional
    public Set<SchBlock> saveBlocks(Set<SchBlock> blocks) {
//        if (blocks != null && !blocks.isEmpty()) {
//            return blocks.stream().map(schBlock -> {
//                if (!schBlock.getBlocks().isEmpty()) {
//                    this.saveBlocks(schBlock.getBlocks());
//                }
//                schBlock.emissions(schEmissionService.saveEmission(schBlock.getEmissions()));
//                return schBlockRepository.save(schBlock);
//            }).collect(toSet());
//        }
//        return Sets.newHashSet();
        return blocks;
    }

    @Transactional
    public void deleteBlock(Set<SchBlock> blocks) {
//        if (blocks != null && !blocks.isEmpty()) {
//            blocks.stream().forEach(schBlock -> {
//                if (!schBlock.getBlocks().isEmpty()) {
//                    this.deleteBlock(schBlock.getBlocks());
//                }
//               // schEmissionService.deleteEmissions(schBlock.getEmissions());
//               // schBlockRepository.delete(schBlock);
//            });
//        }

    }

    @Transactional
    public Set<SchBlock> saveBlocks(Set<SchBlock> blocks, SchClock entity, LocalDate date) {
        if (blocks != null && !blocks.isEmpty()) {
            return blocks.stream().map(schBlock -> {
                if (!schBlock.getBlocks().isEmpty()) {
                    schBlock.blocks(this.saveBlocks(schBlock.getBlocks(), schBlock, date));
                }
                schBlock.clock(entity);
                return schBlock.emissions(schEmissionService.saveEmission(schBlock.getEmissions(), schBlock, date));
            }).collect(toSet());
        }
        return Sets.newHashSet();
    }


    @Transactional
    private Set<SchBlock> saveBlocks(Set<SchBlock> blocks, SchBlock parentBlock, LocalDate date) {
        if (blocks != null && !blocks.isEmpty()) {
            return blocks.stream().map(schBlock -> {
                if (!schBlock.getBlocks().isEmpty()) {
                    schBlock.blocks(this.saveBlocks(schBlock.getBlocks(), schBlock, date));
                }
                schBlock.clock(null);
                schBlock.block(parentBlock);
                schEmissionService.saveEmission(schBlock.getEmissions(), schBlock, date);
                return schBlock;
            }).collect(toSet());
        }
        return Sets.newHashSet();
    }
}
