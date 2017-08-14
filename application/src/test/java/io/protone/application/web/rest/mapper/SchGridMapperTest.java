package io.protone.application.web.rest.mapper;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.scheduler.to.delete.factory.SchGridFactory;
import io.protone.scheduler.api.dto.SchGridDTO;
import io.protone.scheduler.domain.SchGrid;
import io.protone.scheduler.mapper.SchGridMapper;
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
    private CorNetwork network;
    private CorChannel corChannel;

    @Before
    public void initPojos() {
        corChannel = factory.manufacturePojo(CorChannel.class);
        network = factory.manufacturePojo(CorNetwork.class);
        // Fill entity instance
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
        SchGrid entity = gridMapper.toEntity(gridDTO, network, corChannel);

        assertEquals(entity.getClocks().size(), 3);
        assertNotNull(entity.getName());
        assertNotNull(entity.getDayOfWeek());
        assertNotNull(entity.getShortName());

        assertNotNull(entity.getNetwork());
        assertNotNull(entity.getChannel());
    }

    @Test
    public void toEntities() throws Exception {
        List<SchGrid> entities = gridMapper.toEntity(gridDTOs, network, corChannel);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertEquals(entity.getClocks().size(), 3);
            assertNotNull(entity.getName());
            assertNotNull(entity.getDayOfWeek());
            assertNotNull(entity.getShortName());

            assertNotNull(entity.getNetwork());
            assertNotNull(entity.getChannel());
        });
    }
}