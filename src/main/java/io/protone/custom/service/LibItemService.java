package io.protone.custom.service;

import io.protone.config.s3.S3Client;
import io.protone.config.s3.exceptions.*;
import io.protone.service.constans.ServiceConstants;
import io.protone.service.library.LibLibraryService;
import io.protone.service.library.LibMarkerService;
import io.protone.service.mapper.LibItemMapper;
import io.protone.domain.*;
import io.protone.repository.*;
import io.protone.repository.custom.CustomCorUserRepository;
import io.protone.repository.custom.CustomLibAudioObjectRepository;
import io.protone.repository.custom.CustomLibMediaItemRepository;
import io.protone.custom.service.dto.LibItemPT;
import io.protone.custom.utils.MediaUtils;
import io.protone.security.SecurityUtils;
import org.apache.commons.io.IOUtils;
import org.apache.tika.detect.Detector;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AutoDetectParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class LibItemService {

    private final Logger log = LoggerFactory.getLogger(LibItemService.class);

    @Inject
    private S3Client s3Client;

    @Inject
    private LibLibraryService libraryService;

    @Inject
    private CustomLibMediaItemRepository itemRepository;

    @Inject
    private LibItemMapper itemMapper;

    @Inject
    private LibCloudObjectRepository cloudObjectRepository;

    @Inject
    private MediaUtils mediaUtils;

    @Inject
    private CustomLibMediaItemRepository mediaItemRepository;

    @Inject
    private CustomLibAudioObjectRepository audioObjectRepository;

    @Inject
    private CustomCorUserRepository userRepository;

    @Inject
    private LibMetadataService libMetadataService;

    @Inject
    private LibArtistService libArtistRepository;

    @Inject
    private LibAlbumService libAlbumRepository;

    @Inject
    private LibMarkerService libMarkerService;

    @Inject
    private LibTrackRepository libTrackRepository;

    @Inject
    private LibLabelRepository libLabelRepository;

    public LibItemPT getItem(String networkShortcut, String libraryShortcut, String idx) {
        LibItemPT result = null;
        LibLibrary libraryDB = libraryService.findLibrary(networkShortcut, libraryShortcut);
        if (libraryDB == null)
            return result;

        Optional<LibMediaItem> optionalItemDB = itemRepository.findByLibraryAndIdx(libraryDB, idx);
        result = itemMapper.DB2DTO(optionalItemDB.orElse(null));
        return result;
    }

    public List<LibItemPT> getItem(String networkShortcut, String libraryShortcut) {
        List<LibItemPT> results = new ArrayList<>();
        LibLibrary libraryDB = libraryService.findLibrary(networkShortcut, libraryShortcut);
        if (libraryDB == null)
            return results;

        List<LibMediaItem> itemsDB = itemRepository.findByLibrary(libraryDB);
        results = itemMapper.DBs2DTOs(itemsDB);
        return results;
    }

    @Transactional
    public void deleteItem(String networkShortcut, String libraryShortcut, String idx) {

        LibMediaItem itemToDelete = getItemFromDB(networkShortcut, libraryShortcut, idx);
        if (itemToDelete != null) {

            List<LibAudioObject> audioObjects = audioObjectRepository.findByMediaItem(itemToDelete);
            if (audioObjects != null || audioObjects.size() > 0) {
                for (LibAudioObject audioObject : audioObjects) {
                    LibCloudObject cloudObject = audioObject.getCloudObject();
                    try {
                        s3Client.delete(cloudObject.getUuid());
                        audioObjectRepository.delete(audioObject);
                        audioObjectRepository.flush();
                        cloudObjectRepository.delete(cloudObject);
                        cloudObjectRepository.flush();
                    } catch (DeleteException e) {
                        e.printStackTrace();
                    } catch (S3Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            itemRepository.delete(itemToDelete);
        }

    }

    public LibItemPT update(LibItemPT libItemPT, CorNetwork corNetwork) {
        LibMediaItem libItem = itemMapper.DTO2DB(libItemPT, corNetwork);
        LibArtist artist = libArtistRepository.findOrSaveOne(libItem.getArtist().getName(), corNetwork);
        libItem.setArtist(artist);
        LibAlbum album = libAlbumRepository.findOrSaveOne(libItem.getAlbum(), artist, corNetwork);
        libItem.setAlbum(album);
        if (libItem.getLabel() != null) {
            libLabelRepository.saveAndFlush(libItem.getLabel());
        }
        if (libItem.getTrack() != null) {
            libTrackRepository.saveAndFlush(libItem.getTrack());
        }
        if (libItem.getMarkers() != null) {
            libItem.setMarkers(libMarkerService.saveLibMarkers(libItem.getMarkers()));
        }
        LibMediaItem item = itemRepository.saveAndFlush(libItem);

        return itemMapper.DB2DTO(item);
    }

    public List<LibItemPT> upload(String networkShortcut, String libraryShortcut, MultipartFile[] files) throws IOException {

        List<LibItemPT> result = new ArrayList<>();

        if (files == null || files.length == 0)
            return result;

        LibLibrary libraryDB = libraryService.findLibrary(networkShortcut, libraryShortcut);
        if (libraryDB == null)
            return result;

        for (MultipartFile file : files) {

            String fileName = file.getOriginalFilename();
            String fileUUID = UUID.randomUUID().toString();

            ByteArrayInputStream bais = new ByteArrayInputStream(file.getBytes());
            AutoDetectParser parser = new AutoDetectParser();
            Detector detector = parser.getDetector();
            Metadata md = new Metadata();
            md.add(Metadata.RESOURCE_NAME_KEY, fileName);
            MediaType mediaType = detector.detect(bais, md);
            String contentType = mediaType.toString();

            try {

                log.debug("Uploading Media Item: {} ", fileUUID);
                s3Client.upload(fileUUID, bais, contentType);
                LibCloudObject cloudObject = new LibCloudObject();
                cloudObject.setUuid(fileUUID);
                cloudObject.setContentType(contentType);
                cloudObject.setOriginalName(fileName);
                cloudObject.setOriginal(Boolean.TRUE);
                cloudObject.setSize(file.getSize());
                cloudObject.setCreateDate(ZonedDateTime.now());

                CorUser currentUser = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
                CorNetwork corNetwork = currentUser.getNetworks().stream().findAny().orElse(null);

                cloudObject.setCreatedBy(currentUser);
                cloudObject.setNetwork(libraryDB.getNetwork());
                cloudObject.setHash(ServiceConstants.NO_HASH);
                cloudObject.network(corNetwork);

                log.debug("Persisting LibCloudObject: {}", cloudObject);
                cloudObject = cloudObjectRepository.saveAndFlush(cloudObject);
                LibMediaItem libMediaItem = new LibMediaItem();
                LibAudioObject audioObject = new LibAudioObject();
                libMediaItem = libMetadataService.resolveMetadata(file, libraryDB, corNetwork, libMediaItem, audioObject);

                audioObject.setCloudObject(cloudObject);
                audioObject.setMediaItem(libMediaItem);

                log.debug("Persisting LibAudioObject: {}", audioObject);
                audioObjectRepository.saveAndFlush(audioObject);

                result.add(itemMapper.DB2DTO(libMediaItem));
            } catch (UploadException e) {
                e.printStackTrace();
            } catch (S3Exception e) {
                e.printStackTrace();
            } catch (TikaException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } finally {
                bais.close();
            }
        }
        return result;
    }

    public byte[] download(String networkShortcut, String libraryShortcut, String idx) throws IOException {

        byte[] result = null;

        LibMediaItem itemDB = getItemFromDB(networkShortcut, libraryShortcut, idx);

        if (itemDB == null)
            return result;

        List<LibAudioObject> audioObjects = audioObjectRepository.findByMediaItem(itemDB);

        if (audioObjects == null || audioObjects.size() == 0)
            return result;

        LibAudioObject audioObject = audioObjects.iterator().next();

        LibCloudObject cloudObject = audioObject.getCloudObject();

        InputStream stream = null;
        try {
            stream = s3Client.download(cloudObject.getUuid());

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("content-disposition", "filename=" + cloudObject.getOriginalName());
            responseHeaders.add("Content-Length", String.format("%d", cloudObject.getSize()));

            responseHeaders.add("Content-Type", cloudObject.getContentType());

            result = IOUtils.toByteArray(stream);
        } catch (S3Exception e) {
            e.printStackTrace();
        } catch (DownloadException e) {
            e.printStackTrace();
        } finally {
            stream.close();
        }
        return result;
    }

    public LibMediaItem getItemFromDB(String networkShortcut, String libraryShortcut, String idx) {
        LibMediaItem result = null;
        LibLibrary libraryDB = libraryService.findLibrary(networkShortcut, libraryShortcut);
        if (libraryDB == null)
            return result;

        Optional<LibMediaItem> optItemDB = itemRepository.findByLibraryAndIdx(libraryDB, idx);

        return optItemDB.orElse(null);
    }

}
