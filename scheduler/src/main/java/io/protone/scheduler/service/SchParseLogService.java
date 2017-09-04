package io.protone.scheduler.service;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.library.service.LibFileItemService;
import io.protone.scheduler.domain.SchEmission;
import io.protone.scheduler.domain.SchLog;
import io.protone.scheduler.domain.SchLogColumn;
import io.protone.scheduler.domain.SchPlaylist;
import io.protone.scheduler.domain.enumeration.LogColumnTypEnum;
import io.protone.scheduler.service.log.parser.SchColumnParser;
import io.protone.scheduler.service.log.parser.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


@Service
public class SchParseLogService {
    private final Logger log = LoggerFactory.getLogger(SchParseLogService.class);

    @Inject
    private LibFileItemService libFileItemService;

    @Inject
    private SchColumnIdxParser columnParser;

    private Map<LogColumnTypEnum, SchColumnParser> columnParserMap;

    @PostConstruct
    public void initializeColumStrategies() {
        columnParserMap = new HashMap<>();
        columnParserMap.put(LogColumnTypEnum.LCT_IDX, columnParser);
        columnParserMap.put(LogColumnTypEnum.LCT_LENGHT, new SchColumnLenghtParser());
        columnParserMap.put(LogColumnTypEnum.LCT_NAME, new SchColumnNameParser());
        columnParserMap.put(LogColumnTypEnum.LCT_LIBRARY, new SchColumnLibraryParser());
        columnParserMap.put(LogColumnTypEnum.LCT_START_TIME, new SchColumnTimeParser());

    }

    @Transactional
    public Set<SchEmission> parseLog(SchLog schLog, CorChannel corChannel, CorNetwork corNetwork, SchPlaylist schPlaylist) throws IOException {
        InputStreamReader reader = null;
        Set<SchEmission> schEmissions = new HashSet<>();
        List<SchLogColumn> schLogColumns = new ArrayList<>(schLog.getSchLogConfiguration().getLogColumns());
        schLogColumns = putIdxLCTOnLastPostion(schLogColumns);
        reader = new InputStreamReader(new ByteArrayInputStream(libFileItemService.download(schLog.getLibFileItem())));
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            parseLogLine(schLogColumns, line);
        }
        bufferedReader.close();
        this.columnParser.prugEntityCache();
        return schEmissions;
    }

    private SchEmission parseLogLine(List<SchLogColumn> schLogColumns, String line) {
        SchEmission schEmission = new SchEmission();
        schLogColumns.stream().forEach(schLogColumn -> {
            this.columnParserMap.get(schLogColumn.getName()).parseColumnLog(schEmission, schLogColumn, line);
        });
        return schEmission;
    }

    private List<SchLogColumn> putIdxLCTOnLastPostion(List<SchLogColumn> schLogColumns) {
        SchLogColumn schLogColumn = schLogColumns.stream().filter(logColumn -> logColumn.getName().equals(LogColumnTypEnum.LCT_IDX)).findFirst().get();
        schLogColumns.remove(schLogColumn);
        schLogColumns.add(schLogColumn);
        return schLogColumns;
    }

}