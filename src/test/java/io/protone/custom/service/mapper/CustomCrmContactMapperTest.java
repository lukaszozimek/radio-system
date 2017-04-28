package io.protone.custom.service.mapper;

import io.protone.custom.service.dto.CrmContactPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmContact;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by lukaszozimek on 28.04.2017.
 */
public class CustomCrmContactMapperTest {
    @Autowired
    private CustomCrmContactMapper customCrmContactMapper;

    private CrmContact crmContact;

    private CrmContactPT crmContactPT;

    private CorNetwork corNetwork;

    private List<CrmContactPT> crmContactPTS = new ArrayList<>();

    private List<CrmContact> crmContacts = new ArrayList<>();

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        crmContact = factory.manufacturePojo(CrmContact.class);
        crmContacts.add(crmContact);
        crmContactPT = factory.manufacturePojo(CrmContactPT.class);
        crmContactPTS.add(crmContactPT);
        corNetwork = factory.manufacturePojo(CorNetwork.class);
    }

    @Test
    public void DB2DTO() throws Exception {
        CrmContactPT dto = customCrmContactMapper.DB2DTO(crmContact);

        assertNotNull(dto.getId());
        assertNotNull(dto.getName());

    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<CrmContactPT> dtos = customCrmContactMapper.DBs2DTOs(crmContacts);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {

            assertNotNull(dto.getId());
            assertNotNull(dto.getName());

        });
    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<CrmContact> entities = customCrmContactMapper.DTOs2DBs(crmContactPTS, corNetwork);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getName());

            assertNotNull(entity.getNetwork());


        });
    }

    @Test
    public void DTO2DB() throws Exception {
        CrmContact entity = customCrmContactMapper.DTO2DB(crmContactPT, corNetwork);

        assertNotNull(entity.getId());
        assertNotNull(entity.getName());
        assertNotNull(entity.getNetwork());
    }

}
