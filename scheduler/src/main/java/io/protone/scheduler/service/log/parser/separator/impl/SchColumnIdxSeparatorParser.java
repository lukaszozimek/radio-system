package io.protone.scheduler.service.log.parser.separator.impl;

import io.protone.library.domain.LibMediaItem;
import io.protone.scheduler.domain.SchEmission;
import io.protone.scheduler.domain.SchLogColumn;
import io.protone.scheduler.service.log.parser.separator.SchColumnSeparatorParser;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by lukaszozimek on 04.09.2017.
 */

public class SchColumnIdxSeparatorParser implements SchColumnSeparatorParser {


    @Override
    public SchEmission parseColumnLog(SchEmission schEmission, List<SchLogColumn> schLogColumnList, SchLogColumn schLogColumn, LocalDate localDate, String logLine, String separator) {
        if (schEmission == null) {
            return schEmission;
        }
        if(logLine==null || logLine.isEmpty()){
            return schEmission;
        }
        String[] separatedStrings = logLine.split(separator);
        if (schEmission.getMediaItem() != null) {
            schEmission.getMediaItem().idx(separatedStrings[schLogColumn.getColumnSequence()].trim());
        } else {
            schEmission.mediaItem(new LibMediaItem().idx(separatedStrings[schLogColumn.getColumnSequence()].trim()));
        }
        return schEmission;
    }


}
