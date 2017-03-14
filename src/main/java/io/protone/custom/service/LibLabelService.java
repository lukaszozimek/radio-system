package io.protone.custom.service;

import io.protone.repository.LibLabelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by lukaszozimek on 14/03/2017.
 */
@Service
@Transactional
public class LibLabelService {

    @Inject
    private LibLabelRepository libLabelRepository;
}
