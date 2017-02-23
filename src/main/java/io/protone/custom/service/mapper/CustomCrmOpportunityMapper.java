package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CrmOpportunityPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmOpportunity;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by lukaszozimek on 18.01.2017.
 */
@Service
public class CustomCrmOpportunityMapper {

    @Inject
    private CustomCrmStageMapper stageMapper;

    @Inject
    private CustomCrmContactMapper customCrmContactMapper;
    @Inject
    private CustomCrmTaskMapper customCrmTaskMapper;
    @Inject
    private CustomCORUserMapper corUserMapper;

    public CrmOpportunity createOpportunity(CrmOpportunityPT opportunityPT, CorNetwork corNetwork) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        CrmOpportunity opportunity = new CrmOpportunity();
        opportunity.setId(opportunityPT.getId());
        return
            opportunity.name(opportunityPT.getName())
                ///.closeDate(LocalDate.parse(opportunityPT.getCloseDate(), formatter))
                .probability(opportunityPT.getPropability())
                //.lastTry(LocalDate.parse(opportunityPT.getLastTry(), formatter))
                .network(corNetwork);
    }

    public CrmOpportunityPT buildDTOFromEntites(CrmOpportunity opportunity) {
        CrmOpportunityPT crmOpportunityPT = new CrmOpportunityPT();
        crmOpportunityPT.setId(opportunity.getId());
        return crmOpportunityPT
            //.lastTry(opportunity.getLastTry().toString())
            .name(opportunity.getName())
            .propability(opportunity.getProbability())
            //.closeDate(opportunity.getCloseDate().toString())
            .tasks(customCrmTaskMapper.createCrmTasks(opportunity.getTasks()))
            //.contact(customCrmContactMapper.buildContactDTOFromEntities(opportunity.getContact()))
            //.opportunityOwner(corUserMapper.corUserMapper(opportunity.getKeeper()))
            .stage(stageMapper.cRMStageToCrmStageDTO(opportunity.getStage()));
    }


}
