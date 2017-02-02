package io.protone.custom.service;

import io.protone.custom.service.dto.CoreManagedUserPT;
import io.protone.custom.service.dto.CrmContactPT;
import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.custom.service.mapper.CustomCRMContactMapper;
import io.protone.custom.service.mapper.CustomCRMTaskMapper;
import io.protone.domain.*;
import io.protone.repository.*;
import io.protone.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 17.01.2017.
 */

@Service
@Transactional
public class CRMContactService {

    @Inject
    private CustomCRMContactMapper customCRMContactMapper;

    @Inject
    private CRMContactRepository crmContactRepository;


    public List<CrmContactPT> getAllContact(CORNetwork corNetwork) {
        return null;
    }

    public CrmContactPT saveContact(CrmContactPT crmContactPT, CORNetwork corNetwork) {
        return null;
    }

    public void deleteContact(String shortcut, CORNetwork corNetwork) {
    }

    public void deleteContact(Long id, CORNetwork corNetwork) {
    }

    public CrmContactPT getContact(String shortcut, CORNetwork corNetwork) {
        return null;
    }

    private CrmContactPT fetchContact(CRMContact crmContact, CORNetwork corNetwork) {
        return null;
    }

    public CrmContactPT update(CrmContactPT crmContactPT, CORNetwork corNetwork) {
        return null;
    }

    private void deleteContact(CRMContact crmContact, CORNetwork corNetwork) {

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
