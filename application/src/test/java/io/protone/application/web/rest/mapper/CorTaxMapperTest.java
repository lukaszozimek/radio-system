package io.protone.application.web.rest.mapper;


import io.protone.application.ProtoneApp;
import io.protone.core.api.dto.CorTaxDTO;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorTax;
import io.protone.core.mapper.CorTaxMapper;
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
 * Created by lukaszozimek on 27/04/2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CorTaxMapperTest {

    @Autowired
    private CorTaxMapper corTaxMapper;

    private CorTax corTax;

    private CorTaxDTO corTaxDTO;

    private List<CorTaxDTO> corTaxDTOS = new ArrayList<>();

    private List<CorTax> corTaxes = new ArrayList<>();

    private CorNetwork corNetwork;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        corTax = factory.manufacturePojo(CorTax.class);
        corTaxes.add(corTax);
        corTaxDTO = factory.manufacturePojo(CorTaxDTO.class);
        corTaxDTOS.add(corTaxDTO);
        corNetwork = factory.manufacturePojo(CorNetwork.class);
    }


    @Test
    public void DTO2DB() throws Exception {
        CorTax entity = corTaxMapper.DTO2DB(corTaxDTO, corNetwork);


        assertNotNull(entity.getId());
        assertNotNull(entity.getValidFrom());
        assertNotNull(entity.getValidTo());
        assertNotNull(entity.getName());
        assertNotNull(entity.getValue());
        assertNotNull(entity.isActive());

        assertNotNull(entity.getNetwork());
    }

    @Test
    public void DB2DTO() throws Exception {
        CorTaxDTO dto = corTaxMapper.DB2DTO(corTax);

        assertNotNull(dto.getId());
        assertNotNull(dto.getName());
        assertNotNull(dto.getValue());
        assertNotNull(dto.getValidFrom());
        assertNotNull(dto.getValidTo());
        assertNotNull(dto.getActive());
    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<CorTaxDTO> dtos = corTaxMapper.DBs2DTOs(corTaxes);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getId());
            assertNotNull(dto.getName());
            assertNotNull(dto.getValue());
            assertNotNull(dto.getValidFrom());
            assertNotNull(dto.getValidTo());
            assertNotNull(dto.getActive());
        });
    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<CorTax> entities = corTaxMapper.DTOs2DBs(corTaxDTOS, corNetwork);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getValidFrom());
            assertNotNull(entity.getValidTo());
            assertNotNull(entity.getName());
            assertNotNull(entity.getValue());
            assertNotNull(entity.isActive());

            assertNotNull(entity.getNetwork());


        });
    }
}
