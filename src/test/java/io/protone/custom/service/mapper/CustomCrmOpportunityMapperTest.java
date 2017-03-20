package io.protone.custom.service.mapper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.ProtoneApp;
import io.protone.custom.service.dto.*;
import io.protone.domain.*;
import org.hibernate.annotations.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.protone.util.FiledVisitor.checkFiledsNotNull;
import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 01/03/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CustomCrmOpportunityMapperTest {
    @Autowired
    private CustomCrmOpportunityMapper customCrmOpportunityMapper;
    private CorNetwork mockCorNetwork = null;
    private CrmOpportunity mockCrmOpportunity = null;
    private CrmOpportunityPT mockCrmOpportunityPT = null;

    @Before
    public void initialize() {
        mockCorNetwork = new CorNetwork();
        mockCrmOpportunity = new CrmOpportunity();
        mockCrmOpportunity.setId((long) 1);
        mockCrmOpportunity.name("test")
            .lastTry(LocalDate.now())
            .closeDate(LocalDate.now())
            .probability(89)
            .stage(new CrmStage())
            .keeper(new CorUser())
            .contact(new CrmContact())
            .lead(new CrmLead())
            .account(new CrmAccount())
            .network(mockCorNetwork)
            .addTasks(new CrmTask());
        mockCrmOpportunityPT = new CrmOpportunityPT()
            .id((long) 1)
            .opportunityOwner(new CoreUserPT())
            .name("test")
            .contact(new CrmContactPT())
            .lastTry(LocalDate.now().toString())
            .closeDate(LocalDate.now().toString())
            .stage(new ConfCrmStagePT())
            .propability(1)
            .addTasksItem(new CrmTaskPT());
    }

    @Test
    public void createOpportunity() throws Exception {
        //then
        CrmOpportunity result = customCrmOpportunityMapper.createOpportunity(mockCrmOpportunityPT, mockCorNetwork);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

    @Test
    public void buildDTOFromEntites() throws Exception {
        //then
        CrmOpportunityPT result = customCrmOpportunityMapper.buildDTOFromEntites(mockCrmOpportunity);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

}
