package io.protone.application.web.rest.mapper;


import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorNetwork;
import io.protone.crm.domain.CrmDiscount;
import io.protone.crm.api.dto.CrmDiscountDTO;
import io.protone.crm.mapper.CrmDiscountMapper;
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
 * Created by lukaszozimek on 28.04.2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CrmDiscountMapperTest {

    @Autowired
    private CrmDiscountMapper customCrmDiscountMapper;

    private CrmDiscount traDiscount;

    private CrmDiscountDTO crmDiscountDTO;

    private List<CrmDiscountDTO> crmDiscountDTOS = new ArrayList<>();

    private List<CrmDiscount> traDiscounts = new ArrayList<>();

    private CorNetwork corNetwork;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        traDiscount = factory.manufacturePojo(CrmDiscount.class);
        traDiscounts.add(traDiscount);
        crmDiscountDTO = factory.manufacturePojo(CrmDiscountDTO.class);
        crmDiscountDTOS.add(crmDiscountDTO);
        corNetwork = factory.manufacturePojo(CorNetwork.class);
    }


    @Test
    public void DB2DTO() throws Exception {
        CrmDiscountDTO dto = customCrmDiscountMapper.DB2DTO(traDiscount);

        assertNotNull(dto.getId());
        assertNotNull(dto.getValidFrom());
        assertNotNull(dto.getValidTo());
        assertNotNull(dto.getDiscount());
        assertNotNull(dto.getId());


    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<CrmDiscountDTO> dtos = customCrmDiscountMapper.DBs2DTOs(traDiscounts);

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
        CrmDiscount entity = customCrmDiscountMapper.DTO2DB(crmDiscountDTO, corNetwork);
        assertNotNull(entity.getId());
        assertNotNull(entity.getValidFrom());
        assertNotNull(entity.getValidTo());
        assertNotNull(entity.getDiscount());
        assertNotNull(entity.getId());
        assertNotNull(entity.getNetwork());
    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<CrmDiscount> entities = customCrmDiscountMapper.DTOs2DBs(crmDiscountDTOS, corNetwork);

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
