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
    public Slice<LibLabel> findLabels(String organizationShortcut, String channelShortcut, Pageable pagable) {
        return libLabelRepository.findSliceByChannel_Organization_ShortcutAndChannel_Shortcut(organizationShortcut, channelShortcut, pagable);
    }

    @Transactional
    public void deleteLabel(Long id, String organizationShortcut, String channelShortcut) {
        libLabelRepository.deleteByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(id, organizationShortcut, channelShortcut);
    }

    @Transactional
    public LibLabel findLabel(String organizationShortcut, String channelShortcut, Long id) {
        return libLabelRepository.findOneByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(id, organizationShortcut, channelShortcut);
    }
}
