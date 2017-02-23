package io.protone.custom.service;

import io.protone.custom.service.dto.SchPlaylistPT;
import io.protone.custom.service.mapper.CustomSchPlaylistMapper;
import io.protone.custom.utils.BlockUtils;
import io.protone.domain.CorChannel;
import io.protone.domain.CorNetwork;
import io.protone.domain.SchPlaylist;
import io.protone.repository.custom.CustomCorChannelRepository;
import io.protone.repository.custom.CustomSchPlaylistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SchPlaylistService {

    @Inject
    CorNetworkService networkService;

    @Inject
    CorChannelService channelService;

    @Inject
    CustomSchPlaylistRepository playlistRepository;

    @Inject
    CustomSchPlaylistMapper playlistMapper;

    @Inject
    CustomCorChannelRepository channelRepository;

    @Inject
    SchBlockService blockService;

    @Inject
    BlockUtils blockUtils;

    public SchPlaylistPT getPlaylist(String networkShortcut, String channelShortcut, String date) {

        SchPlaylistPT result = null;

        CorNetwork networkDB = networkService.findNetwork(networkShortcut);
        if (networkDB == null)
            return result;

        CorChannel channelDB = channelService.findChannel(networkShortcut, channelShortcut);
        if ((channelDB == null) || (channelDB.getNetwork().getId().compareTo(networkDB.getId()) != 0))
            return result;

        //Optional<SchPlaylist> playlistDB = playlistRepository.findByChannelAndDate(channelDB, LocalDate.parse(date));
        LocalDate localDate = LocalDate.parse(date);

        return new SchPlaylistPT()
            .channelId(channelDB.getId())
            .dimYear(localDate.getYear())
            .dimMonth(localDate.getMonthValue())
            .dimDay(localDate.getDayOfMonth())
            .blocks(blockUtils.sampleDay());

        //return playlistMapper.DBToDTO(playlistDB.get());
    }

    public SchPlaylistPT setPlaylist(String networkShortcut, String channelShortcut, SchPlaylistPT playlist) {
        SchPlaylistPT result = null;
        return playlist;
    }

    public List<SchPlaylistPT> getPlaylists(String networkShortcut, String channelShortcut) {
        List<SchPlaylistPT> results = new ArrayList<>();

        CorNetwork networkDB = networkService.findNetwork(networkShortcut);
        if (networkDB == null)
            return results;

        CorChannel channelDB = channelService.findChannel(networkShortcut, channelShortcut);
        if ((channelDB == null) || (channelDB.getNetwork().getId().compareTo(networkDB.getId()) != 0))
            return results;

        List<SchPlaylist> resultsDB = playlistRepository.findByChannel(channelDB);

        return playlistMapper.DBsToDTOs(resultsDB);
    }

    public List<SchPlaylistPT> getPlaylists(String networkShortcut) {
        List<SchPlaylistPT> results = new ArrayList<>();

        CorNetwork networkDB = networkService.findNetwork(networkShortcut);
        if (networkDB == null)
            return results;

        List<CorChannel> channels = channelRepository.findAllByNetwork(networkDB);

        for (CorChannel channel: channels) {
            List<SchPlaylist> playlists = playlistRepository.findByChannel(channel);
            results.addAll(playlistMapper.DBsToDTOs(playlists));
        }
        return results;
    }

    @Transactional
    public void deletePlaylist(String networkShortcut, String channelShortcut, String date) {
        SchPlaylistPT playlistToDelete = getPlaylist(networkShortcut, channelShortcut, date);
        playlistRepository.delete(playlistMapper.DTOToDB(playlistToDelete));
    }

    @Transactional
    public SchPlaylistPT createOrUpdatePlaylist(String networkShortcut, SchPlaylistPT playlist) {
        return playlistMapper.DBToDTO(playlistRepository.saveAndFlush(playlistMapper.DTOToDB(playlist)));
    }

    public List<SchPlaylistPT> getPlaylist(String networkShortcut, String date) {
        List<SchPlaylistPT> results = new ArrayList<>();

        CorNetwork networkDB = networkService.findNetwork(networkShortcut);
        if (networkDB == null)
            return results;

        List<CorChannel> channelsDB = channelRepository.findAllByNetwork(networkDB);

        for (CorChannel channelDB: channelsDB) {
            Optional<SchPlaylist> playlistDB = playlistRepository.findByChannelAndDate(channelDB, LocalDate.parse(date));
            if (playlistDB.isPresent())
                results.add(playlistMapper.DBToDTO(playlistDB.get()));
        }

        return results;
    }

    public void deletePlaylist(String networkShortcut, String date) {
        List<SchPlaylistPT> toDeleteDAO = getPlaylist(networkShortcut, date);
        playlistRepository.delete(playlistMapper.DTOsToDBs(toDeleteDAO));
    }

    public SchPlaylistPT createOrUpdatePlaylist(String networkShortcut, String channelShortcut, SchPlaylistPT playlist) {
        CorChannel channel = channelService.findChannelByNetworkShortcutAndChannelShortcut(networkShortcut, channelShortcut);
        playlist.setChannelId(channel.getId());
        return playlistMapper.DBToDTO(playlistRepository.saveAndFlush(playlistMapper.DTOToDB(playlist)));
    }
}
