package io.protone.library.service;


import io.protone.library.domain.LibLabel;
import io.protone.library.repository.LibLabelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Created by lukaszozimek on 05/05/2017.
 */
@Service
public class LibLabelService {

    @Inject
    private LibLabelRepository libLabelRepository;


    @Transactional
    public Optional<LibLabel> saveLibLabel(LibLabel libTrack) {
        if (libTrack != null) {
            return Optional.of(libLabelRepository.save(libTrack));
        }
        return Optional.empty();
    }

}
