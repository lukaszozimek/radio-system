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

    public List<TraOrderPT> getAllOrder(CORNetwork corNetwork) {
        return traOrderRepository.findByNetwork(corNetwork).stream().map(traOrder -> getOrdersByEntitie(traOrder, corNetwork)).collect(toList());
    }

    public TraOrderPT saveOrder(TraOrderPT orderPT, CORNetwork corNetwork) {
        TRAOrder traOrder = customTRAOrderMapper.trasnformDTOtoEntity(orderPT);
        traOrderRepository.save(traOrder);
        return customTRAOrderMapper.transfromEntiteToDTO(traOrder);
    }

    public void deleteOrder(Long id, CORNetwork corNetwork) {
        TRAOrder traOrder = traOrderRepository.findOne(id);
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
        return customTRAOrderMapper.transfromEntiteToDTO(traOrders);
    }

    public List<TraOrderPT> getCustomerOrders(String shortcut, CORNetwork corNetwork) {
    return null;
    }

}
