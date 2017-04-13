package io.protone.custom.service.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.ProtoneApp;
import io.protone.custom.service.dto.CoreUserPT;
import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static io.protone.util.FiledVisitor.checkFiledsNotNull;
import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 01/03/2017.
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = ProtoneApp.class)
public class CustomCrmTaskMapperTest {
    @Autowired
    private CustomCrmTaskMapper crmTaskMapper;
    private CorNetwork mockCorNetwork = null;
    private CrmTask mockCrmTask = null;
    private CrmTaskPT mockCrmTaskPt = null;
    private List<CrmTaskPT> mockListCrmTaskPt = null;
    private List<CrmTask> mockListCrmTask = null;

  //  @Before
    public void initialize() {
        mockCorNetwork = new CorNetwork();
        mockCrmTask = new CrmTask();
        mockCrmTask.setId((long) 1);
        mockCrmTask.subject("test")
            .activityDate(LocalDate.now())
            .activityLength(Long.valueOf(124512521))
            .comment("test")
            .createdBy(new CorUser())
            .assignedTo(new CorUser())
            .status(new CrmTaskStatus())
            .network(mockCorNetwork)
            .opportunity(new CrmOpportunity())
            .lead(new CrmLead())
            .contact(new CrmContact())
            .account(new CrmAccount())
            .tasks(new CrmTask());
        mockCrmTaskPt.id((long) 1)
            .crmTaskStatus(new CrmTaskStatus())
            .createdBy(new CoreUserPT())
            .assignedTo(new CoreUserPT())
            .subject("test")
            .activityDate(LocalDate.now())
            .activityLenght((long) 21444)
            .comment("test")
            .addRelatedTasksItem(new CrmTaskPT());
    }

 //   @Test
    public void createCrmTasks() throws Exception {
        //then
        List<CrmTaskPT> result = crmTaskMapper.createCrmTasks(mockListCrmTask);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

 //   @Test
    public void createCrmTasks1() throws Exception {
        //then
        List<CrmTaskPT> result = crmTaskMapper.createCrmTasks(mockListCrmTask.stream().collect(toSet()));
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

//    @Test
    public void createCrmTask() throws Exception {
        //then
        CrmTaskPT result = crmTaskMapper.createCrmTask(mockCrmTask);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

//    @Test
    public void createTasksEntity() throws Exception {
        //then
        Set<CrmTask> result = crmTaskMapper.createTasksEntity(mockListCrmTaskPt);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

//    @Test
    public void createTaskEntity() throws Exception {
        //then
        CrmTask result = crmTaskMapper.createTaskEntity(mockCrmTaskPt);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

}
