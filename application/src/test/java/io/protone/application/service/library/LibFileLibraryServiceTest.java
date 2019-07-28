package io.protone.application.service.library;


import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorNetwork;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.core.s3.S3Client;
import io.protone.core.s3.exceptions.CreateBucketException;
import io.protone.library.domain.LibFileLibrary;
import io.protone.library.repository.LibFileLibraryRepository;
import io.protone.library.service.LibFileLibraryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;

import static io.protone.core.constans.MinioFoldersConstants.MEDIA_ITEM;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by lukaszozimek on 29/04/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class LibFileLibraryServiceTest {

    private static final String TEST_SHORTNAME = "Tes";
    private static final String TEST_PREFIX = "C";
    @Autowired
    private LibFileLibraryService libFileLibraryService;

    @Autowired
    private LibFileLibraryRepository libFileLibraryRepository;

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
        when(s3Client.makeBucket(anyString(), anyString())).thenReturn(corNetwork.getShortcut() + MEDIA_ITEM + "testBucket");
        ReflectionTestUtils.setField(libFileLibraryService, "s3Client", s3Client);

    }

    @Test
    public void findLibraries() throws Exception {

        //when
        LibFileLibrary libMediaLibrary = factory.manufacturePojo(LibFileLibrary.class);
        libMediaLibrary.setNetwork(corNetwork);
        libMediaLibrary = libFileLibraryRepository.save(libMediaLibrary);

        //then
        Slice<LibFileLibrary> fetchedEntity = libFileLibraryService.findLibraries(corNetwork.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(libMediaLibrary.getId(), fetchedEntity.getContent().get(0).getId());
        assertEquals(libMediaLibrary.getNetwork(), fetchedEntity.getContent().get(0).getNetwork());

    }

    @Test
    public void findLibrary() throws Exception {

        //when
        LibFileLibrary libMediaLibrary = factory.manufacturePojo(LibFileLibrary.class);
        libMediaLibrary.setNetwork(corNetwork);
        libMediaLibrary = libFileLibraryRepository.save(libMediaLibrary);

        //then
        LibFileLibrary fetchedEntity = libFileLibraryService.findLibrary(corNetwork.getShortcut(), libMediaLibrary.getShortcut());

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(libMediaLibrary.getId(), fetchedEntity.getId());
        assertEquals(libMediaLibrary.getNetwork(), fetchedEntity.getNetwork());

    }

    @Test
    public void deleteLibrary() throws Exception {

        //when
        LibFileLibrary libMediaLibrary = factory.manufacturePojo(LibFileLibrary.class);
        libMediaLibrary.setNetwork(corNetwork);
        libMediaLibrary = libFileLibraryRepository.save(libMediaLibrary);

        //then
        libFileLibraryService.deleteLibrary(libMediaLibrary.getShortcut(), corNetwork.getShortcut());
        LibFileLibrary fetchedEntity = libFileLibraryService.findLibrary(corNetwork.getShortcut(), libMediaLibrary.getShortcut());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void createOrUpdateLibrary() throws Exception {

        //when
        LibFileLibrary libMediaLibrary = factory.manufacturePojo(LibFileLibrary.class);
        libMediaLibrary.setNetwork(corNetwork);

        //then
        LibFileLibrary fetchedEntity = libFileLibraryService.createOrUpdateLibrary(libMediaLibrary);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
        assertNotNull(fetchedEntity.getCreatedBy());
        assertNotNull(fetchedEntity.getName(), libMediaLibrary.getName());
        assertNotNull(fetchedEntity.getShortcut(), libMediaLibrary.getShortcut());
        assertEquals(libMediaLibrary.getNetwork(), fetchedEntity.getNetwork());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveTwoLibraryWithSameShortNameInOneNetwork() throws CreateBucketException {

        /// /when
        LibFileLibrary libFileLibrary = factory.manufacturePojo(LibFileLibrary.class);
        libFileLibrary.setId(null);
        libFileLibrary.setShortcut(TEST_SHORTNAME);
        libFileLibrary.setNetwork(corNetwork);
        LibFileLibrary mediaLibrary1 = factory.manufacturePojo(LibFileLibrary.class);
        mediaLibrary1.setId(null);
        mediaLibrary1.setShortcut(TEST_SHORTNAME);
        mediaLibrary1.setNetwork(corNetwork);

        libFileLibrary = libFileLibraryService.createOrUpdateLibrary(libFileLibrary);
        mediaLibrary1 = libFileLibraryService.createOrUpdateLibrary(mediaLibrary1);


    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveTwoLibraryWithSameNameInOneNetwork() throws CreateBucketException {

        /// /when
        LibFileLibrary libMediaLibrary = factory.manufacturePojo(LibFileLibrary.class);
        libMediaLibrary.setId(null);
        libMediaLibrary.setName(TEST_SHORTNAME);
        libMediaLibrary.setNetwork(corNetwork);
        LibFileLibrary libMediaLibrary1 = factory.manufacturePojo(LibFileLibrary.class);
        libMediaLibrary1.setId(null);
        libMediaLibrary1.setName(TEST_SHORTNAME);
        libMediaLibrary1.setNetwork(corNetwork);

        libMediaLibrary = libFileLibraryService.createOrUpdateLibrary(libMediaLibrary);
        libMediaLibrary1 = libFileLibraryService.createOrUpdateLibrary(libMediaLibrary1);


    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveTwoLibraryWithSamePrefixInOneNetwork() throws CreateBucketException {

        /// /when
        LibFileLibrary libMediaLibrary = factory.manufacturePojo(LibFileLibrary.class);
        libMediaLibrary.setId(null);
        libMediaLibrary.prefix(TEST_PREFIX);
        libMediaLibrary.setNetwork(corNetwork);
        LibFileLibrary libMediaLibrary1 = factory.manufacturePojo(LibFileLibrary.class);
        libMediaLibrary1.setId(null);
        libMediaLibrary1.prefix(TEST_PREFIX);
        libMediaLibrary1.setNetwork(corNetwork);

        libMediaLibrary = libFileLibraryService.createOrUpdateLibrary(libMediaLibrary);
        libMediaLibrary1 = libFileLibraryService.createOrUpdateLibrary(libMediaLibrary1);


    }

    @Test
    public void shouldSaveTwoLibraryWithSameShortNameInDifferentNetwork() throws CreateBucketException {
        //given
        CorNetwork corNetworkSecond = factory.manufacturePojo(CorNetwork.class);
        corNetworkSecond.setId(null);
        corNetworkSecond = corNetworkRepository.save(corNetworkSecond);

        ///when
        LibFileLibrary libFileLibrary = factory.manufacturePojo(LibFileLibrary.class);
        libFileLibrary.setId(null);
        libFileLibrary.shortcut(TEST_SHORTNAME);
        libFileLibrary.setNetwork(corNetwork);
        LibFileLibrary libMediaLibrary1 = factory.manufacturePojo(LibFileLibrary.class);
        libMediaLibrary1.setId(null);
        libMediaLibrary1.shortcut(TEST_SHORTNAME);
        libMediaLibrary1.setNetwork(corNetworkSecond);

        //then
        libFileLibrary = libFileLibraryService.createOrUpdateLibrary(libFileLibrary);
        libMediaLibrary1 = libFileLibraryService.createOrUpdateLibrary(libMediaLibrary1);

    }

}
