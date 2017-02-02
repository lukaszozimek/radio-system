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


    public List<CrmOpportunityPT> getAllOpportunity(CORNetwork corNetwork) {
        List<CRMOpportunity> opportunities = opportunityRepository.findByNetwork(corNetwork);
        return opportunities.stream().map(opportunity -> createDTO(opportunity, corNetwork)).collect(toList());
    }

    public CrmOpportunityPT saveOpportunity(CrmOpportunityPT opportunityPT, CORNetwork corNetwork) {
        CRMOpportunity opportunity = opportunityRepository.save(customCRMOpportunityMapper.createOpportunity(opportunityPT, corNetwork));
         return customCRMOpportunityMapper.buildDTOFromEntites(opportunity );
    }

    public void deleteOpportunity(String shortcut, CORNetwork corNetwork) {
         }

    public CrmOpportunityPT getOpportunity(String shortcut, CORNetwork corNetwork) {
        return null; }


    public CrmOpportunityPT update(CrmOpportunityPT opportunityPT, CORNetwork corNetwork) {
        return null; }

    public List<CrmTaskPT> getTasksAssociatedWithLead(String shortcut, CORNetwork corNetwork) {
        return null; }

    public CrmTaskPT getTaskAssociatedWithLead(String shortcut, Long taskId, CORNetwork corNetwork) {
        return null;}

    public void deleteLeadTask(String shortcut, Long taskId, CORNetwork corNetwork) {
     }

    public CrmTaskPT createTasksAssociatedWithLead(String shortcut, CrmTaskPT taskPT, CORNetwork corNetwork) {
        return null;}

    public CrmTaskPT updateLeadTask(String shortcut, CrmTaskPT crmTask, CORNetwork corNetwork) {
        return null;
    }

    private CrmOpportunityPT createDTO(CRMOpportunity opportunity, CORNetwork corNetwork) {
        return null; }


}
