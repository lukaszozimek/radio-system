package io.protone.traffic.service.shuffle;

import io.protone.traffic.api.dto.TraShuffleAdvertisementDTO;
import io.protone.traffic.domain.TraPlaylist;
import io.protone.traffic.service.shuffle.exception.TrafficShuffleReindexException;

import java.util.List;

/**
 * Created by lukaszozimek on 31/07/2017.
 */
public interface TraAdvertismentShuffle {
     List<TraPlaylist> shuffleCommercials(TraShuffleAdvertisementDTO tarShuffleAdvertisementPT, String organizationShortcut, String channelShortcut) throws InterruptedException, TrafficShuffleReindexException;

}
