package io.protone.service.traffic;


import io.protone.domain.TraPlaylist;
import io.protone.repository.traffic.TraPlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by lukaszozimek on 15/05/2017.
 */
@Service
public class TraPlaylistService {

    @Autowired
    private TraPlaylistRepository traPlaylistRepository;

    @Autowired
    private TraBlockService traBlockService;

    @Transactional
    public List<TraPlaylist> savePlaylists(List<TraPlaylist> traPlaylistList) {
        return traPlaylistRepository.save(traPlaylistList);
    }

    @Transactional
    public TraPlaylist savePlaylist(TraPlaylist traPlaylistList) {
        traPlaylistList.playlists(traBlockService.traSaveBlockSet(traPlaylistList.getPlaylists()));
        return traPlaylistRepository.save(traPlaylistList);
    }

    @Transactional
    public List<TraPlaylist> getTraPlaylistListInRange(LocalDate from, LocalDate to, String networkshortcut, String channelShortcut) {
        return traPlaylistRepository.findAllByNetwork_ShortcutAndChannel_ShortcutAndPlaylistDateBetween(networkshortcut, channelShortcut, from, to);
    }

    @Transactional
    public TraPlaylist getTraPlaylistList(LocalDate date, String networkshortcut, String channelShortcut) {
        return traPlaylistRepository.findOneByPlaylistDateAndNetwork_ShortcutAndChannel_Shortcut(date, networkshortcut, channelShortcut);
    }

    @Transactional
    public List<TraPlaylist> getAllPlaylistList(String networkshortcut, String channelShortcut, Pageable pageable) {
        return traPlaylistRepository.findAllByNetwork_ShortcutAndChannel_Shortcut(networkshortcut, channelShortcut, pageable);
    }


    @Transactional
    public void deleteOneTraPlaylistList(LocalDate date, String networkshortcut, String channelShortcut) {
        traPlaylistRepository.deleteByPlaylistDateAndNetwork_ShortcutAndChannel_Shortcut(date, networkshortcut, channelShortcut);
    }
}
