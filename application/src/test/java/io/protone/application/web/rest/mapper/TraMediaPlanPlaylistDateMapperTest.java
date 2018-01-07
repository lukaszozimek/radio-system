package io.protone.application.web.rest.mapper;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.traffic.api.dto.TraMediaPlanPlaylistDateDTO;
import io.protone.traffic.domain.TraMediaPlanPlaylistDate;
import io.protone.traffic.mapper.TraMediaPlanPlaylistDateMapper;
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
public class TraMediaPlanPlaylistDateMapperTest {
    @Autowired
    private TraMediaPlanPlaylistDateMapper traMediaPlanPlaylistDateMapper;

    private TraMediaPlanPlaylistDate traMediaPlanPlaylistDate;

    private TraMediaPlanPlaylistDateDTO traMediaPlanPlaylistDateDTO;

    private List<TraMediaPlanPlaylistDate> traMediaPlanPlaylistDates = new ArrayList<>();

    private List<TraMediaPlanPlaylistDateDTO> traMediaPlanPlaylistDateDTOS = new ArrayList<>();

    private CorChannel corChannel;


    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();

        corChannel = factory.manufacturePojo(CorChannel.class);
        corChannel = factory.manufacturePojo(CorChannel.class);
        traMediaPlanPlaylistDateDTO = factory.manufacturePojo(TraMediaPlanPlaylistDateDTO.class);
        traMediaPlanPlaylistDateDTOS.add(traMediaPlanPlaylistDateDTO);
        traMediaPlanPlaylistDate = factory.manufacturePojo(TraMediaPlanPlaylistDate.class);
        traMediaPlanPlaylistDate.setId(1L);
        traMediaPlanPlaylistDate.setChannel(corChannel);
        traMediaPlanPlaylistDates.add(traMediaPlanPlaylistDate);
    }

    @Test
    public void DB2DTO() throws Exception {
        TraMediaPlanPlaylistDateDTO dto = traMediaPlanPlaylistDateMapper.DB2DTO(traMediaPlanPlaylistDate);

        assertNotNull(dto.getId());
        assertNotNull(dto.getPlaylistDate());

    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<TraMediaPlanPlaylistDateDTO> dtos = traMediaPlanPlaylistDateMapper.DBs2DTOs(traMediaPlanPlaylistDates);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {

            assertNotNull(dto.getId());

            assertNotNull(dto.getPlaylistDate());


        });
    }


    @Test
    public void DTO2DB() throws Exception {
        TraMediaPlanPlaylistDate entity = traMediaPlanPlaylistDateMapper.DTO2DB(traMediaPlanPlaylistDateDTO, corChannel);
        assertNotNull(entity.getId());
        assertNotNull(entity.getPlaylistDate());
        assertNotNull(entity.getChannel());

    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<TraMediaPlanPlaylistDate> entities = traMediaPlanPlaylistDateMapper.DTOs2DBs(traMediaPlanPlaylistDateDTOS, corChannel);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getPlaylistDate());
            assertNotNull(entity.getChannel());

        });
    }
}