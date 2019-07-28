package io.protone.application.service.cor;


import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorPerson;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.core.service.CorPersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 30/04/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class CorPersonServiceTest {
    @Autowired
    private CorPersonService corPersonService;

    @Autowired
    private CorNetworkRepository networkRepository;

    private CorNetwork corNetwork;

    private PodamFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new PodamFactoryImpl();
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corNetwork.setId(null);
        corNetwork = networkRepository.saveAndFlush(corNetwork);

    }

    @Test
    public void shouldSaveCorPerson() throws Exception {
        //then
        CorPerson corPerson = corPersonService.savePerson(factory.manufacturePojo(CorPerson.class).network(corNetwork));

        //assert
        assertNotNull(corPerson);
        assertNotNull(corPerson.getId());
        assertNotNull(corPerson.getContacts());
        assertNotNull(corPerson.getCreatedBy());
        assertFalse(corPerson.getContacts().isEmpty());
        assertNotNull(corPerson.getContacts().stream().findFirst().get().getId());
    }

    @Test
    public void shouldNotSaveCorContacst() throws Exception {
        //then
        CorPerson corPerson = corPersonService.savePerson(factory.manufacturePojo(CorPerson.class).network(corNetwork).contacts(null));

        //assert
        assertNotNull(corPerson);
        assertNotNull(corPerson.getId());
        assertNotNull(corPerson.getCreatedBy());
        assertNull(corPerson.getContacts());

    }

}
