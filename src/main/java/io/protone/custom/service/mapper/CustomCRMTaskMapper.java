package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CoreManagedUserPT;
import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.domain.CRMTask;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukaszozimek on 19.01.2017.
 */
@Service
public class CustomCRMTaskMapper {
    public List<CrmTaskPT> transformTasksFromEntity(List<CRMTask> tasks) {
        List<CrmTaskPT> crmTaskPTList = new ArrayList<>();
        tasks.stream().forEach(task -> {
            crmTaskPTList.add(createCrmTask(task));
        });
        return crmTaskPTList;
    }

    public CrmTaskPT createCrmTask(CRMTask crmTask) {
        return new CrmTaskPT().id(crmTask.getId())
            .activityDate(crmTask.getActivityDate().toString())
            .activityLenght(crmTask.getActivityLength())
            .assignedTo(new CoreManagedUserPT())
            .comment(crmTask.getComment())
            .subject(crmTask.getSubject())
            .createdBy(new CoreManagedUserPT());
    }

    public List<CRMTask> createTasksEntity(List<CrmTaskPT> leadTasks) {
        List<CRMTask> taskList = new ArrayList<>();
        for (CrmTaskPT task : leadTasks) {
            taskList.add(createTaskEntity(task));
        }
        return taskList;
    }

    public CRMTask createTaskEntity(CrmTaskPT leadTask) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        CRMTask crmTask = new CRMTask();
        crmTask.setId(leadTask.getId());
        crmTask.setSubject(leadTask.getSubject());
        crmTask.setActivityDate(LocalDate.parse(leadTask.getActivityDate(), formatter));
        crmTask.setActivityLength(leadTask.getActivityLenght());
        crmTask.setComment(leadTask.getComment());
        return crmTask;
    }

}
