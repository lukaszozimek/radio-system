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
    public List<TraPlaylist> getTraPlaylistListInRange(LocalDate from, LocalDate to, String organizationShortcut, String channelShortcut) {
        long difference = ChronoUnit.DAYS.between(from, to);
        List<TraPlaylist> traPlaylists = Lists.newArrayList();
        if (difference == 0) {
            TraPlaylist traPlaylist = getTraPlaylistList(from.plusDays(difference), organizationShortcut, channelShortcut);
            if (traPlaylist == null) {
                TraPlaylist createdPlaylist = createPlaylistIfWasNotCreatedEarlier(from.plusDays(difference), organizationShortcut, channelShortcut);
                traPlaylists.add(createdPlaylist);
            } else if (traPlaylist.getPlaylists().isEmpty()) {
                TraPlaylist createdPlaylist = createPlaylistIfBlockConfigurationWasnNotProvided(from.plusDays(difference), traPlaylist);
                traPlaylists.add(createdPlaylist);
            } else {
                traPlaylists.add(traPlaylist);

            }
        }
        for (int i = 0; i < difference; i++) {
            TraPlaylist traPlaylist = getTraPlaylistList(from.plusDays(i), organizationShortcut, channelShortcut);
            if (traPlaylist == null) {
                TraPlaylist createdPlaylist = createPlaylistIfWasNotCreatedEarlier(from.plusDays(i), organizationShortcut, channelShortcut);
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
    public TraPlaylist getTraPlaylistList(LocalDate date, String organizationShortcut, String channelShortcut) {
        TraPlaylist traPlaylist = traPlaylistRepository.findOneByPlaylistDateAndChannel_Organization_ShortcutAndChannel_Shortcut(date, organizationShortcut, channelShortcut);
        if (traPlaylist == null) {
            TraPlaylist createdPlaylist = createIfNotExist(date, organizationShortcut, channelShortcut);
            createdPlaylist = savePlaylist(createdPlaylist);
            return createdPlaylist;
        } else if (traPlaylist.getPlaylists().isEmpty()) {
            TraPlaylist createdPlaylist = createPlaylistIfBlockConfigurationWasnNotProvided(date, traPlaylist);
            return createdPlaylist;
        }
        return traPlaylist;
    }

    @Transactional
    public Slice<TraPlaylist> getAllPlaylistList(String organizationShortcut, String channelShortcut, Pageable pageable) {
        return traPlaylistRepository.findSliceByChannel_Organization_ShortcutAndChannel_Shortcut(organizationShortcut, channelShortcut, pageable);
    }


    @Transactional
    public void deleteOneTraPlaylistList(LocalDate date, String organizationShortcut, String channelShortcut) {
        TraPlaylist traPlaylist = traPlaylistRepository.findOneByPlaylistDateAndChannel_Organization_ShortcutAndChannel_Shortcut(date, organizationShortcut, channelShortcut);
        if (traPlaylist == null) {
            throw new EntityNotFoundException();
        }
        traBlockService.deleteBlockSet(traPlaylist.getPlaylists());
        traPlaylistRepository.delete(traPlaylist);
    }

    private TraPlaylist createPlaylistIfWasNotCreatedEarlier(LocalDate localDate, String organizationShortcut, String channelShortcut) {
        TraPlaylist createdPlaylist = createIfNotExist(localDate, organizationShortcut, channelShortcut);
        return savePlaylist(createdPlaylist);
    }

    private TraPlaylist createPlaylistIfBlockConfigurationWasnNotProvided(LocalDate localDate, TraPlaylist traPlaylistWithoutBlocks) {
        TraPlaylist createdPlaylist = createBlocksIfConfigurationWasNotProvided(localDate, traPlaylistWithoutBlocks);
        return savePlaylist(createdPlaylist);
    }

    private TraPlaylist createIfNotExist(LocalDate localDate, String organizationShortcut, String channelShortcut) {
        CorChannel corChannel = corChannelService.findChannel(organizationShortcut, channelShortcut);
        return new TraPlaylist().channel(corChannel).playlistDate(localDate).playlists(traBlockService.buildBlocks(localDate, organizationShortcut, channelShortcut));
    }

    private TraPlaylist createBlocksIfConfigurationWasNotProvided(LocalDate localDate, TraPlaylist traPlaylistWithoutBlocks) {

        TraPlaylist traPlaylist = new TraPlaylist().channel((traPlaylistWithoutBlocks.getChannel())).playlistDate((traPlaylistWithoutBlocks.getPlaylistDate())).playlists(traBlockService.buildBlocks(localDate, traPlaylistWithoutBlocks.getChannel().getOrganization().getShortcut(), traPlaylistWithoutBlocks.getChannel().getShortcut()));
        traPlaylist.setId(traPlaylistWithoutBlocks.getId());
        return traPlaylist;
    }

}
