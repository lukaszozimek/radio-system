package io.protone.traffic.service;


import io.protone.library.service.LibItemService;
import io.protone.traffic.api.dto.TraShuffleAdvertisementDTO;
import io.protone.traffic.domain.TraPlaylist;
import io.protone.traffic.service.shuffle.TraAdvertismentShuffle;
import io.protone.traffic.service.shuffle.exception.TrafficShuffleReindexException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lukaszozimek on 06/03/2017.
 */
@Service
public class TraAdvertisementShuffleService {

    private final Logger log = LoggerFactory.getLogger(TraAdvertisementShuffleService.class);

    @Inject
    private TraPlaylistService traPlaylistService;

    @Inject
    private LibItemService libItemService;
    @Autowired
    @Qualifier("traAdvertismentShuffleDefault")
    private TraAdvertismentShuffle traAdvertismentShuffleDefault;

    @Autowired
    @Qualifier("traAdverstimentShuffleFistInBlock")
    private TraAdvertismentShuffle traAdverstimentShuffleFistInBlock;

    @Autowired
    @Qualifier("traAdverstimentShuffleLastInBlock")
    private TraAdvertismentShuffle traAdverstimentShuffleLastInBlock;


    public static boolean canAddEmissionToBlock(long lastTimeStop, long blockLenght, Double mediaItemLenght) {
        return lastTimeStop < blockLenght && (lastTimeStop + mediaItemLenght.longValue()) <= blockLenght;
    }

    public List<TraPlaylist> shuffleCommercials(TraShuffleAdvertisementDTO tarShuffleAdvertisementPT, String networkShortcut, String channelShortcut) throws InterruptedException, TrafficShuffleReindexException {
        log.debug("Selecting Shuffling strategy");
        if (tarShuffleAdvertisementPT.getTraShuffleAdvertisementOptionalDTO().isFirstPosition()) {
            log.debug("Selecting first postion startegy");
            return traAdverstimentShuffleFistInBlock.shuffleCommercials(tarShuffleAdvertisementPT, networkShortcut, channelShortcut);

        } else if (tarShuffleAdvertisementPT.getTraShuffleAdvertisementOptionalDTO().isLastPostion()) {
            log.debug("Selecting last postion startegy");
            return traAdverstimentShuffleLastInBlock.shuffleCommercials(tarShuffleAdvertisementPT, networkShortcut, channelShortcut);
        }
        log.debug("Selecting default postion startegy");
        return traAdvertismentShuffleDefault.shuffleCommercials(tarShuffleAdvertisementPT, networkShortcut, channelShortcut);
    }
}

