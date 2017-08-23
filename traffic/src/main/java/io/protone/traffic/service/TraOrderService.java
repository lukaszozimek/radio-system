package io.protone.traffic.service;


import io.protone.crm.domain.CrmAccount;
import io.protone.traffic.domain.TraOrder;
import io.protone.traffic.repository.TraOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service
@Transactional
public class TraOrderService {

    private final Logger log = LoggerFactory.getLogger(TraOrderService.class);

    @Inject
    private TraOrderRepository traOrderRepository;

    @Inject
    private TraEmissionService traEmissionService;


    public Slice<TraOrder> getAllOrders(String corNetwork, Pageable pageable) {
        return traOrderRepository.findSliceByNetwork_Shortcut(corNetwork, pageable);
    }

    public TraOrder saveOrder(TraOrder traOrder) {
        log.debug("Persisting TraOrder: {}", traOrder);

        TraOrder traOrder1 = traOrderRepository.saveAndFlush(traOrder);
        return traOrderRepository.findByIdAndNetwork_Shortcut(traOrder1.getId(), traOrder1.getNetwork().getShortcut());
    }


    public TraOrder saveOrderLazy(TraOrder traOrder) {
        log.debug("Persisting TraOrder: {}", traOrder);
        return traOrderRepository.save(traOrder);
    }

    public void deleteOrder(Long id, String corNetwork) {
        TraOrder traOrder = traOrderRepository.findByIdAndNetwork_Shortcut(id, corNetwork);
        traEmissionService.deleteTraEmissions(traOrder.getEmissions());
        traOrderRepository.delete(traOrder);

    }

    public void deleteCustomerOrders(CrmAccount crmAccount, String corNetwork) {
        List<TraOrder> traOrder = traOrderRepository.findByCustomer_ShortNameAndNetwork_Shortcut(crmAccount.getShortName(), corNetwork);
        if (traOrder != null) {
            traOrder.stream().forEach(order -> {
                traEmissionService.deleteTraEmissions(order.getEmissions());
                traOrderRepository.delete(order);
            });

        }

    }

    public TraOrder getOrder(Long id, String corNetwork) {
        return traOrderRepository.findByIdAndNetwork_Shortcut(id, corNetwork);
    }


    public Slice<TraOrder> getCustomerOrders(String shortcut, String corNetwork, Pageable pageable) {
        return traOrderRepository.findSliceByCustomer_ShortNameAndNetwork_Shortcut(shortcut, corNetwork, pageable);
    }

}
