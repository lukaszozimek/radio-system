package io.protone.scheduler.service;

import com.google.common.collect.Lists;
import io.protone.library.service.LibFileItemService;
import io.protone.scheduler.domain.SchEmission;
import io.protone.scheduler.domain.SchLog;
import io.protone.scheduler.domain.SchLogColumn;
import io.protone.scheduler.domain.enumeration.LogColumnTypEnum;
import io.protone.scheduler.service.log.parser.lenght.SchColumnLenghtParser;
import io.protone.scheduler.service.log.parser.lenght.impl.*;
import io.protone.scheduler.service.log.parser.separator.SchColumnSeparatorParser;
import io.protone.scheduler.service.log.parser.separator.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class SchParseLogService {
    private final Logger log = LoggerFactory.getLogger(SchParseLogService.class);

    @Inject
    private LibFileItemService libFileItemService;

    private Map<LogColumnTypEnum, SchColumnLenghtParser> logColumnTypEnumSchColumnLenghtParserMap;

    private Map<LogColumnTypEnum, SchColumnSeparatorParser> logColumnTypEnumSchColumnSeparatorParserMap;

    @PostConstruct
    public void initializeColumStrategies() {
        logColumnTypEnumSchColumnLenghtParserMap = new HashMap<>();
        logColumnTypEnumSchColumnLenghtParserMap.put(LogColumnTypEnum.LCT_IDX, new SchColumnIdxLenghtParser());
        logColumnTypEnumSchColumnLenghtParserMap.put(LogColumnTypEnum.LCT_LENGHT, new SchColumnLenghtLenghtParser());
        logColumnTypEnumSchColumnLenghtParserMap.put(LogColumnTypEnum.LCT_NAME, new SchColumnNameLenghtParser());
        logColumnTypEnumSchColumnLenghtParserMap.put(LogColumnTypEnum.LCT_LIBRARY, new SchColumnLibraryLenghtParser());
        logColumnTypEnumSchColumnLenghtParserMap.put(LogColumnTypEnum.LCT_START_TIME, new SchColumnTimeLenghtParser());

        logColumnTypEnumSchColumnSeparatorParserMap = new HashMap<>();
        logColumnTypEnumSchColumnSeparatorParserMap.put(LogColumnTypEnum.LCT_IDX, new SchColumnIdxSeparatorParser());
        logColumnTypEnumSchColumnSeparatorParserMap.put(LogColumnTypEnum.LCT_LENGHT, new SchColumnLenghtSeparatorParser());
        logColumnTypEnumSchColumnSeparatorParserMap.put(LogColumnTypEnum.LCT_NAME, new SchColumnNameSeparatorParser());
        logColumnTypEnumSchColumnSeparatorParserMap.put(LogColumnTypEnum.LCT_LIBRARY, new SchColumnLibrarySeparatorParser());
        logColumnTypEnumSchColumnSeparatorParserMap.put(LogColumnTypEnum.LCT_START_TIME, new SchColumnTimeSeparatorParser());

    }

    public List<SchEmission> parseLog(SchLog schLog) throws IOException {
        log.debug("Start Parsing Log {}", schLog.getSchLogConfiguration().getExtension());
        InputStreamReader reader;
        List<SchEmission> schEmissions = Lists.newArrayList();
        List<SchLogColumn> schLogColumns = schLog.getSchLogConfiguration().getLogColumns().stream().sorted(Comparator.comparing(SchLogColumn::getColumnSequence)).collect(Collectors.toList());
        reader = new InputStreamReader(new ByteArrayInputStream(libFileItemService.download(schLog.getLibFileItem())));
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        log.debug("Parse Log {}", schLog.getSchLogConfiguration().getExtension());
        while ((line = bufferedReader.readLine()) != null) {
            if (schLog.getSchLogConfiguration().getSpearator() != null && !schLog.getSchLogConfiguration().getSpearator().isEmpty()) {
                schEmissions.add(parseLogLineSeparator(schLogColumns, line.replaceAll("\\s+", ""), schLog.getDate(), schLog.getSchLogConfiguration().getSpearator()));
            } else {
                schEmissions.add(parseLogLine(schLogColumns, line, schLog.getDate()));
            }
        }
        bufferedReader.close();
        return schEmissions;
    }

    private SchEmission parseLogLine(List<SchLogColumn> schLogColumns, String line, LocalDate localDate) {
        final SchEmission[] schEmission = {new SchEmission()};
        String lineLine = line + "                                                      "; //Add some empty space to avoid indexUnBound exception buffered reader trim lines;
        schLogColumns.stream().forEach(schLogColumn -> {
            schEmission[0] = this.logColumnTypEnumSchColumnLenghtParserMap.get(schLogColumn.getName()).parseColumnLog(schEmission[0], schLogColumns, schLogColumn, localDate, lineLine);
        });
        return schEmission[0];
    }

    private SchEmission parseLogLineSeparator(List<SchLogColumn> schLogColumns, String line, LocalDate localDate, String separator) {
        final SchEmission[] schEmission = {new SchEmission()};
        String lineLine = line + "                                                      "; //Add some empty space to avoid indexUnBound exception buffered reader trim lines;
        schLogColumns.stream().forEach(schLogColumn -> {
            schEmission[0] = this.logColumnTypEnumSchColumnSeparatorParserMap.get(schLogColumn.getName()).parseColumnLog(schEmission[0], schLogColumns, schLogColumn, localDate, lineLine, separator);
        });
        return schEmission[0];
    }

}