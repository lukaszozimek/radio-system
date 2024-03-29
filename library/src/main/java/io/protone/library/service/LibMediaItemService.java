package io.protone.library.service;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorImageItemService;
import io.protone.core.service.CorPropertyService;
import io.protone.library.domain.LibArtist;
import io.protone.library.domain.LibMediaItem;
import io.protone.library.domain.LibMediaLibrary;
import io.protone.library.domain.enumeration.LibItemTypeEnum;
import io.protone.library.repository.LibMediaItemRepository;
import io.protone.library.service.file.LibFileService;
import io.protone.library.service.metadata.document.SupportedDocumentContentTypes;
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
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static io.protone.library.service.file.impl.LibAudioFileService.AUDIO;
import static io.protone.library.service.file.impl.LibDocumentFileService.DOCUMENT;
import static io.protone.library.service.file.impl.LibImageFileService.IMAGE;
import static io.protone.library.service.file.impl.LibVideoFileService.VIDEO;

//TODO Extract uploading to separate service
@Service
public class LibMediaItemService {

    private static final String CONTENT_TYPE_SEPARATOR = "/";

    private final Logger log = LoggerFactory.getLogger(LibMediaItemService.class);
    @Inject
    private LibLibraryMediaService libraryService;

    @Inject
    private LibMediaItemRepository itemRepository;

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

    @Inject
    private CorImageItemService corImageItemService;

    @Inject
    private CorPropertyService corPropertyService;

    @Autowired
    @Qualifier("libAudioFileService")
    private LibFileService audioFileService;

    @Autowired
    @Qualifier("libVideoFileService")
    private LibFileService videoFileService;

    @Autowired
    @Qualifier("libImageFileService")
    private LibFileService imageFileService;

    @Autowired
    @Qualifier("libDocumentFileService")
    private LibFileService libDocumentFileService;

    private Map<String, LibFileService> libItemTypeFileServiceMap;

    private Map<String, String> contentTypeLibItemTypeMap;

    @PostConstruct
    public void buildFileServiceMap() {
        libItemTypeFileServiceMap = new HashMap<>();
        libItemTypeFileServiceMap.put(LibItemTypeEnum.IT_AUDIO.name(), audioFileService);
        libItemTypeFileServiceMap.put(LibItemTypeEnum.IT_VIDEO.name(), videoFileService);
        libItemTypeFileServiceMap.put(LibItemTypeEnum.IT_IMAGE.name(), imageFileService);
        libItemTypeFileServiceMap.put(LibItemTypeEnum.IT_DOCUMENT.name(), libDocumentFileService);
        contentTypeLibItemTypeMap = new HashMap<>();
        contentTypeLibItemTypeMap.put(AUDIO, LibItemTypeEnum.IT_AUDIO.name());
        contentTypeLibItemTypeMap.put(VIDEO, LibItemTypeEnum.IT_VIDEO.name());
        contentTypeLibItemTypeMap.put(IMAGE, LibItemTypeEnum.IT_IMAGE.name());
        contentTypeLibItemTypeMap.put(DOCUMENT, LibItemTypeEnum.IT_DOCUMENT.name());
    }

    @Transactional(readOnly = true)
    public LibMediaItem getMediaItem(String networkShortcut, String libraryShortcut, String idx) {
        Optional<LibMediaItem> optionalItemDB = itemRepository.findByNetwork_ShortcutAndLibrary_ShortcutAndIdx(networkShortcut, libraryShortcut, idx);
        return optionalItemDB.orElse(null);
    }

    @Transactional
    public void moveMediaItem(String networkShortcut, String libraryShortcut, String idx, String dstLibararyShortcut) {
        LibMediaLibrary dstLibarary = this.libraryService.findLibrary(networkShortcut, dstLibararyShortcut);
        if (dstLibarary != null) {
            Optional<LibMediaItem> optionalItemDB = itemRepository.findByNetwork_ShortcutAndLibrary_ShortcutAndIdx(networkShortcut, libraryShortcut, idx);
            if (optionalItemDB.isPresent()) {
                optionalItemDB.get().setLibrary(dstLibarary);
                itemRepository.saveAndFlush(optionalItemDB.get());
            }
        }
    }


    @Transactional
    public Slice<LibMediaItem> getMediaItems(String networkShortcut, String libraryShortcut, Pageable pagable) {
        return itemRepository.findSliceByNetwork_ShortcutAndLibrary_Shortcut(networkShortcut, libraryShortcut, pagable);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public LibMediaItem saveMediaItem(LibMediaItem libMediaItem) {
        return itemRepository.save(libMediaItem);
    }


    @Transactional
    public LibMediaItem update(MultipartFile[] covers, LibMediaItem libMediaItem, CorNetwork corNetwork) {
        return update(libMediaItem, corNetwork);
    }

    @Transactional
    public LibMediaItem update(LibMediaItem libMediaItem, CorNetwork corNetwork) {
        LibArtist artist = new LibArtist();
        if (libMediaItem.getArtist() != null) {
            artist = libArtistService.findOrSaveOne(libMediaItem.getArtist().getName(), corNetwork);
            libMediaItem.setArtist(artist);
        }
        if (libMediaItem.getAlbum() != null) {
            libMediaItem.setAlbum(libAlbumService.findOrSaveOne(libMediaItem.getAlbum().getName(), artist.getName(), corNetwork));
        }
        if (libMediaItem.getProperites() != null) {
            libMediaItem.setProperites(libMediaItem.getProperites().stream().map(corPropertyValue -> corPropertyService.saveCorProperty(corPropertyValue)).collect(Collectors.toSet()));
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
        LibMediaLibrary libraryDB = libraryService.findLibrary(networkShortcut, libraryShortcut);
        if (libraryDB == null) {
            return result;
        }

        for (MultipartFile file : files) {
            ByteArrayInputStream bais = new ByteArrayInputStream(file.getBytes());
            byte[] inputStream = new byte[bais.available()];
            bais.read(inputStream);
            Supplier<ByteArrayInputStream> inputStreamSupplier = () -> new ByteArrayInputStream(inputStream);
            String fileName = file.getOriginalFilename();
            Parser parser = new AutoDetectParser();
            BodyContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            ParseContext pcontext = new ParseContext();
            parser.parse(inputStreamSupplier.get(), handler, metadata, pcontext);
            String libItemType = contentTypeLibItemTypeMap.get(resolveType(metadata));
            if (!Strings.isNullOrEmpty(libItemType)) {
                log.debug("Saving file with CONTENT_TYPE: {}", metadata.get(HttpHeaders.CONTENT_TYPE));
                LibMediaItem libMediaItem = libItemTypeFileServiceMap.get(libItemType).saveFile(inputStreamSupplier.get(), metadata, fileName, file.getSize(), libraryDB);
                result.add(libMediaItem);
            } else {
                log.warn("File with name :{} cann't be added into Library because it contect type is not supported yet. CONTENT_TYPE :{}", fileName, metadata.get(HttpHeaders.CONTENT_TYPE));
            }


        }
        return result;
    }

    @Transactional
    public LibMediaItem upload(String networkShortcut, String libraryShortcut, MultipartFile file) throws IOException, TikaException, SAXException {
        LibMediaLibrary libraryDB = libraryService.findLibrary(networkShortcut, libraryShortcut);
        if (libraryDB == null) {
            return null;
        }

        String fileName = file.getOriginalFilename();
        ByteArrayInputStream bais = new ByteArrayInputStream(file.getBytes());
        byte[] inputStream = new byte[bais.available()];
        bais.read(inputStream);
        Supplier<ByteArrayInputStream> inputStreamSupplier = () -> new ByteArrayInputStream(inputStream);
        Parser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext pcontext = new ParseContext();
        parser.parse(inputStreamSupplier.get(), handler, metadata, pcontext);
        String libItemType = contentTypeLibItemTypeMap.get(resolveType(metadata));
        if (!Strings.isNullOrEmpty(libItemType)) {
            log.debug("Saving file with CONTENT_TYPE: {}", metadata.get(HttpHeaders.CONTENT_TYPE));
            LibMediaItem libMediaItem = libItemTypeFileServiceMap.get(libItemType).saveFile(inputStreamSupplier.get(), metadata, fileName, file.getSize(), libraryDB);
            return libMediaItem;
        } else {
            log.warn("File with name :{} cann't be added into Library because it contect type is not supported yet. CONTENT_TYPE :{}", fileName, metadata.get(HttpHeaders.CONTENT_TYPE));
        }
        return null;
    }

    @Transactional
    public LibMediaItem updateItemContent(String networkShortcut, String libraryShortcut, String idx, MultipartFile file) throws IOException, TikaException, SAXException {
        LibMediaLibrary libraryDB = libraryService.findLibrary(networkShortcut, libraryShortcut);
        if (libraryDB == null) {
            return null;
        }
        LibMediaItem mediaItem = getMediaItem(networkShortcut, libraryShortcut, idx);
        if (mediaItem == null) {
            return null;
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(file.getBytes());
        byte[] inputStream = new byte[bais.available()];
        bais.read(inputStream);
        Supplier<ByteArrayInputStream> inputStreamSupplier = () -> new ByteArrayInputStream(inputStream);
        Parser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext pcontext = new ParseContext();
        parser.parse(inputStreamSupplier.get(), handler, metadata, pcontext);
        String libItemType = contentTypeLibItemTypeMap.get(resolveType(metadata));
        if (!Strings.isNullOrEmpty(libItemType)) {
            log.debug("Saving file with CONTENT_TYPE: {}", metadata.get(HttpHeaders.CONTENT_TYPE));
            LibMediaItem libMediaItem = libItemTypeFileServiceMap.get(libItemType).updateContent(inputStreamSupplier.get(), metadata, mediaItem.contentAvailable(true), file.getSize(), libraryDB);
            return libMediaItem;
        } else {
            log.warn("File with name :{} cann't be added into Library because it contect type is not supported yet. CONTENT_TYPE :{}", mediaItem.getName(), metadata.get(HttpHeaders.CONTENT_TYPE));
        }
        return null;
    }

    @Transactional
    public byte[] download(String networkShortcut, String libraryShortcut, String idx) throws IOException {
        LibMediaItem itemDB = getMediaItem(networkShortcut, libraryShortcut, idx);
        return libItemTypeFileServiceMap.get(itemDB.getItemType().name()).download(itemDB);
    }


    public void deleteItem(String networkShortcut, String libraryShortcut, String idx) {
        LibMediaItem itemToDelete = getMediaItem(networkShortcut, libraryShortcut, idx);
        libMarkerService.detachLibMarkers(itemToDelete.getMarkers());
        corPropertyService.detachProperties(itemToDelete.getProperites());
        itemToDelete.setProperites(Sets.newHashSet());
        itemToDelete.setMarkers(Sets.newHashSet());
        itemRepository.saveAndFlush(itemToDelete);
        libItemTypeFileServiceMap.get(itemToDelete.getItemType().name()).deleteFile(itemToDelete);
        libMarkerService.deleteMarkers(itemToDelete.getMarkers());
        corPropertyService.deleteProperties(itemToDelete.getProperites());
        itemRepository.delete(itemToDelete);
    }

    @Transactional
    public void deleteItem(LibMediaItem libMediaItem) {
        if (libMediaItem != null) {
            libMarkerService.detachLibMarkers(libMediaItem.getMarkers());
            corPropertyService.detachProperties(libMediaItem.getProperites());
            libMediaItem.setProperites(Sets.newHashSet());
            libMediaItem.setMarkers(Sets.newHashSet());
            itemRepository.saveAndFlush(libMediaItem);
            libItemTypeFileServiceMap.get(libMediaItem.getItemType().name()).deleteFile(libMediaItem);
            libMarkerService.deleteMarkers(libMediaItem.getMarkers());
            corPropertyService.deleteProperties(libMediaItem.getProperites());
            itemRepository.delete(libMediaItem);
        }
    }

    private String resolveType(Metadata metadata) {
        if (contentTypeLibItemTypeMap.containsKey(metadata.get(HttpHeaders.CONTENT_TYPE).split(CONTENT_TYPE_SEPARATOR)[0])) {
            return metadata.get(HttpHeaders.CONTENT_TYPE).split(CONTENT_TYPE_SEPARATOR)[0];
        }
        if (isDocumentType(metadata.get(HttpHeaders.CONTENT_TYPE))) {
            return DOCUMENT;
        }
        return "";
    }

    private boolean isDocumentType(String contentType) {
        return Arrays.stream(SupportedDocumentContentTypes.values()).anyMatch(supportedDocumentContentTypes -> supportedDocumentContentTypes.getName().equalsIgnoreCase(contentType));
    }


}
