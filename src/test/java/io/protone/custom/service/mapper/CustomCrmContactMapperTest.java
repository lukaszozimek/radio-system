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
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = ProtoneApp.class)
public class CustomCrmContactMapperTest {

    @Autowired
    private CustomCrmContactMapper customCrmContactMapper;

    private CrmContact mockCrmContact = null;
    private CorNetwork mockCorNetwork = null;
    private CrmContactPT mockCrmContactPT = null;

  /*
    public void initialize() {
        mockCorNetwork = new CorNetwork();
        mockCrmContact = new CrmContact();
        mockCrmContact.setId((long) 1);
        mockCrmContact.shortName("test")
            .paymentDate(LocalDate.now())
            .name("test")
            .externalId1("test")
            .externalId2("test")
            .paymentDelay(1)
            .vatNumber("test")
            .addres(new CorAddress())
            .addTasks(new CrmTask())
            .area(new CorArea())
            .country(new CorCountry())
            .person(new CorPerson())
            .network(mockCorNetwork)
            .range(new CorRange())
            .size(new CorSize())
            .industry(new TraIndustry())
            .keeper(new CorUser())
            .status(new CrmContactStatus());

        mockCrmContactPT = new CrmContactPT()
            .id((long) 1)
            .area(new CoreAreaPT())
            .idNumber2("test")
            .idNumber1("test")
            .industry(new ConfIndustryPT())
            .name("test")
            .networkId(new CoreNetworkPT())
            .paymentDate(11)
            .paymentDelay(11)
            .size(new CoreSizePT())
            .vatNumber("test")
            .shortName("test")
            .adress(new CoreAddressPT())
            .account(new CoreUserPT())
            .persons(new TraCustomerPersonPT())
            .addTasksItem(new CrmTaskPT());
    }
*/
   // @Test
    public void createCrmContactEntity() throws Exception {
        //then
        CrmContact result = customCrmContactMapper.createCrmContactEntity(mockCrmContactPT, mockCorNetwork);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

  //  @Test
    public void buildContactDTOFromEntities() throws Exception {
        //then
        CrmContactPT result = customCrmContactMapper.buildContactDTOFromEntities(mockCrmContact);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

}
