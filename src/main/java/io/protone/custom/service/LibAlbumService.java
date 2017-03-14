package io.protone.custom.service;

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

}
