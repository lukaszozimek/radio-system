package io.protone.library.service;

import io.protone.core.constans.ServiceConstants;
import io.protone.core.s3.S3Client;
import io.protone.core.s3.exceptions.DeleteException;
import io.protone.core.s3.exceptions.DownloadException;
import io.protone.core.s3.exceptions.S3Exception;
import io.protone.core.s3.exceptions.UploadException;
import io.protone.library.domain.LibCloudObject;
import io.protone.library.domain.LibFileItem;
import io.protone.library.domain.LibFileLibrary;
import io.protone.library.domain.enumeration.LibObjectTypeEnum;
import io.protone.library.repository.LibCloudObjectRepository;
import io.protone.library.repository.LibFileItemRepository;
import org.apache.commons.io.IOUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import static io.protone.core.constans.MinioFoldersConstants.FILE;

@Service
public class LibFileItemService {

    private final Logger log = LoggerFactory.getLogger(LibFileItemService.class);
    @Inject
    private LibFileLibraryService libraryService;

    @Inject
    private LibFileItemRepository libFileItemRepository;

    @Inject
    private S3Client s3Client;
    @Inject
    private LibCloudObjectRepository cloudObjectRepository;

    public Slice<LibFileItem> findAllLibFileItems(String networkShortcut, String libraryShortcut, Pageable pageable) {
        return libFileItemRepository.findSliceByNetwork_ShortcutAndLibrary_Shortcut(networkShortcut, libraryShortcut, pageable);
    }

    public Optional<LibFileItem> findLibFileItem(String networkShortcut, String libraryShortcut, String idx) {
        return libFileItemRepository.findByNetwork_ShortcutAndLibrary_ShortcutAndIdx(networkShortcut, libraryShortcut, idx);
    }

    public LibFileItem uploadFileItem(String networkShortcut, String libraryShortcut, MultipartFile file) throws IOException {
        LibFileItem libFileItem = new LibFileItem();
        LibFileLibrary libFileLibrary = libraryService.findLibrary(libraryShortcut, networkShortcut);
        ByteArrayInputStream bais = new ByteArrayInputStream(file.getBytes());
        byte[] inputStream = new byte[bais.available()];
        Supplier<ByteArrayInputStream> inputStreamSupplier = () -> new ByteArrayInputStream(inputStream);
        String fileUUID = UUID.randomUUID().toString();
        Parser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext pcontext = new ParseContext();
        try {
            parser.parse(inputStreamSupplier.get(), handler, metadata, pcontext);
            log.debug("Uploading File to Storage: {} ", fileUUID);
            s3Client.upload(libFileLibrary.getNetwork().getShortcut(), FILE + libFileLibrary.getShortcut(), fileUUID, inputStreamSupplier.get(), metadata.get(HttpHeaders.CONTENT_TYPE));
            LibCloudObject cloudObject = new LibCloudObject()
                    .uuid(fileUUID).contentType(metadata.get(HttpHeaders.CONTENT_TYPE))
                    .originalName(file.getOriginalFilename())
                    .original(Boolean.TRUE)
                    .size(file.getSize())
                    .network(libFileLibrary.getNetwork())
                    .hash(ServiceConstants.NO_HASH)
                    .objectType(LibObjectTypeEnum.OT_AUDIO);
            log.debug("Persisting LibCloudObject: {}", cloudObject);
            cloudObject = cloudObjectRepository.saveAndFlush(cloudObject);

            libFileItem.setCloudObject(cloudObject);
            libFileItem.setLibrary(libFileLibrary);
            log.debug("Persisting LibDocumentObject: {}", libFileItem);
            libFileItemRepository.saveAndFlush(libFileItem);
        } catch (UploadException e) {
            log.error("There is a problem with uploading file to S3 Storage :{}", file.getOriginalFilename());

        } catch (S3Exception e) {
            log.error("There is a problem with uploading file to S3 Storage :{}", file.getOriginalFilename());
        } catch (TikaException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            log.error("There is a problem with processing the file  :{}", file.getOriginalFilename());
        } finally {
            bais.close();
            return libFileItem;
        }
    }

    public byte[] download(LibFileItem itemDB) throws IOException {
        byte[] result = null;

        if (itemDB == null) {
            return result;
        }
        LibFileItem libFileItem = libFileItemRepository.findOne(itemDB.getId());
        if (libFileItem == null) {
            return result;
        }
        LibCloudObject cloudObject = libFileItem.getCloudObject();
        InputStream stream = null;
        try {
            stream = s3Client.download(itemDB.getNetwork().getShortcut(), FILE + itemDB.getLibrary().getShortcut(), cloudObject.getUuid());
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("content-disposition", "filename=" + cloudObject.getOriginalName());
            responseHeaders.add("Content-Length", String.format("%d", cloudObject.getSize()));
            responseHeaders.add("Content-Type", cloudObject.getContentType());
            result = IOUtils.toByteArray(stream);
        } catch (S3Exception e) {
            log.error("There is a problem with uploading file to S3 Storage :{}", itemDB.getIdx());
        } catch (DownloadException e) {
            log.error("There is a problem with uploading file to S3 Storage :{}", itemDB.getIdx());
        } finally {
            stream.close();
        }
        return result;
    }

    @Transactional
    public void deleteFile(LibFileItem itemDB, String networkShortcut, String channelShortcut) {
        if (itemDB != null) {

            LibFileItem libFileItem = libFileItemRepository.findOne(itemDB.getId());
            if (itemDB != null) {

                LibCloudObject cloudObject = libFileItem.getCloudObject();
                try {
                    s3Client.delete(itemDB.getNetwork().getShortcut(), FILE + itemDB.getLibrary().getShortcut(), cloudObject.getUuid());
                    libFileItemRepository.delete(libFileItem);
                    libFileItemRepository.flush();
                    cloudObjectRepository.delete(cloudObject);
                    cloudObjectRepository.flush();

                } catch (DeleteException e) {
                    e.printStackTrace();
                } catch (S3Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
