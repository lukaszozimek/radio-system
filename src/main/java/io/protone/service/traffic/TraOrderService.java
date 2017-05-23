package io.protone.service.traffic;

import io.protone.repository.traffic.TraOrderRepository;
import io.protone.domain.TraOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
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

    public List<TraOrder> getAllOrders(String corNetwork, Pageable pageable) {
        return traOrderRepository.findByNetwork_Shortcut(corNetwork, pageable);
    }

    public TraOrder saveOrder(TraOrder traOrder) {
        log.debug("Persisting TraOrder: {}", traOrder);
        return traOrderRepository.saveAndFlush(traOrder);
    }

    public TraOrder saveOrderLazy(TraOrder traOrder) {
        log.debug("Persisting TraOrder: {}", traOrder);
        return traOrderRepository.save(traOrder);
    }

    public void deleteOrder(Long id, String corNetwork) {
        traOrderRepository.deleteByIdAndNetwork_Shortcut(id, corNetwork);
    }

    public TraOrder getOrder(Long id, String corNetwork) {
        return traOrderRepository.findByIdAndNetwork_Shortcut(id, corNetwork);
    }


    public List<TraOrder> getCustomerOrders(String shortcut, String corNetwork, Pageable pageable) {
        return traOrderRepository.findByCustomer_ShortNameAndNetwork_Shortcut(shortcut, corNetwork, pageable);
    }

}
