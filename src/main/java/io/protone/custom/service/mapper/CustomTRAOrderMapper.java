package io.protone.custom.service.mapper;

import io.protone.custom.service.TRACustomerService;
import io.protone.custom.service.dto.TraOrderPT;
import io.protone.domain.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 21.01.2017.
 */
@Service
public class CustomTRAOrderMapper {

    @Inject
    private CustomSCHEmissionMapper schEmissionMapper;

    @Inject
    private CustomCRMAccountMapper customCRMAccountMapper;

    @Inject
    private CustomTRACampaignMapper customTRACampaignMapper;

    @Inject
    private CustomSCHEmissionMapper customSCHEmissionMapper;

    @Inject
    private TRACustomerService customerService;

    public List<TRAOrder> trasnformDTOtoEntity(List<TraOrderPT> traOrderPT, CORNetwork corNetwork) {
        List<TRAOrder> traOrdersList = new ArrayList<>();
        traOrderPT.stream().forEach(traOrderPT1 -> {
            traOrdersList.add(trasnformDTOtoEntity(traOrderPT1, corNetwork));
        });
        return traOrdersList;
    }

    public TRAOrder trasnformDTOtoEntity(TraOrderPT traOrderPT, CORNetwork corNetwork) {
        TRAOrder traOrder = new TRAOrder();
        traOrder.setId(traOrder.getId());
        return traOrder.name(traOrder.getName())
            .calculatedPrize(traOrderPT.getCalculatedPrize())
            .startDate(traOrder.getStartDate())
            .endDate(traOrder.getEndDate())
            .campaign(customTRACampaignMapper.transfromDTOToEntity(traOrderPT.getTraCampaignPT(), corNetwork))
            .customer(customCRMAccountMapper.createCrmAcountEntity(traOrderPT.getCustomerPT(), corNetwork))
            .price(new TRAPrice());
    }

    public List<TraOrderPT> transfromEntitesToDTO(Set<TRAOrder> traOrder) {
        return traOrder.stream().map(this::transfromEntiteToDTO).collect(toList());
    }

    public TraOrderPT transfromEntiteToDTO(TRAOrder traOrder) {
        return new TraOrderPT().id(traOrder.getId()).name(traOrder.getName())
            .calculatedPrize(traOrder.getCalculatedPrize())
            .startDate(traOrder.getStartDate())
            .endDate(traOrder.getEndDate())
            .campaignId(customTRACampaignMapper.transfromEntitytoDTO(traOrder.getCampaign()))
            .customerId(customCRMAccountMapper.createCustomerTrafficDTO(traOrder.getCRMAccount()));

    }


}
