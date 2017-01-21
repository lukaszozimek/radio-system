package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.SchEmissionPT;
import io.protone.custom.service.dto.TraCampaignPT;
import io.protone.domain.TRACampaign;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lukaszozimek on 21.01.2017.
 */
@Service
public class CustomTRACampaignMapper {


    public TRACampaign transfromDTOToEntity(TraCampaignPT traCampaignPT) {
        TRACampaign traCampaign = new TRACampaign();
        traCampaign.setId(traCampaign.getId());
        return traCampaign
            .startDate(traCampaign.getStartDate())
            .endDate(traCampaign.getEndDate())
            .name(traCampaign.getName())
            .prize(traCampaign.getPrize());

    }

    public TraCampaignPT transfromEntitytoDTO(TRACampaign traCampaign, List<SchEmissionPT> emissionPTList) {
        return new TraCampaignPT()
            .name(traCampaign.getName())
            .prize(traCampaign.getPrize())
            .startDate(traCampaign.getStartDate())
            .endDate(traCampaign.getEndDate())
            .emission(emissionPTList);
    }

}
