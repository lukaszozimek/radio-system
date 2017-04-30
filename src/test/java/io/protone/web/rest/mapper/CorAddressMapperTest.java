package io.protone.web.rest.mapper;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.CoreAddressPT;
import io.protone.domain.CorAddress;
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

import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 27.04.2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CorAddressMapperTest {

    @Autowired
    private CorAddressMapper corAddressMapper;

    private CorAddress corAddress;

    private CoreAddressPT coreAddressPT;

    private List<CoreAddressPT> coreAddressPTS = new ArrayList<>();

    private List<CorAddress> corAddresses = new ArrayList<>();
    private CorNetwork corNetwork;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        corAddress = factory.manufacturePojo(CorAddress.class);
        corAddresses.add(corAddress);
        coreAddressPT = factory.manufacturePojo(CoreAddressPT.class);
        coreAddressPTS.add(coreAddressPT);

        corNetwork = factory.manufacturePojo(CorNetwork.class);
    }

    @Test
    public void DB2DTO() throws Exception {

        CoreAddressPT coreAddressPT = corAddressMapper.DB2DTO(corAddress);

        assertNotNull(coreAddressPT.getCity());
        assertNotNull(coreAddressPT.getCountry());
        assertNotNull(coreAddressPT.getId());
        assertNotNull(coreAddressPT.getNumber());
        assertNotNull(coreAddressPT.getPostalCode());
        assertNotNull(coreAddressPT.getStreet());
    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<CoreAddressPT> addressPTS = corAddressMapper.DBs2DTOs(corAddresses);

        assertNotNull(addressPTS);
        assertEquals(addressPTS.size(), 1);
        addressPTS.stream().forEach(dto -> {
            assertNotNull(coreAddressPT.getCity());
            assertNotNull(coreAddressPT.getCountry());
            assertNotNull(coreAddressPT.getId());
            assertNotNull(coreAddressPT.getNumber());
            assertNotNull(coreAddressPT.getPostalCode());
            assertNotNull(coreAddressPT.getStreet());

        });
    }

    @Test
    public void DTO2DB() throws Exception {
        CorAddress entity = corAddressMapper.DTO2DB(coreAddressPT, corNetwork);

        assertNotNull(entity.getId());
        assertNotNull(entity.getStreet());
        assertNotNull(entity.getNumber());
        assertNotNull(entity.getPostalCode());
        assertNotNull(entity.getCity());
        assertNotNull(entity.getCountry());

        assertNotNull(entity.getNetwork());

    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<CorAddress> entities = corAddressMapper.DTOs2DBs(coreAddressPTS, corNetwork);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getStreet());
            assertNotNull(entity.getNumber());
            assertNotNull(entity.getPostalCode());
            assertNotNull(entity.getCity());
            assertNotNull(entity.getCountry());

            assertNotNull(entity.getNetwork());
        });
    }

}
