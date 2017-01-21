package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.TraOrderPT;
import io.protone.domain.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public TRAOrder trasnformDTOtoEntity(TraOrderPT traOrderPT) {
        TRAOrder traOrder = new TRAOrder();
        traOrder.setId(traOrder.getId());
        return traOrder.name(traOrder.getName())
            .calculatedPrize(traOrderPT.getCalculatedPrize())
            .startDate(traOrder.getStartDate())
            .endDate(traOrder.getEndDate());
    }

    public TraOrderPT transfromEntitesToDTO(TRAOrder traOrder, List<SCHEmission> schEmissionList, TRACampaign campaign, CRMAccount crmAccount) {
        return new TraOrderPT().id(traOrder.getId()).name(traOrder.getName())
            .calculatedPrize(traOrder.getCalculatedPrize())
            .startDate(traOrder.getStartDate())
            .endDate(traOrder.getEndDate())
            .emission(schEmissionMapper.createDTOFromListEntites(new HashMap<>()))
            .customerId(customCRMAccountMapper.createCustomerTrafficDTO(crmAccount))
            .campaignId(customTRACampaignMapper.transfromEntitytoDTO(campaign, customSCHEmissionMapper.createDTOFromListEntites(new HashMap<>()), customCRMAccountMapper.createCustomerTrafficDTO(crmAccount)));
    }


    public List<CORAssociation> createScheduledEmissionAssociationList(TRAOrder order, List<SCHEmission> schEmissionsList) {
        List<CORAssociation> corAssociationList = new ArrayList<>();
        schEmissionsList.stream().forEach(schEmission -> {
            corAssociationList.add(createScheduledEmissionAssociation(order, schEmission));
        });
        return corAssociationList;
    }

    public CORAssociation createScheduledEmissionAssociation(TRAOrder traOrder, SCHEmission schEmission) {
        return new CORAssociation().name("ORDER SCHEDULED EMISSION")
            .sourceId(traOrder.getId())
            .sourceClass(TRAOrder.class.getName())
            .targetId(schEmission.getId())
            .targetClass(SCHEmission.class.getName());
    }

    public CORAssociation createCrmAccountAssociation(TRAOrder traOrder, CRMAccount crmAccount) {
        return new CORAssociation().name("ORDER customer")
            .sourceId(traOrder.getId())
            .sourceClass(TRAOrder.class.getName())
            .targetId(crmAccount.getId())
            .targetClass(CRMAccount.class.getName());
    }

    public CORAssociation createTraCampaignAssociation(TRAOrder traOrder, TRACampaign campaign) {
        return new CORAssociation().name("ORDER CAMPAIGN")
            .sourceId(traOrder.getId())
            .sourceClass(TRAOrder.class.getName())
            .targetId(campaign.getId())
            .targetClass(TRACampaign.class.getName());
    }

}
