package io.protone.library.service.file;


import io.protone.core.domain.CorChannel;
import io.protone.library.domain.LibMediaItem;
import io.protone.library.domain.LibMediaLibrary;
import org.apache.tika.metadata.Metadata;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by lukaszozimek on 28/05/2017.
 */
public interface LibFileService {

    LibMediaItem saveFile(ByteArrayInputStream bais, Metadata metadata, String originalFileName, Long size, LibMediaLibrary libraryDB, CorChannel corChannel) throws IOException, SAXException;

    byte[] download(LibMediaItem libMediaItem) throws IOException;

    void deleteFile(LibMediaItem libMediaItem);

    LibMediaItem updateContent(ByteArrayInputStream bais, Metadata metadata, LibMediaItem libMediaItem, Long size, LibMediaLibrary libraryDB, CorChannel corChannel) throws IOException, SAXException;


}
