package io.protone.custom.service;

import io.protone.custom.service.dto.CoreManagedUserPT;
import io.protone.custom.service.dto.CrmAccountPT;
import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.custom.service.mapper.CustomCORUserMapper;
import io.protone.custom.service.mapper.CustomCRMAccountMapper;
import io.protone.custom.service.mapper.CustomCRMTaskMapper;
import io.protone.domain.*;
import io.protone.repository.*;
import io.protone.service.UserService;
import io.protone.service.mapper.CRMAccountMapper;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.protone.custom.service.mapper.CustomCORUserMapper.ACCOUNT_OWNER;
import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 17.01.2017.
 */
@Service
public class CRMCustomerService {

    @Inject
    private CRMAccountRepository accountRepository;

    @Inject
    private CustomCRMAccountMapper crmAccountMapper;

    public List<CrmAccountPT> getAllCustomer(CORNetwork corNetwork) {
        List<CRMAccount> crmAccount = accountRepository.findByNetwork(corNetwork);
        return crmAccount.stream().map(crmAccount1 -> fetchCustomer(crmAccount1, corNetwork)).collect(toList());
    }

    public CrmAccountPT saveCustomer(CrmAccountPT crmAccountPT, CORNetwork corNetwork) {
        CRMAccount crmAccount = crmAccountMapper.createCrmAcountEntity(crmAccountPT,corNetwork);
        accountRepository.save(crmAccount);
        return crmAccountMapper.buildContactDTOFromEntities(crmAccount);

    }

    public CrmAccountPT update(CrmAccountPT crmAccountPT, CORNetwork corNetwork) {
        return null;
    }

    public void deleteCustomer(String shorName, CORNetwork corNetwork) {
    }

    private void deleteCustomer(CRMAccount crmAccount, CORNetwork corNetwork) {

    }

    private CrmAccountPT fetchCustomer(CRMAccount crmAccount, CORNetwork corNetwork) {
        return null;
    }


    public CrmAccountPT getCustomer(String shortcut, CORNetwork corNetwork) {
        return null;
    }

    public CrmTaskPT createTasksAssociatedWithLead(String shortcut, CrmTaskPT taskPT, CORNetwork corNetwork) {
        return null;
    }

    public List<CrmTaskPT> getTasksAssociatedWithLead(String shortcut, CORNetwork corNetwork) {
        return null;
    }

    public CrmTaskPT getTaskAssociatedWithLead(String shortcut, Long taskId, CORNetwork corNetwork) {
        return null;
    }

    public void deleteLeadTask(String shortcut, Long taskId, CORNetwork corNetwork) {
    }

    public CrmTaskPT updateLeadTask(String shortcut, CrmTaskPT crmTask, CORNetwork corNetwork) {
        return null;
    }


}
