package io.protone.application.service.library;


import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorNetwork;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.library.domain.LibAlbum;
import io.protone.library.domain.LibTrack;
import io.protone.library.repository.LibAlbumRepository;
import io.protone.library.repository.LibTrackRepository;
import io.protone.library.service.LibTrackService;
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Created by lukaszozimek on 05/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class LibTrackServiceTest {
    @Autowired
    private LibTrackService libTrackService;

    @Autowired
    private LibTrackRepository libTrackRepository;

    @Autowired
    private CorNetworkRepository networkRepository;

    @Autowired
    private LibAlbumRepository libAlbumRepository;

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
        LibAlbum libAlbum = factory.manufacturePojo(LibAlbum.class).network(corNetwork);
        libAlbum.setArtist(null);
        libAlbum.setCover(null);
        libAlbum.setLabel(null);
        libAlbum = libAlbumRepository.save(libAlbum);
        LibTrack libTrack = factory.manufacturePojo(LibTrack.class);
        libTrack.network(corNetwork);
        libTrack.setId(null);
        libTrack.album(libAlbum);
        libTrack.artist(null);

        Optional<LibTrack> saveLibTrack = libTrackService.saveLibTrack(libTrack);

        assertNotNull(saveLibTrack.get());
        assertNotNull(saveLibTrack.get().getCreatedBy());
        assertNotNull(saveLibTrack.get().getId());

    }

    @Test
    public void shoudlNotSaveLibTrack() throws Exception {

        Optional<LibTrack> saveLibTrack = libTrackService.saveLibTrack(null);

        assertFalse(saveLibTrack.isPresent());
    }
}
