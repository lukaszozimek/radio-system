package io.protone.application.web.rest.mapper;


import io.protone.application.ProtoneApp;
import io.protone.core.domain.*;
import io.protone.crm.api.dto.CrmContactDTO;
import io.protone.crm.api.dto.thin.CrmContactThinDTO;
import io.protone.crm.domain.CrmContact;
import io.protone.crm.domain.CrmTask;
import io.protone.crm.mapper.CrmContactMapper;
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
public class CrmContactMapperTest {


    @Autowired
    private CrmContactMapper customCrmContactMapper;

    private CrmContact crmContact;

    private CrmContactDTO crmContactDTO;

    private CorChannel corChannel;

    private List<CrmContactDTO> crmContactDTOS = new ArrayList<>();

    private List<CrmContact> crmContacts = new ArrayList<>();

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        crmContact = factory.manufacturePojo(CrmContact.class);
        crmContact.setId(1L);
        crmContact.setArea(factory.manufacturePojo(CorDictionary.class));
        crmContact.setPerson(factory.manufacturePojo(CorPerson.class));
        crmContact.setSize(factory.manufacturePojo(CorDictionary.class));
        crmContact.setAddres(factory.manufacturePojo(CorAddress.class));
        crmContact.setRange(factory.manufacturePojo(CorDictionary.class));
        crmContact.setIndustry(factory.manufacturePojo(CorDictionary.class));
        crmContact.setCorImageItem(factory.manufacturePojo(CorImageItem.class));
        crmContact.setKeeper(factory.manufacturePojo(CorUser.class));
        crmContact.setCreatedBy(factory.manufacturePojo(CorUser.class));
        crmContact.setLastModifiedBy(factory.manufacturePojo(CorUser.class));
        crmContact.setShortName("fefe");
        crmContact.setName("fwafwafwa");
        crmContact.setVatNumber("fwafwa");
        crmContact.setTasks(Sets.newLinkedHashSet(factory.manufacturePojo(CrmTask.class)));
        crmContact.setChannel(factory.manufacturePojo(CorChannel.class));
        crmContacts.add(crmContact);
        crmContactDTO = factory.manufacturePojo(CrmContactDTO.class);
        crmContactDTOS.add(crmContactDTO);
        corChannel = factory.manufacturePojo(CorChannel.class);
    }

    @Test
    public void DB2DTO() throws Exception {
        CrmContactDTO dto = customCrmContactMapper.DB2DTO(crmContact);

        assertNotNull(dto.getId());

        assertNotNull(dto.getArea());
        assertNotNull(dto.getSize());
        assertNotNull(dto.getPerson());
        assertNotNull(dto.getAccount());
        assertNotNull(dto.getPublicUrl());
        assertNotNull(dto.getDescription());
        assertNotNull(dto.getAddres());
        assertNotNull(dto.getRange());
        assertNotNull(dto.getIndustry());
        assertNotNull(dto.getShortName());
        assertNotNull(dto.getName());
        assertNotNull(dto.getPaymentDelay());
        assertNotNull(dto.getVatNumber());
        assertNotNull(dto.getTasks());
        assertNotNull(dto.getCreatedBy());
        assertNotNull(dto.getCreatedDate());
        assertNotNull(dto.getLastModifiedBy());
        assertNotNull(dto.getLastModifiedDate());
    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<CrmContactDTO> dtos = customCrmContactMapper.DBs2DTOs(crmContacts);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getId());

            assertNotNull(dto.getArea());
            assertNotNull(dto.getSize());
            assertNotNull(dto.getPerson());
            assertNotNull(dto.getAccount());

            assertNotNull(dto.getPublicUrl());
            assertNotNull(dto.getAddres());
            assertNotNull(dto.getRange());
            assertNotNull(dto.getIndustry());
            assertNotNull(dto.getShortName());
            assertNotNull(dto.getName());
            assertNotNull(dto.getDescription());
            assertNotNull(dto.getPaymentDelay());
            assertNotNull(dto.getVatNumber());
            assertNotNull(dto.getTasks());
            assertNotNull(dto.getCreatedBy());
            assertNotNull(dto.getCreatedDate());
            assertNotNull(dto.getLastModifiedBy());
            assertNotNull(dto.getLastModifiedDate());
        });
    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<CrmContact> entities = customCrmContactMapper.DTOs2DBs(crmContactDTOS, corChannel);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {

            assertNotNull(entity.getId());
            assertNotNull(entity.getArea());
            assertNotNull(entity.getSize());
            assertNotNull(entity.getPerson());
            assertNotNull(entity.getAddres());
            assertNotNull(entity.getRange());
            assertNotNull(entity.getIndustry());
            assertNotNull(entity.getKeeper());
            assertNotNull(entity.getShortName());
            assertNotNull(entity.getPaymentDelay());
            assertNotNull(entity.getDescription());
            assertNotNull(entity.getName());
            assertNotNull(entity.getVatNumber());
            assertNotNull(entity.getTasks());
            assertNotNull(entity.getChannel());
        });
    }

    @Test
    public void DTO2DB() throws Exception {
        CrmContact entity = customCrmContactMapper.DTO2DB(crmContactDTO, corChannel);

        assertNotNull(entity.getId());
        assertNotNull(entity.getArea());
        assertNotNull(entity.getSize());
        assertNotNull(entity.getPerson());
        assertNotNull(entity.getAddres());
        assertNotNull(entity.getRange());
        assertNotNull(entity.getIndustry());
        assertNotNull(entity.getKeeper());
        assertNotNull(entity.getShortName());
        assertNotNull(entity.getPaymentDelay());
        assertNotNull(entity.getDescription());
        assertNotNull(entity.getName());
        assertNotNull(entity.getVatNumber());
        assertNotNull(entity.getTasks());
        assertNotNull(entity.getChannel());
    }

    @Test
    public void DB2ThinDTO() throws Exception {
        CrmContactThinDTO dto = customCrmContactMapper.DB2ThinDTO(crmContact);
        assertNotNull(dto.getId());

        assertNotNull(dto.getArea());
        assertNotNull(dto.getSize());
        assertNotNull(dto.getPerson());
        assertNotNull(dto.getAccount());
        assertNotNull(dto.getPublicUrl());
        assertNotNull(dto.getAddres());
        assertNotNull(dto.getRange());
        assertNotNull(dto.getIndustry());
        assertNotNull(dto.getShortName());
        assertNotNull(dto.getName());
        assertNotNull(dto.getPaymentDelay());
        assertNotNull(dto.getVatNumber());
        assertNotNull(dto.getCreatedBy());
        assertNotNull(dto.getCreatedDate());
        assertNotNull(dto.getLastModifiedBy());
        assertNotNull(dto.getLastModifiedDate());
    }

    @Test
    public void DBs2ThinDTOs() throws Exception {
        List<CrmContactThinDTO> dtos = customCrmContactMapper.DBs2ThinDTOs(crmContacts);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getId());

            assertNotNull(dto.getArea());
            assertNotNull(dto.getSize());
            assertNotNull(dto.getPerson());
            assertNotNull(dto.getAccount());

            assertNotNull(dto.getPublicUrl());
            assertNotNull(dto.getAddres());
            assertNotNull(dto.getRange());
            assertNotNull(dto.getIndustry());
            assertNotNull(dto.getShortName());
            assertNotNull(dto.getName());
            assertNotNull(dto.getPaymentDelay());
            assertNotNull(dto.getVatNumber());
            assertNotNull(dto.getCreatedBy());
            assertNotNull(dto.getCreatedDate());
            assertNotNull(dto.getLastModifiedBy());
            assertNotNull(dto.getLastModifiedDate());
        });
    }

}
