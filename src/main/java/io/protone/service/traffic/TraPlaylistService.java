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

    @Transactional
    public List<TraPlaylist> savePlaylists(List<TraPlaylist> traPlaylistList) {
        return traPlaylistRepository.save(traPlaylistList);
    }

    @Transactional
    public TraPlaylist savePlaylist(TraPlaylist traPlaylistList) {
        return traPlaylistRepository.save(traPlaylistList);
    }

    @Transactional
    public List<TraPlaylist> getTraPlaylistListInRange(LocalDate from, LocalDate to, String networkshortcut) {
        return traPlaylistRepository.findAllByNetwork_ShortcutAndPlaylistDateBetween(networkshortcut, from, to);
    }

    @Transactional
    public TraPlaylist getTraPlaylistList(LocalDate date, String networkshortcut) {
        return traPlaylistRepository.findOneByPlaylistDateAndNetwork_Shortcut(date, networkshortcut);
    }

    @Transactional
    public List<TraPlaylist> getAllPlaylistList(String networkshortcut, Pageable pageable) {
        return traPlaylistRepository.findAllByNetwork_Shortcut(networkshortcut, pageable);
    }


    @Transactional
    public void deleteOneTraPlaylistList(LocalDate date, String networkshortcut) {
        traPlaylistRepository.deleteByPlaylistDateAndNetwork_Shortcut(date, networkshortcut);
    }
}
