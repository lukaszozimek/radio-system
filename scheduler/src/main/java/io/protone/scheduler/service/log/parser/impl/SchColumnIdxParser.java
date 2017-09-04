package io.protone.scheduler.service.log.parser.impl;

import io.protone.library.domain.LibMediaItem;
import io.protone.library.service.LibMediaItemService;
import io.protone.scheduler.domain.SchEmission;
import io.protone.scheduler.domain.SchLogColumn;
import io.protone.scheduler.service.log.parser.SchColumnParser;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lukaszozimek on 04.09.2017.
 */

@Service
public class SchColumnIdxParser implements SchColumnParser {

    @Inject
    private LibMediaItemService libMediaItemService;

    private Map<String, LibMediaItem> libMediaItemMap;

    @PostConstruct
    public void initializeMap() {
        libMediaItemMap = new HashMap<>();
    }

    @Override
    public SchEmission parseColumnLog(SchEmission schEmission, SchLogColumn schLogColumn, String logLine) {
        if (schEmission == null) {
            return schEmission;
        }
        LibMediaItem libMediaItem;
        libMediaItem = libMediaItemMap.get("");
        if (libMediaItem == null) {
            libMediaItem = libMediaItemService.getMediaItem(schLogColumn.getNetwork().getShortcut(), schEmission.getLibraryElementShortCut(), "");
            libMediaItemMap.put("", libMediaItem);
        }
        return schEmission.mediaItem(libMediaItem);
    }


    public void prugEntityCache() {
        this.libMediaItemMap.clear();
    }
}
