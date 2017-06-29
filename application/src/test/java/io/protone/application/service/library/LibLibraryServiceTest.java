package io.protone.application.service.library;


import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorNetwork;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.library.domain.LibLibrary;
import io.protone.library.repository.LibLibraryRepository;
import io.protone.library.service.LibLibraryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
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

    private static final String TEST_SHORTNAME = "Tes";
    private static final String TEST_PREFIX = "C";
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
        assertNotNull(fetchedEntity.getCreatedBy());
        assertNotNull(fetchedEntity.getName(), libLibrary.getName());
        assertNotNull(fetchedEntity.getShortcut(), libLibrary.getShortcut());
        assertEquals(libLibrary.getNetwork(), fetchedEntity.getNetwork());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveTwoLibraryWithSameShortNameInOneNetwork() {

        /// /when
        LibLibrary libLibrary = factory.manufacturePojo(LibLibrary.class);
        libLibrary.setId(null);
        libLibrary.setShortcut(TEST_SHORTNAME);
        libLibrary.setNetwork(corNetwork);
        LibLibrary libLibrary1 = factory.manufacturePojo(LibLibrary.class);
        libLibrary1.setId(null);
        libLibrary1.setShortcut(TEST_SHORTNAME);
        libLibrary1.setNetwork(corNetwork);

        libLibrary = libLibraryService.createOrUpdateLibrary(libLibrary);
        libLibrary1 = libLibraryService.createOrUpdateLibrary(libLibrary1);


    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveTwoLibraryWithSameNameInOneNetwork() {

        /// /when
        LibLibrary libLibrary = factory.manufacturePojo(LibLibrary.class);
        libLibrary.setId(null);
        libLibrary.setName(TEST_SHORTNAME);
        libLibrary.setNetwork(corNetwork);
        LibLibrary libLibrary1 = factory.manufacturePojo(LibLibrary.class);
        libLibrary1.setId(null);
        libLibrary1.setName(TEST_SHORTNAME);
        libLibrary1.setNetwork(corNetwork);

        libLibrary = libLibraryService.createOrUpdateLibrary(libLibrary);
        libLibrary1 = libLibraryService.createOrUpdateLibrary(libLibrary1);


    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveTwoLibraryWithSamePrefixInOneNetwork() {

        /// /when
        LibLibrary libLibrary = factory.manufacturePojo(LibLibrary.class);
        libLibrary.setId(null);
        libLibrary.prefix(TEST_PREFIX);
        libLibrary.setNetwork(corNetwork);
        LibLibrary libLibrary1 = factory.manufacturePojo(LibLibrary.class);
        libLibrary1.setId(null);
        libLibrary1.prefix(TEST_PREFIX);
        libLibrary1.setNetwork(corNetwork);

        libLibrary = libLibraryService.createOrUpdateLibrary(libLibrary);
        libLibrary1 = libLibraryService.createOrUpdateLibrary(libLibrary1);


    }

    @Test
    public void shouldSaveTwoLibraryWithSameShortNameInDifferentNetwork() {
        //given
        CorNetwork corNetworkSecond = factory.manufacturePojo(CorNetwork.class);
        corNetworkSecond.setId(null);
        corNetworkSecond = corNetworkRepository.save(corNetworkSecond);

        ///when
        LibLibrary libLibrary = factory.manufacturePojo(LibLibrary.class);
        libLibrary.setId(null);
        libLibrary.shortcut(TEST_SHORTNAME);
        libLibrary.setNetwork(corNetwork);
        LibLibrary libLibrary1 = factory.manufacturePojo(LibLibrary.class);
        libLibrary1.setId(null);
        libLibrary1.shortcut(TEST_SHORTNAME);
        libLibrary1.setNetwork(corNetworkSecond);

        //then
        libLibrary = libLibraryService.createOrUpdateLibrary(libLibrary);
        libLibrary1 = libLibraryService.createOrUpdateLibrary(libLibrary1);

    }

}
