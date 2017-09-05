package io.protone.scheduler.service.log.parser.separator.impl;

import io.protone.scheduler.domain.SchEmission;
import io.protone.scheduler.domain.SchLogColumn;
import io.protone.scheduler.domain.SchTimeParams;
import io.protone.scheduler.service.log.parser.separator.SchColumnSeparatorParser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by lukaszozimek on 04.09.2017.
 */
public class SchColumnTimeSeparatorParser implements SchColumnSeparatorParser {

    @Override
    public SchEmission parseColumnLog(SchEmission schEmission, List<SchLogColumn> schLogColumnList, SchLogColumn schLogColumn, LocalDate localDate, String logLine, String separator) {
        if (schEmission == null) {
            return schEmission;
        }
        if(logLine==null || logLine.isEmpty()){
            return schEmission;
        }
        String[] separatedStrings = logLine.split(separator);
        String[] splitedTime = separatedStrings[schLogColumn.getColumnSequence()].split(":");
        schEmission.setTimeParams(new SchTimeParams().startTime(LocalDateTime.of(localDate.getYear(), localDate.getMonth(), localDate.getDayOfMonth(), Integer.parseInt(splitedTime[0]), Integer.parseInt(splitedTime[1]), Integer.parseInt(splitedTime[2]))));

        return schEmission;
    }


}
