package io.protone.scheduler.service;

import com.google.common.collect.Lists;
import io.protone.library.service.LibLibraryMediaService;
import io.protone.library.service.LibMediaItemService;
import io.protone.scheduler.domain.SchEmission;
import io.protone.scheduler.repository.SchEmissionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Service
public class SchEmissionService {
    private final Logger log = LoggerFactory.getLogger(SchEmissionService.class);

    @Inject
    private SchEmissionRepository schEmissionRepository;
    @Inject
    private SchAttachmentService schAttachmentService;
    @Inject
    private LibMediaItemService libMediaItemService;
    @Inject
    private SchPlaylistService schPlaylistService;
    @Inject
    private LibLibraryMediaService libLibraryMediaService;


    @Transactional
    public List<SchEmission> saveEmission(List<SchEmission> emissionSet) {
        log.debug("Save Emission Set ");
        if (emissionSet == null || emissionSet.isEmpty()) {
            return Lists.newArrayList();
        }
        return emissionSet.stream().map(schEmission -> {
            schEmission.attachments(schAttachmentService.saveAttachmenst(schEmission.getAttachments()));
            return schEmissionRepository.saveAndFlush(schEmission);
        }).collect(toList());
    }

    @Transactional
    public void deleteEmissions(List<SchEmission> emissionSet) {
        log.debug("Delete Emission Set ");
        schEmissionRepository.deleteInBatch(emissionSet);

    }

}
