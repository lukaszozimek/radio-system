package io.protone.custom.service.mapper;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.CrmAccountPT;
import io.protone.custom.service.dto.TraCustomerPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmAccount;
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
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CustomCrmAccountMapperTest {

    @Autowired
    private CustomCrmAccountMapper crmAccountMappera;

    private CrmAccount mockCrmAccount = null;

    private List<CrmAccount> mockListCrmAccount = null;

    private CrmAccountPT mockCrmAccoumntPT = null;

    private TraCustomerPT mockTraCustomerPT = null;


    private CorNetwork mockCorNetwork = null;

    @Before
    public void initialize() {
        mockCorNetwork = new CorNetwork();
        mockCorNetwork.setId((long) 1);
        mockCorNetwork.setName("test");
        mockCorNetwork.setDescription("test");
        mockCorNetwork.setShortcut("test");

    }

    @Test
    public void createCrmAcountEntity() throws Exception {
        //then
        CrmAccount result = crmAccountMappera.createCrmAcountEntity(mockCrmAccoumntPT, mockCorNetwork);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

    @Test
    public void createCrmAcountEntity1() throws Exception {
        CrmAccount result =  crmAccountMappera.createCrmAcountEntity(mockTraCustomerPT,mockCorNetwork);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

    @Test
    public void createCustomerTrafficDTO() throws Exception {
        TraCustomerPT result =   crmAccountMappera.createCustomerTrafficDTO(mockCrmAccount);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

    @Test
    public void buildContactDTOFromEntities() throws Exception {
        CrmAccountPT result =  crmAccountMappera.buildContactDTOFromEntities(mockCrmAccount);
        //assert
        assertEquals(false, checkFiledsNotNull(result));
    }

}
