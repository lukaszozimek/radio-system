package io.protone.service.library;

import io.protone.domain.CorNetwork;
import io.protone.domain.LibArtist;
import io.protone.domain.LibLibrary;
import io.protone.domain.LibMediaItem;
import io.protone.repository.library.LibMediaItemRepository;
import io.protone.service.library.file.LibFileService;
import io.protone.web.rest.mapper.LibItemMapper;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.HttpHeaders;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

import static io.protone.service.library.file.impl.LibAudioFileService.AUDIO;
import static io.protone.service.library.file.impl.LibImageFileService.IMAGE;
import static io.protone.service.library.file.impl.LibVideoFileService.VIDEO;

@Service
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

    @Autowired
    @Qualifier("libAudioFileService")
    private LibFileService audioFileService;

    @Autowired
    @Qualifier("libVideoFileService")
    private LibFileService videoFileService;

    @Autowired
    @Qualifier("libImageFileService")
    private LibFileService imageFileService;

    private Map<String, LibFileService> libFileServiceMap;

    @PostConstruct
    public void buildFileServiceMap() {
        libFileServiceMap = new HashMap<>();
        libFileServiceMap.put(AUDIO, audioFileService);
        libFileServiceMap.put(VIDEO, videoFileService);
        libFileServiceMap.put(IMAGE, imageFileService);
    }

    @Transactional
    public LibMediaItem getMediaItem(String networkShortcut, String libraryShortcut, String idx) {
        Optional<LibMediaItem> optionalItemDB = itemRepository.findByNetwork_ShortcutAndLibrary_ShortcutAndIdx(networkShortcut, libraryShortcut, idx);
        return optionalItemDB.orElse(null);
    }

    @Transactional
    public List<LibMediaItem> getMediaItems(String networkShortcut, String libraryShortcut, Pageable pagable) {
        List<LibMediaItem> itemsDB = itemRepository.findByNetwork_ShortcutAndLibrary_Shortcut(networkShortcut, libraryShortcut, pagable);
        return itemsDB;
    }


    @Transactional
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

    @Transactional
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
            LibMediaItem libMediaItem = libFileServiceMap.get(metadata.get(HttpHeaders.CONTENT_TYPE)).saveFile(bais, metadata, fileName, file.getSize(), libraryDB);
            result.add(libMediaItem);

        }
        return result;
    }

    @Transactional
    public byte[] download(String networkShortcut, String libraryShortcut, String idx) throws IOException {
        LibMediaItem itemDB = getMediaItem(networkShortcut, libraryShortcut, idx);
        return libFileServiceMap.get(itemDB.getItemType()).download(itemDB);
    }


    public void deleteItem(String networkShortcut, String libraryShortcut, String idx) {
        LibMediaItem itemToDelete = getMediaItem(networkShortcut, libraryShortcut, idx);
        libFileServiceMap.get(itemToDelete.getItemType()).deleteFile(itemToDelete);
        itemRepository.delete(itemToDelete);
    }

    @Transactional
    public void deleteItem(LibMediaItem libMediaItem) {
        libFileServiceMap.get(libMediaItem.getItemType()).deleteFile(libMediaItem);
        itemRepository.delete(libMediaItem);
    }


}
