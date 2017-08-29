package io.protone.scheduler.service;

import io.protone.core.domain.CorChannel;
import io.protone.core.domain.CorNetwork;
import io.protone.library.service.LibFileItemService;
import io.protone.scheduler.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;


@Service
public class SchParseLogService {
    private final Logger log = LoggerFactory.getLogger(SchParseLogService.class);

    @Inject
    private LibFileItemService libFileItemService;

    @Transactional
    public Set<SchEmission> parseLog(SchLog schLog, CorChannel corChannel, CorNetwork corNetwork, SchPlaylist schPlaylist) throws IOException {
        InputStreamReader reader = null;
        Set<SchEmission> schEmissions = new HashSet<>();
        reader = new InputStreamReader(new ByteArrayInputStream(libFileItemService.download(schLog.getLibFileItem())));

        while (reader.ready()) {

        }

        reader.close();
        return schEmissions;
    }


    public static SchEmission emissionFactory(String cvsLine, char separators, SchLogConfiguration schLogConfiguration) {

        SchEmission schEmission = new SchEmission();

        //if empty, return!
        if (cvsLine == null && cvsLine.isEmpty()) {
            return schEmission;
        }


        StringBuffer curVal = new StringBuffer();

        char[] chars = cvsLine.toCharArray();

        for (char ch : chars) {

            if (separators != ch) {

                curVal.append(ch);
            }
        }


        return schEmission;
    }

    private SchTimeParams buildQeueParams() {
        return new SchTimeParams();
    }
}