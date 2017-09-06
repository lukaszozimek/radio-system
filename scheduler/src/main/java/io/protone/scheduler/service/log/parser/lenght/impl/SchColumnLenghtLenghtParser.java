package io.protone.scheduler.service.log.parser.lenght.impl;

import io.protone.library.domain.LibMediaItem;
import io.protone.scheduler.domain.SchEmission;
import io.protone.scheduler.domain.SchLogColumn;
import io.protone.scheduler.service.log.parser.lenght.SchColumnLenghtParser;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by lukaszozimek on 04.09.2017.
 */
public class SchColumnLenghtLenghtParser implements SchColumnLenghtParser {

    @Override
    public SchEmission parseColumnLog(SchEmission schEmission, List<SchLogColumn> schLogColumnList, SchLogColumn schLogColumn, LocalDate localDate, String logLine) {
        if (schEmission == null) {
            return schEmission;
        }

        if (logLine == null || logLine.isEmpty()) {
            return schEmission;
        }
        if (schLogColumn.getColumnSequence() == 0) {
            if (schEmission.getMediaItem() != null) {
                String length = logLine.substring(schLogColumn.getColumnSequence(), schLogColumn.getLength()).trim();
                schEmission.getMediaItem().length(parseLenght(length));
            } else {
                String length = logLine.substring(schLogColumn.getColumnSequence(), schLogColumn.getLength()).trim();
                schEmission.mediaItem(new LibMediaItem().length(parseLenght(length)));
            }
        } else {
            int columnIndex = schLogColumnList.indexOf(schLogColumn);
            int elementStartPostion = 0;
            for (int i = 0; i < columnIndex; i++) {
                elementStartPostion = elementStartPostion + schLogColumnList.get(i).getLength();
            }

            if (schEmission.getMediaItem() != null) {
                String length = logLine.substring(elementStartPostion, elementStartPostion + schLogColumn.getLength()).trim();
                schEmission.getMediaItem().length(parseLenght(length));
            } else {
                String length = logLine.substring(elementStartPostion, elementStartPostion + schLogColumn.getLength()).trim();
                schEmission.mediaItem(new LibMediaItem().length(parseLenght(length)));
            }
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
