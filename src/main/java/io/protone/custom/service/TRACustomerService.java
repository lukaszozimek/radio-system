package io.protone.custom.service;

import io.protone.custom.service.dto.*;
import io.protone.custom.service.mapper.CustomCrmAccountMapper;
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
    private CrmAccountRepository crmAccountRepository;

    @Inject
    private CustomCrmAccountMapper customCrmAccountMapper;


    public List<TraCustomerPT> getAllCustomers(CorNetwork corNetwork) {

        return crmAccountRepository.findByNetwork(corNetwork).stream().map(customers -> getCustomer(customers, corNetwork)).collect(toList());
    }

    public TraCustomerPT saveCustomers(TraCustomerPT traCustomerPT, CorNetwork corNetwork) {
        CrmAccount crmAccount = customCrmAccountMapper.createCrmAcountEntity(traCustomerPT,corNetwork);
        crmAccountRepository.save(crmAccount);

        return customCrmAccountMapper.createCustomerTrafficDTO(crmAccount);
    }

    public void deleteCustomer(String shortcut, CorNetwork corNetwork) {


    }

    public TraCustomerPT getCustomer(String shortcut, CorNetwork corNetwork) {
        CrmAccount crmAccount = crmAccountRepository.findOneByShortNameAndNetwork(shortcut, corNetwork);
        return getCustomer(crmAccount, corNetwork);
    }

    public TraCustomerPT update(TraCustomerPT traCustomerPT, CorNetwork corNetwork) {
        deleteCustomer(traCustomerPT.getShortName(), corNetwork);
        return saveCustomers(traCustomerPT, corNetwork);
    }

    public TraCustomerPT getCustomer(CrmAccount crmAccount, CorNetwork corNetwork) {

        return customCrmAccountMapper.createCustomerTrafficDTO(crmAccount);
    }


    public TraInvoicePT getCustomerInvoice(String shortcut) {
        return null;
    }

}
