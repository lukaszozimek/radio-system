package io.protone.custom.service;

import io.protone.domain.CorNetwork;
import io.protone.domain.LibAlbum;
import io.protone.domain.LibArtist;
import io.protone.domain.enumeration.LibAlbumTypeEnum;
import io.protone.repository.LibAlbumRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by lukaszozimek on 14/03/2017.
 */
@Service

@Transactional
public class LibAlbumService {
    @Inject
    private LibAlbumRepository libAlbumRepository;

    @Inject
    private LibArtistService libArtistService;

    public LibAlbum findOrSaveOne(String name, String albumArtistName, CorNetwork network) {
        LibArtist libArtist = libArtistService.findOrSaveOne(albumArtistName, network);
        if (libArtist != null) {
            LibAlbum libAlbum = libAlbumRepository.findOneByNameAndArtistAndNetwork(name, libArtist, network);
            if (libAlbum != null) {
                return libAlbum;
            }
            return libAlbumRepository.saveAndFlush(new LibAlbum().albumType(LibAlbumTypeEnum.AT_ALBUM).artist(libArtist).name(name).network(network));
        }
        return libAlbumRepository.saveAndFlush(new LibAlbum().name(name).albumType(LibAlbumTypeEnum.AT_ALBUM).network(network));
    }

}
