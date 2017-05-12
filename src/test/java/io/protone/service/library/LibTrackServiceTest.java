package io.protone.service.library;

import io.protone.ProtoneApp;
import io.protone.domain.CorNetwork;
import io.protone.domain.LibAlbum;
import io.protone.domain.LibTrack;
import io.protone.repository.cor.CorNetworkRepository;
import io.protone.repository.library.LibAlbumRepository;
import io.protone.repository.library.LibTrackRepository;
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
        assertNotNull(saveLibTrack.get().getId());

    }

    @Test
    public void shoudlNotSaveLibTrack() throws Exception {

        Optional<LibTrack> saveLibTrack = libTrackService.saveLibTrack(null);

        assertFalse(saveLibTrack.isPresent());
    }
}
