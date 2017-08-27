package io.protone.library.service;


import io.protone.library.domain.LibLabel;
import io.protone.library.repository.LibLabelRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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

    @Transactional
    public LibLabel createOrUpdateLabel(LibLabel entity) {
        return libLabelRepository.saveAndFlush(entity);
    }

    @Transactional
    public Slice<LibLabel> findLabels(String networkShortcut, Pageable pagable) {
        return libLabelRepository.findSliceByNetwork_Shortcut(networkShortcut, pagable);
    }

    @Transactional
    public void deleteLabel(Long id, String networkShortcut) {
        libLabelRepository.deleteByIdAndNetwork_Shortcut(id, networkShortcut);
    }

    @Transactional
    public LibLabel findLabel(String networkShortcut, Long id) {
        return libLabelRepository.findOneByIdAndNetwork_Shortcut(id, networkShortcut);
    }
}
