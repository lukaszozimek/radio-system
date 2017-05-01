package io.protone.web.rest.mapper;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.ConfTaxPT;
import io.protone.domain.CorNetwork;
import io.protone.domain.CorTax;
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
 * Created by lukaszozimek on 27/04/2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CorTaxMapperTest {

    @Autowired
    private CorTaxMapper corTaxMapper;

    private CorTax corTax;

    private ConfTaxPT confTaxPT;

    private List<ConfTaxPT> confTaxPTS = new ArrayList<>();

    private List<CorTax> corTaxes = new ArrayList<>();

    private CorNetwork corNetwork;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        corTax = factory.manufacturePojo(CorTax.class);
        corTaxes.add(corTax);
        confTaxPT = factory.manufacturePojo(ConfTaxPT.class);
        confTaxPTS.add(confTaxPT);
        corNetwork = factory.manufacturePojo(CorNetwork.class);
    }


    @Test
    public void DTO2DB() throws Exception {
        CorTax entity = corTaxMapper.DTO2DB(confTaxPT, corNetwork);


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
        ConfTaxPT dto = corTaxMapper.DB2DTO(corTax);

        assertNotNull(dto.getId());
        assertNotNull(dto.getName());
        assertNotNull(dto.getValue());
        assertNotNull(dto.getValidFrom());
        assertNotNull(dto.getValidTo());
        assertNotNull(dto.getActive());
    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<ConfTaxPT> dtos = corTaxMapper.DBs2DTOs(corTaxes);

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
        List<CorTax> entities = corTaxMapper.DTOs2DBs(confTaxPTS, corNetwork);

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
