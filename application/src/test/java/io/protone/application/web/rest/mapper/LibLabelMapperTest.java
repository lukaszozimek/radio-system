package io.protone.application.web.rest.mapper;


import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorNetwork;
import io.protone.library.api.dto.LibLabelDTO;
import io.protone.library.domain.LibLabel;
import io.protone.library.mapper.LibLabelMapper;
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
 * Created by lukaszozimek on 27/04/2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LibLabelMapperTest {
    @Autowired
    private LibLabelMapper customLibLabelMapperExt;

    private LibLabel libLabel;

    private LibLabelDTO libLabelDTO;

    private List<LibLabelDTO> libLabelDTOS = new ArrayList<>();

    private List<LibLabel> libLabels = new ArrayList<>();
    private CorNetwork corNetwork;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        libLabel = factory.manufacturePojo(LibLabel.class);
        libLabels.add(libLabel);
        libLabelDTO = factory.manufacturePojo(LibLabelDTO.class);
        libLabelDTOS.add(libLabelDTO);

        corNetwork = factory.manufacturePojo(CorNetwork.class);
    }

    @Test
    public void DB2DTO() throws Exception {
        LibLabelDTO dto = customLibLabelMapperExt.DB2DTO(libLabel);

        assertNotNull(dto.getDescription());
        assertNotNull(dto.getId());
        assertNotNull(dto.getName());

    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<LibLabelDTO> dtos = customLibLabelMapperExt.DBs2DTOs(libLabels);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getDescription());
            assertNotNull(dto.getId());
            assertNotNull(dto.getName());
        });
    }

    @Test
    public void DTO2DB() throws Exception {
        LibLabel entity = customLibLabelMapperExt.DTO2DB(libLabelDTO, corNetwork);

        assertNotNull(entity.getId());
        assertNotNull(entity.getName());
        assertNotNull(entity.getDescription());

        assertNotNull(entity.getNetwork());
    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<LibLabel> entities = customLibLabelMapperExt.DTOs2DBs(libLabelDTOS, corNetwork);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getName());
            assertNotNull(entity.getDescription());

            assertNotNull(entity.getNetwork());
        });

    }

}
