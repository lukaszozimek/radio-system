package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.TraCustomerPT;
import io.protone.domain.TRACustomer;
import org.springframework.stereotype.Service;

/**
 * Created by lukaszozimek on 21.01.2017.
 */
@Service
public class CustomTRACustomerMapper {

    public TRACustomer transformDTOtoEntity(TraCustomerPT traCustomerPT) {
        TRACustomer traCustomer = new TRACustomer();
        return traCustomer;
    }

    public TraCustomerPT transformEntityToDTO(TRACustomer traCustomerPT) {
        return new TraCustomerPT();
    }
}
