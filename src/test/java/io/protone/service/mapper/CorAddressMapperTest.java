package io.protone.service.mapper;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.CoreAddressPT;
import io.protone.domain.CorAddress;
import io.protone.service.mapper.CorAddressMapper;
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
    private CorAddressMapper customCorAddressMapper;

    private CorAddress corAddress;

    private CoreAddressPT coreAddressPT;

    private List<CoreAddressPT> coreAddressPTS = new ArrayList<>();

    private List<CorAddress> corAddresses = new ArrayList<>();

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        corAddress = factory.manufacturePojo(CorAddress.class);
        corAddresses.add(corAddress);
        coreAddressPT = factory.manufacturePojo(CoreAddressPT.class);
        coreAddressPTS.add(coreAddressPT);

    }

    @Test
    public void DB2DTO() throws Exception {

        CoreAddressPT coreAddressPT = customCorAddressMapper.DB2DTO(corAddress);

        assertNotNull(coreAddressPT.getCity());
        assertNotNull(coreAddressPT.getCountry());
        assertNotNull(coreAddressPT.getId());
        assertNotNull(coreAddressPT.getNumber());
        assertNotNull(coreAddressPT.getPostalCode());
        assertNotNull(coreAddressPT.getStreet());
    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<CoreAddressPT> addressPTS = customCorAddressMapper.DBs2DTOs(corAddresses);

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
        CorAddress entity = customCorAddressMapper.DTO2DB(coreAddressPT);

        assertNotNull(entity.getId());
        assertNotNull(entity.getStreet());
        assertNotNull(entity.getNumber());
        assertNotNull(entity.getPostalCode());
        assertNotNull(entity.getCity());
        assertNotNull(entity.getCountry());

        assertNull(entity.getNetwork());

    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<CorAddress> entities = customCorAddressMapper.DTOs2DBs(coreAddressPTS);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getStreet());
            assertNotNull(entity.getNumber());
            assertNotNull(entity.getPostalCode());
            assertNotNull(entity.getCity());
            assertNotNull(entity.getCountry());

            assertNull(entity.getNetwork());
        });
    }

}
