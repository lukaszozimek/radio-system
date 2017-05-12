package io.protone.service.library;

import io.protone.ProtoneApp;
import io.protone.domain.CorNetwork;
import io.protone.repository.cor.CorNetworkRepository;
import io.protone.service.library.LibItemService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;

/**
 * Created by lukaszozimek on 30/04/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class LibItemServiceTest {
    @Autowired
    private LibItemService libArtistService;

    @Autowired
    private CorNetworkRepository corNetworkRepository;

    private CorNetwork corNetwork;

    private PodamFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new PodamFactoryImpl();
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corNetwork.setId(null);
        corNetwork = corNetworkRepository.saveAndFlush(corNetwork);
    }

    @Test
    public void getItem() throws Exception {
    }

    @Test
    public void getItem1() throws Exception {
    }

    @Test
    public void deleteItem() throws Exception {
    }

    @Test
    public void deleteItem1() throws Exception {
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void upload() throws Exception {
    }

    @Test
    public void download() throws Exception {
    }

    @Test
    public void getItemFromDB() throws Exception {
    }

}
