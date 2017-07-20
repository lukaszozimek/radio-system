package io.protone.application.web.rest.mapper;

import io.protone.application.ProtoneApp;
import io.protone.core.api.dto.CorFilterDTO;
import io.protone.core.domain.CorFilter;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorUser;
import io.protone.core.mapper.CorFilterMapper;
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
 * Created by lukaszozimek on 20.07.2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class CorFilterMapperTest {

    @Autowired
    private CorFilterMapper corFilterMapper;

    private CorFilter corFilter;

    private CorFilterDTO corFilterDTO;

    private List<CorFilterDTO> corFilterDTOS = new ArrayList<>();

    private List<CorFilter> corFilters = new ArrayList<>();

    private CorNetwork corNetwork;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        corFilter = factory.manufacturePojo(CorFilter.class);
        corFilter.user(factory.manufacturePojo(CorUser.class));
        corFilters.add(corFilter);
        corFilterDTO = factory.manufacturePojo(CorFilterDTO.class);
        corFilterDTOS.add(corFilterDTO);
        corNetwork = factory.manufacturePojo(CorNetwork.class);
    }

    @Test
    public void DTO2DB() throws Exception {
        CorFilter entity = corFilterMapper.DTO2DB(corFilterDTO, corNetwork);

        assertNotNull(entity.getId());
        assertNotNull(entity.getName());
        assertNotNull(entity.getValue());
        assertNotNull(entity.getType());
        assertNotNull(entity.getNetwork());
    }

    @Test
    public void DB2DTO() throws Exception {
        CorFilterDTO dto = corFilterMapper.DB2DTO(corFilter);

        assertNotNull(dto.getId());
        assertNotNull(dto.getName());
        assertNotNull(dto.getType());
        assertNotNull(dto.getValue());

    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<CorFilterDTO> dtos = corFilterMapper.DBs2DTOs(corFilters);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getId());
            assertNotNull(dto.getName());
            assertNotNull(dto.getType());
            assertNotNull(dto.getValue());
        });
    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<CorFilter> entities = corFilterMapper.DTOs2DBs(corFilterDTOS, corNetwork);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {

            assertNotNull(entity.getId());
            assertNotNull(entity.getName());
            assertNotNull(entity.getValue());
            assertNotNull(entity.getType());
            assertNotNull(entity.getNetwork());

        });
    }


}