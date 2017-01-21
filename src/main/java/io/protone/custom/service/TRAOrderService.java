package io.protone.custom.service;

import io.protone.custom.service.dto.TraOrderPT;
import io.protone.custom.service.mapper.CustomSCHEmissionMapper;
import io.protone.custom.service.mapper.CustomTRAOrderMapper;
import io.protone.domain.TRAOrder;
import io.protone.repository.CORAssociationRepository;
import io.protone.repository.SCHEmissionRepository;
import io.protone.repository.TRAOrderRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
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
    private SCHEmissionRepository schEmissionRepository;

    public List<TraOrderPT> getAllOrder() {

        return null;
    }

    public TraOrderPT saveOrder(TraOrderPT orderPT) {
        TRAOrder order = customTRAOrderMapper.trasnformDTOtoEntity(orderPT);
        traOrderRepository.save(order);

        return null;
    }

    public void deleteOrder(Long id) {

    }

    public TraOrderPT getOrder(Long id) {
        return null;
    }

    public List<TraOrderPT> getCustomerOrders(String shortcut) {
        return null;
    }

}
