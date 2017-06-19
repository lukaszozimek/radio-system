package io.protone.service.library;

import io.protone.ProtoneApp;
import io.protone.config.s3.S3Client;
import io.protone.config.s3.exceptions.CreateBucketException;
import io.protone.domain.CorNetwork;
import io.protone.domain.LibLibrary;
import io.protone.repository.cor.CorNetworkRepository;
import io.protone.repository.library.LibLibraryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

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

    @Mock
    private S3Client s3Client;

    private CorNetwork corNetwork;

    private PodamFactory factory;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        factory = new PodamFactoryImpl();
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corNetwork.setId(null);
        corNetwork = corNetworkRepository.saveAndFlush(corNetwork);
        when(s3Client.makeBucket(anyString())).thenReturn("testBucket");
        ReflectionTestUtils.setField(libLibraryService, "s3Client", s3Client);

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

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveTwoLibraryWithSameShortNameInOneNetwork() throws CreateBucketException {

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
    public void shouldNotSaveTwoLibraryWithSameNameInOneNetwork() throws CreateBucketException {

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
    public void shouldNotSaveTwoLibraryWithSamePrefixInOneNetwork() throws CreateBucketException {

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
    public void shouldSaveTwoLibraryWithSameShortNameInDifferentNetwork() throws CreateBucketException {
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
