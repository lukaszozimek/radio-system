package io.protone.custom.service;

import io.protone.service.mapper.TraOrderMapper;
import io.protone.domain.CrmAccount;
import io.protone.repository.custom.CustomCrmAccountRepositoryEx;
import io.protone.repository.custom.CustomTraOrderRepository;
import io.protone.custom.service.dto.TraOrderPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.TraOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger log = LoggerFactory.getLogger(TraOrderService.class);

    @Inject
    private TraOrderMapper customTraOrderMapper;

    @Inject
    private CustomTraOrderRepository traOrderRepository;

    @Inject
    private CustomCrmAccountRepositoryEx crmAccountRepository;

    public List<TraOrderPT> getAllOrder(CorNetwork corNetwork) {
        return traOrderRepository.findByNetwork(corNetwork).stream().map(traOrder -> getOrdersByEntitie(traOrder, corNetwork)).collect(toList());
    }

    public TraOrderPT saveOrder(TraOrderPT orderPT, CorNetwork corNetwork) {
        TraOrder traOrder = customTraOrderMapper.DTO2DB(orderPT);
        traOrder.setNetwork(corNetwork);
        log.debug("Persisting TraOrder: {}", traOrder);
        traOrderRepository.save(traOrder);
        return customTraOrderMapper.DB2DTO(traOrder);
    }

    public void deleteOrder(Long id, CorNetwork corNetwork) {
        TraOrder traOrder = traOrderRepository.findOne(id);
        traOrderRepository.delete(traOrder);
    }

    public TraOrderPT getOrder(Long id, CorNetwork corNetwork) {
        TraOrder traOrder = traOrderRepository.findOne(id);
        return getOrdersByEntitie(traOrder, corNetwork);
    }

    public TraOrderPT getOrdersByEntitie(TraOrder traOrders, CorNetwork corNetwork) {
        return customTraOrderMapper.DB2DTO(traOrders);
    }

    public List<TraOrderPT> getCustomerOrders(String shortcut, CorNetwork corNetwork) {
        CrmAccount crmAccount = crmAccountRepository.findOneByShortNameAndNetwork(shortcut, corNetwork);
        return traOrderRepository.findByCustomerAndNetwork(crmAccount, corNetwork).stream().map(traOrder -> customTraOrderMapper.DB2DTO(traOrder)).collect(toList());
    }

}
