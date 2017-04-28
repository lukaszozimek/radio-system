package io.protone.service.mapper;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.ConfPersonPT;
import io.protone.custom.service.dto.LibPersonPT;
import io.protone.custom.service.dto.TraCustomerPersonPT;
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

    private ConfPersonPT confPersonPT;

    private LibPersonPT libPersonPT;

    private TraCustomerPersonPT traCustomerPersonPT;

    private CorNetwork corNetwork;

    private List<ConfPersonPT> confPersonPTS = new ArrayList<>();

    private List<CorPerson> corPeople = new ArrayList<>();

    private List<LibPersonPT> libPersonPTS = new ArrayList<>();

    private List<TraCustomerPersonPT> traCustomerPersonPTList = new ArrayList<>();

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        corPerson = factory.manufacturePojo(CorPerson.class);
        corPeople.add(corPerson);
        confPersonPT = factory.manufacturePojo(ConfPersonPT.class);
        confPersonPTS.add(confPersonPT);
        libPersonPT = factory.manufacturePojo(LibPersonPT.class);
        libPersonPTS.add(libPersonPT);
        traCustomerPersonPT = factory.manufacturePojo(TraCustomerPersonPT.class);
        traCustomerPersonPTList.add(traCustomerPersonPT);
        corNetwork = factory.manufacturePojo(CorNetwork.class);
    }


    @Test
    public void DB2DTO() throws Exception {
        ConfPersonPT dto = corPersonMapper.DB2DTO(corPerson);
        assertNotNull(dto.getId());
        assertNotNull(dto.getFirstName());
        assertNotNull(dto.getLastName());
        assertNotNull(dto.getDescription());

        assertNotNull(dto.getId());

    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<ConfPersonPT> dtos = corPersonMapper.DBs2DTOs(corPeople);

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
        List<CorPerson> entities = corPersonMapper.DTOs2DBs(confPersonPTS);

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
        CorPerson entity = corPersonMapper.DTO2DB(confPersonPT);

        assertNotNull(entity.getDescription());
        assertNotNull(entity.getFirstName());
        assertNotNull(entity.getId());
        assertNotNull(entity.getLastName());

        assertNull(entity.getNetwork());
    }

    @Test
    public void corPerson2LibPersonPT() throws Exception {
        LibPersonPT dto = corPersonMapper.corPerson2LibPersonPT(corPerson);
        assertNotNull(dto.getId());
        assertNotNull(dto.getFirstName());
        assertNotNull(dto.getLastName());
        assertNotNull(dto.getDescription());


    }

    @Test
    public void corPersons2LibPersonPTs() throws Exception {
        List<LibPersonPT> dtos = corPersonMapper.corPersons2LibPersonPTs(corPeople);

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
        CorPerson entity = corPersonMapper.libPersonPT2CorPerson(libPersonPT);

        assertNotNull(entity.getDescription());
        assertNotNull(entity.getFirstName());
        assertNotNull(entity.getId());
        assertNotNull(entity.getLastName());

        assertNull(entity.getNetwork());
    }

    @Test
    public void libPersonPTs2corPersons() throws Exception {
        List<CorPerson> entities = corPersonMapper.libPersonPTs2corPersons(libPersonPTS);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());
            assertNotNull(entity.getDescription());
            assertNotNull(entity.getFirstName());
            assertNotNull(entity.getId());
            assertNotNull(entity.getLastName());

            assertNull(entity.getNetwork());
        });
    }

    @Test
    public void corPerson2TraCustomerPersonPT() throws Exception {
        TraCustomerPersonPT dto = corPersonMapper.corPerson2TraCustomerPersonPT(corPerson);
        assertNotNull(dto.getId());
        assertNotNull(dto.getDescription());
        assertNotNull(dto.getFirstName());
        assertNotNull(dto.getId());
        assertNotNull(dto.getLastName());
        assertNotNull(dto.getContacts());

    }

    @Test
    public void traCustomerPersonPT2CorPerson() throws Exception {
        CorPerson entity = corPersonMapper.traCustomerPersonPT2CorPerson(traCustomerPersonPT, corNetwork);

        assertNotNull(entity.getDescription());
        assertNotNull(entity.getFirstName());
        assertNotNull(entity.getId());
        assertNotNull(entity.getLastName());
        assertNotNull(entity.getContacts());
        assertNotNull(entity.getNetwork());

    }

    @Test
    public void traCustomerPersonPTs2CorPersons() throws Exception {
        List<CorPerson> entities = corPersonMapper.traCustomerPersonPTs2CorPersons(traCustomerPersonPTList, corNetwork);

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
