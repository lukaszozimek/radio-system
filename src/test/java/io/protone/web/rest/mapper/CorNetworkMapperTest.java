package io.protone.web.rest.mapper;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.CoreNetworkPT;
import io.protone.domain.CorNetwork;
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
 * Created by lukaszozimek on 27/04/2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CorNetworkMapperTest {

    @Autowired
    private CorNetworkMapper corNetworkMapper;

    private CorNetwork corNetwork;

    private CoreNetworkPT coreNetworkPT;

    private List<CoreNetworkPT> coreNetworkPTS = new ArrayList<>();

    private List<CorNetwork> corNetworks = new ArrayList<>();

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corNetworks.add(corNetwork);
        coreNetworkPT = factory.manufacturePojo(CoreNetworkPT.class);
        coreNetworkPTS.add(coreNetworkPT);
    }


    @Test
    public void DTO2DB() throws Exception {
        CorNetwork entity = corNetworkMapper.DTO2DB(coreNetworkPT);

        assertNotNull(entity.getId());
        assertNotNull(entity.getShortcut());
        assertNotNull(entity.getName());
        assertNotNull(entity.getDescription());

    }

    @Test
    public void DB2DTO() throws Exception {
        CoreNetworkPT dto = corNetworkMapper.DB2DTO(corNetwork);

        assertNotNull(dto.getId());
        assertNotNull(dto.getShortcut());
        assertNotNull(dto.getName());
        assertNotNull(dto.getDescription());

    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<CoreNetworkPT> dtos = corNetworkMapper.DBs2DTOs(corNetworks);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getId());
            assertNotNull(dto.getShortcut());
            assertNotNull(dto.getName());
            assertNotNull(dto.getDescription());
        });
    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<CorNetwork> entities = corNetworkMapper.DTOs2DBs(coreNetworkPTS);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getShortcut());
            assertNotNull(entity.getName());
            assertNotNull(entity.getDescription());

        });
    }

}
