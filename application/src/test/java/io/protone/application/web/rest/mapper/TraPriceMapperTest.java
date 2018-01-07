package io.protone.application.web.rest.mapper;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.traffic.api.dto.TraPriceDTO;
import io.protone.traffic.domain.TraPrice;
import io.protone.traffic.mapper.TraPriceMapper;
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
 * Created by lukaszozimek on 12.08.2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TraPriceMapperTest {
    @Autowired
    private TraPriceMapper traPriceMapper;

    private TraPrice price;

    private TraPriceDTO traPriceDTO;

    private List<TraPriceDTO> traPriceDTOS = new ArrayList<>();

    private List<TraPrice> traPrices = new ArrayList<>();
    private CorChannel corChannel;


    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        price = factory.manufacturePojo(TraPrice.class);
        traPrices.add(price);
        traPriceDTO = factory.manufacturePojo(TraPriceDTO.class);
        traPriceDTOS.add(traPriceDTO);
        corChannel = factory.manufacturePojo(CorChannel.class);
    }

    @Test
    public void DB2DTO() throws Exception {
        TraPriceDTO dto = traPriceMapper.DB2DTO(price);
        assertNotNull(dto.getId());
        assertNotNull(dto.getName());
        assertNotNull(dto.getValidFrom());
        assertNotNull(dto.getValidTo());
        assertNotNull(dto.getBasePrice());
        assertNotNull(dto.getLenghtMultiplier());
        assertNotNull(dto.getBaseLength());
        assertNotNull(dto.getPriceLastPostion());
        assertNotNull(dto.getPriceFirstPostion());

    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<TraPriceDTO> dtos = traPriceMapper.DBs2DTOs(traPrices);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getId());
            assertNotNull(dto.getName());
            assertNotNull(dto.getValidFrom());
            assertNotNull(dto.getValidTo());
            assertNotNull(dto.getBasePrice());
            assertNotNull(dto.getLenghtMultiplier());
            assertNotNull(dto.getBaseLength());
            assertNotNull(dto.getPriceLastPostion());
            assertNotNull(dto.getPriceFirstPostion());

        });

    }

    @Test
    public void DTO2DB() throws Exception {
        TraPrice entity = traPriceMapper.DTO2DB(traPriceDTO, corChannel);

        assertNotNull(entity.getId());
        assertNotNull(entity.getName());
        assertNotNull(entity.getValidFrom());
        assertNotNull(entity.getValidTo());
        assertNotNull(entity.getBasePrice());
        assertNotNull(entity.getLenghtMultiplier());
        assertNotNull(entity.getBaseLength());
        assertNotNull(entity.getPriceLastPostion());
        assertNotNull(entity.getPriceFirstPostion());


    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<TraPrice> entities = traPriceMapper.DTOs2DBs(traPriceDTOS, corChannel);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getName());
            assertNotNull(entity.getValidFrom());
            assertNotNull(entity.getValidTo());
            assertNotNull(entity.getBasePrice());
            assertNotNull(entity.getLenghtMultiplier());
            assertNotNull(entity.getBaseLength());
            assertNotNull(entity.getPriceLastPostion());
            assertNotNull(entity.getPriceFirstPostion());
        });

    }

}