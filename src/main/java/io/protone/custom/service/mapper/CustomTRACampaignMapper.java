package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.SchEmissionPT;
import io.protone.custom.service.dto.TraCampaignPT;
import io.protone.custom.service.dto.TraCustomerPT;
import io.protone.domain.*;
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

    public TraCampaignPT transfromEntitytoDTO(TRACampaign traCampaign, List<SchEmissionPT> emissionPTList, TraCustomerPT traCustomerPT) {
        return new TraCampaignPT()
            .name(traCampaign.getName())
            .prize(traCampaign.getPrize())
            .startDate(traCampaign.getStartDate())
            .endDate(traCampaign.getEndDate())
            .emission(emissionPTList)
            .customerId(traCustomerPT);
    }
    public CORAssociation createSCHEmissionAssociation(TRACampaign campaign, SCHEmission emission) {
        return new CORAssociation().name("ORDER SCHEDULED EMISSION")
            .sourceId(campaign.getId())
            .sourceClass(TRACampaign.class.getName())
            .targetId(emission.getId())
            .targetClass(SCHEmission.class.getName());
    }

    public CORAssociation createCrmAccountAssociation(TRACampaign campaign, CRMAccount crmAccount) {
        return new CORAssociation().name("ORDER customer")
            .sourceId(campaign.getId())
            .sourceClass(TRAInvoice.class.getName())
            .targetId(crmAccount.getId())
            .targetClass(CRMAccount.class.getName());
    }

}
