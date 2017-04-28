package io.protone.service.mapper;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.CrmAccountPT;
import io.protone.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by lukaszozimek on 28.04.2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CrmAccountMapperTest {
    @Autowired
    private CrmAccountMapper customCrmAccountMapper;

    private CrmAccount crmAccount;

    private CrmAccountPT crmAccountPT;

    private List<CrmAccountPT> crmAccountPTS = new ArrayList<>();

    private List<CrmAccount> crmAccounts = new ArrayList<>();

    private CorNetwork corNetwork;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        crmAccount = factory.manufacturePojo(CrmAccount.class);
        crmAccounts.add(crmAccount);
        crmAccountPT = factory.manufacturePojo(CrmAccountPT.class);
        crmAccountPTS.add(crmAccountPT);
        corNetwork = factory.manufacturePojo(CorNetwork.class);

    }


    @Test
    public void DB2DTO() throws Exception {
        CrmAccountPT dto = customCrmAccountMapper.DB2DTO(crmAccount);

        assertNotNull(dto.getArea());
        assertNotNull(dto.getSize());
        assertNotNull(dto.getPerson());
        assertNotNull(dto.getAccount());
        assertNotNull(dto.getAddres());
        assertNotNull(dto.getRange());
        assertNotNull(dto.getIndustry());
        assertNotNull(dto.getId());
        assertNotNull(dto.getShortName());
        assertNotNull(dto.getName());
        assertNotNull(dto.getPaymentDelay());
        assertNotNull(dto.getVatNumber());
        assertNotNull(dto.getTasks());
    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<CrmAccountPT> dtos = customCrmAccountMapper.DBs2DTOs(crmAccounts);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {

            assertNotNull(dto.getArea());
            assertNotNull(dto.getSize());
            assertNotNull(dto.getPerson());
            assertNotNull(dto.getAccount());
            assertNotNull(dto.getAddres());
            assertNotNull(dto.getRange());
            assertNotNull(dto.getIndustry());
            assertNotNull(dto.getId());
            assertNotNull(dto.getShortName());
            assertNotNull(dto.getName());
            assertNotNull(dto.getPaymentDelay());
            assertNotNull(dto.getVatNumber());
            assertNotNull(dto.getTasks());

        });
    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<CrmAccount> entities = customCrmAccountMapper.DTOs2DBs(crmAccountPTS, corNetwork);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {

            assertNotNull(entity.getArea());
            assertNotNull(entity.getSize());
            assertNotNull(entity.getPerson());
            assertNotNull(entity.getAddres());
            assertNotNull(entity.getRange());
            assertNotNull(entity.getIndustry());
            assertNotNull(entity.getKeeper());
            assertNotNull(entity.getId());
            assertNotNull(entity.getShortName());
            assertNotNull(entity.getName());
            assertNotNull(entity.getVatNumber());
            assertNotNull(entity.getPaymentDelay());
            assertNotNull(entity.getTasks());
            assertNotNull(entity.getNetwork());


        });
    }

    @Test
    public void DTO2DB() throws Exception {
        CrmAccount entity = customCrmAccountMapper.DTO2DB(crmAccountPT, corNetwork);


        assertNotNull(entity.getArea());
        assertNotNull(entity.getSize());
        assertNotNull(entity.getPerson());
        assertNotNull(entity.getAddres());
        assertNotNull(entity.getRange());
        assertNotNull(entity.getIndustry());
        assertNotNull(entity.getKeeper());
        assertNotNull(entity.getId());
        assertNotNull(entity.getShortName());
        assertNotNull(entity.getName());
        assertNotNull(entity.getVatNumber());
        assertNotNull(entity.getPaymentDelay());
        assertNotNull(entity.getTasks());
        assertNotNull(entity.getNetwork());
    }


}
