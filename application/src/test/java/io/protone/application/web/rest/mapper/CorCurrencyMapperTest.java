package io.protone.application.web.rest.mapper;


import io.protone.application.ProtoneApp;
import io.protone.core.api.dto.CorCurrencyDTO;
import io.protone.core.domain.CorCurrency;
import io.protone.core.domain.CorOrganization;
import io.protone.core.mapper.CorCurrencyMapper;
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
public class CorCurrencyMapperTest {

    @Autowired
    private CorCurrencyMapper corCurrencyMapper;

    private CorCurrency corCurrency;

    private CorCurrencyDTO corCurrencyDTO;

    private List<CorCurrencyDTO> corCurrencyDTOS = new ArrayList<>();

    private List<CorCurrency> corCurrencies = new ArrayList<>();

    private CorOrganization corOrganization;


    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        corCurrency = factory.manufacturePojo(CorCurrency.class);
        corCurrencies.add(corCurrency);
        corCurrencyDTO = factory.manufacturePojo(CorCurrencyDTO.class);
        corCurrencyDTOS.add(corCurrencyDTO);
        corOrganization = factory.manufacturePojo(CorOrganization.class);
    }

    @Test
    public void DTO2DB() throws Exception {
        CorCurrency entity = corCurrencyMapper.DTO2DB(corCurrencyDTO, corOrganization);

        assertNotNull(entity.getId());
        assertNotNull(entity.getName());
        assertNotNull(entity.getSymbol());
        assertNotNull(entity.getDelimiter());
        assertNotNull(entity.getShortName());
        assertNotNull(entity.getOrganization());
    }

    @Test
    public void DB2DTO() throws Exception {
        CorCurrencyDTO dto = corCurrencyMapper.DB2DTO(corCurrency);

        assertNotNull(dto.getId());
        assertNotNull(dto.getName());
        assertNotNull(dto.getSymbol());
        assertNotNull(dto.getDelimiter());
        assertNotNull(dto.getShortName());

    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<CorCurrencyDTO> dtos = corCurrencyMapper.DBs2DTOs(corCurrencies);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getId());
            assertNotNull(dto.getName());
            assertNotNull(dto.getSymbol());
            assertNotNull(dto.getDelimiter());
            assertNotNull(dto.getShortName());
        });
    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<CorCurrency> entities = corCurrencyMapper.DTOs2DBs(corCurrencyDTOS, corOrganization);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getName());
            assertNotNull(entity.getSymbol());
            assertNotNull(entity.getDelimiter());
            assertNotNull(entity.getShortName());
            assertNotNull(entity.getOrganization());

        });
    }

}
