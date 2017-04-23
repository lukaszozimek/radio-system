package io.protone.custom.service.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.protone.ProtoneApp;
import io.protone.custom.service.dto.*;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmAccount;
import io.protone.domain.TraDiscount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import java.util.List;

import static io.protone.util.FiledVisitor.checkFiledsNotNull;
import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 01/03/2017.
 */

///TODO: Apply this test after merging Security user and country
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = ProtoneApp.class)
public class CustomCrmAccountMapperTest {

    @Autowired
    private CustomCrmAccountMapper crmAccountMappera;

    private CrmAccount mockCrmAccount = null;

    private List<CrmAccount> mockListCrmAccount = null;

    private CrmAccountPT mockCrmAccoumntPT = null;

    private TraCustomerPT mockTraCustomerPT = null;

    private CorNetwork mockCorNetwork = null;

/*
    public void initialize() {
        mockCorNetwork = new CorNetwork();
        mockCorNetwork.setId((long) 1);
        mockCorNetwork.setName("test");
        mockCorNetwork.setDescription("test");
        mockCorNetwork.setShortcut("test");
        mockTraCustomerPT = new TraCustomerPT();
        mockTraCustomerPT.setId((long) 1);
        mockTraCustomerPT.shortName("test");
        mockTraCustomerPT.area(new CoreAreaPT());
        mockTraCustomerPT.idNumber1("test");
        mockTraCustomerPT.idNumber2("test2");
        mockTraCustomerPT.paymentDelay(21412421);
        mockTraCustomerPT.industry(new ConfIndustryPT());
        mockTraCustomerPT.name("test");
        mockTraCustomerPT.networkId(new CoreNetworkPT());
        mockTraCustomerPT.paymentDate(121421412);
        mockTraCustomerPT.range(new CoreRangePT());
        mockTraCustomerPT.setSize(new CoreSizePT());
        mockTraCustomerPT.vatNumber("tetst");
        mockTraCustomerPT.setAdress(new CoreAddressPT());
        mockTraCustomerPT.setAccount(new CoreUserPT());
        mockTraCustomerPT.setPersons(new TraCustomerPersonPT());
        mockTraCustomerPT.discount(new TraDiscount());

    }
*/
   // @Test
    public void createCrmAcountEntity() throws Exception {
        //then
        CrmAccount result = crmAccountMappera.createCrmAcountEntity(mockCrmAccoumntPT, mockCorNetwork);
        //assert
        assertEquals(true, checkFiledsNotNull(result));
    }

  //  @Test
    public void createCrmAcountEntity1() throws Exception {
        CrmAccount result = crmAccountMappera.createCrmAcountEntity(mockTraCustomerPT, mockCorNetwork);
        //assert
        assertEquals(true, checkFiledsNotNull(result));
    }

  //  @Test
    public void createCustomerTrafficDTO() throws Exception {
        TraCustomerPT result = crmAccountMappera.createCustomerTrafficDTO(mockCrmAccount);
        //assert
        assertEquals(true, checkFiledsNotNull(result));
    }

 //   @Test
    public void buildContactDTOFromEntities() throws Exception {
        CrmAccountPT result = crmAccountMappera.buildContactDTOFromEntities(mockCrmAccount);
        //assert
        assertEquals(true, checkFiledsNotNull(result));
    }

}
