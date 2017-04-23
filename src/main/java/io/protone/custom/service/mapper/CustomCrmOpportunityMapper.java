package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CrmOpportunityPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmOpportunity;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.format.DateTimeFormatter;

/**
 * Created by lukaszozimek on 18.01.2017.
 */
@Service
public class CustomCrmOpportunityMapper {

    @Inject
    private CustomCorDictionaryMapper corDictionaryMapper;

    @Inject
    private CustomCrmContactMapper customCrmContactMapper;
    @Inject
    private CustomCrmTaskMapper customCrmTaskMapper;
    @Inject
    private CustomCorUserMapperExt corUserMapper;

    public CrmOpportunity createOpportunity(CrmOpportunityPT opportunityPT, CorNetwork corNetwork) {
        if (opportunityPT == null || corNetwork == null) {
            return new CrmOpportunity();
        }
        CrmOpportunity opportunity = new CrmOpportunity();
        opportunity.setId(opportunityPT.getId());
        return
            opportunity.name(opportunityPT.getName()).keeper(corUserMapper.coreUserPTToUser(opportunityPT.getOpportunityOwner()))
                .closeDate(opportunityPT.getCloseDate())
                .probability(opportunityPT.getPropability())
                .lastTry(opportunityPT.getLastTry())
                .network(corNetwork);
    }

    public CrmOpportunityPT buildDTOFromEntites(CrmOpportunity opportunity) {
        if (opportunity != null) {
            return new CrmOpportunityPT();
        }
        CrmOpportunityPT crmOpportunityPT = new CrmOpportunityPT();
        crmOpportunityPT.setId(opportunity.getId());
        return crmOpportunityPT
            .lastTry(opportunity.getLastTry())
            .name(opportunity.getName())
            .propability(opportunity.getProbability())
            .closeDate(opportunity.getCloseDate())
            .tasks(customCrmTaskMapper.createCrmTasks(opportunity.getTasks()))
            .contact(customCrmContactMapper.buildContactDTOFromEntities(opportunity.getContact()))
            .opportunityOwner(corUserMapper.userToCoreUserPT(opportunity.getKeeper()))
            .stage(corDictionaryMapper.corDictionaryToCorDictionaryDTO(opportunity.getStage()));
    }


}
