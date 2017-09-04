package io.protone.scheduler.service.log.parser.impl;

import io.protone.scheduler.domain.SchEmission;
import io.protone.scheduler.domain.SchLogColumn;
import io.protone.scheduler.service.log.parser.SchColumnParser;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by lukaszozimek on 04.09.2017.
 */
@Service
public class SchColumnLibraryParser implements SchColumnParser {


    @Override
    public SchEmission parseColumnLog(SchEmission schEmission, List<SchLogColumn> schLogColumnList, SchLogColumn schLogColumn, String logLine) {
        if (schEmission == null) {
            return schEmission;
        }
        schEmission.setLibraryElementShortCut("");

        return schEmission;
    }

}
