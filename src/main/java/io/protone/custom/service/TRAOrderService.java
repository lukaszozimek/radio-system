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

    public List<TraOrderPT> getAllOrder(CORNetwork corNetwork) {
        return traOrderRepository.findByNetwork(corNetwork).stream().map(traOrder -> getOrdersByEntitie(traOrder, corNetwork)).collect(toList());
    }

    public TraOrderPT saveOrder(TraOrderPT orderPT, CORNetwork corNetwork) {
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

    public void deleteOrder(Long id, CORNetwork corNetwork) {
        List<CORAssociation> corAssociationList = new ArrayList<>();
        TRAOrder traOrder = traOrderRepository.findOne(id);
        corAssociationRepository.deleteBySourceIdAndTargetClassAndNetwork(traOrder.getId(), CRMAccount.class.getName(), corNetwork);
        corAssociationRepository.deleteBySourceIdAndTargetClassAndNetwork(traOrder.getId(), SCHEmission.class.getName(), corNetwork);
        corAssociationRepository.deleteBySourceIdAndTargetClassAndNetwork(traOrder.getId(), TRACampaign.class.getName(), corNetwork);
        traOrderRepository.delete(traOrder);
    }

    public TraOrderPT getOrder(Long id, CORNetwork corNetwork) {
        TRAOrder traOrder = traOrderRepository.findOne(id);
        return getOrdersByEntitie(traOrder, corNetwork);
    }

    public List<TraOrderPT> getOrdersById(List<Long> id, CORNetwork corNetwork) {
        List<TRAOrder> traOrderList = traOrderRepository.findAll(id);
        return getOrdersByEntitie(traOrderList, corNetwork);
    }

    public TraOrderPT update(TraOrderPT traOrderPT, CORNetwork corNetwork) {
        deleteOrder(traOrderPT.getId(), corNetwork);
        return saveOrder(traOrderPT, corNetwork);
    }

    public List<TraOrderPT> getOrdersByEntitie(List<TRAOrder> traOrders, CORNetwork corNetwork) {
        return traOrders.stream().map(traOrder -> getOrdersByEntitie(traOrder, corNetwork)).collect(toList());
    }

    public TraOrderPT getOrdersByEntitie(TRAOrder traOrders, CORNetwork corNetwork) {
        List<CORAssociation> orderAccountAssociation = corAssociationRepository.findBySourceIdAndTargetClassAndNetwork(traOrders.getId(), CRMAccount.class.getName(), corNetwork);
        List<CORAssociation> schEmissionOrderAssociation = corAssociationRepository.findBySourceIdAndTargetClassAndNetwork(traOrders.getId(), SCHEmission.class.getName(), corNetwork);
        List<CORAssociation> orderCampaignAssociation = corAssociationRepository.findBySourceIdAndTargetClassAndNetwork(traOrders.getId(), TRACampaign.class.getName(), corNetwork);
        CRMAccount crmAccount = crmAccountRepository.findOne(orderAccountAssociation.get(0).getTargetId());
        List<Long> schEmissionIds = schEmissionOrderAssociation.stream().map(CORAssociation::getTargetId).collect(toList());
        List<SCHEmission> schEmissionList = schEmissionRepository.findAll(schEmissionIds);
        TRACampaign traCampaign = campaignRepository.findOne(orderCampaignAssociation.get(0).getTargetId());
        return customTRAOrderMapper.transfromEntitesToDTO(traOrders, schEmissionList, traCampaign, crmAccount);
    }

    public List<TraOrderPT> getCustomerOrders(String shortcut, CORNetwork corNetwork) {
        CRMAccount crmAccount = crmAccountRepository.findOneByShortNameAndNetwork(shortcut, corNetwork);
        List<CORAssociation> associations = corAssociationRepository.findByTargetIdAndSourceClassAndNetwork(crmAccount.getId(), TRAOrder.class.getName(), corNetwork);
        return getOrdersById(associations.stream().map(CORAssociation::getSourceId).collect(toList()), corNetwork);
    }

}
