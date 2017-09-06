package io.protone.scheduler.service.log.parser.lenght.impl;

import io.protone.library.domain.LibMediaItem;
import io.protone.scheduler.domain.SchEmission;
import io.protone.scheduler.domain.SchLogColumn;
import io.protone.scheduler.service.log.parser.lenght.SchColumnLenghtParser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.time.LocalTime.parse;

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
                double lenghtInMili = (double) parse(length, DateTimeFormatter.ofPattern("HH:MM")).toNanoOfDay();

                schEmission.getMediaItem().length(lenghtInMili);
            } else {
                String length = logLine.substring(schLogColumn.getColumnSequence(), schLogColumn.getLength()).trim();
                double lenghtInMili;
                lenghtInMili = (double) parse(length, DateTimeFormatter.ofPattern("HH:MM")).toNanoOfDay();
                schEmission.mediaItem(new LibMediaItem().length(lenghtInMili));
            }
        } else {
            int columnIndex = schLogColumnList.indexOf(schLogColumn);
            int elementStartPostion = 0;
            for (int i = 0; i < columnIndex; i++) {
                elementStartPostion = elementStartPostion + schLogColumnList.get(i).getLength();
            }

            if (schEmission.getMediaItem() != null) {
                String length = logLine.substring(elementStartPostion, elementStartPostion+schLogColumn.getLength()).trim();
                double lenghtInMili;
                lenghtInMili = (double) parse(length, DateTimeFormatter.ofPattern("HH:MM")).toNanoOfDay();

                schEmission.getMediaItem().length(lenghtInMili);
            } else {
                String length = logLine.substring(elementStartPostion, elementStartPostion+schLogColumn.getLength()).trim();
                double lenghtInMili;
                lenghtInMili = (double) parse(length, DateTimeFormatter.ofPattern("HH:MM")).toNanoOfDay();
                schEmission.mediaItem(new LibMediaItem().length(lenghtInMili));
            }
        }
        return schEmission;

    }

}
