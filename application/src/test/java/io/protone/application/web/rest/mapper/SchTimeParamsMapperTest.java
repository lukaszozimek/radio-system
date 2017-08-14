package io.protone.application.web.rest.mapper;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.scheduler.api.dto.SchTimeParamsDTO;
import io.protone.scheduler.domain.SchTimeParams;
import io.protone.scheduler.mapper.SchTimeParamsMapper;
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

@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class SchTimeParamsMapperTest {

    @Autowired
    private SchTimeParamsMapper timeParamsMapper;

    private SchTimeParams timeParam;

    private SchTimeParamsDTO timeParamDTO;

    private List<SchTimeParamsDTO> timeParamDTOS = new ArrayList<>();

    private List<SchTimeParams> timeParams = new ArrayList<>();
    private CorNetwork network;
    private CorChannel corChannel;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        corChannel = factory.manufacturePojo(CorChannel.class);
        network = factory.manufacturePojo(CorNetwork.class);
        // Fill entity instance
        timeParam = factory.manufacturePojo(SchTimeParams.class);
        timeParams.add(timeParam);
        timeParamDTO = factory.manufacturePojo(SchTimeParamsDTO.class);
        timeParamDTOS.add(timeParamDTO);
    }

    @Test
    public void toDTO() throws Exception {
        SchTimeParamsDTO dto = timeParamsMapper.toDto(timeParam);

        assertNotNull(dto.getStartTime());
        assertNotNull(dto.getEndTime());
        assertNotNull(dto.getRelativeDelay());
    }

    @Test
    public void toDTOs() throws Exception {
        List<SchTimeParamsDTO> dtos = timeParamsMapper.toDto(timeParams);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getStartTime());
            assertNotNull(dto.getEndTime());
            assertNotNull(dto.getRelativeDelay());
        });
    }

    @Test
    public void toEntity() throws Exception {
        SchTimeParams entity = timeParamsMapper.toEntity(timeParamDTO, network, corChannel);

        assertNotNull(entity.getStartTime());
        assertNotNull(entity.getEndTime());
        assertNotNull(entity.getRelativeDelay());
    }

    @Test
    public void toEntities() throws Exception {
        List<SchTimeParams> entities = timeParamsMapper.toEntity(timeParamDTOS, network, corChannel);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getStartTime());
            assertNotNull(entity.getEndTime());
            assertNotNull(entity.getRelativeDelay());

            assertNotNull(entity.getNetwork());
            assertNotNull(entity.getChannel());
        });
    }

}