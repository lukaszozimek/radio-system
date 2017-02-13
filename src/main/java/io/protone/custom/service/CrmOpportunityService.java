package io.protone.custom.service;

import io.protone.custom.service.dto.CrmOpportunityPT;
import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.custom.service.mapper.CustomCrmOpportunityMapper;
import io.protone.custom.service.mapper.CustomCrmTaskMapper;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmOpportunity;
import io.protone.domain.CrmTask;
import io.protone.repository.CrmOpportunityRepository;
import io.protone.repository.CrmTaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service
@Transactional
public class CrmOpportunityService {

    @Inject
    private CrmOpportunityRepository opportunityRepository;

    @Inject
    private CustomCrmOpportunityMapper customCrmOpportunityMapper;

    @Inject
    private CustomCrmTaskMapper customCrmTaskMapper;

    @Inject
    private CrmTaskRepository crmTaskRepository;

    public List<CrmOpportunityPT> getAllOpportunity(CorNetwork corNetwork) {
        return opportunityRepository.findByNetwork(corNetwork).stream().map(opportunity -> customCrmOpportunityMapper.buildDTOFromEntites(opportunity)).collect(toList());
    }

    public CrmOpportunityPT saveOpportunity(CrmOpportunityPT opportunityPT, CorNetwork corNetwork) {
        CrmOpportunity opportunity = opportunityRepository.save(customCrmOpportunityMapper.createOpportunity(opportunityPT, corNetwork));
        return customCrmOpportunityMapper.buildDTOFromEntites(opportunity);
    }

    public void deleteOpportunity(String shortcut, CorNetwork corNetwork) {
        opportunityRepository.deleteByNameAndNetwork(shortcut, corNetwork);
    }

    public CrmOpportunityPT getOpportunity(String shortcut, CorNetwork corNetwork) {
        CrmOpportunity opportunity = opportunityRepository.findOneByNameAndNetwork(shortcut, corNetwork);
        return customCrmOpportunityMapper.buildDTOFromEntites(opportunity);
    }


    public List<CrmTaskPT> getTasksAssociatedWithOpportunity(String shortcut, CorNetwork corNetwork) {
        CrmOpportunity opportunity = opportunityRepository.findOneByNameAndNetwork(shortcut, corNetwork);
        return customCrmTaskMapper.createCrmTasks(opportunity.getTasks());
    }

    public CrmTaskPT getTaskAssociatedWithOpportunity(String shortcut, Long taskId, CorNetwork corNetwork) {
        CrmOpportunity opportunity = opportunityRepository.findOneByNameAndNetwork(shortcut, corNetwork);
        return customCrmTaskMapper.createCrmTask(opportunity.getTasks().stream().filter(crmTask -> crmTask.getId().equals(taskId)).findFirst().get());
    }


    public void deleteOpportunityTask(String shortcut, Long taskId, CorNetwork corNetwork) {
        CrmOpportunity opportunity = opportunityRepository.findOneByNameAndNetwork(shortcut, corNetwork);
        CrmTask crmTask = crmTaskRepository.findOne(taskId);
        opportunity.removeTasks(crmTask);
        opportunityRepository.save(opportunity);
        crmTaskRepository.delete(crmTask);
    }


    public CrmTaskPT saveTasksAssociatedWithOpportunity(String shortName, CrmTaskPT crmActivityPT, CorNetwork corNetwork) {
        CrmOpportunity crmOpportunity = opportunityRepository.findOneByNameAndNetwork(shortName, corNetwork);
        CrmTask crmTask = crmTaskRepository.save(customCrmTaskMapper.createTaskEntity(crmActivityPT));
        crmOpportunity.addTasks(crmTask);
        opportunityRepository.save(crmOpportunity);
        return customCrmTaskMapper.createCrmTask(crmTask);
    }
}
