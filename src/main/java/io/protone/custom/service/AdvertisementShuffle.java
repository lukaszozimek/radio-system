package io.protone.custom.service;

import java.util.List;

import io.protone.custom.service.dto.TraShuffleAdvertismentPT;
import io.protone.custom.service.mapper.CustomLibMediaItemMapper;
import io.protone.domain.SchBlock;
import io.protone.domain.SchEmission;
import io.protone.domain.TraAdvertisement;
import io.protone.domain.enumeration.SchBlockTypeEnum;
import io.protone.repository.custom.CustomSchBlockRepository;
import io.protone.repository.custom.CustomSchEmissionRepository;
import io.protone.repository.custom.CustomTraAdvertisementRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by lukaszozimek on 06/03/2017.
 */
@Service
public class AdvertisementShuffle {

    @Inject
    private CustomSchBlockRepository customSchBlockRepository;

    @Inject
    private CustomSchEmissionRepository schEmissionRepository;

    @Inject
    private CustomTraAdvertisementRepository customTraAdvertisementRepository;
    @Inject
    private CustomLibMediaItemMapper customLibMediaItemMapper;

    public void schuffleCommercials(TraShuffleAdvertismentPT traShuffleAdvertismentPT) {
        TraAdvertisement traAdvertisement = customTraAdvertisementRepository.findOne(traShuffleAdvertismentPT.getTraAdvertisementPT().getId());
        traAdvertisement.getMediaItem();
        List<SchBlock> schBlockList = customSchBlockRepository.findByScheduledStartTimeBetweenAndType(traShuffleAdvertismentPT.getFrom(), traShuffleAdvertismentPT.getTo(), SchBlockTypeEnum.BT_COMMERCIAL);
        int lastSelectedIndex = 0;
        int numberOfCommercialsSchuffeled = 0;
        int numberOfScheduledBlocks = schBlockList.size();
        while (numberOfCommercialsSchuffeled < numberOfScheduledBlocks) {
            int blockIndex = java.util.concurrent.ThreadLocalRandom.current().nextInt(0, numberOfScheduledBlocks);
            if (lastSelectedIndex != blockIndex) {
                lastSelectedIndex = blockIndex;
                List<SchEmission> schEmissionList = schEmissionRepository.findByBlock(schBlockList.get(blockIndex));
                long numberOfAdvertisements = schEmissionList.stream().filter(schEmission -> schEmission.getMediaItem().getIdx().equalsIgnoreCase(traShuffleAdvertismentPT.getTraAdvertisementPT().getMediaItemId().getIdx())).count();
                if (numberOfAdvertisements == 0) {
                    SchBlock schBlock = schBlockList.get(blockIndex);
                    SchEmission emission = new SchEmission().block(schBlock).seq(schEmissionList.size() + 1).mediaItem(customLibMediaItemMapper.lIBMediaItemPTToLibMediaItem(traShuffleAdvertismentPT.getTraAdvertisementPT().getMediaItemId()));
                    schEmissionRepository.save(emission);
                    numberOfCommercialsSchuffeled++;
                } else {

                }
            }

        }
    }

}
