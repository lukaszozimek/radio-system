package io.protone.application.web.rest.mapper;


import io.protone.application.ProtoneApp;
import io.protone.core.api.dto.CoreAddressDTO;
import io.protone.core.domain.CorAddress;
import io.protone.core.domain.CorChannel;
import io.protone.core.mapper.CorAddressMapper;
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
 * Created by lukaszozimek on 27.04.2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CorAddressMapperTest {

    @Autowired
    private CorAddressMapper corAddressMapper;

    private CorAddress corAddress;

    private CoreAddressDTO coreAddressDTO;

    private List<CoreAddressDTO> coreAddressDTOS = new ArrayList<>();

    private List<CorAddress> corAddresses = new ArrayList<>();

    private CorChannel corChannel;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        corAddress = factory.manufacturePojo(CorAddress.class);
        corAddresses.add(corAddress);
        coreAddressDTO = factory.manufacturePojo(CoreAddressDTO.class);
        coreAddressDTOS.add(coreAddressDTO);

        corChannel = factory.manufacturePojo(CorChannel.class);
    }

    @Test
    public void DB2DTO() throws Exception {

        CoreAddressDTO coreAddressDTO = corAddressMapper.DB2DTO(corAddress);

        assertNotNull(coreAddressDTO.getCity());
        assertNotNull(coreAddressDTO.getCountry());
        assertNotNull(coreAddressDTO.getId());
        assertNotNull(coreAddressDTO.getNumber());
        assertNotNull(coreAddressDTO.getPostalCode());
        assertNotNull(coreAddressDTO.getStreet());
    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<CoreAddressDTO> addressPTS = corAddressMapper.DBs2DTOs(corAddresses);

        assertNotNull(addressPTS);
        assertEquals(addressPTS.size(), 1);
        addressPTS.stream().forEach(dto -> {
            assertNotNull(coreAddressDTO.getCity());
            assertNotNull(coreAddressDTO.getCountry());
            assertNotNull(coreAddressDTO.getId());
            assertNotNull(coreAddressDTO.getNumber());
            assertNotNull(coreAddressDTO.getPostalCode());
            assertNotNull(coreAddressDTO.getStreet());

        });
    }

    @Test
    public void DTO2DB() throws Exception {
        CorAddress entity = corAddressMapper.DTO2DB(coreAddressDTO, corChannel);

        assertNotNull(entity.getId());
        assertNotNull(entity.getStreet());
        assertNotNull(entity.getNumber());
        assertNotNull(entity.getPostalCode());
        assertNotNull(entity.getCity());
        assertNotNull(entity.getCountry());

        assertNotNull(entity.getChannel());

    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<CorAddress> entities = corAddressMapper.DTOs2DBs(coreAddressDTOS, corChannel);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getStreet());
            assertNotNull(entity.getNumber());
            assertNotNull(entity.getPostalCode());
            assertNotNull(entity.getCity());
            assertNotNull(entity.getCountry());

            assertNotNull(entity.getChannel());
        });
    }

}
