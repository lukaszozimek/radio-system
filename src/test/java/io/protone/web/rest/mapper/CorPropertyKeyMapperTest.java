package io.protone.web.rest.mapper;

import io.protone.ProtoneApp;
import io.protone.web.rest.dto.cor.CorKeyDTO;
import io.protone.domain.CorNetwork;
import io.protone.domain.CorPropertyKey;
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
public class CorPropertyKeyMapperTest {

    @Autowired
    private CorPropertyKeyMapper corPropertyKeyMapper;

    private CorPropertyKey corPropertyKey;

    private CorKeyDTO corKeyDTO;

    private List<CorKeyDTO> corKeyDTOS = new ArrayList<>();

    private List<CorPropertyKey> corPropertyKeys = new ArrayList<>();
    private CorNetwork corNetwork;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        corPropertyKey = factory.manufacturePojo(CorPropertyKey.class);
        corPropertyKeys.add(corPropertyKey);
        corKeyDTO = factory.manufacturePojo(CorKeyDTO.class);
        corKeyDTOS.add(corKeyDTO);
        corNetwork = factory.manufacturePojo(CorNetwork.class);
    }


    @Test
    public void DTO2DB() throws Exception {
        CorPropertyKey entity = corPropertyKeyMapper.DTO2DB(corKeyDTO, corNetwork);

        assertNotNull(entity.getId());
        assertNotNull(entity.getKey());
        assertNotNull(entity.getNetwork());
    }

    @Test
    public void DB2DTO() throws Exception {
        CorKeyDTO dto = corPropertyKeyMapper.DB2DTO(corPropertyKey);

        assertNotNull(dto.getId());
        assertNotNull(dto.getKey());
    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<CorKeyDTO> dtos = corPropertyKeyMapper.DBs2DTOs(corPropertyKeys);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getId());
            assertNotNull(dto.getKey());
        });
    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<CorPropertyKey> entities = corPropertyKeyMapper.DTOs2DBs(corKeyDTOS, corNetwork);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getKey());

            assertNotNull(entity.getNetwork());

        });
    }
}
