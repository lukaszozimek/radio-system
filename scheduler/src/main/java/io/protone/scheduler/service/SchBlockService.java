package io.protone.scheduler.service;

import io.protone.scheduler.domain.SchBlock;
import io.protone.scheduler.repository.SchBlockRepository;
import io.protone.scheduler.repository.SchBlockSchBlockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.stream.Collectors;


@Service
public class SchBlockService {
    private final Logger log = LoggerFactory.getLogger(SchBlockService.class);

    @Inject
    private SchBlockRepository schBlockRepository;

    @Inject
    private SchBlockSchBlockRepository schBlockSchBlockRepository;

    @Inject
    private SchEmissionService schEmissionService;

    @Transactional
    public SchBlock saveBlocks(SchBlock block) {
        block = schBlockRepository.saveAndFlush(block);
        schEmissionService.saveEmission(block.getEmissions());
        if (block.getBlocks() != null && !block.getBlocks().isEmpty()) {
            SchBlock finalBlock = block;
            block.setBlocks(block.getBlocks().stream().map(schBlock -> {
                if (schBlock.getChild().getBlocks() != null && !schBlock.getChild().getBlocks().isEmpty()) {
                    schBlock.child(saveBlocks(schBlock.getChild()));
                } else {
                    SchBlock childBlock = schBlockRepository.saveAndFlush(schBlock.getChild());
                    schEmissionService.saveEmission(schBlock.getChild().getEmissions());
                    schBlock.parent(finalBlock).child(childBlock);
                }
                return schBlockSchBlockRepository.saveAndFlush(schBlock);
            }).collect(Collectors.toList()));
        }
        return block;

    }

    @Transactional
    public void deleteBlock(Long id) {
        if (id != null) {
            SchBlock schBlock = schBlockRepository.findOne(id);
            if (schBlock.getBlocks() != null && !schBlock.getBlocks().isEmpty()) {
                schBlock.getBlocks().stream().forEach(blockSchBlockConsumer -> {
                    if (blockSchBlockConsumer.getChild().getBlocks() != null && !blockSchBlockConsumer.getChild().getBlocks().isEmpty()) {

                        deleteBlock(blockSchBlockConsumer.getChild().getId());
                        schBlockSchBlockRepository.delete(blockSchBlockConsumer);
                    }
                });
            }
            schEmissionService.deleteEmissions(schBlock.getEmissions());

        }
    }


}
