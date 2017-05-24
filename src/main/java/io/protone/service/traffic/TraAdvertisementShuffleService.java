package io.protone.service.traffic;

import io.protone.web.rest.dto.traffic.TraShuffleAdvertisementDTO;
import io.protone.domain.TraPlaylist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private TraAdvertisementService traAdvertisementService;


    public List<TraPlaylist> shuffleCommercials(TraShuffleAdvertisementDTO tarShuffleAdvertisementPT, String networkShortcut, String channelShortcut) {
        log.debug("Start shuffling commercial");
        log.debug("Commercial to shuffle {}", tarShuffleAdvertisementPT.getNumber());
        //   TraAdvertisement traAdvertisement = traAdvertisementService.getAdvertisement(tarShuffleAdvertisementPT.getTraAdvertisementDTO().getId());
        //   log.debug("Found advertisment {}", traAdvertisement.getId());
        //   List<SchBlock> schBlockList = traPlaylistService.findByScheduledStartTimeBetweenAndType(tarShuffleAdvertisementPT.getFrom(), tarShuffleAdvertisementPT.getTo(), SchBlockTypeEnum.BT_COMMERCIAL);
        //  log.debug("Found number of blocks in range : {}", schBlockList.size());
        int numberOfCommercialsShuffled = 0;
        int numberOfScheduledBlocks = 0;// = schBlockList.size();
        for (int i = 0; i < numberOfScheduledBlocks; i++) {
            if (numberOfCommercialsShuffled != tarShuffleAdvertisementPT.getNumber()) {
                //            int blockIndex = java.util.concurrent.ThreadLocalRandom.current().nextInt(numberOfScheduledBlocks);
                //          log.debug("Check is it possible to shuffle commercial in block: {}", blockIndex);
                //*   List<SchEmission> schEmissionList = schEmissionRepository.findByBlock(schBlockList.get(blockIndex));
              /*   long numberOfAdvertisements = schEmissionList.stream()
                    .filter(schEmission ->
                        schEmission.getMediaItem().getIdx().equalsIgnoreCase(tarShuffleAdvertisementPT.getTraAdvertisementDTO().getMediaItemId().getIdx())).count();*/
                ///Add Filtering by lenghtScheduledTime
               /*  if (numberOfAdvertisements == 0) {
           //         SchBlock schBlock = schBlockList.get(blockIndex);
                    //     SchEmission emission = new SchEmission().block(schBlock).seq(schEmissionList.size() + 1).mediaItem(libItemMapper.DTO2DB(tarShuffleAdvertisementPT.getTraAdvertisementDTO().getMediaItemId()));
                    numberOfCommercialsShuffled++;*/

            } else {
                break;
            }
        }
        log.debug("Number shuffled: {}, Number unshuffled commercials : {}", numberOfCommercialsShuffled, tarShuffleAdvertisementPT.getNumber() - numberOfCommercialsShuffled);
        return null;
    }
}
