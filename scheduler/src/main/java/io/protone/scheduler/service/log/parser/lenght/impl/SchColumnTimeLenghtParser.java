package io.protone.scheduler.service.log.parser.lenght.impl;

import io.protone.scheduler.domain.SchEmission;
import io.protone.scheduler.domain.SchLogColumn;
import io.protone.scheduler.domain.SchTimeParams;
import io.protone.scheduler.service.log.parser.lenght.SchColumnLenghtParser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by lukaszozimek on 04.09.2017.
 */
public class SchColumnTimeLenghtParser implements SchColumnLenghtParser {

    @Override
    public SchEmission parseColumnLog(SchEmission schEmission, List<SchLogColumn> schLogColumnList, SchLogColumn schLogColumn, LocalDate localDate, String logLine) {
        if (schEmission == null) {
            return schEmission;
        }

        if(logLine==null || logLine.isEmpty()){
            return schEmission;
        }
        if (schLogColumn.getColumnSequence() == 0) {
            String startTime = logLine.substring(schLogColumn.getColumnSequence(), schLogColumn.getLength()).trim();
            if(startTime.isEmpty()){
                return schEmission;
            }
            String[] splitedTime = startTime.split(":");
            schEmission.setTimeParams(new SchTimeParams().startTime(LocalDateTime.of(localDate.getYear(), localDate.getMonth(), localDate.getDayOfMonth(), Integer.parseInt(splitedTime[0]), Integer.parseInt(splitedTime[1]), Integer.parseInt(splitedTime[2]))));
        } else {
            int columnIndex = schLogColumnList.indexOf(schLogColumn);
            int elementStartPostion = 0;
            for (int i = 0; i < columnIndex; i++) {
                elementStartPostion = elementStartPostion + schLogColumnList.get(i).getLength();
            }
            String startTime = logLine.substring(elementStartPostion, elementStartPostion + schLogColumn.getLength()).trim();
            if(startTime.isEmpty()){
                return schEmission;
            }
            String[] splitedTime = startTime.split(":");
            schEmission.setTimeParams(new SchTimeParams().startTime(LocalDateTime.of(localDate.getYear(), localDate.getMonth(), localDate.getDayOfMonth(), Integer.parseInt(splitedTime[0]), Integer.parseInt(splitedTime[1]), Integer.parseInt(splitedTime[2]))));
        }
        return schEmission;
    }


}
