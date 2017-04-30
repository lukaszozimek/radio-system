package io.protone.service.library;

import io.protone.domain.LibMarker;
import io.protone.domain.LibMediaItem;
import io.protone.domain.enumeration.LibMarkerTypeEnum;
import io.protone.repository.library.LibMarkerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
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

    public Set<LibMarker> saveLibMarkers(Set<LibMarker> markers) {
        log.debug("Persisting LibMarker's: {}", markers);
        return libMarkerRepository.save(markers).stream().collect(Collectors.toSet());
    }
}
