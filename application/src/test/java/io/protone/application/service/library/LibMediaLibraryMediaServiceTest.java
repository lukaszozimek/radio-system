package io.protone.application.service.library;


import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorOrganization;
import io.protone.core.repository.CorChannelRepository;
import io.protone.core.repository.CorOrganizationRepository;
import io.protone.core.s3.S3Client;
import io.protone.core.s3.exceptions.CreateBucketException;
import io.protone.library.domain.LibMediaLibrary;
import io.protone.library.repository.LibLibraryRepository;
import io.protone.library.service.LibLibraryMediaService;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.inject.Inject;
import javax.transaction.Transactional;

import static io.protone.application.util.TestConstans.*;
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
@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
public class LibMediaLibraryMediaServiceTest {

    private static final String TEST_SHORTNAME = "Tes";
    private static final String TEST_PREFIX = "C";
    @Autowired
    private LibLibraryMediaService libLibraryMediaService;

    @Autowired
    private LibLibraryRepository libLibraryRepository;

    @Autowired
    private CorOrganizationRepository corOrganizationRepository;

    @Mock
    private S3Client s3Client;

    private CorOrganization corOrganization;

    private CorChannel corChannel;

    private PodamFactory factory;
    @Inject
    private CorChannelRepository corChannelRepostiory;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        factory = new PodamFactoryImpl();
        corOrganization = new CorOrganization().shortcut(TEST_ORGANIZATION_SHORTCUT);
        corOrganization.setId(TEST_ORGANIZATION_ID);
        corChannel = new CorChannel().shortcut(TEST_CHANNEL_SHORTCUT);
        corChannel.setId(TEST_CHANNEL_ID);
        corChannel.setOrganization(corOrganization);
        when(s3Client.makeBucket(anyString(), anyString())).thenReturn(corOrganization.getShortcut() + MEDIA_ITEM + "testBucket");
        ReflectionTestUtils.setField(libLibraryMediaService, "s3Client", s3Client);

    }

    @Test
    public void findLibraries() throws Exception {


        //then
        Slice<LibMediaLibrary> fetchedEntity = libLibraryMediaService.findLibraries(corOrganization.getShortcut(), corChannel.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());

    }

    @Test
    public void findLibrary() throws Exception {

        //when
        LibMediaLibrary libMediaLibrary = factory.manufacturePojo(LibMediaLibrary.class);
        libMediaLibrary.setChannel(corChannel);
        libMediaLibrary = libLibraryRepository.save(libMediaLibrary);

        //then
        LibMediaLibrary fetchedEntity = libLibraryMediaService.findLibrary(corOrganization.getShortcut(), corChannel.getShortcut(), libMediaLibrary.getShortcut());

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(libMediaLibrary.getId(), fetchedEntity.getId());
        assertEquals(libMediaLibrary.getChannel(), fetchedEntity.getChannel());

    }

    @Test
    public void deleteLibrary() throws Exception {

        //when
        LibMediaLibrary libMediaLibrary = factory.manufacturePojo(LibMediaLibrary.class);
        libMediaLibrary.setChannel(corChannel);
        libMediaLibrary = libLibraryRepository.save(libMediaLibrary);

        //then
        libLibraryMediaService.deleteLibrary(libMediaLibrary.getShortcut(), corOrganization.getShortcut(), corChannel.getShortcut());
        LibMediaLibrary fetchedEntity = libLibraryMediaService.findLibrary(corOrganization.getShortcut(), corChannel.getShortcut(), libMediaLibrary.getShortcut());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void createOrUpdateLibrary() throws Exception {

        //when
        LibMediaLibrary libMediaLibrary = factory.manufacturePojo(LibMediaLibrary.class);
        libMediaLibrary.setChannel(corChannel);

        //then
        LibMediaLibrary fetchedEntity = libLibraryMediaService.createOrUpdateLibrary(libMediaLibrary);

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getId());
        assertNotNull(fetchedEntity.getCreatedBy());
        assertNotNull(fetchedEntity.getName(), libMediaLibrary.getName());
        assertNotNull(fetchedEntity.getShortcut(), libMediaLibrary.getShortcut());
        assertEquals(libMediaLibrary.getChannel(), fetchedEntity.getChannel());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveTwoLibraryWithSameShortNameInOneNetwork() throws CreateBucketException {

        /// /when
        LibMediaLibrary libMediaLibrary = factory.manufacturePojo(LibMediaLibrary.class);
        libMediaLibrary.setId(null);
        libMediaLibrary.setShortcut(TEST_SHORTNAME);
        libMediaLibrary.setChannel(corChannel);
        LibMediaLibrary libMediaLibrary1 = factory.manufacturePojo(LibMediaLibrary.class);
        libMediaLibrary1.setId(null);
        libMediaLibrary1.setShortcut(TEST_SHORTNAME);
        libMediaLibrary1.setChannel(corChannel);

        libMediaLibrary = libLibraryMediaService.createOrUpdateLibrary(libMediaLibrary);
        libMediaLibrary1 = libLibraryMediaService.createOrUpdateLibrary(libMediaLibrary1);


    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveTwoLibraryWithSameNameInOneNetwork() throws CreateBucketException {

        /// /when
        LibMediaLibrary libMediaLibrary = factory.manufacturePojo(LibMediaLibrary.class);
        libMediaLibrary.setId(null);
        libMediaLibrary.setName(TEST_SHORTNAME);
        libMediaLibrary.setChannel(corChannel);
        LibMediaLibrary libMediaLibrary1 = factory.manufacturePojo(LibMediaLibrary.class);
        libMediaLibrary1.setId(null);
        libMediaLibrary1.setName(TEST_SHORTNAME);
        libMediaLibrary1.setChannel(corChannel);

        libMediaLibrary = libLibraryMediaService.createOrUpdateLibrary(libMediaLibrary);
        libMediaLibrary1 = libLibraryMediaService.createOrUpdateLibrary(libMediaLibrary1);


    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveTwoLibraryWithSamePrefixInOneNetwork() throws CreateBucketException {

        /// /when
        LibMediaLibrary libMediaLibrary = factory.manufacturePojo(LibMediaLibrary.class);
        libMediaLibrary.setId(null);
        libMediaLibrary.prefix(TEST_PREFIX);
        libMediaLibrary.setChannel(corChannel);
        LibMediaLibrary libMediaLibrary1 = factory.manufacturePojo(LibMediaLibrary.class);
        libMediaLibrary1.setId(null);
        libMediaLibrary1.prefix(TEST_PREFIX);
        libMediaLibrary1.setChannel(corChannel);

        libMediaLibrary = libLibraryMediaService.createOrUpdateLibrary(libMediaLibrary);
        libMediaLibrary1 = libLibraryMediaService.createOrUpdateLibrary(libMediaLibrary1);


    }

    @Test
    public void shouldSaveTwoLibraryWithSameShortNameInDifferentOrganizations() throws CreateBucketException {
        //given
        CorOrganization corOrganization = factory.manufacturePojo(CorOrganization.class);
        corOrganization.setId(null);
        corOrganization = corOrganizationRepository.save(corOrganization);
        CorChannel corChannel1 = corChannelRepostiory.save(new CorChannel().shortcut("ran").name("random").organization(corOrganization));
        ///when
        LibMediaLibrary libMediaLibrary = factory.manufacturePojo(LibMediaLibrary.class);
        libMediaLibrary.setId(null);
        libMediaLibrary.shortcut(TEST_SHORTNAME);
        libMediaLibrary.setChannel(corChannel);
        LibMediaLibrary libMediaLibrary1 = factory.manufacturePojo(LibMediaLibrary.class);
        libMediaLibrary1.setId(null);
        libMediaLibrary1.shortcut(TEST_SHORTNAME);
        libMediaLibrary1.setChannel(corChannel1);

        //then
        libMediaLibrary = libLibraryMediaService.createOrUpdateLibrary(libMediaLibrary);
        libMediaLibrary1 = libLibraryMediaService.createOrUpdateLibrary(libMediaLibrary1);

    }

}
