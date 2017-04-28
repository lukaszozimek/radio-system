package io.protone.custom.service.mapper;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.CrmLeadPT;
import io.protone.custom.service.dto.LibMarkerPT;
import io.protone.custom.service.dto.TraOrderPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmLead;
import io.protone.domain.LibMarker;
import io.protone.domain.TraOrder;
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
public class CustomCrmLeadMapperTest {
    @Autowired
    private CustomCrmLeadMapper customCrmLeadMapper;

    private CrmLead crmLead;

    private CrmLeadPT crmLeadPT;

    private List<CrmLeadPT> crmLeadPTS = new ArrayList<>();

    private List<CrmLead> crmLeads = new ArrayList<>();

    private CorNetwork corNetwork;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        crmLead = factory.manufacturePojo(CrmLead.class);
        crmLeads.add(crmLead);
        crmLeadPT = factory.manufacturePojo(CrmLeadPT.class);
        crmLeadPTS.add(crmLeadPT);
        corNetwork = factory.manufacturePojo(CorNetwork.class);

    }

    @Test
    public void DB2DTO() throws Exception {
        CrmLeadPT dto = customCrmLeadMapper.DB2DTO(crmLead);

        assertNotNull(dto.getId());
        assertNotNull(dto.getName());

    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<CrmLeadPT> dtos = customCrmLeadMapper.DBs2DTOs(crmLeads);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {

            assertNotNull(dto.getId());
            assertNotNull(dto.getName());

        });
    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<CrmLead> entities = customCrmLeadMapper.DTOs2DBs(crmLeadPTS, corNetwork);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getName());

            assertNull(entity.getNetwork());


        });
    }

    @Test
    public void DTO2DB() throws Exception {
        CrmLead entity = customCrmLeadMapper.DTO2DB(crmLeadPT, corNetwork);


        assertNotNull(entity.getId());
        assertNotNull(entity.getName());

        assertNull(entity.getNetwork());
    }

}
