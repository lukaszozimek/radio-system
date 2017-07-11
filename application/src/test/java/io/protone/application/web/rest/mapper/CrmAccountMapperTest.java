package io.protone.application.web.rest.mapper;


import io.protone.application.ProtoneApp;
import io.protone.core.domain.*;
import io.protone.crm.api.dto.CrmAccountDTO;
import io.protone.crm.domain.CrmAccount;
import io.protone.crm.domain.CrmTask;
import io.protone.crm.mapper.CrmAccountMapper;
import org.assertj.core.util.Sets;
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

    private CrmAccountDTO crmAccountDTO;

    private List<CrmAccountDTO> crmAccountDTOS = new ArrayList<>();

    private List<CrmAccount> crmAccounts = new ArrayList<>();

    private CorNetwork corNetwork;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        crmAccount = factory.manufacturePojo(CrmAccount.class);
        crmAccount.setId(1L);
        crmAccount.setArea(factory.manufacturePojo(CorDictionary.class));
        crmAccount.setPerson(factory.manufacturePojo(CorPerson.class));
        crmAccount.setSize(factory.manufacturePojo(CorDictionary.class));
        crmAccount.setAddres(factory.manufacturePojo(CorAddress.class));
        crmAccount.setCorImageItem(factory.manufacturePojo(CorImageItem.class));
        crmAccount.setRange(factory.manufacturePojo(CorDictionary.class));
        crmAccount.setIndustry(factory.manufacturePojo(CorDictionary.class));
        crmAccount.setKeeper(factory.manufacturePojo(CorUser.class));
        crmAccount.setShortName("fefe");
        crmAccount.setName("fwafwafwa");
        crmAccount.setVatNumber("fwafwa");
        crmAccount.setTasks(Sets.newLinkedHashSet(factory.manufacturePojo(CrmTask.class)));
        crmAccount.setNetwork(factory.manufacturePojo(CorNetwork.class));
        crmAccounts.add(crmAccount);
        crmAccountDTO = factory.manufacturePojo(CrmAccountDTO.class);
        crmAccountDTOS.add(crmAccountDTO);
        corNetwork = factory.manufacturePojo(CorNetwork.class);

    }


    @Test
    public void DB2DTO() throws Exception {
        CrmAccountDTO dto = customCrmAccountMapper.DB2DTO(crmAccount);

        assertNotNull(dto.getArea());
        assertNotNull(dto.getSize());
        assertNotNull(dto.getPerson());

        assertNotNull(dto.getPublicUrl());
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
        List<CrmAccountDTO> dtos = customCrmAccountMapper.DBs2DTOs(crmAccounts);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {

            assertNotNull(dto.getArea());
            assertNotNull(dto.getSize());
            assertNotNull(dto.getPerson());
            assertNotNull(dto.getAccount());
            assertNotNull(dto.getAddres());
            assertNotNull(dto.getPublicUrl());
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
        List<CrmAccount> entities = customCrmAccountMapper.DTOs2DBs(crmAccountDTOS, corNetwork);

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
        CrmAccount entity = customCrmAccountMapper.DTO2DB(crmAccountDTO, corNetwork);


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

    @Test
    public void DB2ThinDTO() throws Exception {
    }

    @Test
    public void DBs2ThinDTOs() throws Exception {
    }

}
