package io.protone.scheduler.service;

import io.protone.scheduler.api.dto.SchPlaylistDTO;
import io.protone.scheduler.domain.SchPlaylist;
import io.protone.scheduler.repository.SchPlaylistRepository;
import io.protone.scheduler.service.time.SchPlaylistDTOTimeCalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalDate;


@Service
public class SchPlaylistService {
    private final Logger log = LoggerFactory.getLogger(SchPlaylistService.class);
    @Inject
    private SchPlaylistRepository schPlaylistRepository;

    @Inject
    private SchEmissionService schEmissionService;
    @Inject
    private SchScheduleService schScheduleService;
    @Inject
    private SchPlaylistDTOTimeCalculatorService schPlaylistDTOTimeCalculatorService;

    @Transactional
    public SchPlaylist saveSchPlaylist(SchPlaylist schPaylist) {
        return schPlaylistRepository.saveAndFlush(schPaylist);
    }

    @Transactional(readOnly = true)
    public Slice<SchPlaylist> findSchPlaylistForNetworkAndChannel(String networkShortcut, String channelShortcut, Pageable pageable) {
        return schPlaylistRepository.findAllByNetwork_ShortcutAndChannel_Shortcut(networkShortcut, channelShortcut, pageable);
    }

    @Transactional(readOnly = true)
    public SchPlaylistDTO findSchPlaylistForNetworkAndChannelAndDate(String networkShortcut, String channelShortcut, LocalDate date) {
        SchPlaylist schPlaylist = schPlaylistRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndDate(networkShortcut, channelShortcut, date);
        if (schPlaylist != null) {
            return schPlaylistDTOTimeCalculatorService.calculateTimeInSchPlaylistDTO(schPlaylist);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public SchPlaylist findSchPlaylistForNetworkAndChannelAndDateEntity(String networkShortcut, String channelShortcut, LocalDate date) {
        return schPlaylistRepository.findOne(networkShortcut, channelShortcut, date);

    }

    @Transactional
    public void deleteSchPlaylistByNetworkAndChannelAndDate(String networkShortcut, String channelShortcut, LocalDate date) {
        SchPlaylist schPlaylist = schPlaylistRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndDate(networkShortcut, channelShortcut, date);
        if (schPlaylist != null) {
            if (schPlaylist.getEmissions() != null && !schPlaylist.getEmissions().isEmpty()) {
                this.schEmissionService.deleteEmissions(schPlaylist.getEmissions());
            }
            schPlaylistRepository.deleteByNetwork_ShortcutAndChannel_ShortcutAndDate(networkShortcut, channelShortcut, date);
            schScheduleService.deleteSchScheduleByNetworkAndChannelAndDate(networkShortcut, channelShortcut, date);
        }
    }
}
