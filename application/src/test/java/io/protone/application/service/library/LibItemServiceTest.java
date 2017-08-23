package io.protone.application.service.library;

import com.google.common.collect.Sets;
import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorAuthority;
import io.protone.core.domain.CorNetwork;
import io.protone.core.domain.CorUser;
import io.protone.core.repository.CorNetworkRepository;
import io.protone.core.repository.CorUserRepository;
import io.protone.core.s3.S3Client;
import io.protone.core.service.CorUserService;
import io.protone.library.domain.LibLibrary;
import io.protone.library.domain.LibMediaItem;
import io.protone.library.domain.enumeration.LibItemTypeEnum;
import io.protone.library.repository.LibLibraryRepository;
import io.protone.library.repository.LibMediaItemRepository;
import io.protone.library.service.LibItemService;
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
public class LibItemServiceTest {
    @Autowired
    private LibItemService libItemService;

    @Autowired
    private CorNetworkRepository corNetworkRepository;

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

    private CorNetwork corNetwork;

    private PodamFactory factory;

    private LibLibrary libLibrary;

    private CorUser corUser;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        factory = new PodamFactoryImpl();
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corNetwork.setId(null);
        corNetwork = corNetworkRepository.saveAndFlush(corNetwork);
        libLibrary = factory.manufacturePojo(LibLibrary.class);
        libLibrary.setNetwork(corNetwork);
        libLibrary = libLibraryRepository.save(libLibrary);
        corUser = factory.manufacturePojo(CorUser.class);
        corUser.setNetworks(Sets.newHashSet(corNetwork));
        corUser.setChannels(null);
        corUser.setAuthorities(Sets.newHashSet(new CorAuthority().name(ADMIN)));
        corUser = corUserRepository.saveAndFlush(corUser);
        doNothing().when(s3Client).delete(anyString(), anyObject());
        doNothing().when(s3Client).upload(anyString(), anyObject(), anyString());
        when(corUserService.getUserWithAuthoritiesByLogin(anyString())).thenReturn(Optional.of(corUser));

        ReflectionTestUtils.setField(audioFileService, "s3Client", s3Client);
        ReflectionTestUtils.setField(audioFileService, "corUserService", corUserService);
        ReflectionTestUtils.setField(videoFileService, "s3Client", s3Client);
        ReflectionTestUtils.setField(videoFileService, "corUserService", corUserService);
        ReflectionTestUtils.setField(imageFileService, "s3Client", s3Client);
        ReflectionTestUtils.setField(imageFileService, "corUserService", corUserService);
        ReflectionTestUtils.setField(libDocumentFileService, "s3Client", s3Client);
        ReflectionTestUtils.setField(libDocumentFileService, "corUserService", corUserService);
        ReflectionTestUtils.setField(libItemService, "audioFileService", audioFileService);
        ReflectionTestUtils.setField(libItemService, "videoFileService", videoFileService);
        ReflectionTestUtils.setField(libItemService, "imageFileService", imageFileService);
        ReflectionTestUtils.setField(libItemService, "libDocumentFileService", libDocumentFileService);

    }

    @Test
    public void findMediaItems() throws Exception {

        //when
        LibMediaItem libMediaItem = factory.manufacturePojo(LibMediaItem.class);
        libMediaItem.setLibrary(libLibrary);
        libMediaItem.setNetwork(corNetwork);
        libMediaItem = libMediaItemRepository.save(libMediaItem);

        //then
        Slice<LibMediaItem> fetchedEntity = libItemService.getMediaItems(corNetwork.getShortcut(), libLibrary.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(libMediaItem.getId(), fetchedEntity.getContent().get(0).getId());
        assertEquals(libMediaItem.getNetwork(), fetchedEntity.getContent().get(0).getNetwork());

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
        assertNotNull(fetchedEntity.getCreatedBy());
        assertEquals(libMediaItem.getId(), fetchedEntity.getId());
        assertEquals(libMediaItem.getNetwork(), fetchedEntity.getNetwork());

    }

    @Test
    public void deleteMediaItem() throws Exception {
        //when
        LibMediaItem libMediaItem = factory.manufacturePojo(LibMediaItem.class);
        libMediaItem.setItemType(LibItemTypeEnum.IT_AUDIO);
        libMediaItem.setLibrary(libLibrary);
        libMediaItem.setNetwork(corNetwork);
        libMediaItem = libMediaItemRepository.save(libMediaItem);

        //then
        libItemService.deleteItem(corNetwork.getShortcut(), libLibrary.getShortcut(), libMediaItem.getIdx());
        LibMediaItem fetchedEntity = libItemService.getMediaItem(corNetwork.getShortcut(), libLibrary.getShortcut(), libMediaItem.getIdx());

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
        List<LibMediaItem> libMediaItems = libItemService.upload(corNetwork.getShortcut(), libLibrary.getShortcut(), multipartFiles);

        //assert
        assertNotNull(libMediaItems);
        assertEquals(1, libMediaItems.size());
    }

    @Test
    public void shouldUploadAudio() throws Exception {
        //when
        ReflectionTestUtils.setField(libItemService, "audioFileService", audioFileService);
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/audio/sample_mp3.mp3");
        MultipartFile multipartFile = new MockMultipartFile("testFile", inputStream);
        MultipartFile[] multipartFiles = Arrays.array(multipartFile);

        //then

        List<LibMediaItem> libMediaItems = libItemService.upload(corNetwork.getShortcut(), libLibrary.getShortcut(), multipartFiles);

        //assert
        assertNotNull(libMediaItems);
        assertEquals(1, libMediaItems.size());

    }


    @Test
    public void shouldUploadImage() throws Exception {
        //when
        ReflectionTestUtils.setField(libItemService, "imageFileService", imageFileService);
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/image/sample_image.png");
        MultipartFile multipartFile = new MockMultipartFile("testFile", inputStream);
        MultipartFile[] multipartFiles = Arrays.array(multipartFile);

        //then
        List<LibMediaItem> libMediaItems = libItemService.upload(corNetwork.getShortcut(), libLibrary.getShortcut(), multipartFiles);

        //assert
        assertNotNull(libMediaItems);
        assertEquals(1, libMediaItems.size());

    }

    @Test
    public void shouldUploadDocument() throws Exception {
        //when
        ReflectionTestUtils.setField(libItemService, "libDocumentFileService", libDocumentFileService);
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/document/sample_document.xls");
        MultipartFile multipartFile = new MockMultipartFile("testFile", inputStream);
        MultipartFile[] multipartFiles = Arrays.array(multipartFile);

        //then
        List<LibMediaItem> libMediaItems = libItemService.upload(corNetwork.getShortcut(), libLibrary.getShortcut(), multipartFiles);

        //assert
        assertNotNull(libMediaItems);
        assertEquals(1, libMediaItems.size());

    }

    @Test
    public void shouldUploadDocumentXlsx() throws Exception {
        //when
        ReflectionTestUtils.setField(libItemService, "libDocumentFileService", libDocumentFileService);
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/document/sample_document_2.xlsx");
        MultipartFile multipartFile = new MockMultipartFile("testFileXlsx", inputStream);
        MultipartFile[] multipartFiles = Arrays.array(multipartFile);

        //then
        List<LibMediaItem> libMediaItems = libItemService.upload(corNetwork.getShortcut(), libLibrary.getShortcut(), multipartFiles);

        //assert
        assertNotNull(libMediaItems);
        assertEquals(1, libMediaItems.size());

    }

}
