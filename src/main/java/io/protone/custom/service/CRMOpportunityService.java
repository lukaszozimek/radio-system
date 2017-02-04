package io.protone.custom.service;

import io.protone.custom.service.dto.*;
import io.protone.custom.service.mapper.CustomCORUserMapper;
import io.protone.custom.service.mapper.CustomCRMContactMapper;
import io.protone.custom.service.mapper.CustomCRMOpportunityMapper;
import io.protone.custom.service.mapper.CustomCRMTaskMapper;
import io.protone.domain.*;
import io.protone.repository.*;
import io.protone.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static io.protone.custom.service.mapper.CustomCORUserMapper.OPPORTUNITY_OWNER;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service
@Transactional
public class CRMOpportunityService {

    @Inject
    private CRMOpportunityRepository opportunityRepository;

    @Inject
    private CustomCRMOpportunityMapper customCRMOpportunityMapper;

    @Inject
    private CustomCRMTaskMapper customCRMTaskMapper;

    @Inject
    private CRMTaskRepository crmTaskRepository;


    public List<CrmOpportunityPT> getAllOpportunity(CORNetwork corNetwork) {
        return opportunityRepository.findByNetwork(corNetwork).stream().map(opportunity -> customCRMOpportunityMapper.buildDTOFromEntites(opportunity)).collect(toList());
    }

    public CrmOpportunityPT saveOpportunity(CrmOpportunityPT opportunityPT, CORNetwork corNetwork) {
        CRMOpportunity opportunity = opportunityRepository.save(customCRMOpportunityMapper.createOpportunity(opportunityPT, corNetwork));
        return customCRMOpportunityMapper.buildDTOFromEntites(opportunity);
    }

    public void deleteOpportunity(String shortcut, CORNetwork corNetwork) {
        opportunityRepository.deleteByNameAndNetwork(shortcut, corNetwork);
    }

    public CrmOpportunityPT getOpportunity(String shortcut, CORNetwork corNetwork) {
        CRMOpportunity opportunity = opportunityRepository.findByNameAndNetwork(shortcut, corNetwork);
        return customCRMOpportunityMapper.buildDTOFromEntites(opportunity);
    }


    public List<CrmTaskPT> getTasksAssociatedWithOpportunity(String shortcut, CORNetwork corNetwork) {
        CRMOpportunity opportunity = opportunityRepository.findByNameAndNetwork(shortcut, corNetwork);
        return customCRMTaskMapper.createCrmTasks(opportunity.getTasks());
    }

    public CrmTaskPT getTaskAssociatedWithOpportunity(String shortcut, Long taskId, CORNetwork corNetwork) {
        CRMOpportunity opportunity = opportunityRepository.findByNameAndNetwork(shortcut, corNetwork);
        return customCRMTaskMapper.createCrmTask(opportunity.getTasks().stream().filter(crmTask -> crmTask.getId().equals(taskId)).findFirst().get());
    }


    public void deleteOpportunityTask(String shortcut, Long taskId, CORNetwork corNetwork) {
        CRMOpportunity opportunity = opportunityRepository.findByNameAndNetwork(shortcut, corNetwork);
        CRMTask crmTask = crmTaskRepository.findOne(taskId);
        opportunity.removeTasks(crmTask);
        opportunityRepository.save(opportunity);
        crmTaskRepository.delete(crmTask);
    }


    public CrmTaskPT saveTasksAssociatedWithOpportunity(String shortName, CrmTaskPT crmActivityPT, CORNetwork corNetwork) {
        CRMOpportunity crmOpportunity = opportunityRepository.findByNameAndNetwork(shortName, corNetwork);
        CRMTask crmTask = crmTaskRepository.save(customCRMTaskMapper.createTaskEntity(crmActivityPT));
        crmOpportunity.addTasks(crmTask);
        opportunityRepository.save(crmOpportunity);
        return customCRMTaskMapper.createCrmTask(crmTask);
    }
}
