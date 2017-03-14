package io.protone.custom.service;

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


}
