package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CrmOpportunityPT;
import io.protone.domain.CRMContact;
import io.protone.domain.CRMOpportunity;
import io.protone.domain.CRMStage;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    public CRMOpportunity createOpportunity(CrmOpportunityPT opportunityPT) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return new CRMOpportunity()
            .name(opportunityPT.getName())
            .closeDate(LocalDate.parse(opportunityPT.getCloseDate(), formatter))
            .probability(opportunityPT.getPropability())
            .lastTry(LocalDate.parse(opportunityPT.getLastTry(), formatter));
    }

    public CRMStage createStageEntity(CrmOpportunityPT opportunityPT) {
        return stageMapper.cRMStageDTOToCRMStage(opportunityPT.getStage());
    }
}
