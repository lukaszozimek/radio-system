package io.protone.custom.service;

import io.protone.custom.service.dto.TraOrderPT;
import io.protone.custom.service.mapper.CustomTRAOrderMapper;
import io.protone.domain.*;
import io.protone.repository.*;
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
public class TRAOrderService {

    @Inject
    private CustomTRAOrderMapper customTRAOrderMapper;

    @Inject
    private TRAOrderRepository traOrderRepository;

    public List<TraOrderPT> getAllOrder(CorNetwork corNetwork) {
        return traOrderRepository.findByNetwork(corNetwork).stream().map(traOrder -> getOrdersByEntitie(traOrder, corNetwork)).collect(toList());
    }

    public TraOrderPT saveOrder(TraOrderPT orderPT, CorNetwork corNetwork) {
        TRAOrder traOrder = customTRAOrderMapper.trasnformDTOtoEntity(orderPT, corNetwork);
        traOrderRepository.save(traOrder);
        return customTRAOrderMapper.transfromEntiteToDTO(traOrder);
    }

    public void deleteOrder(Long id, CorNetwork corNetwork) {
        TRAOrder traOrder = traOrderRepository.findOne(id);
        traOrderRepository.delete(traOrder);
    }

    public TraOrderPT getOrder(Long id, CorNetwork corNetwork) {
        TRAOrder traOrder = traOrderRepository.findOne(id);
        return getOrdersByEntitie(traOrder, corNetwork);
    }

    public List<TraOrderPT> getOrdersByEntitie(List<TRAOrder> traOrders, CorNetwork corNetwork) {
        return traOrders.stream().map(traOrder -> getOrdersByEntitie(traOrder, corNetwork)).collect(toList());
    }

    public TraOrderPT getOrdersByEntitie(TRAOrder traOrders, CorNetwork corNetwork) {
        return customTRAOrderMapper.transfromEntiteToDTO(traOrders);
    }

    public List<TraOrderPT> getCustomerOrders(String shortcut, CorNetwork corNetwork) {
        return null;
    }

}
