package io.protone.custom.service;

import io.protone.domain.LibLabel;
import io.protone.domain.LibTrack;
import io.protone.repository.library.LibLabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by lukaszozimek on 05/05/2017.
 */
@Service
public class LibLabelService {

    @Autowired
    private LibLabelRepository libLabelRepository;


    @Transactional
    public Optional<LibLabel> saveLibLabel(LibLabel libTrack) {
        if (libTrack != null) {
            return Optional.of(libLabelRepository.save(libTrack));
        }
        return Optional.empty();
    }

}
