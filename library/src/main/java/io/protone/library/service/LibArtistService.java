package io.protone.library.service;


import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorImageItem;
import io.protone.core.service.CorImageItemService;
import io.protone.library.domain.LibArtist;
import io.protone.library.domain.enumeration.LibArtistTypeEnum;
import io.protone.library.repository.LibArtistRepository;
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

/**
 * Created by lukaszozimek on 14/03/2017.
 */
@Service
public class LibArtistService {
    private final Logger log = LoggerFactory.getLogger(LibArtistService.class);

    @Inject
    private LibArtistRepository libArtistRepository;

    @Inject
    private CorImageItemService corImageItemService;

    @Transactional
    public LibArtist findOrSaveOne(String name, CorChannel network) {
        if (name != null && network != null) {
            LibArtist libArtist = libArtistRepository.findOneByNameAndChannel(name, network);
            if (libArtist != null) {
                log.debug("Resolved  LibArtist: {}", libArtist);
                return libArtist;
            }
            log.debug("Persisting new LibArtist: {}", name);
            return libArtistRepository.saveAndFlush(new LibArtist().name(name).type(LibArtistTypeEnum.AT_OTHER).channel(network));
        }
        return null;
    }

    @Transactional
    public LibArtist save(LibArtist libArtist, MultipartFile avatar) throws IOException, TikaException, SAXException {
        CorImageItem corImageItem = corImageItemService.saveImageItem(avatar, libArtist.getChannel().getOrganization());
        libArtist.mainImage(corImageItem);
        return libArtistRepository.saveAndFlush(libArtist);
    }

    @Transactional
    public LibArtist createOrUpdateArtist(LibArtist entity) {
        return libArtistRepository.saveAndFlush(entity);
    }

    @Transactional
    public LibArtist createOrUpdateArtistWithImage(LibArtist entity, MultipartFile cover) throws TikaException, IOException, SAXException {
        return save(entity, cover);
    }

    @Transactional
    public Slice<LibArtist> findArtists(String organizationShortcut, String channelShortcut, Pageable pagable) {
        return libArtistRepository.findSliceByChannel_Organization_ShortcutAndChannel_Shortcut(organizationShortcut, channelShortcut, pagable);
    }

    @Transactional
    public void deleteArtist(Long id, String organizationShortcut, String channelShortcut) {
        libArtistRepository.deleteByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(id, organizationShortcut, channelShortcut);
    }

    @Transactional
    public LibArtist findArtist(String organizationShortcut, String channelShortcut, Long id) {
        return libArtistRepository.findOneByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(id, organizationShortcut, channelShortcut);
    }
}
