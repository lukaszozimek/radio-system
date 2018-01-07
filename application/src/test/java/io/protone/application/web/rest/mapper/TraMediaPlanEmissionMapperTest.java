package io.protone.application.web.rest.mapper;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.traffic.api.dto.TraMediaPlanEmissionDTO;
import io.protone.traffic.domain.TraMediaPlanBlock;
import io.protone.traffic.domain.TraMediaPlanEmission;
import io.protone.traffic.domain.TraMediaPlanPlaylistDate;
import io.protone.traffic.mapper.TraMediaPlanEmissionMapper;
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
 * Created by lukaszozimek on 16/08/2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TraMediaPlanEmissionMapperTest {
    @Autowired
    private TraMediaPlanEmissionMapper traMediaPlanEmissionMapper;

    private TraMediaPlanEmission traMediaPlanEmission;

    private TraMediaPlanEmissionDTO traMediaPlanEmissionDTO;

    private List<TraMediaPlanEmission> traMediaPlanEmissions = new ArrayList<>();

    private List<TraMediaPlanEmissionDTO> traMediaPlanEmissionDTOS = new ArrayList<>();

    private CorChannel corChannel;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();

        corChannel = factory.manufacturePojo(CorChannel.class);
        traMediaPlanEmissionDTO = factory.manufacturePojo(TraMediaPlanEmissionDTO.class);
        traMediaPlanEmissionDTOS.add(traMediaPlanEmissionDTO);
        traMediaPlanEmission = factory.manufacturePojo(TraMediaPlanEmission.class);
        traMediaPlanEmission.setId(1L);
        traMediaPlanEmission.setMediaPlanPlaylistDate(factory.manufacturePojo(TraMediaPlanPlaylistDate.class));
        traMediaPlanEmission.setMediaPlanBlock(factory.manufacturePojo(TraMediaPlanBlock.class));
        traMediaPlanEmission.setChannel(corChannel);
        traMediaPlanEmissions.add(traMediaPlanEmission);
    }

    @Test
    public void DB2DTO() throws Exception {
        TraMediaPlanEmissionDTO dto = traMediaPlanEmissionMapper.DB2DTO(traMediaPlanEmission);

        assertNotNull(dto.getId());
        assertNotNull(dto.getMediaPlanBlock());
        assertNotNull(dto.getMediaPlanPlaylistDate());
        assertNotNull(dto.getSequence());

    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<TraMediaPlanEmissionDTO> dtos = traMediaPlanEmissionMapper.DBs2DTOs(traMediaPlanEmissions);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getId());
            assertNotNull(dto.getMediaPlanBlock());
            assertNotNull(dto.getMediaPlanPlaylistDate());
            assertNotNull(dto.getSequence());
        });
    }


    @Test
    public void DTO2DB() throws Exception {
        TraMediaPlanEmission entity = traMediaPlanEmissionMapper.DTO2DB(traMediaPlanEmissionDTO, corChannel);
        assertNotNull(entity.getId());
        assertNotNull(entity.getChannel());
        assertNotNull(entity.getSequence());
    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<TraMediaPlanEmission> entities = traMediaPlanEmissionMapper.DTOs2DBs(traMediaPlanEmissionDTOS, corChannel);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getSequence());
            assertNotNull(entity.getChannel());

        });
    }
}