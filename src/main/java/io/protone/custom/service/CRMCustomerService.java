package io.protone.custom.service;

import io.protone.custom.service.dto.CrmAccountPT;
import io.protone.repository.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lukaszozimek on 17.01.2017.
 */
@Service
public class CRMCustomerService {

    @Inject
    private CRMAccountRepository accountRepository;

    @Inject
    private CRMTaskRepository taskRepository;

    @Inject
    private CORAssociationRepository associationRepository;

    @Inject
    private CORRangeRepository rangeRepository;

    @Inject
    private CORSizeRepository sizeRepository;

    @Inject
    private TRAIndustryRepository industryRepository;

    public List<CrmAccountPT> getAllCustomer() {

        return null;
    }

    public CrmAccountPT saveCustomer() {

        return null;
    }

    public void deleteCustomer() {

    }

    public CrmAccountPT getCustomer(String shortcut) {
        return null;
    }
}
