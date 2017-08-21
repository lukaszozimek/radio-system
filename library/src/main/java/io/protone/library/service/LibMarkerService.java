package io.protone.library.service;


import io.protone.library.domain.LibMarker;
import io.protone.library.domain.LibMediaItem;
import io.protone.library.domain.enumeration.LibMarkerTypeEnum;
import io.protone.library.repository.LibMarkerRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by lukaszozimek on 15/03/2017.
 */
@Service
@Transactional
public class LibMarkerService {

    private final Logger log = LoggerFactory.getLogger(LibMarkerService.class);

    @Inject
    private LibMarkerRepository libMarkerRepository;

    public LibMarker saveLibMarker(String markerName, Long startTime, LibMediaItem mediaItem) {
        log.debug("Persisting LibMarker with markerName: {}, startTime: {} for mediaItem :{}", markerName, startTime, mediaItem);
        return libMarkerRepository.saveAndFlush(new LibMarker().name(markerName).startTime(startTime).markerType(LibMarkerTypeEnum.MT_BASIC).mediaItem(mediaItem));
    }

    public Optional<Set<LibMarker>> saveLibMarkers(Set<LibMarker> markers) {
        if (markers != null && CollectionUtils.isNotEmpty(markers)) {
            log.debug("Persisting LibMarker's: {}", markers);
            return Optional.of(libMarkerRepository.save(markers).stream().collect(Collectors.toSet()));
        }
        return Optional.empty();
    }

    public void deleteLibMarkers(Set<LibMarker> markers) {
        if (markers != null && !markers.isEmpty()) {
            libMarkerRepository.delete(markers);
        }
        libMarkerRepository.flush();
    }
}
