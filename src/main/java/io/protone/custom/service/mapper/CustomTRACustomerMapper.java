package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.TraCustomerPT;
import io.protone.domain.TRACustomer;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by lukaszozimek on 21.01.2017.
 */
@Service
public class CustomTRACustomerMapper {

    @Inject
    private CustomTRAIndustryMapper customTRAIndustryMapper;

    public TRACustomer transformDTOtoEntity(TraCustomerPT traCustomerPT) {
        TRACustomer traCustomer = new TRACustomer();
        traCustomer.setId(traCustomerPT.getId());
        return traCustomer.name(traCustomerPT.getName())
            .industry(customTRAIndustryMapper.tRAIndustryDTOToTRAIndustry(traCustomerPT.getIndustry()));
    }

    public TraCustomerPT transformEntityToDTO(TRACustomer traCustomerPT) {
        return new TraCustomerPT();
    }
}
