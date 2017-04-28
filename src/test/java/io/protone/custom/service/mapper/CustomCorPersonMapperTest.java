package io.protone.custom.service.mapper;

import io.protone.ProtoneApp;
import io.protone.custom.service.dto.ConfPersonPT;
import io.protone.custom.service.dto.LibPersonPT;
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
public class CustomCorPersonMapperTest {


    @Autowired
    private CustomCorPersonMapper corPersonMapper;

    private CorPerson corPerson;

    private ConfPersonPT confPersonPT;

    private LibPersonPT libPersonPT;

    private List<ConfPersonPT> confPersonPTS = new ArrayList<>();

    private List<CorPerson> corPeople = new ArrayList<>();

    private List<LibPersonPT> libPersonPTS = new ArrayList<>();


    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        corPerson = factory.manufacturePojo(CorPerson.class);
        corPeople.add(corPerson);
        confPersonPT = factory.manufacturePojo(ConfPersonPT.class);
        confPersonPTS.add(confPersonPT);
        libPersonPT = factory.manufacturePojo(LibPersonPT.class);
        libPersonPTS.add(libPersonPT);
    }


    @Test
    public void DB2DTO() throws Exception {
        ConfPersonPT dto = corPersonMapper.DB2DTO(corPerson);

        assertNotNull(dto.getId());

    }

    @Test
    public void DTOs2DBs() throws Exception {
        List<ConfPersonPT> dtos = corPersonMapper.DBs2DTOs(corPeople);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {

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

            assertNull(entity.getNetwork());


        });
    }

    @Test
    public void DTO2DB() throws Exception {
        CorPerson entity = corPersonMapper.DTO2DB(confPersonPT);


        assertNotNull(entity.getId());

        assertNull(entity.getNetwork());
    }

    @Test
    public void corPerson2LibPersonPT() throws Exception {
        LibPersonPT dto = corPersonMapper.corPerson2LibPersonPT(corPerson);

        assertNotNull(dto.getId());

    }

    @Test
    public void corPersons2LibPersonPTs() throws Exception {
        List<LibPersonPT> dtos = corPersonMapper.corPersons2LibPersonPTs(corPeople);

        assertNotNull(dtos);
        assertEquals(dtos.size(), 1);
        dtos.stream().forEach(dto -> {

            assertNotNull(dto.getId());

        });
    }

    @Test
    public void libPersonPT2CorPerson() throws Exception {
        CorPerson entity = corPersonMapper.libPersonPT2CorPerson(libPersonPT);


        assertNotNull(entity.getId());

        assertNull(entity.getNetwork());
    }

    @Test
    public void libPersonPTs2corPersons() throws Exception {
        List<CorPerson> entities = corPersonMapper.libPersonPTs2corPersons(libPersonPTS);

        assertNotNull(entities);
        assertEquals(entities.size(), 1);
        entities.stream().forEach(entity -> {
            assertNotNull(entity.getId());

            assertNull(entity.getNetwork());
        });
    }

    @Test
    public void corPerson2TraCustomerPersonPT() throws Exception {

    }

    @Test
    public void traCustomerPersonPT2CorPerson() throws Exception {

    }

    @Test
    public void traCustomerPersonPTs2CorPersons() throws Exception {

    }

}
