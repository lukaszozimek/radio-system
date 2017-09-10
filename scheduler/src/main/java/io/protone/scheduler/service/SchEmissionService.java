package io.protone.scheduler.service;

import com.google.common.collect.Sets;
import io.protone.library.domain.LibMediaItem;
import io.protone.library.service.LibMediaItemService;
import io.protone.scheduler.domain.SchBlock;
import io.protone.scheduler.domain.SchClock;
import io.protone.scheduler.domain.SchEmission;
import io.protone.scheduler.repository.SchEmissionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Set;

import static java.util.stream.Collectors.toSet;


@Service
public class SchEmissionService {
    private final Logger log = LoggerFactory.getLogger(SchEmissionService.class);
    @Inject
    private SchEmissionRepository schEmissionRepository;
    @Inject
    private SchAttachmentService schAttachmentService;
    @Inject
    private LibMediaItemService libMediaItemService;

    @Transactional
    public Set<SchEmission> saveEmission(Set<SchEmission> emissionSet) {
        log.debug("Save Emission Set ");
        return emissionSet.stream().map(schEmission -> {
            schEmission.attachments(schAttachmentService.saveAttachmenst(schEmission.getAttachments()));
            return schEmissionRepository.saveAndFlush(schEmission);
        }).collect(toSet());
    }

    @Transactional
    public void deleteEmissions(Set<SchEmission> emissionSet) {
        log.debug("Delete Emission Set ");

        emissionSet.stream().forEach(schEmission -> {
            schAttachmentService.deleteAttachments(schEmission.getAttachments());
            schEmissionRepository.saveAndFlush(schEmission.clock(null).block(null).attachments(Sets.newHashSet()));
            schEmissionRepository.delete(schEmission);
        });
    }

    @Transactional
    public Set<SchEmission> saveEmission(Set<SchEmission> emissions, SchClock entity) {
        log.debug("Save Emission Set in clock level ");

        return emissions.stream().map(schEmission -> {
            SchEmission entitiy = schEmissionRepository.saveAndFlush(schEmission.clock(entity));
            schEmission.attachments(schAttachmentService.saveAttachmenst(schEmission.getAttachments(), entitiy));
            return schEmissionRepository.saveAndFlush(schEmission);
        }).collect(toSet());
    }

    @Transactional
    public Set<SchEmission> saveEmission(Set<SchEmission> emissions, SchBlock entity) {
        log.debug("Save Emission Set in block level ");
        return emissions.stream().map(schEmission -> {
            if (schEmission.getMediaItem().getId() != null) {
                SchEmission entitiy = schEmissionRepository.saveAndFlush(schEmission.block(entity));
                schEmission.attachments(schAttachmentService.saveAttachmenst(schEmission.getAttachments(), entitiy));
            } else {
                LibMediaItem libMediaItem = libMediaItemService.getMediaItem(schEmission.getNetwork().getShortcut(), schEmission.getLibraryElementShortCut(), schEmission.getMediaItem().getIdx());
                if (libMediaItem != null) {
                    SchEmission entitiy = schEmissionRepository.saveAndFlush(schEmission.block(entity).mediaItem(libMediaItem));
                    schEmission.attachments(schAttachmentService.saveAttachmenst(schEmission.getAttachments(), entitiy));
                } else {
                    LibMediaItem savedMediaIem = libMediaItemService.saveMediaItem(schEmission.getMediaItem().network(schEmission.getNetwork()).contentAvailable(false));
                    SchEmission entitiy = schEmissionRepository.saveAndFlush(schEmission.block(entity).mediaItem(savedMediaIem));
                    schEmission.attachments(schAttachmentService.saveAttachmenst(schEmission.getAttachments(), entitiy));
                }
            }
            return schEmissionRepository.saveAndFlush(schEmission);
        }).collect(toSet());
    }
}
