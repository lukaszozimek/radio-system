package io.protone.custom.service;

import io.protone.ProtoneApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 01/03/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class CrmOpportunityServiceTest {
    @Inject
    private CrmOpportunityService crmOpportunityService;

    @Test
    public void getAllOpportunity() throws Exception {

    }

    @Test
    public void saveOpportunity() throws Exception {

    }

    @Test
    public void deleteOpportunity() throws Exception {

    }

    @Test
    public void getOpportunity() throws Exception {

    }

    @Test
    public void getTasksAssociatedWithOpportunity() throws Exception {

    }

    @Test
    public void getTaskAssociatedWithOpportunity() throws Exception {

    }

    @Test
    public void deleteOpportunityTask() throws Exception {

    }

    @Test
    public void saveTasksAssociatedWithOpportunity() throws Exception {

    }

}
