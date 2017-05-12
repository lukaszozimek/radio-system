package io.protone.web.rest.mapper;

import io.protone.ProtoneApp;
import io.protone.web.rest.dto.traffic.TraDiscountDTO;
import io.protone.domain.CorNetwork;
import io.protone.domain.TraDiscount;
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
public class TraDiscountMapperTest {

    @Autowired
    private TraDiscountMapper customTraDiscountMapper;

    private TraDiscount traDiscount;

    private TraDiscountDTO traDiscountDTO;

    private List<TraDiscountDTO> traDiscountDTOS = new ArrayList<>();

    private List<TraDiscount> traDiscounts = new ArrayList<>();

    private CorNetwork corNetwork;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        traDiscount = factory.manufacturePojo(TraDiscount.class);
        traDiscounts.add(traDiscount);
        traDiscountDTO = factory.manufacturePojo(TraDiscountDTO.class);
        traDiscountDTOS.add(traDiscountDTO);
        corNetwork = factory.manufacturePojo(CorNetwork.class);
    }


    @Test
    public void DB2DTO() throws Exception {
        TraDiscountDTO dto = customTraDiscountMapper.DB2DTO(traDiscount);

        assertNotNull(dto.getId());
        assertNotNull(dto.getValidFrom());
        assertNotNull(dto.getValidTo());
        assertNotNull(dto.getDiscount());
        assertNotNull(dto.getId());


    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<TraDiscountDTO> dtos = customTraDiscountMapper.DBs2DTOs(traDiscounts);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getId());
            assertNotNull(dto.getValidFrom());
            assertNotNull(dto.getValidTo());
            assertNotNull(dto.getDiscount());
            assertNotNull(dto.getId());
        });
    }


    @Test
    public void DTO2DB() throws Exception {
        TraDiscount entity = customTraDiscountMapper.DTO2DB(traDiscountDTO,corNetwork);
        assertNotNull(entity.getId());
        assertNotNull(entity.getValidFrom());
        assertNotNull(entity.getValidTo());
        assertNotNull(entity.getDiscount());
        assertNotNull(entity.getId());
        assertNotNull(entity.getNetwork());
    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<TraDiscount> entities = customTraDiscountMapper.DTOs2DBs(traDiscountDTOS,corNetwork);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getValidFrom());
            assertNotNull(entity.getValidTo());
            assertNotNull(entity.getDiscount());
            assertNotNull(entity.getId());
            assertNotNull(entity.getNetwork());

        });
    }


}
