package io.protone.service.cor;

import io.protone.ProtoneApp;
import io.protone.domain.CorChannel;
import io.protone.domain.CorNetwork;
import io.protone.repository.cor.CorNetworkRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 28.04.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class CorNetworkServiceTest {

    private static final String TEST_NAME = "tet";
    @Autowired
    private CorNetworkService corNetworkService;

    @Autowired
    private CorNetworkRepository corNetworkRepository;

    private CorNetwork corNetwork;

    private PodamFactory factory;

    @Before
    public void initPojos() {
        factory = new PodamFactoryImpl();
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corNetwork.setId(null);
        corNetwork = corNetworkRepository.saveAndFlush(corNetwork);
    }

    @Test
    public void findAllNetworks() throws Exception {
        //TODO: implement after upgrading to Junit5
    }

    @Test
    public void findNetwork() throws Exception {
        CorNetwork local = corNetworkService.findNetwork(corNetwork.getShortcut());
        assertNotNull(local);
        assertEquals(corNetwork.getId(), local.getId());

    }


    @Test
    public void save() throws Exception {
        factory = new PodamFactoryImpl();
        CorNetwork local = factory.manufacturePojo(CorNetwork.class);
        local.setId(null);
        local = corNetworkService.save(corNetwork);

        assertNotNull(local.getId());
    }

    @Test
    public void deleteNetwork() throws Exception {
        corNetworkService.deleteNetwork(corNetwork.getShortcut());
        CorNetwork local = corNetworkService.findNetwork(corNetwork.getShortcut());
        assertNull(local);

    }
    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveTwoNetworksWithSameShortName() {

        /// /when
        CorNetwork corNetwork = factory.manufacturePojo(CorNetwork.class);
        corNetwork.setId(null);
        corNetwork.setShortcut(TEST_NAME);

        CorNetwork corNetwork1 = factory.manufacturePojo(CorNetwork.class);
        corNetwork1.setId(null);
        corNetwork1.setShortcut(TEST_NAME);

        corNetwork = corNetworkService.save(corNetwork);
        corNetwork1 = corNetworkService.save(corNetwork1);

    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveTwoNetworksWithSameName() {

        /// /when
        CorNetwork corNetwork = factory.manufacturePojo(CorNetwork.class);
        corNetwork.setId(null);
        corNetwork.setName(TEST_NAME);

        CorNetwork corNetwork1 = factory.manufacturePojo(CorNetwork.class);
        corNetwork1.setId(null);
        corNetwork1.setName(TEST_NAME);

        corNetwork = corNetworkService.save(corNetwork);
        corNetwork1 = corNetworkService.save(corNetwork1);

    }
}
