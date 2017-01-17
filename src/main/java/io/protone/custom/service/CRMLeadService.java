package io.protone.custom.service;

import io.protone.custom.service.dto.CrmLeadPT;
import io.protone.domain.CRMLeadSource;
import io.protone.repository.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service
public class CRMLeadService {

    @Inject
    CRMLeadRepository crmLeadRepository;

    @Inject
    CRMLeadStatusRepository crmLeadStatusRepository;

    @Inject
    CRMLeadSourceRepository crmLeadSourceRepository;

    @Inject
    TRAIndustryRepository industryRepository;

    @Inject
    CORPersonRepository personRepository;

    @Inject
    CORAssociationRepository associationRepository;

    @Inject
    CRMTaskRepository crmTaskRepository;

    public List<CrmLeadPT> getAllLeads() {

        return null;
    }

    public CrmLeadPT saveLead() {

        return null;
    }

    public void deleteLead() {

    }

    public CrmLeadPT getLead(String shortcut) {
        return null;
    }

}
