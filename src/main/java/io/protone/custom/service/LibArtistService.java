package io.protone.custom.service;

import io.protone.domain.CorNetwork;
import io.protone.domain.LibArtist;
import io.protone.domain.enumeration.LibArtistTypeEnum;
import io.protone.repository.library.LibArtistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by lukaszozimek on 14/03/2017.
 */
@Service
@Transactional
public class LibArtistService {

    private final Logger log = LoggerFactory.getLogger(LibArtistService.class);

    @Inject
    private LibArtistRepository libArtistRepository;


    public LibArtist findOrSaveOne(String name, CorNetwork network) {
        if (name != null && network != null) {
            LibArtist libArtist = libArtistRepository.findOneByNameAndNetwork(name, network);
            if (libArtist != null) {

                log.debug("Resolved  LibArtist: {}", libArtist);
                return libArtist;
            }
            log.debug("Persisting new LibArtist: {}", name);
            return libArtistRepository.saveAndFlush(new LibArtist().name(name).type(LibArtistTypeEnum.AT_OTHER).network(network));
        }
        return null;
    }
}
