package io.protone.application.web.rest.mapper;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorNetwork;
import io.protone.traffic.api.dto.TraCompanyDTO;
import io.protone.traffic.domain.TraCompany;
import io.protone.traffic.mapper.TraCompanyMapper;
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
 * Created by lukaszozimek on 11/08/2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TraCompanyMapperTest {
    @Autowired
    private TraCompanyMapper customTraDiscountMapper;

    private TraCompany traCompany;

    private TraCompanyDTO traCompanyDTO;

    private List<TraCompanyDTO> traCompanyDTOS = new ArrayList<>();

    private List<TraCompany> traCompanies = new ArrayList<>();

    private CorNetwork corNetwork;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        traCompany = factory.manufacturePojo(TraCompany.class);
        traCompany.setId(1L);
        traCompanies.add(traCompany);
        traCompanyDTO = factory.manufacturePojo(TraCompanyDTO.class);
        traCompanyDTOS.add(traCompanyDTO);
        corNetwork = factory.manufacturePojo(CorNetwork.class);
    }

    @Test
    public void DB2DTO() throws Exception {
        TraCompanyDTO dto = customTraDiscountMapper.DB2DTO(traCompany);


        assertNotNull(dto.getId());
        assertNotNull(dto.getName());
        assertNotNull(dto.getDescription());
        assertNotNull(dto.getTaxId1());
        assertNotNull(dto.getTaxId2());

    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<TraCompanyDTO> dtos = customTraDiscountMapper.DBs2DTOs(traCompanies);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getId());
            assertNotNull(dto.getName());
            assertNotNull(dto.getDescription());
            assertNotNull(dto.getTaxId1());
            assertNotNull(dto.getTaxId2());

        });
    }


    @Test
    public void DTO2DB() throws Exception {
        TraCompany entity = customTraDiscountMapper.DTO2DB(traCompanyDTO, corNetwork);
        assertNotNull(entity.getId());
        assertNotNull(entity.getName());
        assertNotNull(entity.getDescription());
        assertNotNull(entity.getTaxId1());
        assertNotNull(entity.getTaxId2());
        assertNotNull(entity.getNetwork());
    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<TraCompany> entities = customTraDiscountMapper.DTOs2DBs(traCompanyDTOS, corNetwork);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getName());
            assertNotNull(entity.getDescription());
            assertNotNull(entity.getTaxId1());
            assertNotNull(entity.getTaxId2());
            assertNotNull(entity.getNetwork());

        });
    }

}