package io.protone.service.crm;

import io.protone.repository.custom.CustomCrmTaskRepository;
import io.protone.web.rest.mapper.CrmTaskMapper;
import io.protone.web.rest.mapper.CrmOpportunityMapper;
import io.protone.domain.CrmOpportunity;
import io.protone.domain.CrmTask;
import io.protone.repository.custom.CustomCrmOpportunityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service
@Transactional
public class CrmOpportunityService {

    private final Logger log = LoggerFactory.getLogger(CrmOpportunityService.class);

    @Inject
    private CustomCrmOpportunityRepository opportunityRepository;

    @Inject
    private CrmOpportunityMapper customCrmOpportunityMapper;

    @Inject
    private CrmTaskMapper customCrmTaskMapper;

    @Inject
    private CustomCrmTaskRepository crmTaskRepository;

    public List<CrmOpportunity> getAllOpportunity(String corNetwork, Pageable pageable) {
        return opportunityRepository.findAllByNetwork_Shortcut(corNetwork, pageable);
    }

    public CrmOpportunity saveOpportunity(CrmOpportunity crmOpportunity) {
        log.debug("Persisting CrmOpportunity: {}", crmOpportunity);
        return opportunityRepository.save(crmOpportunity);
    }

    public void deleteOpportunity(String shortcut, String corNetwork) {
        opportunityRepository.deleteByNameAndNetwork_Shortcut(shortcut, corNetwork);
    }

    public CrmOpportunity getOpportunity(String shortcut, String corNetwork) {
        return opportunityRepository.findOneByNameAndNetwork_Shortcut(shortcut, corNetwork);
    }


    public CrmTask saveOrUpdateTaskAssociatiedWithOpportunity(CrmTask crmTask, String shortcut, String corNetwork) {
        CrmOpportunity crmOpportunity = opportunityRepository.findOneByNameAndNetwork_Shortcut(shortcut, corNetwork);
        if (crmOpportunity != null) {
            crmTask.setOpportunity(crmOpportunity);
            crmTask.setNetwork(crmOpportunity.getNetwork());
            CrmTask task = crmTaskRepository.save(crmTask);
            log.debug("Persisting CrmTask: {}, for CrmOpportunity: ", task);
            crmOpportunity.addTasks(task);
            opportunityRepository.save(crmOpportunity);
            return task;
        }
        return null;
    }

    public List<CrmTask> getTasksAssociatedWithOpportunity(String shortcut, String corNetwork, Pageable pageable) {
        return crmTaskRepository.findAllByOpportunity_NameAndNetwork_Shortcut(shortcut, corNetwork, pageable);
    }

    public CrmTask getTaskAssociatedWithOpportunity(Long taskId, String corNetwork) {
        return crmTaskRepository.findOneByIdAndNetwork_Shortcut(taskId, corNetwork);
    }


    public void deleteOpportunityTask(String shortcut, Long taskId, String corNetwork) {
        CrmOpportunity crmOpportunity = opportunityRepository.findOneByNameAndNetwork_Shortcut(shortcut, corNetwork);
        crmOpportunity.getTasks().removeIf(crmTask -> crmTask.getId() == taskId);
        opportunityRepository.save(crmOpportunity);
        crmTaskRepository.deleteByIdAndNetwork_Shortcut(taskId, corNetwork);
    }
}
