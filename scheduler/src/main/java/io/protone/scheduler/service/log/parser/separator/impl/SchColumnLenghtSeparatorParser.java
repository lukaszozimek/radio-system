package io.protone.scheduler.service.log.parser.separator.impl;

import io.protone.library.domain.LibMediaItem;
import io.protone.scheduler.domain.SchEmission;
import io.protone.scheduler.domain.SchLogColumn;
import io.protone.scheduler.service.log.parser.separator.SchColumnSeparatorParser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by lukaszozimek on 04.09.2017.
 */
public class SchColumnLenghtSeparatorParser implements SchColumnSeparatorParser {

    @Override
    public SchEmission parseColumnLog(SchEmission schEmission, List<SchLogColumn> schLogColumnList, SchLogColumn schLogColumn, LocalDate localDate, String logLine, String separator) {
        if (schEmission == null) {
            return schEmission;
        }
        if (logLine == null || logLine.isEmpty()) {
            return schEmission;
        }
        String[] separatedStrings = logLine.split(separator);
        if (schEmission.getMediaItem() != null) {
            schEmission.getMediaItem().length((double) LocalTime.parse(separatedStrings[schLogColumn.getColumnSequence()].trim(), DateTimeFormatter.ofPattern("HH:MM")).toNanoOfDay());
        } else {
            schEmission.mediaItem(new LibMediaItem().length((double) LocalTime.parse(separatedStrings[schLogColumn.getColumnSequence()].trim(), DateTimeFormatter.ofPattern("HH:MM")).toNanoOfDay()));
        }
        return schEmission;

    }

}
