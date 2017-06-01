package io.protone.service.library;

import io.protone.ProtoneApp;
import io.protone.domain.CorNetwork;
import io.protone.domain.LibLibrary;
import io.protone.domain.LibMediaItem;
import io.protone.domain.enumeration.LibItemTypeEnum;
import io.protone.repository.cor.CorNetworkRepository;
import io.protone.repository.library.LibLibraryRepository;
import io.protone.repository.library.LibMediaItemRepository;
import io.protone.service.library.file.LibFileService;
import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
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

    @Mock
    private LibFileService audioFileService;

    @Mock
    private LibFileService videoFileService;

    @Mock
    private LibFileService imageFileService;

    private CorNetwork corNetwork;

    private PodamFactory factory;

    private LibLibrary libLibrary;

    @Before
    public void setUp() throws Exception {
        factory = new PodamFactoryImpl();
        corNetwork = factory.manufacturePojo(CorNetwork.class);
        corNetwork.setId(null);
        corNetwork = corNetworkRepository.saveAndFlush(corNetwork);
        libLibrary = factory.manufacturePojo(LibLibrary.class);
        libLibrary.setNetwork(corNetwork);
        libLibrary = libLibraryRepository.save(libLibrary);
        ReflectionTestUtils.setField(libItemService, "videoFileService", videoFileService);
        ReflectionTestUtils.setField(libItemService, "imageFileService", imageFileService);

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
    public void shouldUploadViedo() throws Exception {
        //when
        LibMediaItem libMediaItem = factory.manufacturePojo(LibMediaItem.class);
        libMediaItem.setLibrary(libLibrary);
        libMediaItem.setNetwork(corNetwork);
        libMediaItem.setItemType(LibItemTypeEnum.IT_VIDEO);
        libMediaItem = libMediaItemRepository.saveAndFlush(libMediaItem);
        when(videoFileService.saveFile(any(), any(), anyString(), anyLong(), any())).thenReturn(libMediaItem);
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
        LibMediaItem libMediaItem = factory.manufacturePojo(LibMediaItem.class);
        libMediaItem.setLibrary(libLibrary);
        libMediaItem.setNetwork(corNetwork);
        libMediaItem.setItemType(LibItemTypeEnum.IT_AUDIO);
        libMediaItem = libMediaItemRepository.saveAndFlush(libMediaItem);

        when(audioFileService.saveFile(any(), any(), any(), any(), any())).thenReturn(libMediaItem);
        ReflectionTestUtils.setField(libItemService, "audioFileService", audioFileService);

        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/audio/SAMPLE_MP3.mp3");
        MultipartFile multipartFile = new MockMultipartFile("testFile", inputStream);
        MultipartFile[] multipartFiles = Arrays.array(multipartFile);

        //then

        List<LibMediaItem> libMediaItems = libItemService.upload(corNetwork.getShortcut(), libLibrary.getShortcut(), multipartFiles);

        //assert
        assertNotNull(libMediaItems);
        assertEquals(1, libMediaItems.size());

    }


    @Test
    @Ignore(value = "Test should be changed after adding relation between media item")
    public void shouldUploadImage() throws Exception {

        //when
        LibMediaItem libMediaItem = factory.manufacturePojo(LibMediaItem.class);
        libMediaItem.setLibrary(libLibrary);
        libMediaItem.setNetwork(corNetwork);
        libMediaItem.setItemType(LibItemTypeEnum.IT_VIDEO);
        libMediaItem = libMediaItemRepository.saveAndFlush(libMediaItem);
        when(audioFileService.saveFile(any(), any(), anyString(), anyLong(), any())).thenReturn(libMediaItem);

        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/audio/SAMPLE_MP3.mp3");
        MultipartFile multipartFile = new MockMultipartFile("testFile", inputStream);
        MultipartFile[] multipartFiles = Arrays.array(multipartFile);

        //then
        List<LibMediaItem> libMediaItems = libItemService.upload(corNetwork.getShortcut(), libLibrary.getShortcut(), multipartFiles);

        //assert
        assertNotNull(libMediaItems);
        assertEquals(1, libMediaItems.size());


    }


}
