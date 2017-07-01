package io.protone.application.service.library;


import io.jsonwebtoken.lang.Assert;
import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorNetwork;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.library.repository.LibAlbumRepository;
import io.protone.library.service.LibAlbumService;
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
public class LibAlbumServiceTest {


    @Autowired
    private LibAlbumService libAlbumService;

    @Autowired
    private LibAlbumRepository libAlbumRepository;

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
    public void shouldFindAlbum() throws Exception {
        Assert.notNull(null);
    }


    @Test
    public void shouldSaveDefaultAlbum() throws Exception {
        Assert.notNull(null);
    }

    @Test
    public void shouldSaveAlbumWithImage() throws Exception {
        Assert.notNull(null);
    }

}
