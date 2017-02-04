package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CoreManagedUserPT;
import io.protone.custom.service.dto.CrmContactPT;
import io.protone.custom.service.dto.CrmOpportunityPT;
import io.protone.custom.service.dto.CrmTaskPT;
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
    @Inject
    CustomCORUserMapper corUserMapper;

    public CRMOpportunity createOpportunity(CrmOpportunityPT opportunityPT, CORNetwork corNetwork) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return new CRMOpportunity()
            .name(opportunityPT.getName())
            .closeDate(LocalDate.parse(opportunityPT.getCloseDate(), formatter))
            .propability(opportunityPT.getPropability())
            .lastTry(LocalDate.parse(opportunityPT.getLastTry(), formatter))
            .network(corNetwork);
    }

    public CrmOpportunityPT buildDTOFromEntites(CRMOpportunity opportunity) {
        CrmOpportunityPT crmOpportunityPT = new CrmOpportunityPT();
        opportunity.setId(opportunity.getId());
        return crmOpportunityPT.lastTry(opportunity.getLastTry().toString())
            .name(opportunity.getName())
            .closeDate(opportunity.getCloseDate().toString())
            .tasks(customCRMTaskMapper.createCrmTasks(opportunity.getTasks()))
            .contact(customCRMContactMapper.buildContactDTOFromEntities(opportunity.getContact()))
            .opportunityOwner(corUserMapper.corUserMapper(opportunity.getKeeper()))
            .stage(stageMapper.cRMStageToCRMStageDTO(opportunity.getStage()));
    }


}
