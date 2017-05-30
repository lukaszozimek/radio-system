package io.protone.service.library;

import io.protone.ProtoneApp;
import io.protone.domain.CorNetwork;
import io.protone.domain.LibLibrary;
import io.protone.domain.LibMediaItem;
import io.protone.repository.cor.CorNetworkRepository;
import io.protone.repository.library.LibLibraryRepository;
import io.protone.repository.library.LibMediaItemRepository;
import io.protone.service.library.LibItemService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by lukaszozimek on 30/04/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class LibItemServiceTest {
    @Autowired
    private LibItemService libItemService;

    @Autowired
    private CorNetworkRepository corNetworkRepository;

    @Autowired
    private LibMediaItemRepository libMediaItemRepository;

    @Autowired
    private LibLibraryRepository libLibraryRepository;

    private CorNetwork corNetwork;

    private PodamFactory factory;

    LibLibrary libLibrary;

    @Before
    public void setUp() throws Exception {
        factory = new PodamFactoryImpl();
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corNetwork.setId(null);
        corNetwork = corNetworkRepository.saveAndFlush(corNetwork);
        libLibrary = factory.manufacturePojo(LibLibrary.class);
        libLibrary.setNetwork(corNetwork);
        libLibrary = libLibraryRepository.save(libLibrary);
    }

    @Test
    public void findMediaItems() throws Exception {

        //when
        LibMediaItem libMediaItem = factory.manufacturePojo(LibMediaItem.class);
        libMediaItem.setLibrary(libLibrary);
        libMediaItem.setNetwork(corNetwork);
        libMediaItem = libMediaItemRepository.save(libMediaItem);

        //then
        List<LibMediaItem> fetchedEntity = libItemService.getMediaItems(corNetwork.getShortcut(), libLibrary.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(1, fetchedEntity.size());
        assertEquals(libMediaItem.getId(), fetchedEntity.get(0).getId());
        assertEquals(libMediaItem.getNetwork(), fetchedEntity.get(0).getNetwork());

    }

    @Test
    public void findMediaItem() throws Exception {

        //when
        LibMediaItem libMediaItem = factory.manufacturePojo(LibMediaItem.class);
        libMediaItem.setLibrary(libLibrary);
        libMediaItem.setNetwork(corNetwork);
        libMediaItem = libMediaItemRepository.save(libMediaItem);

        //then
        LibMediaItem fetchedEntity = libItemService.getMediaItem(corNetwork.getShortcut(), libLibrary.getShortcut(), libMediaItem.getIdx());

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(libLibrary.getId(), fetchedEntity.getId());
        assertEquals(libLibrary.getNetwork(), fetchedEntity.getNetwork());

    }

    @Test
    public void deleteMediaItem() throws Exception {

        //when
        LibMediaItem libMediaItem = factory.manufacturePojo(LibMediaItem.class);
        libMediaItem.setLibrary(libLibrary);
        libMediaItem.setNetwork(corNetwork);
        libMediaItem = libMediaItemRepository.save(libMediaItem);

        //then
        libItemService.deleteItem(libLibrary.getShortcut(), corNetwork.getShortcut(), libMediaItem.getIdx());
        LibMediaItem fetchedEntity = libItemService.getMediaItem(corNetwork.getShortcut(), libLibrary.getShortcut(), libMediaItem.getIdx());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void upload() throws Exception {

    }

    @Test
    public void download() throws Exception {

    }


}
