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
    }

    @Test
    public void DBs2DTOs() throws Exception {
    }

    @Test
    public void DTO2DB() throws Exception {
    }

    @Test
    public void DTOs2DBs() throws Exception {
    }

    @Test
    public void db2Dto() throws Exception {
    }

    @Test
    public void dBs2DTOs() throws Exception {
    }

    @Test
    public void dto2Db() throws Exception {
    }

    @Test
    public void dtos2Dbs() throws Exception {
    }

}
