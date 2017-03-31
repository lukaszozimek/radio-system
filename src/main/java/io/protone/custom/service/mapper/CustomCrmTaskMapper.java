package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.domain.CrmTask;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * Created by lukaszozimek on 19.01.2017.
 */
@Service
public class CustomCrmTaskMapper {

    public static final String CRM_TASK_STATUS = "CrmTaskStatus";
    @Inject
    private CustomCorUserMapperExt corUserMapper;

    public List<CrmTaskPT> createCrmTasks(Set<CrmTask> crmTask) {
        if (crmTask == null) {
            return new ArrayList<>();
        }
        return crmTask.stream().map(this::createCrmTask).collect(toList());
    }

    public List<CrmTaskPT> createCrmTasks(List<CrmTask> crmTask) {
        if (crmTask == null) {
            return new ArrayList<>();
        }
        return crmTask.stream().map(this::createCrmTask).collect(toList());
    }

    public CrmTaskPT createCrmTask(CrmTask crmTask) {
        if (crmTask == null) {
            return new CrmTaskPT();
        }
        CrmTaskPT crmTaskPT = new CrmTaskPT().id(crmTask.getId()).activityLenght(crmTask.getActivityLength())
            .crmTaskStatus(crmTask.getStatus())
            .assignedTo(corUserMapper.userToCoreUserPT(crmTask.getAssignedTo()))
            .comment(crmTask.getComment())
            .subject(crmTask.getSubject())
            .createdBy(corUserMapper.userToCoreUserPT(crmTask.getCreatedBy()));
        if (crmTask.getActivityDate() != null) {
            crmTaskPT.activityDate(crmTask.getActivityDate().toString());
        }
        return crmTaskPT;

    }

    public Set<CrmTask> createTasksEntity(List<CrmTaskPT> crmTaskPTS) {
        if (crmTaskPTS == null) {
            return new HashSet<>();
        }
        return crmTaskPTS.stream().map(this::createTaskEntity).collect(toSet());
    }

    public CrmTask createTaskEntity(CrmTaskPT taskPT) {
        if (taskPT == null) {
            return new CrmTask();
        }
        CrmTask crmTask = new CrmTask();
        crmTask.setId(taskPT.getId());
        crmTask.setSubject(taskPT.getSubject());
        crmTask.setActivityDate(LocalDate.parse(taskPT.getActivityDate()));
        crmTask.setActivityLength(taskPT.getActivityLenght());
        crmTask.setComment(taskPT.getComment());
        crmTask.assignedTo(corUserMapper.coreUserPTToUser(taskPT.getAssignedTo()));
        crmTask.createdBy(corUserMapper.coreUserPTToUser(taskPT.getCreatedBy()));
        return crmTask;
    }


}
