package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.SchEmissionPT;
import io.protone.custom.service.dto.TraOrderPT;
import io.protone.domain.TRAOrder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 21.01.2017.
 */
@Service
public class CustomTRAOrderMapper {

    @Inject
    private CustomSCHEmissionMapper customSCHEmissionMapper;

    @Inject
    private CustomTRACampaignMapper customTRACampaignMapper;

    @Inject
    private CustomTRACustomerMapper customTRACustomerMapper;

    public TRAOrder trasnformDTOtoEntity(TraOrderPT traOrderPT) {
        TRAOrder traOrder = new TRAOrder();
        traOrder.setId(traOrder.getId());
        return traOrder.name(traOrder.getName())
            .calculatedPrize(traOrderPT.getCalculatedPrize())
            .campaign(customTRACampaignMapper.transfromDTOToEntity(traOrderPT.getCampaignId()))
            .customer(customTRACustomerMapper.transformDTOtoEntity(traOrderPT.getCustomerId()))
            .startDate(traOrder.getStartDate())
            .endDate(traOrder.getEndDate());
    }

    public TraOrderPT transfromEntitesToDTO(TRAOrder traOrder, List<SchEmissionPT> schEmissionPTS) {
        return new TraOrderPT().id(traOrder.getId()).name(traOrder.getName())
            .calculatedPrize(traOrder.getCalculatedPrize())
            .campaignId(customTRACampaignMapper.transfromEntitytoDTO(traOrder.getCampaign(), schEmissionPTS))
            .customerId(customTRACustomerMapper.transformEntityToDTO(traOrder.getCustomer()))
            .startDate(traOrder.getStartDate())
            .endDate(traOrder.getEndDate());
    }
}
