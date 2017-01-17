package io.protone.custom.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.custom.service.dto.*;
import io.protone.repository.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service
public class CRMOpportunityService {

    @Inject
    private CRMStageRepository stageRepository;

    @Inject
    private CORAssociationRepository associationRepository;

    @Inject
    private CRMOpportunityRepository opportunityRepository;

    @Inject
    private CRMTaskRepository taskRepository;

    public List<CrmOpportunityPT> getAllOpportunity() {

        return null;
    }

    public CrmOpportunityPT saveOpportunity() {

        return null;
    }

    public void deleteOpportunity() {

    }

    public CrmOpportunityPT getOpportunity(String shortcut) {
        return null;
    }
}
