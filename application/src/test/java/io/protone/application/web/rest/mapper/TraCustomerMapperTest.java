package io.protone.application.web.rest.mapper;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.*;
import io.protone.crm.domain.CrmAccount;
import io.protone.crm.domain.CrmTask;
import io.protone.traffic.api.dto.TraCustomerDTO;
import io.protone.traffic.mapper.TraCustomerMapper;
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
 * Created by lukaszozimek on 28/06/2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TraCustomerMapperTest {
    @Autowired
    private TraCustomerMapper traCustomerMapper;

    private CrmAccount crmAccount;

    private TraCustomerDTO traCustomerDTO;

    private List<TraCustomerDTO> traCustomerDTOS = new ArrayList<>();

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
        crmAccount.setRange(factory.manufacturePojo(CorDictionary.class));
        crmAccount.setIndustry(factory.manufacturePojo(CorDictionary.class));
        crmAccount.setKeeper(factory.manufacturePojo(CorUser.class));
        crmAccount.setShortName("fefe");
        crmAccount.setName("fwafwafwa");
        crmAccount.setVatNumber("fwafwa");
        crmAccount.setTasks(Sets.newLinkedHashSet(factory.manufacturePojo(CrmTask.class)));
        crmAccount.setNetwork(factory.manufacturePojo(CorNetwork.class));
        crmAccounts.add(crmAccount);
        traCustomerDTO = factory.manufacturePojo(TraCustomerDTO.class);
        traCustomerDTOS.add(traCustomerDTO);
        corNetwork = factory.manufacturePojo(CorNetwork.class);

    }


    @Test
    public void traDB2DTO() throws Exception {
        TraCustomerDTO dto = traCustomerMapper.traDB2DTO(crmAccount);

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

    }

    @Test
    public void traDTOs2DBs() throws Exception {
        List<TraCustomerDTO> dtos = traCustomerMapper.traDBs2DTOs(crmAccounts);

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


        });
    }

    @Test
    public void traDBs2DTOs() throws Exception {
        List<CrmAccount> entities = traCustomerMapper.traDTOs2DBs(traCustomerDTOS, corNetwork);

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
    public void traDTO2DB() throws Exception {
        CrmAccount entity = traCustomerMapper.traDTO2DB(traCustomerDTO, corNetwork);


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
