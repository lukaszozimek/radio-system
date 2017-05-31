package io.protone.service.library.file.impl;

import io.protone.ProtoneApp;
import io.protone.domain.LibMediaItem;
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

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by lukaszozimek on 30/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class LibAudioFileServiceTest extends LibFileServiceBaseTest {

    private InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/audio/SAMPLE_MP3.mp3");

    @Autowired
    @Qualifier("libAudioFileService")
    private LibFileService libAudioFileService;

    @Test
    public void shouldSaveAudioFile() throws Exception {
        //when
        ReflectionTestUtils.setField(libAudioFileService, "s3Client", s3Client);
        ReflectionTestUtils.setField(libAudioFileService, "corUserService", corUserService);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(parseInputStream(inputStream).toByteArray());
        parser.parse(byteArrayInputStream, handler, metadata, pcontext);

        //then
        LibMediaItem libMediaItem = libAudioFileService.saveFile(byteArrayInputStream, metadata, SAMPLE_FILE_NAME, SAMPLE_FILE_SIZE, libLibrary);

        //assert
    }

    @Test
    public void shouldSaveDownloadAudioFile() throws Exception {
        //when
        when(s3Client.download(anyString())).thenReturn(inputStream);
        ReflectionTestUtils.setField(libAudioFileService, "s3Client", s3Client);
        ReflectionTestUtils.setField(libAudioFileService, "corUserService", corUserService);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(parseInputStream(inputStream).toByteArray());
        parser.parse(byteArrayInputStream, handler, metadata, pcontext);
        //then
        LibMediaItem libMediaItem = libAudioFileService.saveFile(byteArrayInputStream, metadata, SAMPLE_FILE_NAME, SAMPLE_FILE_SIZE, libLibrary);
        byte[] bytes = libAudioFileService.download(libMediaItem);

        //assert
    }

    @Test
    public void shouldSaveDeleteAudioFile() throws Exception {
        //when
        ReflectionTestUtils.setField(libAudioFileService, "s3Client", s3Client);
        ReflectionTestUtils.setField(libAudioFileService, "corUserService", corUserService);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(parseInputStream(inputStream).toByteArray());
        parser.parse(byteArrayInputStream, handler, metadata, pcontext);

        //then
        LibMediaItem libMediaItem = libAudioFileService.saveFile(byteArrayInputStream, metadata, SAMPLE_FILE_NAME, SAMPLE_FILE_SIZE, libLibrary);
        libAudioFileService.deleteFile(libMediaItem);


        //assert

    }

}
