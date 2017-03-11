package io.protone.custom.service;

import io.protone.custom.service.dto.TraShuffleAdvertisementPT;
import io.protone.custom.service.mapper.CustomLibMediaItemMapper;
import io.protone.domain.SchBlock;
import io.protone.domain.SchEmission;
import io.protone.domain.TraAdvertisement;
import io.protone.domain.enumeration.SchBlockTypeEnum;
import io.protone.repository.custom.CustomSchBlockRepository;
import io.protone.repository.custom.CustomSchEmissionRepository;
import io.protone.repository.custom.CustomTraAdvertisementRepository;
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
    private CustomSchBlockRepository customSchBlockRepository;

    @Inject
    private CustomSchEmissionRepository schEmissionRepository;

    @Inject
    private CustomTraAdvertisementRepository customTraAdvertisementRepository;

    @Inject
    private CustomLibMediaItemMapper customLibMediaItemMapper;

    public void shuffleCommercials(TraShuffleAdvertisementPT tarShuffleAdvertisementPT) {
        log.debug("Start shuffling commercial");
        log.debug("Commercial to shuffle {}", tarShuffleAdvertisementPT.getNumber());
        TraAdvertisement traAdvertisement = customTraAdvertisementRepository.findOne(tarShuffleAdvertisementPT.getTraAdvertisementPT().getId());
        log.debug("Found advertisment {}", traAdvertisement.getId());
        List<SchBlock> schBlockList = customSchBlockRepository.findByScheduledStartTimeBetweenAndType(tarShuffleAdvertisementPT.getFrom(), tarShuffleAdvertisementPT.getTo(), SchBlockTypeEnum.BT_COMMERCIAL);
        log.debug("Found number of blocks in range : {}", schBlockList.size());
        int numberOfCommercialsShuffled = 0;
        int numberOfScheduledBlocks = schBlockList.size();
        for (int i = 0; i < numberOfScheduledBlocks; i++) {
            if (numberOfCommercialsShuffled != tarShuffleAdvertisementPT.getNumber()) {
                int blockIndex = java.util.concurrent.ThreadLocalRandom.current().nextInt(numberOfScheduledBlocks);
                log.debug("Check is it possible to shuffle commercial in block: {}", blockIndex);
                List<SchEmission> schEmissionList = schEmissionRepository.findByBlock(schBlockList.get(blockIndex));
                long numberOfAdvertisements = schEmissionList.stream()
                    .filter(schEmission ->
                        schEmission.getMediaItem().getIdx().equalsIgnoreCase(tarShuffleAdvertisementPT.getTraAdvertisementPT().getMediaItemId().getIdx())).count();

                ///Add Filtering by lenghtScheduledTime
                if (numberOfAdvertisements == 0) {
                    SchBlock schBlock = schBlockList.get(blockIndex);
                    SchEmission emission = new SchEmission().block(schBlock).seq(schEmissionList.size() + 1).mediaItem(customLibMediaItemMapper.lIBMediaItemPTToLibMediaItem(tarShuffleAdvertisementPT.getTraAdvertisementPT().getMediaItemId()));
                    numberOfCommercialsShuffled++;
                }
            } else {
                break;
            }
        }
        log.debug("Number shuffled: {}, Number unshuffled commercials : {}", numberOfCommercialsShuffled, tarShuffleAdvertisementPT.getNumber() - numberOfCommercialsShuffled);
    }
}
