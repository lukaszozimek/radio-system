package io.protone.custom.service;

import io.protone.custom.service.dto.SchPlaylistPT;
import io.protone.custom.service.mapper.CustomSchBlockMapper;
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
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SchPlaylistService {

    @Inject
    private CorNetworkService networkService;

    @Inject
    private CorChannelService channelService;

    @Inject
    private CustomSchPlaylistRepository playlistRepository;

    @Inject
    private CustomSchPlaylistMapper playlistMapper;

    @Inject
    private CustomCorChannelRepository channelRepository;

    @Inject
    private SchBlockService blockService;

    @Inject
    private CustomSchBlockMapper blockMapper;

    @Inject
    private BlockUtils blockUtils;

    public SchPlaylistPT randomPlaylist(String networkShortcut, String channelShortcut, String date) {
        CorChannel channelDB = channelService.getChannel(networkShortcut, channelShortcut);
        LocalDate localDate = LocalDate.parse(date);

        return new SchPlaylistPT()
            .date(localDate);
          //  .blocks(blockUtils.sampleDay(localDate.atStartOfDay(ZoneOffset.UTC)));
    }

    private SchPlaylistPT savePlaylist(SchPlaylistPT playlist) {
        SchPlaylist playlistDB = playlistMapper.DTOToDB(playlist);
        playlistDB = playlistRepository.saveAndFlush(playlistDB);
        SchPlaylistPT result = playlistMapper.DBToDTO(playlistDB);
      //  result.blocks(blockService.setClocks(playlist.getBlocks(), result, null));
        return result;
    }

    public List<SchPlaylistPT> getAllPlaylists(String networkShortcut) {
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

    public List<SchPlaylistPT> getAllPlaylistsByChannel(String networkShortcut, String channelShortcut) {
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

    public SchPlaylistPT getPlaylistByChannelAndDate(String networkShortcut, String channelShortcut, String date) {

        SchPlaylistPT result = null;
        CorChannel channelDB = channelService.getChannel(networkShortcut, channelShortcut);
        Optional<SchPlaylist> optionalPlaylist = playlistRepository.findByChannelAndDate(channelDB, LocalDate.parse(date));
        if (optionalPlaylist.isPresent()) {
            result = playlistMapper.DBToDTO(optionalPlaylist.get());
            result.blocks(new ArrayList<>());
           // result.blocks(blockService.getBlocks(result, null, null));
        }
        return result;
    }

    public SchPlaylistPT setPlaylist(String networkShortcut, String channelShortcut, SchPlaylistPT playlist) {

        CorChannel channelDB = channelService.findChannel(networkShortcut, channelShortcut);
        if (channelDB == null)
            return null;

        SchPlaylistPT result = savePlaylist(playlist);

        return result;
    }

    public void deletePlaylist(String networkShortcut, String channelShortcut, String date) {
        SchPlaylistPT playlistToDelete = getPlaylistByChannelAndDate(networkShortcut, channelShortcut, date);
        playlistRepository.delete(playlistMapper.DTOToDB(playlistToDelete));
    }


    public SchPlaylistPT createOrUpdatePlaylist(String networkShortcut, SchPlaylistPT playlist) {
        return playlistMapper.DBToDTO(playlistRepository.saveAndFlush(playlistMapper.DTOToDB(playlist)));
    }

    public List<SchPlaylistPT> getAllPlaylistsByDate(String networkShortcut, String date) {
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
        List<SchPlaylistPT> toDeleteDAO = getAllPlaylistsByDate(networkShortcut, date);
        playlistRepository.delete(playlistMapper.DTOsToDBs(toDeleteDAO));
    }

    public SchPlaylistPT createOrUpdatePlaylist(String networkShortcut, String channelShortcut, SchPlaylistPT playlist) {
        return setPlaylist(networkShortcut, channelShortcut, playlist);
    }

}
