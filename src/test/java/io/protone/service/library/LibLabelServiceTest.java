package io.protone.service.library;

import io.protone.ProtoneApp;
import io.protone.domain.CorNetwork;
import io.protone.domain.LibLabel;
import io.protone.repository.cor.CorNetworkRepository;
import io.protone.repository.library.LibLabelRepository;
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
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Created by lukaszozimek on 05/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class LibLabelServiceTest {
    private static final String TEST_NAME = "TEST";
    @Autowired
    private LibLabelService libLabelService;

    @Autowired
    private LibLabelRepository libLabelRepository;

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
    public void shoudlSaveLibTrack() throws Exception {
        LibLabel libLabel = factory.manufacturePojo(LibLabel.class).network(corNetwork);

        Optional<LibLabel> savedLibLabel = libLabelService.saveLibLabel(libLabel);

        assertNotNull(savedLibLabel.get());
        assertNotNull(savedLibLabel.get().getId());

    }

    @Test
    public void shoudlNotSaveLibTrack() throws Exception {

        Optional<LibLabel> libLabel = libLabelService.saveLibLabel(null);

        assertFalse(libLabel.isPresent());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveTwoArtistWithSameShortNameInOneNetwork() {

        /// /when
        LibLabel libArtist = factory.manufacturePojo(LibLabel.class);
        libArtist.setId(null);
        libArtist.name(TEST_NAME);
        libArtist.setNetwork(corNetwork);
        LibLabel libArtist1 = factory.manufacturePojo(LibLabel.class);
        libArtist1.setId(null);
        libArtist1.setName(TEST_NAME);
        libArtist1.setNetwork(corNetwork);

        libArtist = libLabelRepository.saveAndFlush(libArtist);
        libArtist1 = libLabelRepository.saveAndFlush(libArtist1);


    }

    @Test
    public void shouldSaveTwoArtistWithSameNameInDifferentNetwork() {
        //given
        CorNetwork corNetworkSecond = factory.manufacturePojo(CorNetwork.class);
        corNetworkSecond.setId(null);
        corNetworkSecond = networkRepository.save(corNetworkSecond);

        /// /when
        LibLabel libArtist = factory.manufacturePojo(LibLabel.class);
        libArtist.setId(null);
        libArtist.name(TEST_NAME);
        libArtist.setNetwork(corNetwork);
        LibLabel libArtist1 = factory.manufacturePojo(LibLabel.class);
        libArtist1.setId(null);
        libArtist1.setName(TEST_NAME);
        libArtist1.setNetwork(corNetworkSecond);

        libArtist = libLabelRepository.saveAndFlush(libArtist);
        libArtist1 = libLabelRepository.saveAndFlush(libArtist1);


    }
}
