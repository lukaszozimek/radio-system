package io.protone.application.web.rest.mapper;


import io.protone.application.ProtoneApp;
import io.protone.core.api.dto.CorPersonDTO;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorPerson;
import io.protone.core.mapper.CorPersonMapper;
import io.protone.library.api.dto.LibPersonDTO;
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
public class CorPersonMapperTest {


    @Autowired
    private CorPersonMapper corPersonMapper;

    private CorPerson corPerson;

    private CorPersonDTO corPersonDTO;

    private LibPersonDTO libPersonDTO;

    private List<LibPersonDTO> libPersonDTOS = new ArrayList<>();

    private CorNetwork corNetwork;

    private List<CorPersonDTO> corPersonDTOS = new ArrayList<>();

    private List<CorPerson> corPeople = new ArrayList<>();


    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        corPerson = factory.manufacturePojo(CorPerson.class);
        corPerson.setId(1L);
        corPeople.add(corPerson);
        corPersonDTO = factory.manufacturePojo(CorPersonDTO.class);
        corPersonDTO.setId(1L);
        corPersonDTOS.add(corPersonDTO);
        libPersonDTO = factory.manufacturePojo(LibPersonDTO.class);
        libPersonDTO.setId(1L);

        libPersonDTOS.add(libPersonDTO);
        corNetwork = factory.manufacturePojo(CorNetwork.class);
    }


    @Test
    public void DB2DTO() throws Exception {
        CorPersonDTO dto = corPersonMapper.DB2DTO(corPerson);
        assertNotNull(dto.getId());
        assertNotNull(dto.getFirstName());
        assertNotNull(dto.getLastName());
        assertNotNull(dto.getDescription());

        assertNotNull(dto.getId());

    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<CorPersonDTO> dtos = corPersonMapper.DBs2DTOs(corPeople);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {
            assertNotNull(dto.getId());
            assertNotNull(dto.getFirstName());
            assertNotNull(dto.getLastName());
            assertNotNull(dto.getDescription());
            assertNotNull(dto.getId());

        });
    }

    @Test
    public void DBs2DTOs() throws Exception {
        List<CorPerson> entities = corPersonMapper.DTOs2DBs(corPersonDTOS, corNetwork);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getFirstName());
            assertNotNull(entity.getLastName());
            assertNotNull(entity.getDescription());
            assertNotNull(entity.getNetwork());


        });
    }

    @Test
    public void DTO2DB() throws Exception {
        CorPerson entity = corPersonMapper.DTO2DB(corPersonDTO, corNetwork);

        assertNotNull(entity.getDescription());
        assertNotNull(entity.getFirstName());
        assertNotNull(entity.getId());
        assertNotNull(entity.getLastName());

        assertNotNull(entity.getNetwork());
    }


}
