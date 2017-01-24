package io.protone.custom.service;

import io.protone.custom.service.dto.TraCampaignPT;
import io.protone.custom.service.dto.TraOrderPT;
import io.protone.custom.service.mapper.CustomCRMAccountMapper;
import io.protone.custom.service.mapper.CustomSCHEmissionMapper;
import io.protone.custom.service.mapper.CustomTRACampaignMapper;
import io.protone.custom.service.mapper.CustomTRAOrderMapper;
import io.protone.domain.*;
import io.protone.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service
@Transactional
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

    @Inject
    private CORAssociationRepository corAssociationRepository;

    @Inject
    private CRMAccountRepository crmAccountRepository;

    @Inject
    private TRACampaignRepository campaignRepository;

    public List<TraOrderPT> getAllOrder() {
        List<TraOrderPT> traOrderPTS = new ArrayList<>();
        List<TRAOrder> traOrderList = traOrderRepository.findAll();
        traOrderList.stream().forEach(traOrder -> traOrderPTS.add(getOrdersByEntitie(traOrder)));
        return traOrderPTS;
    }

    public TraOrderPT saveOrder(TraOrderPT orderPT) {
        TRAOrder traOrder = customTRAOrderMapper.trasnformDTOtoEntity(orderPT);
        traOrderRepository.save(traOrder);
        List<SCHEmission> schEmissionList = customSCHEmissionMapper.createListEmissionFromListDTO(orderPT.getEmission());
        TRACampaign traCampaigns = customTRACampaignMapper.transfromDTOToEntity(orderPT.getTraCampaignPT());
        CRMAccount crmAccount = customCRMAccountMapper.createCrmAcountEntity(orderPT.getCustomerPT());
        corAssociationRepository.save(customTRAOrderMapper.createScheduledEmissionAssociationList(traOrder, schEmissionList));
        corAssociationRepository.save(customTRAOrderMapper.createCrmAccountAssociation(traOrder, crmAccount));
        corAssociationRepository.save(customTRAOrderMapper.createTraCampaignAssociation(traOrder, traCampaigns));
        return customTRAOrderMapper.transfromEntitesToDTO(traOrder, schEmissionList, traCampaigns, crmAccount);
    }

    public void deleteOrder(Long id) {
        List<CORAssociation> corAssociationList = new ArrayList<>();
        TRAOrder traOrder = traOrderRepository.findOne(id);
        corAssociationRepository.deleteBySourceIdAndTargetClass(traOrder.getId(), CRMAccount.class.getName());
        corAssociationRepository.deleteBySourceIdAndTargetClass(traOrder.getId(), SCHEmission.class.getName());
        corAssociationRepository.deleteBySourceIdAndTargetClass(traOrder.getId(), TRACampaign.class.getName());
        traOrderRepository.delete(traOrder);
    }

    public TraOrderPT getOrder(Long id) {
        TRAOrder traOrder = traOrderRepository.findOne(id);
        return getOrdersByEntitie(traOrder);
    }

    public List<TraOrderPT> getOrdersById(List<Long> id) {
        List<TRAOrder> traOrderList = traOrderRepository.findAll(id);
        return getOrdersByEntitie(traOrderList);
    }

    public TraOrderPT update(TraOrderPT traOrderPT) {
        deleteOrder(traOrderPT.getId());
        return saveOrder(traOrderPT);
    }

    public List<TraOrderPT> getOrdersByEntitie(List<TRAOrder> traOrders) {
        return traOrders.stream().map(this::getOrdersByEntitie).collect(toList());
    }

    public TraOrderPT getOrdersByEntitie(TRAOrder traOrders) {
        List<CORAssociation> orderAccountAssociation = corAssociationRepository.findBySourceIdAndTargetClass(traOrders.getId(), CRMAccount.class.getName());
        List<CORAssociation> schEmissionOrderAssociation = corAssociationRepository.findBySourceIdAndTargetClass(traOrders.getId(), SCHEmission.class.getName());
        List<CORAssociation> orderCampaignAssociation = corAssociationRepository.findBySourceIdAndTargetClass(traOrders.getId(), TRACampaign.class.getName());
        CRMAccount crmAccount = crmAccountRepository.findOne(orderAccountAssociation.get(0).getTargetId());
        List<Long> schEmissionIds = schEmissionOrderAssociation.stream().map(CORAssociation::getTargetId).collect(toList());
        List<SCHEmission> schEmissionList = schEmissionRepository.findAll(schEmissionIds);
        TRACampaign traCampaign = campaignRepository.findOne(orderCampaignAssociation.get(0).getTargetId());
        return customTRAOrderMapper.transfromEntitesToDTO(traOrders, schEmissionList, traCampaign, crmAccount);
    }

    public List<TraOrderPT> getCustomerOrders(String shortcut) {
        CRMAccount crmAccount = crmAccountRepository.findByShortName(shortcut);
        List<CORAssociation> associations = corAssociationRepository.findByTargetIdAndSourceClass(crmAccount.getId(), TRAOrder.class.getName());
        return getOrdersById(associations.stream().map(CORAssociation::getSourceId).collect(toList()));
    }

}
