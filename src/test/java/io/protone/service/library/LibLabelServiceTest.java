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
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 05/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class LibLabelServiceTest {
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
}
