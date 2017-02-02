package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CoreManagedUserPT;
import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.domain.*;
import io.protone.repository.UserRepository;
import io.protone.service.UserService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Created by lukaszozimek on 19.01.2017.
 */
@Service
public class CustomCRMTaskMapper {

    public static final String CRM_TASK_STATUS="CRMTaskStatus";
    public List<CrmTaskPT> createCrmTasks(Set<CRMTask> crmTask) {
        return crmTask.stream().map(this::createCrmTask).collect(toList());
    }
    public CrmTaskPT createCrmTask(CRMTask crmTask) {
        return new CrmTaskPT().id(crmTask.getId())
            .activityDate(crmTask.getActivityDate().toString())
            .activityLenght(crmTask.getActivityLength())
            .crmTaskStatus(crmTask.getStatus())
            .assignedTo(crmTask.getAssignedTo())
            .comment(crmTask.getComment())
            .subject(crmTask.getSubject())
            .createdBy(crmTask.getCreatedBy());
    }

    public List<CRMTask> createTasksEntity(List<CrmTaskPT> crmTaskPTS) {
        return crmTaskPTS.stream().map(this::createTaskEntity).collect(toList());
    }

    public CRMTask createTaskEntity(CrmTaskPT taskPT) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        CRMTask crmTask = new CRMTask();
        crmTask.setId(taskPT.getId());
        crmTask.setSubject(taskPT.getSubject());
        crmTask.setActivityDate(LocalDate.parse(taskPT.getActivityDate(), formatter));
        crmTask.setActivityLength(taskPT.getActivityLenght());
        crmTask.setComment(taskPT.getComment());
        return crmTask;
    }


}
