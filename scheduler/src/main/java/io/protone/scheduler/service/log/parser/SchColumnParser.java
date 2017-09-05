package io.protone.scheduler.service.log.parser;

import io.protone.scheduler.domain.SchEmission;
import io.protone.scheduler.domain.SchLogColumn;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by lukaszozimek on 04.09.2017.
 */
public interface SchColumnParser {

    SchEmission parseColumnLog(SchEmission schEmission, List<SchLogColumn> schLogColumnList, SchLogColumn schLogColumn, LocalDate localDate, String logLine);

}
