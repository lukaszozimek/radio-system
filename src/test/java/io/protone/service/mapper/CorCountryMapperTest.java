package io.protone.service.mapper;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.ConfCountryPt;
import io.protone.domain.CorCountry;
import io.protone.service.mapper.CorCountryMapper;
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
 * Created by lukaszozimek on 27.04.2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CorCountryMapperTest {

    @Autowired
    private CorCountryMapper customCorCountryMapper;

    private CorCountry corCountry;

    private ConfCountryPt confCountryPt;

    private List<ConfCountryPt> confCountryPts = new ArrayList<>();

    private List<CorCountry> corCountries = new ArrayList<>();

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        corCountry = factory.manufacturePojo(CorCountry.class);
        corCountries.add(corCountry);
        confCountryPt = factory.manufacturePojo(ConfCountryPt.class);
        confCountryPts.add(confCountryPt);
    }

    @Test
    public void DTO2DB() throws Exception {
        CorCountry entity = customCorCountryMapper.DTO2DB(confCountryPt);

        assertNotNull(entity.getId());
        assertNotNull(entity.getName());
        assertNotNull(entity.getShortName());
        assertNotNull(entity.getCurrnecy());

        assertNull(entity.getNetwork());
    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<CorCountry> entities = customCorCountryMapper.DTOs2DBs(confCountryPts);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getName());
            assertNotNull(entity.getShortName());
            assertNotNull(entity.getCurrnecy());

            assertNull(entity.getNetwork());

        });
    }

    @Test
    public void DB2DTO() throws Exception {
        ConfCountryPt dto = customCorCountryMapper.DB2DTO(corCountry);

        assertNotNull(dto.getId());
        assertNotNull(dto.getName());
        assertNotNull(dto.getShortName());


    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<ConfCountryPt> dtos = customCorCountryMapper.DBs2DTOs(corCountries);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getId());
            assertNotNull(dto.getName());
            assertNotNull(dto.getShortName());

        });
    }


}
