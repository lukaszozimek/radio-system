package io.protone.application.service.library;

import io.jsonwebtoken.lang.Assert;
import io.protone.application.ProtoneApp;
import io.protone.application.util.TestUtil;
import io.protone.library.domain.LibDocumentObject;
import io.protone.library.domain.LibMediaItem;
import io.protone.library.domain.enumeration.LibItemTypeEnum;
import io.protone.library.repository.LibDocumentObjectRepository;
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
public class LibDocumentFileServiceTest extends LibFileServiceBaseTest {

    InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/document/sample_document.xls");

    @Autowired
    @Qualifier("libDocumentFileService")
    private LibFileService libDocumentFileService;

    @Autowired
    private LibDocumentObjectRepository libDocumentObjectRepository;

    @Test
    public void shouldSaveDocumentFile() throws Exception {
        //when

        ReflectionTestUtils.setField(libDocumentFileService, "s3Client", s3Client);
        ReflectionTestUtils.setField(libDocumentFileService, "corUserService", corUserService);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(TestUtil.parseInputStream(inputStream).toByteArray());
        parser.parse(byteArrayInputStream, handler, metadata, pcontext);

        //then
        LibMediaItem libMediaItem = libDocumentFileService.saveFile(byteArrayInputStream, metadata, SAMPLE_FILE_NAME, SAMPLE_FILE_SIZE, libLibrary);
        List<LibDocumentObject> libImageObjects = libDocumentObjectRepository.findByMediaItem(libMediaItem);
        //assert
        Assert.notNull(libMediaItem);
        Assert.notNull(libMediaItem.getId());
        Assert.notNull(libMediaItem.getIdx());
        Assert.notNull(libMediaItem.getProperites());
        Assert.notNull(libMediaItem.getNetwork());
        Assert.isTrue(libMediaItem.getLibrary().getName().equals(libLibrary.getName()));
        Assert.isTrue(libMediaItem.getNetwork().getName().equals(corNetwork.getName()));
        Assert.isTrue(libMediaItem.getItemType().equals(LibItemTypeEnum.IT_DOCUMENT));
        Assert.isTrue(libMediaItem.getProperites().size() > 1);
        Assert.isTrue(libImageObjects.size() == 1);

    }

    @Test
    public void shoulDownloadDocumentFile() throws Exception {
        //when
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/document/sample_document.xls");
        when(s3Client.download(anyString(),anyString())).thenReturn(Thread.currentThread().getContextClassLoader().getResourceAsStream("sample/document/sample_document.xls"));
        ReflectionTestUtils.setField(libDocumentFileService, "s3Client", s3Client);
        ReflectionTestUtils.setField(libDocumentFileService, "corUserService", corUserService);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(TestUtil.parseInputStream(inputStream).toByteArray());
        parser.parse(byteArrayInputStream, handler, metadata, pcontext);

        //then
        LibMediaItem libMediaItem = libDocumentFileService.saveFile(byteArrayInputStream, metadata, SAMPLE_FILE_NAME, SAMPLE_FILE_SIZE, libLibrary);
        byte[] bytes = libDocumentFileService.download(libMediaItem);

        //assert
        Assert.notNull(bytes);
        Assert.isTrue(bytes.length > 1);
    }

    @Test
    public void shouldDeleteDocumentFile() throws Exception {
        //when
        ReflectionTestUtils.setField(libDocumentFileService, "s3Client", s3Client);
        ReflectionTestUtils.setField(libDocumentFileService, "corUserService", corUserService);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(TestUtil.parseInputStream(inputStream).toByteArray());
        parser.parse(byteArrayInputStream, handler, metadata, pcontext);

        //then
        LibMediaItem libMediaItem = libDocumentFileService.saveFile(byteArrayInputStream, metadata, SAMPLE_FILE_NAME, SAMPLE_FILE_SIZE, libLibrary);
        libDocumentFileService.deleteFile(libMediaItem);
        List<LibDocumentObject> audioObjectList = libDocumentObjectRepository.findByMediaItem(libMediaItem);

        //assert
        Assert.isTrue(audioObjectList.isEmpty());


    }
}
