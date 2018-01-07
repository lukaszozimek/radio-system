package io.protone.application.service.library;

import io.jsonwebtoken.lang.Assert;
import io.protone.application.ProtoneApp;
import io.protone.application.util.TestUtil;
import io.protone.library.domain.LibAudioObject;
import io.protone.library.domain.LibMediaItem;
import io.protone.library.domain.enumeration.LibItemTypeEnum;
import io.protone.library.repository.LibAudioObjectRepository;
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
public class LibAudioFileServiceTest extends LibFileServiceBaseTest {

    private InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/audio/sample_mp3.mp3");

    @Autowired
    @Qualifier("libAudioFileService")
    private LibFileService libAudioFileService;

    @Autowired
    private LibAudioObjectRepository audioObjectRepository;

    @Test
    public void shouldSaveAudioFile() throws Exception {
        //when
        ReflectionTestUtils.setField(libAudioFileService, "s3Client", s3Client);
        ReflectionTestUtils.setField(libAudioFileService, "corUserService", corUserService);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(TestUtil.parseInputStream(inputStream).toByteArray());
        parser.parse(byteArrayInputStream, handler, metadata, pcontext);

        //then
        LibMediaItem libMediaItem = libAudioFileService.saveFile(byteArrayInputStream, metadata, SAMPLE_FILE_NAME, SAMPLE_FILE_SIZE, libMediaLibrary, corChannel);
        List<LibAudioObject> libAudioObjects = audioObjectRepository.findByMediaItem(libMediaItem);
        //assert
        Assert.notNull(libMediaItem);
        Assert.notNull(libMediaItem.getId());
        Assert.notNull(libMediaItem.getIdx());
        Assert.notNull(libMediaItem.getProperites());
        Assert.isTrue(libMediaItem.getLibrary().getName().equals(libMediaLibrary.getName()));
        Assert.isTrue(libMediaItem.getItemType().equals(LibItemTypeEnum.IT_AUDIO));
        Assert.isTrue(libMediaItem.getProperites().size() > 1);
        Assert.isTrue(libAudioObjects.size() == 1);

    }

    @Test
    public void shoulDownloadAudioFile() throws Exception {
        //when
        when(s3Client.download(anyString(), anyString(), anyString())).thenReturn(Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/audio/sample_mp3.mp3"));
        ReflectionTestUtils.setField(libAudioFileService, "s3Client", s3Client);
        ReflectionTestUtils.setField(libAudioFileService, "corUserService", corUserService);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(TestUtil.parseInputStream(inputStream).toByteArray());
        parser.parse(byteArrayInputStream, handler, metadata, pcontext);

        //then
        LibMediaItem libMediaItem = libAudioFileService.saveFile(byteArrayInputStream, metadata, SAMPLE_FILE_NAME, SAMPLE_FILE_SIZE, libMediaLibrary, corChannel);
        byte[] bytes = libAudioFileService.download(libMediaItem);

        //assert
        Assert.notNull(bytes);
        Assert.isTrue(bytes.length > 1);
    }

    @Test
    public void shouldDeleteAudioFile() throws Exception {
        //when
        ReflectionTestUtils.setField(libAudioFileService, "s3Client", s3Client);
        ReflectionTestUtils.setField(libAudioFileService, "corUserService", corUserService);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(TestUtil.parseInputStream(inputStream).toByteArray());
        parser.parse(byteArrayInputStream, handler, metadata, pcontext);

        //then
        LibMediaItem libMediaItem = libAudioFileService.saveFile(byteArrayInputStream, metadata, SAMPLE_FILE_NAME, SAMPLE_FILE_SIZE, libMediaLibrary, corChannel);
        libAudioFileService.deleteFile(libMediaItem);
        List<LibAudioObject> audioObjectList = audioObjectRepository.findByMediaItem(libMediaItem);

        //assert
        Assert.isTrue(audioObjectList.isEmpty());


    }

}
