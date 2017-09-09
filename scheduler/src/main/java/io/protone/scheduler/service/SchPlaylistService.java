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
    private SchPlaylistDTOTimeCalculatorService schPlaylistDTOTimeCalculatorService;

    @Transactional
    public SchPlaylist saveSchPlaylist(SchPlaylist schSchedule) {
        return schPlaylistRepository.saveAndFlush(schSchedule);
    }

    @Transactional(readOnly = true)
    public Slice<SchPlaylist> findSchPlaylistForNetworkAndChannel(String networkShortcut, String channelShortcut, Pageable pageable) {
        return schPlaylistRepository.findAllByNetwork_ShortcutAndChannel_Shortcut(networkShortcut, channelShortcut, pageable);
    }

    @Transactional(readOnly = true)
    public SchPlaylistDTO findSchPlaylistForNetworkAndChannelAndDate(String networkShortcut, String channelShortcut, LocalDate date) {
        SchPlaylist schPlaylist = schPlaylistRepository.findOneByNetwork_ShortcutAndChannel_ShortcutAndDate(networkShortcut, channelShortcut, date);
        return schPlaylistDTOTimeCalculatorService.calculateTimeInSchPlaylistDTO(schPlaylist);
    }

    @Transactional
    public void deleteSchPlaylistByNetworkAndChannelAndDate(String networkShortcut, String channelShortcut, LocalDate date) {
        schPlaylistRepository.deleteByNetwork_ShortcutAndChannel_ShortcutAndDate(networkShortcut, channelShortcut, date);
    }
}
