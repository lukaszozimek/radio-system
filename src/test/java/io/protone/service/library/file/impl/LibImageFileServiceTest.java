package io.protone.service.library.file.impl;

import io.protone.ProtoneApp;
import io.protone.config.s3.S3Client;
import io.protone.domain.CorNetwork;
import io.protone.domain.LibLibrary;
import io.protone.domain.LibMediaItem;
import io.protone.repository.cor.CorNetworkRepository;
import io.protone.repository.library.LibLibraryRepository;
import io.protone.service.library.file.LibFileService;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import javax.inject.Inject;
import javax.transaction.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by lukaszozimek on 30/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProtoneApp.class)
@Transactional
public class LibImageFileServiceTest extends LibFileServiceBaseTest {


    private InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/video/sample-video.mp4");

    @Autowired
    @Qualifier("libImageFileService")
    private LibFileService libImageFileService;

    @Test
    public void shouldSaveVideoFile() throws Exception {
        //when
        ReflectionTestUtils.setField(libImageFileService, "s3Client", s3Client);
        ReflectionTestUtils.setField(libImageFileService, "corUserService", corUserService);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(parseInputStream(inputStream).toByteArray());
        parser.parse(byteArrayInputStream, handler, metadata, pcontext);

        //then
        LibMediaItem libMediaItem = libImageFileService.saveFile(byteArrayInputStream, metadata, SAMPLE_FILE_NAME, SAMPLE_FILE_SIZE, libLibrary);

        //assert
    }

    @Test
    public void shouldSaveDownloadImageFile() throws Exception {
        //when
        when(s3Client.download(anyString())).thenReturn(inputStream);
        ReflectionTestUtils.setField(libImageFileService, "s3Client", s3Client);
        ReflectionTestUtils.setField(libImageFileService, "corUserService", corUserService);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(parseInputStream(inputStream).toByteArray());
        parser.parse(byteArrayInputStream, handler, metadata, pcontext);
        //then
        LibMediaItem libMediaItem = libImageFileService.saveFile(byteArrayInputStream, metadata, SAMPLE_FILE_NAME, SAMPLE_FILE_SIZE, libLibrary);
        byte[] bytes = libImageFileService.download(libMediaItem);

        //assert
    }

    @Test
    public void shouldSaveDeleteImageFile() throws Exception {
        //when
        ReflectionTestUtils.setField(libImageFileService, "s3Client", s3Client);
        ReflectionTestUtils.setField(libImageFileService, "corUserService", corUserService);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(parseInputStream(inputStream).toByteArray());
        parser.parse(byteArrayInputStream, handler, metadata, pcontext);

        //then
        LibMediaItem libMediaItem = libImageFileService.saveFile(byteArrayInputStream, metadata, SAMPLE_FILE_NAME, SAMPLE_FILE_SIZE, libLibrary);
        libImageFileService.deleteFile(libMediaItem);


        //assert
    }
}
