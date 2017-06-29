package io.protone.application.service.library;


import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorNetwork;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.library.domain.LibLibrary;
import io.protone.library.domain.LibMarker;
import io.protone.library.domain.LibMediaItem;
import io.protone.library.domain.enumeration.LibMarkerTypeEnum;
import io.protone.library.repository.LibMediaItemRepository;
import io.protone.library.service.LibMarkerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by lukaszozimek on 29/04/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class LibMarkerServiceTest {
    private static final String SAMPLE_MARKER_NAME = "xxx";
    private static final Long SAMPLE_MARKER_START_TIME = 124L;
    @Autowired
    private LibMarkerService libMarkerService;

    @Autowired
    private CorNetworkRepository corNetworkRepository;

    @Autowired
    private LibMediaItemRepository libMediaItemRepository;

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
    public void shouldSaveMarker() throws Exception {
        //when
        LibLibrary libLibrary = new LibLibrary();
        libLibrary.setId(1L);
        libLibrary.setShortcut("tes");
        LibMediaItem libMediaItem = factory.manufacturePojo(LibMediaItem.class);
        libMediaItem.setNetwork(corNetwork);
        libMediaItem.setLibrary(libLibrary);
        libMediaItemRepository.save(libMediaItem);

        //then
        LibMarker libMarker = libMarkerService.saveLibMarker(SAMPLE_MARKER_NAME, SAMPLE_MARKER_START_TIME, libMediaItem);

        //assert
        assertNotNull(libMarker);
        assertNotNull(libMarker.getId());
        assertNotNull(libMarker.getCreatedBy());
        assertEquals(SAMPLE_MARKER_NAME, libMarker.getName());
        assertEquals(SAMPLE_MARKER_START_TIME, libMarker.getStartTime());
        assertEquals(libMediaItem, libMarker.getMediaItem());
    }

    @Test
    public void saveLibMarkers() throws Exception {
        //when
        LibLibrary libLibrary = new LibLibrary();
        libLibrary.setId(1L);
        libLibrary.setShortcut("tes");
        LibMediaItem libMediaItem = factory.manufacturePojo(LibMediaItem.class);
        libMediaItem.setNetwork(corNetwork);
        libMediaItem.setLibrary(libLibrary);
        libMediaItemRepository.save(libMediaItem);
        LibMarker libMarker = new LibMarker().markerType(LibMarkerTypeEnum.MT_BASIC).mediaItem(libMediaItem).startTime(SAMPLE_MARKER_START_TIME).name(SAMPLE_MARKER_NAME);
        Set<LibMarker> libMarkerSet = Sets.newSet(libMarker);
        //then
        Set<LibMarker> savedLibMarkers = libMarkerService.saveLibMarkers(libMarkerSet).orElse(null);

        //assert
        assertNotNull(savedLibMarkers);
        assertEquals(1, savedLibMarkers.size());
        assertNotNull(savedLibMarkers.stream().findFirst().get().getId());
        assertEquals(SAMPLE_MARKER_NAME, savedLibMarkers.stream().findFirst().get().getName());
        assertEquals(SAMPLE_MARKER_START_TIME, savedLibMarkers.stream().findFirst().get().getStartTime());
        assertEquals(libMediaItem, savedLibMarkers.stream().findFirst().get().getMediaItem());

    }

}
