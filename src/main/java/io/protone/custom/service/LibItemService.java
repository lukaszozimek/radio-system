package io.protone.custom.service;

import io.protone.config.s3.S3Client;
import io.protone.config.s3.exceptions.*;
import io.protone.repository.library.*;
import io.protone.service.constans.ServiceConstants;
import io.protone.service.library.LibArtistService;
import io.protone.service.library.LibLibraryService;
import io.protone.service.library.LibMarkerService;
import io.protone.web.rest.mapper.LibItemMapper;
import io.protone.domain.*;
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
import org.springframework.data.domain.Pageable;
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
    private LibMediaItemRepository itemRepository;

    @Inject
    private LibItemMapper itemMapper;

    @Inject
    private LibCloudObjectRepository cloudObjectRepository;

    @Inject
    private MediaUtils mediaUtils;

    @Inject
    private LibAudioObjectRepository audioObjectRepository;

    @Inject
    private CorUserService corUserService;

    @Inject
    private LibMetadataService libMetadataService;

    @Inject
    private LibArtistService libArtistService;

    @Inject
    private LibAlbumService libAlbumService;

    @Inject
    private LibMarkerService libMarkerService;

    @Inject
    private LibTrackService libTrackService;

    @Inject
    private LibLabelService libLabelService;

    public LibMediaItem getMediaItem(String networkShortcut, String libraryShortcut, String idx) {
        Optional<LibMediaItem> optionalItemDB = itemRepository.findByLibrary_ShortcutAndIdx(libraryShortcut, idx);
        return optionalItemDB.orElse(null);
    }

    public List<LibMediaItem> getMediaItems(String networkShortcut, String libraryShortcut, Pageable pagable) {
        List<LibMediaItem> itemsDB = itemRepository.findByLibrary_Shortcut(libraryShortcut, pagable);
        return itemsDB;
    }


    public LibMediaItem update(LibMediaItem libMediaItem, CorNetwork corNetwork) {
        LibArtist artist = new LibArtist();
        if (libMediaItem.getArtist() != null) {
            artist = libArtistService.findOrSaveOne(libMediaItem.getArtist().getName(), corNetwork);
            libMediaItem.setArtist(artist);
        }
        if (libMediaItem.getAlbum() != null) {
            libMediaItem.setAlbum(libAlbumService.findOrSaveOne(libMediaItem.getAlbum(), artist, corNetwork));
        }
        libMediaItem.setLabel(libLabelService.saveLibLabel(libMediaItem.getLabel()).orElse(null));
        libMediaItem.setTrack(libTrackService.saveLibTrack(libMediaItem.getTrack()).orElse(null));
        libMediaItem.setMarkers(libMarkerService.saveLibMarkers(libMediaItem.getMarkers()).orElse(null));

        return itemRepository.saveAndFlush(libMediaItem);
    }

    public List<LibMediaItem> upload(String networkShortcut, String libraryShortcut, MultipartFile[] files) throws IOException {

        List<LibMediaItem> result = new ArrayList<>();

        if (files == null || files.length == 0) {
            return result;
        }
        LibLibrary libraryDB = libraryService.findLibrary(networkShortcut, libraryShortcut);
        if (libraryDB == null) {
            return result;
        }
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

                CorUser currentUser = corUserService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin()).get();
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

                result.add(libMediaItem);
            } catch (UploadException e) {
                log.error("There is a problem with uploading file to S3 Storage :{}", fileName);

            } catch (S3Exception e) {
                log.error("There is a problem with uploading file to S3 Storage :{}", fileName);
            } catch (TikaException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                log.error("There is a problem with processing the file  :{}", fileName);
            } finally {
                bais.close();
            }
        }
        return result;
    }

    public byte[] download(String networkShortcut, String libraryShortcut, String idx) throws IOException {

        byte[] result = null;

        LibMediaItem itemDB = getItemFromDB(networkShortcut, libraryShortcut, idx);

        if (itemDB == null) {
            return result;
        }
        List<LibAudioObject> audioObjects = audioObjectRepository.findByMediaItem(itemDB);

        if (audioObjects == null || audioObjects.size() == 0) {
            return result;
        }
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
            log.error("There is a problem with uploading file to S3 Storage :{}", idx);
        } catch (DownloadException e) {
            log.error("There is a problem with uploading file to S3 Storage :{}", idx);
        } finally {
            stream.close();
        }
        return result;
    }

    public LibMediaItem getItemFromDB(String networkShortcut, String libraryShortcut, String idx) {
        Optional<LibMediaItem> optItemDB = itemRepository.findByLibrary_ShortcutAndIdx(libraryShortcut, idx);
        return optItemDB.orElse(null);
    }

    public void deleteItem(String networkShortcut, String libraryShortcut, String idx) {
        LibMediaItem itemToDelete = getItemFromDB(networkShortcut, libraryShortcut, idx);
        deleteItemFromMinio(itemToDelete);
        itemRepository.delete(itemToDelete);
    }

    public void deleteItem(LibMediaItem libMediaItem) {
        deleteItemFromMinio(libMediaItem);
        itemRepository.delete(libMediaItem);
    }

    private void deleteItemFromMinio(LibMediaItem libMediaItem) {
        if (libMediaItem != null) {

            List<LibAudioObject> audioObjects = audioObjectRepository.findByMediaItem(libMediaItem);
            if (audioObjects != null || !audioObjects.isEmpty()) {
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
        }
    }


}
