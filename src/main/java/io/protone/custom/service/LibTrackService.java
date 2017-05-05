package io.protone.custom.service;

import io.protone.domain.LibTrack;
import io.protone.repository.library.LibTrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * Created by lukaszozimek on 05/05/2017.
 */
@Service
public class LibTrackService {

    @Autowired
    private LibTrackRepository libTrackRepository;

    @Transactional
    public Optional<LibTrack> saveLibTrack(LibTrack libTrack) {
        if (libTrack != null) {
            return Optional.of(libTrackRepository.saveAndFlush(libTrack));
        }
        return Optional.empty();
    }
}
