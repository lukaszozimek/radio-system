package io.protone.scheduler.service.log.parser.separator.impl;

import io.protone.library.domain.LibMediaItem;
import io.protone.scheduler.domain.SchEmission;
import io.protone.scheduler.domain.SchLogColumn;
import io.protone.scheduler.service.log.parser.separator.SchColumnSeparatorParser;

import java.time.Duration;
import java.time.LocalDate;
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
            String length = separatedStrings[schLogColumn.getColumnSequence()].trim();
            schEmission.getMediaItem().length(parseLenght(length));
        } else {
            String length = separatedStrings[schLogColumn.getColumnSequence()].trim();
            schEmission.mediaItem(new LibMediaItem().length(parseLenght(length)));
        }
        return schEmission;
    }

    private double parseLenght(String length) {
        String[] time = length.split(":");
        int minutes = Integer.valueOf(time[0]);
        int seconds = Integer.valueOf(time[1]);
        return Duration.ofMinutes(minutes).plusSeconds(seconds).toMillis();
    }

}
