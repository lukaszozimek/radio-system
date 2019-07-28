package io.protone.scheduler.service.log.parser.separator.impl;

import io.protone.scheduler.domain.SchEmission;
import io.protone.scheduler.domain.SchLogColumn;
import io.protone.scheduler.service.log.parser.separator.SchColumnSeparatorParser;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


/**
 * Created by lukaszozimek on 04.09.2017.
 */
@Service
public class SchColumnLibrarySeparatorParser implements SchColumnSeparatorParser {


    @Override
    public SchEmission parseColumnLog(SchEmission schEmission, List<SchLogColumn> schLogColumnList, SchLogColumn schLogColumn, LocalDate localDate, String logLine, String separator) {
        if (schEmission == null) {
            return schEmission;
        }
        if(logLine==null || logLine.isEmpty()){
            return schEmission;
        }
        String[] separatedStrings = logLine.split(separator);
        schEmission.setLibraryElementShortCut(separatedStrings[schLogColumn.getColumnSequence()].trim());

        return schEmission;
    }

}
