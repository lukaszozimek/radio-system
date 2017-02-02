package io.protone.custom.service;

import io.protone.custom.service.dto.CrmLeadPT;
import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.custom.service.mapper.CustomCRMLeadMapper;
import io.protone.custom.service.mapper.CustomCRMTaskMapper;
import io.protone.domain.*;
import io.protone.repository.*;
import io.protone.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service
@Transactional
public class CRMLeadService {

    @Inject
    private CustomCRMLeadMapper customCRMLeadMapper;

    @Inject
    private CRMLeadRepository crmLeadRepository;


    @Transactional
    public List<CrmLeadPT> getAllLeads(CORNetwork corNetwork) {
        return null;
    }

    public CrmLeadPT saveLead(CrmLeadPT lead, CORNetwork corNetwork) {
        return null;
    }

    public void deleteLead(String shortcut, CORNetwork corNetwork) {

    }

    @Transactional
    public CrmLeadPT getLead(String shortcut, CORNetwork corNetwork) {
        return null;
    }

    public CrmLeadPT update(CrmLeadPT leadPT, CORNetwork corNetwork) {
        return null;
    }

    public List<CrmTaskPT> getTasksAssociatedWithLead(String shortcut, CORNetwork corNetwork) {
        return null;
    }

    public CrmTaskPT getTaskAssociatedWithLead(String shortcut, Long taskId, CORNetwork corNetwork) {
        return null;
    }

    public void deleteLeadTask(String shortcut, Long taskId, CORNetwork corNetwork) {
    }

    public CrmTaskPT createTasksAssociatedWithLead(String shortcut, CrmTaskPT taskPT, CORNetwork corNetwork) {
        return null;
    }

    public CrmTaskPT updateLeadTask(String shortcut, CrmTaskPT crmTask, CORNetwork corNetwork) {
        return null;
    }
}
