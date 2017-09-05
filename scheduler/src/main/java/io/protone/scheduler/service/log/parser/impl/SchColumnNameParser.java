package io.protone.scheduler.service.log.parser.impl;

import io.protone.library.domain.LibMediaItem;
import io.protone.scheduler.domain.SchEmission;
import io.protone.scheduler.domain.SchLogColumn;
import io.protone.scheduler.service.log.parser.SchColumnParser;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by lukaszozimek on 04.09.2017.
 */
public class SchColumnNameParser implements SchColumnParser {

    @Override
    public SchEmission parseColumnLog(SchEmission schEmission, List<SchLogColumn> schLogColumnList, SchLogColumn schLogColumn, LocalDate localDate, String logLine) {
        if (schEmission == null) {
            return schEmission;
        }
        if (schLogColumn.getColumnSequence() == 0) {
            if (schEmission.getMediaItem() != null) {
                schEmission.getMediaItem().name(logLine.substring(schLogColumn.getColumnSequence(), schLogColumn.getLength()).trim());
            } else {
                schEmission.mediaItem(new LibMediaItem().name(logLine.substring(schLogColumn.getColumnSequence(), schLogColumn.getLength()).trim()));
            }
        } else {
            int columnIndex = schLogColumnList.indexOf(schLogColumn);
            int elementStartPostion = 0;
            for (int i = 0; i < columnIndex; i++) {
                elementStartPostion = elementStartPostion + schLogColumnList.get(i).getLength();
            }
            if (schEmission.getMediaItem() != null) {
                schEmission.getMediaItem().name(logLine.substring(elementStartPostion, elementStartPostion + schLogColumn.getLength()).trim());
            } else {
                schEmission.mediaItem(new LibMediaItem().name(logLine.substring(elementStartPostion, elementStartPostion + schLogColumn.getLength()).trim()));

            }
        }
        return schEmission;
    }

}
