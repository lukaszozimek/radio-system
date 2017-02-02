package io.protone.custom.service;

import io.protone.custom.service.dto.*;
import io.protone.custom.service.mapper.CustomCRMAccountMapper;
import io.protone.domain.*;
import io.protone.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service

@Transactional
public class TRACustomerService {

    @Inject
    private CRMAccountRepository crmAccountRepository;

    @Inject
    private CustomCRMAccountMapper customCRMAccountMapper;


    public List<TraCustomerPT> getAllCustomers(CORNetwork corNetwork) {

        return crmAccountRepository.findByNetwork(corNetwork).stream().map(customers -> getCustomer(customers, corNetwork)).collect(toList());
    }

    public TraCustomerPT saveCustomers(TraCustomerPT traCustomerPT, CORNetwork corNetwork) {
        CRMAccount crmAccount = customCRMAccountMapper.createCrmAcountEntity(traCustomerPT);
        crmAccountRepository.save(crmAccount);

        return customCRMAccountMapper.createCustomerTrafficDTO(crmAccount);
    }

    public void deleteCustomer(String shortcut, CORNetwork corNetwork) {


    }

    public TraCustomerPT getCustomer(String shortcut, CORNetwork corNetwork) {
        CRMAccount crmAccount = crmAccountRepository.findOneByShortNameAndNetwork(shortcut, corNetwork);
        return getCustomer(crmAccount, corNetwork);
    }

    public TraCustomerPT update(TraCustomerPT traCustomerPT, CORNetwork corNetwork) {
        deleteCustomer(traCustomerPT.getShortName(), corNetwork);
        return saveCustomers(traCustomerPT, corNetwork);
    }

    public TraCustomerPT getCustomer(CRMAccount crmAccount, CORNetwork corNetwork) {

        return customCRMAccountMapper.createCustomerTrafficDTO(crmAccount);
    }


    public TraInvoicePT getCustomerInvoice(String shortcut) {
        return null;
    }

}
