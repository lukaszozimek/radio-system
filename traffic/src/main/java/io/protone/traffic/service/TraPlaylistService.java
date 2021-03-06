package io.protone.traffic.service;


import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorChannelService;
import io.protone.core.service.CorNetworkService;
import io.protone.traffic.domain.TraPlaylist;
import io.protone.traffic.repository.TraPlaylistRepository;
import io.protone.traffic.service.mediaplan.TraPlaylistMediaPlanMappingService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Created by lukaszozimek on 15/05/2017.
 */
@Service
public class TraPlaylistService {

    @Inject
    private CorNetworkService corNetworkService;

    @Inject
    private CorChannelService corChannelService;

    @Inject
    private TraPlaylistRepository traPlaylistRepository;

    @Inject
    private TraBlockService traBlockService;

    @Inject
    private TraPlaylistMediaPlanMappingService traPlaylistMediaPlanMappingService;

    @Inject
    private TraMediaPlanService traMediaPlanService;

    @Transactional
    public TraPlaylist savePlaylist(TraPlaylist traPlaylistList) {
        traPlaylistList.playlists(traBlockService.traSaveBlockSet(traPlaylistList.getPlaylists()));
        return traPlaylistRepository.saveAndFlush(traPlaylistList);
    }

    @Transactional
    public List<TraPlaylist> getTraPlaylistListInRange(LocalDate from, LocalDate to, String networkshortcut, String channelShortcut) {
        long difference = ChronoUnit.DAYS.between(from, to);
        List<TraPlaylist> traPlaylists = Lists.newArrayList();
        if(difference ==0){
            TraPlaylist traPlaylist = getTraPlaylistList(from.plusDays(difference), networkshortcut, channelShortcut);
            if (traPlaylist == null) {
                TraPlaylist createdPlaylist = createPlaylistIfWasNotCreatedEarlier(from.plusDays(difference), networkshortcut, channelShortcut);
                traPlaylists.add(createdPlaylist);
            } else if (traPlaylist.getPlaylists().isEmpty()) {
                TraPlaylist createdPlaylist = createPlaylistIfBlockConfigurationWasnNotProvided(from.plusDays(difference), traPlaylist);
                traPlaylists.add(createdPlaylist);
            } else {
                traPlaylists.add(traPlaylist);

            }
        }
        for (int i = 0; i < difference; i++) {
            TraPlaylist traPlaylist = getTraPlaylistList(from.plusDays(i), networkshortcut, channelShortcut);
            if (traPlaylist == null) {
                TraPlaylist createdPlaylist = createPlaylistIfWasNotCreatedEarlier(from.plusDays(i), networkshortcut, channelShortcut);
                traPlaylists.add(createdPlaylist);
            } else if (traPlaylist.getPlaylists().isEmpty()) {
                TraPlaylist createdPlaylist = createPlaylistIfBlockConfigurationWasnNotProvided(from.plusDays(i), traPlaylist);
                traPlaylists.add(createdPlaylist);
            } else {
                traPlaylists.add(traPlaylist);

            }
        }
        return traPlaylists;
    }

    @Transactional
    public TraPlaylist getTraPlaylistList(LocalDate date, String networkshortcut, String channelShortcut) {
        TraPlaylist traPlaylist = traPlaylistRepository.findOneByPlaylistDateAndNetwork_ShortcutAndChannel_Shortcut(date, networkshortcut, channelShortcut);
        if (traPlaylist == null) {
            TraPlaylist createdPlaylist = createIfNotExist(date, networkshortcut, channelShortcut);
            createdPlaylist = savePlaylist(createdPlaylist);
            return createdPlaylist;
        } else if (traPlaylist.getPlaylists().isEmpty()) {
            TraPlaylist createdPlaylist = createPlaylistIfBlockConfigurationWasnNotProvided(date, traPlaylist);
            return createdPlaylist;
        }
        return traPlaylist;
    }

    @Transactional
    public Slice<TraPlaylist> getAllPlaylistList(String networkshortcut, String channelShortcut, Pageable pageable) {
        return traPlaylistRepository.findSliceByNetwork_ShortcutAndChannel_Shortcut(networkshortcut, channelShortcut, pageable);
    }


    @Transactional
    public void deleteOneTraPlaylistList(LocalDate date, String networkshortcut, String channelShortcut) {
        TraPlaylist traPlaylist = traPlaylistRepository.findOneByPlaylistDateAndNetwork_ShortcutAndChannel_Shortcut(date, networkshortcut, channelShortcut);
        if (traPlaylist == null) {
            throw new EntityNotFoundException();
        }
        traBlockService.deleteBlockSet(traPlaylist.getPlaylists());
        traPlaylistRepository.delete(traPlaylist);
    }

    private TraPlaylist createPlaylistIfWasNotCreatedEarlier(LocalDate localDate, String networkshortcut, String channelShortcut) {
        TraPlaylist createdPlaylist = createIfNotExist(localDate, networkshortcut, channelShortcut);
        return savePlaylist(createdPlaylist);
    }

    private TraPlaylist createPlaylistIfBlockConfigurationWasnNotProvided(LocalDate localDate, TraPlaylist traPlaylistWithoutBlocks) {
        TraPlaylist createdPlaylist = createBlocksIfConfigurationWasNotProvided(localDate, traPlaylistWithoutBlocks);
        return savePlaylist(createdPlaylist);
    }

    private TraPlaylist createIfNotExist(LocalDate localDate, String networkshortcut, String channelShortcut) {
        CorNetwork corNetwork = corNetworkService.findNetwork(networkshortcut);
        CorChannel corChannel = corChannelService.findChannel(networkshortcut, channelShortcut);
        return new TraPlaylist().network(corNetwork).channel(corChannel).playlistDate(localDate).playlists(traBlockService.buildBlocks(localDate, networkshortcut));
    }

    private TraPlaylist createBlocksIfConfigurationWasNotProvided(LocalDate localDate, TraPlaylist traPlaylistWithoutBlocks) {

        TraPlaylist traPlaylist = new TraPlaylist().network(traPlaylistWithoutBlocks.getNetwork()).channel((traPlaylistWithoutBlocks.getChannel())).playlistDate((traPlaylistWithoutBlocks.getPlaylistDate())).playlists(traBlockService.buildBlocks(localDate, traPlaylistWithoutBlocks.getNetwork().getShortcut()));
        traPlaylist.setId(traPlaylistWithoutBlocks.getId());
        return traPlaylist;
    }

    @VisibleForTesting
    public void deleteAllPlaylist() {
        List<TraPlaylist> list = traPlaylistRepository.findAll();
        list.stream().forEach(traPlaylist -> {
            this.deleteOneTraPlaylistList(traPlaylist.getPlaylistDate(), traPlaylist.getNetwork().getShortcut(), traPlaylist.getChannel().getShortcut());
        });
    }

}
