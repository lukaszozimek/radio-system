package io.protone.custom.service.mapper;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.CrmTaskPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmTask;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static io.protone.util.FiledVisitor.checkFiledsNotNull;
import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 01/03/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CustomCrmTaskMapperTest {
    @Autowired
    private CustomCrmTaskMapper crmTaskMapper;
    private CorNetwork mockCorNetwork = null;
    private CrmTask mockCrmTask = null;
    private CrmTaskPT mockCrmTaskPt = null;
    private List<CrmTaskPT> mockListCrmTaskPt = null;
    private List<CrmTask> mockListCrmTask = null;

    @Before
    public void initialize() {

    }

    @Test
    public void createCrmTasks() throws Exception {
        //then
        List<CrmTaskPT> result = crmTaskMapper.createCrmTasks(mockListCrmTask);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

    @Test
    public void createCrmTasks1() throws Exception {
        //then
        List<CrmTaskPT> result = crmTaskMapper.createCrmTasks(mockListCrmTask.stream().collect(toSet()));
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

    @Test
    public void createCrmTask() throws Exception {
        //then
        CrmTaskPT result = crmTaskMapper.createCrmTask(mockCrmTask);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

    @Test
    public void createTasksEntity() throws Exception {
        //then
        Set<CrmTask> result = crmTaskMapper.createTasksEntity(mockListCrmTaskPt);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

    @Test
    public void createTaskEntity() throws Exception {
        //then
        CrmTask result = crmTaskMapper.createTaskEntity(mockCrmTaskPt);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

}
