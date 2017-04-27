package io.protone.custom.service.mapper;

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
        CorAddress corAddress = customCorAddressMapper.DTO2DB(coreAddressPT);

        assertNotNull(corAddress.getId());
        assertNotNull(corAddress.getStreet());
        assertNotNull(corAddress.getNumber());
        assertNotNull(corAddress.getPostalCode());
        assertNotNull(corAddress.getCity());
        assertNotNull(corAddress.getCountry());

        assertNull(corAddress.getNetwork());

    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<CorAddress> addresses = customCorAddressMapper.DTOs2DBs(coreAddressPTS);

        assertNotNull(addresses);
        assertEquals(addresses.size(), 1);
        addresses.stream().forEach(dto -> {
            assertNotNull(dto.getId());
            assertNotNull(dto.getStreet());
            assertNotNull(dto.getNumber());
            assertNotNull(dto.getPostalCode());
            assertNotNull(dto.getCity());
            assertNotNull(dto.getCountry());

            assertNull(dto.getNetwork());
        });
    }

}
