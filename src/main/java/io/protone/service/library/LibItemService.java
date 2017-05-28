package io.protone.service.library;

import io.protone.config.s3.S3Client;
import io.protone.repository.library.*;
import io.protone.service.cor.CorUserService;
import io.protone.service.library.file.LibFileService;
import io.protone.service.library.metadata.LibAudioMetadataService;
import io.protone.service.metadata.ProtoneMetadataProperty;
import io.protone.web.rest.mapper.LibItemMapper;
import io.protone.domain.*;
import io.protone.custom.utils.MediaUtils;
import io.protone.security.SecurityUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

import static io.protone.service.library.file.impl.LibAudioFileService.AUDIO;
import static io.protone.service.library.file.impl.LibImageFileService.IMAGE;
import static io.protone.service.library.file.impl.LibVideoFileService.VIDEO;

@Service
@Transactional
public class LibItemService {

    private final Logger log = LoggerFactory.getLogger(LibItemService.class);

    @Inject
    private LibLibraryService libraryService;

    @Inject
    private LibMediaItemRepository itemRepository;

    @Inject
    private LibItemMapper itemMapper;

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

    @Named(value = "libAudioFileService")
    private LibFileService audioFileService;

    @Named(value = "libVideoFileService")
    private LibFileService videoFileService;

    @Named(value = "libImageFileService")
    private LibFileService imageFileService;

    private Map<String, LibFileService> libFileServiceMap;

    @PostConstruct
    public void buildFileServiceMap() {
        libFileServiceMap = new HashMap<>();
        libFileServiceMap.put(AUDIO, audioFileService);
        libFileServiceMap.put(VIDEO, videoFileService);
        libFileServiceMap.put(IMAGE, imageFileService);
    }

    public LibMediaItem getMediaItem(String networkShortcut, String libraryShortcut, String idx) {
        Optional<LibMediaItem> optionalItemDB = itemRepository.findByNetwork_ShortcutAndLibrary_ShortcutAndIdx(networkShortcut, libraryShortcut, idx);
        return optionalItemDB.orElse(null);
    }

    public List<LibMediaItem> getMediaItems(String networkShortcut, String libraryShortcut, Pageable pagable) {
        List<LibMediaItem> itemsDB = itemRepository.findByNetwork_ShortcutAndLibrary_Shortcut(networkShortcut, libraryShortcut, pagable);
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

    public List<LibMediaItem> upload(String networkShortcut, String libraryShortcut, MultipartFile[] files) throws IOException, TikaException, SAXException {

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
            ByteArrayInputStream bais = new ByteArrayInputStream(file.getBytes());
            Parser parser = new AutoDetectParser();
            BodyContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            ParseContext pcontext = new ParseContext();
            parser.parse(bais, handler, metadata, pcontext);
            LibMediaItem libMediaItem = libFileServiceMap.get(metadata.get(ProtoneMetadataProperty.CONTENT_TYPE_HINT)).saveFile(bais, metadata, fileName, file.getSize(), libraryDB);
            result.add(libMediaItem);

        }
        return result;
    }

    public byte[] download(String networkShortcut, String libraryShortcut, String idx) throws IOException {
        LibMediaItem itemDB = getItemFromDB(networkShortcut, libraryShortcut, idx);
        return libFileServiceMap.get(itemDB.getItemType()).download(itemDB);
    }

    public LibMediaItem getItemFromDB(String networkShortcut, String libraryShortcut, String idx) {
        Optional<LibMediaItem> optItemDB = itemRepository.findByNetwork_ShortcutAndLibrary_ShortcutAndIdx(networkShortcut, libraryShortcut, idx);
        return optItemDB.orElse(null);
    }

    public void deleteItem(String networkShortcut, String libraryShortcut, String idx) {
        LibMediaItem itemToDelete = getItemFromDB(networkShortcut, libraryShortcut, idx);
        libFileServiceMap.get(itemToDelete.getItemType()).deleteFile(itemToDelete);
        itemRepository.delete(itemToDelete);
    }

    public void deleteItem(LibMediaItem libMediaItem) {
        libFileServiceMap.get(libMediaItem.getItemType()).deleteFile(libMediaItem);
        itemRepository.delete(libMediaItem);
    }


}
