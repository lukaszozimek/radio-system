package io.protone.custom.service;

import io.protone.custom.service.dto.TraOrderPT;
import io.protone.custom.service.mapper.CustomCRMAccountMapper;
import io.protone.custom.service.mapper.CustomSCHEmissionMapper;
import io.protone.custom.service.mapper.CustomTRACampaignMapper;
import io.protone.custom.service.mapper.CustomTRAOrderMapper;
import io.protone.domain.*;
import io.protone.repository.CORAssociationRepository;
import io.protone.repository.SCHEmissionRepository;
import io.protone.repository.TRAOrderRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service
public class TRAOrderService {

    @Inject
    private CustomTRAOrderMapper customTRAOrderMapper;

    @Inject
    private CORAssociationRepository associationRepository;

    @Inject
    private TRAOrderRepository traOrderRepository;

    @Inject
    private CustomSCHEmissionMapper customSCHEmissionMapper;

    @Inject
    private CustomTRACampaignMapper customTRACampaignMapper;

    @Inject
    private SCHEmissionRepository schEmissionRepository;
    @Inject
    private CustomCRMAccountMapper customCRMAccountMapper;

    public List<TraOrderPT> getAllOrder() {
        List<CORAssociation> corAssociationList = new ArrayList<>();
        return null;
    }

    public TraOrderPT saveOrder(TraOrderPT orderPT) {
        List<CORAssociation> corAssociationList = new ArrayList<>();
        TRAOrder order = customTRAOrderMapper.trasnformDTOtoEntity(orderPT);
        traOrderRepository.save(order);
        List<SCHEmission> emissions = customSCHEmissionMapper.createListEmissionFromListDTO(orderPT.getEmission());
        TRACampaign campaign = customTRACampaignMapper.transfromDTOToEntity(orderPT.getCampaignId());
        CRMAccount crmAccount = customCRMAccountMapper.createCrmAcountEntity(orderPT.getCustomerId());
        emissions = schEmissionRepository.save(emissions);
        corAssociationList.add(customTRAOrderMapper.createCrmAccountAssociation(order, crmAccount));
        corAssociationList.addAll(customTRAOrderMapper.createScheduledEmissionAssociationList(order, emissions));
        corAssociationList.add(customTRAOrderMapper.createTraCampaignAssociation(order, campaign));

        return customTRAOrderMapper.transfromEntitesToDTO(order,emissions,campaign,crmAccount);
    }

    public void deleteOrder(Long id) {
        List<CORAssociation> corAssociationList = new ArrayList<>();

    }

    public TraOrderPT getOrder(Long id) {
        List<CORAssociation> corAssociationList = new ArrayList<>();
        return null;
    }

    public List<TraOrderPT> getCustomerOrders(String shortcut) {
        List<CORAssociation> corAssociationList = new ArrayList<>();
        return null;
    }

}
