package io.protone.service.library;

import io.protone.ProtoneApp;
import io.protone.domain.CorNetwork;
import io.protone.domain.LibMarker;
import io.protone.domain.LibMediaItem;
import io.protone.domain.enumeration.LibMarkerTypeEnum;
import io.protone.repository.cor.CorNetworkRepository;
import io.protone.repository.library.LibMediaItemRepository;
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

import static org.junit.Assert.*;

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
        LibMediaItem libMediaItem = factory.manufacturePojo(LibMediaItem.class);
        libMediaItemRepository.save(libMediaItem);

        //then
        LibMarker libMarker = libMarkerService.saveLibMarker(SAMPLE_MARKER_NAME, SAMPLE_MARKER_START_TIME, libMediaItem);

        //assert
        assertNotNull(libMarker);
        assertNotNull(libMarker.getId());
        assertEquals(SAMPLE_MARKER_NAME, libMarker.getName());
        assertEquals(SAMPLE_MARKER_START_TIME, libMarker.getStartTime());
        assertEquals(libMediaItem, libMarker.getMediaItem());
    }

    @Test
    public void saveLibMarkers() throws Exception {
        //when
        LibMediaItem libMediaItem = factory.manufacturePojo(LibMediaItem.class);
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
