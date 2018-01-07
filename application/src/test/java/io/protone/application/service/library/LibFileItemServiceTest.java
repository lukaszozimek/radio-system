package io.protone.application.service.library;

import io.protone.application.ProtoneApp;
import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorOrganization;
import io.protone.core.domain.CorUser;
import io.protone.core.repository.CorUserRepository;
import io.protone.core.s3.S3Client;
import io.protone.core.service.CorUserService;
import io.protone.library.domain.LibCloudObject;
import io.protone.library.domain.LibFileItem;
import io.protone.library.domain.LibFileLibrary;
import io.protone.library.repository.LibCloudObjectRepository;
import io.protone.library.repository.LibFileItemRepository;
import io.protone.library.repository.LibFileLibraryRepository;
import io.protone.library.service.LibFileItemService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Optional;

import static io.protone.application.util.TestConstans.*;
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
public class LibFileItemServiceTest {
    @Autowired
    private LibFileItemService libFileItemService;

    @Autowired
    private LibFileItemRepository libFileItemRepository;

    @Autowired
    private LibCloudObjectRepository libCloudObjectRepository;

    @Autowired
    private LibFileLibraryRepository libFileLibraryRepository;

    @Autowired
    private CorUserRepository corUserRepository;

    @Mock
    private S3Client s3Client;

    @Mock
    private CorUserService corUserService;

    private CorChannel corChannel;

    private CorOrganization corOrganization;

    private PodamFactory factory;

    private LibFileLibrary fileLibrary;

    private LibCloudObject libCloudObject;

    private CorUser corUser;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        factory = new PodamFactoryImpl();
        corOrganization = new CorOrganization().shortcut(TEST_ORGANIZATION_SHORTCUT);
        corOrganization.setId(TEST_ORGANIZATION_ID);
        corChannel = new CorChannel().shortcut(TEST_CHANNEL_SHORTCUT);
        corChannel.setId(TEST_CHANNEL_ID);
        corChannel.setOrganization(corOrganization);
        fileLibrary = libFileLibraryRepository.findOne(8L);
        corUser = new CorUser();
        corUser.setId(3L);
        corUser.setLogin("admin");
        corUser.setOrganization(corOrganization);
        doNothing().when(s3Client).delete(anyString(), anyString(), anyString());
        doNothing().when(s3Client).upload(anyString(), anyObject(), anyString());
        when(corUserService.getUserWithAuthoritiesByLogin(anyString())).thenReturn(Optional.of(corUser));
        ReflectionTestUtils.setField(libFileItemService, "s3Client", s3Client);

        libCloudObject = libCloudObjectRepository.save(new LibCloudObject().originalName("test").size(3223L).uuid("UUID").hash("hash").contentType("tesst"));
    }

    @Test
    public void findFileItems() throws Exception {

        //when
        LibFileItem libFileItem = factory.manufacturePojo(LibFileItem.class);
        libFileItem.setLibrary(fileLibrary);
        libFileItem.setChannel(corChannel);
        libFileItem.setCloudObject(libCloudObject);
        libFileItem = libFileItemRepository.save(libFileItem);

        //then
        Slice<LibFileItem> fetchedEntity = libFileItemService.findAllLibFileItems(corOrganization.getShortcut(), corChannel.getShortcut(), fileLibrary.getShortcut(), new PageRequest(0, 10));

        //assert
        assertNotNull(fetchedEntity.getContent());
        assertEquals(1, fetchedEntity.getContent().size());
        assertEquals(libFileItem.getId(), fetchedEntity.getContent().get(0).getId());
        assertEquals(libFileItem.getChannel(), fetchedEntity.getContent().get(0).getChannel());

    }

    @Test
    public void findFileItem() throws Exception {

        //when
        LibFileItem libFileItem = factory.manufacturePojo(LibFileItem.class);
        libFileItem.setLibrary(fileLibrary);
        libFileItem.setChannel(corChannel);

        libFileItem.setCloudObject(libCloudObject);
        libFileItem = libFileItemRepository.save(libFileItem);

        //then
        LibFileItem fetchedEntity = libFileItemService.findLibFileItem(corOrganization.getShortcut(), corChannel.getShortcut(), fileLibrary.getShortcut(), libFileItem.getIdx());

        //assert
        assertNotNull(fetchedEntity);
        assertNotNull(fetchedEntity.getCreatedBy());
        assertEquals(libFileItem.getId(), fetchedEntity.getId());
        assertEquals(libFileItem.getChannel(), fetchedEntity.getChannel());

    }

    @Test
    public void deleteFileItem() throws Exception {
        //when
        LibFileItem libFileItem = factory.manufacturePojo(LibFileItem.class);
        libFileItem.setLibrary(fileLibrary);
        libFileItem.setChannel(corChannel);
        libFileItem.setCloudObject(libCloudObject);
        libFileItem = libFileItemRepository.save(libFileItem);

        //then
        libFileItemService.deleteFile(corOrganization.getShortcut(), corChannel.getShortcut(), fileLibrary.getShortcut(), libFileItem.getIdx());
        LibFileItem fetchedEntity = libFileItemService.findLibFileItem(corOrganization.getShortcut(), corChannel.getShortcut(), fileLibrary.getShortcut(), libFileItem.getIdx());

        //assert
        assertNull(fetchedEntity);
    }

    @Test
    public void shouldUploadViedo() throws Exception {
        //when
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/video/sample-video.mp4");
        MultipartFile multipartFile = new MockMultipartFile("testFile", inputStream);
        //then
        LibFileItem libFileItems = libFileItemService.uploadFileItem(corOrganization.getShortcut(), corChannel.getShortcut(), fileLibrary.getShortcut(), multipartFile);

        //assert
        assertNotNull(libFileItems);

    }


}
