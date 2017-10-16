package io.protone.scheduler.service;

import com.google.common.collect.Sets;
import io.protone.core.s3.exceptions.CreateBucketException;
import io.protone.library.domain.LibMediaItem;
import io.protone.library.domain.LibMediaLibrary;
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

        emissionSet.stream().forEach(schEmission -> {
            schAttachmentService.deleteAttachments(schEmission.getAttachments());
            schEmissionRepository.saveAndFlush(schEmission.clock(null).block(null).attachments(Sets.newHashSet()));
            schEmissionRepository.delete(schEmission);
        });
    }


    @Transactional
    public Set<SchEmission> saveEmission(Set<SchEmission> emissions, SchClock entity, LocalDate date) {
        log.debug("Save Emission Set in clock level ");
        SchPlaylist schPlaylist = schPlaylistService.findSchPlaylistForNetworkAndChannelAndDateEntity(entity.getNetwork().getShortcut(), entity.getChannel().getShortcut(), date);
        if (emissions == null || emissions.isEmpty()) {
            return new HashSet<>();
        }
        return emissions.stream().map(schEmission -> {
            SchEmission entitiy = schEmissionRepository.save(schEmission.clock(entity).playlist(schPlaylist));
            schEmission.attachments(schAttachmentService.saveAttachmenst(schEmission.getAttachments(), entitiy));
            if (schEmission.getPlaylist() != null) {
                schPlaylistService.saveSchPlaylist(schEmission.getPlaylist().addEmission(schEmission));
            }
            return schEmissionRepository.save(schEmission);
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
            SchEmission entitiyEmissions = null;
            if (schEmission.getMediaItem().getId() != null) {
                entitiyEmissions = schEmissionRepository.save(schEmission.block(entity).playlist(schPlaylist));
                entitiyEmissions.attachments(schAttachmentService.saveAttachmenst(schEmission.getAttachments(), entitiyEmissions));
            } else {
                LibMediaItem libMediaItem = libMediaItemService.getMediaItem(schEmission.getNetwork().getShortcut(), schEmission.getLibraryElementShortCut(), schEmission.getMediaItem().getIdx());
                if (libMediaItem != null) {
                    entitiyEmissions = schEmissionRepository.save(schEmission.block(entity).playlist(schPlaylist).mediaItem(libMediaItem));
                    entitiyEmissions.attachments(schAttachmentService.saveAttachmenst(schEmission.getAttachments(), entitiyEmissions));
                } else {
                    LibMediaLibrary libMediaLibrary = libLibraryMediaService.findLibrary(schEmission.getNetwork().getShortcut(), schEmission.getLibraryElementShortCut());
                    if (libMediaLibrary != null) {
                        LibMediaItem savedMediaIem = libMediaItemService.saveMediaItem(schEmission.getMediaItem().network(schEmission.getNetwork()).library(libMediaLibrary).contentAvailable(false));
                        entitiyEmissions = schEmissionRepository.save(schEmission.block(entity).playlist(schPlaylist).mediaItem(savedMediaIem));
                        entitiyEmissions.attachments(schAttachmentService.saveAttachmenst(schEmission.getAttachments(), entitiyEmissions));
                    } else {
                        LibMediaLibrary libMediaLibrary1 = null;
                        try {
                            libMediaLibrary1 = libLibraryMediaService.createOrUpdateLibrary(new LibMediaLibrary().network(schEmission.getNetwork()).addChannel(schEmission.getChannel()).name(schEmission.getLibraryElementShortCut()).prefix(schEmission.getLibraryElementShortCut()).name(schEmission.getLibraryElementShortCut()));
                        } catch (CreateBucketException e) {
                            log.debug("There was a problem with building library which should contain media item");
                        }
                        LibMediaItem savedMediaIem = libMediaItemService.saveMediaItem(schEmission.getMediaItem().network(schEmission.getNetwork()).library(libMediaLibrary1).contentAvailable(false));
                        entitiyEmissions = schEmissionRepository.save(schEmission.block(entity).playlist(schPlaylist).mediaItem(savedMediaIem));
                        entitiyEmissions.attachments(schAttachmentService.saveAttachmenst(schEmission.getAttachments(), entitiyEmissions));

                    }
                }
            }
            return entitiyEmissions;
        }).collect(toSet());
    }
}
