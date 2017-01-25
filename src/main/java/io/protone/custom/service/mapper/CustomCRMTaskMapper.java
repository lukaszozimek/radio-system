package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CoreManagedUserPT;
import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.domain.CRMTask;
import io.protone.repository.UserRepository;
import io.protone.service.UserService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
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

    public List<CRMTask> createTasksEntity(List<CrmTaskPT> crmTaskPTS) {
        List<CRMTask> taskList = new ArrayList<>();
        for (CrmTaskPT task : crmTaskPTS) {
            taskList.add(createTaskEntity(task));
        }
        return taskList;
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
