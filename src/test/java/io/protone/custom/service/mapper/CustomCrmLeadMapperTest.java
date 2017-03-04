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
public class CustomCrmLeadMapperTest {
    @Autowired
    private CustomCrmLeadMapper customCrmLeadMapper;
    private CrmLead mockCrmLead = null;
    private CrmLeadPT mockCrmLeadPT = null;
    private CorNetwork mockCorNetwork = null;

    @Before
    public void initialize() {
        mockCorNetwork = new CorNetwork();
        mockCrmLead = new CrmLead();
        mockCrmLead.setId((long) 1);
        mockCrmLead.name("test")
            .shortname("test")
            .description("test")
            .person(new CorPerson())
            .addres(new CorAddress())
            .leadStatus(new CrmLeadStatus())
            .leadSource(new CrmLeadSource())
            .keeper(new CorUser())
            .industry(new TraIndustry())
            .area(new CorArea())
            .network(new CorNetwork())
            .addTasks(new CrmTask());
        mockCrmLeadPT = new CrmLeadPT().id((long) 1)
            .name("test")
            .shortName("test")
            .description("test")
            .source(new ConfLeadSourcePT())
            .status(new ConfLeadStatusPT())
            .adress(new CoreAddressPT())
            .area(new CoreAreaPT())
            .industry(new ConfIndustryPT())
            .owner(new CoreUserPT())
            .person(new TraCustomerPersonPT())
            .addTasksItem(new CrmTaskPT());

    }

    @Test
    public void createLeadEntity() throws Exception {
        //then
        CrmLead result = customCrmLeadMapper.createLeadEntity(mockCrmLeadPT, mockCorNetwork);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

    @Test
    public void createDTOFromEntites() throws Exception {
        //then
        CrmLeadPT result = customCrmLeadMapper.createDTOFromEntites(mockCrmLead);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

}
