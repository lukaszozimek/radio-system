package io.protone.service.library;

import io.protone.ProtoneApp;
import io.protone.domain.CorNetwork;
import io.protone.domain.LibLibrary;
import io.protone.repository.cor.CorNetworkRepository;
import io.protone.repository.library.LibLibraryRepository;
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

import static org.junit.Assert.*;

/**
 * Created by lukaszozimek on 29/04/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class LibLibraryServiceTest {

    @Autowired
    private LibLibraryService libLibraryService;

    @Autowired
    private LibLibraryRepository libLibraryRepository;

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
    public void findLibraries() throws Exception {

        //when
        LibLibrary libLibrary = factory.manufacturePojo(LibLibrary.class);
        libLibrary.setNetwork(corNetwork);
        libLibrary = libLibraryRepository.save(libLibrary);

        //then
        List<LibLibrary> fetchedEntity = libLibraryService.findLibraries(corNetwork.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(1, fetchedEntity.size());
        assertEquals(libLibrary.getId(), fetchedEntity.get(0).getId());
        assertEquals(libLibrary.getNetwork(), fetchedEntity.get(0).getNetwork());

    }

    @Test
    public void findLibrary() throws Exception {

        //when
        LibLibrary libLibrary = factory.manufacturePojo(LibLibrary.class);
        libLibrary.setNetwork(corNetwork);
        libLibrary = libLibraryRepository.save(libLibrary);

        //then
        LibLibrary fetchedEntity = libLibraryService.findLibrary(corNetwork.getShortcut(), libLibrary.getShortcut());

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(libLibrary.getId(), fetchedEntity.getId());
        assertEquals(libLibrary.getNetwork(), fetchedEntity.getNetwork());

    }

    @Test
    public void deleteLibrary() throws Exception {

        //when
        LibLibrary libLibrary = factory.manufacturePojo(LibLibrary.class);
        libLibrary.setNetwork(corNetwork);
        libLibrary = libLibraryRepository.save(libLibrary);

        //then
        libLibraryService.deleteLibrary(libLibrary.getShortcut(), corNetwork.getShortcut());
        LibLibrary fetchedEntity = libLibraryService.findLibrary(corNetwork.getShortcut(), libLibrary.getShortcut());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void createOrUpdateLibrary() throws Exception {

        //when
        LibLibrary libLibrary = factory.manufacturePojo(LibLibrary.class);
        libLibrary.setNetwork(corNetwork);

        //then
        LibLibrary fetchedEntity = libLibraryService.createOrUpdateLibrary(libLibrary);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
        assertNotNull(fetchedEntity.getName(), libLibrary.getName());
        assertNotNull(fetchedEntity.getShortcut(), libLibrary.getShortcut());
        assertEquals(libLibrary.getNetwork(), fetchedEntity.getNetwork());
    }

}
