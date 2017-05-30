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
import org.springframework.web.multipart.MultipartFile;
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
public class LibVideoFileServiceTest extends LibFileServiceBaseTest {

    private InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/video/sample-video.mp4");

    private static final long SAMPLE_FILE_SIZE = 50000;

    private static final String SAMPLE_FILE_NAME = "test";

    @Autowired
    @Qualifier(value = "libVideoFileService")
    private LibFileService libVideoFileService;

    @Test
    public void shouldSaveVideoFile() throws Exception {
        //when
        ReflectionTestUtils.setField(libVideoFileService, "s3Client", s3Client);
        ReflectionTestUtils.setField(libVideoFileService, "corUserService", corUserService);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(parseInputStream(inputStream).toByteArray());

        parser.parse(byteArrayInputStream, handler, metadata, pcontext);

        //then
        LibMediaItem libMediaItem = libVideoFileService.saveFile(byteArrayInputStream, metadata, SAMPLE_FILE_NAME, SAMPLE_FILE_SIZE, libLibrary);

        //assert
    }

    @Test
    public void shouldSaveDownloadVideoFile() throws Exception {
        //when
        when(s3Client.download(anyString())).thenReturn(inputStream);
        ReflectionTestUtils.setField(libVideoFileService, "s3Client", s3Client);
        ReflectionTestUtils.setField(libVideoFileService, "corUserService", corUserService);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(parseInputStream(inputStream).toByteArray());
        parser.parse(byteArrayInputStream, handler, metadata, pcontext);

        //then
        LibMediaItem libMediaItem = libVideoFileService.saveFile(byteArrayInputStream, metadata, SAMPLE_FILE_NAME, SAMPLE_FILE_SIZE, libLibrary);
        byte[] bytes = libVideoFileService.download(libMediaItem);


        //assert

    }

    @Test
    public void shouldSaveDeleteVideoFile() throws Exception {
        //when
        ReflectionTestUtils.setField(libVideoFileService, "s3Client", s3Client);
        ReflectionTestUtils.setField(libVideoFileService, "corUserService", corUserService);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(parseInputStream(inputStream).toByteArray());
        parser.parse(byteArrayInputStream, handler, metadata, pcontext);

        //then
        LibMediaItem libMediaItem = libVideoFileService.saveFile(byteArrayInputStream, metadata, SAMPLE_FILE_NAME, SAMPLE_FILE_SIZE, libLibrary);
        libVideoFileService.deleteFile(libMediaItem);


        //assert
    }

}
