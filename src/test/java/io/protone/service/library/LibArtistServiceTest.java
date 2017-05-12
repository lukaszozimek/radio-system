package io.protone.service.library;

import io.protone.ProtoneApp;
import io.protone.domain.CorNetwork;
import io.protone.domain.LibArtist;
import io.protone.repository.cor.CorNetworkRepository;
import io.protone.repository.library.LibArtistRepository;
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
public class LibArtistServiceTest {

    @Autowired
    private LibArtistService libArtistService;

    @Autowired
    private LibArtistRepository libArtistRepository;

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
    public void shouldFindOne() throws Exception {
        //when
        LibArtist libArtist = factory.manufacturePojo(LibArtist.class).name("test").network(corNetwork);
        libArtist.setId(null);
        libArtist = libArtistRepository.save(libArtist);

        //then
        LibArtist exisiting = libArtistService.findOrSaveOne("test", corNetwork);

        //assert
        assertNotNull(exisiting);
        assertEquals(libArtist.getId(), exisiting.getId());
    }

    @Test
    public void shouldSaveNewOne() throws Exception {
        int size = libArtistRepository.findAll().size();

        LibArtist newOne = libArtistService.findOrSaveOne("test", corNetwork);


        int afterAdd = libArtistRepository.findAll().size();

        assertNotNull(newOne);
        assertNotNull(newOne.getId());
        assertEquals(corNetwork.getId(), newOne.getNetwork().getId());
        assertEquals(size + 1, afterAdd);
    }

    @Test
    public void shouldNotCreateAnyWithOutName() throws Exception {
        int size = libArtistRepository.findAll().size();

        LibArtist newOne = libArtistService.findOrSaveOne(null, corNetwork);


        int afterAdd = libArtistRepository.findAll().size();

        assertNull(newOne);
        assertEquals(size, afterAdd);
    }

    @Test
    public void shouldNotCreateAnyNetwork() throws Exception {
        int size = libArtistRepository.findAll().size();

        LibArtist newOne = libArtistService.findOrSaveOne("test", null);


        int afterAdd = libArtistRepository.findAll().size();

        assertNull(newOne);
        assertEquals(size, afterAdd);
    }

}
