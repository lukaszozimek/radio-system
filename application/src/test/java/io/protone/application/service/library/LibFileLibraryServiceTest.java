package io.protone.application.service.library;


import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorOrganization;
import io.protone.core.repository.CorChannelRepository;
import io.protone.core.repository.CorOrganizationRepository;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

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
public class LibFileLibraryServiceTest {

    private static final String TEST_SHORTNAME = "Tes";
    private static final String TEST_PREFIX = "C";
    @Autowired
    private LibFileLibraryService libFileLibraryService;

    @Autowired
    private LibFileLibraryRepository libFileLibraryRepository;

    @Autowired
    private CorChannelRepository corChannelRepository;

    @Autowired
    private CorOrganizationRepository corOrganizationRepository;

    @Mock
    private S3Client s3Client;

    private CorChannel corChannel;

    private CorOrganization corOrganization;
    private PodamFactory factory;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        factory = new PodamFactoryImpl();
        corOrganization = new CorOrganization().shortcut(TEST_ORGANIZATION_SHORTCUT);
        corOrganization.setId(TEST_ORGANIZATION_ID);
        corChannel = new CorChannel().shortcut(TEST_CHANNEL_SHORTCUT);
        corChannel.setId(TEST_CHANNEL_ID);
        corChannel.setOrganization(corOrganization);
        when(s3Client.makeBucket(anyString(), anyString())).thenReturn(corOrganization.getShortcut() + corChannel.getShortcut() + MEDIA_ITEM + "testBucket");
        ReflectionTestUtils.setField(libFileLibraryService, "s3Client", s3Client);

    }

    @Test
    public void findLibraries() throws Exception {

        //then
        Slice<LibFileLibrary> fetchedEntity = libFileLibraryService.findLibrariesByChannel(corOrganization.getShortcut(), corChannel.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());

    }

    @Test
    public void findLibrary() throws Exception {

        //when
        LibFileLibrary libMediaLibrary = factory.manufacturePojo(LibFileLibrary.class);
        libMediaLibrary.setChannel(corChannel);
        libMediaLibrary = libFileLibraryRepository.save(libMediaLibrary);

        //then
        LibFileLibrary fetchedEntity = libFileLibraryService.findLibraryByChannel(corOrganization.getShortcut(), corChannel.getShortcut(), libMediaLibrary.getShortcut());

        //assert
        assertNotNull(fetchedEntity);
        assertEquals(libMediaLibrary.getId(), fetchedEntity.getId());
        assertEquals(libMediaLibrary.getChannel(), fetchedEntity.getChannel());

    }

    @Test
    public void deleteLibrary() throws Exception {

        //when
        LibFileLibrary libMediaLibrary = factory.manufacturePojo(LibFileLibrary.class);
        libMediaLibrary.setChannel(corChannel);
        libMediaLibrary = libFileLibraryRepository.save(libMediaLibrary);

        //then
        libFileLibraryService.deleteLibrary(libMediaLibrary.getShortcut(), corChannel.getShortcut(), corOrganization.getShortcut());
        LibFileLibrary fetchedEntity = libFileLibraryService.findLibraryByChannel(corOrganization.getShortcut(), corChannel.getShortcut(), libMediaLibrary.getShortcut());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void createOrUpdateLibrary() throws Exception {

        //when
        LibFileLibrary libMediaLibrary = factory.manufacturePojo(LibFileLibrary.class);
        libMediaLibrary.setChannel(corChannel);

        //then
        LibFileLibrary fetchedEntity = libFileLibraryService.createOrUpdateLibrary(libMediaLibrary);

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
        LibFileLibrary libFileLibrary = factory.manufacturePojo(LibFileLibrary.class);
        libFileLibrary.setId(null);
        libFileLibrary.setShortcut(TEST_SHORTNAME);
        libFileLibrary.setChannel(corChannel);
        LibFileLibrary mediaLibrary1 = factory.manufacturePojo(LibFileLibrary.class);
        mediaLibrary1.setId(null);
        mediaLibrary1.setShortcut(TEST_SHORTNAME);
        mediaLibrary1.setChannel(corChannel);

        libFileLibrary = libFileLibraryService.createOrUpdateLibrary(libFileLibrary);
        mediaLibrary1 = libFileLibraryService.createOrUpdateLibrary(mediaLibrary1);


    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveTwoLibraryWithSameNameInOneNetwork() throws CreateBucketException {


        /// /when
        LibFileLibrary libMediaLibrary = factory.manufacturePojo(LibFileLibrary.class);
        libMediaLibrary.setId(null);
        libMediaLibrary.setName(TEST_SHORTNAME);
        libMediaLibrary.setChannel(corChannel);
        LibFileLibrary libMediaLibrary1 = factory.manufacturePojo(LibFileLibrary.class);
        libMediaLibrary1.setId(null);
        libMediaLibrary1.setName(TEST_SHORTNAME);
        libMediaLibrary1.setChannel(corChannel);

        libMediaLibrary = libFileLibraryService.createOrUpdateLibrary(libMediaLibrary);
        libMediaLibrary1 = libFileLibraryService.createOrUpdateLibrary(libMediaLibrary1);


    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveTwoLibraryWithSamePrefixInOneNetwork() throws CreateBucketException {

        /// /when
        LibFileLibrary libMediaLibrary = factory.manufacturePojo(LibFileLibrary.class);
        libMediaLibrary.setId(null);
        libMediaLibrary.prefix(TEST_PREFIX);
        libMediaLibrary.setChannel(corChannel);
        LibFileLibrary libMediaLibrary1 = factory.manufacturePojo(LibFileLibrary.class);
        libMediaLibrary1.setId(null);
        libMediaLibrary1.prefix(TEST_PREFIX);
        libMediaLibrary1.setChannel(corChannel);

        libMediaLibrary = libFileLibraryService.createOrUpdateLibrary(libMediaLibrary);
        libMediaLibrary1 = libFileLibraryService.createOrUpdateLibrary(libMediaLibrary1);


    }

    @Test
    public void shouldSaveTwoLibraryWithSameShortNameInDifferentNetwork() throws CreateBucketException {
        //given
        CorOrganization corOrganizationkSecond = factory.manufacturePojo(CorOrganization.class);
        corOrganizationkSecond.setId(null);
        corOrganizationkSecond = corOrganizationRepository.saveAndFlush(corOrganizationkSecond);
        CorChannel corChannelSecond = factory.manufacturePojo(CorChannel.class);
        corChannelSecond.setId(null);
        corChannelSecond.setOrganization(corOrganizationkSecond);

        corChannelSecond = corChannelRepository.save(corChannelSecond);

        ///when
        LibFileLibrary libFileLibrary = factory.manufacturePojo(LibFileLibrary.class);
        libFileLibrary.setId(null);
        libFileLibrary.shortcut(TEST_SHORTNAME);
        libFileLibrary.setChannel(corChannel);

        LibFileLibrary libMediaLibrary1 = factory.manufacturePojo(LibFileLibrary.class);
        libMediaLibrary1.setId(null);
        libMediaLibrary1.shortcut(TEST_SHORTNAME);
        libMediaLibrary1.setChannel(corChannelSecond);

        //then
        libFileLibrary = libFileLibraryService.createOrUpdateLibrary(libFileLibrary);
        libMediaLibrary1 = libFileLibraryService.createOrUpdateLibrary(libMediaLibrary1);

    }

}
