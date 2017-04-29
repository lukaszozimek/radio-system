package io.protone.web.rest.mapper;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.ConfCurrencyPT;
import io.protone.domain.CorCurrency;
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
 * Created by lukaszozimek on 27/04/2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CorCurrencyMapperTest {

    @Autowired
    private CorCurrencyMapper corCurrencyMapper;

    private CorCurrency corCurrency;

    private ConfCurrencyPT confCurrencyPT;

    private List<ConfCurrencyPT> confCurrencyPTS = new ArrayList<>();

    private List<CorCurrency> corCurrencies = new ArrayList<>();

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        corCurrency = factory.manufacturePojo(CorCurrency.class);
        corCurrencies.add(corCurrency);
        confCurrencyPT = factory.manufacturePojo(ConfCurrencyPT.class);
        confCurrencyPTS.add(confCurrencyPT);
    }

    @Test
    public void DTO2DB() throws Exception {
        CorCurrency entity = corCurrencyMapper.DTO2DB(confCurrencyPT);


        assertNotNull(entity.getId());
        assertNotNull(entity.getName());
        assertNotNull(entity.getSymbol());
        assertNotNull(entity.getDelimiter());
        assertNotNull(entity.getShortName());


        assertNull(entity.getNetwork());
    }

    @Test
    public void DB2DTO() throws Exception {
        ConfCurrencyPT dto = corCurrencyMapper.DB2DTO(corCurrency);

        assertNotNull(dto.getId());
        assertNotNull(dto.getName());
        assertNotNull(dto.getSymbol());
        assertNotNull(dto.getDelimiter());
        assertNotNull(dto.getShortName());

    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<ConfCurrencyPT> dtos = corCurrencyMapper.DBs2DTOs(corCurrencies);

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
        List<CorCurrency> entities = corCurrencyMapper.DTOs2DBs(confCurrencyPTS);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getName());
            assertNotNull(entity.getSymbol());
            assertNotNull(entity.getDelimiter());
            assertNotNull(entity.getShortName());


            assertNull(entity.getNetwork());

        });
    }

}
