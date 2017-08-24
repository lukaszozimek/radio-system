package io.protone.library.service;

import com.google.common.base.Strings;
import io.protone.core.domain.CorImageItem;
import io.protone.core.domain.CorNetwork;
import io.protone.core.service.CorImageItemService;
import io.protone.library.domain.LibAlbum;
import io.protone.library.domain.LibArtist;
import io.protone.library.domain.enumeration.LibAlbumTypeEnum;
import io.protone.library.repository.LibAlbumRepository;
import org.apache.tika.exception.TikaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.inject.Inject;

import java.io.IOException;
import java.util.Set;

import static io.protone.core.constans.ServiceConstants.NO_DATA;


/**
 * Created by lukaszozimek on 14/03/2017.
 */
@Service
@Transactional
public class LibAlbumService {

    private final Logger log = LoggerFactory.getLogger(LibAlbumService.class);

    @Inject
    private LibAlbumRepository libAlbumRepository;

    @Inject
    private LibArtistService libArtistService;

    @Inject
    private CorImageItemService corImageItemService;

    public LibAlbum findOrSaveOne(String name, String albumArtistName, CorNetwork network) {

        LibArtist libArtist = libArtistService.findOrSaveOne(albumArtistName, network);
        if (libArtist != null) {
            if (Strings.isNullOrEmpty(name)) {
                log.debug("Persisting LibAlbum new empty album");
                return libAlbumRepository.saveAndFlush(new LibAlbum().name(NO_DATA).albumType(LibAlbumTypeEnum.AT_ALBUM).network(network));

            }
            LibAlbum libAlbum = libAlbumRepository.findOneByNameAndArtistAndNetwork(name, libArtist, network);
            if (libAlbum != null) {
                log.debug("Resolved LibAlbum: {}", libAlbum);
                return libAlbum;
            }
            log.debug("Persisting LibAlbum with Artist :{} and Name :{} ", libArtist, name);
            return libAlbumRepository.saveAndFlush(new LibAlbum().albumType(LibAlbumTypeEnum.AT_ALBUM).artist(libArtist).name(name).network(network));
        }

        log.debug("Persisting LibAlbum new empty album");
        return libAlbumRepository.saveAndFlush(new LibAlbum().name(NO_DATA).albumType(LibAlbumTypeEnum.AT_ALBUM).network(network));
    }

    public LibAlbum save(LibAlbum libAlbum, MultipartFile mainImage, MultipartFile[] booklet) throws IOException, TikaException, SAXException {
        log.debug("Persisting CorImageItem mainImage {}", mainImage.getOriginalFilename());
        CorImageItem corImageItem = corImageItemService.saveImageItem(mainImage);
        log.debug("Persisting CorImageItem booklet");
        Set<CorImageItem> coverBook = corImageItemService.saveImageItems(booklet);
        libAlbum.mainImage(corImageItem).cover(coverBook);
        return libAlbumRepository.saveAndFlush(libAlbum);
    }

    public LibAlbum saveOrUpdate(LibAlbum entity) {
        return null;
    }

    public Slice<LibAlbum> findAlbums(String networkShortcut, Pageable pagable) {
        return libAlbumRepository.findSliceByNetwork_Shortcut(networkShortcut,pagable);
    }

    public void deleteAlbum(Long id, String networkShortcut) {
    }

    public LibAlbum findAlbum(String networkShortcut, Long id) {
        return libAlbumRepository.findOneByIdAndNetwork_Shortcut(id,networkShortcut);
    }
}
