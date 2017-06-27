package io.protone.library.service.file;

import io.protone.domain.LibLibrary;
import io.protone.domain.LibMediaItem;
import org.apache.tika.metadata.Metadata;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by lukaszozimek on 28/05/2017.
 */
public interface LibFileService {

    LibMediaItem saveFile(ByteArrayInputStream bais, Metadata metadata, String originalFileName, Long size, LibLibrary libraryDB) throws IOException, SAXException;

    byte[] download(LibMediaItem libMediaItem) throws IOException;

    void deleteFile(LibMediaItem libMediaItem);

}
