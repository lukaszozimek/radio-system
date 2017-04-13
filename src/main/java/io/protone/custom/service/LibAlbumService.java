package io.protone.custom.service;

import com.google.common.base.Strings;
import io.protone.domain.CorNetwork;
import io.protone.domain.LibAlbum;
import io.protone.domain.LibArtist;
import io.protone.domain.enumeration.LibAlbumTypeEnum;
import io.protone.repository.LibAlbumRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import static io.protone.custom.consts.ServiceConstants.NO_DATA;

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
            if (Strings.isNullOrEmpty(name)) {
                return libAlbumRepository.saveAndFlush(new LibAlbum().name(NO_DATA).albumType(LibAlbumTypeEnum.AT_ALBUM).network(network));

            }
            LibAlbum libAlbum = libAlbumRepository.findOneByNameAndArtistAndNetwork(name, libArtist, network);
            if (libAlbum != null) {
                return libAlbum;
            }

            return libAlbumRepository.saveAndFlush(new LibAlbum().albumType(LibAlbumTypeEnum.AT_ALBUM).artist(libArtist).name(name).network(network));
        }

        return libAlbumRepository.saveAndFlush(new LibAlbum().name(NO_DATA).albumType(LibAlbumTypeEnum.AT_ALBUM).network(network));
    }

    public LibAlbum findOrSaveOne(LibAlbum libAlbum,LibArtist artist, CorNetwork network) {
        if (libAlbum == null) {
            libAlbum = new LibAlbum().name(NO_DATA).albumType(LibAlbumTypeEnum.AT_OTHER).network(network);
        }
            LibAlbum album = libAlbumRepository.findOneByNameAndArtistAndNetwork(libAlbum.getName(), artist, network);
            if (album != null) {
                return album;
            }

            return libAlbumRepository.saveAndFlush(libAlbum);
        }
}
