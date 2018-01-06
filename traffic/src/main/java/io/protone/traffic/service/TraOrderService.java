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


    public Slice<TraOrder> getAllOrders(String organizationShortcut, String channelShortcut, Pageable pageable) {
        return traOrderRepository.findSliceByChannel_Organization_ShortcutAndChannel_ShortName(organizationShortcut, channelShortcut, pageable);
    }

    public TraOrder saveOrder(TraOrder traOrder) {
        log.debug("Persisting TraOrder: {}", traOrder);

        TraOrder traOrder1 = traOrderRepository.saveAndFlush(traOrder);
        return traOrderRepository.findByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(traOrder1.getId(), traOrder1.getChannel().getOrganization().getShortcut(), traOrder.getChannel().getShortcut());
    }


    public TraOrder saveOrderLazy(TraOrder traOrder) {
        log.debug("Persisting TraOrder: {}", traOrder);
        return traOrderRepository.save(traOrder);
    }

    public void deleteOrder(Long id, String organizationShortcut, String channelShortcut) {
        TraOrder traOrder = traOrderRepository.findByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(id, organizationShortcut, channelShortcut);
        traEmissionService.deleteTraEmissions(traOrder.getEmissions());
        traOrderRepository.delete(traOrder);

    }

    public void deleteCustomerOrders(CrmAccount crmAccount, String organizationShortcut, String channelShortcut) {
        List<TraOrder> traOrder = traOrderRepository.findByCustomer_ShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(crmAccount.getShortName(), organizationShortcut, channelShortcut);
        if (traOrder != null) {
            traOrder.stream().forEach(order -> {
                traEmissionService.deleteTraEmissions(order.getEmissions());
                traOrderRepository.delete(order);
            });

        }

    }

    public TraOrder getOrder(Long id, String organizationShortcut, String channelShortcut) {
        return traOrderRepository.findByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(id, organizationShortcut, channelShortcut);
    }

    public TraOrder getOrder(Long id) {
        return traOrderRepository.findOne(id);
    }

    public Slice<TraOrder> getCustomerOrders(String shortcut, String organizationShortcut, String channelShortcut, Pageable pageable) {
        return traOrderRepository.findSliceByCustomer_ShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(shortcut, organizationShortcut, channelShortcut, pageable);
    }

}
