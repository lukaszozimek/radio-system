package io.protone.custom.service;

import io.protone.custom.service.dto.SchBlockPT;
import io.protone.custom.service.dto.SchEmissionPT;
import io.protone.custom.service.mapper.CustomSchBlockMapper;
import io.protone.custom.service.mapper.CustomSchEmissionMapperV2;
import io.protone.custom.utils.BlockUtils;
import io.protone.domain.SchBlock;
import io.protone.domain.SchEmission;
import io.protone.repository.custom.CustomSchBlockRepository;
import io.protone.repository.custom.CustomSchEmissionRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class SchBlockService {

    @Inject
    private CustomSchEmissionMapperV2 emissionMapper;

    @Inject
    private CustomSchEmissionRepository emissionRepository;

    @Inject
    private CustomSchBlockMapper blockMapper;

    @Inject
    private CustomSchBlockRepository blockRepository;

    @Inject
    private BlockUtils blockUtils;

    public List<SchBlockPT> getBlocks() {
        return blockUtils.sampleDay();
    }

    public List<SchBlockPT> setBlocks(List<SchBlockPT> blocks) {

        List<SchBlockPT> results = new ArrayList<>();

        for (SchBlockPT block: blocks)
            results.add(setBlock(block));

        return results;
    }

    public SchBlockPT setBlock(SchBlockPT blockDTO) {

        for (SchEmissionPT emission: blockDTO.getEmissions())
            setEmission(emission);

        SchBlock blockDB = blockMapper.DTO2DB(blockDTO);

        if (blockDB.getId() == null) {
            // new block - add it
            blockDB = blockRepository.saveAndFlush(blockDB);
        }
        else if (blockDB.getId() > 0) {
            // existing block - update it
            blockDB = blockRepository.saveAndFlush(blockDB);
        }
        else {
            // existing block - to delete
            blockDB.setId(-1 * blockDB.getId());
            blockRepository.delete(blockDB);
            blockRepository.flush();
            blockDB = null;
        }

        for(SchBlockPT block: blockDTO.getBlocks())
            setBlock(block);

        return blockMapper.DB2DTO(blockDB);
    }

    private SchEmissionPT setEmission(SchEmissionPT emissionDTO) {
        SchEmission emissionDB = emissionMapper.DTO2DB(emissionDTO);

        if (emissionDB.getId() == null) {
            // new emission - add it
            emissionDB = emissionRepository.saveAndFlush(emissionDB);
        }
        else if (emissionDB.getId() > 0) {
            // existing emission - update it
            emissionDB = emissionRepository.saveAndFlush(emissionDB);
        }
        else {
            // existing emission - to delete
            emissionDB.setId(-1 * emissionDB.getId());
            emissionRepository.delete(emissionDB);
            emissionDB = null;
        }

        return emissionMapper.DB2DTO(emissionDB);
    }
}
