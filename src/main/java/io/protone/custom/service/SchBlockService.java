package io.protone.custom.service;

import io.protone.custom.service.dto.SchBlockPT;
import io.protone.custom.service.dto.SchEmissionPT;
import io.protone.custom.service.dto.SchPlaylistPT;
import io.protone.custom.service.dto.SchTemplatePT;
import io.protone.custom.service.mapper.CustomSchBlockMapper;
import io.protone.custom.service.mapper.CustomSchEmissionMapperV2;
import io.protone.custom.service.mapper.CustomSchPlaylistMapper;
import io.protone.custom.utils.BlockUtils;
import io.protone.domain.SchBlock;
import io.protone.domain.SchEmission;
import io.protone.domain.SchPlaylist;
import io.protone.domain.SchTemplate;
import io.protone.repository.custom.CustomSchBlockRepository;
import io.protone.repository.custom.CustomSchEmissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZonedDateTime;
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

    public List<SchBlockPT> getBlocks(SchPlaylistPT playlistDTO, SchTemplatePT templateDTO, SchBlockPT parentBlockDTO) {

        List<SchBlockPT> results = new ArrayList<>();

        SchPlaylist playlistDB = null;
        if (playlistDTO != null)
            playlistDB = blockMapper.mapSchPlaylist(playlistDTO.getId());

        SchTemplate templateDB = null;
        if (templateDTO != null)
            templateDB = blockMapper.mapSchTemplate(templateDTO.getId());

        SchBlock parentBlockDB = null;
        if (parentBlockDTO != null)
            parentBlockDB = emissionMapper.mapSchBlock(parentBlockDTO.getId());

        List<SchBlock> rootBlocksDB = blockRepository.findByPlaylistAndTemplateAndParentBlock(playlistDB, templateDB, parentBlockDB);
        for (SchBlock childBlockDB: rootBlocksDB) {
            SchBlockPT childBlockDTO = blockMapper.DB2DTO(childBlockDB);
            childBlockDTO.blocks(getBlocks(playlistDTO, templateDTO, childBlockDTO));
            childBlockDTO.emissions(getEmissions(childBlockDTO));
            results.add(childBlockDTO);
        }

        return results;
    }

    private List<SchEmissionPT> getEmissions(SchBlockPT childBlockDTO) {
        return emissionMapper.DBs2DTOs(emissionRepository.findByBlock(emissionMapper.mapSchBlock(childBlockDTO.getId())));
    }

    public List<SchBlockPT> setBlocks(List<SchBlockPT> blocks, SchPlaylistPT playlist, SchTemplatePT template) {

        List<SchBlockPT> results = new ArrayList<>();

        for (SchBlockPT block: blocks)
            results.add(setBlock(block, null, playlist, template));

        return results;
    }

    @Transactional
    public SchBlockPT setBlock(SchBlockPT blockDTO, Long parentId, SchPlaylistPT playlist, SchTemplatePT template) {

        SchBlock blockDB = blockMapper.DTO2DB(blockDTO);
        blockDB.setParentBlock(emissionMapper.mapSchBlock(parentId));

        if (playlist != null)
            blockDB.setPlaylist(blockMapper.mapSchPlaylist(playlist.getId()));

        if (template != null)
            blockDB.setTemplate(blockMapper.mapSchTemplate(template.getId()));

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

        SchBlockPT result = blockMapper.DB2DTO(blockDB);

        for(SchBlockPT block: blockDTO.getBlocks())
            result.addBlock(setBlock(block, blockDB.getId(), playlist, template));

        for (SchEmissionPT emission: blockDTO.getEmissions())
            result.addEmission(setEmission(emission, blockDB.getId()));

        return result;
    }

    private SchEmissionPT setEmission(SchEmissionPT emissionDTO, Long parentId) {
        SchEmission emissionDB = emissionMapper.DTO2DB(emissionDTO);
        emissionDB.setBlock(emissionMapper.mapSchBlock(parentId));

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
