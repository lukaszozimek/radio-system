package io.protone.application.web.rest.mapper;

import io.protone.application.ProtoneApp;
import io.protone.application.web.rest.mapper.factory.SchClockFactory;
import io.protone.application.web.rest.mapper.factory.SchGridFactory;
import io.protone.scheduler.api.dto.SchGridDTO;
import io.protone.scheduler.domain.SchGrid;
import io.protone.scheduler.mapper.SchGridMapper;
import org.junit.After;
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
public class SchGridMapperTest {
    
    private static final PodamFactory factory = new PodamFactoryImpl();

    @Autowired
    private SchGridMapper gridMapper;

    private SchGrid grid;

    private SchGridDTO gridDTO;

    private List<SchGrid> grids = new ArrayList<>();

    private List<SchGridDTO> gridDTOs = new ArrayList<>();

    @Before
    public void initPojos() {

        grid = SchGridFactory.produceEntity();
        grids.add(grid);
        gridDTO = SchGridFactory.produceDTO();
        gridDTOs.add(gridDTO);
    }

    @Test
    public void toDTO() throws Exception {
        SchGridDTO dto = gridMapper.toDto(grid);
        assertEquals(dto.getClocks().size(), 3);
        assertNotNull(dto.getName());
        assertNotNull(dto.getDayOfWeek());
        assertNotNull(dto.getShortName());
    }

    @Test
    public void toDTOs() throws Exception {
        List<SchGridDTO> dtos = gridMapper.toDto(grids);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertEquals(dto.getClocks().size(), 3);
            assertNotNull(dto.getName());
            assertNotNull(dto.getDayOfWeek());
            assertNotNull(dto.getShortName());
        });
    }

    @Test
    public void toEntity() throws Exception {
        SchGrid entity = gridMapper.toEntity(gridDTO);

        assertEquals(entity.getClocks().size(), 3);
        assertNotNull(entity.getName());
        assertNotNull(entity.getDayOfWeek());
        assertNotNull(entity.getShortName());
    }

    @Test
    public void toEntities() throws Exception {
        List<SchGrid> entities = gridMapper.toEntity(gridDTOs);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertEquals(entity.getClocks().size(), 3);
            assertNotNull(entity.getName());
            assertNotNull(entity.getDayOfWeek());
            assertNotNull(entity.getShortName());
        });
    }
}