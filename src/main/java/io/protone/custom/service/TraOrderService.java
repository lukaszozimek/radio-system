package io.protone.custom.service;

import io.protone.domain.CrmAccount;
import io.protone.repository.custom.CustomCrmAccountRepositoryEx;
import io.protone.repository.custom.CustomTraOrderRepository;
import io.protone.custom.service.dto.TraOrderPT;
import io.protone.custom.service.mapper.CustomTraOrderMapper;
import io.protone.domain.CorNetwork;
import io.protone.domain.TraOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service
@Transactional
public class TraOrderService {

    @Inject
    private CustomTraOrderMapper customTraOrderMapper;

    @Inject
    private CustomTraOrderRepository traOrderRepository;
    private CustomCrmAccountRepositoryEx crmAccountRepository;

    public List<TraOrderPT> getAllOrder(CorNetwork corNetwork) {
        return traOrderRepository.findByNetwork(corNetwork).stream().map(traOrder -> getOrdersByEntitie(traOrder, corNetwork)).collect(toList());
    }

    public TraOrderPT saveOrder(TraOrderPT orderPT, CorNetwork corNetwork) {
        TraOrder traOrder = customTraOrderMapper.trasnformDTOtoEntity(orderPT, corNetwork);
        traOrderRepository.save(traOrder);
        return customTraOrderMapper.transfromEntiteToDTO(traOrder);
    }

    public void deleteOrder(Long id, CorNetwork corNetwork) {
        TraOrder traOrder = traOrderRepository.findOne(id);
        traOrderRepository.delete(traOrder);
    }

    public TraOrderPT getOrder(Long id, CorNetwork corNetwork) {
        TraOrder traOrder = traOrderRepository.findOne(id);
        return getOrdersByEntitie(traOrder, corNetwork);
    }

    public List<TraOrderPT> getOrdersByEntitie(List<TraOrder> traOrders, CorNetwork corNetwork) {
        return traOrders.stream().map(traOrder -> getOrdersByEntitie(traOrder, corNetwork)).collect(toList());
    }

    public TraOrderPT getOrdersByEntitie(TraOrder traOrders, CorNetwork corNetwork) {
        return customTraOrderMapper.transfromEntiteToDTO(traOrders);
    }

    public List<TraOrderPT> getCustomerOrders(String shortcut, CorNetwork corNetwork) {
        CrmAccount crmAccount = crmAccountRepository.findOneByShortNameAndNetwork(shortcut, corNetwork);
        return traOrderRepository.findByCustomerAndNetwork(crmAccount, corNetwork).stream().map(traOrder -> customTraOrderMapper.transfromEntiteToDTO(traOrder)).collect(toList());
    }

}
