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

    @Transactional
    public SchBlock saveBlocks(SchBlock block) {
        schBlockRepository.saveAndFlush(block);
        if (block.getBlocks() != null && !block.getBlocks().isEmpty()) {
            block.setBlocks(block.getBlocks().stream().map(schBlock -> {
                if (schBlock.getChild().getBlocks() != null && !schBlock.getChild().getBlocks().isEmpty()) {
                    schBlock.child(saveBlocks(schBlock.getChild()));
                } else {
                    schBlock.parent(block).child(schBlockRepository.saveAndFlush(schBlock.getChild()));
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
                schBlock.getBlocks().stream().forEach(schEventTemplateEvnetTemplate -> {
                    if (schEventTemplateEvnetTemplate.getChild().getBlocks() != null && !schEventTemplateEvnetTemplate.getChild().getBlocks().isEmpty()) {
                        deleteBlock(schEventTemplateEvnetTemplate.getChild().getId());
                    }
                    schBlockSchBlockRepository.delete(schEventTemplateEvnetTemplate);
                });
                schBlockRepository.delete(schBlock);
            }
            schBlockRepository.flush();
            schBlockSchBlockRepository.flush();
        }
    }


}
