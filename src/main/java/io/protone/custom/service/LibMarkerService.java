package io.protone.custom.service;

import io.protone.domain.LibMarker;
import io.protone.domain.LibMediaItem;
import io.protone.domain.enumeration.LibMarkerTypeEnum;
import io.protone.repository.LibMarkerRepository;
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

    @Inject
    private LibMarkerRepository libMarkerRepository;

    public LibMarker saveLibMarker(String markerName, Long startTime, LibMediaItem mediaItem) {
        return libMarkerRepository.saveAndFlush(new LibMarker().name(markerName).startTime(startTime).markerType(LibMarkerTypeEnum.MT_BASIC).mediaItem(mediaItem));
    }
    public Set<LibMarker> saveLibMarkers(Set<LibMarker> markers){
        return libMarkerRepository.save(markers).stream().collect(Collectors.toSet());
    }
}
