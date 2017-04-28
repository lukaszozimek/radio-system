package io.protone.custom.service.mapper;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.CrmAccountPT;
import io.protone.custom.service.dto.LibMarkerPT;
import io.protone.custom.service.dto.TraOrderPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.CrmAccount;
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
public class CustomCrmAccountMapperTest {
    @Autowired
    private CustomCrmAccountMapper customCrmAccountMapper;

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

        assertNotNull(dto.getId());
        assertNotNull(dto.getName());

    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<CrmAccountPT> dtos = customCrmAccountMapper.DBs2DTOs(crmAccounts);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {

            assertNotNull(dto.getId());
            assertNotNull(dto.getName());

        });
    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<CrmAccount> entities = customCrmAccountMapper.DTOs2DBs(crmAccountPTS, corNetwork);

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
        CrmAccount entity = customCrmAccountMapper.DTO2DB(crmAccountPT, corNetwork);


        assertNotNull(entity.getId());
        assertNotNull(entity.getName());

        assertNull(entity.getNetwork());
    }

    @Test
    public void traDB2DTO() throws Exception {
    }

    @Test
    public void traDBs2DTOs() throws Exception {
    }

    @Test
    public void traDTO2DB() throws Exception {
    }

    @Test
    public void traDTOs2DBs() throws Exception {
    }

}
