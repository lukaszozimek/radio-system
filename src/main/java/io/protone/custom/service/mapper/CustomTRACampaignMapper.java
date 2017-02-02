package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.SchEmissionPT;
import io.protone.custom.service.dto.TraCampaignPT;
import io.protone.custom.service.dto.TraCustomerPT;
import io.protone.domain.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

/**
 * Created by lukaszozimek on 21.01.2017.
 */
@Service
public class CustomTRACampaignMapper {

    @Inject
    private CustomSCHEmissionMapper emissionMapper;
    @Inject
    private CustomCRMAccountMapper crmAccountMapper;

    public TRACampaign transfromDTOToEntity(TraCampaignPT traCampaignPT,CORNetwork corNetwork) {
        TRACampaign traCampaign = new TRACampaign();
        traCampaign.setId(traCampaign.getId());
        return traCampaign
            .startDate(traCampaign.getStartDate())
            .endDate(traCampaign.getEndDate())
            .name(traCampaign.getName())
            .prize(traCampaign.getPrize())
            .cRMAccount(crmAccountMapper.createCrmAcountEntity(traCampaignPT.getCustomerId(),corNetwork))
            .emissions(traCampaignPT.getEmission().stream().map(schEmissionPT -> emissionMapper.createEmissionFromDTO(schEmissionPT)).collect(toSet()))
            .network(corNetwork);
    }

    public TraCampaignPT transfromEntitytoDTO(TRACampaign traCampaign) {
        return new TraCampaignPT()
            .name(traCampaign.getName())
            .prize(traCampaign.getPrize())
            .startDate(traCampaign.getStartDate())
            .endDate(traCampaign.getEndDate())
            .emission(emissionMapper.createDTOFromListEntites(traCampaign.getEmissions()))
            .customerId(crmAccountMapper.createCustomerTrafficDTO(traCampaign.getCustomer()));
    }


}
