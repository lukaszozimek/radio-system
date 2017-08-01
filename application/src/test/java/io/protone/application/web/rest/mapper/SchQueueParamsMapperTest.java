package io.protone.application.web.rest.mapper;

import io.protone.application.ProtoneApp;
import io.protone.scheduler.api.dto.SchQueueParamsDTO;
import io.protone.scheduler.domain.SchQueueParams;
import io.protone.scheduler.mapper.SchQueueParamsMapper;
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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class SchQueueParamsMapperTest {

    @Autowired
    private SchQueueParamsMapper queueParamsMapper;

    private SchQueueParams queueParam;

    private SchQueueParamsDTO queueParamDTO;

    private List<SchQueueParamsDTO> queueParamDTOS = new ArrayList<>();

    private List<SchQueueParams> queueParams = new ArrayList<>();

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        queueParam = factory.manufacturePojo(SchQueueParams.class);
        queueParams.add(queueParam);
        queueParamDTO = factory.manufacturePojo(SchQueueParamsDTO.class);
        queueParamDTOS.add(queueParamDTO);
    }

    @Test
    public void toDTO() throws Exception {
        SchQueueParamsDTO dto = queueParamsMapper.toDto(queueParam);

        assertNotNull(dto.getNextId());
        assertNotNull(dto.getNextType());
        assertNotNull(dto.getPreviousId());
        assertNotNull(dto.getPreviousType());
        assertNotNull(dto.getSeq());
    }

    @Test
    public void toDTOs() throws Exception {
        List<SchQueueParamsDTO> dtos = queueParamsMapper.toDto(queueParams);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getNextId());
            assertNotNull(dto.getNextType());
            assertNotNull(dto.getPreviousId());
            assertNotNull(dto.getPreviousType());
            assertNotNull(dto.getSeq());
        });
    }

    @Test
    public void toEntity() throws Exception {
        SchQueueParams entity = queueParamsMapper.toEntity(queueParamDTO);

        assertNotNull(entity.getNextId());
        assertNotNull(entity.getNextType());
        assertNotNull(entity.getPreviousId());
        assertNotNull(entity.getPreviousType());
        assertNotNull(entity.getSeq());
    }

    @Test
    public void toEntities() throws Exception {
        List<SchQueueParams> entities = queueParamsMapper.toEntity(queueParamDTOS);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getNextId());
            assertNotNull(entity.getNextType());
            assertNotNull(entity.getPreviousId());
            assertNotNull(entity.getPreviousType());
            assertNotNull(entity.getSeq());
        });
    }

}