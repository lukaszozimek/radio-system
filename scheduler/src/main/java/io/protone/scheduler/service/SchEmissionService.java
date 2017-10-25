package io.protone.scheduler.service;

import io.protone.library.service.LibLibraryMediaService;
import io.protone.library.service.LibMediaItemService;
import io.protone.scheduler.domain.SchBlock;
import io.protone.scheduler.domain.SchClock;
import io.protone.scheduler.domain.SchEmission;
import io.protone.scheduler.domain.SchPlaylist;
import io.protone.scheduler.repository.SchEmissionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.HashSet;
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
    @Inject
    private SchPlaylistService schPlaylistService;
    @Inject
    private LibLibraryMediaService libLibraryMediaService;


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
        //    schEmissionRepository.delete(emissionSet);
    }


    @Transactional
    public Set<SchEmission> saveEmission(Set<SchEmission> emissions, SchClock entity, LocalDate date) {
        log.debug("Save Emission Set in clock level ");
        SchPlaylist schPlaylist = schPlaylistService.findSchPlaylistForNetworkAndChannelAndDateEntity(entity.getNetwork().getShortcut(), entity.getChannel().getShortcut(), date);
        if (emissions == null || emissions.isEmpty()) {
            return new HashSet<>();
        }

        return emissions.stream().map(schEmission -> {
            schEmission.clock(entity).playlist(schPlaylist);
            if (schPlaylist != null) {
                schPlaylist.addEmission(schEmission);
            }
            return schEmission;
        }).collect(toSet());
    }

    @Transactional
    public Set<SchEmission> saveEmission(Set<SchEmission> emissions, SchBlock entity, LocalDate date) {
        log.debug("Save Emission Set in block level ");
        SchPlaylist schPlaylist = schPlaylistService.findSchPlaylistForNetworkAndChannelAndDateEntity(entity.getNetwork().getShortcut(), entity.getChannel().getShortcut(), date);
        if (emissions == null || emissions.isEmpty()) {
            return new HashSet<>();
        }
        return emissions.stream().map(schEmission -> {
            schEmission.id(null);
            if (schEmission.getMediaItem().getId() != null) {
                schEmission.block(entity).playlist(schPlaylist);
            } else {
                //  LibMediaItem libMediaItem = libMediaItemService.getMediaItem(schEmission.getNetwork().getShortcut(), schEmission.getLibraryElementShortCut(), schEmission.getMediaItem().getIdx());

                schEmission.attachments(schAttachmentService.saveAttachmenst(schEmission.getAttachments(), schEmission));
            }
            if (schPlaylist != null) {
                schPlaylist.addEmission(schEmission);
            }
            return schEmission;
        }).collect(toSet());
    }
}
