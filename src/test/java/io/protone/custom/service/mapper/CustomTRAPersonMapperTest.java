package io.protone.custom.service.mapper;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.protone.ProtoneApp;
import io.protone.custom.service.dto.CoreContactPT;
import io.protone.custom.service.dto.CoreNetworkPT;
import io.protone.custom.service.dto.TraCustomerPersonPT;
import io.protone.domain.CorContact;
import io.protone.domain.CorNetwork;
import io.protone.domain.CorPerson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;

import static io.protone.util.FiledVisitor.checkFiledsNotNull;
import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 01/03/2017.
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = ProtoneApp.class)
public class CustomTRAPersonMapperTest {
    @Autowired
    private CustomTRAPersonMapper customTRAPersonMapper;

    private CorPerson mockPerson = null;
    private CorNetwork mockCorNetwork = null;
    private TraCustomerPersonPT mockTraPersonPt = null;

  //  @Before
    public void initialize() {
        mockPerson = new CorPerson();
        mockPerson.setFirstName("test");
        mockPerson.setLastName("test");
        mockPerson.setNetwork(new CorNetwork());
        mockPerson.setContacts(Sets.newHashSet(new CorContact()));
        mockPerson.setId((long) 1);
        mockPerson.setDescription("test");
        mockCorNetwork = new CorNetwork();

        mockTraPersonPt = new TraCustomerPersonPT();
        mockTraPersonPt.setId((long) 1);
        mockTraPersonPt.setDescription("test");
        mockTraPersonPt.setFirstName("test");
        mockTraPersonPt.setLastName("test");
        mockTraPersonPt.setNetworkId(new CoreNetworkPT());
        mockTraPersonPt.setContacts(Lists.newArrayList(new CoreContactPT()));

    }


   // @Test
    public void createDTOObject() throws Exception {
        //then
        TraCustomerPersonPT result = customTRAPersonMapper.createDTOObject(mockPerson);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

//    @Test
    public void createPersonEntity() throws Exception {
        //then
        CorPerson result = customTRAPersonMapper.createPersonEntity(mockTraPersonPt, mockCorNetwork);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

}
