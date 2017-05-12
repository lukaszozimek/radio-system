package io.protone.web.rest.mapper;

import io.protone.ProtoneApp;
import io.protone.web.rest.dto.library.LibPersonDTO;
import io.protone.web.rest.dto.cor.CorPersonDTO;
import io.protone.web.rest.dto.traffic.TraCustomerPersonDTO;
import io.protone.domain.CorNetwork;
import io.protone.domain.CorPerson;
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
import static org.junit.Assert.assertNull;

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

    private TraCustomerPersonDTO traCustomerPersonDTO;

    private CorNetwork corNetwork;

    private List<CorPersonDTO> corPersonDTOS = new ArrayList<>();

    private List<CorPerson> corPeople = new ArrayList<>();

    private List<LibPersonDTO> libPersonDTOS = new ArrayList<>();

    private List<TraCustomerPersonDTO> traCustomerPersonDTOList = new ArrayList<>();

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
        traCustomerPersonDTO = factory.manufacturePojo(TraCustomerPersonDTO.class);
        traCustomerPersonDTO.setId(1L);
        traCustomerPersonDTOList.add(traCustomerPersonDTO);
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
        List<CorPerson> entities = corPersonMapper.DTOs2DBs(corPersonDTOS);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getFirstName());
            assertNotNull(entity.getLastName());
            assertNotNull(entity.getDescription());
            assertNull(entity.getNetwork());


        });
    }

    @Test
    public void DTO2DB() throws Exception {
        CorPerson entity = corPersonMapper.DTO2DB(corPersonDTO);

        assertNotNull(entity.getDescription());
        assertNotNull(entity.getFirstName());
        assertNotNull(entity.getId());
        assertNotNull(entity.getLastName());

        assertNull(entity.getNetwork());
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
        CorPerson entity = corPersonMapper.libPersonPT2CorPerson(libPersonDTO, corNetwork);

        assertNotNull(entity.getDescription());
        assertNotNull(entity.getFirstName());
        assertNotNull(entity.getId());
        assertNotNull(entity.getLastName());

        assertNotNull(entity.getNetwork());
    }

    @Test
    public void libPersonPTs2corPersons() throws Exception {
        List<CorPerson> entities = corPersonMapper.libPersonPTs2corPersons(libPersonDTOS, corNetwork);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getDescription());
            assertNotNull(entity.getFirstName());
            assertNotNull(entity.getId());
            assertNotNull(entity.getLastName());

            assertNotNull(entity.getNetwork());
        });
    }

    @Test
    public void corPerson2TraCustomerPersonPT() throws Exception {
        TraCustomerPersonDTO dto = corPersonMapper.corPerson2TraCustomerPersonPT(corPerson);
        assertNotNull(dto.getId());
        assertNotNull(dto.getDescription());
        assertNotNull(dto.getFirstName());
        assertNotNull(dto.getId());
        assertNotNull(dto.getLastName());
        assertNotNull(dto.getContacts());

    }

    @Test
    public void traCustomerPersonPT2CorPerson() throws Exception {
        CorPerson entity = corPersonMapper.traCustomerPersonPT2CorPerson(traCustomerPersonDTO, corNetwork);

        assertNotNull(entity.getDescription());
        assertNotNull(entity.getFirstName());
        assertNotNull(entity.getId());
        assertNotNull(entity.getLastName());
        assertNotNull(entity.getContacts());
        assertNotNull(entity.getNetwork());

    }

    @Test
    public void traCustomerPersonPTs2CorPersons() throws Exception {
        List<CorPerson> entities = corPersonMapper.traCustomerPersonPTs2CorPersons(traCustomerPersonDTOList, corNetwork);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getFirstName());
            assertNotNull(entity.getLastName());
            assertNotNull(entity.getDescription());
            assertNotNull(entity.getContacts());
            assertNotNull(entity.getNetwork());


        });
    }

}
