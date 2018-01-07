package io.protone.application.web.rest.mapper;


import io.protone.application.ProtoneApp;
import io.protone.core.api.dto.CorCountryDTO;
import io.protone.core.domain.CorCountry;
import io.protone.core.domain.CorOrganization;
import io.protone.core.mapper.CorCountryMapper;
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
public class CorCountryMapperTest {

    @Autowired
    private CorCountryMapper corCountryMapper;

    private CorCountry corCountry;

    private CorCountryDTO corCountryDTO;

    private List<CorCountryDTO> corCountryDTOS = new ArrayList<>();

    private List<CorCountry> corCountries = new ArrayList<>();

    private CorOrganization corOrganization;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        corCountry = factory.manufacturePojo(CorCountry.class);
        corCountries.add(corCountry);
        corCountryDTO = factory.manufacturePojo(CorCountryDTO.class);
        corCountryDTOS.add(corCountryDTO);

        corOrganization = factory.manufacturePojo(CorOrganization.class);
    }

    @Test
    public void DTO2DB() throws Exception {
        CorCountry entity = corCountryMapper.DTO2DB(corCountryDTO, corOrganization);

        assertNotNull(entity.getId());
        assertNotNull(entity.getName());
        assertNotNull(entity.getShortName());
        assertNotNull(entity.getOrganization());
    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<CorCountry> entities = corCountryMapper.DTOs2DBs(corCountryDTOS, corOrganization);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getName());
            assertNotNull(entity.getShortName());
            assertNotNull(entity.getOrganization());

        });
    }

    @Test
    public void DB2DTO() throws Exception {
        CorCountryDTO dto = corCountryMapper.DB2DTO(corCountry);

        assertNotNull(dto.getId());
        assertNotNull(dto.getName());
        assertNotNull(dto.getShortName());


    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<CorCountryDTO> dtos = corCountryMapper.DBs2DTOs(corCountries);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getId());
            assertNotNull(dto.getName());
            assertNotNull(dto.getShortName());

        });
    }


}
