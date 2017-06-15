package io.protone.service.crm;

import io.protone.domain.*;
import io.protone.repository.crm.CrmTaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lukaszozimek on 15/06/2017.
 */
@Service
public class CrmTaskService {
    private final Logger log = LoggerFactory.getLogger(CrmTaskService.class);

    @Inject
    private CrmTaskRepository crmTaskRepository;

    @Transactional
    public CrmTask saveOrUpdateTaskAssociatiedWithContact(CrmContact contact, CrmTask crmTask) {
        crmTask.setContact(contact);
        crmTask.setNetwork(contact.getNetwork());
        CrmTask task = crmTaskRepository.saveAndFlush(crmTask);
        log.debug("Persisting CrmTask: {}, for CrmContact: ", task);
        return task;
    }

    @Transactional
    public CrmTask saveOrUpdateTaskAssociatiedWithCustomer(CrmAccount crmAccount, CrmTask crmTask) {
        crmTask.setAccount(crmAccount);
        crmTask.setNetwork(crmAccount.getNetwork());
        CrmTask task = crmTaskRepository.saveAndFlush(crmTask);
        log.debug("Persisting CrmTask: {}, for CrmAccount: ", task);
        return task;
    }

    @Transactional
    public CrmTask saveOrUpdateTaskAssociatiedWithLead(CrmLead crmLead, CrmTask crmTask) {
        crmTask.setLead(crmLead);
        crmTask.setNetwork(crmLead.getNetwork());
        CrmTask task = crmTaskRepository.saveAndFlush(crmTask);
        log.debug("Persisting CrmTask: {}, for CrmLead: ", task);
        return task;
    }

    @Transactional
    public CrmTask saveOrUpdateTaskAssociatiedWithOpportunity(CrmOpportunity crmOpportunity, CrmTask crmTask) {
        crmTask.setOpportunity(crmOpportunity);
        crmTask.setNetwork(crmOpportunity.getNetwork());
        CrmTask task = crmTaskRepository.saveAndFlush(crmTask);
        log.debug("Persisting CrmTask: {}, for CrmOpportunity: ", task);
        return task;
    }

    @Transactional
    public void deleteCrmTask(Long taskId, String corNetwork) {
        crmTaskRepository.deleteByIdAndNetwork_Shortcut(taskId, corNetwork);
    }

    @Transactional
    public void deleteByContactByShortNameAndNetworkByShortcut(String shortcut, String corNetwork) {
        crmTaskRepository.deleteByContact_ShortNameAndNetwork_Shortcut(shortcut, corNetwork);
    }

    @Transactional
    public List<CrmTask> findAllByContactByShortNameAndNetworkByShortcut(String shortcut, String corNetwork, Pageable pageable) {
        return crmTaskRepository.findAllByContact_ShortNameAndNetwork_Shortcut(shortcut, corNetwork, pageable);
    }

    public CrmTask findOneByIdAndNetwork_Shortcut(Long taskId, String corNetwork) {
        return crmTaskRepository.findOneByIdAndNetwork_Shortcut(taskId, corNetwork);
    }

    public List<CrmTask> findAllByAccount_ShortNameAndNetwork_Shortcut(String shortcut, String corNetwork, Pageable pageable) {
        return crmTaskRepository.findAllByAccount_ShortNameAndNetwork_Shortcut(shortcut, corNetwork, pageable);
    }

    @Transactional
    public void deleteByAccount_ShortNameAndNetwork_Shortcut(String shortName, String corNetwork) {
        crmTaskRepository.deleteByAccount_ShortNameAndNetwork_Shortcut(shortName, corNetwork);
    }

    @Transactional
    public void deleteByIdAndNetwork_Shortcut(Long taskId, String corNetwork) {
        crmTaskRepository.deleteByIdAndNetwork_Shortcut(taskId, corNetwork);
    }

    @Transactional
    public void deleteByLead_ShortnameAndNetwork_Shortcut(String shortcut, String corNetwork) {
        crmTaskRepository.deleteByLead_ShortnameAndNetwork_Shortcut(shortcut, corNetwork);
    }

    public List<CrmTask> findAllByLead_ShortnameAndNetwork_Shortcut(String shortcut, String corNetwork, Pageable pageable) {
        return crmTaskRepository.findAllByLead_ShortnameAndNetwork_Shortcut(shortcut, corNetwork, pageable);
    }

    public List<CrmTask> findAllByOpportunity_ShortNameAndNetwork_Shortcut(String shortcut, String corNetwork, Pageable pageable) {
        return crmTaskRepository.findAllByOpportunity_ShortNameAndNetwork_Shortcut(shortcut, corNetwork, pageable);
    }
}
