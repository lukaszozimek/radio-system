package io.protone.application.web.rest.mapper;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.traffic.api.dto.TraMediaPlanTemplateDTO;
import io.protone.traffic.domain.TraMediaPlanTemplate;
import io.protone.traffic.mapper.TraMediaPlanTemplateMapper;
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
 * Created by lukaszozimek on 31/07/2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class TraMediaPlanTemplateMapperTest {
    @Autowired
    private TraMediaPlanTemplateMapper traMediaPlanTemplateMapper;

    private TraMediaPlanTemplate traMediaPlanTemplate;

    private TraMediaPlanTemplateDTO traMediaPlanTemplateDTO;

    private List<TraMediaPlanTemplateDTO> traMediaPlanTemplateDTOS = new ArrayList<>();

    private List<TraMediaPlanTemplate> traMediaPlanTemplates = new ArrayList<>();

    private CorChannel corChannel;


    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        traMediaPlanTemplate = factory.manufacturePojo(TraMediaPlanTemplate.class);
        traMediaPlanTemplates.add(traMediaPlanTemplate);
        traMediaPlanTemplateDTO = factory.manufacturePojo(TraMediaPlanTemplateDTO.class);
        traMediaPlanTemplateDTOS.add(traMediaPlanTemplateDTO);

        corChannel = factory.manufacturePojo(CorChannel.class);
    }

    @Test
    public void DTO2DB() throws Exception {
        TraMediaPlanTemplate entity = traMediaPlanTemplateMapper.DTO2DB(traMediaPlanTemplateDTO, corChannel);


        assertNotNull(entity.getBlockStartCell());
        assertNotNull(entity.getBlockEndCell());
        assertNotNull(entity.getBlockHourSeparator());
        assertNotNull(entity.getPlaylistDateStartColumn());
        assertNotNull(entity.getPlaylistDateEndColumn());
        assertNotNull(entity.getPlaylistFirsValueCell());
        assertNotNull(entity.getPlaylistDatePattern());
        assertNotNull(entity.getSheetIndexOfMediaPlan());
        assertNotNull(entity.getBlockStartColumn());
        assertNotNull(entity.getFirstEmissionValueCell());
        assertNotNull(entity.getLastEmissionValueCell());
        assertNotNull(entity.getId());
        assertNotNull(entity.getName());
        assertNotNull(entity.getChannel());
    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<TraMediaPlanTemplate> entities = traMediaPlanTemplateMapper.DTOs2DBs(traMediaPlanTemplateDTOS, corChannel);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getBlockStartCell());
            assertNotNull(entity.getBlockEndCell());
            assertNotNull(entity.getBlockHourSeparator());
            assertNotNull(entity.getPlaylistDateStartColumn());
            assertNotNull(entity.getPlaylistDateEndColumn());
            assertNotNull(entity.getPlaylistFirsValueCell());
            assertNotNull(entity.getPlaylistDatePattern());
            assertNotNull(entity.getSheetIndexOfMediaPlan());
            assertNotNull(entity.getBlockStartColumn());
            assertNotNull(entity.getFirstEmissionValueCell());
            assertNotNull(entity.getLastEmissionValueCell());
            assertNotNull(entity.getId());
            assertNotNull(entity.getName());
            assertNotNull(entity.getChannel());

        });
    }

    @Test
    public void DB2DTO() throws Exception {
        TraMediaPlanTemplateDTO dto = traMediaPlanTemplateMapper.DB2DTO(traMediaPlanTemplate);

        assertNotNull(dto.getId());
        assertNotNull(dto.getName());
        assertNotNull(dto.getBlockStartColumn());
        assertNotNull(dto.getBlockStartCell());
        assertNotNull(dto.getBlockEndCell());
        assertNotNull(dto.getBlockHourSeparator());
        assertNotNull(dto.getPlaylistDateStartColumn());
        assertNotNull(dto.getPlaylistDateEndColumn());
        assertNotNull(dto.getPlaylistFirsValueCell());
        assertNotNull(dto.getPlaylistDatePattern());
        assertNotNull(dto.getSheetIndexOfMediaPlan());
        assertNotNull(dto.getFirstEmissionValueCell());
        assertNotNull(dto.getLastEmissionValueCell());
    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<TraMediaPlanTemplateDTO> dtos = traMediaPlanTemplateMapper.DBs2DTOs(traMediaPlanTemplates);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getId());
            assertNotNull(dto.getName());
            assertNotNull(dto.getBlockStartColumn());
            assertNotNull(dto.getBlockStartCell());
            assertNotNull(dto.getBlockEndCell());
            assertNotNull(dto.getBlockHourSeparator());
            assertNotNull(dto.getPlaylistDateStartColumn());
            assertNotNull(dto.getPlaylistDateEndColumn());
            assertNotNull(dto.getPlaylistFirsValueCell());
            assertNotNull(dto.getPlaylistDatePattern());
            assertNotNull(dto.getSheetIndexOfMediaPlan());
            assertNotNull(dto.getFirstEmissionValueCell());
            assertNotNull(dto.getLastEmissionValueCell());


        });

    }
}