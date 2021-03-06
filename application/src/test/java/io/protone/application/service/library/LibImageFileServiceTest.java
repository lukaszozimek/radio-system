package io.protone.application.service.library;

import io.jsonwebtoken.lang.Assert;
import io.protone.application.ProtoneApp;
import io.protone.application.util.TestUtil;
import io.protone.library.domain.LibImageObject;
import io.protone.library.domain.LibMediaItem;
import io.protone.library.domain.enumeration.LibItemTypeEnum;
import io.protone.library.repository.LibImageObjectRepository;
import io.protone.library.service.file.LibFileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by lukaszozimek on 30/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class LibImageFileServiceTest extends LibFileServiceBaseTest {


    private InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/image/sample_image.png");

    @Autowired
    @Qualifier("libImageFileService")
    private LibFileService libImageFileService;

    @Autowired
    private LibImageObjectRepository libImageObjectRepository;

    @Test
    public void shouldSaveImageFile() throws Exception {
        //when
        ReflectionTestUtils.setField(libImageFileService, "s3Client", s3Client);
        ReflectionTestUtils.setField(libImageFileService, "corUserService", corUserService);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(TestUtil.parseInputStream(inputStream).toByteArray());
        parser.parse(byteArrayInputStream, handler, metadata, pcontext);

        //then
        LibMediaItem libMediaItem = libImageFileService.saveFile(byteArrayInputStream, metadata, SAMPLE_FILE_NAME, SAMPLE_FILE_SIZE, libMediaLibrary);
        List<LibImageObject> libImageObjects = libImageObjectRepository.findByMediaItem(libMediaItem);
        //assert
        Assert.notNull(libMediaItem);
        Assert.notNull(libMediaItem.getId());
        Assert.notNull(libMediaItem.getIdx());
        Assert.notNull(libMediaItem.getProperites());
        Assert.notNull(libMediaItem.getNetwork());
        Assert.isTrue(libMediaItem.getLibrary().getName().equals(libMediaLibrary.getName()));
        Assert.isTrue(libMediaItem.getNetwork().getName().equals(corNetwork.getName()));
        Assert.isTrue(libMediaItem.getItemType().equals(LibItemTypeEnum.IT_IMAGE));
        Assert.isTrue(libMediaItem.getProperites().size() > 1);
        Assert.isTrue(libImageObjects.size() == 1);

    }

    @Test
    public void shoulDownloadImageFile() throws Exception {
        //when
        when(s3Client.download(anyString(),anyString(),anyString())).thenReturn(Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/image/sample_image.png"));
        ReflectionTestUtils.setField(libImageFileService, "s3Client", s3Client);
        ReflectionTestUtils.setField(libImageFileService, "corUserService", corUserService);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(TestUtil.parseInputStream(inputStream).toByteArray());
        parser.parse(byteArrayInputStream, handler, metadata, pcontext);

        //then
        LibMediaItem libMediaItem = libImageFileService.saveFile(byteArrayInputStream, metadata, SAMPLE_FILE_NAME, SAMPLE_FILE_SIZE, libMediaLibrary);
        byte[] bytes = libImageFileService.download(libMediaItem);

        //assert
        Assert.notNull(bytes);
        Assert.isTrue(bytes.length > 1);
    }

    @Test
    public void shouldDeleteImageFile() throws Exception {
        //when
        ReflectionTestUtils.setField(libImageFileService, "s3Client", s3Client);
        ReflectionTestUtils.setField(libImageFileService, "corUserService", corUserService);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(TestUtil.parseInputStream(inputStream).toByteArray());
        parser.parse(byteArrayInputStream, handler, metadata, pcontext);

        //then
        LibMediaItem libMediaItem = libImageFileService.saveFile(byteArrayInputStream, metadata, SAMPLE_FILE_NAME, SAMPLE_FILE_SIZE, libMediaLibrary);
        libImageFileService.deleteFile(libMediaItem);
        List<LibImageObject> audioObjectList = libImageObjectRepository.findByMediaItem(libMediaItem);

        //assert
        Assert.isTrue(audioObjectList.isEmpty());


    }
}
