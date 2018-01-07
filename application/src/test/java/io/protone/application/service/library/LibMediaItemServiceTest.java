package io.protone.application.service.library;

import com.google.common.collect.Sets;
import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorAuthority;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorOrganization;
import io.protone.core.domain.CorUser;
import io.protone.core.repository.CorUserRepository;
import io.protone.core.s3.S3Client;
import io.protone.core.service.CorUserService;
import io.protone.library.domain.LibMediaItem;
import io.protone.library.domain.LibMediaLibrary;
import io.protone.library.domain.enumeration.LibItemTypeEnum;
import io.protone.library.repository.LibLibraryRepository;
import io.protone.library.repository.LibMediaItemRepository;
import io.protone.library.service.LibMediaItemService;
import io.protone.library.service.file.LibFileService;
import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static io.protone.application.util.TestConstans.*;
import static io.protone.core.security.AuthoritiesConstants.ADMIN;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by lukaszozimek on 30/04/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class LibMediaItemServiceTest {
    @Autowired
    private LibMediaItemService libMediaItemService;

    @Autowired
    private LibMediaItemRepository libMediaItemRepository;

    @Autowired
    private LibLibraryRepository libLibraryRepository;

    @Autowired
    @Qualifier("libAudioFileService")
    private LibFileService audioFileService;

    @Autowired
    @Qualifier("libVideoFileService")
    private LibFileService videoFileService;

    @Autowired
    @Qualifier("libImageFileService")
    private LibFileService imageFileService;

    @Autowired
    @Qualifier("libDocumentFileService")
    private LibFileService libDocumentFileService;

    @Autowired
    private CorUserRepository corUserRepository;

    @Mock
    private S3Client s3Client;

    @Mock
    private CorUserService corUserService;

    private CorOrganization corOrganization;

    private CorChannel corChannel;

    private PodamFactory factory;

    private LibMediaLibrary libMediaLibrary;

    private CorUser corUser;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        factory = new PodamFactoryImpl();
        corOrganization = new CorOrganization().shortcut(TEST_ORGANIZATION_SHORTCUT);
        corOrganization.setId(TEST_ORGANIZATION_ID);
        corChannel = new CorChannel().shortcut(TEST_CHANNEL_SHORTCUT);
        corChannel.setId(TEST_CHANNEL_ID);

        libMediaLibrary = factory.manufacturePojo(LibMediaLibrary.class);
        libMediaLibrary.setChannel(corChannel);
        libMediaLibrary = libLibraryRepository.save(libMediaLibrary);
        corUser = new CorUser();
        corUser.setId(3L);
        corUser.setLogin("admin");
        corUser.setOrganization(corOrganization);
        corUser.setAuthorities(Sets.newHashSet(new CorAuthority().name(ADMIN)));

        doNothing().when(s3Client).delete(anyString(), anyString(), anyObject());
        doNothing().when(s3Client).upload(anyString(), anyObject(), anyString());
        when(corUserService.getUserWithAuthoritiesByLogin(anyString())).thenReturn(Optional.of(corUser));

        ReflectionTestUtils.setField(audioFileService, "s3Client", s3Client);
        ReflectionTestUtils.setField(videoFileService, "s3Client", s3Client);
        ReflectionTestUtils.setField(imageFileService, "s3Client", s3Client);
        ReflectionTestUtils.setField(libDocumentFileService, "s3Client", s3Client);
        ReflectionTestUtils.setField(libMediaItemService, "audioFileService", audioFileService);
        ReflectionTestUtils.setField(libMediaItemService, "videoFileService", videoFileService);
        ReflectionTestUtils.setField(libMediaItemService, "imageFileService", imageFileService);
        ReflectionTestUtils.setField(libMediaItemService, "libDocumentFileService", libDocumentFileService);

    }

    @Test
    public void findMediaItems() throws Exception {

        //when
        LibMediaItem libMediaItem = factory.manufacturePojo(LibMediaItem.class);
        libMediaItem.setLibrary(libMediaLibrary);
        libMediaItem.setChannel(corChannel);
        libMediaItem = libMediaItemRepository.save(libMediaItem);

        //then
        Slice<LibMediaItem> fetchedEntity = libMediaItemService.getMediaItems(corOrganization.getShortcut(), corChannel.getShortcut(), libMediaLibrary.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(libMediaItem.getId(), fetchedEntity.getContent().get(0).getId());
        assertEquals(libMediaItem.getChannel(), fetchedEntity.getContent().get(0).getChannel());

    }

    @Test
    public void findMediaItem() throws Exception {

        //when
        LibMediaItem libMediaItem = factory.manufacturePojo(LibMediaItem.class);
        libMediaItem.setLibrary(libMediaLibrary);
        libMediaItem.setChannel(corChannel);
        libMediaItem = libMediaItemRepository.save(libMediaItem);

        //then
        LibMediaItem fetchedEntity = libMediaItemService.getMediaItem(corOrganization.getShortcut(), corChannel.getShortcut(), libMediaLibrary.getShortcut(), libMediaItem.getIdx());

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getCreatedBy());
        assertEquals(libMediaItem.getId(), fetchedEntity.getId());
        assertEquals(libMediaItem.getChannel(), fetchedEntity.getChannel());

    }

    @Test
    public void deleteMediaItem() throws Exception {
        //when
        LibMediaItem libMediaItem = factory.manufacturePojo(LibMediaItem.class);
        libMediaItem.setItemType(LibItemTypeEnum.IT_AUDIO);
        libMediaItem.setLibrary(libMediaLibrary);
        libMediaItem.setChannel(corChannel);
        libMediaItem = libMediaItemRepository.save(libMediaItem);

        //then
        libMediaItemService.deleteItem(corOrganization.getShortcut(), corChannel.getShortcut(), libMediaLibrary.getShortcut(), libMediaItem.getIdx());
        LibMediaItem fetchedEntity = libMediaItemService.getMediaItem(corOrganization.getShortcut(), corChannel.getShortcut(), libMediaLibrary.getShortcut(), libMediaItem.getIdx());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void shouldUploadViedo() throws Exception {
        //when
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/video/sample-video.mp4");
        MultipartFile multipartFile = new MockMultipartFile("testFile", inputStream);
        MultipartFile[] multipartFiles = Arrays.array(multipartFile);
        //then
        List<LibMediaItem> libMediaItems = libMediaItemService.upload(corOrganization.getShortcut(), corChannel.getShortcut(), libMediaLibrary.getShortcut(), multipartFiles);

        //assert
        assertNotNull(libMediaItems);
        assertEquals(1, libMediaItems.size());
    }

    @Test
    public void shouldUploadAudio() throws Exception {
        //when
        ReflectionTestUtils.setField(libMediaItemService, "audioFileService", audioFileService);
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/audio/sample_mp3.mp3");
        MultipartFile multipartFile = new MockMultipartFile("testFile", inputStream);
        MultipartFile[] multipartFiles = Arrays.array(multipartFile);

        //then

        List<LibMediaItem> libMediaItems = libMediaItemService.upload(corOrganization.getShortcut(), corChannel.getShortcut(), libMediaLibrary.getShortcut(), multipartFiles);

        //assert
        assertNotNull(libMediaItems);
        assertEquals(1, libMediaItems.size());

    }


    @Test
    public void shouldUploadImage() throws Exception {
        //when
        ReflectionTestUtils.setField(libMediaItemService, "imageFileService", imageFileService);
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/image/sample_image.png");
        MultipartFile multipartFile = new MockMultipartFile("testFile", inputStream);
        MultipartFile[] multipartFiles = Arrays.array(multipartFile);

        //then
        List<LibMediaItem> libMediaItems = libMediaItemService.upload(corOrganization.getShortcut(), corChannel.getShortcut(), libMediaLibrary.getShortcut(), multipartFiles);

        //assert
        assertNotNull(libMediaItems);
        assertEquals(1, libMediaItems.size());

    }

    @Test
    public void shouldUploadDocument() throws Exception {
        //when
        ReflectionTestUtils.setField(libMediaItemService, "libDocumentFileService", libDocumentFileService);
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/document/sample_document.xls");
        MultipartFile multipartFile = new MockMultipartFile("testFile", inputStream);
        MultipartFile[] multipartFiles = Arrays.array(multipartFile);

        //then
        List<LibMediaItem> libMediaItems = libMediaItemService.upload(corOrganization.getShortcut(), corChannel.getShortcut(), libMediaLibrary.getShortcut(), multipartFiles);

        //assert
        assertNotNull(libMediaItems);
        assertEquals(1, libMediaItems.size());

    }

    @Test
    public void shouldUploadDocumentXlsx() throws Exception {
        //when
        ReflectionTestUtils.setField(libMediaItemService, "libDocumentFileService", libDocumentFileService);
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/document/sample_document_2.xlsx");
        MultipartFile multipartFile = new MockMultipartFile("testFileXlsx", inputStream);
        MultipartFile[] multipartFiles = Arrays.array(multipartFile);

        //then
        List<LibMediaItem> libMediaItems = libMediaItemService.upload(corOrganization.getShortcut(), corChannel.getShortcut(), libMediaLibrary.getShortcut(), multipartFiles);

        //assert
        assertNotNull(libMediaItems);
        assertEquals(1, libMediaItems.size());

    }

}
