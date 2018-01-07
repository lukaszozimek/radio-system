package io.protone.application.web.rest.mapper;

import io.protone.application.ProtoneApp;
import io.protone.core.api.dto.CorPersonDTO;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorPerson;
import io.protone.library.api.dto.LibPersonDTO;
import io.protone.library.mapper.LibPersonMapper;
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
 * Created by lukaszozimek on 28/06/2017.
 */
@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
public class LibPersonMapperTest {
    @Autowired
    private LibPersonMapper corPersonMapper;

    private CorPerson corPerson;

    private CorPersonDTO corPersonDTO;

    private LibPersonDTO libPersonDTO;

    private List<LibPersonDTO> libPersonDTOS = new ArrayList<>();

    private CorChannel corChannel;

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
        corChannel = factory.manufacturePojo(CorChannel.class);
    }

    @Test
    public void corPerson2LibPersonPT() throws Exception {
        LibPersonDTO dto = corPersonMapper.corPerson2LibPersonPT(corPerson);
        assertNotNull(dto.getId());
        assertNotNull(dto.getFirstName());
        assertNotNull(dto.getLastName());
        assertNotNull(dto.getDescription());


    }

    @Test
    public void corPersons2LibPersonPTs() throws Exception {
        List<LibPersonDTO> dtos = corPersonMapper.corPersons2LibPersonPTs(corPeople);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {

            assertNotNull(dto.getId());
            assertNotNull(dto.getFirstName());
            assertNotNull(dto.getLastName());
            assertNotNull(dto.getDescription());

        });
    }

    @Test
    public void libPersonPT2CorPerson() throws Exception {
        CorPerson entity = corPersonMapper.libPersonPT2CorPerson(libPersonDTO, corChannel);

        assertNotNull(entity.getDescription());
        assertNotNull(entity.getFirstName());
        assertNotNull(entity.getId());
        assertNotNull(entity.getLastName());

        assertNotNull(entity.getChannel());
    }

    @Test
    public void libPersonPTs2corPersons() throws Exception {
        List<CorPerson> entities = corPersonMapper.libPersonPTs2corPersons(libPersonDTOS, corChannel);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getDescription());
            assertNotNull(entity.getFirstName());
            assertNotNull(entity.getId());
            assertNotNull(entity.getLastName());

            assertNotNull(entity.getChannel());
        });
    }
}
