package io.protone.application.web.rest.mapper;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.scheduler.api.dto.SchScheduleDTO;
import io.protone.scheduler.domain.SchSchedule;
import io.protone.scheduler.mapper.SchScheduleMapper;
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
public class SchScheduleMapperTest {

    @Autowired
    private SchScheduleMapper scheduleMapper;

    private SchSchedule schedule;

    private SchScheduleDTO scheduleDTO;

    private List<SchSchedule> schedules = new ArrayList<>();

    private List<SchScheduleDTO> scheduleDTOs = new ArrayList<>();
    private CorNetwork network;
    private CorChannel corChannel;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        corChannel = factory.manufacturePojo(CorChannel.class);
        network = factory.manufacturePojo(CorNetwork.class);
        // Fill entity instance
        schedule = factory.manufacturePojo(SchSchedule.class);
        schedule.setBlocks(null);
        schedules.add(schedule);
        scheduleDTO = factory.manufacturePojo(SchScheduleDTO.class);
        scheduleDTO.setSchClockDTOS(null);
        scheduleDTOs.add(scheduleDTO);
    }

    @Test
    public void toDTO() throws Exception {
        SchScheduleDTO dto = scheduleMapper.DB2DTO(schedule);

        assertNotNull(dto.getDate());
        //assertNotNull(dto.getSchClockDTOS()); //TODO: test media item instance mapping
    }

    @Test
    public void toDTOs() throws Exception {
        List<SchScheduleDTO> dtos = scheduleMapper.DBs2DTOs(schedules);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getDate());
            //assertNotNull(dto.getSchClockDTOS()); //TODO: test media item instance mapping
        });
    }

    @Test
    public void DTO2DB() throws Exception {
        SchSchedule entity = scheduleMapper.DTO2DB(scheduleDTO, network, corChannel);

        assertNotNull(entity.getDate());
        //assertNotNull(dto.getSchClockDTOS()); //TODO: test media item instance mapping
    }

    @Test
    public void toEntities() throws Exception {
        List<SchSchedule> entities = scheduleMapper.DTOs2DBs(scheduleDTOs, network, corChannel);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getDate());

            assertNotNull(entity.getNetwork());
            assertNotNull(entity.getChannel());
            //assertNotNull(dto.getSchClockDTOS()); //TODO: test media item instance mapping
        });
    }

}