package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CoreManagedUserPT;
import io.protone.custom.service.dto.CrmContactPT;
import io.protone.custom.service.dto.CrmOpportunityPT;
import io.protone.domain.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 18.01.2017.
 */
@Service
public class CustomCRMOpportunityMapper {

    @Inject
    CustomCRMStageMapper stageMapper;

    @Inject
    CustomCRMContactMapper customCRMContactMapper;
    @Inject
    CustomCRMTaskMapper customCRMTaskMapper;

    public CRMOpportunity createOpportunity(CrmOpportunityPT opportunityPT) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return new CRMOpportunity()
            .name(opportunityPT.getName())
            .closeDate(LocalDate.parse(opportunityPT.getCloseDate(), formatter))
            .probability(opportunityPT.getPropability())
            .lastTry(LocalDate.parse(opportunityPT.getLastTry(), formatter));
    }

    public CrmOpportunityPT buildDTOFromEntites(CRMOpportunity opportunity, CRMStage stage, CoreManagedUserPT userPT, CrmContactPT contatPT, List<CRMTask> crmTaskList) {
        CrmOpportunityPT crmOpportunityPT = new CrmOpportunityPT();
        opportunity.setId(opportunity.getId());
        return crmOpportunityPT.lastTry(opportunity.getLastTry().toString())
            .name(opportunity.getName())
            .closeDate(opportunity.getCloseDate().toString())
            .tasks(customCRMTaskMapper.transformTasksFromEntity(crmTaskList))
            .contact(contatPT)
            .opportunityOwner(userPT)
            .stage(stageMapper.cRMStageToCRMStageDTO(stage));
    }

    public CORAssociation createOpportunityContactAssociationEntity(CRMOpportunity opportunity, CRMContact crmContact) {
        CORAssociation association = new CORAssociation();
        association.setName("CONTACT");
        association.setSourceClass(CRMOpportunity.class.getName());
        association.setSourceId(opportunity.getId());
        association.setTargetClass(CRMContact.class.getName());
        association.setTargetId(crmContact.getId());
        return association;
    }

    public CORAssociation createOpportunityStageAssociationEntity(CRMOpportunity opportunity, CRMStage crmStage) {
        CORAssociation association = new CORAssociation();
        association.setName("STAGE");
        association.setSourceClass(CRMOpportunity.class.getName());
        association.setSourceId(opportunity.getId());
        association.setTargetClass(CRMStage.class.getName());
        association.setTargetId(crmStage.getId());
        return association;
    }

    public List<CORAssociation> createOpportunityTasksAssociationEntity(CRMOpportunity crmContact, List<CRMTask> crmTasks) {
        List<CORAssociation> associations = new ArrayList<>();
        for (CRMTask crmTask : crmTasks) {
            associations.add(createOpportunityTaskAssociationEntity(crmContact, crmTask));
        }
        return associations;
    }

    public CORAssociation createOpportunityTaskAssociationEntity(CRMOpportunity opportunity, CRMTask crmTask) {
        CORAssociation association = new CORAssociation();
        association.setName("CRMTask");
        association.setSourceClass(CRMOpportunity.class.getName());
        association.setSourceId(opportunity.getId());
        association.setTargetClass(CRMTask.class.getName());
        association.setTargetId(crmTask.getId());
        return association;
    }
}
