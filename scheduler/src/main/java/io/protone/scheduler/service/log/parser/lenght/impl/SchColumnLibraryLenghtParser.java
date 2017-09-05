package io.protone.scheduler.service.log.parser.lenght.impl;

import io.protone.scheduler.domain.SchEmission;
import io.protone.scheduler.domain.SchLogColumn;
import io.protone.scheduler.service.log.parser.lenght.SchColumnLenghtParser;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


/**
 * Created by lukaszozimek on 04.09.2017.
 */
@Service
public class SchColumnLibraryLenghtParser implements SchColumnLenghtParser {


    @Override
    public SchEmission parseColumnLog(SchEmission schEmission, List<SchLogColumn> schLogColumnList, SchLogColumn schLogColumn, LocalDate localDate, String logLine) {
        if (schEmission == null) {
            return schEmission;
        }

        if(logLine==null || logLine.isEmpty()){
            return schEmission;
        }
        if (schLogColumn.getColumnSequence() == 0) {
            schEmission.setLibraryElementShortCut(logLine.substring(schLogColumn.getColumnSequence(), schLogColumn.getLength()));
        } else {
            int columnIndex = schLogColumnList.indexOf(schLogColumn);
            int elementStartPostion = 0;
            for (int i = 0; i < columnIndex; i++) {
                elementStartPostion = elementStartPostion + schLogColumnList.get(i).getLength();
            }
            schEmission.setLibraryElementShortCut(logLine.substring(elementStartPostion, elementStartPostion + schLogColumn.getLength()).trim());
        }
        return schEmission;
    }

}
