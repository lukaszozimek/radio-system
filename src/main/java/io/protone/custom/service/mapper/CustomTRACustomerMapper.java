package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.TraCustomerPT;
import io.protone.domain.CRMAccount;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by lukaszozimek on 21.01.2017.
 */
@Service
public class CustomTRACustomerMapper {

    @Inject
    private CustomTRAIndustryMapper customTRAIndustryMapper;

    public CRMAccount transformDTOtoEntity(TraCustomerPT traCustomerPT) {
        CRMAccount traCustomer = new CRMAccount();
        traCustomer.setId(traCustomerPT.getId());
        return traCustomer.name(traCustomerPT.getName());
    }

    public TraCustomerPT transformEntityToDTO(CRMAccount traCustomerPT) {
        return new TraCustomerPT();
    }
}
