package io.protone.service.library.file.impl;

import io.jsonwebtoken.lang.Assert;
import io.protone.ProtoneApp;
import io.protone.config.s3.S3Client;
import io.protone.domain.*;
import io.protone.domain.enumeration.LibItemTypeEnum;
import io.protone.repository.cor.CorNetworkRepository;
import io.protone.repository.library.LibImageObjectRepository;
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
import java.util.List;

import static io.protone.util.TestUtil.parseInputStream;
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


    private InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/image/SAMPLE_IMAGE.png");

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
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(parseInputStream(inputStream).toByteArray());
        parser.parse(byteArrayInputStream, handler, metadata, pcontext);

        //then
        LibMediaItem libMediaItem = libImageFileService.saveFile(byteArrayInputStream, metadata, SAMPLE_FILE_NAME, SAMPLE_FILE_SIZE, libLibrary);
        List<LibImageObject> libImageObjects = libImageObjectRepository.findByMediaItem(libMediaItem);
        //assert
        Assert.notNull(libMediaItem);
        Assert.notNull(libMediaItem.getId());
        Assert.notNull(libMediaItem.getIdx());
        Assert.notNull(libMediaItem.getProperites());
        Assert.notNull(libMediaItem.getNetwork());
        Assert.isTrue(libMediaItem.getLibrary().getName().equals(libLibrary.getName()));
        Assert.isTrue(libMediaItem.getNetwork().getName().equals(corNetwork.getName()));
        Assert.isTrue(libMediaItem.getItemType().equals(LibItemTypeEnum.IT_IMAGE));
        Assert.isTrue(libMediaItem.getProperites().size() > 1);
        Assert.isTrue(libImageObjects.size() == 1);

    }

    @Test
    public void shoulDownloadImageFile() throws Exception {
        //when
        when(s3Client.download(anyString())).thenReturn(Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/image/SAMPLE_IMAGE.png"));
        ReflectionTestUtils.setField(libImageFileService, "s3Client", s3Client);
        ReflectionTestUtils.setField(libImageFileService, "corUserService", corUserService);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(parseInputStream(inputStream).toByteArray());
        parser.parse(byteArrayInputStream, handler, metadata, pcontext);

        //then
        LibMediaItem libMediaItem = libImageFileService.saveFile(byteArrayInputStream, metadata, SAMPLE_FILE_NAME, SAMPLE_FILE_SIZE, libLibrary);
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
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(parseInputStream(inputStream).toByteArray());
        parser.parse(byteArrayInputStream, handler, metadata, pcontext);

        //then
        LibMediaItem libMediaItem = libImageFileService.saveFile(byteArrayInputStream, metadata, SAMPLE_FILE_NAME, SAMPLE_FILE_SIZE, libLibrary);
        libImageFileService.deleteFile(libMediaItem);
        List<LibImageObject> audioObjectList = libImageObjectRepository.findByMediaItem(libMediaItem);

        //assert
        Assert.isTrue(audioObjectList.isEmpty());


    }
}
