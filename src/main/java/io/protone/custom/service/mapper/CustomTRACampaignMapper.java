package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.TraCampaignPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.TraCampaign;
import io.protone.domain.TraPrice;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

/**
 * Created by lukaszozimek on 21.01.2017.
 */
@Service
public class CustomTRACampaignMapper {

    @Inject
    private CustomSchEmissionMapper emissionMapper;
    @Inject
    private CustomCrmAccountMapper crmAccountMapper;
    @Inject
    private CustomTraOrderMapper customTraOrderMapper;

    public TraCampaign transfromDTOToEntity(TraCampaignPT traCampaignPT, CorNetwork corNetwork) {
        if (traCampaignPT == null || corNetwork == null) {
            return null;
        }
        TraCampaign traCampaign = new TraCampaign();
        traCampaign.setId(traCampaignPT.getId());
        traCampaign
            .startDate(traCampaignPT.getStartDate())
            .endDate(traCampaignPT.getEndDate())
            .name(traCampaignPT.getName())
            .prize(traCampaignPT.getPrize())
            .customer(crmAccountMapper.createCrmAcountEntity(traCampaignPT.getCustomerId(), corNetwork))
            .status(traCampaignPT.getStatsus());
        if (traCampaign.getPrice()!=null) {
            traCampaign.price(new TraPrice());
        }
        if (traCampaignPT.getOrders() != null) {
            traCampaign.orders(customTraOrderMapper.trasnformDTOtoEntity(traCampaignPT.getOrders(),traCampaignPT.getId(), corNetwork).stream().collect(toSet()));
        }
        traCampaign.network(corNetwork);
        return traCampaign;
    }

    public TraCampaignPT transfromEntitytoDTO(TraCampaign traCampaign) {
        if (traCampaign == null) {
            return null;
        }
        TraCampaignPT traCampaignPT = new TraCampaignPT();
        traCampaignPT.setId(traCampaign.getId());
        return
            traCampaignPT.name(traCampaign.getName())
                .prize(traCampaign.getPrize())
                .startDate(traCampaign.getStartDate())
                .endDate(traCampaign.getEndDate())
                .customerId(crmAccountMapper.createCustomerTrafficDTO(traCampaign.getCustomer()))
                .status(traCampaign.getStatus())
                .orders(customTraOrderMapper.transfromEntitesToDTO(traCampaign.getOrders()).stream().collect(Collectors.toList()));
    }


}
