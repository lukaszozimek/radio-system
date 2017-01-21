package io.protone.custom.service;

import io.protone.custom.service.dto.*;
import io.protone.custom.service.mapper.CustomCRMAccountMapper;
import io.protone.domain.CRMAccount;
import io.protone.repository.CORAssociationRepository;
import io.protone.repository.CRMAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service

@Transactional
public class TRACustomerService {

    @Inject
    private CORAssociationRepository corAssociationRepository;

    @Inject
    private CRMAccountRepository crmAccountRepository;

    @Inject
    private CustomCRMAccountMapper customCRMAccountMapper;

    public List<TraCustomerPT> getAllCustomers() {

        return null;
    }

    public TraCustomerPT saveCustomers(TraCustomerPT traCustomerPT) {

        return null;
    }

    public void deleteCustomer(String shortcut) {

    }

    public TraCustomerPT getCustomer(String shortcut) {
        return null;
    }


    public TraInvoicePT getCustomerInvoice(String shortcut) {
        return null;
    }

}
