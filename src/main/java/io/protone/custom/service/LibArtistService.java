package io.protone.custom.service;

import io.protone.domain.CorNetwork;
import io.protone.domain.LibArtist;
import io.protone.domain.enumeration.LibArtistTypeEnum;
import io.protone.repository.LibArtistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by lukaszozimek on 14/03/2017.
 */
@Service
@Transactional
public class LibArtistService {

    @Inject
    private LibArtistRepository libArtistRepository;


    public LibArtist findOrSaveOne(String name, CorNetwork network) {
        if (name != null && network != null) {
            LibArtist libArtist = libArtistRepository.findOneByNameAndNetwork(name, network);
            if (libArtist != null) {
                return libArtist;
            }
            return libArtistRepository.saveAndFlush(new LibArtist().name(name).type(LibArtistTypeEnum.AT_OTHER).network(network));
        }
        return null;
    }
}
