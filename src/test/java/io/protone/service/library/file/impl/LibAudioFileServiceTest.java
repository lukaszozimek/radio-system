package io.protone.service.library.file.impl;

import io.jsonwebtoken.lang.Assert;
import io.protone.ProtoneApp;
import io.protone.domain.LibAudioObject;
import io.protone.domain.LibMediaItem;
import io.protone.domain.enumeration.LibItemTypeEnum;
import io.protone.repository.library.LibAudioObjectRepository;
import io.protone.repository.library.LibCloudObjectRepository;
import io.protone.repository.library.LibMediaItemRepository;
import io.protone.service.library.file.LibFileService;
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

import static io.protone.util.TestUtil.parseInputStream;
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
    private LibMediaItemRepository libMediaItemRepository;

    @Autowired
    private LibAudioObjectRepository audioObjectRepository;

    @Autowired
    private LibCloudObjectRepository cloudObjectRepository;

    @Test
    public void shouldSaveAudioFile() throws Exception {
        //when
        ReflectionTestUtils.setField(libAudioFileService, "s3Client", s3Client);
        ReflectionTestUtils.setField(libAudioFileService, "corUserService", corUserService);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(parseInputStream(inputStream).toByteArray());
        parser.parse(byteArrayInputStream, handler, metadata, pcontext);

        //then
        LibMediaItem libMediaItem = libAudioFileService.saveFile(byteArrayInputStream, metadata, SAMPLE_FILE_NAME, SAMPLE_FILE_SIZE, libLibrary);
        List<LibAudioObject> libAudioObjects = audioObjectRepository.findByMediaItem(libMediaItem);
        //assert
        Assert.notNull(libMediaItem);
        Assert.notNull(libMediaItem.getId());
        Assert.notNull(libMediaItem.getIdx());
        Assert.notNull(libMediaItem.getProperites());
        Assert.notNull(libMediaItem.getNetwork());
        Assert.isTrue(libMediaItem.getLibrary().getName().equals(libLibrary.getName()));
        Assert.isTrue(libMediaItem.getNetwork().getName().equals(corNetwork.getName()));
        Assert.isTrue(libMediaItem.getItemType().equals(LibItemTypeEnum.IT_AUDIO));
        Assert.isTrue(libMediaItem.getProperites().size() > 1);
        Assert.isTrue(libAudioObjects.size() == 1);

    }

    @Test
    public void shoulDownloadAudioFile() throws Exception {
        //when
        when(s3Client.download(anyString(),anyString())).thenReturn(Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/audio/sample_mp3.mp3"));
        ReflectionTestUtils.setField(libAudioFileService, "s3Client", s3Client);
        ReflectionTestUtils.setField(libAudioFileService, "corUserService", corUserService);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(parseInputStream(inputStream).toByteArray());
        parser.parse(byteArrayInputStream, handler, metadata, pcontext);

        //then
        LibMediaItem libMediaItem = libAudioFileService.saveFile(byteArrayInputStream, metadata, SAMPLE_FILE_NAME, SAMPLE_FILE_SIZE, libLibrary);
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
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(parseInputStream(inputStream).toByteArray());
        parser.parse(byteArrayInputStream, handler, metadata, pcontext);

        //then
        LibMediaItem libMediaItem = libAudioFileService.saveFile(byteArrayInputStream, metadata, SAMPLE_FILE_NAME, SAMPLE_FILE_SIZE, libLibrary);
        libAudioFileService.deleteFile(libMediaItem);
        List<LibAudioObject> audioObjectList = audioObjectRepository.findByMediaItem(libMediaItem);

        //assert
        Assert.isTrue(audioObjectList.isEmpty());


    }

}
