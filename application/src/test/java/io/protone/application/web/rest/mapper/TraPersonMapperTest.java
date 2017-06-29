package io.protone.application.web.rest.mapper;

import io.protone.application.ProtoneApp;
import io.protone.core.api.dto.CorPersonDTO;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorPerson;
import io.protone.traffic.api.dto.TraCustomerPersonDTO;
import io.protone.traffic.mapper.TraPersonMapper;
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
public class TraPersonMapperTest {

    @Autowired
    private TraPersonMapper corPersonMapper;


    private CorPerson corPerson;

    private CorPersonDTO corPersonDTO;

    private TraCustomerPersonDTO traCustomerPersonDTO;

    private List<TraCustomerPersonDTO> traCustomerPersonDTOList = new ArrayList<>();

    private CorNetwork corNetwork;

    @Before
    public void initPojos() {
        PodamFactory factory = new PodamFactoryImpl();
        corPerson = factory.manufacturePojo(CorPerson.class);
        corPerson.setId(1L);
        corPersonDTO = factory.manufacturePojo(CorPersonDTO.class);
        corPersonDTO.setId(1L);

        traCustomerPersonDTO = factory.manufacturePojo(TraCustomerPersonDTO.class);
        traCustomerPersonDTO.setId(1L);
        traCustomerPersonDTOList.add(traCustomerPersonDTO);
        corNetwork = factory.manufacturePojo(CorNetwork.class);
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
